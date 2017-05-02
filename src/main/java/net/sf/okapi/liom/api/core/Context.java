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

import org.oasisopen.liom.api.core.Directionality;
import org.oasisopen.liom.api.core.IWithContext;

public class Context implements IWithContext {

	final private transient IWithContext parent;
	
	private Boolean canReSegment = null;
	private Boolean translate = null;
	private Boolean preserveWS = null;
	private Directionality srcDir = null;
	private Directionality trgDir = null;
	
	public Context (IWithContext parent) {
		this.parent = parent;
	}
	
	@Override
	public Boolean getCanResegment () {
		// If undefined: inherit from parent
		if ( canReSegment == null ) {
			if ( parent != null ) return parent.getCanResegment();
			return true; // Sub-document default
		}
		return canReSegment;
	}

	@Override
	public void setCanResegment (Boolean canReSegment) {
		this.canReSegment = canReSegment;
	}

	@Override
	public Boolean getTranslate () {
		// If undefined: inherit from parent
		if ( translate == null ) {
			if ( parent != null ) return parent.getTranslate();
			return true; // Sub-document default
		}
		return translate;
	}

	@Override
	public void setTranslate (Boolean translate) {
		this.translate = translate;
	}

	@Override
	public Directionality getSrcDir () {
		// If undefined: inherit from parent
		if ( srcDir == null ) {
			if ( parent != null ) return parent.getSrcDir();
			return Directionality.AUTO; // Sub-document default
		}
		return srcDir;
	}

	@Override
	public void setSrcDir (Directionality srcDir) {
		this.srcDir = srcDir;
	}

	@Override
	public Directionality getTrgDir () {
		// If undefined: inherit from parent
		if ( trgDir == null ) {
			if ( parent != null ) return parent.getTrgDir();
			return Directionality.AUTO; // Sub-document default
		}
		return trgDir;
	}

	@Override
	public void setTrgDir (Directionality trgDir) {
		this.trgDir = trgDir;
	}

	@Override
	public Boolean getPreserveWS () {
		// If undefined: inherit from parent
		if ( preserveWS == null ) {
			if ( parent != null ) return parent.getPreserveWS();
			//TODO: get default from IDocument
			return false; // Default
		}
		return preserveWS;
	}

	@Override
	public void setPreserveWS (Boolean preserveWS) {
		this.preserveWS = preserveWS;
	}

}
