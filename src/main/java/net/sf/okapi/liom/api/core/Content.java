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

import java.util.Iterator;
import java.util.regex.Pattern;

import org.oasisopen.liom.api.core.IContent;
import org.oasisopen.liom.api.core.ISubUnit;
import org.oasisopen.liom.api.core.TagType;

import net.sf.okapi.liom.api.InvalidParameterException;
import net.sf.okapi.liom.api.InvalidPositionException;

public class Content implements IContent {

	final private boolean isSource;

	private ISubUnit parent;
	
	protected StringBuilder ctext = new StringBuilder();
	
	private transient Tags tags;
	private transient Store store;

	public Content (ISubUnit parent,
		boolean isSource)
	{
		this.parent = parent;
		this.isSource = isSource;
		this.store = ((Unit)parent.getParent()).getStore();
		if ( isSource ) this.tags = store.getSourceTags();
		else this.tags = store.getTargetTags();
	}
	
	/**
	 * Creates a new {@link Target} object and copy a given content in it.
	 * @param parent the parent {@link ISubUnit} for this content.
	 * @param isSource true if the content to create is for the source, false if it is for the target. 
	 * @param from the object to copy the content from.
	 */
	public Content (ISubUnit parent,
		boolean isSource,
		Content from)
	{
		this(parent, isSource);
		this.ctext = new StringBuilder(from.ctext);
	}

	@Override
	public Iterator<Object> iterator () {
		//Temporary 
		Iterator<Object> iter = new Iterator<Object>() {
			private int count = 0;
			private String text = ctext.toString();
			@Override
			public Object next () {
				if ( count == 1 ) return text;
				return null;
			}
			@Override
			public boolean hasNext () {
				return ((++count)==1); 
			}
		};
		return iter;
	}

	@Override
	public boolean isSource () {
		return isSource;
	}

	@Override
	public IContent asSource () {
		if ( isSource ) return this;
		else return null;
	}

	@Override
	public IContent asTarget () {
		if ( !isSource ) return this;
		else return null;
	}

	@Override
	public ISubUnit getParent () {
		return parent;
	}

	public String toString () {
		return ctext.toString();
	}

	@Override
	public IContent set (String plainText) {
		ctext = new StringBuilder(plainText);
		return this;
	}
	
	@Override
	public IContent append (String plainText) {
		ctext.append(plainText);
		return this;
	}

	@Override
	public IContent append (char ch) {
		ctext.append(ch);
		return this;
	}

	@Override
	public boolean isEmpty () {
		return (ctext.length()==0);
	}

	@Override
	public IContent delete (int start, int end) {
		ctext.delete(start, end);
		return this;
	}

	/**
	 * Inserts a code at a given position (including the end) of this fragment.
	 * @param tagType the type of tag of the code.
	 * @param type the type of the code (can be null).
	 * @param id the ID of the code (if null an new ID is created automatically)
	 * This parameter is ignored when the new tag is created by connecting it with another one.
	 * @param data the original data for the code (can be null).
	 * @param offset the position where to insert the code. Use -1 to append. Other negative values or values greater
	 * than the length of the coded text also cause the code to be appended at the end of the fragment.
	 * @param connect true to connect a new opening code to its closing counterpart, or to connect
	 * a new closing code to its opening counterpart (the counterpart may be in a different content).
	 * Use false to create new opening or closing codes.
	 * This option is ignored used if the code is standalone.
	 * @param allowOrphan true to allow the connect option to fail, that is: to not found the counterpart
	 * of the new code. this option is ignore if connect is false or if the new code is a standalone code. 
	 * @return the new tag created.
	 */
	public CTag insert (TagType tagType,
		String type,
		String id, 
		String data,
		int offset,
		boolean connect,
		boolean allowOrphan)
	{
		// Check ID
		if ( id == null ) {
			if ( connect && !allowOrphan ) {
				throw new InvalidParameterException(
					"Cannot have auto-generated ID when requesting to link to an existing code and not allowing orphan.");
			}
			// Generate an Id
			id = store.suggestId(false);
		}
		// Check/adjust the offset
		if (( offset < 0 ) || ( offset > ctext.length() )) {
			offset = -1; // append at the end
		}
		else { // Check if the insertion point is valid
			checkPosition(offset);
		}
		// Try to get the counterpart code if it is requested
		Tag opposite = null;
		if ( connect && ( tagType != TagType.STANDALONE )) {
			if ( tagType == TagType.OPENING ) {
				opposite = tags.getClosingTag(id);
			}
			else { // TagType.CLOSING
				opposite = tags.getOpeningTag(id);
			}
			if ( opposite == null ) {
				if ( !allowOrphan ) {
					throw new InvalidParameterException(
						String.format("Cannot add closing/opening code because opening/closing code for id '%s' does not exist.", id));
				}
			}
		}
		// Create the new code
		CTag ctag;
		if ( opposite == null ) {
			// This work also for standalone since opposite will always be null for that case
			ctag = new CTag(null, tagType, id, data);
		}
		else {
			// Here id comes from the opposite tag
			CTag tmp = (CTag)opposite;
			ctag = new CTag(tmp, data);
	
			// The case of the firstNo in closing tag is handled in getReorder()
//			// Set the canReorder value based on the opposite tag.
//			switch ( tmp.getTagType() ) {
//			case OPENING:
//				if ( tmp.getCanReorder() == CanReorder.FIRSTNO ) {
//					ctag.setCanReorder(CanReorder.NO);
//					break;
//				}
//				// Else: fall thru
//			default:
//				ctag.setCanReorder(tmp.getCanReorder());
//			}
			// But we still need to copy the value (maybe should move it to code-common?)
			ctag.setCanReorder(tmp.getCanReorder());
		}

		// Add the new code to the collection and add its reference in the coded text
		if ( offset == -1 ) ctext.append(Content.toRef(tags.add(ctag)));
		else ctext.insert(offset, Content.toRef(tags.add(ctag)));
		// Set the type only if not null, to avoid overriding type already defined
		// when this new code is created with a counterpart.
		if ( type != null ) {
			ctag.setType(type);
		}
		return ctag;
	}
	
