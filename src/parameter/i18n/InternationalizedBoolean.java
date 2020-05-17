/*
 * InternationalizedBoolean.java
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
 * A boolean with internationalized methods for string conversion.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedBoolean extends InternationalizedValue<Boolean> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString FALSE = new InternationalizedString("internationalized.boolean.false");
	private static final InternationalizedString TRUE = new InternationalizedString("internationalized.boolean.true");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"internationalized.boolean.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized boolean with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedBoolean(Boolean value) {
		super(value);
	}

	/**
	 * Instances an internationalized boolean with the value resulting from parsing
	 * the given string.
	 * 
	 * If the given string is null or matches the internationalized value of null,
	 * the value will be set to null.
	 * 
	 * @param value The value.
	 * @throws IllegalArgumentException If the string can't be parsed as a valid
	 *                                  boolean value.
	 */
	@Internationalized
	public InternationalizedBoolean(String value) {
		if (value == null || NULL.getValue().equals(value)) {
			this.setValue(null);
		} else if (value.equals(FALSE.getValue())) {
			this.setValue(false);
		} else if (value.equals(TRUE.getValue())) {
			this.setValue(true);
		} else {
			throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value));
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Instances an internationalized boolean with the given value.
	 * 
	 * @param value The value.
	 * @return An internationalized boolean with the given value.
	 */
	public static InternationalizedBoolean of(Boolean value) {
		return new InternationalizedBoolean(value);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	@Internationalized
	public String toString() {
		if (this.getValue() == null) {
			return NULL.getValue();
		}
		if (this.getValue()) {
			return TRUE.getValue();
		} else {
			return FALSE.getValue();
		}
	}

}
