/*
 * DoubleParameter.java
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
import parameter.i18n.InternationalizedDouble;
import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is a double with maximum and minimum values.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class DoubleParameter extends Parameter<InternationalizedDouble> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT = new InternationalizedString(
			"parameter.double.constraint.noDefault");
	private static final InternationalizedString CONSTRAINT_DEFAULT = new InternationalizedString(
			"parameter.double.constraint.default");
	private static final InternationalizedString EXCEPTION_BELOW_MINIMUM = new InternationalizedString(
			"parameter.double.exception.belowMinimum");
	private static final InternationalizedString EXCEPTION_ABOVE_MAXIMUM = new InternationalizedString(
			"parameter.double.exception.aboveMaximum");
	private static final InternationalizedString EXCEPTION_NULL = new InternationalizedString(
			"parameter.double.exception.null");
	private static final InternationalizedString EXCEPTION_NOT_A_NUMBER = new InternationalizedString(
			"parameter.double.exception.notANumber");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"parameter.double.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The minimum value of this parameter (inclusive).
	 */
	private InternationalizedDouble minimumValue;
	/**
	 * The maximum value of this parameter (inclusive).
	 */
	private InternationalizedDouble maximumValue;
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
	 * Constructs a new double parameter with the given name, default value, minimum
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
	public DoubleParameter(String nameKey, InternationalizedDouble defaultValue, InternationalizedDouble minimumValue,
			InternationalizedDouble maximumValue, boolean canBeNull, boolean canBeNaN) {
		super(nameKey);
		// set these first to avoid null pointer exceptions
		this.setMinimumValue(InternationalizedDouble.of(null));
		this.setMaximumValue(InternationalizedDouble.of(null));
		// set with the method to trigger the checks in the setters
		this.setCanBeNull(canBeNull);
		this.setCanBeNaN(canBeNaN);
		this.setMinimumValue(minimumValue);
		this.setMaximumValue(maximumValue);
		this.setDefaultValue(defaultValue);
		this.setCurrentValue(defaultValue);
	}

	/**
	 * Constructs a new double parameter with the given name, default value, minimum
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
	public DoubleParameter(String nameKey, Double defaultValue, Double minimumValue, Double maximumValue,
			boolean canBeNull, boolean canBeNaN) {
		this(nameKey, new InternationalizedDouble(defaultValue), new InternationalizedDouble(minimumValue),
				new InternationalizedDouble(maximumValue), canBeNull, canBeNaN);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Checks

	/**
	 * Checks if the given value is below the minimum value and throws an exception
	 * if that's the case. Doesn't do anything if the given value is NaN.
	 * 
	 * @param value A value.
	 */
	private void checkBelowMinimum(InternationalizedDouble value) {
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
	private void checkAboveMaximum(InternationalizedDouble value) {
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
	public void setCurrentValue(InternationalizedDouble currentValue) {
		if (currentValue == null) {
			currentValue = InternationalizedDouble.of(null);
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
	 * @see DoubleParameter#setCurrentValue(InternationalizedDouble)
	 */
	public void setCurrentValue(double currentValue) {
		this.setCurrentValue(new InternationalizedDouble(currentValue));
	}

	/**
	 * Set the default value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value
	 * or if it's NaN and NaN is not allowed.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(InternationalizedDouble defaultValue) {
		if (defaultValue == null) {
			defaultValue = InternationalizedDouble.of(null);
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
	 * @see DoubleParameter#setDefaultValue(InternationalizedDouble)
	 */
	public void setDefaultValue(double defaultValue) {
		this.setDefaultValue(new InternationalizedDouble(defaultValue));
	}

	/**
	 * Get the minimum value of this parameter (inclusive).
	 * 
	 * @return The minimum value of this parameter.
	 */
	public InternationalizedDouble getMinimumValue() {
		return this.minimumValue;
	}

	/**
	 * Set the minimum value of this parameter (inclusive) to the given value.
	 * Throws an exception if the value is above the maximum value or NaN.
	 * 
	 * @param minimumValue A value.
	 */
	public void setMinimumValue(InternationalizedDouble minimumValue) {
		if (minimumValue == null) {
			minimumValue = InternationalizedDouble.of(null);
		}
		if (minimumValue.isNull()) {
			// If no minimum value is provided, set to the minimum possible value
			this.minimumValue = new InternationalizedDouble(Double.NEGATIVE_INFINITY);
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
	 * @see DoubleParameter#setMinimumValue(InternationalizedDouble)
	 */
	public void setMinimumValue(double minimumValue) {
		this.setMinimumValue(new InternationalizedDouble(minimumValue));
	}

	/**
	 * Get the maximum value of this parameter (inclusive).
	 * 
	 * @return The maximum value of this parameter.
	 */
	public InternationalizedDouble getMaximumValue() {
		return this.maximumValue;
	}

	/**
	 * Set the maximum value of this parameter (inclusive) to the given value.
	 * Throws an exception if the value is below the minimum value or NaN.
	 * 
	 * @param maximumValue A value.
	 */
	public void setMaximumValue(InternationalizedDouble maximumValue) {
		if (maximumValue == null) {
			maximumValue = InternationalizedDouble.of(null);
		}
		if (maximumValue.isNull()) {
			// If no maximum value is provided, set to the maximum possible value
			this.maximumValue = new InternationalizedDouble(Double.POSITIVE_INFINITY);
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
	 * @see DoubleParameter#setMaximumValue(InternationalizedDouble)
	 */
	public void setMaximumValue(double maximumValue) {
		this.setMaximumValue(new InternationalizedDouble(maximumValue));
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
	@Internationalized
	public String userGetCurrentValue() {
		return this.getCurrentValue().toString();
	}

	/**
	 * Set the current value of this parameter to the value represented by the given
	 * string. If the string is empty, the current value is set to the default
	 * value, otherwise the current value is set to the result of parsing the string
	 * as a double.
	 * 
	 * Replaces the decimal separator character as present in the
	 * internationalization files with a dot before parsing for international
	 * support. Also supports the internationalized names of special values positive
	 * infinity, negative infinity and NaN.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or the expected format for
	 *                                  double is not met.
	 * @see Double#parseDouble(String)
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
			this.setCurrentValue(new InternationalizedDouble(value));
		}
	}

}
