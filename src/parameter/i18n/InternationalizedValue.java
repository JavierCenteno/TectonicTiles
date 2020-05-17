/*
 * InternationalizedValue.java
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
 * A value with internationalization related functionalities so it can be
 * converted from and to string in a way that's adapted to a language.
 *
 * @param <T> The type of the value.
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedValue<T> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * The string representing the null value.
	 */
	public static final InternationalizedString NULL = new InternationalizedString("internationalized.value.null");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * This value.
	 */
	private T value;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized value with a null value.
	 */
	public InternationalizedValue() {
	}

	/**
	 * Instances an internationalized value with the given value.
	 * 
	 * @param value The value.
	 */
	public InternationalizedValue(T value) {
		this.value = value;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public int hashCode() {
		return this.isNull() ? 0 : this.value.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof InternationalizedValue) {
			if (this.value == null) {
				return ((InternationalizedValue<?>) object).value == null;
			} else {
				return this.value.equals(((InternationalizedValue<?>) object).value);
			}
		} else {
			return false;
		}
	}

	/**
	 * Get the string representation of this value. Returns the string representing
	 * the null value if the underlying value is null, otherwise just returns the
	 * result of calling the toString() method on the underlying value.
	 * 
	 * Classes extending this class should overload this method if the value's class
	 * can have different string representations in different languages.
	 * 
	 * @see InternationalizedValue#NULL
	 */
	@Override
	@Internationalized
	public String toString() {
		if (this.value == null) {
			return NULL.getValue();
		} else {
			return this.value.toString();
		}
	}

	/**
	 * Get the value.
	 * 
	 * @return The value.
	 */
	public T getValue() {
		return this.value;
	}

	/**
	 * Set the value.
	 * 
	 * @param value The value.
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * Get whether the value is null.
	 * 
	 * @return Whether the value is null.
	 */
	public boolean isNull() {
		return this.value == null;
	}

}
