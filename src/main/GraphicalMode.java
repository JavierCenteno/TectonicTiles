/*
 * GraphicalMode.java
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

package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Crease;
import io.CreaseConfigurer;
import io.GenerationConfigurer;
import io.TerrainConfigurer;
import parameter.configuration.ConfigurationConfigurer;
import parameter.i18n.InternationalizedString;
import parameter.parameter.FileParameter;
import parameter.ui.ConfigurerOptionPanel;
import parameter.ui.ConfigurerPanel;
import parameter.ui.ParameterPanel;
import random.RandomGenerator;
import random.Xorshift64StarGenerator;

/**
 * This is the driver class for the graphical mode of the application.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class GraphicalMode {

	////////////////////////////////////////////////////////////////////////////////
	// Program fields

	private static JFrame WINDOW;
	/**
	 * Configurer for terrain generation.
	 */
	private static GenerationConfigurer GENERATION_CONFIGURER;
	private static final CommandActionListener COMMAND_ACTION_LISTENER = new CommandActionListener();
	/**
	 * Panel containing the buttons that activate the main program functions.
	 */
	private static JPanel BUTTON_PANEL;
	private static final InternationalizedString PROGRAM_NAME = new InternationalizedString("program.name");
	private static final InternationalizedString GENERATE_COMMAND = new InternationalizedString(
			"console.command.generate");
	private static final InternationalizedString GENERATE_COMMAND_SUCCESS = new InternationalizedString(
			"console.command.generate.success");
	private static JButton GENERATE_BUTTON;
	private static final InternationalizedString PRINT_COMMAND = new InternationalizedString("console.command.print");
	private static JButton PRINT_BUTTON;
	private static final InternationalizedString PRINT_COMMAND_SUCCESS = new InternationalizedString(
			"console.command.print.success");
	private static final InternationalizedString IMPORT_COMMAND = new InternationalizedString("console.command.import");
	private static JButton IMPORT_BUTTON;
	private static final InternationalizedString IMPORT_COMMAND_SUCCESS = new InternationalizedString(
			"console.command.import.success");
	private static final InternationalizedString EXPORT_COMMAND = new InternationalizedString("console.command.export");
	private static JButton EXPORT_BUTTON;
	private static final InternationalizedString EXPORT_COMMAND_SUCCESS = new InternationalizedString(
			"console.command.export.success");
	private static final InternationalizedString CONFIGURE_COMMAND = new InternationalizedString(
			"console.command.configure");
	private static final InternationalizedString CONFIGURE_COMMAND_SAVE = new InternationalizedString(
			"console.command.configure.save");
	private static final InternationalizedString CONFIGURE_COMMAND_RESTART = new InternationalizedString(
			"console.command.configure.restart");
	private static JButton CONFIGURE_BUTTON;

	////////////////////////////////////////////////////////////////////////////////
	// Nested classes

	private static class CommandActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			switch (event.getActionCommand()) {
			case "configure":
				configure();
				break;
			case "export":
				exportTerrain();
				break;
			case "generate":
				generate();
				break;
			case "import":
				importTerrain();
				break;
			case "print":
				printTerrain();
				break;
			}
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Run the graphical mode as an entry point.
	 * 
	 * @param arguments Arguments.
	 */
	public static void main(String[] arguments) {
		run();
	}

	/**
	 * Run this program.
	 */
	public static void run() {
		// initialize configuration
		ConfigurationConfigurer.getConfiguration().load();

		// initialize window
		WINDOW = new JFrame();
		WINDOW.setIconImage(new ImageIcon("img\\LOGO_256.png").getImage());
		WINDOW.setTitle(PROGRAM_NAME.getValue());
		WINDOW.setMinimumSize(new Dimension(128 * 4, 128 * 3));
		WINDOW.setSize(new Dimension(256 * 4, 256 * 3));
		// WINDOW.setResizable(true);
		WINDOW.setLocationRelativeTo(null);
		WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		GENERATION_CONFIGURER = new GenerationConfigurer("generation");

		ConfigurerOptionPanel terrainConfigurer = new ConfigurerOptionPanel(WINDOW,
				GENERATION_CONFIGURER.getTerrainTypeParameter());
		mainPanel.add(terrainConfigurer);

		ConfigurerOptionPanel creaseConfigurer = new ConfigurerOptionPanel(WINDOW,
				GENERATION_CONFIGURER.getCreaseTypeParameter());
		mainPanel.add(creaseConfigurer);

		CONFIGURE_BUTTON = new JButton(CONFIGURE_COMMAND.getValue());
		CONFIGURE_BUTTON.addActionListener(COMMAND_ACTION_LISTENER);
		CONFIGURE_BUTTON.setActionCommand("configure");

		GENERATE_BUTTON = new JButton(GENERATE_COMMAND.getValue());
		GENERATE_BUTTON.addActionListener(COMMAND_ACTION_LISTENER);
		GENERATE_BUTTON.setActionCommand("generate");

		PRINT_BUTTON = new JButton(PRINT_COMMAND.getValue());
		PRINT_BUTTON.addActionListener(COMMAND_ACTION_LISTENER);
		PRINT_BUTTON.setActionCommand("print");

		IMPORT_BUTTON = new JButton(IMPORT_COMMAND.getValue());
		IMPORT_BUTTON.addActionListener(COMMAND_ACTION_LISTENER);
		IMPORT_BUTTON.setActionCommand("import");

		EXPORT_BUTTON = new JButton(EXPORT_COMMAND.getValue());
		EXPORT_BUTTON.addActionListener(COMMAND_ACTION_LISTENER);
		EXPORT_BUTTON.setActionCommand("export");

		BUTTON_PANEL = new JPanel();
		BUTTON_PANEL.setLayout(new BoxLayout(BUTTON_PANEL, BoxLayout.Y_AXIS));
		BUTTON_PANEL.add(CONFIGURE_BUTTON);
		BUTTON_PANEL.add(GENERATE_BUTTON);
		BUTTON_PANEL.add(IMPORT_BUTTON);
		mainPanel.add(BUTTON_PANEL);

		WINDOW.getContentPane().add(mainPanel);
		WINDOW.setVisible(true);
	}

	private static void generate() {
		TerrainConfigurer<?> terrainConfigurer = GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue();
		CreaseConfigurer<?> creaseConfigurer = GENERATION_CONFIGURER.getCreaseTypeParameter().getCurrentValue();

		Crease crease = creaseConfigurer.generate();
		RandomGenerator randomGenerator = new Xorshift64StarGenerator(
				terrainConfigurer.getSeed().getCurrentValue().getValue());
		terrainConfigurer.generate(randomGenerator, crease);

		JOptionPane.showMessageDialog(null, GENERATE_COMMAND_SUCCESS.getValue());

		BUTTON_PANEL.removeAll();
		BUTTON_PANEL.add(CONFIGURE_BUTTON);
		BUTTON_PANEL.add(GENERATE_BUTTON);
		BUTTON_PANEL.add(IMPORT_BUTTON);
		BUTTON_PANEL.add(PRINT_BUTTON);
		BUTTON_PANEL.add(EXPORT_BUTTON);

		WINDOW.revalidate();
	}

	private static void printTerrain() {
		FileParameter fileParameter = new FileParameter("console.command.print.imageOutputFile", null, false, true,
				false, false, false, ImageIO.getWriterFormatNames());

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(PRINT_COMMAND.getValue());
		FileFilter fileFilter = new FileNameExtensionFilter(fileParameter.userGetConstraints(),
				ImageIO.getWriterFormatNames());
		fileChooser.setFileFilter(fileFilter);

		while (true) {
			switch (fileChooser.showSaveDialog(null)) {
			case JFileChooser.ERROR_OPTION:
				// Handle error?
				break;
			case JFileChooser.CANCEL_OPTION:
				// If the user cancels, finish the method
				return;
			case JFileChooser.APPROVE_OPTION:
				// If the user approves, continue
				break;
			}
			File file = fileChooser.getSelectedFile();
			try {
				fileParameter.setCurrentValue(file);
			} catch (IllegalArgumentException exception) {
				JOptionPane.showMessageDialog(null, exception.getLocalizedMessage());
				continue;
			}
			break;
		}

		// Generate image
		RenderedImage image = GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().toImage();

		// Get image data
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, fileParameter.getFormat(), byteArrayOutputStream);
		} catch (IOException e) {
			// Shouldn't throw an exception
			e.printStackTrace();
		}
		byte[] imageData = byteArrayOutputStream.toByteArray();

		// Output
		try (OutputStream fileOutputStream = fileParameter.getOutputStream()) {
			fileOutputStream.write(imageData);
		} catch (IOException e) {
			// Shouldn't throw an exception
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, PRINT_COMMAND_SUCCESS.getValue());

		BUTTON_PANEL.removeAll();
		BUTTON_PANEL.add(CONFIGURE_BUTTON);
		BUTTON_PANEL.add(GENERATE_BUTTON);
		BUTTON_PANEL.add(IMPORT_BUTTON);
		BUTTON_PANEL.add(PRINT_BUTTON);
		BUTTON_PANEL.add(EXPORT_BUTTON);

		WINDOW.revalidate();
	}

	private static void importTerrain() {
		FileParameter fileParameter = new FileParameter("console.command.import.inputFile", null, true, false, true,
				false, true, GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getFormatNames());

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(IMPORT_COMMAND.getValue());
		FileFilter fileFilter = new FileNameExtensionFilter(fileParameter.userGetConstraints(),
				GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getFormatNames());
		fileChooser.setFileFilter(fileFilter);

		while (true) {
			switch (fileChooser.showSaveDialog(null)) {
			case JFileChooser.ERROR_OPTION:
				// Handle error?
				break;
			case JFileChooser.CANCEL_OPTION:
				// If the user cancels, finish the method
				return;
			case JFileChooser.APPROVE_OPTION:
				// If the user approves, continue
				break;
			}
			File file = fileChooser.getSelectedFile();
			try {
				fileParameter.setCurrentValue(file);
			} catch (IllegalArgumentException exception) {
				JOptionPane.showMessageDialog(null, exception.getLocalizedMessage());
				continue;
			}
			break;
		}

		JOptionPane.showMessageDialog(null, IMPORT_COMMAND_SUCCESS.getValue());

		// Input
		try (InputStream fileInputStream = fileParameter.getInputStream()) {
			GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().importTerrain(fileParameter.getFormat(),
					fileInputStream);
		} catch (IOException e) {
			// Shouldn't throw an exception
			e.printStackTrace();
		}

		BUTTON_PANEL.removeAll();
		BUTTON_PANEL.add(CONFIGURE_BUTTON);
		BUTTON_PANEL.add(GENERATE_BUTTON);
		BUTTON_PANEL.add(IMPORT_BUTTON);
		BUTTON_PANEL.add(PRINT_BUTTON);
		BUTTON_PANEL.add(EXPORT_BUTTON);

		WINDOW.revalidate();
	}

	private static void exportTerrain() {
		FileParameter fileParameter = new FileParameter("console.command.export.outputFile", null, false, true, false,
				false, false, GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getFormatNames());

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(EXPORT_COMMAND.getValue());
		FileFilter fileFilter = new FileNameExtensionFilter(fileParameter.userGetConstraints(),
				GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getFormatNames());
		fileChooser.setFileFilter(fileFilter);

		while (true) {
			switch (fileChooser.showSaveDialog(null)) {
			case JFileChooser.ERROR_OPTION:
				// Handle error?
				break;
			case JFileChooser.CANCEL_OPTION:
				// If the user cancels, finish the method
				return;
			case JFileChooser.APPROVE_OPTION:
				// If the user approves, continue
				break;
			}
			File file = fileChooser.getSelectedFile();
			try {
				fileParameter.setCurrentValue(file);
			} catch (IllegalArgumentException exception) {
				JOptionPane.showMessageDialog(null, exception.getLocalizedMessage());
				continue;
			}
			break;
		}

		JOptionPane.showMessageDialog(null, EXPORT_COMMAND_SUCCESS.getValue());

		// Output
		try (OutputStream fileOutputStream = fileParameter.getOutputStream()) {
			GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().exportTerrain(fileParameter.getFormat(),
					fileOutputStream);
		} catch (IOException e) {
			// Shouldn't throw an exception
			e.printStackTrace();
		}

		BUTTON_PANEL.removeAll();
		BUTTON_PANEL.add(CONFIGURE_BUTTON);
		BUTTON_PANEL.add(GENERATE_BUTTON);
		BUTTON_PANEL.add(IMPORT_BUTTON);
		BUTTON_PANEL.add(PRINT_BUTTON);
		BUTTON_PANEL.add(EXPORT_BUTTON);

		WINDOW.revalidate();
	}

	private static void configure() {
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				switch (event.getActionCommand()) {
				case "save":
					ConfigurationConfigurer.getConfiguration().save();
					JOptionPane.showMessageDialog(null, CONFIGURE_COMMAND_RESTART.getValue());
					break;
				}
			}

		};

		JFrame configurationWindow = new JFrame();
		configurationWindow.setTitle(CONFIGURE_COMMAND.getValue());
		configurationWindow.setMinimumSize(new Dimension(128 * 2, 128 * 1));
		configurationWindow.setSize(new Dimension(256 * 2, 256 * 1));
		configurationWindow.setLocationRelativeTo(WINDOW);

		configurationWindow.setLayout(new BoxLayout(configurationWindow.getContentPane(), BoxLayout.Y_AXIS));
		configurationWindow.add(new ConfigurerPanel(configurationWindow, ConfigurationConfigurer.getConfiguration()));

		JButton saveButton = new JButton(CONFIGURE_COMMAND_SAVE.getValue());
		saveButton.addActionListener(actionListener);
		saveButton.setActionCommand("save");
		saveButton.setMinimumSize(new Dimension(ParameterPanel.CELL_WIDTH, ParameterPanel.CELL_HEIGHT));
		saveButton.setPreferredSize(new Dimension(ParameterPanel.CELL_WIDTH, ParameterPanel.CELL_HEIGHT));
		saveButton.setMaximumSize(new Dimension(ParameterPanel.CELL_WIDTH, ParameterPanel.CELL_HEIGHT));
		configurationWindow.add(saveButton);

		configurationWindow.setVisible(true);
	}

}
