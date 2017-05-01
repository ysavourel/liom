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

import org.oasisopen.liom.api.core.IContent;
import org.oasisopen.liom.api.core.IIgnorable;
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.IfNoTarget;

public class SubUnit implements ISubUnit {

	final private boolean isSegment;
	
	private IUnit parent;
	
	private String id;
	private String srcLang;
	private String trgLang;
	private boolean preserveWS;
	private int trgOrder;
	private IContent source = new Content(this, true);
	private IContent target;
	
	public SubUnit (IUnit parent,
		boolean isSegment)
	{
		this.parent = parent;
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
	public IUnit getParent() {
		return parent;
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
	public IContent getSource () {
		return source;
	}

	@Override
	public IContent getTarget () {
		return target;
	}

	@Override
	public boolean hasTarget () {
		return (target!=null);
	}

	@Override
	public IContent getTarget (IfNoTarget ifNoTarget) {
		if ( target == null ) {
			switch ( ifNoTarget ) {
			case CLONE_SOURCE:
				target = new Content(this, false, (Content)source);
				break;
			case CREATE_EMPTY:
				target = new Content(this, false);
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

	@Override
	public String getSrcLang () {
		return srcLang;
	}

	@Override
	public ISubUnit setSrcLang (String srcLang) {
		this.srcLang = srcLang;
		return this;
	}

	@Override
	public String getTrgLang () {
		return trgLang;
	}

	@Override
	public ISubUnit setTrgLang (String trgLang) {
		this.trgLang = trgLang;
		return this;
	}

	@Override
	public boolean getPreserveWS () {
		return preserveWS;
	}

	@Override
	public ISubUnit setPreserveWS (boolean preserveWS) {
		this.preserveWS = preserveWS;
		return this;
	}

	@Override
	public int getTrgOrder () {
		return trgOrder;
	}

	@Override
	public ISubUnit setTrgOrder (int trgOrder) {
		this.trgOrder = trgOrder;
		return this;
	}

}
