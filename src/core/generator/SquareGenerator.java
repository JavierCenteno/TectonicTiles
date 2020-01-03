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

package core.generator;

import core.Generator;
import core.terrain.SquareTerrain;
import util.ConsoleUtil;

/**
 * A generator used to generate square terrains.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 * @see core.Generator
 * @see core.terrain.SquareTerrain
 *
 */
public class SquareGenerator extends Generator<SquareTerrain> {

	////////////////////////////////////////////////////////////////////////////////
	// Generator parameters

	private int plateSize;
	private int numberOfPlatesX;
	private int numberOfPlatesY;

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	public int getPlateSize() {
		return plateSize;
	}

	public void setPlateSize(int plateSize) {
		this.plateSize = plateSize;
	}

	public int getNumberOfPlatesX() {
		return numberOfPlatesX;
	}

	public void setNumberOfPlatesX(int numberOfPlatesX) {
		this.numberOfPlatesX = numberOfPlatesX;
	}

	public int getNumberOfPlatesY() {
		return numberOfPlatesY;
	}

	public void setNumberOfPlatesY(int numberOfPlatesY) {
		this.numberOfPlatesY = numberOfPlatesY;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Instantiates a generator with default parameters.
	 */
	public SquareGenerator(long seed) {
		super(seed);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Generation methods

	public void generate() {
		this.initializeGenerator();
		int plateSizeY = this.plateSize;
		int plateSizeX = this.plateSize;
		int centerTileIndexY = this.plateSize / 2;
		int centerTileIndexX = this.plateSize / 2;
		int terrainSizeY = plateSizeY * numberOfPlatesY;
		int terrainSizeX = plateSizeX * numberOfPlatesX;
		SquareTerrain terrain = new SquareTerrain(terrainSizeX, terrainSizeY);
		for (int plateIndexY = 0; plateIndexY < numberOfPlatesY; ++plateIndexY) {
			for (int plateIndexX = 0; plateIndexX < numberOfPlatesX; ++plateIndexX) {
				// Console info
				int percent = ((plateIndexY * numberOfPlatesX) + plateIndexX) * 100
						/ (numberOfPlatesY * numberOfPlatesX);
				String percentString;
				if (percent < 10) {
					percentString = "  " + percent;
				} else if (percent < 100) {
					percentString = " " + percent;
				} else {
					percentString = "" + percent;
				}
				ConsoleUtil.output("[" + percentString + "%] Generating plate " + (plateIndexY + 1) + ","
						+ (plateIndexX + 1) + " out of " + numberOfPlatesY + "," + numberOfPlatesX);
				// Actual calculations
				int startTileIndexY = plateIndexY * plateSizeY + centerTileIndexY;
				int startTileIndexX = plateIndexX * plateSizeX + centerTileIndexX;
				// Randomly choose end tile
				int endTileIndexY = (int) randomGenerator.generateLong(plateIndexY * plateSizeY,
						(plateIndexY + 1) * plateSizeY);
				int endTileIndexX = (int) randomGenerator.generateLong(plateIndexX * plateSizeX,
						(plateIndexX + 1) * plateSizeX);
				for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
					for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
						double tile = terrain.getTile(tileIndexX, tileIndexY);
						tile += this.crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX, tileIndexY);
						terrain.setTile(tileIndexX, tileIndexY, tile);
					}
				}
			}
		}
		ConsoleUtil.output("[100%] Done");
		this.terrain = terrain;
	}

}
