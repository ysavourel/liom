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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oasisopen.liom.api.core.CanReorder;
import org.oasisopen.liom.api.core.Directionality;
import org.oasisopen.liom.api.core.TagType;

import net.sf.okapi.liom.api.InvalidParameterException;

public class CTagTests {

	@Test
	public void testSimple () {
		CTag code = new CTag(null, TagType.OPENING, "1", null);
		assertEquals(TagType.OPENING, code.getTagType());
		assertEquals("1", code.getId());
		assertNull(code.getData());
	}

	@Test
	public void testOriginalData () {
		CTag code = new CTag(null, TagType.STANDALONE, "1", null);
		assertEquals(null, code.getData());
		assertFalse(code.hasData());
		code = new CTag(null, TagType.STANDALONE, "1", "");
		assertEquals("", code.getData());
		assertFalse(code.hasData());
		code = new CTag(null, TagType.STANDALONE, "1", "z");
		assertEquals("z", code.getData());
		assertTrue(code.hasData());
	}
	
	@Test
	public void testHintsDefaults () {
		CTag code = new CTag(null, TagType.STANDALONE, "1", null);
		assertTrue(code.getCanDelete());
		assertTrue(code.getCanCopy());
		assertEquals(CanReorder.YES, code.getCanReorder());
	}
	
	@Test
	public void testHintsCanDelete () {
		CTag code = new CTag(null, TagType.STANDALONE, "1", null);
		code.setCanDelete(false);
		assertFalse(code.getCanDelete());
		assertTrue(code.getCanCopy());
		assertEquals(CanReorder.YES, code.getCanReorder());
		code.setCanDelete(true);
		assertTrue(code.getCanDelete());
		assertTrue(code.getCanCopy());
		assertEquals(CanReorder.YES, code.getCanReorder());
	}
	
	@Test
	public void testHintsCanReplicate () {
		CTag code = new CTag(null, TagType.STANDALONE, "1", null);
		code.setCanCopy(false);
		assertTrue(code.getCanDelete());
		assertFalse(code.getCanCopy());
		assertEquals(CanReorder.YES, code.getCanReorder());
		code.setCanCopy(true);
		assertTrue(code.getCanDelete());
		assertTrue(code.getCanCopy());
		assertEquals(CanReorder.YES, code.getCanReorder());
	}
	
	@Test
	public void testHintsCanReorder () {
		CTag code = new CTag(null, TagType.STANDALONE, "1", null);
		assertTrue(code.getCanDelete());
		assertTrue(code.getCanCopy());
		code.setCanReorder(CanReorder.FIRSTNO);
		// Note that canDelete and canCopy are reset automatically based on canReorder in the library
		assertFalse(code.getCanDelete());
		assertFalse(code.getCanCopy());
		assertEquals(CanReorder.FIRSTNO, code.getCanReorder());
		code.setCanReorder(CanReorder.NO);
		assertFalse(code.getCanDelete());
		assertFalse(code.getCanCopy());
		assertEquals(CanReorder.NO, code.getCanReorder());
	}
	
	@Test
	public void testOtherDefaults () {
		CTag code = new CTag(null, TagType.STANDALONE, "1", null);
		assertEquals("", code.getEquiv());
		assertEquals(null, code.getCopyOf());
		assertEquals(Directionality.AUTO, code.getDataDir());
		assertEquals(null, code.getDataRef());
		assertEquals(null, code.getDisp());
	}

	@Test
	public void testEquals () {
		assertTrue(new CTag(TagType.STANDALONE, "1", null).equals(
			new CTag(TagType.STANDALONE, "1", null)));
		assertFalse(new CTag(TagType.STANDALONE, "1", null).equals(
			new CTag(TagType.STANDALONE, "2", null)));
		assertFalse(new CTag(TagType.STANDALONE, "1", null).equals(
			new CTag(TagType.OPENING, "1", null)));
		assertFalse(new CTag(TagType.STANDALONE, "1", null).equals(
			new CTag(TagType.STANDALONE, "1", "data")));
		
		CTag code1 = new CTag(TagType.STANDALONE, "1", "d1");
		code1.setDisp("di1");
		code1.setEquiv("eq1");
		code1.setSubFlows("sf1");
		code1.setCanDelete(false);
		code1.setType("ui");

		CTag code2 = new CTag(TagType.STANDALONE, "1", "d1");
		code2.setDisp("di1");
		code2.setEquiv("eq1");
		code2.setSubFlows("sf1");
		code2.setCanDelete(false);
		code2.setType("ui");
		
		assertTrue(code1.equals(code2));
		
		code2.setType("fmt");
		assertFalse(code1.equals(code2));
		code2.setType("ui");
		assertTrue(code1.equals(code2));
		
		code2.setEquiv("eq2");
		assertFalse(code1.equals(code2));
		code2.setEquiv("eq1");
		assertTrue(code1.equals(code2));
		
		code2.setDisp("di2");
		assertFalse(code1.equals(code2));
		code2.setDisp("di1");
		assertTrue(code1.equals(code2));
		
		code2.setSubFlows("sf2");
		assertFalse(code1.equals(code2));
		code2.setSubFlows("sf1");
		assertTrue(code1.equals(code2));
		
		code2.setCanCopy(false);
		assertFalse(code1.equals(code2));
		code2.setCanCopy(true);
		assertTrue(code1.equals(code2));
	}
	
