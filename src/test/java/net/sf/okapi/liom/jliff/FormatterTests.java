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

import org.junit.Test;
import org.oasisopen.liom.api.core.AppliesTo;
import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISubDocument;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IfNoTarget;
import org.oasisopen.liom.api.core.TargetState;
import org.oasisopen.liom.api.glossary.IGlossEntry;

import net.sf.okapi.liom.api.core.Factory;
import net.sf.okapi.liom.api.core.Unit;

public class FormatterTests {

	@Test
	public void testDocument1 () {
		Formatter fmt = new Formatter();
		IDocument doc = createDocument1();
		fmt.process(doc);
		System.out.println("===== Document 1 =====");
		System.out.println(fmt.makePretty(fmt.getOutput()));
		System.out.println("==========");
	}
	
	@Test
	public void testDocument2 () {
		Formatter fmt = new Formatter();
		fmt.process(createDocument2());
		System.out.println("===== Document 2 =====");
		System.out.println(fmt.makePretty(fmt.getOutput()));
		System.out.println("==========");
	}
	
	@Test
	public void testDocument3 () {
		Formatter fmt = new Formatter();
		fmt.process(createDocument3());
		System.out.println("===== Document 3 =====");
		System.out.println(fmt.makePretty(fmt.getOutput()));
		System.out.println("==========");
	}
	
	@Test
	public void testDocument4 () {
		// Create the document
		IDocument doc = Factory.SI.createDocument().setSrcLang("en").setTrgLang("fy");
		// Create a sub-document and a group
		IGroup group = doc.addSubDocument("f1").addGroup("g1");
		
		// Create a first unit and its source and target
		ISegment seg = group.addUnit("u1").addSegment();
		seg.getSource().append("Summer is coming.");
		seg.setState(TargetState.TRANSLATED) // Set the target as 'translated'
			.getTarget(IfNoTarget.CREATE_EMPTY).append("Simmer komt deroan.");
		// Add a couple of non-core fields
		seg.getParent().getNCFields().set("my:ncName1", "ncValue1");
		seg.getParent().getNCFields().set("my:ncName2", "ncValue2");
		
		// Create a second unit with source (two segments and one ignorable)
		IUnit unit = group.addUnit("u2");
		unit.addSegment().getSource().append("Summer will be hot.");
		unit.addIgnorable().getSource().append(' ');
		unit.addSegment().getSource().append("But I will be at the beach.");
		// Add glossary
		unit.getGlossary().addEntry().setId("ge1").newTerm("Term text").setSource("Term source");
		// Add second glossary entry
		IGlossEntry ge = unit.getGlossary().addEntry();
		ge.newTerm("hot");
		ge.addTranslation("hyt").setSource("Google");
		// Add non-core fields to the glossary entry
		ge.getNCFields().set("my:ncName3", "ncValue3");
		
		// Output it in JLIFF
		Formatter fmt = new Formatter();
		fmt.process(doc);
		System.out.println(fmt.makePretty(fmt.getOutput()));
	}

	@Test
	public void testUnit1 () {
		IUnit unit = new Unit(null, "u1");
		unit.setName("name");
		unit.setType("unit-type");
		unit.addSegment().getSource().append("Source");
		ISegment seg = unit.addSegment();
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("Target");
		seg.setState(TargetState.TRANSLATED);
		seg.setSubState("my:trans-step");
		
		unit.addNote().setId("n1").setAppliesTo(AppliesTo.TARGET).setText("Text for n1");
		IGlossEntry ge = unit.getGlossary().addEntry();
		ge.setId("ge1").newTerm("term value");
		
		// Output it in JLIFF
		Formatter fmt = new Formatter();
		fmt.process(unit);
		System.out.println(fmt.makePretty(fmt.getOutput()));
	}
	
	private IDocument createDocument1 () {
		IDocument doc = Factory.SI.createDocument();
		doc.setTrgLang("fr");
		ISubDocument sd = doc.addSubDocument("f1");
		sd.setOriginal("myOriginalFile");
		// Add notes
		sd.addNote().setText("My note n1").setId("n1").setAppliesTo(AppliesTo.SOURCE);
		sd.addNote().setText("My note n2").setId("n2").setPriority(1);
		
		// Add a unit
		IUnit unit = sd.addUnit("u1");
		unit.setName("u1Name");
		ISegment seg = unit.addSegment();
		seg.setId("u1-s1");
		seg.getSource().append("Source for u1-s1.");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("Target for u1-s1.");
		seg.setState(TargetState.TRANSLATED);
		
		// Add a group
		IGroup group = sd.addGroup("g1");
		// Add a unit in the group
		unit = group.addUnit("g1-u1");
		// Add a group to the group
		IGroup group2 = sd.addGroup("g2");
		group2.setCanResegment(false);
		group2.setTranslate(false);
		// Add a unit in the nested group
		unit = group2.addUnit("g2-u1");
		seg = unit.addSegment();
		seg.setId("g2-u1-s1");
		seg.getSource().append("Source for g2-u1-s1.");
		
		return doc;
	}

	private IDocument createDocument2 () {
		IDocument doc = Factory.SI.createDocument();
		doc.setSrcLang("en-US");
		doc.setSrcLang("ja-JP");
		ISubDocument sd = doc.addSubDocument("f1");
		sd.setOriginal("Graphic Example.psd");
		sd.newSkeleton().setRef("Graphic Example.psd.skl");
		
		IUnit unit = sd.addUnit("1");
		ISegment seg = unit.addSegment();
		seg.getSource().append("Quetzal");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("Quetzal");
		
		unit = sd.addUnit("2");
		seg = unit.addSegment();
		seg.getSource().append("An application to manipulate and process XLIFF documents");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("XLIFF \u6587\u66f8\u3092\u7de8\u96c6\u3001\u307e\u305f\u306f\u51e6\u7406 \u3059\u308b\u30a2\u30d7\u30ea\u30b1\u30fc\u30b7\u30e7\u30f3\u3067\u3059\u3002");
		
		unit = sd.addUnit("3");
		seg = unit.addSegment();
		seg.getSource().append("XLIFF Data Manager");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("XLIFF \u30c7\u30fc\u30bf\u30fb\u30de\u30cd\u30fc\u30b8\u30e3");
		return doc;
	}
	
	private IDocument createDocument3 () {
		IDocument doc = Factory.SI.createDocument();
		doc.setSrcLang("en");
		doc.setSrcLang("fr");
		ISubDocument sd = doc.addSubDocument("f1");
		
		IUnit unit = sd.addUnit("u1");
		ISegment seg = unit.addSegment();
		seg.setCanResegment(false);
		seg.setState(TargetState.TRANSLATED);
		seg.getSource().append("text");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("TEXT");

		return doc;
	}
	
}
