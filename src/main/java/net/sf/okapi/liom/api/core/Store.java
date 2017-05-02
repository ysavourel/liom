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

import net.sf.okapi.liom.api.InvalidParameterException;

public class Store {

	private final Unit parent;

	private Tags srcTags;
	private Tags trgTags;
	private int lastSuggested;
	private int lastSegSuggested;

	/**
	 * Creates a new store and associates it to a given parent object (e.g. a {@link Unit}).
	 * @param parent the parent object to associate this store with (cannot be null).
	 */
	public Store (Unit parent) {
		if ( parent == null ) {
			throw new InvalidParameterException("Parent parameter must not be null.");
		}
		this.parent = parent;
	}

	/**
	 * Gets the source tags for this store.
	 * @return the source tags for this store (can be empty but never null).
	 */
	public Tags getSourceTags () {
		if ( srcTags == null ) srcTags = new Tags(this);
		return srcTags;
	}
	
	/**
	 * Gets the target tags for this store.
	 * @return the target tags for this store (can be empty but never null).
	 */
	public Tags getTargetTags () {
		if ( trgTags == null ) trgTags = new Tags(this);
		return trgTags;
	}

	/**
	 * Gets a suggested id for code, annotation, ignorable or segment.
	 * @param forSegment true for a segment id, false for other elements.
	 * @return a suggested id which does not exist in this unit.
	 */
	public String suggestId (boolean forSegment) {
		String id;
		while ( true ) {
			if ( forSegment ) {
				id = "s"+String.valueOf(++lastSegSuggested);
			}
			else {
				id = String.valueOf(++lastSuggested);
			}
			if ( !parent.isIdUsed(id) ) return id;
			// Else: try another one
		}
	}

	/**
	 * Gets the tag object for a given id.
	 * <p>This method does not look for {@link PCont}.
	 * @param id the id to look for.
	 * @return the {@link Tag} object for the given id, or null if not found.
	 */
	public Tag getTag (String id) {
		if ( srcTags != null ) {
			for ( Tag tag : srcTags ) {
				if ( id.equals(tag.getId()) ) return tag;
			}
		}
		if ( trgTags != null ) {
			for ( Tag tag : trgTags ) {
				if ( id.equals(tag.getId()) ) return tag;
			}
		}
		return null;
	}

}
