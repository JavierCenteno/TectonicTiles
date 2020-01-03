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

/**
 * Definition of the delta crease function. This function equals 1 at the center, 0 elsewhere.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 * @see core.Crease
 *
 */
public class Delta implements Crease {

	public Delta() {
	}

	@Override
	public double valueAt(double startX, double startY, double endX, double endY, double thisX, double thisY) {
		if (thisY == endY) {
			if (thisX == endX) {
				return 1.0d;
			}
		}
		return 0.0d;
	}

}
