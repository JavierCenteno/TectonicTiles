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

package core.crease;

import core.Crease;
import util.Util;

/**
 * Crease function using an interpolation function such that its value is 0 at 0, 1 at 1 and a given value at 0.5 having a zero derivative at 0 and 1.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 * @see core.Crease
 *
 */
public class GeneralizedSmoothStep implements Crease {

	private final double heightFactor;
	private final double heightPower;
	private final double radiusFactor;
	private final double radiusPower;
	private final double middlePointFactor;
	private final double middlePointPower;

	public GeneralizedSmoothStep(double heightFactor, double heightPower, double radiusFactor, double radiusPower, double middlePointFactor, double middlePointPower) {
		this.heightFactor = heightFactor;
		this.heightPower = heightPower;
		this.radiusFactor = radiusFactor;
		this.radiusPower = radiusPower;
		this.middlePointFactor = middlePointFactor;
		this.middlePointPower = middlePointPower;
	}

	@Override
	public double valueAt(double startX, double startY, double endX, double endY, double thisX, double thisY) {
		/*
		 * Calculate distance to end tile normalized to distance between start and end
		 * tiles
		 */
		// Magnitude of the movement vector
		final double movementVectorMagnitude = Util.euclideanDistance(startY, startX, endY, endX);
		// Height of the crease function
		final double height = Util.power(movementVectorMagnitude, heightPower) * heightFactor;
		// Radius of the crease function
		final double radius = Util.power(movementVectorMagnitude, radiusPower) * radiusFactor;
		// Height of the middle point of the interpolation function
		final double middlePoint = Util.power(movementVectorMagnitude, middlePointPower) * middlePointFactor;
		// Distance from the target tile to the center of the crease function
		final double distanceToCenter = Util.euclideanDistance(thisY, thisX, endY, endX);
		// If the target tile is outside of the tiles affected by the function, height
		// is zero.
		if (distanceToCenter > radius) {
			return 0;
		}
		// Distance from the target tile to the edge of the crease function
		final double distanceToEdge = radius - distanceToCenter;
		// Distance from the target tile to the edge of the crease function,
		// normalized so that the edge is at a distance of 1
		final double distanceToEdgeNormalized = distanceToEdge / radius;
		/*
		 * Calculate interpolation function
		 */
		final double a = middlePoint / height;
		final double x = distanceToEdgeNormalized;
		final double x2 = x * x;
		final double x3 = x2 * x;
		final double x4 = x3 * x;
		final double y = (16 * a - 8) * x4 + (-32 * a + 14) * x3 + (16 * a - 5) * x2;
		// Result
		return y * height;
	}

}
