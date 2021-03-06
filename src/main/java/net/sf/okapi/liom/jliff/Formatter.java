/*===========================================================================
  Copyright (C) 2017 by the Okapi Framework contributors
-----------------------------------------------------------------------------
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
===========================================================================*/

package net.sf.okapi.liom.jliff;

import org.oasisopen.liom.api.core.ICollection;
import org.oasisopen.liom.api.core.IContent;
import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.IGroupOrUnit;
import org.oasisopen.liom.api.core.INCFields;
import org.oasisopen.liom.api.core.INote;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISkeleton;
import org.oasisopen.liom.api.core.ISubDocument;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithContext;
import org.oasisopen.liom.api.core.IWithNCFields;
import org.oasisopen.liom.api.core.IWithNotes;
import org.oasisopen.liom.api.core.TargetState;
import org.oasisopen.liom.api.glossary.IDefinition;
import org.oasisopen.liom.api.glossary.IGlossEntry;
import org.oasisopen.liom.api.glossary.IGlossary;
import org.oasisopen.liom.api.glossary.ITerm;
import org.oasisopen.liom.api.glossary.ITranslation;
import org.oasisopen.liom.api.glossary.IWithGlossary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Formatter {

	private final Gson gson = new Gson();

	private StringBuilder out;

	public String getOutput () {
		return out.toString();
	}
	
	public void process (IDocument document) {
		out = new StringBuilder("{");
		out.append(toJLIFF(document)); // Document fields
		
		out.append(",\"subDocuments\":["); // Start list of sub-documents
		for ( int i=0; i<document.size(); i++ ) {
			if ( i>0 ) out.append(",");
			out.append("{"); // Start sub-document
			ISubDocument sd = document.get(i);
			out.append(toJLIFF(sd)); // Sub-document fields
			out.append("}"); // End sub-document
		}
		out.append("]"); // End list of sub-documents
		
		out.append("}"); // End document
	}

	public void process (IUnit unit) {
		out = new StringBuilder("");
		toJLIFF ((IGroupOrUnit)unit, null, out);
	}
	
	// Returns true if the next field at the same level will need a leading comma, false otherwise. 
	boolean inn (String name,
		String value,
		boolean leadComma,
		StringBuilder sb)
	{
		if ( value == null ) {
			// This field was not written, the next will need a comma if this one would have need one
			return leadComma;
		}
		if ( leadComma ) sb.append(',');
		sb.append("\""+name+"\":"+gson.toJson(value));
		return true; // The field was written, so the next will need a comma
	}

	String toJLIFF (IDocument document) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"version\":"+gson.toJson(document.getVersion()));
		sb.append(",\"srcLang\":"+gson.toJson(document.getSrcLang()));
		inn("trgLang", document.getTrgLang(), true, sb);
		return sb.toString();
	}
	
	String toJLIFF (ISubDocument sd) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"id\":"+gson.toJson(sd.getId()));
		inn("original", sd.getOriginal(), true, sb);
		withContextToJLIFF((IWithContext)sd, null, sb); // No context parent for sub-document
		withNotesToJLIFF((IWithNotes)sd, sb, true);
		skeletonToJLIFF(sd.getSkeleton(), sb);
		sb.append(",");
		subDocGroupsOrUnits(sd, sb);
		return sb.toString();
	}
	
	void skeletonToJLIFF (ISkeleton skeleton,
		StringBuilder sb)
	{
		if ( skeleton == null ) return;
		sb.append(",\"skeleton\":{");
		if ( skeleton.getRef() != null ) {
			sb.append("\"ref\":"+gson.toJson(skeleton.getRef()));
		}
		else if ( skeleton.getText() != null ) {
			sb.append("\"text\":"+gson.toJson(skeleton.getText()));
		}
		sb.append("}");
	}
	
	void withContextToJLIFF (IWithContext item,
		IWithContext parent,
		StringBuilder sb)
	{
		if (( parent == null ) || ( parent.getTranslate() != item.getTranslate() )) {
			sb.append(",\"translate\":"+gson.toJson(item.getTranslate()));
		}
		if (( parent == null ) || ( parent.getCanResegment() != item.getCanResegment() )) {
			sb.append(",\"canResegment\":"+gson.toJson(item.getCanResegment()));
		}
		if (( parent == null ) || ( parent.getPreserveWS() != item.getPreserveWS() )) {
			sb.append(",\"preserveWS\":"+gson.toJson(item.getPreserveWS()));
		}
		if (( parent == null ) || !parent.getSrcDir().equals(item.getSrcDir()) ) {
			sb.append(",\"srcDir\":"+gson.toJson(item.getSrcDir().toString()));
		}
		if (( parent == null ) || !parent.getTrgDir().equals(item.getTrgDir()) ) {
			sb.append(",\"trgDir\":"+gson.toJson(item.getTrgDir().toString()));
		}
	}

	void withNotesToJLIFF (IWithNotes item,
		StringBuilder sb,
		boolean leadComma)
	{
		if ( !item.hasNote() ) return;
		if ( leadComma ) {
			sb.append(",");
		}
		sb.append("\"notes\":[");
		if ( item.hasNote() ) {
			ICollection<INote> col =  item.getNotes();
			for ( int i=0; i<col.size(); i++ ) {
				INote note = col.get(i);
				if ( i>0 ) sb.append(",");
				sb.append(gson.toJson(note));
			}
		}
		sb.append("]");
	}

	void withGlossaryToJLIFF (IWithGlossary item,
		StringBuilder sb,
		boolean leadComma)
	{
		if ( !item.hasGlossary() ) return;

		if ( leadComma ) sb.append(",");
		sb.append("\"glossary\":[");
		IGlossary glossary = item.getGlossary();
		for ( int i=0; i<glossary.size(); i++ ) {
			IGlossEntry entry = glossary.get(i);
			
			if ( i>0 ) sb.append(",");
			sb.append("{");
			
			boolean needComma2 = false;
			needComma2 = inn("id", entry.getId(), needComma2, sb);
			needComma2 = inn("ref", entry.getRef(), needComma2, sb);
			
			// Term
			ITerm term = entry.getTerm();
			if ( needComma2 ) sb.append(",");
			sb.append("\"term\":{");
			boolean needComma3 = false;
			needComma3 = inn("text", term.getText(), needComma3, sb);
			inn("source", term.getSource(), needComma3, sb);
			sb.append("}");
			
			// Definition
			IDefinition def = entry.getDefinition();
			if ( def != null ) {
				sb.append(",\"definition\":{");
				needComma3 = false;
				needComma3 = inn("text", def.getText(), needComma3, sb);
				inn("source", def.getSource(), needComma3, sb);
				sb.append("}");
			}
			
			// Translations
			if ( !entry.isEmpty() ) {
				sb.append(",\"translations\":[");
				for ( int j=0; j<entry.size(); j++ ) {
					if ( j>0 ) sb.append(",");
					sb.append("{");
					ITranslation trans = entry.get(j);
					needComma3 = false;
					needComma3 = inn("text", trans.getText(), needComma3, sb);
					needComma3 = inn("source", trans.getSource(), needComma3, sb);
					needComma3 = inn("id", trans.getId(), needComma3, sb);
					inn("ref", trans.getRef(), needComma3, sb);
					sb.append("}");
				}
				sb.append("]");
			}

			withNCFieldsToJLIFF(entry, true, sb);
			
			sb.append("}"); // End entry
		}
		sb.append("]");
	}

	void withNCFieldsToJLIFF (IWithNCFields item,
		boolean needComma,
		StringBuilder sb)
	{
		if ( !item.hasNCField() ) return;
		if ( needComma ) sb.append(",");
		sb.append("\"ncFields\":{");
		boolean needComma2 = false;
		INCFields flds = item.getNCFields();
		for ( String name : flds ) {
			needComma2 = inn(name, flds.get(name), needComma2, sb);
		}
		sb.append("}");
	}

	void subDocGroupsOrUnits (ISubDocument item,
		StringBuilder sb)
	{
		sb.append("\"groupsOrUnits\":[");
		for ( int i=0; i<item.size(); i++ ) {
			IGroupOrUnit gou = item.get(i);
			if ( i>0 ) sb.append(",");
			toJLIFF(gou, item, sb);
		}
		sb.append("]");
	}

	void toJLIFF (IGroupOrUnit item,
		IWithContext parent,
		StringBuilder sb)
	{
		sb.append("{");
		sb.append("\"isUnit\":"+gson.toJson(item.isUnit()));
		sb.append(",\"id\":"+gson.toJson(item.getId()));
		withContextToJLIFF(item, parent, sb);
		withNotesToJLIFF(item, sb, true);
		withNCFieldsToJLIFF(item, true, sb);
		if ( item.isUnit() ) {
			unitToJLIFF(item.asUnit(), sb);
		}
		else { // Recursive call
			IGroup group = item.asGroup();
			sb.append(",\"groupsOrUnits\":[");
			for ( int i=0; i<group.size(); i++ ) {
				IGroupOrUnit gou = group.get(i);
				if ( i>0 ) sb.append(",");
				toJLIFF(gou, item, sb);
			}
			sb.append("]");
		}
		sb.append("}");
	}
	
	private void unitToJLIFF (IUnit unit,
		StringBuilder sb)
	{
		sb.append(",\"subUnits\":[");
		for ( int i=0; i<unit.size(); i++ ) {
			if ( i>0 ) sb.append(",");
			toJLIFF(unit.get(i), sb);
		}
		sb.append("]");
		withGlossaryToJLIFF(unit, sb, true);
	}
	
	void toJLIFF (ISubUnit su,
		StringBuilder sb)
	{
		sb.append("{");
		sb.append("\"isSegment\":"+gson.toJson(su.isSegment()));
		inn("id", su.getId(), true, sb);
		sb.append(",\"srcLang\":"+gson.toJson(su.getSrcLang()));
		sb.append(",\"trgLang\":"+gson.toJson(su.getTrgLang()));
		
		if ( su.getPreserveWS() != su.getParent().getPreserveWS() ) {
			sb.append(",\"preserveWS\":"+gson.toJson(su.getPreserveWS()));
		}

		if ( su.getTrgOrder() > 0 ) {
			sb.append(",\"trgOrder\":"+gson.toJson(su.getTrgOrder()));
		}
		
		if ( su.isSegment() ) {
			ISegment seg = su.asSegment();
			if ( seg.getCanResegment() != seg.getParent().getCanResegment() ) {
				sb.append(",\"canResegment\":"+gson.toJson(seg.getCanResegment()));
			}
			if ( seg.getState() != TargetState.DEFAULT ) {
				sb.append(",\"state\":"+gson.toJson(seg.getState().toString()));
			}
			inn("subState", seg.getSubState(), true, sb);
		}
		toJLIFF(su.getSource(), sb);
		toJLIFF(su.getTarget(), sb);
		sb.append("}");
	}

	void toJLIFF (IContent content,
		StringBuilder sb)
	{
		if ( content == null ) return;
		sb.append(",\""+(content.isSource() ? "source" : "target")+"\":[");
		boolean first = true;
		for ( Object obj : content ) {
			if ( obj instanceof String ) {
				if ( first ) first = false;
				else sb.append(",");
				sb.append("{\"text\":"+gson.toJson(content.toString())+"}");
			}
		}
		sb.append("]");
	}
	
	public String makePretty (String json) {
    	JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jo);
    }

}
