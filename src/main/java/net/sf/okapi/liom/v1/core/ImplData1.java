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

import org.oasisopen.liom.api.core.ICollection;
import org.oasisopen.liom.api.core.INCFields;
import org.oasisopen.liom.api.core.INCObjects;
import org.oasisopen.liom.api.core.INote;
import org.oasisopen.liom.api.core.IWithContext;
import org.oasisopen.liom.api.core.IWithNCFields;
import org.oasisopen.liom.api.core.IWithNCObjects;
import org.oasisopen.liom.api.core.IWithNotes;

class ImplData1 extends Context
implements IWithContext, IWithNotes, IWithNCObjects, IWithNCFields {

	private BaseCollection<INote> notes;
	private NCObjects ncObjs;
	private NCFields ncFlds;
	
	@Override
	public boolean hasNote () {
		return (( notes != null ) && !notes.isEmpty() );
	}

	@Override
	public ICollection<INote> getNotes () {
		if ( notes == null ) notes = new BaseCollection<>();
		return notes;
	}

	@Override
	public INote addNote () {
		getNotes();
		INote note = new Note(this);
		notes.add(note);
		return note;
	}

	@Override
	public boolean hasNCObject (String type) {
		if ( ncObjs != null ) {
			return ncObjs.has(type);
		}
		return false;
	}

	@Override
	public INCObjects getNCObjects () {
		if ( ncObjs == null ) {
			ncObjs = new NCObjects();
		}
		return ncObjs;
	}

	@Override
	public boolean hasNCField (String type) {
		if ( ncFlds != null ) {
			return ncFlds.has(type);
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
