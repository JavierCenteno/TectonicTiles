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

package core;

import crease.*;
import random.RandomGenerator;
import random.Xorshift64StarGenerator;

public class Generator {

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
	public Generator() {
	}

	////////////////////////////////////////////////////////////////////////////////
	// Generation methods

	public Terrain generate() {

		/*
		 * TODO: If magma is not being generated, volcanoes could be generated in plates
		 * with a very short movement vector
		 */

		RandomGenerator randomGenerator = new Xorshift64StarGenerator();
		// TODO: configure how to set crease function
		Crease crease = new SmoothStep(2, 1, 0.125, 2.5);

		// TODO: configure clumping factor

		// plate size = 20
		// Crease crease = new SmoothStep(1, 1, 5, 1);
		// Crease crease = new SmoothStep(1, 1, 0.5, 2);
		// Crease crease = new SmoothStep(1, 1, 0.05, 3);
		// Crease crease = new SmoothStep(1, 1, 0.005, 4);
		// Crease crease = new SmoothStep(1, 1, 0.0005, 5);
		// Crease crease = new SmoothStep(1, 1, 0.00005, 6);

		// plate size = 100
		// Crease crease = new SmoothStep(1, 1, 0.1, 2);
		// Crease crease = new SmoothStep(1, 1, 0.002, 3);
		// Crease crease = new SmoothStep(1, 1, 0.00004, 4);
		// Crease crease = new SmoothStep(1, 1, 0.0000008, 5);

		int plateSizeY = this.plateSize;
		int plateSizeX = this.plateSize;
		int centerTileIndexY = this.plateSize / 2;
		int centerTileIndexX = this.plateSize / 2;

		int terrainSizeY = plateSizeY * numberOfPlatesY;
		int terrainSizeX = plateSizeX * numberOfPlatesX;

		Terrain terrain = new Terrain(terrainSizeX, terrainSizeY);

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
				System.out.println("[" + percentString + "%] Generating plate " + (plateIndexY + 1) + ","
						+ (plateIndexX + 1) + " out of " + numberOfPlatesY + "," + numberOfPlatesX);
				// Actual calculations
				int startTileIndexY = plateIndexY * plateSizeY + centerTileIndexY;
				int startTileIndexX = plateIndexX * plateSizeX + centerTileIndexX;
				// Randomly choose end tile
				int endTileIndexY = (int) randomGenerator.generateLong(plateIndexY * plateSizeY,
						(plateIndexY + 1) * plateSizeY);
				int endTileIndexX = (int) randomGenerator.generateLong(plateIndexX * plateSizeX,
						(plateIndexX + 1) * plateSizeX);

				/*
				 * 
				 * // Code to find the end tile using a circle instead of a square
				 * 
				 * double endTileAngle = randomGenerator.nextDouble(); double endTileRadius =
				 * randomGenerator.nextDouble() * Math.sqrt(Util.square(plateSizeX) / Math.PI);
				 * 
				 * int endTileIndexY = startTileIndexY + (int)
				 * Util.polarToCartesianY(endTileAngle, endTileRadius); int endTileIndexX =
				 * startTileIndexX + (int) Util.polarToCartesianX(endTileAngle, endTileRadius);
				 * 
				 */

				for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
					for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
						double tile = terrain.getTile(tileIndexX, tileIndexY);
						tile += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX, tileIndexY);
						terrain.setTile(tileIndexX, tileIndexY, tile);
					}
				}
			}
		}
		System.out.println("[100%] Done");

		return terrain;
	}

}
