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

package crease;

public interface Crease {

	/**
	 * Calculates the influence of the movement vector starting at the tile with coordinates startX, startY and ending at the tile with coordinates endX, endY on the tile with coordinates thisX, thisY.
	 * 
	 * @param startX X coordinate of the starting tile of a movement vector.
	 * @param startY Y coordinate of the starting tile of a movement vector.
	 * @param endX X coordinate of the ending tile of a movement vector.
	 * @param endY Y coordinate of the ending tile of a movement vector.
	 * @param thisX X coordinate of the tile on which we're calculating the influence of the given movement vector.
	 * @param thisY Y coordinate of the tile on which we're calculating the influence of the given movement vector.
	 * @return The influence of the movement vector starting at the tile with coordinates startX, startY and ending at the tile with coordinates endX, endY on the tile with coordinates thisX, thisY.
	 */
	public double valueAt(int startX, int startY, int endX, int endY, int thisX, int thisY);

}
