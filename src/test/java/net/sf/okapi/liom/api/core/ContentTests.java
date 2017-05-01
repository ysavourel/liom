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

import static org.junit.Assert.*;

import org.junit.Test;
import org.oasisopen.liom.api.core.IContent;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IfNoTarget;

import net.sf.okapi.liom.api.core.Unit;

public class ContentTests {

	@Test
	public void testSimple () {
		IUnit unit = new Unit(null);
		ISegment seg1 = unit.addSegment();
		IContent src1 = seg1.getSource();
		src1.append("text1");
		assertTrue(seg1==src1.getParent());
		assertEquals("text1", src1.toString());

		ISegment seg2 = unit.addSegment();
		IContent src2 = seg2.getSource();
		src2.append("text2");
		IContent trg2 = seg2.getTarget(IfNoTarget.CLONE_SOURCE);
		assertTrue(seg2==trg2.getParent());
		assertEquals("text2", src2.toString());
		assertEquals("text2", trg2.toString());
	}

}
