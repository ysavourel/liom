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

package org.oasisopen.liom.api.core;

public interface IWithContext {

	public boolean getCanResegment ();
	
	public void setCanResegment (boolean canResegment);
	
	public boolean getTranslate ();
	
	public void setTranslate (boolean translate);
	
	public Directionality getSrcDir ();
	
	public void setSrcDir (Directionality srcDir);

	public Directionality getTrgDir ();
	
	public void setTrgDir (Directionality trgtDir);

	public boolean getPreserveWS ();
	
	public void setPreserveWS (boolean preserveWS);

}
