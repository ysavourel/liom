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

import org.oasisopen.liom.api.core.IGroup;
import org.oasisopen.liom.api.core.IGroupOrUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithGroupOrUnit;

public class Group extends ImplData3<IGroupOrUnit> implements IGroup {

	public Group (IWithGroupOrUnit parent,
		String id)
	{
		super(false, parent, id);
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

}
