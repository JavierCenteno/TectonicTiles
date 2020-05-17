/*
 * ConsoleMode.java
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

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import core.Crease;
import io.CreaseConfigurer;
import io.GenerationConfigurer;
import io.TerrainConfigurer;
import parameter.ConsoleHelper;
import parameter.configuration.ConfigurationConfigurer;
import parameter.i18n.InternationalizedString;
import parameter.parameter.CancelledException;
import parameter.parameter.FileParameter;
import parameter.parameter.LoadingException;
import parameter.parameter.OptionParameter;
import parameter.parameter.OptionParameter.InternationalizedOption;
import random.RandomGenerator;
import random.Xorshift64StarGenerator;

/**
 * This is the driver class for the console mode of the application.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 *
 */
public class ConsoleMode {

	////////////////////////////////////////////////////////////////////////////////
	// Program fields

	/**
	 * Console helper used to access console functions.
	 */
	private static ConsoleHelper CONSOLE;
	/**
	 * Whether the program is currently running. When set to false, the main program
	 * loop will exit once the current iteration is done.
	 */
	private static boolean RUNNING;
	/**
	 * Configurer for terrain generation.
	 */
	private static GenerationConfigurer GENERATION_CONFIGURER;

	////////////////////////////////////////////////////////////////////////////////
	// Internationalization fields

	private static final InternationalizedString PROGRAM_NAME = new InternationalizedString("program.name");
	private static final InternationalizedString PROGRAM_VERSION = new InternationalizedString("program.version");
	private static final InternationalizedString PROGRAM_COPYRIGHT = new InternationalizedString("program.copyright");
	private static final InternationalizedString PROGRAM_LOGO = new InternationalizedString("program.logo");
	private static final InternationalizedString EXIT_PROCESS_MESSAGE = new InternationalizedString(
			"console.command.exit.process");
	private static final InternationalizedString EXPORT_SUCCESS_MESSAGE = new InternationalizedString(
			"console.command.export.success");
	private static final InternationalizedString GENERATE_SUCCESS_MESSAGE = new InternationalizedString(
			"console.command.generate.success");
	private static final InternationalizedString HELP_MESSAGE = new InternationalizedString("console.commands");
	private static final InternationalizedString IMPORT_SUCCESS_MESSAGE = new InternationalizedString(
			"console.command.import.success");
	private static final InternationalizedString LICENSE_LOGO = new InternationalizedString("program.license.logo");
	private static final InternationalizedString LICENSE_MESSAGE = new InternationalizedString(
			"console.command.license.info");
	private static final InternationalizedString PRINT_SUCCESS_MESSAGE = new InternationalizedString(
			"console.command.print.success");
	private static final InternationalizedString ERROR_NO_TERRAIN = new InternationalizedString(
			"console.command.error.noTerrain");

	////////////////////////////////////////////////////////////////////////////////
	// Main method

