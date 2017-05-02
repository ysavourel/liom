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

import org.oasisopen.liom.api.core.IDocument;
import org.oasisopen.liom.api.core.ISubDocument;

public class Document extends BaseCollection<ISubDocument> implements IDocument {

	private String version = "1.0";
	private String srcLang = "en";
	private String trgLang;
	private Boolean preserveWS = null;
	
	@Override
	public String getVersion () {
		return version;
	}

	@Override
	public String getSrcLang () {
		return srcLang;
	}

	@Override
	public IDocument setSrcLang (String srcLang) {
		this.srcLang = srcLang;
		return this;
	}

	@Override
	public String getTrgLang () {
		return trgLang;
	}

	@Override
	public IDocument setTrgLang (String trgLang) {
		this.trgLang = trgLang;
		return this;
	}

	@Override
	public Boolean getPreserveWS () {
		// If undefined: use default
		if ( preserveWS == null ) {
			return false; // Document default
		}
		return preserveWS;
	}

	@Override
	public void setPreserveWS (Boolean preserveWS) {
		this.preserveWS = preserveWS;
	}

	@Override
	public ISubDocument addSubDocument (String id) {
		ISubDocument sd = new SubDocument(this, id);
		list.add(sd);
		return sd;
	}

	@Override
	public ISubDocument find (String id) {
		for ( int i=0; i<size(); i++ ) {
			if ( id.equals(get(i).getId()) ) return get(i);
		}
		return null;
	}

}
