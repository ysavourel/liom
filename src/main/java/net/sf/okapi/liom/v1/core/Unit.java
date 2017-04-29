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

package net.sf.okapi.liom.v1.core;

import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IWithGroupOrUnit;
import org.oasisopen.liom.api.glossary.IGlossary;

public class Unit extends ContextAndNotesAndCollectionAndGOU<ISubUnit> implements IUnit {

	private IGlossary glossary;
	
	public Unit (IWithGroupOrUnit parent) {
		super(true, parent);
	}

	@Override
	public boolean hasNCObject (String type) {
		if ( type.equals("glossary") ) {
			return (( glossary != null ));
		}
		return super.hasNCObject(type);
	}

}
