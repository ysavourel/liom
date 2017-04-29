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

/**
 * Represents a top-level LIOM object: a document.
 * A {@link IDocument} is made of one or more {@link ISubDocument} objects.
 */
public interface IDocument extends ICollection<ISubDocument> {

	public String getVersion ();
	
	public String getSrcLang ();
	
	public void setSrcLang (String srcLang);
	
	public String getTrgLang ();
	
	public void setTrgLang (String trgLang);

	public ISubDocument addSubDocument ();

}
