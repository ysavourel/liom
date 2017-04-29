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

import java.util.ArrayList;
import java.util.List;

import org.oasisopen.liom.api.core.ICollection;

public class ContextAndNotesAndCollection<T> extends ContextAndNotes implements ICollection<T> {

	protected List<T> list = new ArrayList<>();
	
	@Override
	public boolean isEmpty () {
		return list.isEmpty();
	}

	@Override
	public int size () {
		return list.size();
	}

	@Override
	public boolean remove (T item) {
		return list.remove(item);
	}

	@Override
	public void clear () {
		list.clear();
	}

	@Override
	public T get (int index) {
		return list.get(index);
	}

}
