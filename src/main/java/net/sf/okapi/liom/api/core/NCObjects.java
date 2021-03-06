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

import java.util.HashMap;
import java.util.Map;

import org.oasisopen.liom.api.core.INCObjects;

public class NCObjects implements INCObjects {

	private Map<String, Object> map;
	
	@Override
	public boolean has (String type) {
		return (( map != null ) && !map.isEmpty() );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get (String type) {
		if ( map == null ) return null;
		Object object = map.get(type);
		if ( object != null ) return (T)object;
		else return null;
	}

	@Override
	public void set (String type,
		Object object)
	{
		if ( map == null ) map = new HashMap<>();
		map.put(type, object);
	}

}
