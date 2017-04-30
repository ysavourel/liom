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

import org.oasisopen.liom.api.core.ISkeleton;

public class Skeleton implements ISkeleton {

	private String href;
	private String text;
	
	@Override
	public String getRef () {
		return href;
	}

	@Override
	public ISkeleton setRef (String href) {
		this.href = href;
		return this;
	}

	@Override
	public String getText () {
		return text;
	}

	@Override
	public ISkeleton setText (String text) {
		this.text = text;
		return this;
	}

}
