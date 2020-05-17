/*
 * InternationalizationParameter.java
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

import java.io.File;
import java.io.InputStreamReader;
import java.util.Properties;

import parameter.configuration.ConfigurationConfigurer;
import parameter.parameter.FileParameter;
import parameter.parameter.LoadingException;
import parameter.parameter.OptionParameter;
import parameter.parameter.OptionParameter.FixedOption;

/**
 * A parameter representing a program's language, including functionality
 * related to internationalization.
 * 
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizationParameter extends OptionParameter<String> {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The file parameter containing the properties file. Used to perform file
	 * checks on the internationalization files loaded.
	 */
	private FileParameter propertiesFile;
	/**
	 * The properties containing the internationalization data.
	 */
	private Properties properties;

	////////////////////////////////////////////////////////////////////////////////
	// Nested classes

	/**
	 * An option similar to fixed option but ignoring checks to load
	 * internationalization without the internationalization required for those
	 * checks.
	 * 
	 * @param <T> The type of the value.
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 * @see FixedOption
	 *
	 */
	public static class InternationalizationOption<T> extends Option<T> {

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
		public InternationalizationOption(String name, T value) {
			this.name = name;
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

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new internationalization parameter with no default value and an
	 * empty list of options.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 */
	public InternationalizationParameter(String nameKey) {
		super(nameKey);
		this.propertiesFile = new FileParameter(nameKey, null, true, false, true, false, true);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public void setCurrentValueOption(Option<String> currentValueOption) {
		super.setCurrentValueOption(currentValueOption);
		loadInternationalization();
	}

	@Override
	public void setCurrentValue(String currentValue) {
		super.setCurrentValue(currentValue);
		loadInternationalization();
	}

	/**
	 * Loads the internationalization corresponding to the language code of this
	 * parameter and stores it in the properties parameter.
	 */
	private void loadInternationalization() {
		try {
			if (this.getCurrentValue() != null) {
				// load the properties file corresponding to the current language
				String languageCode = this.getCurrentValue();
				File propertiesFileObject = new File(
						ConfigurationConfigurer.I18N_PATH + File.separator + languageCode + ".properties");
				this.propertiesFile.setCurrentValue(propertiesFileObject);
				this.properties = new Properties();
				this.properties.load(
						new InputStreamReader(this.propertiesFile.getInputStream(), ConfigurationConfigurer.CHARSET));
			}
		} catch (Exception exception) {
			// If an exception happens here, there's nothing that can be done
			throw new LoadingException("ERROR LOADING INTERNATIONALIZATION");
		}
	}

	/**
	 * Searches for the property with the key in the current language's properties
	 * file.
	 * 
	 * @param key A key.
	 * @return The property for the given key.
	 * @see Properties#getProperty(String)
	 */
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

}
