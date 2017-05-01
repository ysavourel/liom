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

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oasisopen.liom.api.core.AppliesTo;
import org.oasisopen.liom.api.core.IfNoTarget;
import org.oasisopen.liom.api.core.State;
import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISubDocument;
import org.oasisopen.liom.api.core.IUnit;

import net.sf.okapi.liom.api.core.Factory;
import net.sf.okapi.liom.api.core.Skeleton;

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
	
	private IDocument createDocument1 () {
		IDocument doc = Factory.SI.createDocument();
		doc.setTrgLang("fr");
		ISubDocument sd = doc.addSubDocument();
		sd.setId("f1");
		sd.setOriginal("myOriginalFile");
		// Add notes
		sd.addNote().setText("My note n1").setId("n1").setAppliesTo(AppliesTo.SOURCE);
		sd.addNote().setText("My note n2").setId("n2").setPriority(1);
		
		// Add a unit
		IUnit unit = sd.addUnit();
		unit.setId("u1");
		unit.setName("u1Name");
		ISegment seg = unit.addSegment();
		seg.setId("u1-s1");
		seg.getSource().append("Source for u1-s1.");
		
		// Add a group
		IGroup group = sd.addGroup();
		group.setId("g1");
		// Add a unit in the group
		unit = group.addUnit();
		unit.setId("g1-u1");
		// Add a group to the group
		IGroup group2 = sd.addGroup();
		group2.setId("g2");
		// Add a unit in the nested group
		unit = group2.addUnit();
		unit.setId("g2-u1");
		seg = unit.addSegment();
		seg.setId("g2-u1-s1");
		seg.getSource().append("Source for g2-u1-s1.");
		
		return doc;
	}

	private IDocument createDocument2 () {
		IDocument doc = Factory.SI.createDocument();
		doc.setSrcLang("en-US");
		doc.setSrcLang("ja-JP");
		ISubDocument sd = doc.addSubDocument();
		sd.setId("f1");
		sd.setOriginal("Graphic Example.psd");
		sd.newSkeleton().setRef("Graphic Example.psd.skl");
		
		IUnit unit = sd.addUnit();
		unit.setId("1");
		ISegment seg = unit.addSegment();
		seg.getSource().append("Quetzal");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("Quetzal");
		
		unit = sd.addUnit();
		unit.setId("2");
		seg = unit.addSegment();
		seg.getSource().append("An application to manipulate and process XLIFF documents");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("XLIFF \u6587\u66f8\u3092\u7de8\u96c6\u3001\u307e\u305f\u306f\u51e6\u7406 \u3059\u308b\u30a2\u30d7\u30ea\u30b1\u30fc\u30b7\u30e7\u30f3\u3067\u3059\u3002");
		
		unit = sd.addUnit();
		unit.setId("3");
		seg = unit.addSegment();
		seg.getSource().append("XLIFF Data Manager");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("XLIFF \u30c7\u30fc\u30bf\u30fb\u30de\u30cd\u30fc\u30b8\u30e3");
		return doc;
	}
	
	private IDocument createDocument3 () {
		IDocument doc = Factory.SI.createDocument();
		doc.setSrcLang("en");
		doc.setSrcLang("fr");
		ISubDocument sd = doc.addSubDocument();
		sd.setId("f1");
		
		IUnit unit = sd.addUnit();
		unit.setId("u1");
		ISegment seg = unit.addSegment();
		seg.setCanResegment(false);
		seg.setState(State.TRANSLATED);
		seg.getSource().append("text");
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("TEXT");

		return doc;
	}
	
}
