/*
 * CancelledException.java
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

package parameter.parameter;

import parameter.i18n.InternationalizedString;

/**
 * An unchecked exception that is thrown when the user cancels a parameter
 * input. This exception indicates that the current action being executed should
 * stop, and it should be caught by the method that called the current action
 * being executed.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class CancelledException extends RuntimeException {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final InternationalizedString CANCEL_EXCEPTION_MESSAGE = new InternationalizedString(
			"parameter.cancel.exception");

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new cancelled exception with the default "action cancelled"
	 * internationalized message.
	 */
	public CancelledException() {
		super(CANCEL_EXCEPTION_MESSAGE.getValue());
	}

}
