/*
 * InternationalizedString.java
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

import parameter.configuration.ConfigurationConfigurer;

/**
 * A string whose value depends on internationalization.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class InternationalizedString {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The key used to look up this internationalized string's value.
	 */
	private String key;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Instances an internationalized string with the given key.
	 * 
	 * @param key The key used to look up this internationalized string's value.
	 * @throws NullPointerException If key is null.
	 */
	public InternationalizedString(String key) {
		if (key == null) {
			throw new NullPointerException("The internationalization key can't be null.");
		}
		this.key = key;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public int hashCode() {
		return this.getKey().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof InternationalizedString) {
			return this.key.equals(((InternationalizedString) object).key);
		} else {
			return false;
		}
	}

	/**
	 * Get the string representation of this value. By default, it just returns the
	 * result of calling the getValue() method, which depends on
	 * internationalization.
	 */
	@Internationalized
	@Override
	public String toString() {
		return this.getValue();
	}

	/**
	 * Get the key used to look up this internationalized string's value.
	 * 
	 * @return The key.
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Set the key used to look up this internationalized string's value to the
	 * given key.
	 * 
	 * @param key A key.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Get the value corresponding to the key in internationalization parameter used
	 * by this internationalized string.
	 * 
	 * @return The string resulting from looking up the key in the properties file
	 *         of the internationalization parameter.
	 */
	@Internationalized
	public String getValue() {
		String value = ConfigurationConfigurer.getConfiguration().getInternationalizationParameter().getProperty(key);
		return value != null ? value : key;
	}

	/**
	 * Get the value corresponding to the key in internationalization parameter used
	 * by this internationalized string with some substrings replaced. This method
	 * will replace every substring matching an even indexed string in the
	 * replacements parameter with the string after it.
	 * 
	 * @param replacements Strings to replace in the value.
	 * @return The string resulting from looking up the key in the properties file
	 *         of the internationalization parameter with some substrings replaced.
	 * @throws IllegalArgumentException If replacements doesn't have an even number
	 *                                  of strings.
	 * @see InternationalizedString#getValue()
	 * @see String#replace(CharSequence, CharSequence)
	 */
	@Internationalized
	public String getValue(String... replacements) {
		String value = this.getValue();
		if (replacements.length % 2 != 0) {
			throw new IllegalArgumentException("Replacements must contain an even number of strings");
		}
		for (int i = 0; i < replacements.length; i = i + 2) {
			value = value.replace(replacements[i], replacements[i + 1]);
		}
		return value;
	}

}
