/*
 * Parameter.java
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

package parameter.parameter;

import parameter.i18n.Internationalized;
import parameter.i18n.InternationalizedString;

/**
 * A value of a given type which can have a name and a default value.
 *
 * @param <T> The type of the value.
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public abstract class Parameter<T> {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	/**
	 * The name of this parameter.
	 */
	@Internationalized
	private InternationalizedString name;
	/**
	 * The current value of this parameter. May be null.
	 */
	private T currentValue;
	/**
	 * The default value of this parameter. May be null.
	 */
	private T defaultValue;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new parameter with a given name and the current or default value
	 * set to null.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 */
	public Parameter(String nameKey) {
		this.name = new InternationalizedString(nameKey);
		this.currentValue = null;
		this.defaultValue = null;
	}

	/**
	 * Constructs a new parameter with a given name and a given default value.
	 * Automatically sets its current value to the default value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 */
	public Parameter(String nameKey, T defaultValue) {
		this(nameKey);
		this.currentValue = defaultValue;
		this.defaultValue = defaultValue;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Get the current value of this parameter. May be null if the parameter doesn't
	 * have a value.
	 * 
	 * @return The current value of this parameter.
	 */
	public T getCurrentValue() {
		return this.currentValue;
	}

	/**
	 * Set the current value of this parameter to the given value.
	 * 
	 * @param currentValue A value.
	 */
	public void setCurrentValue(T currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * Get the default value of this parameter. May be null if the parameter doesn't
	 * have a default value.
	 * 
	 * @return The default value of this parameter.
	 */
	public T getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Set the default value of this parameter to the given value.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Set the current value of this parameter to the default value.
	 */
	public void reset() {
		this.currentValue = this.defaultValue;
	}

	////////////////////////////////////////////////////////////////////////////////
	// I/O methods

	/**
	 * Get the name of this parameter.
	 * 
	 * @return The name of this parameter.
	 */
	@Internationalized
	public String userGetName() {
		return this.name.getValue();
	}

	/**
	 * Get the constraints for the value of this parameter as described in the
	 * internationalization files.
	 * 
	 * The string informs the user of the allowed values for this parameter and it
	 * describes what inputs the setValue(String) method can take.
	 * 
	 * @return A string expressing the constraints for the value of this parameter.
	 * @see Parameter#userSetCurrentValue(String)
	 */
	@Internationalized
	public abstract String userGetConstraints();

	/**
	 * Get the current value of this parameter as a string. Used to display the
	 * value to the user.
	 * 
	 * The implementing class should guarantee that the format of the returned value
	 * string matches the format of the input string taken by the setValue(String)
	 * method, that is, if this parameter has a valid value it should be possible to
	 * pass the result of this method to the setValue(String) method and this
	 * parameter's current value should be the same.
	 * 
	 * @return The current value of this parameter as a string.
	 * @see Parameter#userSetCurrentValue(String)
	 */
	@Internationalized
	public abstract String userGetCurrentValue();

	/**
	 * Set the current value of this parameter to the value represented by the given
	 * string. Used to set the value based on input given by the user.
	 * 
	 * The implementing class should guarantee that the format of the taken input
	 * string matches the format of the value string returned by the getValue()
	 * method, that is, if this parameter has a valid value it should be possible to
	 * pass the result of the getValue() method to this method and this parameter's
	 * current value should be the same.
	 * 
	 * For implementing classes, it is also recommended that an empty value is
	 * interpreted as either resetting the parameter's value to the default value if
	 * possible or as performing no change.
	 * 
	 * This method should throw an IllegalArgumentException if the given string
	 * can't be interpreted as a valid value.
	 * 
	 * This method should throw a CancelledException if the given string matches the
	 * value of cancel in the internationalization.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If the given string can't be interpreted as
	 *                                  a valid value.
	 * @throws CancelledException       If a value matching a cancellation
	 *                                  instruction is given.
	 * @see Parameter#userGetCurrentValue()
	 */
	@Internationalized
	public abstract void userSetCurrentValue(String value);

}
