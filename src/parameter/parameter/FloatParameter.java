/*
 * FloatParameter.java
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

import parameter.i18n.InternationalizedFloat;
import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is a float with maximum and minimum values.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class FloatParameter extends Parameter<InternationalizedFloat> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT = new InternationalizedString(
			"parameter.float.constraint.noDefault");
	private static final InternationalizedString CONSTRAINT_DEFAULT = new InternationalizedString(
			"parameter.float.constraint.default");
	private static final InternationalizedString EXCEPTION_BELOW_MINIMUM = new InternationalizedString(
			"parameter.float.exception.belowMinimum");
	private static final InternationalizedString EXCEPTION_ABOVE_MAXIMUM = new InternationalizedString(
			"parameter.float.exception.aboveMaximum");
	private static final InternationalizedString EXCEPTION_NULL = new InternationalizedString(
			"parameter.float.exception.null");
	private static final InternationalizedString EXCEPTION_NOT_A_NUMBER = new InternationalizedString(
			"parameter.float.exception.notANumber");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"parameter.float.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The minimum value of this parameter (inclusive).
	 */
	private InternationalizedFloat minimumValue;
	/**
	 * The maximum value of this parameter (inclusive).
	 */
	private InternationalizedFloat maximumValue;
	/**
	 * Whether this parameter can be null.
	 */
	private boolean canBeNull;
	/**
	 * Whether this parameter can be NaN.
	 */
	private boolean canBeNaN;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new float parameter with the given name, default value, minimum
	 * value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to negative infinity.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to positive infinity.
	 * @param canBeNull    Whether this parameter can be null.
	 * @param canBeNaN     Whether this parameter can be NaN.
	 */
	public FloatParameter(String nameKey, InternationalizedFloat defaultValue, InternationalizedFloat minimumValue,
			InternationalizedFloat maximumValue, boolean canBeNull, boolean canBeNaN) {
		super(nameKey);
		// set these first to avoid null pointer exceptions
		this.setMinimumValue(InternationalizedFloat.of(null));
		this.setMaximumValue(InternationalizedFloat.of(null));
		// set with the method to trigger the checks in the setters
		this.setCanBeNull(canBeNull);
		this.setCanBeNaN(canBeNaN);
		this.setMinimumValue(minimumValue);
		this.setMaximumValue(maximumValue);
		this.setDefaultValue(defaultValue);
		this.setCurrentValue(defaultValue);
	}

	/**
	 * Constructs a new float parameter with the given name, default value, minimum
	 * value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to negative infinity.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to positive infinity.
	 * @param canBeNull    Whether this parameter can be null.
	 * @param canBeNaN     Whether this parameter can be NaN.
	 */
	public FloatParameter(String nameKey, Float defaultValue, Float minimumValue, Float maximumValue, boolean canBeNull,
			boolean canBeNaN) {
		this(nameKey, new InternationalizedFloat(defaultValue), new InternationalizedFloat(minimumValue),
				new InternationalizedFloat(maximumValue), canBeNull, canBeNaN);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Checks

	/**
	 * Checks if the given value is below the minimum value and throws an exception
	 * if that's the case. Doesn't do anything if the given value is NaN.
	 * 
	 * @param value A value.
	 */
	private void checkBelowMinimum(InternationalizedFloat value) {
		if (!value.isNaN()) {
			if (value.compareTo(this.minimumValue) < 0) {
				throw new IllegalArgumentException(EXCEPTION_BELOW_MINIMUM.getValue("{value}", value.toString(),
						"{minimumValue}", this.minimumValue.toString()));
			}
		}
	}

	/**
	 * Checks if the given value is above the maximum value and throws an exception
	 * if that's the case. Doesn't do anything if the given value is NaN.
	 * 
	 * @param value A value.
	 */
	private void checkAboveMaximum(InternationalizedFloat value) {
		if (!value.isNaN()) {
			if (value.compareTo(this.maximumValue) > 0) {
				throw new IllegalArgumentException(EXCEPTION_ABOVE_MAXIMUM.getValue("{value}", value.toString(),
						"{maximumValue}", this.maximumValue.toString()));
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Set the current value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value
	 * or if it's NaN and NaN is not allowed.
	 * 
	 * @param currentValue A value.
	 */
	public void setCurrentValue(InternationalizedFloat currentValue) {
		if (currentValue == null) {
			currentValue = InternationalizedFloat.of(null);
		}
		if (!this.canBeNull && currentValue.isNull()) {
			throw new IllegalArgumentException(EXCEPTION_NULL.getValue());
		} else if (!currentValue.isNull()) {
			if (!this.canBeNaN && currentValue.isNaN()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_A_NUMBER.getValue());
			}
			if (!this.minimumValue.isNull()) {
				checkBelowMinimum(currentValue);
			}
			if (!this.maximumValue.isNull()) {
				checkAboveMaximum(currentValue);
			}
		}
		super.setCurrentValue(currentValue);
	}

	/**
	 * Set the current value of this parameter to the given value.
	 * 
	 * @param currentValue A value.
	 * @see FloatParameter#setCurrentValue(InternationalizedFloat)
	 */
	public void setCurrentValue(float currentValue) {
		this.setCurrentValue(new InternationalizedFloat(currentValue));
	}

	/**
	 * Set the default value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value
	 * or if it's NaN and NaN is not allowed.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(InternationalizedFloat defaultValue) {
		if (defaultValue == null) {
			defaultValue = InternationalizedFloat.of(null);
		}
		if (!this.canBeNull && defaultValue.isNull()) {
			throw new IllegalArgumentException(EXCEPTION_NULL.getValue());
		} else if (!defaultValue.isNull()) {
			if (!this.canBeNaN && defaultValue.isNaN()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_A_NUMBER.getValue());
			}
			if (!this.minimumValue.isNull()) {
				checkBelowMinimum(defaultValue);
			}
			if (!this.maximumValue.isNull()) {
				checkAboveMaximum(defaultValue);
			}
		}
		super.setDefaultValue(defaultValue);
	}

	/**
	 * Set the default value of this parameter to the given value.
	 * 
	 * @param defaultValue A value.
	 * @see FloatParameter#setDefaultValue(InternationalizedFloat)
	 */
	public void setDefaultValue(float defaultValue) {
		this.setDefaultValue(new InternationalizedFloat(defaultValue));
	}

	/**
	 * Get the minimum value of this parameter (inclusive).
	 * 
	 * @return The minimum value of this parameter.
	 */
	public InternationalizedFloat getMinimumValue() {
		return this.minimumValue;
	}

	/**
	 * Set the minimum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is above the maximum value or NaN.
	 * 
	 * @param minimumValue A value.
	 */
	public void setMinimumValue(InternationalizedFloat minimumValue) {
		if (minimumValue == null) {
			minimumValue = InternationalizedFloat.of(null);
		}
		if (minimumValue.isNull()) {
			// If no minimum value is provided, set to the minimum possible value
			this.minimumValue = new InternationalizedFloat(Float.NEGATIVE_INFINITY);
		} else {
			if (minimumValue.isNaN()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_A_NUMBER.getValue());
			}
			if (!this.maximumValue.isNull()) {
				checkAboveMaximum(minimumValue);
			}
			this.minimumValue = minimumValue;
		}
	}

	/**
	 * Set the minimum value of this parameter to the given value (inclusive).
	 * 
	 * @param minimumValue A value.
	 * @see FloatParameter#setMinimumValue(InternationalizedFloat)
	 */
	public void setMinimumValue(float minimumValue) {
		this.setMinimumValue(new InternationalizedFloat(minimumValue));
	}

	/**
	 * Get the maximum value of this parameter (inclusive).
	 * 
	 * @return The maximum value of this parameter.
	 */
	public InternationalizedFloat getMaximumValue() {
		return this.maximumValue;
	}

	/**
	 * Set the maximum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is below the minimum value or NaN.
	 * 
	 * @param maximumValue A value.
	 */
	public void setMaximumValue(InternationalizedFloat maximumValue) {
		if (maximumValue == null) {
			maximumValue = InternationalizedFloat.of(null);
		}
		if (maximumValue.isNull()) {
			// If no maximum value is provided, set to the maximum possible value
			this.maximumValue = new InternationalizedFloat(Float.POSITIVE_INFINITY);
		} else {
			if (maximumValue.isNaN()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_A_NUMBER.getValue());
			}
			if (!this.minimumValue.isNull()) {
				checkBelowMinimum(maximumValue);
			}
			this.maximumValue = maximumValue;
		}
	}

	/**
	 * Set the maximum value of this parameter to the given value (inclusive).
	 * 
	 * @param maximumValue A value.
	 * @see FloatParameter#setMaximumValue(InternationalizedFloat)
	 */
	public void setMaximumValue(float maximumValue) {
		this.setMaximumValue(new InternationalizedFloat(maximumValue));
	}

	/**
	 * Get whether this parameter's value can be null.
	 * 
	 * @return Whether this parameter's value can be null.
	 */
	public boolean getCanBeNull() {
		return this.canBeNull;
	}

	/**
	 * Set whether this parameter's value can be null.
	 * 
	 * @param canBeNull Whether this parameter's value can be null.
	 */
	public void setCanBeNull(boolean canBeNull) {
		this.canBeNull = canBeNull;
	}

	/**
	 * Get whether this parameter's value can be NaN.
	 * 
	 * @return Whether this parameter's value can be NaN.
	 */
	public boolean getCanBeNaN() {
		return this.canBeNaN;
	}

	/**
	 * Set whether this parameter's value can be NaN.
	 * 
	 * @param canBeNaN Whether this parameter's value can be NaN.
	 */
	public void setCanBeNaN(boolean canBeNaN) {
		this.canBeNaN = canBeNaN;
	}

	////////////////////////////////////////////////////////////////////////////////
	// I/O methods

	@Override
	public String userGetConstraints() {
		if (this.getDefaultValue().isNull()) {
			return CONSTRAINT_NO_DEFAULT.getValue("{minimumValue}", this.getMinimumValue().toString(), "{maximumValue}",
					this.getMaximumValue().toString());
		} else {
			return CONSTRAINT_DEFAULT.getValue("{minimumValue}", this.getMinimumValue().toString(), "{maximumValue}",
					this.getMaximumValue().toString(), "{defaultValue}", this.getDefaultValue().toString());
		}
	}

	@Override
	public String userGetCurrentValue() {
		return this.getCurrentValue().toString();
	}

	/**
	 * Set the current value of this parameter to the value represented by the given
	 * string. If the string is empty, the current value is set to the default
	 * value, otherwise the current value is set to the result of parsing the string
	 * as a float.
	 * 
	 * Replaces the decimal separator character as present in the
	 * internationalization files with a dot before parsing for international
	 * support. Also supports the internationalized names of special values positive
	 * infinity, negative infinity and NaN.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or the expected format for
	 *                                  float is not met.
	 * @see Float#parseFloat(String)
	 */
	@Override
	public void userSetCurrentValue(String value) {
		if (value.equals(CANCEL.getValue())) {
			throw new CancelledException();
		}
		if (value.isEmpty()) {
			if (!this.getDefaultValue().isNull()) {
				this.reset();
			} else {
				throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value));
			}
		} else {
			this.setCurrentValue(new InternationalizedFloat(value));
		}
	}

}
