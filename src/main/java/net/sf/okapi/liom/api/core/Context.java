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

	private boolean canReSegment = true;
	private boolean translate = true;
	private boolean preserveWS = false;
	private Directionality srcDir;
	private Directionality trgDir;
	
	@Override
	public boolean getCanResegment () {
		return canReSegment;
	}

	@Override
	public void setCanResegment (boolean canReSegment) {
		this.canReSegment = canReSegment;
	}

	@Override
	public boolean getTranslate () {
		return translate;
	}

	@Override
	public void setTranslate (boolean translate) {
		this.translate = translate;
	}

	@Override
	public Directionality getSrcDir () {
		return srcDir;
	}

	@Override
	public void setSrcDir (Directionality srcDir) {
		this.srcDir = srcDir;
	}

	@Override
	public Directionality getTrgDir () {
		return trgDir;
	}

	@Override
	public void setTrgDir (Directionality trgDir) {
		this.trgDir = trgDir;
	}

	@Override
	public boolean getPreserveWS () {
		return preserveWS;
	}

	@Override
	public void setPreserveWS (boolean preserveWS) {
		this.preserveWS = preserveWS;
	}

}
