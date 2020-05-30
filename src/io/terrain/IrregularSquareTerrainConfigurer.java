/*
 * SquareTerrainConfigurer.java
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

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import core.Crease;
import core.terrain.SquareTerrain;
import core.terrain.SquareTerrain.SquareTerrainLayer;
import io.TerrainConfigurer;
import io.terrain.TerrainUtil.MagmaParameters;
import io.terrain.TerrainUtil.WaterParameters;
import parameter.i18n.InternationalizedString;
import parameter.parameter.IntegerParameter;
import parameter.parameter.OptionParameter;
import parameter.parameter.ParameterUtil;
import random.RandomGenerator;
import util.ArrayUtil;

/**
 * A configurer that handles square terrains with irregularly shaped tectonic
 * plates.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.4
 * @since 0.4
 * @see io.TerrainConfigurer
 * @see core.terrain.SquareTerrain
 *
 */
public class IrregularSquareTerrainConfigurer extends TerrainConfigurer<SquareTerrain> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString ERROR_NO_TERRAIN = new InternationalizedString(
			"console.command.error.noTerrain");
	private static final InternationalizedString TERRAIN_EXCEPTION_FORMAT = new InternationalizedString(
			"terrain.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Parameters

	private final IntegerParameter numberOfPlates;
	private final IntegerParameter terrainSizeX;
	private final IntegerParameter terrainSizeY;
	private final OptionParameter<Boolean> wrapAroundX;
	private final OptionParameter<Boolean> wrapAroundY;
	private final WaterParameters waterParameters;
	private final MagmaParameters magmaParameters;

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Get the parameter that represents the number of plates.
	 * 
	 * @return The parameter that represents the number of plates.
	 */
	public IntegerParameter getNumberOfPlates() {
		return this.numberOfPlates;
	}

	/**
	 * Get the parameter that represents the terrain size in tiles along the x axis.
	 * 
	 * @return The parameter that represents the terrain size in tiles along the x
	 *         axis.
	 */
	public IntegerParameter getTerrainSizeX() {
		return this.terrainSizeX;
	}

	/**
	 * Get the parameter that represents the terrain size in tiles along the y axis.
	 * 
	 * @return The parameter that represents the terrain size in tiles along the y
	 *         axis.
	 */
	public IntegerParameter getTerrainSizeY() {
		return this.terrainSizeY;
	}

	/**
	 * Get the parameter that represents whether the terrain wraps around along the
	 * x axis.
	 * 
	 * @return The parameter that represents whether the terrain wraps around along
	 *         the x axis.
	 */
	public OptionParameter<Boolean> getWrapAroundX() {
		return this.wrapAroundX;
	}

	/**
	 * Get the parameter that represents whether the terrain wraps around along the
	 * y axis.
	 * 
	 * @return The parameter that represents whether the terrain wraps around along
	 *         the y axis.
	 */
	public OptionParameter<Boolean> getWrapAroundY() {
		return this.wrapAroundY;
	}

	/**
	 * Get the optional parameter that contains the parameters related to water
	 * generation.
	 * 
	 * @return The optional parameter that contains the parameters related to water
	 *         generation.
	 */
	public WaterParameters getWaterParameters() {
		return this.waterParameters;
	}

	/**
	 * Get the optional parameter that contains the parameters related to magma
	 * generation.
	 * 
	 * @return The optional parameter that contains the parameters related to magma
	 *         generation.
	 */
	public MagmaParameters getMagmaParameters() {
		return this.magmaParameters;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Construct a new square terrain configurer with default parameters.
	 * 
	 * @param nameKey The internationalization key for the name of the configurer.
	 */
	public IrregularSquareTerrainConfigurer(String nameKey) {
		super(nameKey);
		this.numberOfPlates = new IntegerParameter("terrain.numberOfPlates", 512, 2, 1 << 15, false);
		this.getParameters().add(this.numberOfPlates);
		this.terrainSizeX = new IntegerParameter("terrain.terrainSizeX", 512, 2, 1 << 15, false);
		this.getParameters().add(this.terrainSizeX);
		this.terrainSizeY = new IntegerParameter("terrain.terrainSizeY", 256, 2, 1 << 15, false);
		this.getParameters().add(this.terrainSizeY);
		this.wrapAroundX = ParameterUtil.makeBooleanParameter("terrain.wrapAroundX");
		this.getParameters().add(this.wrapAroundX);
		this.wrapAroundY = ParameterUtil.makeBooleanParameter("terrain.wrapAroundY");
		this.getParameters().add(this.wrapAroundY);
		this.waterParameters = new WaterParameters();
		this.getOptionalParameters().add(this.waterParameters);
		this.magmaParameters = new MagmaParameters();
		this.getOptionalParameters().add(this.magmaParameters);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public SquareTerrain generate(RandomGenerator randomGenerator, Crease crease) {
		int numberOfPlates = this.numberOfPlates.getCurrentValue().getValue();
		int terrainSizeY = this.terrainSizeY.getCurrentValue().getValue();
		int terrainSizeX = this.terrainSizeX.getCurrentValue().getValue();
		int terrainSize = terrainSizeX * terrainSizeY;
		boolean wrapAroundX = this.wrapAroundX.getCurrentValue();
		boolean wrapAroundY = this.wrapAroundY.getCurrentValue();
		boolean hasWater = this.waterParameters.getDeterminer().getCurrentValue();
		boolean hasMagma = this.magmaParameters.getDeterminer().getCurrentValue();
		SquareTerrain terrain = new SquareTerrain(terrainSizeX, terrainSizeY, hasWater, hasMagma);

		// The x coordinates of tiles that haven't been assigned to a plate
		int[] tilesX = new int[terrainSize];
		// The y coordinates of tiles that haven't been assigned to a plate
		int[] tilesY = new int[terrainSize];
		// Populate the list
		for (int y = 0, t = 0; y < terrainSizeY; ++y) {
			for (int x = 0; x < terrainSizeX; ++x) {
				tilesX[t] = x;
				tilesY[t] = y;
				++t;
			}
		}
		// Shuffle the list
		for (int t = 0; t < terrainSize; ++t) {
			int r = (int) randomGenerator.generateLong(t, terrainSize);
			int tileX = tilesX[t];
			tilesX[t] = tilesX[r];
			tilesX[r] = tileX;
			int tileY = tilesY[t];
			tilesY[t] = tilesY[r];
			tilesY[r] = tileY;
		}

		// plateTilesX[plateIndex] is a list of the x coordinate of all tiles
		// in the plate with index plateIndex
		int[][] plateTilesX = new int[numberOfPlates][1];
		int[][] plateTilesY = new int[numberOfPlates][1];
		// Assign the first (numberOfPlates) tiles to each plate
		for (int p = 0; p < numberOfPlates; ++p) {
			plateTilesX[p][0] = tilesX[0];
			plateTilesY[p][0] = tilesY[0];
			tilesX = ArrayUtil.remove(tilesX, 0);
			tilesY = ArrayUtil.remove(tilesY, 0);
		}

		loop: while (tilesX.length > 0) {
			// For all unassigned tiles
			for (int t = 0; t < tilesX.length; ++t) {
				// Coordinates of the current unassigned tile
				int tileX = tilesX[t];
				int tileY = tilesY[t];
				
				// For all plates
				for (int p = 0; p < plateTilesX.length; ++p) {
					// For all tiles currently assigned to the plate
					for (int pt = 0; pt < plateTilesX[p].length; ++pt) {
						// Coordinates of the current tile in the plate
						int plateTileX = plateTilesX[p][pt];
						int plateTileY = plateTilesY[p][pt];
						// Whether tile neighbors plateTile
						boolean isNeighboring = ((plateTileX - 1) == tileX && plateTileY == tileY)
								|| (plateTileX == tileX && (plateTileY - 1) == tileY)
								|| ((plateTileX + 1) == tileX && plateTileY == tileY)
								|| (plateTileX == tileX && (plateTileY + 1) == tileY);
						if (isNeighboring) {
							tilesX = ArrayUtil.remove(tilesX, t);
							tilesY = ArrayUtil.remove(tilesY, t);
							plateTilesX[p] = ArrayUtil.add(plateTilesX[p], tileX);
							plateTilesY[p] = ArrayUtil.add(plateTilesY[p], tileY);
							// Reset the iteration
							continue loop;
						}
					}
				}
			}
		}

		for (int p = 0; p < numberOfPlates; ++p) {
			int startTileIndexInPlate = (int) randomGenerator.generateLong(plateTilesX[p].length);
			int startTileIndexX = plateTilesX[p][startTileIndexInPlate];
			int startTileIndexY = plateTilesY[p][startTileIndexInPlate];
			int endTileIndexInPlate = (int) randomGenerator.generateLong(plateTilesX[p].length);
			int endTileIndexX = plateTilesX[p][endTileIndexInPlate];
			int endTileIndexY = plateTilesY[p][endTileIndexInPlate];

			for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
				for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {

					// set land
					double land = terrain.getLandLayer().getTile(tileIndexX, tileIndexY);
					land += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY, tileIndexX,
							tileIndexY);
					if (wrapAroundY) {
						land += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX, tileIndexY - terrainSizeY);
						land += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX, tileIndexY + terrainSizeY);
					}
					if (wrapAroundX) {
						land += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX - terrainSizeX, tileIndexY);
						land += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX + terrainSizeX, tileIndexY);
					}
					terrain.getLandLayer().setTile(tileIndexX, tileIndexY, land);

					// set magma
					if (terrain.getMagmaLayer() != null) {
						double magma = terrain.getMagmaLayer().getTile(tileIndexX, tileIndexY);
						// magma is generated like land
						// but in the opposite direction of the plate movement
						magma += crease.valueAt(endTileIndexX, endTileIndexY, startTileIndexX, startTileIndexY,
								tileIndexX, tileIndexY);
						if (wrapAroundY) {
							magma += crease.valueAt(endTileIndexX, endTileIndexY, startTileIndexX, startTileIndexY,
									tileIndexX, tileIndexY - terrainSizeY);
							magma += crease.valueAt(endTileIndexX, endTileIndexY, startTileIndexX, startTileIndexY,
									tileIndexX, tileIndexY + terrainSizeY);
						}
						if (wrapAroundX) {
							magma += crease.valueAt(endTileIndexX, endTileIndexY, startTileIndexX, startTileIndexY,
									tileIndexX - terrainSizeX, tileIndexY);
							magma += crease.valueAt(endTileIndexX, endTileIndexY, startTileIndexX, startTileIndexY,
									tileIndexX + terrainSizeX, tileIndexY);
						}
						terrain.getMagmaLayer().setTile(tileIndexX, tileIndexY, magma);
					}

				}
			}

		}

		// for all tiles
		for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
			for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {

				// set water
				if (terrain.getWaterLayer() != null) {
					double seaLevel = (double) this.waterParameters.getSeaLevel().getCurrentValue().getValue();
					terrain.getWaterLayer().setTile(tileIndexX, tileIndexY, seaLevel);
				}

				// shift magma according to crust thickness
				if (terrain.getMagmaLayer() != null) {
					double crustThickness = (double) this.magmaParameters.getCrustThickness().getCurrentValue()
							.getValue();
					double magma = terrain.getMagmaLayer().getTile(tileIndexX, tileIndexY);
					terrain.getMagmaLayer().setTile(tileIndexX, tileIndexY, magma - crustThickness);
				}

			}
		}

		return this.terrain = terrain;
	}

	@Override
	public RenderedImage toImage() {
		if (terrain == null) {
			throw new NullPointerException(ERROR_NO_TERRAIN.getValue());
		}
		int terrainSizeY = terrain.getSizeY();
		int terrainSizeX = terrain.getSizeX();

		// The tile with the highest value
		double highestTile = Double.NEGATIVE_INFINITY;
		// The tile with the lowest value
		double lowestTile = Double.POSITIVE_INFINITY;

		for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
			for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
				double land = terrain.getLandLayer().getTile(tileIndexX, tileIndexY);
				if (land > highestTile) {
					highestTile = land;
				} else if (land < lowestTile) {
					lowestTile = land;
				}
				if (terrain.getWaterLayer() != null) {
					double water = terrain.getWaterLayer().getTile(tileIndexX, tileIndexY);
					if (water > highestTile) {
						highestTile = water;
					} else if (water < lowestTile) {
						lowestTile = water;
					}
				}
				if (terrain.getMagmaLayer() != null) {
					double magma = terrain.getMagmaLayer().getTile(tileIndexX, tileIndexY);
					if (magma > highestTile) {
						highestTile = magma;
					} else if (magma < lowestTile) {
						lowestTile = magma;
					}
				}
			}
		}

		final BufferedImage bufferedImage = new BufferedImage(terrainSizeX, terrainSizeY, BufferedImage.TYPE_INT_ARGB);
		for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
			for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
				double land = terrain.getLandLayer().getTile(tileIndexX, tileIndexY);
				double water;
				if (terrain.getWaterLayer() == null) {
					water = Double.NEGATIVE_INFINITY;
				} else {
					water = terrain.getWaterLayer().getTile(tileIndexX, tileIndexY);
				}
				double magma;
				if (terrain.getMagmaLayer() == null) {
					magma = Double.NEGATIVE_INFINITY;
				} else {
					magma = terrain.getMagmaLayer().getTile(tileIndexX, tileIndexY);
				}
				int color = TileUtil.tileToColor(land, water, magma, lowestTile, highestTile);
				bufferedImage.setRGB(tileIndexX, tileIndexY, color);
			}
		}
		return bufferedImage;
	}

	@Override
	public String[] getFormatNames() {
		return new String[] { "csv", "CSV" };
	}

	@Override
	public void importTerrain(String formatName, InputStream input) throws IOException {
		switch (formatName) {
		case "csv":
		case "CSV":
			Scanner scanner = new Scanner(input).useDelimiter("\n\n");
			try {

				int terrainSizeY;
				int terrainSizeX;

				// land layer
				if (scanner.hasNext()) {
					String result = scanner.next();
					String[] lines = result.split("\n");
					String[][] values = new String[lines.length][];
					for (int i = 0; i < lines.length; ++i) {
						values[i] = lines[i].split(",");
					}
					this.terrain = new SquareTerrain(values[0].length, values.length, false, false);
					terrainSizeY = terrain.getSizeY();
					terrainSizeX = terrain.getSizeX();
					for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
						for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
							double tile = Double.parseDouble(values[tileIndexY][tileIndexX]);
							terrain.getLandLayer().setTile(tileIndexX, tileIndexY, tile);
						}
					}
				} else {
					throw new TerrainFormatException(TERRAIN_EXCEPTION_FORMAT.getValue());
				}

				// water layer
				if (scanner.hasNext()) {
					String result = scanner.next();
					if (result.equals("-")) {
						// No water layer
					} else {
						String[] lines = result.split("\n");
						String[][] values = new String[lines.length][];
						for (int i = 0; i < lines.length; ++i) {
							values[i] = lines[i].split(",");
						}
						if (terrainSizeY != terrain.getSizeY() || terrainSizeX != terrain.getSizeX()) {
							throw new TerrainFormatException(TERRAIN_EXCEPTION_FORMAT.getValue());
						}
						terrain.setWaterLayer(new SquareTerrainLayer(terrainSizeX, terrainSizeY));
						for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
							for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
								System.out.println(tileIndexX);
								System.out.println(tileIndexY);
								double tile = Double.parseDouble(values[tileIndexY][tileIndexX]);
								terrain.getWaterLayer().setTile(tileIndexX, tileIndexY, tile);
							}
						}
					}
				}

				// magma layer
				if (scanner.hasNext()) {
					String result = scanner.next();
					if (result.equals("-")) {
						// No magma layer
					} else {
						String[] lines = result.split("\n");
						String[][] values = new String[lines.length][];
						for (int i = 0; i < lines.length; ++i) {
							values[i] = lines[i].split(",");
						}
						if (terrainSizeY != terrain.getSizeY() || terrainSizeX != terrain.getSizeX()) {
							throw new TerrainFormatException(TERRAIN_EXCEPTION_FORMAT.getValue());
						}
						terrain.setMagmaLayer(new SquareTerrainLayer(terrainSizeX, terrainSizeY));
						for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
							for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
								double tile = Double.parseDouble(values[tileIndexY][tileIndexX]);
								terrain.getMagmaLayer().setTile(tileIndexX, tileIndexY, tile);
							}
						}
					}
				}

			} catch (Exception exception) {
				throw new TerrainFormatException(TERRAIN_EXCEPTION_FORMAT.getValue());
			}
		}
	}

	@Override
	public void exportTerrain(String formatName, OutputStream output) throws IOException {
		if (terrain == null) {
			throw new NullPointerException(ERROR_NO_TERRAIN.getValue());
		}
		switch (formatName) {
		case "csv":
		case "CSV":
			OutputStreamWriter writer = new OutputStreamWriter(output);
			int terrainSizeY = terrain.getSizeY();
			int terrainSizeX = terrain.getSizeX();

			// land layer
			for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
				for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
					double tile = terrain.getLandLayer().getTile(tileIndexX, tileIndexY);
					writer.write(Double.toString(tile));
					writer.write(",");
				}
				writer.write("\n");
			}
			writer.write("\n");

			// water layer
			if (terrain.getWaterLayer() == null) {
				writer.write("-\n");
			} else {
				for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
					for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
						double tile = terrain.getWaterLayer().getTile(tileIndexX, tileIndexY);
						writer.write(Double.toString(tile));
						writer.write(",");
					}
					writer.write("\n");
				}
			}
			writer.write("\n");

			// magma layer
			if (terrain.getMagmaLayer() == null) {
				writer.write("-\n");
			} else {
				for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
					for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
						double tile = terrain.getMagmaLayer().getTile(tileIndexX, tileIndexY);
						writer.write(Double.toString(tile));
						writer.write(",");
					}
					writer.write("\n");
				}
			}
			writer.write("\n");

			// It is needed to flush the stream, otherwise the data may be cut off
			writer.flush();
		}
	}

}
