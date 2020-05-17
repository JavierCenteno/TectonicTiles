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
import random.RandomGenerator;

/**
 * A configurer that handles square terrains.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 * @see io.TerrainConfigurer
 * @see core.terrain.SquareTerrain
 *
 */
public class SquareTerrainConfigurer extends TerrainConfigurer<SquareTerrain> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString ERROR_NO_TERRAIN = new InternationalizedString(
			"console.command.error.noTerrain");
	private static final InternationalizedString TERRAIN_EXCEPTION_FORMAT = new InternationalizedString(
			"terrain.exception.format");

	////////////////////////////////////////////////////////////////////////////////
	// Parameters

	private final IntegerParameter plateSize;
	private final IntegerParameter numberOfPlatesX;
	private final IntegerParameter numberOfPlatesY;
	private final WaterParameters waterParameters;
	private final MagmaParameters magmaParameters;

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Get the parameter that represents the plate size.
	 * 
	 * @return The parameter that represents the plate size.
	 */
	public IntegerParameter getPlateSize() {
		return this.plateSize;
	}

	/**
	 * Get the parameter that represents the number of plates along the x axis.
	 * 
	 * @return The parameter that represents the number of plates along the x axis.
	 */
	public IntegerParameter getNumberOfPlatesX() {
		return this.numberOfPlatesX;
	}

	/**
	 * Get the parameter that represents the number of plates along the y axis.
	 * 
	 * @return The parameter that represents the number of plates along the y axis.
	 */
	public IntegerParameter getNumberOfPlatesY() {
		return this.numberOfPlatesY;
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
	public SquareTerrainConfigurer(String nameKey) {
		super(nameKey);
		this.plateSize = new IntegerParameter("terrain.plateSize", 16, 2, null, false);
		this.getParameters().add(this.plateSize);
		this.numberOfPlatesX = new IntegerParameter("terrain.numberOfPlatesX", 32, 1, null, false);
		this.getParameters().add(this.numberOfPlatesX);
		this.numberOfPlatesY = new IntegerParameter("terrain.numberOfPlatesY", 16, 1, null, false);
		this.getParameters().add(this.numberOfPlatesY);
		this.waterParameters = new WaterParameters();
		this.getOptionalParameters().add(this.waterParameters);
		this.magmaParameters = new MagmaParameters();
		this.getOptionalParameters().add(this.magmaParameters);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public SquareTerrain generate(RandomGenerator randomGenerator, Crease crease) {
		int numberOfPlatesX = this.numberOfPlatesX.getCurrentValue().getValue();
		int numberOfPlatesY = this.numberOfPlatesY.getCurrentValue().getValue();
		int plateSizeY = this.plateSize.getCurrentValue().getValue();
		int plateSizeX = this.plateSize.getCurrentValue().getValue();
		int centerTileIndexY = plateSizeY / 2;
		int centerTileIndexX = plateSizeX / 2;
		int terrainSizeY = plateSizeY * numberOfPlatesY;
		int terrainSizeX = plateSizeX * numberOfPlatesX;
		boolean hasWater = this.waterParameters.getDeterminer().getCurrentValue();
		boolean hasMagma = this.magmaParameters.getDeterminer().getCurrentValue();
		SquareTerrain terrain = new SquareTerrain(terrainSizeX, terrainSizeY, hasWater, hasMagma);

		// for all plates
		for (int plateIndexY = 0; plateIndexY < numberOfPlatesY; ++plateIndexY) {
			for (int plateIndexX = 0; plateIndexX < numberOfPlatesX; ++plateIndexX) {

				int startTileIndexY = plateIndexY * plateSizeY + centerTileIndexY;
				int startTileIndexX = plateIndexX * plateSizeX + centerTileIndexX;

				// randomly choose end tile
				int endTileIndexY = (int) randomGenerator.generateLong(plateIndexY * plateSizeY,
						(plateIndexY + 1) * plateSizeY);
				int endTileIndexX = (int) randomGenerator.generateLong(plateIndexX * plateSizeX,
						(plateIndexX + 1) * plateSizeX);

				for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
					for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {

						// set height
						double height = terrain.getHeightLayer().getTile(tileIndexX, tileIndexY);
						height += crease.valueAt(startTileIndexX, startTileIndexY, endTileIndexX, endTileIndexY,
								tileIndexX, tileIndexY);
						terrain.getHeightLayer().setTile(tileIndexX, tileIndexY, height);

						// set magma
						if (terrain.getMagmaLayer() != null) {
							double magma = terrain.getMagmaLayer().getTile(tileIndexX, tileIndexY);
							// magma is generated like height
							// but in the opposite direction of the plate movement
							magma += crease.valueAt(endTileIndexX, endTileIndexY, startTileIndexX, startTileIndexY,
									tileIndexX, tileIndexY);
							terrain.getMagmaLayer().setTile(tileIndexX, tileIndexY, magma);
						}

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
				double height = terrain.getHeightLayer().getTile(tileIndexX, tileIndexY);
				if (height > highestTile) {
					highestTile = height;
				} else if (height < lowestTile) {
					lowestTile = height;
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
				double height = terrain.getHeightLayer().getTile(tileIndexX, tileIndexY);
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
				int color = TileUtil.tileToColor(height, water, magma, lowestTile, highestTile);
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

				// height layer
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
							terrain.getHeightLayer().setTile(tileIndexX, tileIndexY, tile);
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

			// height layer
			for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
				for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
					double tile = terrain.getHeightLayer().getTile(tileIndexX, tileIndexY);
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
