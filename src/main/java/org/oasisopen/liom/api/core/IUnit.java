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

import org.oasisopen.liom.api.glossary.IWithGlossary;

public interface IUnit extends IGroupOrUnit, IWithGlossary, ICollection<ISubUnit> {

	public ISegment addSegment ();
	
	public IIgnorable addIgnorable ();

	public boolean isSourceEmpty ();
	
	public boolean isTargetEmpty ();

}
