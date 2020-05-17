/*
 * TileUtil.java
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

/**
 * Utilities related to tiles.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see io.TerrainConfigurer
 *
 */
public class TileUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Converts a tile to a 32-bit color with the first byte representing the alpha
	 * channel, the second byte representing the red channel, the third byte
	 * representing the green channel and the fourth byte representing the blue
	 * channel.
	 * 
	 * @param height  The height value of the tile.
	 * @param water   The water value of the tile. Should be negative infinity if
	 *                the terrain has no water layer.
	 * @param magma   The magma value of the tile. Should be negative infinity if
	 *                the terrain has no magma layer.
	 * @param minimum The minimum value among all tiles and layers in the terrain.
	 * @param maximum The maximum value among all tiles and layers in the terrain.
	 * @return A 32 bit color with the first byte representing the alpha channel,
	 *         the second byte representing the red channel, the third byte
	 *         representing the green channel and the fourth byte representing the
	 *         blue channel.
	 */
	public static int tileToColor(double height, double water, double magma, double minimum, double maximum) {
		double boundedHeight = (height - minimum) / (maximum - minimum);
		double boundedWater = (water - minimum) / (maximum - minimum);
		double boundedMagma = (magma - minimum) / (maximum - minimum);
		int alpha = 0xFF;
		int red = 0x00;
		int green = 0x00;
		int blue = 0x00;
		if (boundedMagma > boundedHeight && boundedMagma > boundedWater) {
			red = (int) (128.0d * boundedMagma);
		} else {
			if (boundedHeight > boundedWater) {
				if (Double.isFinite(boundedWater)) {
					// drier terrain should have a higher red level, that is, be yellower
					red = (int) (128.0d * (boundedHeight - boundedWater));
				}
				// lower areas are lighter, higher areas are darker
				green = 0xFF - (int) (128.0d * boundedHeight);
			} else {
				// shallower waters are lighter, deeper waters are darker
				blue = 0xFF - (int) (128.0d * (boundedWater - boundedHeight));
			}
		}
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

}
