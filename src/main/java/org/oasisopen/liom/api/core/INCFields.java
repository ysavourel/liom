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
 * Provides access to a set of non-core fields.
 */
public interface INCFields extends Iterable<String> {

	/**
	 * Indicates if a field exists.
	 * @param name the name of the field.
	 * @return true if the field was found, false otherwise.
	 */
	public boolean has (String name);
	
	/**
	 * Gets the value for a given field.
	 * @param name the name of the field.
	 * @return the value, or null if not found.
	 */
	public String get (String name);
	
	/**
	 * Sets the value for a given field.
	 * @param name the name of the field to set.
	 * @param value the value to set. If this field is already set, its old value is discarded.
	 */
	public void set (String name, String value);

	/**
	 * Removes a given field.
	 * If the field is not found nothing happens.
	 * @param name the name of the field to remove.
	 */
	public void remove (String name);

	public boolean isEmpty ();
	
}
