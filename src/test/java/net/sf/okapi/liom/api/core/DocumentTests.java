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

package net.sf.okapi.liom.api.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
import org.oasisopen.liom.api.core.IWithNotes;

import net.sf.okapi.liom.api.core.Factory;

public class DocumentTests {

	@Test
	public void testVersion () {
		IDocument doc = Factory.SI.createDocument();
		assertEquals("1.0", doc.getVersion()); // Default
	}
	
	@Test
	public void testSrcLang () {
		IDocument doc = Factory.SI.createDocument();
		assertEquals("en", doc.getSrcLang()); // Default
		doc.setSrcLang("zh");
		assertEquals("zh", doc.getSrcLang());
	}
	
	@Test
	public void testTrgLang () {
		IDocument doc = Factory.SI.createDocument();
		assertEquals(null, doc.getTrgLang()); // Default
		doc.setTrgLang("fr");
		assertEquals("fr", doc.getTrgLang());
	}
	
	@Test
	public void testSubDocument () {
		IDocument doc = Factory.SI.createDocument();
		// Add
		ISubDocument sd1 = doc.addSubDocument("sd1");
		assertNotNull(sd1);
		// Access
		ISubDocument sd2 = doc.get(0);
		assertTrue(sd1==sd2);
		// Find
		doc.addSubDocument("lastSD");
		assertEquals("lastSD", doc.find("lastSD").getId());
		assertNull(doc.find("lastsd"));
	}
	
	@Test
	public void testSimple () {
		IDocument doc = Factory.SI.createDocument();
		doc.setSrcLang("en");
		doc.setTrgLang("fr");
		assertEquals("en", doc.getSrcLang());
		assertEquals("fr", doc.getTrgLang());
		
		ISubDocument sd = doc.addSubDocument("f1");
		assertTrue(doc==sd.getDocument());
		
		IUnit unit = sd.addUnit("f1-u1");
		assertTrue(sd==unit.getParent());
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
		
		IGroup group = sd.addGroup("f1-g1");
		assertTrue(sd==group.getParent());
		
		unit = group.addUnit("f1-g1-u2");
		assertTrue(group==unit.getParent());
		
		IGroup group2 = group.addGroup("f1-g1-g2");
		assertTrue(group==group2.getParent());

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

	@Test
	public void testCreateDocumentUpTosegment () {
		ISegment seg = Factory.SI.createDocumentUpToSegment("de", "zh-CN", "sd1", "u1");
		IUnit unit = seg.getParent();
		assertEquals("u1", unit.getId());
		ISubDocument sd = unit.getParent();
		assertEquals("sd1", sd.getId());
		IDocument doc = sd.getParent();
		assertEquals("de", doc.getSrcLang());
		assertEquals("zh-CN", doc.getTrgLang());
		assertTrue(doc==sd.getDocument());
	}
	
	@Test
	public void testTranslateInheritanceInUnit () {
		IGroup g1 = Factory.SI.createDocument().addSubDocument("f1").addGroup("g1");
		g1.setTranslate(false);
		IGroup g2 = g1.addGroup("g2");
		IUnit unit = g2.addUnit("u1");
		assertFalse(unit.getTranslate());
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
		IContent src = su.getSource();
		System.out.println(indent+" src="+src.toString());
	}

}