	/**
	 * Run the console mode as an entry point.
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
		// initialize console
		CONSOLE = new ConsoleHelper(System.in, System.out);
		try {
			// initialize configuration
			ConfigurationConfigurer.getConfiguration().load();
			// initialize program fields
			RUNNING = true;
			// main loop
			printIntroduction();
			GENERATION_CONFIGURER = new GenerationConfigurer("generation");
			OptionParameter<String> commands = new OptionParameter<>("console.command");
			commands.addOption(new InternationalizedOption<String>("console.command.configure", "configure"));
			commands.addOption(new InternationalizedOption<String>("console.command.exit", "exit"));
			commands.addOption(new InternationalizedOption<String>("console.command.export", "export"));
			commands.addOption(new InternationalizedOption<String>("console.command.generate", "generate"));
			commands.addOption(new InternationalizedOption<String>("console.command.help", "help"));
			commands.addOption(new InternationalizedOption<String>("console.command.import", "import"));
			commands.addOption(new InternationalizedOption<String>("console.command.license", "license"));
			commands.addOption(new InternationalizedOption<String>("console.command.print", "print"));
			while (RUNNING) {
				try {
					CONSOLE.readParameter(commands);
					switch (commands.getCurrentValue()) {
					case "configure":
						configure();
						break;
					case "exit":
						exit();
						break;
					case "export":
						exportTerrain();
						break;
					case "generate":
						generate();
						break;
					case "help":
						printHelp();
						break;
					case "import":
						importTerrain();
						break;
					case "license":
						printLicense();
						break;
					case "print":
						printTerrain();
						break;
					}
				} catch (CancelledException cancelledException) {
					CONSOLE.writeLine(cancelledException.getLocalizedMessage());
				} catch (Exception exception) {
					CONSOLE.writeLine(exception.getLocalizedMessage());
				}
			}
		} catch (LoadingException exception) {
			CONSOLE.writeLine(exception.getLocalizedMessage());
			CONSOLE.writeLine("Press enter to close");
			CONSOLE.readLine();
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// User actions

	private static void printIntroduction() {
		CONSOLE.writeLine(PROGRAM_LOGO.getValue());
		CONSOLE.writeLine();
		CONSOLE.writeLine(PROGRAM_NAME.getValue());
		CONSOLE.writeLine(PROGRAM_VERSION.getValue("{version}", Main.VERSION));
		CONSOLE.writeLine(PROGRAM_COPYRIGHT.getValue("{year}", Main.COPYRIGHT));
		CONSOLE.writeLine();
		printHelp();
	}

	private static void printHelp() {
		CONSOLE.writeLine(HELP_MESSAGE.getValue());
	}

	private static void printLicense() {
		CONSOLE.writeLine(LICENSE_LOGO.getValue());
		CONSOLE.writeLine();
		CONSOLE.writeLine(LICENSE_MESSAGE.getValue());
	}

	private static void generate() {
		OptionParameter<TerrainConfigurer<?>> terrainTypeParameter = GENERATION_CONFIGURER.getTerrainTypeParameter();
		CONSOLE.readParameter(terrainTypeParameter);
		TerrainConfigurer<?> terrainTypeConfigurer = terrainTypeParameter.getCurrentValue();
		CONSOLE.readConfigurer(terrainTypeConfigurer);

		OptionParameter<CreaseConfigurer<?>> creaseTypeParameter = GENERATION_CONFIGURER.getCreaseTypeParameter();
		CONSOLE.readParameter(creaseTypeParameter);
		CreaseConfigurer<?> creaseTypeConfigurer = creaseTypeParameter.getCurrentValue();
		CONSOLE.readConfigurer(creaseTypeConfigurer);

		Crease crease = creaseTypeConfigurer.generate();
		RandomGenerator randomGenerator = new Xorshift64StarGenerator(
				terrainTypeConfigurer.getSeed().getCurrentValue().getValue());

		terrainTypeConfigurer.generate(randomGenerator, crease);

		CONSOLE.writeLine(GENERATE_SUCCESS_MESSAGE.getValue());
	}

	private static void printTerrain() {
		if (GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getTerrain() == null) {
			CONSOLE.writeLine(ERROR_NO_TERRAIN.getValue());
			return;
		}

		// Get image output file
		FileParameter fileParameter = new FileParameter("console.command.print.imageOutputFile", null, false, true,
				false, false, false, ImageIO.getWriterFormatNames());
		CONSOLE.readParameter(fileParameter);

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

		// Done
		CONSOLE.writeLine(PRINT_SUCCESS_MESSAGE.getValue());
	}

	private static void importTerrain() {
		// Select the type of terrain to parse
		CONSOLE.readParameter(GENERATION_CONFIGURER.getTerrainTypeParameter());

		// Get input file
		FileParameter fileParameter = new FileParameter("console.command.import.inputFile", null, true, false, true,
				false, true, GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getFormatNames());
		CONSOLE.readParameter(fileParameter);

		// Input
		try (InputStream fileInputStream = fileParameter.getInputStream()) {
			GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().importTerrain(fileParameter.getFormat(),
					fileInputStream);
		} catch (IOException e) {
			// Shouldn't throw an exception
			e.printStackTrace();
		}

		// Done
		CONSOLE.writeLine(IMPORT_SUCCESS_MESSAGE.getValue());
	}

	private static void exportTerrain() {
		if (GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getTerrain() == null) {
			CONSOLE.writeLine(ERROR_NO_TERRAIN.getValue());
			return;
		}

		// Get output file
		FileParameter fileParameter = new FileParameter("console.command.export.outputFile", null, false, true, false,
				false, false, GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().getFormatNames());
		CONSOLE.readParameter(fileParameter);

		// Output
		try (OutputStream fileOutputStream = fileParameter.getOutputStream()) {
			GENERATION_CONFIGURER.getTerrainTypeParameter().getCurrentValue().exportTerrain(fileParameter.getFormat(),
					fileOutputStream);
		} catch (IOException e) {
			// Shouldn't throw an exception
			e.printStackTrace();
		}

		// Done
		CONSOLE.writeLine(EXPORT_SUCCESS_MESSAGE.getValue());
	}

	private static void configure() {
		// This updates the parameters but doesn't save them to the configuration file
		CONSOLE.readConfigurer(ConfigurationConfigurer.CONFIGURATION);
		ConfigurationConfigurer.CONFIGURATION.save();
	}

	private static void exit() {
		CONSOLE.writeLine(EXIT_PROCESS_MESSAGE.getValue());
		RUNNING = false;
	}

}
