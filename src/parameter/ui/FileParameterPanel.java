/*
 * FileParameterPanel.java
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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parameter.i18n.InternationalizedString;
import parameter.parameter.CancelledException;
import parameter.parameter.FileParameter;
import parameter.parameter.OptionParameter;
import parameter.parameter.Parameter;

public class FileParameterPanel extends JPanel implements ActionListener {

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

	private FileParameter parameter;
	private JFileChooser fileChooser;
	private JLabel nameLabel;
	private JLabel constraintLabel;
	private JLabel updateLabel;
	private JButton chooseButton;
	private JButton resetButton;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	public FileParameterPanel(FileParameter parameter) {
		super();
		this.parameter = parameter;
		this.fileChooser = new JFileChooser();

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

		// Update label
		this.updateLabel = new JLabel();
		GridBagConstraints updateLabelGridBagConstraints = new GridBagConstraints();
		updateLabelGridBagConstraints.gridx = 0;
		updateLabelGridBagConstraints.gridy = 1;
		updateLabelGridBagConstraints.anchor = GridBagConstraints.WEST;
		updateLabelGridBagConstraints.fill = GridBagConstraints.BOTH;
		this.updateLabel.setMinimumSize(new Dimension(cellWidth * 3, cellHeight));
		this.updateLabel.setPreferredSize(new Dimension(cellWidth * 3, cellHeight));
		this.updateLabel.setMaximumSize(new Dimension(cellWidth * 3, cellHeight));
		updateLabelGridBagConstraints.weightx = 4;
		updateLabelGridBagConstraints.weighty = 1;
		this.add(this.updateLabel, updateLabelGridBagConstraints);

		// Third row

		// Choose button
		this.chooseButton = new JButton(IO_ENTER.getValue());
		this.chooseButton.addActionListener(this);
		this.chooseButton.setActionCommand("choose");
		GridBagConstraints enterButtonGridBagConstraints = new GridBagConstraints();
		enterButtonGridBagConstraints.gridx = 0;
		enterButtonGridBagConstraints.gridy = 2;
		enterButtonGridBagConstraints.anchor = GridBagConstraints.WEST;
		enterButtonGridBagConstraints.fill = GridBagConstraints.NONE;
		this.chooseButton.setMinimumSize(new Dimension(cellWidth, cellHeight));
		this.chooseButton.setPreferredSize(new Dimension(cellWidth, cellHeight));
		this.chooseButton.setMaximumSize(new Dimension(cellWidth, cellHeight));
		enterButtonGridBagConstraints.weightx = 1;
		enterButtonGridBagConstraints.weighty = 1;
		this.add(this.chooseButton, enterButtonGridBagConstraints);

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
	 * @see OptionParameter#setCurrentValue(OptionParameter.Option)
	 */
	public void setValue() {
		try {
			if (this.parameter.getIsFile() && this.parameter.getIsDirectory()) {
				this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			} else if (this.parameter.getIsFile() && !this.parameter.getIsDirectory()) {
				this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			} else if (!this.parameter.getIsFile() && this.parameter.getIsDirectory()) {
				this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			}
			File value = this.fileChooser.getSelectedFile();
			parameter.setCurrentValue(value);
			// If no exception happened, set the info text to the success message
			this.updateLabel.setText(IO_SET_SUCCESS.getValue("{value}", parameter.userGetCurrentValue()));
		} catch (IllegalArgumentException exception) {
			// If an exception is thrown, set the info text to the exception's text
			this.updateLabel.setText(exception.getLocalizedMessage());
		} catch (CancelledException exception) {
			// If an exception is thrown, set the info text to the exception's text
			this.updateLabel.setText(exception.getLocalizedMessage());
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
		this.fileChooser.setSelectedFile(this.parameter.getCurrentValue());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "choose":
			int state = this.fileChooser.showOpenDialog(this);
			switch (state) {
			case JFileChooser.APPROVE_OPTION:
				this.setValue();
				break;
			case JFileChooser.CANCEL_OPTION:
				// Do nothing
				break;
			case JFileChooser.ERROR_OPTION:
				// Do nothing
				break;
			}
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