	@Test
	public void testSubFlows () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setSubFlows(" 1 \t 2\t3  4\n5\t");
		assertEquals("1 2 3 4 5", cm.getSubFlows());
		int i = 1;
		for ( String id : cm.getSubFlowsIds() ) {
			assertEquals(""+i, id);
			i++;
		}
		cm.setSubFlows(null);
		assertEquals(null, cm.getSubFlows());
		assertEquals(0, cm.getSubFlowsIds().length);
	}
	
	@Test
	public void testTypes () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		assertEquals("fmt", cm.getType());
		cm.setType("ui");
		assertEquals("ui", cm.getType());
		cm.setType("quote");
		assertEquals("quote", cm.getType());
		cm.setType("link");
		assertEquals("link", cm.getType());
		cm.setType("image");
		assertEquals("image", cm.getType());
		cm.setType("other");
		assertEquals("other", cm.getType());
	}
	
	@Test
	public void testTypeAndSubType () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType("scPrefix:scValue");
		assertEquals("fmt", cm.getType());
		assertEquals("scPrefix:scValue", cm.getSubType());
	}

	@Test (expected=InvalidParameterException.class)
	public void testBadSubTypeValues1 () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType(":value");
	}
	
	@Test (expected=InvalidParameterException.class)
	public void testBadSubTypeValues2 () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType("my:");
	}
	
	@Test (expected=InvalidParameterException.class)
	public void testBadSubTypeValues3 () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType(":");
	}
	
	@Test (expected=InvalidParameterException.class)
	public void testBadSubTypeValues4 () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType("");
	}
	
	@Test (expected=InvalidParameterException.class)
	public void testBadSubTypeValues5 () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType("xlf:_badvalue_");
	}
	
	@Test (expected=InvalidParameterException.class)
	public void testBadTypeValue () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("fmt");
		cm.setSubType("xlf:var");
		cm.verifyTypeSubTypeValues();
	}
	
	public void testTypeSubTypeValues () {
		CTag cm = new CTag(TagType.STANDALONE, "1", null);
		cm.setType("ui");
		cm.setSubType("xlf:var"); cm.verifyTypeSubTypeValues();
		cm.setType("fmt");
		cm.setSubType("xlf:lb"); cm.verifyTypeSubTypeValues();
		cm.setSubType("xlf:pb"); cm.verifyTypeSubTypeValues();
		cm.setSubType("xlf:b"); cm.verifyTypeSubTypeValues();
		cm.setSubType("xlf:i"); cm.verifyTypeSubTypeValues();
		cm.setSubType("xlf:u"); cm.verifyTypeSubTypeValues();
		cm.setSubType("abc:xyz"); cm.verifyTypeSubTypeValues();
		cm.setType("fmt"); cm.verifyTypeSubTypeValues();
	}
	
	@Test
	public void testCopyConstructor () {
		CTag cm1 = new CTag(TagType.STANDALONE, "1", "data");
		cm1.setCanCopy(false);
		cm1.setCanDelete(false);
		cm1.setCanOverlap(true);
		cm1.setCanReorder(CanReorder.NO);
		cm1.setDisp("disp");
		cm1.setEquiv("equiv");
		cm1.setDataRef("dataRef");
		cm1.setSubFlows("sf1");
		
		// Create a deep clone and compare
		CTag cm2 = new CTag(cm1, (CTag)null);
		
		assertTrue(cm2.equals(cm1));
		assertFalse(cm2==cm1);
	}

}
