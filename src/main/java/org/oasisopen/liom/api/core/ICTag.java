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

import java.util.List;

public interface ICTag extends ITag {

	public String getSubType ();
	
	public void setSubType (String subType);
	
	public String getDisp ();
	
	public void setDisp (String disp);
	
	public String getEquiv ();
	
	public void setEquiv (String equiv);
	
	public Directionality getDir ();
	
	public void setDir (Directionality dir);

	public String getDataRef ();
	
	public void setDataRef (String dataRef);

	public List<String> getSubFlows ();
	
	public void setSubFlows (List<String> subFlows);

	public String getCopyOf ();
	
	public void setCopyOf (String copyOf);

	public boolean getCanCopy ();
	
	public void setCanCopy (boolean canCopy);
	
	public boolean getCanDelete ();
	
	public void setCanDelete (boolean canDelete);
	
	public boolean getCanOverlap ();
	
	public void setCanOverlap (boolean canOverlap);
	
	public CanReorder getCanReorder ();
	
	public void setCanReorder (CanReorder canReorder);

}
