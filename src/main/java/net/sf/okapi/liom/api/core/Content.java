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

import java.util.Iterator;

import org.oasisopen.liom.api.core.IContent;
import org.oasisopen.liom.api.core.ISubUnit;

public class Content implements IContent {

	final private boolean isSource;

	private ISubUnit parent;
	
	protected StringBuilder ctext = new StringBuilder();

	public Content (ISubUnit parent,
		boolean isSource)
	{
		this.parent = parent;
		this.isSource = isSource;
	}
	
	/**
	 * Creates a new {@link Target} object and copy a given content in it.
	 * @param parent the parent {@link ISubUnit} for this content.
	 * @param isSource true if the content to create is for the source, false if it is for the target. 
	 * @param from the object to copy the content from.
	 */
	public Content (ISubUnit parent,
		boolean isSource,
		Content from)
	{
		this(parent, isSource);
		this.ctext = new StringBuilder(from.ctext);
	}

	@Override
	public boolean isSource () {
		return isSource;
	}

	@Override
	public IContent asSource () {
		if ( isSource ) return this;
		else return null;
	}

	@Override
	public IContent asTarget () {
		if ( !isSource ) return this;
		else return null;
	}

	@Override
	public ISubUnit getParent () {
		return parent;
	}

	@Override
	public String getLang () {
		if ( isSource ) return parent.getSrcLang();
		else return parent.getTrgLang();
	}

	@Override
	public IContent setLang (String lang) {
		if ( isSource ) parent.setSrcLang(lang);
		else parent.setTrgLang(lang);
		return this;
	}

	@Override
	public boolean getPreserveWS () {
		return parent.getPreserveWS();
	}

	@Override
	public IContent setPreserveWS (boolean preserveWS) {
		parent.setPreserveWS(preserveWS);
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

	@Override
	public boolean isEmpty () {
		return (ctext.length()==0);
	}

	@Override
	public IContent delete (int start, int end) {
		ctext.delete(start, end);
		return this;
	}

}
