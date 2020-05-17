/*
 * ConfigurerPanel.java
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

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parameter.parameter.Configurer;
import parameter.parameter.Multiparameter;
import parameter.parameter.OptionalParameter;
import parameter.parameter.Parameter;

public class ConfigurerPanel extends JPanel {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private Configurer configurer;
	private JLabel nameLabel;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	public ConfigurerPanel(JFrame frame, Configurer configurer) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.configurer = configurer;
		this.nameLabel = new JLabel(configurer.userGetName());
		this.add(nameLabel);
		for (Parameter<?> parameter : configurer.getParameters()) {
			this.add(ParameterPanelUtil.getPanel(parameter));
		}
		for (Multiparameter<?> multiparameter : configurer.getMultiparameters()) {
			this.add(new MultiparameterPanel(multiparameter));
		}
		for (OptionalParameter optionalParameter : configurer.getOptionalParameters()) {
			this.add(new OptionalParameterPanel(frame, optionalParameter));
		}
	}

}
