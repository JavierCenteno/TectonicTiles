/*
 * Diamond.java
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

package core.crease;

import core.Crease;
import util.Math;

/**
 * Definition of the diamond crease function. This function uses the Manhattan distance to the center to determine its value.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 * @see core.Crease
 *
 */
public class Diamond implements Crease {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private final double heightFactor;
	private final double heightPower;
	private final double radiusFactor;
	private final double radiusPower;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Construct a new diamond crease function.
	 * 
	 * @param heightFactor The factor by which the height of the function is
	 *                     multiplied.
	 * @param heightPower  The power to which the height of the function is raised.
	 * @param radiusFactor The factor by which the width of the function is
	 *                     multiplied.
	 * @param radiusPower  The power to which the width of the function is raised.
	 */
	public Diamond(double heightFactor, double heightPower, double radiusFactor, double radiusPower) {
		this.heightFactor = heightFactor;
		this.heightPower = heightPower;
		this.radiusFactor = radiusFactor;
		this.radiusPower = radiusPower;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public double valueAt(double startX, double startY, double endX, double endY, double thisX, double thisY) {
		// Magnitude of the movement vector
		final double movementVectorMagnitude = Math.manhattanDistance(startY, startX, endY, endX);
		// Height of the crease function
		final double height = Math.power(movementVectorMagnitude, heightPower) * heightFactor;
		// Radius of the crease function
		final double radius = Math.power(movementVectorMagnitude, radiusPower) * radiusFactor;
		// Distance from the target tile to the center of the crease function
		final double distanceToCenter = Math.manhattanDistance(thisY, thisX, endY, endX);
		// If the target tile is outside of the tiles affected by the function, height is zero.
		if (distanceToCenter > radius) {
			return 0;
		}
		// Distance from the target tile to the edge of the crease function
		final double distanceToEdge = radius - distanceToCenter;
		// Distance from the target tile to the edge of the crease function,
		// normalized so that the edge is at a distance of 1
		final double distanceToEdgeNormalized = distanceToEdge / radius;
		// Interpolate
		return distanceToEdgeNormalized * height;
	}

}
