/*
 * BigDecimalParameter.java
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

import java.math.BigDecimal;

import parameter.i18n.InternationalizedBigDecimal;
import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is a big decimal with maximum and minimum values.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class BigDecimalParameter extends Parameter<InternationalizedBigDecimal> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT = new InternationalizedString(
			"parameter.bigDecimal.constraint.noDefault");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT_ABOVE_MINIMUM = new InternationalizedString(
			"parameter.bigDecimal.constraint.noDefaultAboveMinimum");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT_BELOW_MAXIMUM = new InternationalizedString(
			"parameter.bigDecimal.constraint.noDefaultBelowMaximum");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT_ABOVE_MINIMUM_BELOW_MAXIMUM = new InternationalizedString(
			"parameter.bigDecimal.constraint.noDefaultAboveMinimumBelowMaximum");
	private static final InternationalizedString CONSTRAINT_DEFAULT = new InternationalizedString(
			"parameter.bigDecimal.constraint.default");
	private static final InternationalizedString CONSTRAINT_DEFAULT_ABOVE_MINIMUM = new InternationalizedString(
			"parameter.bigDecimal.constraint.defaultAboveMinimum");
	private static final InternationalizedString CONSTRAINT_DEFAULT_BELOW_MAXIMUM = new InternationalizedString(
			"parameter.bigDecimal.constraint.defaultBelowMaximum");
	private static final InternationalizedString CONSTRAINT_DEFAULT_ABOVE_MINIMUM_BELOW_MAXIMUM = new InternationalizedString(
			"parameter.bigDecimal.constraint.defaultAboveMinimumBelowMaximum");
	private static final InternationalizedString EXCEPTION_BELOW_MINIMUM = new InternationalizedString(
			"parameter.bigDecimal.exception.belowMinimum");
	private static final InternationalizedString EXCEPTION_ABOVE_MAXIMUM = new InternationalizedString(
			"parameter.bigDecimal.exception.aboveMaximum");
	private static final InternationalizedString EXCEPTION_NULL = new InternationalizedString(
			"parameter.bigDecimal.exception.null");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"parameter.bigDecimal.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The minimum value of this parameter (inclusive).
	 */
	private InternationalizedBigDecimal minimumValue;
	/**
	 * The maximum value of this parameter (inclusive).
	 */
	private InternationalizedBigDecimal maximumValue;
	/**
	 * Whether this parameter can be null.
	 */
	private boolean canBeNull;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new big decimal parameter with the given name, default value,
	 * minimum value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to the minimum value that a byte can have.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to the maximum value that a byte can have.
	 * @param canBeNull    Whether this parameter can be null.
	 */
	public BigDecimalParameter(String nameKey, InternationalizedBigDecimal defaultValue,
			InternationalizedBigDecimal minimumValue, InternationalizedBigDecimal maximumValue, boolean canBeNull) {
		super(nameKey);
		// set these first to avoid null pointer exceptions
		this.setMinimumValue(InternationalizedBigDecimal.of(null));
		this.setMaximumValue(InternationalizedBigDecimal.of(null));
		// set with the method to trigger the checks in the setters
		this.setCanBeNull(canBeNull);
		this.setMinimumValue(minimumValue);
		this.setMaximumValue(maximumValue);
		this.setDefaultValue(defaultValue);
		this.setCurrentValue(defaultValue);
	}

	/**
	 * Constructs a new big decimal parameter with the given name, default value,
	 * minimum value and maximum value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param defaultValue The default and current value of the parameter.
	 * @param minimumValue The minimum value of the parameter (inclusive). If null,
	 *                     it'll be set to the minimum value that a byte can have.
	 * @param maximumValue The maximum value of the parameter (inclusive). If null,
	 *                     it'll be set to the maximum value that a byte can have.
	 * @param canBeNull    Whether this parameter can be null.
	 */
	public BigDecimalParameter(String nameKey, BigDecimal defaultValue, BigDecimal minimumValue,
			BigDecimal maximumValue, boolean canBeNull) {
		this(nameKey, new InternationalizedBigDecimal(defaultValue), new InternationalizedBigDecimal(minimumValue),
				new InternationalizedBigDecimal(maximumValue), canBeNull);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Checks

	/**
	 * Checks if the given value is below the minimum value and throws an exception
	 * if that's the case.
	 * 
	 * @param value A value.
	 */
	private void checkBelowMinimum(InternationalizedBigDecimal value) {
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
	private void checkAboveMaximum(InternationalizedBigDecimal value) {
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
	public void setCurrentValue(InternationalizedBigDecimal currentValue) {
		if (currentValue == null) {
			currentValue = InternationalizedBigDecimal.of(null);
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
	 * @see BigDecimalParameter#setCurrentValue(InternationalizedBigDecimal)
	 */
	public void setCurrentValue(BigDecimal currentValue) {
		this.setCurrentValue(InternationalizedBigDecimal.of(currentValue));
	}

	/**
	 * Set the default value of this parameter to the given value. Throws an
	 * exception if the value is below the minimum value or above the maximum value.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(InternationalizedBigDecimal defaultValue) {
		if (defaultValue == null) {
			defaultValue = InternationalizedBigDecimal.of(null);
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
	 * @see BigDecimalParameter#setDefaultValue(InternationalizedBigDecimal)
	 */
	public void setDefaultValue(BigDecimal defaultValue) {
		this.setDefaultValue(InternationalizedBigDecimal.of(defaultValue));
	}

	/**
	 * Get the minimum value of this parameter (inclusive).
	 * 
	 * @return The minimum value of this parameter.
	 */
	public InternationalizedBigDecimal getMinimumValue() {
		return this.minimumValue;
	}

	/**
	 * Set the minimum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is above the maximum value.
	 * 
	 * @param minimumValue A value.
	 */
	public void setMinimumValue(InternationalizedBigDecimal minimumValue) {
		if (minimumValue == null) {
			minimumValue = InternationalizedBigDecimal.of(null);
		}
		if (!minimumValue.isNull()) {
			if (!this.maximumValue.isNull()) {
				checkAboveMaximum(minimumValue);
			}
		}
		this.minimumValue = minimumValue;
	}

	/**
	 * Set the minimum value of this parameter to the given value (inclusive).
	 * 
	 * @param minimumValue A value.
	 * @see BigDecimalParameter#setMinimumValue(InternationalizedBigDecimal)
	 */
	public void setMinimumValue(BigDecimal minimumValue) {
		this.setMinimumValue(InternationalizedBigDecimal.of(minimumValue));
	}

	/**
	 * Get the maximum value of this parameter (inclusive).
	 * 
	 * @return The maximum value of this parameter.
	 */
	public InternationalizedBigDecimal getMaximumValue() {
		return this.maximumValue;
	}

	/**
	 * Set the maximum value of this parameter to the given value (inclusive).
	 * Throws an exception if the value is below the minimum value.
	 * 
	 * @param maximumValue A value.
	 */
	public void setMaximumValue(InternationalizedBigDecimal maximumValue) {
		if (maximumValue == null) {
			maximumValue = InternationalizedBigDecimal.of(null);
		}
		if (!maximumValue.isNull()) {
			if (!this.minimumValue.isNull()) {
				checkBelowMinimum(maximumValue);
			}
		}
		this.maximumValue = maximumValue;
	}

	/**
	 * Set the maximum value of this parameter to the given value (inclusive).
	 * 
	 * @param maximumValue A value.
	 * @see BigDecimalParameter#setMaximumValue(InternationalizedBigDecimal)
	 */
	public void setMaximumValue(BigDecimal maximumValue) {
		this.setMaximumValue(InternationalizedBigDecimal.of(maximumValue));
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
			if (this.getMaximumValue().isNull()) {
				if (this.getMinimumValue().isNull()) {
					return CONSTRAINT_NO_DEFAULT.getValue();
				} else {
					return CONSTRAINT_NO_DEFAULT_ABOVE_MINIMUM.getValue("{minimumValue}",
							this.getMinimumValue().toString());
				}
			} else {
				if (this.getMinimumValue().isNull()) {
					return CONSTRAINT_NO_DEFAULT_BELOW_MAXIMUM.getValue("{maximumValue}",
							this.getMaximumValue().toString());
				} else {
					return CONSTRAINT_NO_DEFAULT_ABOVE_MINIMUM_BELOW_MAXIMUM.getValue("{minimumValue}",
							this.getMinimumValue().toString(), "{maximumValue}", this.getMaximumValue().toString());
				}
			}
		} else {
			if (this.getMaximumValue().isNull()) {
				if (this.getMinimumValue().isNull()) {
					return CONSTRAINT_DEFAULT.getValue("{defaultValue}", this.getDefaultValue().toString());
				} else {
					return CONSTRAINT_DEFAULT_ABOVE_MINIMUM.getValue("{minimumValue}",
							this.getMinimumValue().toString(), "{defaultValue}", this.getDefaultValue().toString());
				}
			} else {
				if (this.getMinimumValue().isNull()) {
					return CONSTRAINT_DEFAULT_BELOW_MAXIMUM.getValue("{maximumValue}",
							this.getMaximumValue().toString(), "{defaultValue}", this.getDefaultValue().toString());
				} else {
					return CONSTRAINT_DEFAULT_ABOVE_MINIMUM_BELOW_MAXIMUM.getValue("{minimumValue}",
							this.getMinimumValue().toString(), "{maximumValue}", this.getMaximumValue().toString(),
							"{defaultValue}", this.getDefaultValue().toString());
				}
			}
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
	 * as a big decimal.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or the expected format for big
	 *                                  decimal is not met.
	 * @see BigDecimal#BigDecimal(String)
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
			this.setCurrentValue(new InternationalizedBigDecimal(value));
		}
	}

}
