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

package net.sf.okapi.liom.api;

import java.security.InvalidParameterException;

/**
 * Provides various helper methods for the library.
 */
public class Utils {
	
	/**
	 * Checks if a string is null or empty.
	 * @param string the string to check.
	 * @return true if the given string is null or empty.
	 */
	public static boolean isNoE (String string) {
		return (( string == null ) || string.isEmpty() );
	}

	/**
	 * Checks if a given value is in a given list of allowed values.
	 * @param allowedValues list of allowed values (each value must be separated by a semicolon). 
	 * @param value the value to check.
	 * @param name the name of the object to check (e.g. name of the attribute).
	 * @return true if the value is allowed and not null, false if it's null.
	 * @throws InvalidParameterException if the value is invalid.
	 */
	public static boolean checkValueList (String allowedValues,
		String value,
		String name)
	{
		if ( value == null ) return false; // Allowed but nothing to set
		if ( (";"+allowedValues+";").indexOf(";"+value+";") == -1 ) {
			throw new InvalidParameterException(String.format("The value '%s' is not allowed for '%s'.", value, name));
		}
		return true;
	}

	/**
	 * Compares two strings allowing them to be null.
	 * @param s1 the first string.
	 * @param s2 the second string.
	 * @return 0 if the two string are identical.
	 */
	public static int compareAllowingNull (String s1,
		String s2)
	{
		if ( s1 == null ) {
			if ( s2 == null ) return 0;
			else return -1;
		}
		if ( s2 == null ) {
			return 1;
		}
		return s1.compareTo(s2);
		
	}

}
