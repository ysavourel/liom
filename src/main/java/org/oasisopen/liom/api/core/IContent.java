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

public interface IContent extends Iterable<Object> {

	public boolean isSource ();
	
	public IContent asSource ();
	
	public IContent asTarget ();
	
	public ISubUnit getParent ();

	public IContent set (String plainText);

	/**
	 * Adds a string of plain text at the end of this content.
	 * @param plainText the plain text to append.
	 * @return the content itself.
	 */
	public IContent append (String plainText);

	/**
	 * Adds a character at the end of this content.
	 * @param ch the character to append.
	 * @return the content itself.
	 */
	public IContent append (char ch);

	public boolean isEmpty ();
	
	/**
	 * Deletes a section of this content.
	 * @param start the start index (inclusive)
	 * @param end the end index (exclusive)
	 * @return the content itself.
	 */
	public IContent delete (int start,
		int end);

}
