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

import org.oasisopen.liom.api.core.ICollection;
import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.IGroupOrUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithContext;
import org.oasisopen.liom.api.core.IWithGroupOrUnit;
import org.oasisopen.liom.api.core.IWithNCFields;
import org.oasisopen.liom.api.core.IWithNCObjects;
import org.oasisopen.liom.api.core.IWithNotes;

class ImplData3<T> extends ImplData2<T> 
implements IWithContext, IWithNotes, IWithNCObjects, IWithNCFields, ICollection<T>, IGroupOrUnit {

	final private transient IWithGroupOrUnit parent;

	final private boolean isUnit;
	
	private String id;
	private String name;
	private String type;
	
	public ImplData3 (boolean isUnit,
		IWithGroupOrUnit parent)
	{
		this.isUnit = isUnit;
		this.parent = parent;
	}
	
	@Override
	public boolean isUnit () {
		return isUnit;
	}

	@Override
	public IUnit asUnit () {
		if ( isUnit ) return (IUnit)this;
		else return null;
	}

	@Override
	public IGroup asGroup () {
		if ( !isUnit ) return (IGroup)this;
		else return null;
	}

	@Override
	public String getId () {
		return id;
	}

	@Override
	public void setId (String id) {
		this.id = id;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public void setName (String name) {
		this.name = name;
	}

	@Override
	public String getType () {
		return type;
	}

	@Override
	public void setType (String type) {
		this.type = type;
	}

	@Override
	public <T> T getParent () {
		return (T)parent;
	}

}
