/*
 * This software is a random terrain generator inspired by plate tectonics.
 * Copyright (C) 2019 Javier Centeno Vega
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
 * @version 0.2
 * @since 0.2
 * @see core.Terrain
 *
 */
public class SquareTerrain extends Terrain {

	private double[][] tiles;

	public SquareTerrain(int sizeX, int sizeY) {
		this.tiles = new double[sizeY][sizeX];
	}

	public int getSizeX() {
		return tiles[0].length;
	}

	public int getSizeY() {
		return tiles.length;
	}

	public double getTile(int indexX, int indexY) {
		return tiles[indexY][indexX];
	}

	public double setTile(int indexX, int indexY, double value) {
		return tiles[indexY][indexX] = value;
	}

}
