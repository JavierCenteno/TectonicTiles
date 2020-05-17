/*
 * InternationalizedByte.java
 * 
 * This file is part of Tectonic Tiles.
 * Tectonic Tiles is a random terrain generator inspired by plate tectonics.
 * Copyright (C) 2020 Javier Centeno Vega
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package parameter.i18n;

/**
 * A byte with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedByte extends InternationalizedValue<Byte> implements Comparable<InternationalizedByte> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.byte.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized byte with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedByte(Byte value) {
		super(value);
	}

	/**
	 * Instances an internationalized byte with the value resulting from parsing the
	 * given string.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null.
	 * 
	 * @param value The value.
	 * @see Byte#parseByte(String)
	 * @throws IllegalArgumentException If the string can't be parsed as a valid
	 *                                  byte value.
	 */
	@Internationalized
	public InternationalizedByte(String value) {
		try {
			if (value == null || NULL.getValue().equals(value)) {
				this.setValue(null);
			} else {
				this.setValue(Byte.parseByte(value));
			}
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value), exception);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized byte with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized byte with the given value.
	 */
	public static InternationalizedByte of(Byte value) {
		return new InternationalizedByte(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Compares this internationalized byte with the given internationalized byte.
	 * Throws a null pointer exception if either internationalized byte is null.
	 * 
	 * @param internationalizedByte Internationalized byte to compare this
	 *                              internationalized byte to.
	 * @return An integer indicating how this internationalized byte compares with
	 *         the given internationalized byte.
	 * @throws NullPointerException if either internationalized byte is null.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(InternationalizedByte internationalizedByte) {
		return this.getValue().compareTo(internationalizedByte.getValue());
	}

}
