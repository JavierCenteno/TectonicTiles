/*
 * ConsoleHelper.java
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

package parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import parameter.i18n.InternationalizedString;
import parameter.parameter.Configurer;
import parameter.parameter.Multiparameter;
import parameter.parameter.OptionalParameter;
import parameter.parameter.Parameter;

/**
 * A class to read from and write to different streams.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class ConsoleHelper {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString IO_PROMPT = new InternationalizedString("io.prompt");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * Reader for the input stream.
	 */
	private BufferedReader inReader;
	/**
	 * Output stream.
	 */
	private PrintStream outWriter;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Create a new console helper using the provided input and output streams.
	 * 
	 * @param in  An input stream to read from.
	 * @param out An output stream to write to.
	 */
	public ConsoleHelper(InputStream in, PrintStream out) {
		this.inReader = new BufferedReader(new InputStreamReader(in));
		this.outWriter = out;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Reading methods

	/**
	 * Read a line from the input stream.
	 * 
	 * @return A line from the input stream. Null if an I/O error occurs while
	 *         reading.
	 */
	public String readLine() {
		String line = null;
		try {
			line = this.inReader.readLine();
		} catch (IOException exception) {
			// Nothing special to do with this exception except letting execution fail
			throw new Error(exception);
		}
		return line;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Writing methods

	/**
	 * Write an empty line.
	 */
	public void writeLine() {
		this.outWriter.println();
	}

	/**
	 * Write a string to the output stream and then start a new line.
	 * 
	 * @param string A string to be written to the output stream.
	 */
	public void writeLine(final String string) {
		this.outWriter.println(string);
	}

	/**
	 * Write multiple strings to the output stream separated by new line characters.
	 * 
	 * @param strings Multiple strings to be written to the output stream.
	 */
	public void writeLine(final String... strings) {
		for (String string : strings) {
			this.outWriter.println(string);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Parameter

	/**
	 * Sets parameters of the given configurer using the console.
	 * 
	 * @param configurer A configurer.
	 */
	public void readConfigurer(Configurer configurer) {
		this.readParameters(configurer.getParameters());
		this.readMultiparameters(configurer.getMultiparameters());
		this.readOptionalParameters(configurer.getOptionalParameters());
	}

	/**
	 * Sets the given parameter using the console, prompting for an input. Tries
	 * until a valid value is supplied.
	 * 
	 * @param parameter A parameter.
	 */
	public void readParameter(Parameter<?> parameter) {
		while (true) {
			try {
				this.writeLine(ConsoleHelper.IO_PROMPT.getValue("{parameter}", parameter.userGetName()));
				this.writeLine(parameter.userGetConstraints());
				String value = this.readLine();
				parameter.userSetCurrentValue(value);
				break;
			} catch (IllegalArgumentException exception) {
				this.writeLine(exception.getLocalizedMessage());
			}
		}
	}

	/**
	 * Sets the given parameters using the console using the
	 * readParameter(Parameter) method.
	 * 
	 * @param parameters Multiple parameters.
	 * @see ConsoleHelper#readParameter(Parameter)
	 */
	public void readParameters(Iterable<Parameter<?>> parameters) {
		for (Parameter<?> parameter : parameters) {
			this.readParameter(parameter);
		}
	}

	/**
	 * Sets the given parameters using the console using the
	 * readParameter(Parameter) method.
	 * 
	 * @param parameters Multiple parameters.
	 * @see ConsoleHelper#readParameter(Parameter)
	 */
	public void readParameters(Parameter<?>... parameters) {
		for (Parameter<?> parameter : parameters) {
			this.readParameter(parameter);
		}
	}

	/**
	 * Resets the value of the given multiparameter and adds values to it using the
	 * console, prompting for an input. For each value, tries until a valid value is
	 * supplied.
	 * 
	 * @param multiparameter A multiparameter.
	 */
	public void readMultiparameter(Multiparameter<?> multiparameter) {
		multiparameter.reset();
		while (true) {
			try {
				this.writeLine(ConsoleHelper.IO_PROMPT.getValue("{parameter}", multiparameter.userGetName()));
				this.writeLine(multiparameter.userGetConstraints());
				String value = this.readLine();
				if (value.isEmpty()) {
					break;
				} else {
					multiparameter.userAddValue(value);
				}
			} catch (IllegalArgumentException exception) {
				this.writeLine(exception.getLocalizedMessage());
			}
		}
	}

	/**
	 * Sets the given multiparameters using the console using the
	 * readMultiparameter(Multiparameter) method.
	 * 
	 * @param multiparameters Multiple multiparameters.
	 * @see ConsoleHelper#readMultiparameter(Multiparameter)
	 */
	public void readMultiparameters(Iterable<Multiparameter<?>> multiparameters) {
		for (Multiparameter<?> multiparameter : multiparameters) {
			this.readMultiparameter(multiparameter);
		}
	}

	/**
	 * Sets the given multiparameters using the console using the
	 * readMultiparameter(Multiparameter) method.
	 * 
	 * @param multiparameters Multiple multiparameters.
	 * @see ConsoleHelper#readMultiparameter(Multiparameter)
	 */
	public void readMultiparameters(Multiparameter<?>... multiparameters) {
		for (Multiparameter<?> multiparameter : multiparameters) {
			this.readMultiparameter(multiparameter);
		}
	}

	/**
	 * Sets the given optional parameter using the console by setting the determiner
	 * and then setting its parameters if necessary.
	 * 
	 * @param optionalParameter An optional parameter.
	 */
	public void readOptionalParameter(OptionalParameter optionalParameter) {
		this.readParameter(optionalParameter.getDeterminer());
		if (optionalParameter.getDeterminer().getCurrentValue()) {
			this.readParameters(optionalParameter.getParameters());
		}
	}

	/**
	 * Sets the given optional parameters using the console using the
	 * readOptionalParameter(OptionalParameter) method.
	 * 
	 * @param optionalParameters Multiple optional parameters.
	 * @see ConsoleHelper#readOptionalParameter(OptionalParameter)
	 */
	public void readOptionalParameters(Iterable<OptionalParameter> optionalParameters) {
		for (OptionalParameter optionalParameter : optionalParameters) {
			this.readOptionalParameter(optionalParameter);
		}
	}

	/**
	 * Sets the given optional parameters using the console using the
	 * readOptionalParameter(OptionalParameter) method.
	 * 
	 * @param optionalParameters Multiple optional parameters.
	 * @see ConsoleHelper#readOptionalParameter(OptionalParameter)
	 */
	public void readOptionalParameters(OptionalParameter... optionalParameters) {
		for (OptionalParameter optionalParameter : optionalParameters) {
			this.readOptionalParameter(optionalParameter);
		}
	}

}
