/*
 * InternationalizedBigDecimal.java
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

import java.math.BigDecimal;

/**
 * A big decimal with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedBigDecimal extends InternationalizedValue<BigDecimal>
		implements Comparable<InternationalizedBigDecimal> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString DECIMAL_SEPARATOR = new InternationalizedString(
			"internationalized.bigDecimal.decimalSeparator");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.bigDecimal.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized big decimal with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedBigDecimal(BigDecimal value) {
		super(value);
	}

	/**
	 * Instances an internationalized big decimal with the value resulting from
	 * parsing the given string.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null.
	 * 
	 * @param value The value.
	 * @see BigDecimal#BigDecimal(String)
	 * @throws IllegalArgumentException If the string can't be parsed as a valid big
	 *                                  decimal value.
	 */
	@Internationalized
	public InternationalizedBigDecimal(String value) {
		try {
			if (value == null || NULL.getValue().equals(value)) {
				this.setValue(null);
			} else {
				value = value.replace(DECIMAL_SEPARATOR.getValue(), ".");
				this.setValue(new BigDecimal(value));
			}
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value), exception);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized big decimal with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized big decimal with the given value.
	 */
	public static InternationalizedBigDecimal of(BigDecimal value) {
		return new InternationalizedBigDecimal(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	@Internationalized
	public String toString() {
		if (this.getValue() == null) {
			return NULL.getValue();
		} else {
			String value = this.getValue().toString();
			value = value.replace(".", DECIMAL_SEPARATOR.getValue());
			return value;
		}
	}

	/**
	 * Compares this internationalized big decimal with the given internationalized
	 * big decimal. Throws a null pointer exception if either internationalized big
	 * decimal is null.
	 * 
	 * @param internationalizedBigDecimal Internationalized big decimal to compare
	 *                                    this internationalized big decimal to.
	 * @return An decimal indicating how this internationalized big decimal compares
	 *         with the given internationalized big decimal.
	 * @throws NullPointerException if either internationalized big decimal is null.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(InternationalizedBigDecimal internationalizedBigDecimal) {
		return this.getValue().compareTo(internationalizedBigDecimal.getValue());
	}

}
