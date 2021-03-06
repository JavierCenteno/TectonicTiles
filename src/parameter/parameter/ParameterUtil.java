/*
 * ParameterUtil.java
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

import parameter.parameter.OptionParameter.FixedOption;
import parameter.parameter.OptionParameter.InternationalizedOption;

/**
 * A class containing static methods to instance parameters easily.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class ParameterUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Creates a new option parameter with the given name and the given options.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 * @param options The names and values for the returned options.
	 * @return An option parameter with the given name and options.
	 */
	public static OptionParameter<String> makeStringOptions(String nameKey, String... options) {
		OptionParameter<String> parameter = new OptionParameter<String>(nameKey);
		for (String option : options) {
			parameter.addOption(new FixedOption<String>(option, option));
		}
		return parameter;
	}

	/**
	 * Creates an option parameter where the options are yes and no.
	 * 
	 * @param nameKey The internationalization key for the name of the parameter.
	 * @return An option parameter where the options are yes and no.
	 */
	public static OptionParameter<Boolean> makeBooleanParameter(String nameKey) {
		OptionParameter<Boolean> parameter = new OptionParameter<Boolean>(nameKey);
		parameter.addOption(new InternationalizedOption<Boolean>("internationalized.boolean.true", true));
		parameter.addOption(new InternationalizedOption<Boolean>("internationalized.boolean.false", false));
		return parameter;
	}

}
