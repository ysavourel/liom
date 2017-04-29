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

package net.sf.okapi.liom.v1.core;

import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.ISubDocument;

public class Document extends BaseCollection<ISubDocument> implements IDocument {

	private String version = "2.1";
	private String srcLang = "en";
	private String trgLang;
	
	@Override
	public String getVersion () {
		return version;
	}

	@Override
	public String getSrcLang () {
		return srcLang;
	}

	@Override
	public void setSrcLang (String srcLang) {
		this.srcLang = srcLang;
	}

	@Override
	public String getTrgLang () {
		return trgLang;
	}

	@Override
	public void setTrgLang (String trgLang) {
		this.trgLang = trgLang;
	}

	@Override
	public ISubDocument addSubDocument () {
		ISubDocument sd = new SubDocument(this);
		list.add(sd);
		return sd;
	}

}
