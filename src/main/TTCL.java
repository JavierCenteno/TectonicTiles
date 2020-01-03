/*
 * This software is a random terrain generator inspired by plate tectonics.
 * Copyright (C) 2019 Javier Centeno Vega
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Generator;
import core.TerrainUtil;
import core.crease.SmoothStep;
import core.generator.SquareGenerator;
import util.ConsoleUtil;

/**
 * This is the driver class of the application.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 *
 */
public class TTCL {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * Name of this software.
	 */
	public static final String NAME = "Tectonic Tiles";
	/**
	 * Current version of this software.
	 */
	public static final String VERSION = "0.1";
	/**
	 * Copyright years.
	 */
	public static final String COPYRIGHT = "©2019";
	/**
	 * Whether the program is currently running. When set to false, the main program
	 * loop will exit once the current iteration is done.
	 */
	private static boolean RUNNING = true;
	/**
	 * Terrain generator being used to generate terrain.
	 */
	private static Generator<?> GENERATOR;

	////////////////////////////////////////////////////////////////////////////////
	// Main method

	public static void main(String[] arguments) {
		try {
			// If a file path is passed as an argument
			if (arguments.length != 0) {
				File commandsFile = new File(arguments[0]);
				ConsoleUtil.setInputStream(new FileInputStream(commandsFile));
				ConsoleUtil.setOutputStream(System.out);
			} else {
				ConsoleUtil.setInputStream(System.in);
				ConsoleUtil.setOutputStream(System.out);
			}
			printIntroduction();
			// Main loop
			while (RUNNING) {
				String command = ConsoleUtil.input().trim();
				switch (command) {
				case "help":
					printHelp();
					break;
				case "generate":
					generate();
					break;
				case "print":
					print();
					break;
				case "exit":
					exit();
					break;
				}
			}
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// User actions

	public static void printIntroduction() {
		ConsoleUtil.output("TECTONIC TILES");
		ConsoleUtil.output(VERSION);
		ConsoleUtil.output(COPYRIGHT);
		ConsoleUtil.output("");
		ConsoleUtil.output("Type \"help\" for help.");
	}

	public static void printHelp() {
		ConsoleUtil.output("Type \"help\" for help.");
		ConsoleUtil.output("Type \"generate\" to generate a terrain.");
		ConsoleUtil.output("Type \"print\" to print a generated terrain.");
		ConsoleUtil.output("Type \"exit\" to quit.");
	}

	public static void generate() throws IOException {
		// Get seed
		long seed = ConsoleUtil.readLongDefault("Enter seed or leave empty to pick a default value:", System.currentTimeMillis());

		// Initialize terrain
		ConsoleUtil.output("Enter the type of terrain desired");
		String terrainType = ConsoleUtil.readOption("Enter the type of terrain desired. Options are \"square\":", "square");
		switch (terrainType) {
		case "square":
			int plateSize = ConsoleUtil.readInteger("Enter tectonic plate size:");
			int numberOfPlatesX = ConsoleUtil.readInteger("Enter width in number of plates:");
			int numberOfPlatesY = ConsoleUtil.readInteger("Enter height in number of plates:");
			SquareGenerator generator = new SquareGenerator(seed);
			generator.setPlateSize(plateSize);
			generator.setNumberOfPlatesX(numberOfPlatesX);
			generator.setNumberOfPlatesY(numberOfPlatesY);
			GENERATOR = generator;
			break;
		default:
			throw new IllegalArgumentException("The type of terrain is not implemented.");
		}

		// Initialize crease
		String creaseType = ConsoleUtil.readOption("Enter the type of crease desired. Options are \"smoothstep\":", "smoothstep");
		switch (creaseType) {
		case "smoothstep":
			double heightFactor = ConsoleUtil.readDouble("Enter the height factor:");
			double heightPower = ConsoleUtil.readDouble("Enter the height power:");
			double radiusFactor = ConsoleUtil.readDouble("Enter the radius factor:");
			double radiusPower = ConsoleUtil.readDouble("Enter the radius power:");
			SmoothStep crease = new SmoothStep(heightFactor, heightPower, radiusFactor, radiusPower);
			GENERATOR.setCrease(crease);
			break;
		// TODO: add cases
		default:
			throw new IllegalArgumentException("The type of crease is not implemented.");
		}

		// Generate
		GENERATOR.generate();
		ConsoleUtil.output("Terrain generated successfully. Enter print to print the generated terrain.");
	}

	public static void print() {
		final File file = ConsoleUtil.readFile("Enter path of the file to print to:");
		RenderedImage image = TerrainUtil.printTerrain(GENERATOR.getTerrain());
		try {
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConsoleUtil.output("File printed successfully.");
	}

	public static void exit() {
		RUNNING = false;
	}

}
