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

import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.IUnit;
import org.oasisopen.liom.api.core.TargetState;

public class Segment extends SubUnit implements ISegment {

	private boolean canResegment;
	private TargetState state = TargetState.INITIAL;
	private String subState;
	
	public Segment (IUnit parent) {
		super(parent, true);
	}

	@Override
	public boolean getCanReSegment () {
		return canResegment;
	}

	@Override
	public void setCanResegment (boolean canReSegment) {
		this.canResegment = canReSegment;
	}

	@Override
	public TargetState getState () {
		return state;
	}

	@Override
	public void setState (TargetState state) {
		this.state = state;
	}

	@Override
	public String getSubState () {
		return subState;
	}

	@Override
	public void setSubState (String subState) {
		this.subState = subState;
	}

}
