/*
 * SmoothStepFixedVolume.java
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
 * Crease function using an interpolation function such that its value is 0 at
 * 0, 1 at 1 and a given value at 0.5 having a zero derivative at 0 and 1.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 * @see core.Crease
 *
 */
public class SmoothStepFixedVolume implements Crease {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private final double heightFactor;
	private final double heightPower;
	private final double radiusFactor;
	private final double radiusPower;
	private final double targetVolume;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Construct a new smooth step fixed volume crease function.
	 * 
	 * @param heightFactor The factor by which the height of the function is
	 *                     multiplied.
	 * @param heightPower  The power to which the height of the function is raised.
	 * @param radiusFactor The factor by which the width of the function is
	 *                     multiplied.
	 * @param radiusPower  The power to which the width of the function is raised.
	 * @param targetVolume The target volume of the function.
	 */
	public SmoothStepFixedVolume(double heightFactor, double heightPower, double radiusFactor, double radiusPower,
			double targetVolume) {
		this.heightFactor = heightFactor;
		this.heightPower = heightPower;
		this.radiusFactor = radiusFactor;
		this.radiusPower = radiusPower;
		this.targetVolume = targetVolume;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public double valueAt(double startX, double startY, double endX, double endY, double thisX, double thisY) {
		/*
		 * Calculate distance to end tile normalized to distance between start and end
		 * tiles
		 */
		// Magnitude of the movement vector
		final double movementVectorMagnitude = Math.euclideanDistance(startY, startX, endY, endX);
		// Height of the crease function
		final double height = Math.power(movementVectorMagnitude, heightPower) * heightFactor;
		// Radius of the crease function
		final double radius = Math.power(movementVectorMagnitude, radiusPower) * radiusFactor;
		// Distance from the target tile to the center of the crease function
		final double distanceToCenter = Math.euclideanDistance(thisY, thisX, endY, endX);
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
		final double a = ((targetVolume / (height * radius)) - 14) / 32;
		final double x = distanceToEdgeNormalized;
		final double x2 = x * x;
		final double x3 = x2 * x;
		final double x4 = x3 * x;
		final double y = (16 * a - 8) * x4 + (-32 * a + 14) * x3 + (16 * a - 5) * x2;
		// Result
		return y * height;
	}

}
