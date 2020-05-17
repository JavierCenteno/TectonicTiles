/*
 * SmoothestStepConfigurer.java
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

package io.crease;

import core.crease.SmoothestStep;
import io.CreaseConfigurer;
import parameter.parameter.DoubleParameter;

/**
 * A configurer that handles smootheststep crease functions.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see io.CreaseConfigurer
 * @see core.crease.SmoothestStep
 *
 */
public class SmoothestStepConfigurer extends CreaseConfigurer<SmoothestStep> {

	////////////////////////////////////////////////////////////////////////////////
	// Parameters

	private final DoubleParameter heightFactor;
	private final DoubleParameter heightPower;
	private final DoubleParameter widthFactor;
	private final DoubleParameter widthPower;

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Get the factor by which the height of the function is multiplied.
	 * 
	 * @return The factor by which the height of the function is multiplied.
	 */
	public DoubleParameter getHeightFactor() {
		return this.heightFactor;
	}

	/**
	 * Get the power to which the height of the function is raised.
	 * 
	 * @return The power to which the height of the function is raised.
	 */
	public DoubleParameter getHeightPower() {
		return this.heightPower;
	}

	/**
	 * Get the factor by which the width of the function is multiplied.
	 * 
	 * @return The factor by which the width of the function is multiplied.
	 */
	public DoubleParameter getWidthFactor() {
		return this.widthFactor;
	}

	/**
	 * Get the power to which the width of the function is raised.
	 * 
	 * @return The power to which the width of the function is raised.
	 */
	public DoubleParameter getWidthPower() {
		return this.widthPower;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Construct a new smoothest step crease configurer with default parameters.
	 * 
	 * @param nameKey The internationalization key for the name of the configurer.
	 */
	public SmoothestStepConfigurer(String nameKey) {
		super(nameKey);
		this.heightFactor = new DoubleParameter("crease.heightFactor", 1d, null, null, false, false);
		this.getParameters().add(this.heightFactor);
		this.heightPower = new DoubleParameter("crease.heightPower", 1d, null, null, false, false);
		this.getParameters().add(this.heightPower);
		this.widthFactor = new DoubleParameter("crease.widthFactor", 4d, null, null, false, false);
		this.getParameters().add(this.widthFactor);
		this.widthPower = new DoubleParameter("crease.widthPower", 1d, null, null, false, false);
		this.getParameters().add(this.widthPower);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Methods

	@Override
	public SmoothestStep generate() {
		return new SmoothestStep(heightFactor.getCurrentValue().getValue(), heightPower.getCurrentValue().getValue(),
				widthFactor.getCurrentValue().getValue(), widthPower.getCurrentValue().getValue());
	}

}
