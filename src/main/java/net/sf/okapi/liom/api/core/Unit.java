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

import org.oasisopen.liom.api.core.IIgnorable;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithGroupOrUnit;

public class Unit extends ImplData3<ISubUnit> implements IUnit {

	public Unit (IWithGroupOrUnit parent) {
		super(true, parent);
	}

	@Override
	public ISegment addSegment () {
		ISegment item = new Segment();
		list.add(item);
		return item;
	}

	@Override
	public IIgnorable addIgnorable () {
		IIgnorable item = new Ignorable();
		list.add(item);
		return item;
	}

	@Override
	public boolean isSourceEmpty () {
		for ( int i=0; i<size(); i++ ) {
			if ( !get(i).isSourceEmpty() ) return false;
		}
		return true;
	}

	@Override
	public boolean isTargetEmpty () {
		for ( int i=0; i<size(); i++ ) {
			if ( !get(i).isTargetEmpty() ) return false;
		}
		return true;
	}

}
