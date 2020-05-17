/*
 * GenerationConfigurer.java
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

package io;

import io.crease.ConeConfigurer;
import io.crease.DiamondConfigurer;
import io.crease.PyramidConfigurer;
import io.crease.SmoothStepConfigurer;
import io.crease.SmootherStepConfigurer;
import io.crease.SmoothestStepConfigurer;
import io.terrain.SquareTerrainConfigurer;
import parameter.parameter.Configurer;
import parameter.parameter.OptionParameter;
import parameter.parameter.OptionParameter.InternationalizedOption;

/**
 * Configurer which handles the different aspects of terrain generation,
 * including choosing a type of terrain configurer and choosing a type of crease
 * configurer.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see io.CreaseConfigurer
 * @see io.TerrainConfigurer
 *
 */
public class GenerationConfigurer extends Configurer {

	////////////////////////////////////////////////////////////////////////////////
	// Parameters

	/**
	 * The parameter which is used to select the type of terrain.
	 */
	private final OptionParameter<TerrainConfigurer<?>> terrainType;
	/**
	 * The parameter which is used to select the type of crease.
	 */
	private final OptionParameter<CreaseConfigurer<?>> creaseType;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Construct a new generation configurer with default parameters.
	 * 
	 * @param nameKey The internationalization key for the name of the configurer.
	 */
	public GenerationConfigurer(String nameKey) {
		super(nameKey);
		// Instance terrain type parameter
		this.terrainType = new OptionParameter<>("terrain.type");
		// Instance terrain type parameter options
		this.terrainType.addOption(new InternationalizedOption<TerrainConfigurer<?>>("terrain.type.square",
				new SquareTerrainConfigurer("terrain.type.square")));
		// Instance crease type parameter
		this.creaseType = new OptionParameter<>("crease.type");
		// Instance crease type parameter options
		this.creaseType.addOption(new InternationalizedOption<CreaseConfigurer<?>>("crease.type.cone",
				new ConeConfigurer("crease.type.cone")));
		this.creaseType.addOption(new InternationalizedOption<CreaseConfigurer<?>>("crease.type.diamond",
				new DiamondConfigurer("crease.type.diamond")));
		this.creaseType.addOption(new InternationalizedOption<CreaseConfigurer<?>>("crease.type.pyramid",
				new PyramidConfigurer("crease.type.pyramid")));
		this.creaseType.addOption(new InternationalizedOption<CreaseConfigurer<?>>("crease.type.smoothStep",
				new SmoothStepConfigurer("crease.type.smoothStep")));
		this.creaseType.addOption(new InternationalizedOption<CreaseConfigurer<?>>("crease.type.smootherStep",
				new SmootherStepConfigurer("crease.type.smootherStep")));
		this.creaseType.addOption(new InternationalizedOption<CreaseConfigurer<?>>("crease.type.smoothestStep",
				new SmoothestStepConfigurer("crease.type.smoothestStep")));
	}

	////////////////////////////////////////////////////////////////////////////////
	// Methods

	/**
	 * Get the parameter which is used to select the type of terrain.
	 * 
	 * @return The parameter which is used to select the type of terrain.
	 */
	public OptionParameter<TerrainConfigurer<?>> getTerrainTypeParameter() {
		return this.terrainType;
	}

	/**
	 * Get the parameter which is used to select the type of crease.
	 * 
	 * @return The parameter which is used to select the type of crease.
	 */
	public OptionParameter<CreaseConfigurer<?>> getCreaseTypeParameter() {
		return this.creaseType;
	}

}
