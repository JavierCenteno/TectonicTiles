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

package io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Terrain;
import util.Util;

public class TerrainUtil {

	public static void printTerrain(Terrain terrain) {
		int terrainSizeY = terrain.getSizeY();
		int terrainSizeX = terrain.getSizeX();

		// TODO: settable parameter
		double seaLevelFactor = 1.0d;

		double highestTile = Double.MIN_VALUE;
		double lowestTile = Double.MAX_VALUE;
		for (int tileIndexY = 0; tileIndexY < terrainSizeY; ++tileIndexY) {
			for (int tileIndexX = 0; tileIndexX < terrainSizeX; ++tileIndexX) {
				double tile = terrain.getTile(tileIndexX, tileIndexY);
				if(tile > highestTile) {
					highestTile = tile;
				} else if(tile < lowestTile) {
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
					color |= 0xFF << 24;// alpha
					color |= 0x00 << 16;// red
					color |= 0xFF << 8;// green
					color |= 0xFF << 0;// blue
				} else if (tile > 0.0d) {
					color |= 0xFF << 24;// alpha
					color |= 0x00 << 16;// red
					color |= (0xFF - Util.toUnsignedByteWithoutOverflow((int) (128.0d * tile / tileDifference))) << 8;// green
					color |= 0x00 << 0;// blue
				} else if (tile < 0.0d) {
					color |= 0xFF << 24;// alpha
					color |= 0x00 << 16;// red
					color |= 0x00 << 0;// green
					color |= (0xFF - Util.toUnsignedByteWithoutOverflow((int) (-128.0d * tile / tileDifference)));// blue
				}
				bufferedImage.setRGB(tileIndexX, tileIndexY, color);
			}
		}
		final File file = new File("C:\\Users\\jacen\\Desktop\\world.png");
		try {
			ImageIO.write(bufferedImage, "PNG", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
