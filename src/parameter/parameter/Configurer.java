/*
 * Configurer.java
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

import parameter.i18n.Internationalized;
import parameter.i18n.InternationalizedString;

/**
 * Configurer class which holds a collection of parameters related to a single
 * functionality and can hold an arbitrary amount of parameters.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public abstract class Configurer {

	////////////////////////////////////////////////////////////////////////////////
	// Parameters

	/**
	 * The name of this configurer.
	 */
	@Internationalized
	private InternationalizedString name;
	/**
	 * The parameters contained in this configurer.
	 */
	private final Collection<Parameter<?>> parameters;
	/**
	 * The multiparameters contained in this configurer.
	 */
	private final Collection<Multiparameter<?>> multiparameters;
	/**
	 * The optional parameters contained in this configurer.
	 */
	private final Collection<OptionalParameter> optionalparameters;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Construct a new configurer with the given name and an empty list of
	 * parameters.
	 * 
	 * @param nameKey The internationalization key for the name of the configurer.
	 */
	public Configurer(String nameKey) {
		this.name = new InternationalizedString(nameKey);
		this.parameters = new ArrayList<>();
		this.multiparameters = new ArrayList<>();
		this.optionalparameters = new ArrayList<>();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Methods

	/**
	 * Get the parameters contained in this configurer.
	 * 
	 * @return A collection of parameters.
	 */
	public Collection<Parameter<?>> getParameters() {
		return this.parameters;
	}

	/**
	 * Get the multiparameters contained in this configurer.
	 * 
	 * @return A collection of multiparameters.
	 */
	public Collection<Multiparameter<?>> getMultiparameters() {
		return this.multiparameters;
	}

	/**
	 * Get the optional parameters contained in this configurer.
	 * 
	 * @return A collection of optional parameters.
	 */
	public Collection<OptionalParameter> getOptionalParameters() {
		return this.optionalparameters;
	}

	////////////////////////////////////////////////////////////////////////////////
	// I/O methods

	/**
	 * Get the name of this parameter.
	 * 
	 * @return The name of this parameter.
	 */
	@Internationalized
	public String userGetName() {
		return this.name.getValue();
	}

}
