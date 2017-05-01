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
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oasisopen.liom.api.core.Directionality;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IfNoTarget;

import net.sf.okapi.liom.api.core.Unit;

public class UnitTests {

	@Test
	public void testSimple () {
		IUnit unit = new Unit(null);
		assertTrue(unit.isSourceEmpty());
		assertTrue(unit.isTargetEmpty());
		
		unit.setId("id");
		assertEquals("id", unit.getId());
		unit.setName("name");
		assertEquals("name", unit.getName());

		// Defaults
		assertTrue(unit.getCanResegment());
		assertFalse(unit.getPreserveWS());
		assertTrue(unit.getTranslate());
		assertEquals(Directionality.INHERITED, unit.getSrcDir());
		assertEquals(Directionality.INHERITED, unit.getTrgDir());
		
		ISegment seg = unit.addSegment();
		seg.getSource().append("");
		assertTrue(unit.isSourceEmpty());
		seg.getTarget(IfNoTarget.CREATE_EMPTY).append("");
		assertTrue(unit.isTargetEmpty());
		
		seg = unit.addSegment();
		seg.getSource().append("B");
		assertFalse(unit.isSourceEmpty());
		assertTrue(unit.isTargetEmpty());
		seg.getTarget(IfNoTarget.CLONE_SOURCE);
		assertFalse(unit.isTargetEmpty());
	}

}
