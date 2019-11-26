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

import util.Util;

/**
 * Definition of the pyramid crease function. This function uses the Chebyshev distance to the center to determine its value.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.1
 * @since 0.1
 * @see crease.Crease
 *
 */
public class Pyramid implements Crease {

	private final double heightFactor;
	private final double heightPower;
	private final double radiusFactor;
	private final double radiusPower;

	public Pyramid(double heightFactor, double heightPower, double radiusFactor, double radiusPower) {
		this.heightFactor = heightFactor;
		this.heightPower = heightPower;
		this.radiusFactor = radiusFactor;
		this.radiusPower = radiusPower;
	}

	@Override
	public double valueAt(int startX, int startY, int endX, int endY, int thisX, int thisY) {
		// Magnitude of the movement vector
		final double movementVectorMagnitude = Util.chebyshevDistance(startY, startX, endY, endX);
		// Height of the crease function
		final double height = Util.power(movementVectorMagnitude, heightPower) * heightFactor;
		// Radius of the crease function
		final double radius = Util.power(movementVectorMagnitude, radiusPower) * radiusFactor;
		// Distance from the target tile to the center of the crease function
		final double distanceToCenter = Util.chebyshevDistance(thisY, thisX, endY, endX);
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
