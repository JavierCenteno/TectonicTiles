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

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import core.terrain.SquareTerrain;
import util.Util;

/**
 * Utilities to export and import terrains to other forms of data.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 * @see core.Terrain
 *
 */
public class TerrainUtil {

	public static RenderedImage printTerrain(Terrain terrain) {
		if(terrain.getClass().equals(SquareTerrain.class)) {
			return printSquareTerrain((SquareTerrain) terrain);
		}
		throw new IllegalArgumentException("Terrain class not supported.");
	}

	public static RenderedImage printSquareTerrain(SquareTerrain terrain) {
		int terrainSizeY = terrain.getSizeY();
		int terrainSizeX = terrain.getSizeX();

		// TODO: settable parameter
		double seaLevelFactor = 1.0d;

		double highestTile = Double.MIN_VALUE;
		double lowestTile = Double.MAX_VALUE;
		for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
			for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
				double tile = terrain.getTile(tileIndexX, tileIndexY);
				if (tile > highestTile) {
					highestTile = tile;
				} else if (tile < lowestTile) {
					lowestTile = tile;
				}
			}
		}
		double middleTile = (highestTile + lowestTile) / 2.0d;
		double tileDifference = highestTile - middleTile;

		final BufferedImage bufferedImage = new BufferedImage(terrainSizeX, terrainSizeY, BufferedImage.TYPE_INT_ARGB);
		for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
			for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
				int color = 0;
				double tile = terrain.getTile(tileIndexX, tileIndexY) - (seaLevelFactor * middleTile);
				if (tile == 0.0d) {
					// alpha
					color |= 0xFF << 24;
					// red
					color |= 0x00 << 16;
					// green
					color |= 0xFF << 8;
					// blue
					color |= 0xFF << 0;
				} else if (tile > 0.0d) {
					// alpha
					color |= 0xFF << 24;
					// red
					color |= 0x00 << 16;
					// green
					color |= (0xFF - Util.bound(0x00, 0xFF, (int) (128.0d * tile / tileDifference))) << 8;
					// blue
					color |= 0x00 << 0;
				} else if (tile < 0.0d) {
					// alpha
					color |= 0xFF << 24;
					// red
					color |= 0x00 << 16;
					// green
					color |= 0x00 << 0;
					// blue
					color |= (0xFF - Util.bound(0x00, 0xFF, (int) (-128.0d * tile / tileDifference)));
				}
				bufferedImage.setRGB(tileIndexX, tileIndexY, color);
			}
		}
		return bufferedImage;
	}

}
