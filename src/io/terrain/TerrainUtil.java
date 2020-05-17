/*
 * TerrainUtil.java
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

package io.terrain;

import parameter.parameter.IntegerParameter;
import parameter.parameter.OptionalParameter;
import parameter.parameter.ParameterUtil;

/**
 * Utilities related to terrain.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see io.TerrainConfigurer
 *
 */
public class TerrainUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Nested classes

	/**
	 * A set of optional parameters related to generation of water in terrain.
	 *
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static class WaterParameters extends OptionalParameter {

		////////////////////////////////////////////////////////////////////////////////
		// Instance fields

		private final IntegerParameter seaLevel;

		////////////////////////////////////////////////////////////////////////////////
		// Instance initializers

		/**
		 * Constructs a new optional parameter for water generation.
		 */
		public WaterParameters() {
			super(ParameterUtil.makeBooleanParameter("terrain.hasWater"));
			this.seaLevel = new IntegerParameter("terrain.seaLevel", 32, 1, null, false);
			this.getParameters().add(this.seaLevel);
		}

		////////////////////////////////////////////////////////////////////////////////
		// Instance methods

		/**
		 * Get the parameter that represents the sea level.
		 * 
		 * @return The parameter that represents the sea level.
		 */
		public IntegerParameter getSeaLevel() {
			return this.seaLevel;
		}

	}

	/**
	 * A set of optional parameters related to generation of magma in terrain.
	 *
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static class MagmaParameters extends OptionalParameter {

		////////////////////////////////////////////////////////////////////////////////
		// Instance fields

		private final IntegerParameter crustThickness;

		////////////////////////////////////////////////////////////////////////////////
		// Instance initializers

		/**
		 * Constructs a new optional parameter for magma generation.
		 */
		public MagmaParameters() {
			super(ParameterUtil.makeBooleanParameter("terrain.hasMagma"));
			this.crustThickness = new IntegerParameter("terrain.crustThickness", 32, 1, null, false);
			this.getParameters().add(this.crustThickness);
		}

		////////////////////////////////////////////////////////////////////////////////
		// Instance methods

		/**
		 * Get the parameter that represents the crust thickness.
		 * 
		 * @return The parameter that represents the crust thickness.
		 */
		public IntegerParameter getCrustThickness() {
			return this.crustThickness;
		}

	}

}
