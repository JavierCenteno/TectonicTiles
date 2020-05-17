/*
 * Main.java
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

/**
 * This is the driver class of the application.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.2
 *
 */
public class Main {

	////////////////////////////////////////////////////////////////////////////////
	// Program info

	/**
	 * Name of this software.
	 */
	public static final String NAME = "Tectonic Tiles";
	/**
	 * Current version of this software.
	 */
	public static final String VERSION = "0.3";
	/**
	 * Copyright years.
	 */
	public static final String COPYRIGHT = "2020";

	////////////////////////////////////////////////////////////////////////////////
	// Program modes

	/**
	 * The different modes this software can run in.
	 * 
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static enum Mode {
		/**
		 * Console mode where the user interacts with this software through the command
		 * line.
		 */
		CONSOLE,
		/**
		 * Graphical mode where the user interacts with this software through an user
		 * interface window.
		 */
		GRAPHICAL,
		/**
		 * Test mode where tests are run.
		 */
		TEST
	}

	/**
	 * The mode this software is running in.
	 * 
	 * @see Main.Mode
	 */
	private static Mode MODE;

	/**
	 * Get the mode this software is running in.
	 * 
	 * @return The mode this software is running in.
	 */
	public static Mode mode() {
		return Main.MODE;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Main method

	/**
	 * This software's entry point. Use the arguments to choose which mode to run
	 * the software in. Runs the graphical mode by default.
	 * 
	 * @param arguments Arguments.
	 */
	public static void main(String[] arguments) {
		// set the program's mode
		// set mode to the default mode, then check if it should be anything else
		// graphical is the default mode
		Main.MODE = Mode.GRAPHICAL;
		if (arguments.length >= 1) {
			switch (arguments[0]) {
			case "cl":
				Main.MODE = Mode.CONSOLE;
				break;
			case "gui":
				Main.MODE = Mode.GRAPHICAL;
				break;
			case "test":
				Main.MODE = Mode.TEST;
				break;
			default:
				System.out.println("Option not supported. Options are \"gui\" and \"cl\".");
				break;
			}
		}
		// run a version of the program depending on the program's mode
		switch (Main.MODE) {
		case CONSOLE:
			ConsoleMode.run();
			break;
		case GRAPHICAL:
			GraphicalMode.run();
			break;
		case TEST:
			TestMode.run();
			break;
		}
	}

}
