/*
 * SquareTerrain.java
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

package core.terrain;

import core.Terrain;

/**
 * A terrain with square shaped tiles, square shaped tectonic plates and a
 * rectangular shape.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 * @see core.Terrain
 *
 */
public class SquareTerrain extends Terrain<SquareTerrain.SquareTerrainLayer> {

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Create a new square terrain of the given size.
	 * 
	 * @param sizeX    The amount of tiles along the x axis.
	 * @param sizeY    The amount of tiles along the y axis.
	 * @param hasWater Whether the terrain has water.
	 * @param hasMagma Whether the terrain has magma.
	 */
	public SquareTerrain(int sizeX, int sizeY, boolean hasWater, boolean hasMagma) {
		this.landLayer = new SquareTerrainLayer(sizeX, sizeY);
		if (hasWater) {
			this.waterLayer = new SquareTerrainLayer(sizeX, sizeY);
		}
		if (hasMagma) {
			this.magmaLayer = new SquareTerrainLayer(sizeX, sizeY);
		}
	}

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
	public static class SquareTerrainLayer extends Terrain.TerrainLayer {

		////////////////////////////////////////////////////////////////////////////////
		// Instance fields

		/**
		 * The value of each tile of this layer.
		 */
		protected double[][] tiles;

		////////////////////////////////////////////////////////////////////////////////
		// Instance initializers

		/**
		 * Create a new square terrain layer of the given size.
		 * 
		 * @param sizeX The amount of tiles along the x axis.
		 * @param sizeY The amount of tiles along the y axis.
		 */
		public SquareTerrainLayer(int sizeX, int sizeY) {
			this.tiles = new double[sizeY][sizeX];
		}

		////////////////////////////////////////////////////////////////////////////////
		// Instance methods

		/**
		 * Get the size of this layer along the x axis.
		 * 
		 * @return The size of this layer along the x axis.
		 */
		public int getSizeX() {
			return this.tiles[0].length;
		}

		/**
		 * Get the size of this layer along the y axis.
		 * 
		 * @return The size of this layer along the y axis.
		 */
		public int getSizeY() {
			return this.tiles.length;
		}

		/**
		 * Get the value of this layer at the tile at the given indices.
		 * 
		 * @param indexX The index of the tile along the x axis.
		 * @param indexY The index of the tile along the y axis.
		 * @return The value of this layer at the tile at the given indices.
		 */
		public double getTile(int indexX, int indexY) {
			return this.tiles[indexY][indexX];
		}

		/**
		 * Set the value of this layer at the tile at the given indices.
		 * 
		 * @param indexX The index of the tile along the x axis.
		 * @param indexY The index of the tile along the y axis.
		 * @param value  The value to set the tile to.
		 */
		public void setTile(int indexX, int indexY, double value) {
			this.tiles[indexY][indexX] = value;
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Get the size of this terrain along the x axis.
	 * 
	 * @return The size of this terrain along the x axis.
	 */
	public int getSizeX() {
		return this.landLayer.getSizeX();
	}

	/**
	 * Get the size of this terrain along the y axis.
	 * 
	 * @return The size of this terrain along the y axis.
	 */
	public int getSizeY() {
		return this.landLayer.getSizeY();
	}

}
