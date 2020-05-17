/*
 * Math.java
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

package util;

/**
 * Math utilities.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 *
 */
public class Math {

	////////////////////////////////////////////////////////////////////////////////
	// Arithmetic

	/**
	 * Calculates the power of the given base to the given exponent.
	 * 
	 * @param base A base.
	 * @param exponent An exponent.
	 * @return The power of the given base to the given exponent.
	 * @see java.lang.Math#pow(double, double)
	 */
	public static double power(double base, double exponent) {
		return java.lang.Math.pow(base, exponent);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Distance

	/**
	 * Calculates the euclidean distance between two points.
	 * 
	 * @param y1 The y coordinate of a point.
	 * @param x1 The x coordinate of a point.
	 * @param y2 The y coordinate of a point.
	 * @param x2 The x coordinate of a point.
	 * @return The euclidean distance between the points with the given coordinates.
	 */
	public static double euclideanDistance(double y1, double x1, double y2, double x2) {
		double deltaY = y2 - y1;
		double deltaX = x2 - x1;
		return java.lang.Math.sqrt(deltaY * deltaY + deltaX * deltaX);
	}

	/**
	 * Calculates the manhattan distance between two points.
	 * 
	 * @param y1 The y coordinate of a point.
	 * @param x1 The x coordinate of a point.
	 * @param y2 The y coordinate of a point.
	 * @param x2 The x coordinate of a point.
	 * @return The manhattan distance between the points with the given coordinates.
	 */
	public static double manhattanDistance(double y1, double x1, double y2, double x2) {
		return java.lang.Math.abs(y2 - y1) + java.lang.Math.abs(x2 - x1);
	}

	/**
	 * Calculates the chebyshev distance between two points.
	 * 
	 * @param y1 The y coordinate of a point.
	 * @param x1 The x coordinate of a point.
	 * @param y2 The y coordinate of a point.
	 * @param x2 The x coordinate of a point.
	 * @return The chebyshev distance between the points with the given coordinates.
	 */
	public static double chebyshevDistance(double y1, double x1, double y2, double x2) {
		return java.lang.Math.max(java.lang.Math.abs(y2 - y1), java.lang.Math.abs(x2 - x1));
	}

	/**
	 * Calculates the cartesian x coordinate of a point with the given polar coordinates.
	 * 
	 * @param angle An angle.
	 * @param radius A radius.
	 * @return The cartesian x coordinate of a point with the given polar coordinates.
	 */
	public static double polarToCartesianX(double angle, double radius) {
		return radius * java.lang.Math.cos(angle * 2 * java.lang.Math.PI);
	}

	/**
	 * Calculates the cartesian y coordinate of a point with the given polar coordinates.
	 * 
	 * @param angle An angle.
	 * @param radius A radius.
	 * @return The cartesian y coordinate of a point with the given polar coordinates.
	 */
	public static double polarToCartesianY(double angle, double radius) {
		return radius * java.lang.Math.sin(angle * 2 * java.lang.Math.PI);
	}

	/**
	 * Calculates the polar coordinate angle of a point with the cartesian coordinates.
	 * 
	 * @param y A cartesian y coordinate.
	 * @param x A cartesian x coordinate.
	 * @return The polar coordinate angle of a point with the cartesian coordinates.
	 */
	public static double cartesianToPolarAngle(double y, double x) {
		return (java.lang.Math.atan2(y, x) / (java.lang.Math.PI * 2)) + 1.0d;
	}

	/**
	 * Calculates the polar coordinate radius of a point with the cartesian coordinates.
	 * 
	 * @param y A cartesian y coordinate.
	 * @param x A cartesian x coordinate.
	 * @return The polar coordinate radius of a point with the cartesian coordinates.
	 */
	public static double cartesianToPolarRadius(double y, double x) {
		return java.lang.Math.sqrt(y * y + x * x);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Bound

	/**
	 * Ensures that the parameter x is within the given bounds.
	 * 
	 * @param x A value.
	 * @param min The minimum value x can have.
	 * @param max The maximum value x can have.
	 * @return x if min < x < max, min if x < min, max if max < x
	 */
	public static byte bound(byte x, byte min, byte max) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

	/**
	 * Ensures that the parameter x is within the given bounds.
	 * 
	 * @param x A value.
	 * @param min The minimum value x can have.
	 * @param max The maximum value x can have.
	 * @return x if min < x < max, min if x < min, max if max < x
	 */
	public static short bound(short x, short min, short max) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

	/**
	 * Ensures that the parameter x is within the given bounds.
	 * 
	 * @param x A value.
	 * @param min The minimum value x can have.
	 * @param max The maximum value x can have.
	 * @return x if min < x < max, min if x < min, max if max < x
	 */
	public static int bound(int x, int min, int max) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

	/**
	 * Ensures that the parameter x is within the given bounds.
	 * 
	 * @param x A value.
	 * @param min The minimum value x can have.
	 * @param max The maximum value x can have.
	 * @return x if min < x < max, min if x < min, max if max < x
	 */
	public static long bound(long x, long min, long max) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

	/**
	 * Ensures that the parameter x is within the given bounds.
	 * 
	 * @param x A value.
	 * @param min The minimum value x can have.
	 * @param max The maximum value x can have.
	 * @return x if min < x < max, min if x < min, max if max < x
	 */
	public static float bound(float x, float min, float max) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

	/**
	 * Ensures that the parameter x is within the given bounds.
	 * 
	 * @param x A value.
	 * @param min The minimum value x can have.
	 * @param max The maximum value x can have.
	 * @return x if min < x < max, min if x < min, max if max < x
	 */
	public static double bound(double x, double min, double max) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

}
