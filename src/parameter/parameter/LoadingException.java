/*
 * LoadingException.java
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

/**
 * An unchecked exception that is thrown when there's an error loading files
 * that are necessary for running the software.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class LoadingException extends RuntimeException {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new loading exception with the given message.
	 * 
	 * @param message A message.
	 */
	public LoadingException(String message) {
		super(message);
	}

}