	/* ================================================================ */
	/* Implementation specific methods                                  */
	/* ================================================================ */
	
	/**
	 * First character of the special pair indicating a reference to the opening tag of a code (a {@link CTag}).
	 */
	public static final char CODE_OPENING = '\uE101';
	/**
	 * First character of the special pair indicating a reference to the closing tag of a code (a {@link CTag}).
	 */
	public static final char CODE_CLOSING = '\uE102';
	/**
	 * First character of the special pair indicating a reference to the standalone tag of a code (a {@link CTag}).
	 */
	public static final char CODE_STANDALONE = '\uE103';
	/**
	 * First character of the special pair indicating a reference to the opening tag of a marker (a {@link MTag}).
	 */
	public static final char MARKER_OPENING = '\uE104';
	/**
	 * First character of the special pair indicating a reference to the closing tag of a marker (a {@link MTag}).
	 */
	public static final char MARKER_CLOSING = '\uE105';
	/**
	 * Base value for the tag reference index.
	 */
	public static final int TAGREF_BASE = 0xE110;
	/**
	 * Maximum number of tag possible per unit for a given type of tag: 6127.
	 */
	public static final int TAGREF_MAX = (0xF8FF-TAGREF_BASE);
	
	/**
	 * Compiled regular expression for all possible kinds of tag reference (the two characters) in a coded text.
	 */
	public static final Pattern TAGREF_REGEX = Pattern.compile("[\uE101\uE102\uE103\uE104\uE105].");

	/**
	 * Converts the first and second character of a tag reference to the key of the tag.
	 * <p>Note that both parameters are passed as integer for convenience, but they are characters.
	 * @param c1 the first character of the reference (the type of inline object and the type of tag).
	 * @param c2 the second character of the reference (the encoded 'index' part of the key)
	 * @return the key for the given tag reference.
	 * @see #toChar1(int)
	 * @see #toChar2(int)
	 * @see #toRef(int)
	 */
	public static int toKey (int c1,
		int c2)
	{
		return ((c1 << 16) | c2);
	}

	/**
	 * Gets the first character of a tag reference from a given tag key.
	 * @param key the key to process.
	 * @return the first character of the tag reference for the given tag key.
	 * @see #toChar2(int)
	 * @see #toRef(int)
	 * @see #toKey(int, int)
	 */
	public static char toChar1 (int key) {
		return (char)(key >> 16);
	}
	
	/**
	 * Gets the second character of a tag reference from a given tag key. 
	 * @param key the key to process.
	 * @return the second character of the tag reference for the given tag key.
	 * @see #toChar1(int)
	 * @see #toRef(int)
	 * @see #toKey(int, int)
	 */
	public static char toChar2 (int key) {
		return (char)key;
	}
	
	/**
	 * Converts a tag key to a reference as used in the coded text.
	 * The reference is a pair of special characters.
	 * @param key the key to convert
	 * @return the tag reference for the given key.
	 * @see #toKey(int, int)
	 * @see #toChar1(int)
	 * @see #toChar2(int)
	 */
	public static String toRef (int key) {
		return ""+(char)(key >> 16)+(char)key;
	}
	
	/**
	 * Helper method that checks if a given character is the first special character
	 * of a tag reference.
	 * If it is true, the next character is the second character of a tag reference and
	 * the key for the tag can be obtained using {@link #toKey(int, int)}. 
	 * @param value the character to check.
	 * @return true if the given character is the first character of a tag reference.
	 */
	public static boolean isChar1 (char value) {
		switch ( value ) {
		case CODE_STANDALONE:
		case CODE_OPENING:
		case CODE_CLOSING:
		case MARKER_OPENING:
		case MARKER_CLOSING:
			return true;
		}
		return false;
	}

	/**
	 * Indicates if a given character is the first special character of a 
	 * {@link CTag} reference.
	 * @param value the character to check.
	 * @return true if the given character denotes a {@link CTag} reference.
	 */
	public static boolean isCTag (char value) {
		switch ( value ) {
		case CODE_STANDALONE:
		case CODE_OPENING:
		case CODE_CLOSING:
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the content of this content in coded text format.
	 * <p>The coded text format is made of normal content and a pair of special characters for each tag in the content. 
	 * @return the coded text string for this content.
	 */
	public String getCodedText () {
		return ctext.toString();
	}

	/**
	 * Verifies if a given position in the coded text is on the second special
	 * character of a tag reference.
	 * @param position the position to check.
	 * @throws InvalidPositionException when position points inside a tag reference.
	 */
	public void checkPosition (int position) {
		if ( position > 0 ) {
			if ( isChar1(ctext.charAt(position-1)) ) {
				throw new InvalidPositionException (
					String.format("Position %d is inside a tag reference.", position));
			}
		}
	}

	
}
