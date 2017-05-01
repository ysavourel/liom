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

import org.oasisopen.liom.api.core.ICTag;
import org.oasisopen.liom.api.core.IMTag;
import org.oasisopen.liom.api.core.TagType;

/**
 * Represents an opening or closing tag for an annotation marker.
 */
public class MTag extends Tag implements IMTag {

	/**
	 * Default type of a marker.
	 */
	public static final String TYPE_DEFAULT = "generic"; // Default value for type

	protected MTagCommon mc;

	/**
	 * Creates a new {@link MTag} object with a given id and type.
	 * Use this to create opening tags, the safe method to create closing tags is {@link #MTag(MTag)}.
	 * @param opening true to create a new opening tag, false to create a closing tag.
	 * @param id the ID of the new marker.
	 * @param type the type of the new marker (can be null for the default type).
	 */
	MTag (boolean opening,
		String id,
		String type)
	{
		tagType = (opening ? TagType.OPENING : TagType.CLOSING);
		mc = new MTagCommon(id, type);
	}
	
	/**
	 * Creates a new opening tag for a marker.
	 * @param id the ID of the new marker.
	 * @param type the type of the new marker (can be null for the default type).
	 */
	public MTag (String id,
		String type)
	{
		this(true, id, type);
	}
	
	/**
	 * Creates a new opening or closing tag for a marker.
	 * @param opposite the counterpart tag to connect to this new opening or closing tag.
	 */
	public MTag (MTag opposite) {
		this.mc = opposite.mc;
		if ( opposite.tagType == TagType.OPENING ) tagType = TagType.CLOSING;
		else tagType = TagType.OPENING;
	}

	/**
	 * Copy constructor.
	 * @param original the existing {@link MTag} to duplicate.
	 * @param opposite the opposite tag to allow connecting the new created tag with its counterpart.
	 */
	public MTag (MTag original,
		MTag opposite)
	{
		// Copy the specific fields
		if ( opposite == null ) this.mc = new MTagCommon(original.mc);
		else this.mc = opposite.mc;
	}
	
	@Override
	public String getId () {
		return mc.getId();
	}

	@Override
	public String getType () {
		return mc.getType();
	}

	@Override
	public void setType (String type) {
		mc.setType(type);
	}

	/**
	 * Gets the URI reference for this marker.
	 * @return the URI reference for this marker (can be null).
	 */
	public String getRef () {
		return mc.getRef();
	}
	
	/**
	 * Sets the URI reference for this marker (for both opening/closing tags).
	 * @param ref the new URI reference for this marker (can be null).
	 */
	public void setRef (String ref) {
		mc.setRef(ref);
	}

	/**
	 * Gets the value for this marker.
	 * @return the value for this marker (can be null).
	 */
	public String getValue () {
		return mc.getValue();
	}
	
	/**
	 * sets the value for this marker (for both opening/closing tags).
	 * @param value the new value for this marker.
	 */
	public void setValue (String value) {
		mc.setValue(value);
	}

	/**
	 * Gets the translate property for this marker.
	 * @return the translate property for this marker (can be null).
	 */
	public boolean getTranslate () {
		return mc.getTranslate();
	}

	/**
	 * Sets the translate property for this marker (for both opening/closing tags).
	 * @param translate the new translate property (can be null).
	 */
	public void setTranslate (Boolean translate) {
		mc.setTranslate(translate);
	}

	@Override
	public boolean equals (Tag tag) {
		if ( tag == null ) return false;
		if ( this == tag ) return true;
		if ( !(tag instanceof MTag) ) return false;
		MTag mtag = (MTag)tag;
		if ( tagType.compareTo(mtag.getTagType()) != 0 ) return false;
		return mc.equals(mtag.mc);
	}

	@Override
	public boolean isMarker () {
		return true;
	}

	@Override
	public boolean isCode () {
		return false;
	}

	@Override
	public IMTag asMTag () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICTag asCTag () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId (String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seType (String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate (boolean translate) {
		// TODO Auto-generated method stub
		
	}

}
