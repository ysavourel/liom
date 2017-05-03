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

import org.oasisopen.liom.api.core.INCFields;
import org.oasisopen.liom.api.glossary.IDefinition;
import org.oasisopen.liom.api.glossary.IGlossEntry;
import org.oasisopen.liom.api.glossary.ITerm;
import org.oasisopen.liom.api.glossary.ITranslation;

import net.sf.okapi.liom.api.core.BaseCollection;
import net.sf.okapi.liom.api.core.NCFields;

public class GlossEntry extends BaseCollection<ITranslation> implements IGlossEntry {

	private NCFields ncFlds;
	private String id;
	private String ref;
	private Term term;
	private Definition definition;
	
	/**
	 * Gets the id for this entry.
	 * @return the id for this entry (can be null).
	 */
	@Override
	public String getId () {
		return id;
	}

	@Override
	public IGlossEntry setId (String id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the reference for this entry.
	 * @return the reference for this entry (can be null).
	 */
	@Override
	public String getRef () {
		return ref;
	}

	@Override
	public IGlossEntry setRef (String ref) {
		this.ref = ref;
		return this;
	}

	@Override
	public ITerm getTerm () {
		return term;
	}

	/**
	 * Gets the definition for this entry.
	 * @return the definition for this entry (can be null).
	 */
	@Override
	public IDefinition getDefinition () {
		return definition;
	}

	/**
	 * Adds a translation to this entry.
	 * @param text the text of the translation to add.
	 */
	@Override
	public ITranslation addTranslation (String text) {
		ITranslation trans = new Translation(text);
		this.list.add(trans);
		return trans;
	}

	@Override
	public ITerm newTerm (String text) {
		term = new Term(text);
		return term;
	}

	@Override
	public IDefinition newDefinition (String text) {
		definition = new Definition(text);
		return definition;
	}

	@Override
	public boolean hasNCField (String name) {
		return (( ncFlds != null ) && ncFlds.has(name) );
	}

	@Override
	public INCFields getNCFields () {
		if ( ncFlds == null ) ncFlds = new NCFields();
		return ncFlds;
	}

	@Override
	public boolean hasNCField () {
		return (( ncFlds != null ) && !ncFlds.isEmpty() );
	};

}
