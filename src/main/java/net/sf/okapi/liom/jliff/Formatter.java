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
import org.oasisopen.liom.api.core.INote;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISubDocument;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithContext;
import org.oasisopen.liom.api.core.IWithGroupOrUnit;
import org.oasisopen.liom.api.core.IWithNotes;

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

	String toJLIFF (IDocument document) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"version\":"+gson.toJson(document.getVersion()));
		sb.append(",\"srcLang\":"+gson.toJson(document.getSrcLang()));
		sb.append(",\"trgLang\":"+gson.toJson(document.getTrgLang()));
		return sb.toString();
	}
	
	String toJLIFF (ISubDocument sd) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"id\":"+gson.toJson(sd.getId()));
		sb.append(",\"original\":"+gson.toJson(sd.getOriginal()));
		sb.append(",");
		toJLIFF((IWithContext)sd, sb);
		sb.append(",");
		toJLIFF((IWithNotes)sd, sb);
		sb.append(",");
		toJLIFF((IWithGroupOrUnit)sd, sb);
		return sb.toString();
	}
	
	void toJLIFF (IWithContext item,
		StringBuilder sb)
	{
		sb.append("\"translate\":"+gson.toJson(item.getTranslate()));
		sb.append(",\"canResegment\":"+gson.toJson(item.getCanResegment()));
		sb.append(",\"preserveWS\":"+gson.toJson(item.getPreserveWS()));
		sb.append(",\"srcDir\":"+gson.toJson(item.getSrcDir()));
		sb.append(",\"trgDir\":"+gson.toJson(item.getTrgDir()));
	}

	void toJLIFF (IWithNotes item,
		StringBuilder sb)
	{
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

	void toJLIFF (IWithGroupOrUnit item,
		StringBuilder sb)
	{
		sb.append("\"groupOrUnits\":[");
		for ( int i=0; i<item.size(); i++ ) {
			IGroupOrUnit gou = item.get(i);
			if ( i>0 ) sb.append(",");
			toJLIFF(gou, sb);
		}
		sb.append("]");
	}

	void toJLIFF (IGroupOrUnit item,
		StringBuilder sb)
	{
		sb.append("{");
		sb.append("\"isUnit\":"+gson.toJson(item.isUnit()));
		sb.append(",");
		toJLIFF((IWithContext)item, sb);
		sb.append(",");
		toJLIFF((IWithNotes)item, sb);
		if ( item.isUnit() ) {
			unitToJLIFF(item.asUnit(), sb);
		}
		else { // Recursive call
			IGroup group = item.asGroup();
			sb.append(",\"groupOrUnits\":[");
			for ( int i=0; i<group.size(); i++ ) {
				IGroupOrUnit gou = group.get(i);
				if ( i>0 ) sb.append(",");
				toJLIFF(gou, sb);
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
	}
	
	void toJLIFF (ISubUnit su,
		StringBuilder sb)
	{
		sb.append("{");
		sb.append("\"isSegment\":"+gson.toJson(su.isSegment()));
		sb.append(",\"id\":"+gson.toJson(su.getId()));
		if ( su.isSegment() ) {
			ISegment seg = su.asSegment();
			sb.append(",\"canResegment\":"+gson.toJson(seg.getCanReSegment()));
			sb.append(",\"state\":"+gson.toJson(seg.getState()));
			sb.append(",\"subState\":"+gson.toJson(seg.getSubState()));
		}
		toJLIFF(su.getSource(), sb);
		toJLIFF(su.getTarget(), sb);
		sb.append("}");
	}

	void toJLIFF (IContent content,
		StringBuilder sb)
	{
		if ( content == null ) return;
		sb.append(",\""+(content.isSource() ? "source" : "target")+"\":{");
		sb.append("\"lang\":"+gson.toJson(content.getLang()));
		sb.append(",\"preserveWS\":"+gson.toJson(content.getPreserveWS()));
		if ( !content.isSource() ) {
			sb.append(",\"order\":"+gson.toJson(content.asTarget().getOrder()));
		}
		
		sb.append(",\"text\":"+gson.toJson(content.toString()));
		
		sb.append("}");
	}
	
	public String makePretty (String json) {
    	JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jo);
    }

}
