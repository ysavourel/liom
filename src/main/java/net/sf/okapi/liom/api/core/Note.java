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

import org.oasisopen.liom.api.core.AppliesTo;
import org.oasisopen.liom.api.core.INCFields;
import org.oasisopen.liom.api.core.INote;
import org.oasisopen.liom.api.core.IWithNotes;

public class Note implements INote {

	private transient IWithNotes parent;

	private String id;
	private AppliesTo appliesTo = AppliesTo.UNDEFINED;
	private String category;
	private int priority = 1;
	private INCFields ncFlds;
	private String text;
	
	public Note (IWithNotes parent) {
		this.parent = parent;
	}

	@Override
	public String getText () {
		return text;
	}

	@Override
	public INote setText (String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getId () {
		return id;
	}

	@Override
	public INote setId (String id) {
		this.id = id;
		return this;
	}

	@Override
	public AppliesTo getAppliesTo () {
		return appliesTo;
	}

	@Override
	public INote setAppliesTo (AppliesTo appliesTo) {
		this.appliesTo = appliesTo;
		return this;
	}

	@Override
	public String getCategory () {
		return category;
	}

	@Override
	public INote setCategory (String category) {
		this.category = category;
		return this;
	}

	@Override
	public int getPriority () {
		return priority;
	}

	@Override
	public INote setPriority (int priority) {
		this.priority = priority;
		return this;
	}

	@Override
	public IWithNotes getParent () {
		return parent;
	}

	@Override
	public boolean hasNCField (String name) {
		if ( ncFlds != null ) {
			return ncFlds.has(name);
		}
		return false;
	}

	@Override
	public INCFields getNCFields () {
		if ( ncFlds == null ) {
			ncFlds = new NCFields();
		}
		return ncFlds;
	}

}
