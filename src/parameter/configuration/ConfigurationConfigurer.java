/*
 * ConfigurationConfigurer.java
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

package parameter.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;
import java.util.Properties;

import parameter.i18n.InternationalizationParameter;
import parameter.i18n.InternationalizationParameter.InternationalizationOption;
import parameter.parameter.Configurer;
import parameter.parameter.FileParameter;
import parameter.parameter.LoadingException;

/**
 * Configurer which handles the software's configuration.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class ConfigurationConfigurer extends Configurer {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * Charset used to save configuration and internationalization data.
	 */
	public static final Charset CHARSET = StandardCharsets.UTF_8;
	/**
	 * Path to the configuration folder used by the software.
	 */
	public static final String CONFIGURATION_PATH = "config";
	/**
	 * Path to the internationalization folder used by the software.
	 */
	public static final String I18N_PATH = "i18n";
	/**
	 * The current configuration configurer used by the software. Implemented as a
	 * singleton so other classes have direct access to the configuration.
	 */
	public static final ConfigurationConfigurer CONFIGURATION = new ConfigurationConfigurer("configuration");
	/**
	 * Comments that are added at the beginning of the configuration file when a
	 * configuration file is saved.
	 */
	public static final String COMMENTS = "################################################################################################################################\n"
			+ "#                                                                                                                              #\n"
			+ "# configuration.properties                                                                                                     #\n"
			+ "#                                                                                                                              #\n"
			+ "# This file is part of Tectonic Tiles.                                                                                         #\n"
			+ "# Tectonic Tiles is a random terrain generator inspired by plate tectonics.                                                    #\n"
			+ "# Copyright (C) 2020 Javier Centeno Vega                                                                                       #\n"
			+ "#                                                                                                                              #\n"
			+ "# This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as #\n"
			+ "# published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.           #\n"
			+ "#                                                                                                                              #\n"
			+ "# This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty  #\n"
			+ "# of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.                 #\n"
			+ "#                                                                                                                              #\n"
			+ "# You should have received a copy of the GNU General Public License along with this program. If not, see                       #\n"
			+ "# <http://www.gnu.org/licenses/>.                                                                                              #\n"
			+ "#                                                                                                                              #\n"
			+ "################################################################################################################################\n"
			+ "\n"
			+ "################################################################################################################################\n"
			+ "#                                                                                                                              #\n"
			+ "# This file contains the configuration data used by the software.                                                              #\n"
			+ "#                                                                                                                              #\n"
			+ "################################################################################################################################\n";

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The file parameter containing the configuration file.
	 */
	private FileParameter configurationFile;
	private final InternationalizationParameter internationalizationParameter;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Construct a new configuration configurer with default parameters.
	 * 
	 * @param nameKey The internationalization key for the name of the configurer.
	 */
	public ConfigurationConfigurer(String nameKey) {
		super(nameKey);
		this.configurationFile = new FileParameter(nameKey, null, true, true, true, false, true);
		// Language parameter
		this.internationalizationParameter = new InternationalizationParameter("configuration.language");
		this.getParameters().add(this.internationalizationParameter);
		// Language identifiers consist of an ISO 639-3 language code
		// plus an optional ISO 3166 alpha 3 country code separated by a dash
		// if it's necessary to specify a region.
		this.internationalizationParameter
				.addOption(new InternationalizationOption<>("English (United States)", "eng-USA"));
		this.internationalizationParameter.addOption(new InternationalizationOption<>("Español neutro", "esp"));
		// Set default value
		this.internationalizationParameter.setDefaultValue("eng-USA");
	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Get the configuration configurer used by the software.
	 * 
	 * @return The configuration configurer used by the software.
	 */
	public static ConfigurationConfigurer getConfiguration() {
		return CONFIGURATION;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Get the internationalization parameter.
	 * 
	 * @return The internationalization parameter.
	 */
	public InternationalizationParameter getInternationalizationParameter() {
		return this.internationalizationParameter;
	}

	/**
	 * Loads all the current settings from the configuration file.
	 */
	public void load() {
		Properties configuration;
		try {
			File configurationFileObject = new File(
					ConfigurationConfigurer.CONFIGURATION_PATH + File.separator + "configuration.properties");
			this.configurationFile.setCurrentValue(configurationFileObject);
			configuration = new Properties();
			configuration.load(new InputStreamReader(this.configurationFile.getInputStream(), CHARSET));
		} catch (Exception exception) {
			// If an exception happens here, there's nothing that can be done
			throw new LoadingException("ERROR LOADING CONFIGURATION");
		}
		// Set current value according to the stored configuration
		this.internationalizationParameter.setCurrentValue(configuration.getProperty("language"));
	}

	/**
	 * Saves all the current settings to the configuration file.
	 */
	public void save() {
		Properties properties = new Properties();
		properties.put("language", this.internationalizationParameter.getCurrentValue());
		Writer writer = new OutputStreamWriter(this.configurationFile.getOutputStream(), CHARSET);
		try {
			writer.write(COMMENTS);
			writer.write("\n");
			for (Entry<?, ?> entry : properties.entrySet()) {
				writer.write(entry.getKey().toString() + " = " + entry.getValue().toString());
			}
			writer.write("\n");
			writer.flush();
		} catch (IOException exception) {
			// If an exception happens here, there's nothing that can be done
			throw new LoadingException("ERROR SAVING CONFIGURATION");
		}
	}

}
