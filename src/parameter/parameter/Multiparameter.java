/*
 * Multiparameter.java
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

import java.util.ArrayList;
import java.util.List;

import parameter.i18n.Internationalized;
import parameter.i18n.InternationalizedString;

/**
 * A parameter which can hold an arbitrary amount of values.
 *
 * @param <T> The type of the value of the parameter.
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class Multiparameter<T> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CONSTRAINT_REPEATED_VALUES = new InternationalizedString(
			"parameter.multi.constraint.repeatedValues");
	private static final InternationalizedString CONSTRAINT_NO_REPEATED_VALUES = new InternationalizedString(
			"parameter.multi.constraint.noRepeatedValues");
	private static final InternationalizedString EXCEPTION_ALREADY_EXISTS = new InternationalizedString(
			"parameter.multi.exception.alreadyExists");
	private static final InternationalizedString EXCEPTION_NO_SUCH_VALUE = new InternationalizedString(
			"parameter.multi.exception.noSuchValue");

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	/**
	 * The underlying parameter holding the methods that enable this multiparameter
	 * to work.
	 */
	private Parameter<T> parameter;
	/**
	 * The list of values this parameter currently holds.
	 */
	private List<T> values;
	/**
	 * Whether this multiparameter allows repeated values.
	 */
	private boolean allowsRepeatedValues;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new multiparameter using the methods from given parameter. The
	 * given parameter should be used by this multiparameter only.
	 * 
	 * @param parameter            The underlying parameter this multiparameter is
	 *                             based on. Should be used by this multiparameter
	 *                             only.
	 * @param allowsRepeatedValues Whether this multiparameter allows repeated
	 *                             values.
	 */
	public Multiparameter(Parameter<T> parameter, boolean allowsRepeatedValues) {
		this.parameter = parameter;
		this.values = new ArrayList<T>();
		this.allowsRepeatedValues = allowsRepeatedValues;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Get the list of values this multiparameter currently holds.
	 * 
	 * @return The list of values this multiparameter currently holds.
	 */
	public List<T> getValues() {
		return this.values;
	}

	/**
	 * Set the list of values this multiparameter currently holds.
	 * 
	 * @param values A list of values.
	 */
	public void getValues(List<T> values) {
		this.values = values;
	}

	/**
	 * Add the value to the list of values.
	 * 
	 * @param value A value.
	 * @throws IllegalArgumentException If the list doesn't allow repeated values
	 *                                  and it already contains such a value.
	 */
	public void addValue(T value) {
		if (!this.allowsRepeatedValues && this.values.contains(value)) {
			throw new IllegalArgumentException(
					"The list doesn't allow repeated values and it already contains such a value.");
		}
		this.values.add(value);
	}

	/**
	 * Remove the value from the list of values.
	 * 
	 * @param value A value.
	 * @throws IllegalArgumentException If the list doesn't contain such a value.
	 */
	public void removeValue(T value) {
		if (!this.values.remove(value)) {
			throw new IllegalArgumentException("The list doesn't contain such a value.");
		}
	}

	/**
	 * Void the list of values this multiparameter currently holds.
	 */
	public void reset() {
		this.values.clear();
	}

	/**
	 * Get whether this multiparameter allows repeated values.
	 * 
	 * @return Whether this multiparameter allows repeated values.
	 */
	public boolean getAllowsRepeatedValues() {
		return this.allowsRepeatedValues;
	}

	/**
	 * Set whether this multiparameter allows repeated values.
	 * 
	 * @param allowsRepeatedValues Whether this multiparameter allows repeated
	 *                             values.
	 */
	public void setAllowsRepeatedValues(boolean allowsRepeatedValues) {
		this.allowsRepeatedValues = allowsRepeatedValues;
	}

	////////////////////////////////////////////////////////////////////////////////
	// I/O methods

	/**
	 * Get the name of this multiparameter. This method returns the name of the
	 * underlying parameter.
	 * 
	 * @return The name of this multiparameter.
	 * @see Parameter#userGetName()
	 */
	@Internationalized
	public String userGetName() {
		return this.parameter.userGetName();
	}

	/**
	 * Get the constraints for the value of this multiparameter as described in the
	 * internationalization files. This method returns the constraints of the
	 * underlying parameter.
	 * 
	 * @return A string expressing the constraints for the values of this
	 *         multiparameter.
	 * @see Parameter#userGetConstraints()
	 */
	@Internationalized
	public String userGetConstraints() {
		if (this.allowsRepeatedValues) {
			return CONSTRAINT_REPEATED_VALUES.getValue("{constraints}", this.parameter.userGetConstraints());
		} else {
			return CONSTRAINT_NO_REPEATED_VALUES.getValue("{constraints}", this.parameter.userGetConstraints());
		}
	}

	/**
	 * Set the list of values to the value represented by the given string using
	 * newlines as the separation between strings representing different values.
	 * Each line of the string is interpreted as a value through the underlying
	 * parameter.
	 * 
	 * @param values A string consisting of strings representing multiple values
	 *               separated by newlines.
	 * @throws IllegalArgumentException If the given string can't be interpreted as
	 *                                  a valid value.
	 * @see Parameter#userSetCurrentValue(String)
	 */
	public void userSetValues(String values) {
		this.reset();
		for (String value : values.split("\n")) {
			this.userAddValue(value);
		}
	}

	/**
	 * Add the value represented by the given string to the list of values. The
	 * string is interpreted as a value through the underlying parameter.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If the given string can't be interpreted as
	 *                                  a valid value or the list doesn't allow
	 *                                  repeats and it already contains such a
	 *                                  value.
	 * @see Parameter#userSetCurrentValue(String)
	 * @see Multiparameter#addValue(Object)
	 */
	public void userAddValue(String value) {
		this.parameter.userSetCurrentValue(value);
		T t = this.parameter.getCurrentValue();
		try {
			this.addValue(t);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException(EXCEPTION_ALREADY_EXISTS.getValue("{value}", value), exception);
		}
	}

	/**
	 * Remove the value represented by the given string from the list of values. The
	 * string is interpreted as a value through the underlying parameter.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If the given string can't be interpreted as
	 *                                  a valid value or the list doesn't contain
	 *                                  such a value.
	 * @see Parameter#userSetCurrentValue(String)
	 * @see Multiparameter#removeValue(Object)
	 */
	public void userRemoveValue(String value) {
		this.parameter.userSetCurrentValue(value);
		T t = this.parameter.getCurrentValue();
		try {
			this.removeValue(t);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException(EXCEPTION_NO_SUCH_VALUE.getValue("{value}", value), exception);
		}
	}

}
