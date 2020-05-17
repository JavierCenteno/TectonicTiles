/*
 * InternationalizedShort.java
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
 * A short with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedShort extends InternationalizedValue<Short> implements Comparable<InternationalizedShort> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.short.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized short with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedShort(Short value) {
		super(value);
	}

	/**
	 * Instances an internationalized short with the value resulting from parsing
	 * the given string.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null.
	 * 
	 * @param value The value.
	 * @see Short#parseShort(String)
	 * @throws IllegalArgumentException If the string can't be parsed as a valid
	 *                                  short value.
	 */
	@Internationalized
	public InternationalizedShort(String value) {
		try {
			if (value == null || NULL.getValue().equals(value)) {
				this.setValue(null);
			} else {
				this.setValue(Short.parseShort(value));
			}
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value), exception);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized short with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized short with the given value.
	 */
	public static InternationalizedShort of(Short value) {
		return new InternationalizedShort(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Compares this internationalized short with the given internationalized short.
	 * Throws a null pointer exception if either internationalized short is null.
	 * 
	 * @param internationalizedShort Internationalized short to compare this
	 *                               internationalized short to.
	 * @return An integer indicating how this internationalized short compares with
	 *         the given internationalized short.
	 * @throws NullPointerException if either internationalized short is null.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(InternationalizedShort internationalizedShort) {
		return this.getValue().compareTo(internationalizedShort.getValue());
	}

}
