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

package net.sf.okapi.liom.api.glossary;

import org.oasisopen.liom.api.glossary.IDefinition;

public class Definition implements IDefinition {

	private String text;
	private String source;

	/**
	 * Creates a {@link Definition} object with a given text.
	 * @param text the text of the definition.
	 */
	public Definition (String text) {
		setText(text);
	}

	@Override
	public String getText () {
		return text;
	}

	@Override
	public IDefinition setText (String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getSource () {
		return source;
	}

	@Override
	public IDefinition setSource (String source) {
		this.source = source;
		return this;
	}
	
}
