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

package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import core.Generator;
import core.Terrain;

/**
 * This is the driver class of the application.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.1
 * @since 0.1
 *
 */
public class Main {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * Current version of this software.
	 */
	public static final String VERSION = "0.1";
	/**
	 * Copyright years.
	 */
	public static final String COPYRIGHT = "©2019";
	/**
	 * Reader the input is obtained from.
	 */
	private static BufferedReader INPUT_READER;
	/**
	 * Whether the program is currently running. When set to false, the main program
	 * loop will exit once the current iteration is done.
	 */
	private static boolean RUNNING = true;

	////////////////////////////////////////////////////////////////////////////////
	// Main method

	public static void main(String[] arguments) {
		try {
			printIntroduction();
			if(arguments.length == 0) {
				INPUT_READER = new BufferedReader(new InputStreamReader(System.in));
			} else {
				// TODO: open file whose location is passed as an argument
			}
			// Main loop
			while (RUNNING) {
				String command = input();
				switch (command) {
				case "help":
					printHelp();
					break;
				case "generate":
					generate();
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
	// I/O methods

	public static void output(final String string) {
		System.out.println(string);
	}

	public static String input() throws IOException {
		return INPUT_READER.readLine();
	}

	////////////////////////////////////////////////////////////////////////////////
	// User actions

	public static void printIntroduction() {
		output("TECTONIC SQUARES");
		output(VERSION);
		output(COPYRIGHT);
		output("");
		output("Type \"help\" for help.");
	}

	public static void printHelp() {
		output("Type \"help\" for help.");
		output("Type \"generate\" to generate a world.");
		output("Type \"exit\" to quit.");
	}

	public static void generate() throws IOException {
		Generator generator = new Generator();
		
		output("Input tectonic plate size:");
		String plateSize = input();
		generator.setPlateSize(Integer.parseInt(plateSize));
		
		output("Input width in number of plates:");
		String numberOfPlatesX = input();
		generator.setNumberOfPlatesX(Integer.parseInt(numberOfPlatesX));

		output("Input height in number of plates:");
		String numberOfPlatesY = input();
		generator.setNumberOfPlatesY(Integer.parseInt(numberOfPlatesY));
		
		Terrain terrain = generator.generate();
		TerrainUtil.printTerrain(terrain);
	}

	public static void exit() {
		RUNNING = false;
	}

}
