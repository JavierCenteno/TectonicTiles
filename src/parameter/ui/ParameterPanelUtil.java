/*
 * ParameterPanelUtil.java
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

package parameter.ui;

import javax.swing.JPanel;

import parameter.parameter.FileParameter;
import parameter.parameter.OptionParameter;
import parameter.parameter.Parameter;

public class ParameterPanelUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	public static JPanel getPanel(Parameter<?> parameter) {
		if (parameter instanceof OptionParameter) {
			return new OptionParameterPanel((OptionParameter<?>) parameter);
		} else if (parameter instanceof FileParameter) {
			return new FileParameterPanel((FileParameter) parameter);
		} else {
			return new ParameterPanel(parameter);
		}
	}

}
