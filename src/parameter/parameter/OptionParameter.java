/*
 * OptionParameter.java
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
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import parameter.i18n.Internationalized;
import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is one of a set of options.
 *
 * @param <T> The type of the possible values of the parameter.
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class OptionParameter<T> extends Parameter<T> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString LIST_SEPARATOR = new InternationalizedString("meta.listSeparator");
	private static final InternationalizedString NULL = new InternationalizedString("parameter.option.null");
	private static final InternationalizedString CONSTRAINT = new InternationalizedString(
			"parameter.option.constraint");
	private static final InternationalizedString EXCEPTION_NO_SUCH_VALUE = new InternationalizedString(
			"parameter.option.exception.noSuchValue");
	private static final InternationalizedString EXCEPTION_AMBIGUOUS = new InternationalizedString(
			"parameter.option.exception.ambiguous");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The collection of options for the values this parameter can have. The options
	 * must have unique names.
	 */
	private final Collection<Option<T>> options;
	/**
	 * The option representing the current value of this parameter. May be null.
	 */
	private Option<T> currentValue;
	/**
	 * The option representing the default value of this parameter. May be null.
	 */
	private Option<T> defaultValue;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new option parameter with an empty collection of options, using
	 * the null option as the default and current value.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 * @see NullOption
	 */
	public OptionParameter(String nameKey) {
		/*
		 * Initialize the default and current values to the null option, skipping setter
		 * checks. Normally setting a value to an option that is not in the collection
		 * of options would cause an exception.
		 */
		super(nameKey, null);
		this.options = new ArrayList<>();
		this.currentValue = new NullOption<>();
		this.defaultValue = new NullOption<>();
	}

	/**
	 * Constructs a new option parameter with the given options as the collection of
	 * options, using the null option as the default and current value.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 * @param options The options of the parameter.
	 * @see NullOption
	 */
	@SafeVarargs
	public OptionParameter(String nameKey, Option<T>... options) {
		/*
		 * Initialize the default and current values to the null option, skipping setter
		 * checks. Normally setting a value to an option that is not in the collection
		 * of options would cause an exception.
		 */
		super(nameKey, null);
		this.options = new ArrayList<>();
		for (Option<T> option : options) {
			this.addOption(option);
		}
		this.currentValue = new NullOption<>();
		this.defaultValue = new NullOption<>();
	}

	/**
	 * Constructs a new option parameter with the given collection of options, using
	 * the null option as the default and current value.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 * @param options The options of the parameter.
	 * @see NullOption
	 */
	public OptionParameter(String nameKey, Collection<Option<T>> options) {
		/*
		 * Initialize the default and current values to the null option, skipping setter
		 * checks. Normally setting a value to an option that is not in the collection
		 * of options would cause an exception.
		 */
		super(nameKey, null);
		this.options = options;
		this.currentValue = new NullOption<>();
		this.defaultValue = new NullOption<>();
	}

	/**
	 * Constructs a new option parameter with the given collection of options, using
	 * the given option as the default and current value.
	 * 
	 * @param nameKey      The internationalization key for the name of the
	 *                     parameter.
	 * @param options      The options of the parameter.
	 * @param defaultValue The option to be used as the default and current value of
	 *                     the parameter.
	 */
	public OptionParameter(String nameKey, Collection<Option<T>> options, Option<T> defaultValue) {
		super(nameKey);
		this.options = options;
		// set with the method to trigger the checks in the setters
		this.setDefaultValueOption(defaultValue);
		this.setCurrentValueOption(defaultValue);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Classes

	/**
	 * A value that an option parameter can take with an unique name among the
	 * options of the parameter.
	 *
	 * @param <T> The type of the value.
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static abstract class Option<T> {

		/**
		 * Get the name of this option.
		 * 
		 * @return The name of this option.
		 */
		@Internationalized
		public abstract String userGetName();

		/**
		 * Get the value represented by this option.
		 * 
		 * @return The value represented by this option.
		 */
		public abstract T getValue();

		@Override
		public boolean equals(Object object) {
			if (object instanceof Option) {
				Object value = ((Option<?>) object).getValue();
				if (this.getValue() == null) {
					return value == null;
				} else {
					return this.getValue().equals(value);
				}
			} else {
				return false;
			}
		}

	}

	/**
	 * An option whose name depends on internationalization.
	 *
	 * @param <T> The type of the value.
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static class InternationalizedOption<T> extends Option<T> {

		////////////////////////////////////////////////////////////////////////////////
		// Fields

		/**
		 * The name of this option. Must be unique among the options of the parameter.
		 */
		@Internationalized
		private InternationalizedString name;
		/**
		 * The value represented by this option.
		 */
		private T value;

		////////////////////////////////////////////////////////////////////////////////
		// Constructors

		/**
		 * Constructs a new option with a given name that represents a given value.
		 * 
		 * @param nameKey The internationalization key for the name of the option.
		 * @param value   The value represented by the option.
		 */
		public InternationalizedOption(String nameKey, T value) {
			this.name = new InternationalizedString(nameKey);
			if (this.name.getValue().equals(CANCEL.getValue())) {
				throw new IllegalArgumentException("Options can't have a name matching the cancel instruction.");
			}
			this.value = value;
		}

		////////////////////////////////////////////////////////////////////////////////
		// Methods

		@Internationalized
		@Override
		public String userGetName() {
			return this.name.getValue();
		}

		@Override
		public T getValue() {
			return this.value;
		}

	}

	/**
	 * An option whose name doesn't depend on internationalization.
	 * 
	 * @param <T> The type of the value.
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static class FixedOption<T> extends Option<T> {

		////////////////////////////////////////////////////////////////////////////////
		// Fields

		/**
		 * The name of this option. Must be unique among the options of the parameter.
		 */
		private String name;
		/**
		 * The value represented by this option.
		 */
		private T value;

		////////////////////////////////////////////////////////////////////////////////
		// Constructors

		/**
		 * Constructs a new option with a given name that represents a given value.
		 * 
		 * @param name  The name of the option.
		 * @param value The value represented by the option.
		 */
		public FixedOption(String name, T value) {
			this.name = name;
			if (this.name.equals(CANCEL.getValue())) {
				throw new IllegalArgumentException("Options can't have a name matching the cancel instruction.");
			}
			this.value = value;
		}

		////////////////////////////////////////////////////////////////////////////////
		// Methods

		@Override
		public String userGetName() {
			return this.name;
		}

		@Override
		public T getValue() {
			return this.value;
		}

	}

	/**
	 * An option that represents the null value.
	 * 
	 * Option parameter values that aren't set are initialized to the null option so
	 * that the methods will always return a valid option with a valid
	 * internationalized name.
	 * 
	 * Note, however, that the null option is subject to the same checks as other
	 * options, that is, once an option is initialized, its values can't be set to
	 * the null option unless the null option is in the collection of options of the
	 * parameter. Although the value can be reset through the reset() method since
	 * the null option is still the default value.
	 * 
	 * @param <T> The type of the value.
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 * @see Parameter#reset()
	 *
	 */
	public static class NullOption<T> extends Option<T> {

		////////////////////////////////////////////////////////////////////////////////
		// Constructors

		/**
		 * Constructs a null option
		 */
		public NullOption() {
		}

		////////////////////////////////////////////////////////////////////////////////
		// Methods

		@Internationalized
		@Override
		public String userGetName() {
			return NULL.getValue();
		}

		@Override
		public T getValue() {
			return null;
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Methods

	/**
	 * Adds an option to the set of options of this parameter.
	 * 
	 * @param option An option.
	 * @throws IllegalArgumentException If there already exists an option with the
	 *                                  name of the given option or there already
	 *                                  exists an option with a value that equals
	 *                                  that of the given option.
	 */
	@Internationalized
	public void addOption(Option<T> option) {
		for (Option<T> o : this.options) {
			if (o.userGetName().equals(option.userGetName())) {
				throw new IllegalArgumentException("The option " + option.userGetName()
						+ " has the same name as the existing option " + o.userGetName() + ".");
			}
			if (Objects.equals(o.getValue(), option.getValue())) {
				throw new IllegalArgumentException("The option " + option.userGetName()
						+ " has the same value as the existing option " + o.userGetName() + ".");
			}
		}
		this.options.add(option);
	}

	/**
	 * Get the collection of the names of options.
	 * 
	 * @return A collection of the names of all options.
	 */
	@Internationalized
	public Collection<String> getOptionNames() {
		Collection<String> optionNames = new ArrayList<>();
		for (Option<T> option : this.options) {
			optionNames.add(option.userGetName());
		}
		return optionNames;
	}

	/**
	 * Get the option representing the current value of this parameter.
	 * 
	 * @return The option representing the current value of this parameter.
	 */
	public Option<T> getCurrentValueOption() {
		return this.currentValue;
	}

	/**
	 * Set the current value of this parameter to the given option. Throws an
	 * exception if the option is not one of the possible options.
	 * 
	 * If null is given, the method will attempt to set the current value to the
	 * null option.
	 * 
	 * Note, however, that the null option is subject to the same checks as other
	 * options, that is, once an option is initialized, its values can't be set to
	 * the null option unless the null option is in the collection of options of the
	 * parameter. Although the value can be reset through the reset() method since
	 * the null option is still the default value.
	 * 
	 * @param currentValueOption A value.
	 * @throws IllegalArgumentException If the option is not one of the possible
	 *                                  values.
	 * @see Parameter#reset()
	 */
	public void setCurrentValueOption(Option<T> currentValueOption) {
		if (currentValueOption == null) {
			currentValueOption = new NullOption<T>();
		}
		if (!this.options.contains(currentValueOption)) {
			throw new IllegalArgumentException(
					"The current value \"" + currentValueOption.userGetName() + "\" is not one of the allowed values: [\""
							+ String.join("\", \"", this.getOptionNames()) + "\"].");
		}
		this.currentValue = currentValueOption;
		super.setCurrentValue(currentValueOption.getValue());
	}

	/**
	 * Set the current value of this parameter to the option matching the given
	 * value according to the equals() method.
	 * 
	 * @param currentValue A value.
	 * @throws IllegalArgumentException If no option matches the given value.
	 */
	public void setCurrentValue(T currentValue) {
		boolean set = false;
		for (Option<T> option : this.options) {
			if (Objects.equals(currentValue, option.getValue())) {
				this.currentValue = option;
				super.setCurrentValue(currentValue);
				set = true;
			}
		}
		if (!set) {
			throw new IllegalArgumentException("The current value is not one of the allowed values.");
		}
	}

	/**
	 * Get the option representing the default value of this parameter.
	 * 
	 * @return The option representing the default value of this parameter.
	 */
	public Option<T> getDefaultValueOption() {
		return this.defaultValue;
	}

	/**
	 * Set the default value of this parameter to the given option. Throws an
	 * exception if the option is not one of the possible options.
	 * 
	 * If null is given, the method will attempt to set the default value to the
	 * null option.
	 * 
	 * Note, however, that the null option is subject to the same checks as other
	 * options, that is, once an option is initialized, its values can't be set to
	 * the null option unless the null option is in the collection of options of the
	 * parameter. Although the value can be reset through the reset() method since
	 * the null option is still the default value.
	 * 
	 * @param defaultValueOption A value.
	 * @throws IllegalArgumentException If the option is not one of the possible
	 *                                  values.
	 * @see Parameter#reset()
	 */
	public void setDefaultValueOption(Option<T> defaultValueOption) {
		if (defaultValueOption == null) {
			defaultValueOption = new NullOption<T>();
		}
		if (!this.options.contains(defaultValueOption)) {
			throw new IllegalArgumentException(
					"The default value \"" + defaultValueOption.userGetName() + "\" is not one of the allowed values: [\""
							+ String.join("\", \"", this.getOptionNames()) + "\"].");
		}
		this.defaultValue = defaultValueOption;
		super.setDefaultValue(defaultValueOption.getValue());
	}

	/**
	 * Set the default value of this parameter to the option matching the given
	 * value according to the equals() method.
	 * 
	 * @param defaultValue A value.
	 * @throws IllegalArgumentException If no option matches the given value.
	 */
	public void setDefaultValue(T defaultValue) {
		boolean set = false;
		for (Option<T> option : this.options) {
			if (Objects.equals(defaultValue, option.getValue())) {
				this.defaultValue = option;
				super.setDefaultValue(defaultValue);
				set = true;
			}
		}
		if (!set) {
			throw new IllegalArgumentException("The default value is not one of the allowed values.");
		}
	}

	/**
	 * Get the options of this parameter.
	 * 
	 * @return The options of this parameter. The collection returned is not
	 *         modifiable as any changes to the collection of options should be made
	 *         through the appropriate methods.
	 */
	public Collection<Option<T>> getOptions() {
		return Collections.unmodifiableCollection(this.options);
	}

	/**
	 * Get the option whose name starts with the given string if there's only one
	 * option matching that criteria.
	 * 
	 * Since the name of the options depends on internationalization, the behavior
	 * of this method for a given input may depend on internationalization.
	 * 
	 * @param name A string.
	 * @return An option whose name starts with the given string.
	 * @throws IllegalArgumentException If there are multiple options or no options
	 *                                  whose name starts with the given string.
	 */
	@Internationalized
	public Option<T> getOptionMatchingName(String name) {
		Option<T> matchingOption = null;
		for (Option<T> option : this.options) {
			if (option.userGetName().equals(name)) {
				// If a value is exactly the same, select it automatically
				// This prevents ambiguous results when a value starts by the name of another
				// value
				matchingOption = option;
				break;
			} else if (option.userGetName().startsWith(name)) {
				// We consider a possible value to be the one selected
				// if it starts with the given input
				if (matchingOption != null) {
					// If there was a value already selected and there's a matching value
					// it's ambiguous which value it's referring to
					throw new IllegalArgumentException(EXCEPTION_AMBIGUOUS.getValue("{value}", name, "{option1}",
							matchingOption.userGetName(), "{option2}", option.userGetName()));
				} else {
					matchingOption = option;
				}
			}
		}
		if (matchingOption == null) {
			// There are no values starting with the given value
			throw new IllegalArgumentException(EXCEPTION_NO_SUCH_VALUE.getValue("{value}", name));
		}
		return matchingOption;
	}

	////////////////////////////////////////////////////////////////////////////////
	// I/O methods

	@Override
	public String userGetConstraints() {
		return CONSTRAINT.getValue("{options}", String.join(LIST_SEPARATOR.getValue(), this.getOptionNames()));
	}

	@Internationalized
	@Override
	public String userGetCurrentValue() {
		return this.getCurrentValueOption().userGetName();
	}

	/**
	 * Set the current value of this parameter to the value represented by the given
	 * string. If the string is empty, the current value is set to the default
	 * value, otherwise the current value is set to the option whose name starts
	 * with the given string if there's only one option matching that criteria.
	 * 
	 * Since the name of the options depends on internationalization, the behavior
	 * of this method for a given input may depend on internationalization.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or if there are multiple
	 *                                  options or no options whose name starts with
	 *                                  the given string.
	 */
	@Override
	public void userSetCurrentValue(String value) {
		if (value.equals(CANCEL.getValue())) {
			throw new CancelledException();
		}
		if (value.isEmpty()) {
			if (!(this.getDefaultValue() instanceof NullOption)) {
				this.reset();
			} else {
				throw new IllegalArgumentException(EXCEPTION_NO_SUCH_VALUE.getValue("{value}", value));
			}
		} else {
			this.setCurrentValueOption(getOptionMatchingName(value));
		}
	}

}
