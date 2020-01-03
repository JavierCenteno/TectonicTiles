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

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Utilities to read from an input stream and write to an output stream.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 *
 */
public class ConsoleUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * Input stream data is read from.
	 */
	private static InputStream IN;
	/**
	 * Output stream data is written to.
	 */
	private static PrintStream OUT;
	/**
	 * Reader for the input stream.
	 */
	private static BufferedReader IN_READER;

	////////////////////////////////////////////////////////////////////////////////
	// I/O stream methods

	/**
	 * Set the input stream.
	 * 
	 * @param stream An InputStream.
	 */
	public static void setInputStream(InputStream stream) {
		IN = stream;
		IN_READER = new BufferedReader(new InputStreamReader(IN));
	}

	/**
	 * Set the output stream.
	 * 
	 * @param stream A PrintStream.
	 */
	public static void setOutputStream(PrintStream stream) {
		OUT = stream;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Basic I/O methods

	/**
	 * Read a line from the input stream.
	 * 
	 * @return A line from the input stream.
	 * @throws IOException If an I/O error occurs.
	 */
	public static String input() throws IOException {
		return IN_READER.readLine();
	}

	/**
	 * Write a line to the output stream.
	 * 
	 * @param string A line to be written to the output stream.
	 */
	public static void output(final String string) {
		OUT.println(string);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Other I/O methods

	/**
	 * Reads a line from the input stream. Keeps trying until the input is one of
	 * the given options.
	 * 
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return The chosen option.
	 */
	public static String readOption(String promptMessage, String... options) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				for (int i = 0; i < options.length; ++i) {
					if (options[i].equals(input)) {
						return options[i];
					}
				}
				throw new IllegalArgumentException();
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not one of the valid options.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream. Keeps trying until the input is one of
	 * the enum values.
	 * 
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return An enum value.
	 */
	public static <E extends Enum<E>> Enum<E> readEnum(String promptMessage, Class<E> enumeration) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				Enum<E>[] enumerationConstants = enumeration.getEnumConstants();
				for (int i = 0; i < enumerationConstants.length; ++i) {
					if (enumerationConstants[i].toString().compareToIgnoreCase(input) == 0) {
						return enumerationConstants[i];
					}
				}
				throw new IllegalArgumentException();
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not one of the valid options.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream. Keeps trying until the input is one of
	 * the enum values. If the trimmed input is empty, returns the default value
	 * provided as an argument.
	 * 
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return An enum value.
	 */
	public static <E extends Enum<E>> Enum<E> readEnumDefault(String promptMessage, Class<E> enumeration,
			Enum<E> defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				Enum<E>[] enumerationConstants = enumeration.getEnumConstants();
				for (int i = 0; i < enumerationConstants.length; ++i) {
					if (enumerationConstants[i].toString().compareToIgnoreCase(input) == 0) {
						return enumerationConstants[i];
					}
				}
				throw new IllegalArgumentException();
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not one of the valid options.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a boolean. Keeps
	 * trying until a valid input is supplied.
	 * 
	 * The strings "true", "t", "yes", "y" and "1" are interpreted as true. The
	 * strings "false", "f", "no", "n" and "0" are interpreted as false.
	 * 
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A value.
	 */
	public static boolean readBoolean(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("t") || input.equalsIgnoreCase("yes")
						|| input.equalsIgnoreCase("y") || input.equals("1")) {
					return true;
				} else if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("f")
						|| input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n") || input.equals("0")) {
					return false;
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not a valid truth value. It must be \"true\", \"t\", \"yes\", \"y\" or \"1\" for true or \"false\", \"f\", \"no\", \"n\" or \"0\" for false.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a boolean. Keeps
	 * trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * The strings "true", "t", "yes", "y" and "1" are interpreted as true. The
	 * strings "false", "f", "no", "n" and "0" are interpreted as false.
	 * 
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A value.
	 */
	public static boolean readBooleanDefault(String promptMessage, boolean defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("t") || input.equalsIgnoreCase("yes")
						|| input.equalsIgnoreCase("y") || input.equals("1")) {
					return true;
				} else if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("f")
						|| input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n") || input.equals("0")) {
					return false;
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not a valid truth value. It must be \"true\", \"t\", \"yes\", \"y\" or \"1\" for true or \"false\", \"f\", \"no\", \"n\" or \"0\" for false.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a byte. Keeps trying
	 * until a valid input is supplied.
	 * 
	 * @see Byte#parseByte(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A byte.
	 */
	public static byte readByte(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				byte result = Byte.parseByte(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a byte. Keeps trying
	 * until a valid input is supplied. If the trimmed input is empty, returns the
	 * default value provided as an argument.
	 * 
	 * @see Byte#parseByte(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A byte.
	 */
	public static byte readByteDefault(String promptMessage, byte defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				byte result = Byte.parseByte(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded byte. Keeps
	 * trying until a valid input is supplied.
	 * 
	 * @see Byte#parseByte(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided byte to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided byte to be accepted,
	 *                      inclusive.
	 * @return A byte.
	 */
	public static byte readByteBounded(String promptMessage, byte minimum, byte maximum) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				byte result = Byte.parseByte(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded byte. Keeps
	 * trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Byte#parseByte(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided byte to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided byte to be accepted,
	 *                      inclusive.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A byte.
	 */
	public static byte readByteBoundedDefault(String promptMessage, byte minimum, byte maximum, byte defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				byte result = Byte.parseByte(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a short. Keeps trying
	 * until a valid input is supplied.
	 * 
	 * @see Short#parseShort(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A short.
	 */
	public static short readShort(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				short result = Short.parseShort(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a short. Keeps trying
	 * until a valid input is supplied. If the trimmed input is empty, returns the
	 * default value provided as an argument.
	 * 
	 * @see Short#parseShort(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A short.
	 */
	public static short readShortDefault(String promptMessage, short defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				short result = Short.parseShort(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded short.
	 * Keeps trying until a valid input is supplied.
	 * 
	 * @see Short#parseShort(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided short to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided short to be accepted,
	 *                      inclusive.
	 * @return A short.
	 */
	public static short readShortBounded(String promptMessage, short minimum, short maximum) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				short result = Short.parseShort(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded short.
	 * Keeps trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Short#parseShort(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided short to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided short to be accepted,
	 *                      inclusive.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A short.
	 */
	public static short readShortBoundedDefault(String promptMessage, short minimum, short maximum,
			short defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				short result = Short.parseShort(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as an integer. Keeps
	 * trying until a valid input is supplied.
	 * 
	 * @see Integer#parseInt(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return An integer.
	 */
	public static int readInteger(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				int result = Integer.parseInt(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as an integer. Keeps
	 * trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Integer#parseInt(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return An integer.
	 */
	public static int readIntegerDefault(String promptMessage, int defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				int result = Integer.parseInt(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded integer.
	 * Keeps trying until a valid input is supplied.
	 * 
	 * @see Integer#parseInt(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided integer to be
	 *                      accepted, inclusive.
	 * @param minimum       The maximum value for the provided integer to be
	 *                      accepted, inclusive.
	 * @return An integer.
	 */
	public static int readIntegerBounded(String promptMessage, int minimum, int maximum) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				int result = Integer.parseInt(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded integer.
	 * Keeps trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Integer#parseInt(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided integer to be
	 *                      accepted, inclusive.
	 * @param minimum       The maximum value for the provided integer to be
	 *                      accepted, inclusive.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return An integer.
	 */
	public static int readIntegerBoundedDefault(String promptMessage, int minimum, int maximum, int defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				int result = Integer.parseInt(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a long. Keeps trying
	 * until a valid input is supplied.
	 * 
	 * @see Long#parseLong(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A long.
	 */
	public static long readLong(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				long result = Long.parseLong(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a long. Keeps trying
	 * until a valid input is supplied. If the trimmed input is empty, returns the
	 * default value provided as an argument.
	 * 
	 * @see Long#parseLong(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A long.
	 */
	public static long readLongDefault(String promptMessage, long defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				long result = Long.parseLong(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded long. Keeps
	 * trying until a valid input is supplied.
	 * 
	 * @see Long#parseLong(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided long to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided long to be accepted,
	 *                      inclusive.
	 * @return A long.
	 */
	public static long readLongBounded(String promptMessage, long minimum, long maximum) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				long result = Long.parseLong(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded long. Keeps
	 * trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Long#parseLong(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided long to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided long to be accepted,
	 *                      inclusive.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A long.
	 */
	public static long readLongBoundedDefault(String promptMessage, long minimum, long maximum, long defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				long result = Long.parseLong(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a float. Keeps trying
	 * until a valid input is supplied.
	 * 
	 * @see Float#parseFloat(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A float.
	 */
	public static float readFloat(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				float result = Float.parseFloat(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a float. Keeps trying
	 * until a valid input is supplied. If the trimmed input is empty, returns the
	 * default value provided as an argument.
	 * 
	 * @see Float#parseFloat(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A float.
	 */
	public static float readFloatDefault(String promptMessage, float defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				float result = Float.parseFloat(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded float.
	 * Keeps trying until a valid input is supplied.
	 * 
	 * @see Float#parseFloat(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided float to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided float to be accepted,
	 *                      inclusive.
	 * @return A float.
	 */
	public static float readFloatBounded(String promptMessage, float minimum, float maximum) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				float result = Float.parseFloat(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded float.
	 * Keeps trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Float#parseFloat(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided float to be accepted,
	 *                      inclusive.
	 * @param minimum       The maximum value for the provided float to be accepted,
	 *                      inclusive.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A float.
	 */
	public static float readFloatBoundedDefault(String promptMessage, float minimum, float maximum,
			float defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				float result = Float.parseFloat(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a double. Keeps
	 * trying until a valid input is supplied.
	 * 
	 * @see Double#parseDouble(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A double.
	 */
	public static double readDouble(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				double result = Double.parseDouble(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a double. Keeps
	 * trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Double#parseDouble(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A double.
	 */
	public static double readDoubleDefault(String promptMessage, double defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				double result = Double.parseDouble(input);
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded double.
	 * Keeps trying until a valid input is supplied.
	 * 
	 * @see Double#parseDouble(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided double to be
	 *                      accepted, inclusive.
	 * @param minimum       The maximum value for the provided double to be
	 *                      accepted, inclusive.
	 * @return A double.
	 */
	public static double readDoubleBounded(String promptMessage, double minimum, double maximum) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				double result = Double.parseDouble(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a bounded double.
	 * Keeps trying until a valid input is supplied. If the trimmed input is empty,
	 * returns the default value provided as an argument.
	 * 
	 * @see Double#parseDouble(String)
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @param minimum       The minimum value for the provided double to be
	 *                      accepted, inclusive.
	 * @param minimum       The maximum value for the provided double to be
	 *                      accepted, inclusive.
	 * @param default       The value to be returned if the trimmed input is empty.
	 * @return A double.
	 */
	public static double readDoubleBoundedDefault(String promptMessage, double minimum, double maximum,
			double defaultValue) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				if (input.isEmpty()) {
					return defaultValue;
				}
				double result = Double.parseDouble(input);
				if (result < minimum || result > maximum) {
					throw new IllegalArgumentException();
				}
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred.");
				continue;
			} catch (NumberFormatException exception) {
				output("The input \"" + input + "\" doesn't meet the expected format.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The input is not in the expected bounds [" + minimum + "," + maximum + "]");
				continue;
			}
		} while (true);
	}

	/**
	 * Reads a line from the input stream and interprets it as a file. Keeps trying
	 * until a valid input is supplied.
	 * 
	 * @see File#exists()
	 * @param promptMessage Message to be printed to the output stream before
	 *                      reading.
	 * @return A file.
	 */
	public static File readFile(String promptMessage) {
		do {
			String input = null;
			try {
				output(promptMessage);
				input = input().trim();
				File result = new File(input);
				// Throws an I/O exception if the path is not valid
				result.getCanonicalPath();
				return result;
			} catch (IOException exception) {
				output("An I/O error has ocurred or the path is not valid.");
				continue;
			} catch (IllegalArgumentException exception) {
				output("The file is not a valid path.");
				continue;
			} catch (SecurityException exception) {
				output("Cannot access the file.");
				continue;
			}
		} while (true);
	}

}
