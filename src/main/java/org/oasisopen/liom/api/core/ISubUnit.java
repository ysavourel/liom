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

package org.oasisopen.liom.api.core;

/**
 * Represents a {@link ISegment} or a {@link IIgnorable} object.
 */
public interface ISubUnit {

	/**
	 * Indicates if this object is a {@link ISegment}.
	 * @return true if this object is a {@link ISegment}, false otherwise.
	 */
	public boolean isSegment ();
	
	/**
	 * Gets this object as a {@link ISegment}.
	 * @return this object as a {@link ISegment}.
	 */
	public ISegment asSegment ();
	
	public IIgnorable asIgnorable ();
	
	public IUnit getParent ();
	
	public String getId ();
	
	public void setId (String id);

	public IContent getSource ();
	
	public IContent getTarget ();

	/**
	 * Indicates if this sub-unit has a target or not.
	 * @return true if this sub-unit has a target, false if it does not.
	 * @see isTargetEmpty
	 */
	public boolean hasTarget ();

	public boolean isSourceEmpty ();
	
	/**
	 * Indicates if the target of this sub-unit is empty or has at least one character or one tag. 
	 * @return true if the target of this sub-unit is empty (no text and no tags), false otherwise.
	 */
	public boolean isTargetEmpty ();
	
	public IContent getTarget (IfNoTarget ifNoTarget);

	public String getSrcLang ();
	
	public ISubUnit setSrcLang (String srcLang);

	public String getTrgLang ();
	
	public ISubUnit setTrgLang (String trgLang);
	
	public Boolean getPreserveWS ();
	
	public ISubUnit setPreserveWS (Boolean preserveWS);

	public int getTrgOrder ();

	public ISubUnit setTrgOrder (int trgOrder);

}
