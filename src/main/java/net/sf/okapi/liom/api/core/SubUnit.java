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

import org.oasisopen.liom.api.core.IfNoTarget;
import org.oasisopen.liom.api.core.IIgnorable;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISource;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.ITarget;

public class SubUnit implements ISubUnit {

	final private boolean isSegment;
	
	private String id;
	private ISource source = new Source();
	private ITarget target;
	
	public SubUnit (boolean isSegment) {
		this.isSegment = isSegment;
	}
	
	@Override
	public boolean isSegment () {
		return isSegment;
	}

	@Override
	public ISegment asSegment () {
		if ( isSegment ) return (ISegment)this;
		else return null;
	}

	@Override
	public IIgnorable asIgnorable () {
		if ( !isSegment ) return (IIgnorable)this;
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
	public ISource getSource () {
		return source;
	}

	@Override
	public ITarget getTarget () {
		return target;
	}

	@Override
	public boolean hasTarget () {
		return (target!=null);
	}

	@Override
	public ITarget getTarget (IfNoTarget ifNoTarget) {
		if ( target == null ) {
			switch ( ifNoTarget ) {
			case CLONE_SOURCE:
				target = new Target((Content)source);
				break;
			case CREATE_EMPTY:
				target = new Target();
				break;
			case DONT_CREATE:
				// Do not create: leave it null
				break;
			}
		}
		return target;
	}

	@Override
	public boolean isSourceEmpty () {
		return source.isEmpty();
	}

	@Override
	public boolean isTargetEmpty () {
		return (( target == null ) || target.isEmpty() );
	}

}
