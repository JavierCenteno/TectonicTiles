/*
 * Terrain.java
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

package core;

/**
 * A terrain consisting of a set of tiles which have a set of values each.
 *
 * @param <L> The type of layers used by this terrain.
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 *
 */
public abstract class Terrain<L extends Terrain.TerrainLayer> {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * The seed used to generate this terrain.
	 */
	protected long seed;
	/**
	 * The height layer of this terrain. Shouldn't be null.
	 */
	protected L heightLayer;
	/**
	 * The water layer of this terrain. Should be null if the terrain doesn't
	 * implement water.
	 */
	protected L waterLayer;
	/**
	 * The magma layer of this terrain. Should be null if the terrain doesn't
	 * implement magma.
	 */
	protected L magmaLayer;

	////////////////////////////////////////////////////////////////////////////////
	// Nested classes

	/**
	 * A layer of a terrain, which contains a specific value for the tiles in the
	 * terrain.
	 *
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static abstract class TerrainLayer {
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Get the seed of this terrain.
	 * 
	 * @return The seed of this terrain.
	 */
	public long getSeed() {
		return this.seed;
	}

	/**
	 * Set the seed of this terrain.
	 * 
	 * @param seed The seed of this terrain.
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * Get the height layer of this terrain.
	 * 
	 * @return The height layer of this terrain.
	 */
	public L getHeightLayer() {
		return this.heightLayer;
	}

	/**
	 * Set the height layer of this terrain.
	 * 
	 * @param heightLayer The height layer of this terrain.
	 */
	public void setHeightLayer(L heightLayer) {
		this.heightLayer = heightLayer;
	}

	/**
	 * Get the water layer of this terrain.
	 * 
	 * @return The water layer of this terrain. Null if the terrain doesn't
	 *         implement water.
	 */
	public L getWaterLayer() {
		return this.waterLayer;
	}

	/**
	 * Set the water layer of this terrain.
	 * 
	 * @param waterLayer The water layer of this terrain.
	 */
	public void setWaterLayer(L waterLayer) {
		this.waterLayer = waterLayer;
	}

	/**
	 * Get the magma layer of this terrain.
	 * 
	 * @return The magma layer of this terrain. Null if the terrain doesn't
	 *         implement magma.
	 */
	public L getMagmaLayer() {
		return this.magmaLayer;
	}

	/**
	 * Set the magma layer of this terrain.
	 * 
	 * @param magmaLayer The magma layer of this terrain.
	 */
	public void setMagmaLayer(L magmaLayer) {
		this.magmaLayer = magmaLayer;
	}

}
