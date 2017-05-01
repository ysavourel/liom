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

import org.oasisopen.liom.api.core.ITarget;

public class Target extends Content implements ITarget {

	private int order;
	
	/**
	 * Creates a new {@link Target} object.
	 */
	public Target () {
		super(false);
	}
	
	/**
	 * Creates a new {@link Target} object and copy a given content in it.
	 * @param from the object to copy the content from.
	 */
	public Target (Content from) {
		super(false);
		this.ctext = new StringBuilder(from.ctext);
	}

	@Override
	public int getOrder () {
		return order;
	}

	@Override
	public void setOrder (int order) {
		this.order = order;
	}

}
