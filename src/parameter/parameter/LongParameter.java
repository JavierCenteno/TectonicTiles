/*
 * LongParameter.java
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

import parameter.i18n.InternationalizedLong;
import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is a long with maximum and minimum values.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class LongParameter extends Parameter<InternationalizedLong> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT = new InternationalizedString(
			"parameter.long.constraint.noDefault");
	private static final InternationalizedString CONSTRAINT_DEFAULT = new InternationalizedString(
			"parameter.long.constraint.default");
	private static final InternationalizedString EXCEPTION_BELOW_MINIMUM = new InternationalizedString(
			"parameter.long.exception.belowMinimum");
	private static final InternationalizedString EXCEPTION_ABOVE_MAXIMUM = new InternationalizedString(
			"parameter.long.exception.aboveMaximum");
	private static final InternationalizedString EXCEPTION_NULL = new InternationalizedString(
			"parameter.long.exception.null");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"parameter.long.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The minimum value of this parameter (inclusive).
	 */
	private InternationalizedLong minimumValue;
	/**
	 * The maximum value of this parameter (inclusive).
	 */
	private InternationalizedLong maximumValue;
	/**
	 * Whether this parameter can be null.
	 */
	private boolean canBeNull;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new long parameter with the given name, default value, minimum
	 * value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to the minimum value that a long can have.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to the maximum value that a long can have.
	 * @param canBeNull    Whether this parameter can be null.
	 */
	public LongParameter(String nameKey, InternationalizedLong defaultValue, InternationalizedLong minimumValue,
			InternationalizedLong maximumValue, boolean canBeNull) {
		super(nameKey);
		// set these first to avoid null pointer exceptions
		this.setMinimumValue(InternationalizedLong.of(null));
		this.setMaximumValue(InternationalizedLong.of(null));
		// set with the method to trigger the checks in the setters
		this.setCanBeNull(canBeNull);
		this.setMinimumValue(minimumValue);
		this.setMaximumValue(maximumValue);
		this.setDefaultValue(defaultValue);
		this.setCurrentValue(defaultValue);
	}

	/**
	 * Constructs a new long parameter with the given name, default value, minimum
	 * value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to the minimum value that a long can have.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to the maximum value that a long can have.
	 * @param canBeNull    Whether this parameter can be null.
	 */
	public LongParameter(String nameKey, Long defaultValue, Long minimumValue, Long maximumValue, boolean canBeNull) {
		this(nameKey, new InternationalizedLong(defaultValue), new InternationalizedLong(minimumValue),
				new InternationalizedLong(maximumValue), canBeNull);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Checks

	/**
	 * Checks if the given value is below the minimum value and throws an exception
	 * if that's the case.
	 * 
	 * @param value A value.
	 */
	private void checkBelowMinimum(InternationalizedLong value) {
		if (value.compareTo(this.minimumValue) < 0) {
			throw new IllegalArgumentException(EXCEPTION_BELOW_MINIMUM.getValue("{value}", value.toString(),
					"{minimumValue}", this.minimumValue.toString()));
		}
	}

	/**
	 * Checks if the given value is above the maximum value and throws an exception
	 * if that's the case.
	 * 
	 * @param value A value.
	 */
	private void checkAboveMaximum(InternationalizedLong value) {
		if (value.compareTo(this.maximumValue) > 0) {
			throw new IllegalArgumentException(EXCEPTION_ABOVE_MAXIMUM.getValue("{value}", value.toString(),
					"{maximumValue}", this.maximumValue.toString()));
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Set the current value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value.
	 * 
	 * @param currentValue A value.
	 */
	public void setCurrentValue(InternationalizedLong currentValue) {
		if (currentValue == null) {
			currentValue = InternationalizedLong.of(null);
		}
		if (!this.canBeNull && currentValue.isNull()) {
			throw new IllegalArgumentException(EXCEPTION_NULL.getValue());
		} else if (!currentValue.isNull()) {
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
	 * @see LongParameter#setCurrentValue(InternationalizedLong)
	 */
	public void setCurrentValue(long currentValue) {
		this.setCurrentValue(new InternationalizedLong(currentValue));
	}

	/**
	 * Set the default value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(InternationalizedLong defaultValue) {
		if (defaultValue == null) {
			defaultValue = InternationalizedLong.of(null);
		}
		if (!this.canBeNull && defaultValue.isNull()) {
			throw new IllegalArgumentException(EXCEPTION_NULL.getValue());
		} else if (!defaultValue.isNull()) {
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
	 * @see LongParameter#setDefaultValue(InternationalizedLong)
	 */
	public void setDefaultValue(long defaultValue) {
		this.setDefaultValue(new InternationalizedLong(defaultValue));
	}

	/**
	 * Get the minimum value of this parameter (inclusive).
	 * 
	 * @return The minimum value of this parameter.
	 */
	public InternationalizedLong getMinimumValue() {
		return this.minimumValue;
	}

	/**
	 * Set the minimum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is above the maximum value.
	 * 
	 * @param minimumValue A value.
	 */
	public void setMinimumValue(InternationalizedLong minimumValue) {
		if (minimumValue == null) {
			minimumValue = InternationalizedLong.of(null);
		}
		if (minimumValue.isNull()) {
			// If no minimum value is provided, set to the minimum possible value
			this.minimumValue = new InternationalizedLong(Long.MIN_VALUE);
		} else {
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
	 * @see LongParameter#setMinimumValue(InternationalizedLong)
	 */
	public void setMinimumValue(long minimumValue) {
		this.setMinimumValue(new InternationalizedLong(minimumValue));
	}

	/**
	 * Get the maximum value of this parameter (inclusive).
	 * 
	 * @return The maximum value of this parameter.
	 */
	public InternationalizedLong getMaximumValue() {
		return this.maximumValue;
	}

	/**
	 * Set the maximum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is below the minimum value.
	 * 
	 * @param maximumValue A value.
	 */
	public void setMaximumValue(InternationalizedLong maximumValue) {
		if (maximumValue == null) {
			maximumValue = InternationalizedLong.of(null);
		}
		if (maximumValue.isNull()) {
			// If no maximum value is provided, set to the maximum possible value
			this.maximumValue = new InternationalizedLong(Long.MAX_VALUE);
		} else {
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
	 * @see LongParameter#setMaximumValue(InternationalizedLong)
	 */
	public void setMaximumValue(long maximumValue) {
		this.setMaximumValue(new InternationalizedLong(maximumValue));
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
	 * as a long.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or the expected format for
	 *                                  long is not met.
	 * @see Long#parseLong(String)
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
			this.setCurrentValue(new InternationalizedLong(value));
		}
	}

}
