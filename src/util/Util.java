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

package util;

/**
 * Math utilities.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 *
 */
public class Util {

	public static double square(double x) {
		return x * x;
	}

	public static double power(double base, double exponent) {
		return Math.pow(base, exponent);
	}

	public static double euclideanDistance(double y1, double x1, double y2, double x2) {
		return Math.sqrt(square(y2 - y1) + square(x2 - x1));
	}

	public static double manhattanDistance(double y1, double x1, double y2, double x2) {
		return Math.abs(y2 - y1) + Math.abs(x2 - x1);
	}

	public static double chebyshevDistance(double y1, double x1, double y2, double x2) {
		return Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));
	}

	public static double polarToCartesianX(double angle, double radius) {
		return radius * Math.cos(angle * 2 * Math.PI);
	}

	public static double polarToCartesianY(double angle, double radius) {
		return radius * Math.sin(angle * 2 * Math.PI);
	}

	public static double cartesianToPolarAngle(double y, double x) {
		return (Math.atan2(y, x) / (Math.PI * 2)) + 1.0d;
	}

	public static double cartesianToPolarRadius(double y, double x) {
		return Math.sqrt(square(y) + square(x));
	}

	public static int bound(int min, int max, int x) {
		if(x <= min) {
			return min;
		} else if(x >= max) {
			return max;
		} else {
			return x;
		}
	}

}
