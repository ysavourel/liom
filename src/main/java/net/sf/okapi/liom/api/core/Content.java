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

import java.util.ArrayList;
import java.util.Iterator;

import org.oasisopen.liom.api.core.IContent;
import org.oasisopen.liom.api.core.ISource;
import org.oasisopen.liom.api.core.ITarget;

public class Content implements IContent {

	final private boolean isSource;
	
	private String lang;
	private boolean preserveWS;
	private StringBuilder ctext = new StringBuilder();

	public Content (boolean isSource) {
		this.isSource = isSource;
	}
	
	@Override
	public boolean isSource () {
		return isSource;
	}

	@Override
	public ISource asSource () {
		if ( isSource ) return (ISource)this;
		else return null;
	}

	@Override
	public ITarget asTarget () {
		if ( !isSource ) return (ITarget)this;
		else return null;
	}

	@Override
	public String getLang () {
		return lang;
	}

	@Override
	public IContent setLang (String lang) {
		this.lang = lang;
		return this;
	}

	@Override
	public boolean getPreserveWS () {
		return preserveWS;
	}

	@Override
	public IContent setPreserveWS (boolean preserveWS) {
		this.preserveWS = preserveWS;
		return this;
	}

	public String toString () {
		return ctext.toString();
	}

	@Override
	public IContent append (String plainText) {
		ctext.append(plainText);
		return this;
	}

	@Override
	public IContent append (char ch) {
		ctext.append(ch);
		return this;
	}

	@Override
	public Iterator<Object> iterator () {
		//Temporary 
		Iterator<Object> iter = new Iterator<Object>() {
			private int count = 0;
			private String text = ctext.toString();
			@Override
			public Object next () {
				if ( count == 1 ) return text;
				return null;
			}
			@Override
			public boolean hasNext () {
				return ((++count)==1); 
			}
		};
		return iter;
	}

}
