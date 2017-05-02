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
import org.oasisopen.liom.api.core.ISegment;
import org.oasisopen.liom.api.core.IUnit;

public enum Factory {
	
	/**
	 * The unique single instance of the factory. 
	 */
	SI;
	
	public IDocument createDocument () {
		return new Document();
	}

	/**
	 * Creates a new document, a sub-document a unit with one source segment.
	 * @param srcLang the source language.
	 * @param trgLang the target language.
	 * @param subDocId the ID of the sub-document.
	 * @param unitId the ID of the unit.
	 * @return the segment of the first unit in the first sub-document of the new document.
	 */
	public ISegment createDocumentUpToSegment (String srcLang,
		String trgLang,
		String subDocId,
		String unitId)
	{
		IUnit unit = new Document()
			.setSrcLang(srcLang)
			.setTrgLang(trgLang)
			.addSubDocument(subDocId)
			.addUnit(unitId);
		return unit.addSegment();
	}

}
