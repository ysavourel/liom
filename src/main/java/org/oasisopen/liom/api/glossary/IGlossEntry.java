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

package org.oasisopen.liom.api.glossary;

import org.oasisopen.liom.api.core.ICollection;
import org.oasisopen.liom.api.core.IWithNCFields;

public interface IGlossEntry extends IWithNCFields, ICollection<ITranslation> {

	public String getId ();
	
	public IGlossEntry setId (String id);

	public String getRef ();
	
	public IGlossEntry setRef (String ref);

	public ITerm getTerm ();

	public ITerm newTerm (String text);

	public IDefinition getDefinition ();
	
	public IDefinition newDefinition (String text);

	public ITranslation addTranslation (String text);

}
