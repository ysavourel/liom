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
 * Represents a {@link IUnit} or a {@link IGroup} object.
 */
public interface IGroupOrUnit extends IWithContext, IWithNCObjects, IWithNCFields, IWithNotes  {

	/**
	 * Indicates if this object is a {@link IUnit}.
	 * @return true if this object is a {@link IUnit}, false otherwise.
	 */
	public boolean isUnit ();
	
	/**
	 * Gets this object as a {@link IUnit}.
	 * @return this object as a {@link IUnit}.
	 */
	public IUnit asUnit ();
	
	public IGroup asGroup ();
	
	public String getId ();
	
	public void setId (String id);
	
	public String getName ();
	
	public void setName (String name);
	
	public String getType ();
	
	public void setType (String type);
	
	public IWithGroupOrUnit getParent ();

}
