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

public enum IfNoTarget {
	/**
	 * Indicates to not create a target if a target does not exists yet: the target remains null.
	 */
	DONT_CREATE,
	/**
	 * Indicates to create an empty target if a target does not exist yet.
	 */
	CREATE_EMPTY,
	/**
	 * Indicates to clone the source in the target if a target does not exist yet.
	 */
	CLONE_SOURCE
}

