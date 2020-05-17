/*
 * ShortParameter.java
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

import parameter.i18n.InternationalizedShort;
import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is a short with maximum and minimum values.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class ShortParameter extends Parameter<InternationalizedShort> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT = new InternationalizedString(
			"parameter.short.constraint.noDefault");
	private static final InternationalizedString CONSTRAINT_DEFAULT = new InternationalizedString(
			"parameter.short.constraint.default");
	private static final InternationalizedString EXCEPTION_BELOW_MINIMUM = new InternationalizedString(
			"parameter.short.exception.belowMinimum");
	private static final InternationalizedString EXCEPTION_ABOVE_MAXIMUM = new InternationalizedString(
			"parameter.short.exception.aboveMaximum");
	private static final InternationalizedString EXCEPTION_NULL = new InternationalizedString(
			"parameter.short.exception.null");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"parameter.short.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The minimum value of this parameter (inclusive).
	 */
	private InternationalizedShort minimumValue;
	/**
	 * The maximum value of this parameter (inclusive).
	 */
	private InternationalizedShort maximumValue;
	/**
	 * Whether this parameter can be null.
	 */
	private boolean canBeNull;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new short parameter with the given name, default value, minimum
	 * value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to the minimum value that a short can have.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to the maximum value that a short can have.
	 * @param canBeNull    Whether this parameter can be null.
	 */
	public ShortParameter(String nameKey, InternationalizedShort defaultValue, InternationalizedShort minimumValue,
			InternationalizedShort maximumValue, boolean canBeNull) {
		super(nameKey);
		// set these first to avoid null pointer exceptions
		this.setMinimumValue(InternationalizedShort.of(null));
		this.setMaximumValue(InternationalizedShort.of(null));
		// set with the method to trigger the checks in the setters
		this.setCanBeNull(canBeNull);
		this.setMinimumValue(minimumValue);
		this.setMaximumValue(maximumValue);
		this.setDefaultValue(defaultValue);
		this.setCurrentValue(defaultValue);
	}

	/**
	 * Constructs a new short parameter with the given name, default value, minimum
	 * value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to the minimum value that a short can have.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to the maximum value that a short can have.
	 * @param canBeNull    Whether this parameter can be null.
	 */
	public ShortParameter(String nameKey, Short defaultValue, Short minimumValue, Short maximumValue,
			boolean canBeNull) {
		this(nameKey, new InternationalizedShort(defaultValue), new InternationalizedShort(minimumValue),
				new InternationalizedShort(maximumValue), canBeNull);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Checks

	/**
	 * Checks if the given value is below the minimum value and throws an exception
	 * if that's the case.
	 * 
	 * @param value A value.
	 */
	private void checkBelowMinimum(InternationalizedShort value) {
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
	private void checkAboveMaximum(InternationalizedShort value) {
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
	public void setCurrentValue(InternationalizedShort currentValue) {
		if (currentValue == null) {
			currentValue = InternationalizedShort.of(null);
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
	 * @see ShortParameter#setCurrentValue(InternationalizedShort)
	 */
	public void setCurrentValue(short currentValue) {
		this.setCurrentValue(new InternationalizedShort(currentValue));
	}

	/**
	 * Set the default value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(InternationalizedShort defaultValue) {
		if (defaultValue == null) {
			defaultValue = InternationalizedShort.of(null);
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
	 * @see ShortParameter#setDefaultValue(InternationalizedShort)
	 */
	public void setDefaultValue(short defaultValue) {
		this.setDefaultValue(new InternationalizedShort(defaultValue));
	}

	/**
	 * Get the minimum value of this parameter (inclusive).
	 * 
	 * @return The minimum value of this parameter.
	 */
	public InternationalizedShort getMinimumValue() {
		return this.minimumValue;
	}

	/**
	 * Set the minimum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is above the maximum value.
	 * 
	 * @param minimumValue A value.
	 */
	public void setMinimumValue(InternationalizedShort minimumValue) {
		if (minimumValue == null) {
			minimumValue = InternationalizedShort.of(null);
		}
		if (minimumValue.isNull()) {
			// If no minimum value is provided, set to the minimum possible value
			this.minimumValue = new InternationalizedShort(Short.MIN_VALUE);
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
	 * @see ShortParameter#setMinimumValue(InternationalizedShort)
	 */
	public void setMinimumValue(short minimumValue) {
		this.setMinimumValue(new InternationalizedShort(minimumValue));
	}

	/**
	 * Get the maximum value of this parameter (inclusive).
	 * 
	 * @return The maximum value of this parameter.
	 */
	public InternationalizedShort getMaximumValue() {
		return this.maximumValue;
	}

	/**
	 * Set the maximum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is below the minimum value.
	 * 
	 * @param maximumValue A value.
	 */
	public void setMaximumValue(InternationalizedShort maximumValue) {
		if (maximumValue == null) {
			maximumValue = InternationalizedShort.of(null);
		}
		if (maximumValue.isNull()) {
			// If no maximum value is provided, set to the maximum possible value
			this.maximumValue = new InternationalizedShort(Short.MAX_VALUE);
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
	 * @see ShortParameter#setMaximumValue(InternationalizedShort)
	 */
	public void setMaximumValue(short maximumValue) {
		this.setMaximumValue(new InternationalizedShort(maximumValue));
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
	 * as a short.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or the expected format for
	 *                                  short is not met.
	 * @see Short#parseShort(String)
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
			this.setCurrentValue(new InternationalizedShort(value));
		}
	}

}
