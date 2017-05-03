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

import org.oasisopen.liom.api.glossary.ITranslation;

public class Translation implements ITranslation {

	private String id;
	private String ref;
	private String text;
	private String source;

	/**
	 * Creates a {@link Translation} object with a given text.
	 * @param text the text of the translation.
	 */
	public Translation (String text) {
		setText(text);
	}
	
	/**
	 * Gets the id for this translation.
	 * @return the id for this translation (can be null).
	 */
	public String getId () {
		return id;
	}

	public ITranslation setId (String id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the reference for this translation.
	 * @return the reference for this translation (can be null).
	 */
	public String getRef () {
		return ref;
	}

	public ITranslation setRef (String ref) {
		this.ref = ref;
		return this;
	}

	@Override
	public String getText () {
		return text;
	}

	@Override
	public ITranslation setText (String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getSource () {
		return source;
	}

	@Override
	public ITranslation setSource (String source) {
		this.source = source;
		return this;
	}
	
}
