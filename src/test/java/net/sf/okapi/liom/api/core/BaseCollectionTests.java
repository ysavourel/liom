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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.sf.okapi.liom.api.core.BaseCollection;

public class BaseCollectionTests {

	@Test
	public void testMethods () {
		BaseCollection<String> bc = new BaseCollection<>();
		
		// Test initial collection
		assertTrue(bc.isEmpty());
		assertEquals(0, bc.size());
		
		// Add an item
		bc.add("item1");
		assertFalse(bc.isEmpty());
		assertEquals(1, bc.size());
		
		// Access
		String item1 = bc.get(0);
		assertEquals("item1", item1);
		
		// Deletion
		bc.remove(item1);
		assertTrue(bc.isEmpty());
		
		// Clear
		bc.add("itemA");
		bc.add("itemB");
		assertEquals(2, bc.size());
		bc.clear();
		assertEquals(0, bc.size());
		assertTrue(bc.isEmpty());
	}

}
