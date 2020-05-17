/*
 * InternationalizedDouble.java
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
 * A double with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedDouble extends InternationalizedValue<Double> implements Comparable<InternationalizedDouble> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString DECIMAL_SEPARATOR = new InternationalizedString(
			"internationalized.double.decimalSeparator");
	private static final InternationalizedString NEGATIVE_INFINITY = new InternationalizedString(
			"internationalized.double.negativeInfinity");
	private static final InternationalizedString POSITIVE_INFINITY = new InternationalizedString(
			"internationalized.double.positiveInfinity");
	private static final InternationalizedString NOT_A_NUMBER = new InternationalizedString(
			"internationalized.double.notANumber");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.double.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized double with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedDouble(Double value) {
		super(value);
	}

	/**
	 * Instances an internationalized double with the value resulting from parsing
	 * the given string using the decimal separator used in the internationalization
	 * files as the decimal separator.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null. If it matches the internationalized values for
	 * negative infinity, positive infinity or NaN, it'll be set to that value.
	 * 
	 * @param value The value.
	 * @see Double#parseDouble(String)
	 * @throws IllegalArgumentException If the string can't be parsed as a valid
	 *                                  double value.
	 */
	@Internationalized
	public InternationalizedDouble(String value) {
		try {
			if (value == null || NULL.getValue().equals(value)) {
				this.setValue(null);
			} else if (value.equals(NEGATIVE_INFINITY.getValue())) {
				this.setValue(Double.NEGATIVE_INFINITY);
			} else if (value.equals(POSITIVE_INFINITY.getValue())) {
				this.setValue(Double.POSITIVE_INFINITY);
			} else if (value.equals(NOT_A_NUMBER.getValue())) {
				this.setValue(Double.NaN);
			} else {
				value = value.replace(DECIMAL_SEPARATOR.getValue(), ".");
				this.setValue(Double.parseDouble(value));
			}
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value), exception);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized double with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized double with the given value.
	 */
	public static InternationalizedDouble of(Double value) {
		return new InternationalizedDouble(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	@Internationalized
	public String toString() {
		if (this.getValue() == null) {
			return NULL.getValue();
		} else if (this.getValue() == Double.NEGATIVE_INFINITY) {
			return NEGATIVE_INFINITY.getValue();
		} else if (this.getValue() == Double.POSITIVE_INFINITY) {
			return POSITIVE_INFINITY.getValue();
		} else if (this.isNaN()) {
			return NOT_A_NUMBER.getValue();
		} else {
			String value = this.getValue().toString();
			value = value.replace(".", DECIMAL_SEPARATOR.getValue());
			return value;
		}
	}

	/**
	 * Compares this internationalized double with the given internationalized
	 * double. Throws a null pointer exception if either internationalized double is
	 * null.
	 * 
	 * @param internationalizedDouble Internationalized double to compare this
	 *                                internationalized double to.
	 * @return An integer indicating how this internationalized double compares with
	 *         the given internationalized double.
	 * @throws NullPointerException if either internationalized double is null.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(InternationalizedDouble internationalizedDouble) {
		return this.getValue().compareTo(internationalizedDouble.getValue());
	}

	/**
	 * Get whether this internationalized double is NaN.
	 * 
	 * @return Whether this internationalized double is NaN.
	 */
	public boolean isNaN() {
		return this.isNull() ? false : this.getValue().isNaN();
	}

}
