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

import org.oasisopen.liom.api.core.Directionality;

import net.sf.okapi.liom.api.InvalidParameterException;
import net.sf.okapi.liom.api.Utils;

/**
 * Represents common data for the opening and closing {@link CTag}.
 */
class CTagCommon {

	private static final int CANCOPY = 0x01;
	private static final int CANDELETE = 0x02;

	private String id;
	private String type;
	private int hints = (CANCOPY | CANDELETE);
	private boolean canOverlap;
	private String subType;
	private String copyOf;
	private Directionality dir = Directionality.INHERITED;
	
	CTagCommon (String id) {
		if ( id == null ) {
			throw new InvalidParameterException("The is parameter cannot be null.");
		}
		this.id = id;
	}

	/**
	 * Copy constructor.
	 * @param original the original object to copy.
	 */
	CTagCommon (CTagCommon original) {
		this.canOverlap = original.canOverlap;
		this.copyOf = original.copyOf;
		this.dir = original.dir;
		this.hints = original.hints;
		this.id = original.id;
		this.subType = original.subType;
		this.type = original.type;
	}
	
	public String getId () {
		return id;
	}

	public void setId (String id) {
		this.id = id;
	}

	public String getType () {
		return type;
	}

	public void setType (String type) {
		Utils.checkValueList("fmt;ui;quote;link;image;other", type, "type");
		// Allows null value
		this.type = type;
	}

	public String getSubType () {
		return subType;
	}

	public void setSubType (String subType) {
		if ( subType != null ) {
			int n = subType.indexOf(':');
			if (( n == -1 ) || ( n == 0 ) || ( n == subType.length()-1 )) {
				throw new InvalidParameterException(String.format("Invalid value '%s' for subType.", subType));
			}
			if ( subType.startsWith("xlf:") ) {
				Utils.checkValueList("xlf:lb;xlf:pb;xlf:b;xlf:i;xlf:u;xlf:var", subType, "subType");
			}
		}
		this.subType = subType;
	}

	public boolean getCanCopy () {
		return (( hints & CANCOPY ) == CANCOPY);
	}

	public void setCanCopy (boolean canCopy) {
		if ( canCopy ) hints |= CANCOPY;
		else hints &= ~CANCOPY;
	}

	public boolean getCanDelete () {
		return (( hints & CANDELETE ) == CANDELETE);
	}

	public void setCanDelete (boolean canDelete) {
		if ( canDelete ) hints |= CANDELETE;
		else hints &= ~CANDELETE;
	}

	public boolean getCanOverlap () {
		return canOverlap;
	}

	public void setCanOverlap (boolean canOverlap) {
		this.canOverlap = canOverlap;
	}

	public String getCopyOf () {
		return copyOf;
	}

	public void setCopyOf (String copyOf) {
		this.copyOf = copyOf;
	}

	public Directionality getDir () {
		return dir;
	}

	public void setDir (Directionality dir) {
		this.dir = dir;
	}

	public boolean equals (CTagCommon other) {
		if ( other == null ) return false;
		if ( this == other) return true;
		if ( Utils.compareAllowingNull(getId(), other.getId()) != 0 ) return false;
		if ( Utils.compareAllowingNull(getType(), other.getType()) != 0 ) return false;
		if ( Utils.compareAllowingNull(getSubType(), other.getSubType()) != 0 ) return false;
		if ( Utils.compareAllowingNull(getCopyOf(), other.getCopyOf()) != 0 ) return false;
		if ( getDir() != other.getDir() ) return false;
		if ( getCanCopy() != other.getCanCopy() ) return false;
		if ( getCanDelete() != other.getCanDelete() ) return false;
		if ( getCanOverlap() != other.getCanOverlap() ) return false;
		return true;
	}

}
