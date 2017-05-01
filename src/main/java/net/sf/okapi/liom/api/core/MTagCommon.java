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

import java.security.InvalidParameterException;

import net.sf.okapi.liom.api.Utils;

/**
 * Represents common data for the opening and closing {@link MTag}.
 */
class MTagCommon {

	private String id;
	private String type;
	private String value;
	private String ref;
	private Boolean translate;

	public MTagCommon (String id,
		String type)
	{
		setId(id);
		setType(type);
	}
	
	/**
	 * Copy constructor.
	 * @param original the existing {@link MTagCommon} to duplicate.
	 */
	public MTagCommon (MTagCommon original) {
		// Copy the AMarker-specific fields
		id = original.id;
		type = original.type;
		value = original.value;
		ref = original.ref;
		translate = original.translate;
	}
	
	public String getId () {
		return id;
	}

	public void setId (String id) {
		if ( id == null ) {
			throw new InvalidParameterException("ID cannot be null.");
		}
		this.id = id;
	}

	public void setType (String type) {
		if ( type == null ) {
			type = MTag.TYPE_DEFAULT; // Use the default
		}
		else {
			// Is it one of the standard values?
			if ( ";generic;comment;term;".indexOf(";"+type+";") == -1 ) {
				// If not: check the pattern "prefix:value"
				int n = type.indexOf(':');
				if (( n == -1 ) || ( n == 0 ) || ( n == type.length()-1 )) {
					throw new InvalidParameterException(String.format("Invalid value '%s' for an annotation type.", type));
				}
			}
		}
		this.type = type;
	}

	public String getRef () {
		return ref;
	}
	
	public void setRef (String ref) {
		this.ref = ref;
	}

	public String getValue () {
		return value;
	}
	
	public void setValue (String value) {
		this.value = value;
	}

	public Boolean getTranslate () {
		return translate;
	}

	public void setTranslate (Boolean translate) {
		this.translate = translate;
	}

	/**
	 * Indicates if this object is equals to a given one.
	 * @param other the other object to compare.
	 * @return true if the two objects are identical.
	 */
	public boolean equals (MTagCommon other) {
		if ( other == null ) return false;
		if ( this == other ) return true;
		if ( Utils.compareAllowingNull(type, other.getType()) != 0 ) return false;
		if ( Utils.compareAllowingNull(id, other.getId()) != 0 ) return false;
		if ( Utils.compareAllowingNull(ref, other.getRef()) != 0 ) return false;
		if ( Utils.compareAllowingNull(value, other.getValue()) != 0 ) return false;
		// Note that translate can be null, and it's OK
		if ( translate != other.getTranslate() ) return false;
		//TODO: compare the ITS items
		return true;
	}

	public String getType () {
		return type;
	}

}
