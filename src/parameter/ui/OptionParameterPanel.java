/*
 * OptionParameterPanel.java
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parameter.i18n.InternationalizedString;
import parameter.parameter.OptionParameter;
import parameter.parameter.Parameter;

public class OptionParameterPanel extends JPanel implements ActionListener, ItemListener {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	public static final int CELL_WIDTH = 32 * 3;
	public static final int CELL_HEIGHT = 8 * 3;
	private static final InternationalizedString IO_PROMPT = new InternationalizedString("io.prompt");
	private static final InternationalizedString IO_RESET = new InternationalizedString("io.reset");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private OptionParameter<?> parameter;
	private JLabel nameLabel;
	private JComboBox<String> inputField;
	private JButton resetButton;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	public OptionParameterPanel(OptionParameter<?> parameter) {
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

		// Second row

		// Input field
		this.inputField = new JComboBox<>();
		this.inputField.addItemListener(this);
		for (String name : this.parameter.getOptionNames()) {
			this.inputField.addItem(name);
		}
		try {
			this.inputField.setSelectedItem(this.parameter.getCurrentValueOption().userGetName());
		} catch (IllegalArgumentException exception) {
			this.inputField.setSelectedItem(null);
		}
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

		// Reset button
		if (parameter.getDefaultValue() != null) {
			this.resetButton = new JButton(IO_RESET.getValue());
			this.resetButton.setActionCommand("reset");
			this.resetButton.addActionListener(this);
			GridBagConstraints resetButtonGridBagConstraints = new GridBagConstraints();
			resetButtonGridBagConstraints.gridx = 1;
			resetButtonGridBagConstraints.gridy = 1;
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
	 * @see OptionParameter#setCurrentValue(OptionParameter.Option)
	 */
	public void setValue() {
		this.parameter.userSetCurrentValue((String) this.inputField.getSelectedItem());
	}

	/**
	 * Reset the value of the underlying parameter and update this panel
	 * accordingly.
	 * 
	 * @see Parameter#reset()
	 */
	public void reset() {
		this.parameter.reset();
		try {
			this.inputField.setSelectedItem(this.parameter.getCurrentValueOption().userGetName());
		} catch (IllegalArgumentException exception) {
			this.inputField.setSelectedItem(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "reset":
			// There's no reset button if default value is null
			// so this condition should always be true
			if (this.parameter.getDefaultValue() != null) {
				this.reset();
			}
			break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		switch (event.getStateChange()) {
		case ItemEvent.SELECTED:
			this.setValue();
			break;
		}
	}

}
