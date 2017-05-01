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

import org.oasisopen.liom.api.core.ITag;
import org.oasisopen.liom.api.core.TagType;

/**
 * Provides the common methods for code and marker tags.
 * <p>Any object deriving from this class must also provide an implementation 
 * for {@link CloneFactory#create(Tag, Tags)}. 
 * @see CTag
 * @see MTag
 */
abstract public class Tag implements ITag {
	
	protected TagType tagType;

	/**
	 * Creates an empty {@link Tag} object.
	 */
	protected Tag () {
		// Nothing to do
	}
	
	@Override
	public String toString () {
		return tagType+getId();
	}
	
	/**
	 * Gets the {@link TagType} value of this tag.
	 * @return the {@link TagType} value of this tag.
	 */
	public TagType getTagType () {
		return tagType;
	}

//	/**
//	 * Sets the {@link TagType} value of this tag.
//	 * @param tagType the {@link TagType} to set.
//	 */
//	public void setTagType (TagType tagType) {
//		this.tagType = tagType;
//	}
	
	/**
	 * Gets the id for the code or annotation using this tag.
	 * @return the id for the code or annotation using this tag.
	 */
	abstract public String getId ();
	
	/**
	 * Gets the type of the code or marker this tag represents.
	 * @return the type of the code or marker this tag represents.
	 * If this is a {@link CTag} the type value can be null.
	 */
	abstract public String getType ();
	
	/**
	 * Sets the type of the code or marker (for both opening/closing tags).
	 * @param type the type of the code or marker this tag represents.
	 */
	abstract public void setType (String type);

	/**
	 * Indicates if this tag is equal to another.
	 * <p>Use the <code>==</code> operator to test if two tags are the same.
	 * @param tag the other tag to compare to this one.
	 * @return true if both tags are equals.
	 */
	abstract boolean equals (Tag tag);

	/**
	 * Indicates if this tag is for a marker ({@link MTag}).
	 * @return true if this tag is one for a marker ({@link MTag}), false if it is for a code ({@link CTag}).
	 */
	abstract public boolean isMarker ();

	/**
	 * Indicates if this tag is for a code ({@link CTag}).
	 * @return true if this tag is used by a code ({@link CTag}), false if it is for a marker ({@link MTag}).
	 */
	abstract public boolean isCode ();

}
