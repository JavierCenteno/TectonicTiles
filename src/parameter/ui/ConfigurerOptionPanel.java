/*
 * ConfigurerOptionPanel.java
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

import java.awt.Window;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import parameter.i18n.InternationalizedString;
import parameter.parameter.Configurer;
import parameter.parameter.OptionParameter;

public class ConfigurerOptionPanel extends JPanel {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString IO_PROMPT = new InternationalizedString("io.prompt");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private JFrame frame;
	private OptionParameter<? extends Configurer> configurerOptions;
	private JLabel nameLabel;
	private JPanel configurerPanel;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	public ConfigurerOptionPanel(JFrame frame, OptionParameter<? extends Configurer> configurerOptions) {
		super();
		this.frame = frame;
		this.configurerOptions = configurerOptions;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.nameLabel = new JLabel(configurerOptions.userGetName());
		this.add(this.nameLabel);
		this.add(new ConfigurerOptionParameterPanel(this.configurerOptions));
		this.setConfigurer(this.configurerOptions.getCurrentValue());
	}

	////////////////////////////////////////////////////////////////////////////////
	// Inner classes

	/**
	 * 
	 *
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public class ConfigurerOptionParameterPanel extends OptionParameterPanel {

		public ConfigurerOptionParameterPanel(OptionParameter<?> parameter) {
			super(parameter);
		}

		@Override
		public void setValue() {
			super.setValue();
			this.updateConfigurer();
		}

		@Override
		public void reset() {
			super.reset();
			this.updateConfigurer();
		}

		private void updateConfigurer() {
			Configurer currentValue = ConfigurerOptionPanel.this.configurerOptions.getCurrentValue();
			ConfigurerOptionPanel.this.setConfigurer(currentValue);
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	private void setConfigurer(Configurer configurer) {
		if (this.configurerPanel != null) {
			this.remove(this.configurerPanel);
		}
		if (configurer != null) {
			this.configurerPanel = new ConfigurerPanel(frame, configurer);
			this.add(this.configurerPanel);
		}
		// update the frame
		frame.revalidate();
	}

}
