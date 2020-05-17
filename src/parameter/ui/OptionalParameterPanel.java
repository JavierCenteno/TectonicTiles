/*
 * OptionalParameterPanel.java
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
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import parameter.i18n.InternationalizedBoolean;
import parameter.i18n.InternationalizedString;
import parameter.parameter.OptionParameter;
import parameter.parameter.OptionalParameter;
import parameter.parameter.Parameter;

public class OptionalParameterPanel extends JPanel {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString IO_PROMPT = new InternationalizedString("io.prompt");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private JFrame frame;
	private OptionalParameter optionalParameter;
	/**
	 * The list of parameter panels in this panel.
	 */
	private Collection<JPanel> parameterPanels;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	public OptionalParameterPanel(JFrame frame, OptionalParameter optionalParameter) {
		super();
		this.frame = frame;
		this.optionalParameter = optionalParameter;
		this.parameterPanels = new ArrayList<>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new OptionalParameterDeterminerPanel(this.optionalParameter.getDeterminer()));
		this.setParameters(this.optionalParameter.getParameters());
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
	public class OptionalParameterDeterminerPanel extends OptionParameterPanel {

		public OptionalParameterDeterminerPanel(OptionParameter<Boolean> determiner) {
			super(determiner);
		}

		@Override
		public void setValue() {
			super.setValue();
			this.updateParameters();
		}

		@Override
		public void reset() {
			super.reset();
			this.updateParameters();
		}

		private void updateParameters() {
			if (OptionalParameterPanel.this.optionalParameter.getDeterminer().getCurrentValue()) {
				OptionalParameterPanel.this
						.setParameters(OptionalParameterPanel.this.optionalParameter.getParameters());
			} else {
				OptionalParameterPanel.this
				.setParameters();
			}
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	private void setParameters() {
		for (JPanel panel : this.parameterPanels) {
			this.remove(panel);
		}
		// update the frame
		frame.revalidate();
	}

	private void setParameters(Collection<Parameter<?>> parameters) {
		for (JPanel panel : this.parameterPanels) {
			this.remove(panel);
		}
		for (Parameter<?> parameter : parameters) {
			JPanel panel = ParameterPanelUtil.getPanel(parameter);
			this.parameterPanels.add(panel);
			this.add(panel);
		}
		// update the frame
		frame.revalidate();
	}

}
