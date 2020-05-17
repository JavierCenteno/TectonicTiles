/*
 * InternationalizedInteger.java
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
 * An integer with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedInteger extends InternationalizedValue<Integer> implements Comparable<InternationalizedInteger> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.integer.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized integer with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedInteger(Integer value) {
		super(value);
	}

	/**
	 * Instances an internationalized integer with the value resulting from parsing
	 * the given string.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null.
	 * 
	 * @param value The value.
	 * @see Integer#parseInt(String)
	 * @throws IllegalArgumentException If the string can't be parsed as a valid
	 *                                  integer value.
	 */
	@Internationalized
	public InternationalizedInteger(String value) {
		try {
			if (value == null || NULL.getValue().equals(value)) {
				this.setValue(null);
			} else {
				this.setValue(Integer.parseInt(value));
			}
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value), exception);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized integer with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized integer with the given value.
	 */
	public static InternationalizedInteger of(Integer value) {
		return new InternationalizedInteger(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Compares this internationalized integer with the given internationalized
	 * integer. Throws a null pointer exception if either internationalized integer
	 * is null.
	 * 
	 * @param internationalizedInteger Internationalized integer to compare this
	 *                                 internationalized integer to.
	 * @return An integer indicating how this internationalized integer compares
	 *         with the given internationalized integer.
	 * @throws NullPointerException if either internationalized integer is null.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(InternationalizedInteger internationalizedInteger) {
		return this.getValue().compareTo(internationalizedInteger.getValue());
	}

}
