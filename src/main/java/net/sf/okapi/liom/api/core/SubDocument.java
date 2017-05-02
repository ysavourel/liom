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

import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.IGroupOrUnit;
import org.oasisopen.liom.api.core.ISkeleton;
import org.oasisopen.liom.api.core.ISubDocument;
import org.oasisopen.liom.api.core.IUnit;

public class SubDocument extends ImplData2<IGroupOrUnit> implements ISubDocument {

	final private IDocument document;
	
	private String id;
	private String original;
	private ISkeleton skeleton;
	
	public SubDocument (IDocument document,
		String id)
	{
		super(null);
		this.document = document;
		setId(id);
	}

	@Override
	public String getId () {
		return id;
	}

	@Override
	public ISubDocument setId (String id) {
		this.id = id;
		return this;
	}

	@Override
	public String getOriginal () {
		return original;
	}

	@Override
	public ISubDocument setOriginal (String original) {
		this.original = original;
		return this;
	}

	@Override
	public IDocument getDocument () {
		return document;
	}

	@Override
	public IUnit addUnit (String id) {
		IUnit item = new Unit(this, id);
		list.add(item);
		return item;
	}

	@Override
	public IGroup addGroup (String id) {
		IGroup item = new Group(this, id);
		list.add(item);
		return item;
	}

	@Override
	public ISkeleton getSkeleton () {
		return skeleton;
	}

	@Override
	public ISkeleton newSkeleton () {
		skeleton = new Skeleton();
		return skeleton;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getParent () {
		return (T)document;
	}

}
