/*
 * InternationalizedFloat.java
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
 * A float with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedFloat extends InternationalizedValue<Float>
		implements Comparable<InternationalizedFloat> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString DECIMAL_SEPARATOR = new InternationalizedString(
			"internationalized.float.decimalSeparator");
	private static final InternationalizedString NEGATIVE_INFINITY = new InternationalizedString(
			"internationalized.float.negativeInfinity");
	private static final InternationalizedString POSITIVE_INFINITY = new InternationalizedString(
			"internationalized.float.positiveInfinity");
	private static final InternationalizedString NOT_A_NUMBER = new InternationalizedString(
			"internationalized.float.notANumber");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.float.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized float with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedFloat(Float value) {
		super(value);
	}

	/**
	 * Instances an internationalized float with the value resulting from parsing
	 * the given string using the decimal separator used in the internationalization
	 * files as the decimal separator.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null. If it matches the internationalized values for
	 * negative infinity, positive infinity or NaN, it'll be set to that value.
	 * 
	 * @param value The value.
	 * @see Float#parseFloat(String)
	 * @throws IllegalArgumentException If the string can't be parsed as a valid
	 *                                  float value.
	 */
	@Internationalized
	public InternationalizedFloat(String value) {
		try {
			if (value == null || NULL.getValue().equals(value)) {
				this.setValue(null);
			} else if (value.equals(NEGATIVE_INFINITY.getValue())) {
				this.setValue(Float.NEGATIVE_INFINITY);
			} else if (value.equals(POSITIVE_INFINITY.getValue())) {
				this.setValue(Float.POSITIVE_INFINITY);
			} else if (value.equals(NOT_A_NUMBER.getValue())) {
				this.setValue(Float.NaN);
			} else {
				value = value.replace(DECIMAL_SEPARATOR.getValue(), ".");
				this.setValue(Float.parseFloat(value));
			}
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value), exception);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized float with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized float with the given value.
	 */
	public static InternationalizedFloat of(Float value) {
		return new InternationalizedFloat(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	@Internationalized
	public String toString() {
		if (this.getValue() == null) {
			return NULL.getValue();
		} else if (this.getValue() == Float.NEGATIVE_INFINITY) {
			return NEGATIVE_INFINITY.getValue();
		} else if (this.getValue() == Float.POSITIVE_INFINITY) {
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
	 * Compares this internationalized float with the given internationalized float.
	 * Throws a null pointer exception if either internationalized float is null.
	 * 
	 * @param internationalizedFloat Internationalized float to compare this
	 *                               internationalized float to.
	 * @return An integer indicating how this internationalized float compares with
	 *         the given internationalized float.
	 * @throws NullPointerException if either internationalized float is null.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(InternationalizedFloat internationalizedFloat) {
		return this.getValue().compareTo(internationalizedFloat.getValue());
	}

	/**
	 * Get whether this internationalized float is NaN.
	 * 
	 * @return Whether this internationalized float is NaN.
	 */
	public boolean isNaN() {
		return this.isNull() ? false : this.getValue().isNaN();
	}

}
