/*
 * ParameterPanel.java
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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import parameter.i18n.InternationalizedString;
import parameter.parameter.CancelledException;
import parameter.parameter.Parameter;

public class ParameterPanel extends JPanel implements ActionListener {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	public static final int CELL_WIDTH = 32 * 3;
	public static final int CELL_HEIGHT = 8 * 3;
	private static final InternationalizedString IO_PROMPT = new InternationalizedString("io.prompt");
	private static final InternationalizedString IO_SET_SUCCESS = new InternationalizedString("io.set.success");
	private static final InternationalizedString IO_ENTER = new InternationalizedString("io.enter");
	private static final InternationalizedString IO_RESET = new InternationalizedString("io.reset");
	private static final InternationalizedString IO_RESET_SUCCESS = new InternationalizedString("io.reset.success");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private Parameter<?> parameter;
	private JLabel nameLabel;
	private JLabel constraintLabel;
	private JTextField inputField;
	private JLabel updateLabel;
	private JButton enterButton;
	private JButton resetButton;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	public ParameterPanel(Parameter<?> parameter) {
		super();
		this.parameter = parameter;

		int cellWidth = CELL_WIDTH;
		int cellHeight = CELL_HEIGHT;

		this.setMinimumSize(new Dimension(cellWidth * 4, cellHeight * 3));
		this.setPreferredSize(new Dimension(cellWidth * 4, cellHeight * 3));
		this.setMaximumSize(new Dimension(cellWidth * 4, cellHeight * 3));

		GridBagLayout gridBagLayout = new GridBagLayout();

		this.setLayout(gridBagLayout);

		// First row

		// Name label
		String nameLabelText = IO_PROMPT.getValue("{parameter}", parameter.userGetName());
		this.nameLabel = new JLabel();
		this.nameLabel.setText(nameLabelText);
		this.nameLabel.setToolTipText(nameLabelText);
		GridBagConstraints nameLabelGridBagConstraints = new GridBagConstraints();
		nameLabelGridBagConstraints.gridx = 0;
		nameLabelGridBagConstraints.gridy = 0;
		nameLabelGridBagConstraints.anchor = GridBagConstraints.WEST;
		nameLabelGridBagConstraints.fill = GridBagConstraints.BOTH;
		this.nameLabel.setMinimumSize(new Dimension(cellWidth, cellHeight));
		this.nameLabel.setPreferredSize(new Dimension(cellWidth, cellHeight));
		this.nameLabel.setMaximumSize(new Dimension(cellWidth, cellHeight));
		nameLabelGridBagConstraints.weightx = 1;
		nameLabelGridBagConstraints.weighty = 1;
		this.add(this.nameLabel, nameLabelGridBagConstraints);

		// Constraint label
		String constraintLabelText = parameter.userGetConstraints();
		this.constraintLabel = new JLabel();
		this.constraintLabel.setText(constraintLabelText);
		this.constraintLabel.setToolTipText(constraintLabelText);
		GridBagConstraints constraintLabelGridBagConstraints = new GridBagConstraints();
		constraintLabelGridBagConstraints.gridx = 1;
		constraintLabelGridBagConstraints.gridy = 0;
		constraintLabelGridBagConstraints.anchor = GridBagConstraints.WEST;
		constraintLabelGridBagConstraints.fill = GridBagConstraints.BOTH;
		this.constraintLabel.setMinimumSize(new Dimension(cellWidth * 3, cellHeight));
		this.constraintLabel.setPreferredSize(new Dimension(cellWidth * 3, cellHeight));
		this.constraintLabel.setMaximumSize(new Dimension(cellWidth * 3, cellHeight));
		constraintLabelGridBagConstraints.weightx = 3;
		constraintLabelGridBagConstraints.weighty = 1;
		this.add(this.constraintLabel, constraintLabelGridBagConstraints);

		// Second row

		// Input field
		this.inputField = new JTextField();
		this.inputField.addFocusListener(new ParameterFocusListener());
		this.inputField.setText(parameter.userGetCurrentValue());
		GridBagConstraints inputFieldGridBagConstraints = new GridBagConstraints();
		inputFieldGridBagConstraints.gridx = 0;
		inputFieldGridBagConstraints.gridy = 1;
		inputFieldGridBagConstraints.anchor = GridBagConstraints.WEST;
		inputFieldGridBagConstraints.fill = GridBagConstraints.BOTH;
		this.inputField.setMinimumSize(new Dimension(cellWidth, cellHeight));
		this.inputField.setPreferredSize(new Dimension(cellWidth, cellHeight));
		this.inputField.setMaximumSize(new Dimension(cellWidth, cellHeight));
		inputFieldGridBagConstraints.weightx = 1;
		inputFieldGridBagConstraints.weighty = 1;
		this.add(this.inputField, inputFieldGridBagConstraints);

		// Update label
		this.updateLabel = new JLabel();
		GridBagConstraints updateLabelGridBagConstraints = new GridBagConstraints();
		updateLabelGridBagConstraints.gridx = 1;
		updateLabelGridBagConstraints.gridy = 1;
		updateLabelGridBagConstraints.anchor = GridBagConstraints.WEST;
		updateLabelGridBagConstraints.fill = GridBagConstraints.BOTH;
		this.updateLabel.setMinimumSize(new Dimension(cellWidth * 3, cellHeight));
		this.updateLabel.setPreferredSize(new Dimension(cellWidth * 3, cellHeight));
		this.updateLabel.setMaximumSize(new Dimension(cellWidth * 3, cellHeight));
		updateLabelGridBagConstraints.weightx = 3;
		updateLabelGridBagConstraints.weighty = 1;
		this.add(this.updateLabel, updateLabelGridBagConstraints);

		// Third row

		// Enter button
		this.enterButton = new JButton(IO_ENTER.getValue());
		this.enterButton.addActionListener(this);
		this.enterButton.setActionCommand("enter");
		GridBagConstraints enterButtonGridBagConstraints = new GridBagConstraints();
		enterButtonGridBagConstraints.gridx = 0;
		enterButtonGridBagConstraints.gridy = 2;
		enterButtonGridBagConstraints.anchor = GridBagConstraints.WEST;
		enterButtonGridBagConstraints.fill = GridBagConstraints.NONE;
		this.enterButton.setMinimumSize(new Dimension(cellWidth, cellHeight));
		this.enterButton.setPreferredSize(new Dimension(cellWidth, cellHeight));
		this.enterButton.setMaximumSize(new Dimension(cellWidth, cellHeight));
		enterButtonGridBagConstraints.weightx = 1;
		enterButtonGridBagConstraints.weighty = 1;
		this.add(this.enterButton, enterButtonGridBagConstraints);

		// Reset button
		if (parameter.getDefaultValue() != null) {
			this.resetButton = new JButton(IO_RESET.getValue());
			this.resetButton.addActionListener(this);
			this.resetButton.setActionCommand("reset");
			GridBagConstraints resetButtonGridBagConstraints = new GridBagConstraints();
			resetButtonGridBagConstraints.gridx = 1;
			resetButtonGridBagConstraints.gridy = 2;
			resetButtonGridBagConstraints.anchor = GridBagConstraints.WEST;
			resetButtonGridBagConstraints.fill = GridBagConstraints.NONE;
			this.resetButton.setMinimumSize(new Dimension(cellWidth, cellHeight));
			this.resetButton.setPreferredSize(new Dimension(cellWidth, cellHeight));
			this.resetButton.setMaximumSize(new Dimension(cellWidth, cellHeight));
			resetButtonGridBagConstraints.weightx = 1;
			resetButtonGridBagConstraints.weighty = 1;
			this.add(this.resetButton, resetButtonGridBagConstraints);
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Inner classes

	/**
	 * A focus listener for actions in parameter panel related to focus.
	 *
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public class ParameterFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent event) {
			/*
			 * If focus is gained, clear text field?
			 * 
			 * ParameterPanel.this.inputField.setText("");
			 * 
			 * Better do nothing instead, would be bad for the user to lose input in an
			 * accidental click.
			 */
		}

		@Override
		public void focusLost(FocusEvent e) {
			// If focus is lost, set value
			ParameterPanel.this.setValue();
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Get the value of the underlying parameter.
	 * 
	 * @return The value of the underlying parameter.
	 * @see Parameter#userGetCurrentValue()
	 */
	public String getValue() {
		return parameter.userGetCurrentValue();
	}

	/**
	 * Set the value of the underlying parameter and update this panel accordingly.
	 * 
	 * @see Parameter#userSetCurrentValue(String)
	 */
	public void setValue() {
		try {
			String value = this.inputField.getText();
			parameter.userSetCurrentValue(value);
			// If no exception happened, set the info text to the success message
			this.updateLabel.setText(IO_SET_SUCCESS.getValue("{value}", parameter.userGetCurrentValue()));
			// And update the input field just in case the correct way of printing the value
			// is different from the (valid) input
			this.inputField.setText(parameter.userGetCurrentValue());
		} catch (IllegalArgumentException exception) {
			// If an exception is thrown, set the info text to the exception's text
			this.updateLabel.setText(exception.getLocalizedMessage());
			// Set the input field's text back to the previous value
			this.inputField.setText(parameter.userGetCurrentValue());
		} catch (CancelledException exception) {
			// If an exception is thrown, set the info text to the exception's text
			this.updateLabel.setText(exception.getLocalizedMessage());
			// Set the input field's text back to the previous value
			this.inputField.setText(parameter.userGetCurrentValue());
		}
	}

	/**
	 * Reset the value of the underlying parameter and update this panel
	 * accordingly.
	 * 
	 * @see Parameter#reset()
	 */
	public void reset() {
		this.parameter.reset();
		this.updateLabel.setText(IO_RESET_SUCCESS.getValue("{value}", parameter.userGetCurrentValue()));
		this.inputField.setText(parameter.userGetCurrentValue());
	}

	public void setConstraintText(String text) {
		this.constraintLabel.setText(text);
	}

	public void setUpdateText(String text) {
		this.updateLabel.setText(text);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "enter":
			/*
			 * When focus is lost when pressing this button, setValue() will be called
			 * anyway because of ParameterFocusListener's focusLost(FocusEvent) method. This
			 * second call to setValue() is useless and will only change updateLabel's
			 * message, hiding its information from the user.
			 */
			// this.setValue();
			break;
		case "reset":
			// There's no reset button if default value is null
			// so this condition should always be true
			if (this.parameter.getDefaultValue() != null) {
				this.reset();
			}
			break;
		}
	}

}
