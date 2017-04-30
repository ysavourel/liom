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

package net.sf.okapi.liom.v1.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.IGroupOrUnit;
import org.oasisopen.liom.api.core.INote;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISource;
import org.oasisopen.liom.api.core.ISubDocument;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithContext;
import org.oasisopen.liom.api.core.IWithNotes;

public class DocumentTests {

	@Test
	public void testSimple () {
		IDocument doc = Factory.SI.createDocument();
		doc.setSrcLang("en");
		doc.setTrgLang("fr");
		assertEquals("en", doc.getSrcLang());
		assertEquals("fr", doc.getTrgLang());
		
		ISubDocument sd = doc.addSubDocument();
		sd.setId("f1");
		assertTrue(doc==sd.getDocument());
		
		IUnit unit = sd.addUnit();
		assertTrue(sd==unit.getParent());
		unit.setId("f1-u1");
		unit.addNote()
			.setText("Text of the note n1")
			.setId("n1");
		assertTrue(unit.hasNote());
		INote note = unit.getNotes().get(0);
		assertEquals("Text of the note n1", note.getText());
		assertEquals("n1", note.getId());
		
		assertFalse(unit.hasNCObject("ext:test"));
		Object obj = unit.getNCObjects().get("ext:test");
		assertNull(obj);
		
		unit.getNCObjects().set("ext:test", "ext:test DATA");
		assertTrue(unit.hasNCObject("ext:test"));
		String str = unit.getNCObjects().get("ext:test");
		assertEquals("ext:test DATA", str);
		
		ISegment seg = unit.addSegment();
		seg.setId("f1-u1-s1");
		seg.getSource().append("Source").append(' ').append("text.");
		assertEquals("f1-u1-s1", seg.getId());
		assertEquals("Source text.", seg.getSource().toString());
		
		IGroup group = sd.addGroup();
		assertTrue(sd==group.getParent());
		group.setId("f1-g1");
		
		unit = group.addUnit();
		assertTrue(group==unit.getParent());
		unit.setId("f1-g1-u2");
		
		IGroup group2 = group.addGroup();
		assertTrue(group==group2.getParent());
		group2.setId("f1-g1-g2");

		assertFalse(doc.isEmpty());
		System.out.println("- Document");
		System.out.println("version="+doc.getVersion());
		System.out.println("srcLang="+doc.getSrcLang());
		System.out.println("trgLang="+doc.getTrgLang());
		
		for ( int i=0; i<doc.size(); i++ ) {
			sd = doc.get(i);
			System.out.println(" - Sub-document");
			System.out.println(" id="+sd.getId());
			assertFalse(sd.isEmpty());
			for ( int j=0; j<sd.size(); j++ ) {
				showGroupOrUnit(sd.get(j), 3);
			}
		}
	}
	
	private void showGroupOrUnit (IGroupOrUnit gou,
		int level)
	{
		String indent = String.format("%"+level+"s"," ");
		System.out.println(indent+(gou.isUnit() ? "- Unit" : "- Group")+" (level "+level+")");
		System.out.println(indent+"id="+gou.getId());
		showContext(gou, indent);
		System.out.println(indent+"name="+gou.getName());
		System.out.println(indent+"type="+gou.getType());
		showNotes(gou, indent);
		if ( gou.isUnit() ) {
			IUnit unit = gou.asUnit();
			for ( int i=0; i<unit.size(); i++ ) {
				ISubUnit su = unit.get(i);
				System.out.println(indent+(su.isSegment() ? "segment" : "Ignorable"));
				showSubUnit(su, indent);
			}
		}
		else {
			IGroup group = gou.asGroup();
			for ( int i=0; i<group.size(); i++ ) {
				showGroupOrUnit(group.get(i), level+1);
			}
		}
	}
	
	private void showContext (IWithContext item,
		String indent)
	{
		System.out.println(indent+"translate="+item.getTranslate());
		System.out.println(indent+"canResegment="+item.getCanResegment());
		System.out.println(indent+"preserveWS="+item.getPreserveWS());
		System.out.println(indent+"srcDir="+item.getSrcDir());
		System.out.println(indent+"trgDir="+item.getTrgDir());
	}

	private void showNotes (IWithNotes item,
		String indent)
	{
		System.out.println(indent+"hasNotes="+item.hasNote());
		if ( item.hasNote() ) {
			for ( int i=0; i<item.getNotes().size(); i++ ) {
				INote note = item.getNotes().get(i);
				System.out.println(indent+"Note "+(i+1));
				System.out.println(indent+" id="+note.getId());
				System.out.println(indent+" appliesto="+note.getAppliesTo());
				System.out.println(indent+" category="+note.getCategory());
				System.out.println(indent+" priority="+note.getPriority());
				System.out.println(indent+" text="+note.getText());
			}
		}
	}
	
	private void showSubUnit (ISubUnit su,
		String indent)
	{
		System.out.println(indent+" id="+su.getId());
		ISource src = su.getSource();
		System.out.println(indent+" src="+src.toString());
	}

}
