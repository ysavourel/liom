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

import org.oasisopen.liom.api.core.IIgnorable;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithGroupOrUnit;
import org.oasisopen.liom.api.glossary.IGlossary;

import net.sf.okapi.liom.api.glossary.Glossary;

public class Unit extends ImplData3<ISubUnit> implements IUnit {

	final private Store store;
	
	private Glossary glossary;
	
	public Unit (IWithGroupOrUnit parent,
		String id)
	{
		super(true, parent, id);
		store = new Store(this);
	}

	@Override
	public ISegment addSegment () {
		ISegment item = new Segment(this);
		list.add(item);
		return item;
	}

	@Override
	public IIgnorable addIgnorable () {
		IIgnorable item = new Ignorable(this);
		list.add(item);
		return item;
	}

	@Override
	public boolean isSourceEmpty () {
		for ( int i=0; i<size(); i++ ) {
			if ( !get(i).isSourceEmpty() ) return false;
		}
		return true;
	}

	@Override
	public boolean isTargetEmpty () {
		for ( int i=0; i<size(); i++ ) {
			if ( !get(i).isTargetEmpty() ) return false;
		}
		return true;
	}

	public Store getStore () {
		return store;
	}

	public boolean isIdUsed (String id) {
		return (getObjectFromId(id) != null);
	}
	
	/**
	 * Gets the object associated with a given span-class id in this unit.
	 * <p>The objects checked are: the parts (including segments) and all the 
	 * tags except the {@link PCont} objects.
	 * @param id the id to look for.
	 * @return the object found, or null if not found.
	 */
	public Object getObjectFromId (String id) {
		// Check the sub-units
		for ( int i=0; i<size(); i++ ) {
			// The part's id can be null: equals should support that
			ISubUnit su = get(i);
			if ( id.equals(su.getId()) ) return su;
		}
		// Check the tags
		return store.getTag(id);
	}

	@Override
	public boolean hasGlossary () {
		return (( glossary != null ) && !glossary.isEmpty() );
	}

	@Override
	public IGlossary getGlossary () {
		if ( glossary == null ) {
			glossary = new Glossary();
		}
		return glossary;
	}

}
