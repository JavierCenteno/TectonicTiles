/*
 * OptionalParameter.java
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

import java.util.ArrayList;
import java.util.Collection;

/**
 * A set of parameters that only need to be set depending on a boolean value.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class OptionalParameter {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	/**
	 * The parameter used to determine whether the values need to be set.
	 */
	private OptionParameter<Boolean> determiner;
	/**
	 * The parameters that need to be set if determiner is true.
	 */
	private Collection<Parameter<?>> parameters;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new optional parameter with the given boolean parameter and no
	 * parameters.
	 * 
	 * @param determiner The parameter used to determine whether the values need to
	 *                   be set.
	 */
	public OptionalParameter(OptionParameter<Boolean> determiner) {
		this.determiner = determiner;
		this.parameters = new ArrayList<>();
	}

	/**
	 * Constructs a new optional parameter with the given boolean parameter and the
	 * given parameters.
	 * 
	 * @param determiner The parameter used to determine whether the values need to
	 *                   be set.
	 * @param parameters The parameters that need to be set if determiner is true.
	 */
	public OptionalParameter(OptionParameter<Boolean> determiner, Parameter<?>... parameters) {
		this(determiner);
		for (int i = 0; i < parameters.length; ++i) {
			this.parameters.add(parameters[i]);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Get the parameter used to determine whether the values need to be set.
	 * 
	 * @return The parameter used to determine whether the values need to be set.
	 */
	public OptionParameter<Boolean> getDeterminer() {
		return this.determiner;
	}

	/**
	 * Get the parameters that need to be set if determiner is true.
	 * 
	 * @return The parameters that need to be set if determiner is true.
	 */
	public Collection<Parameter<?>> getParameters() {
		return this.parameters;
	}

}
