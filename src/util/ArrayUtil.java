/*
 * ArrayUtil.java
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
 * Utilities to work with arrays.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.4
 * @since 0.4
 *
 */
public class ArrayUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Creates a new array with the element at the given index removed.
	 * 
	 * @param array An array.
	 * @param index An index.
	 * @return A new array with the element at the given index removed.
	 */
	public static int[] remove(int[] array, int index) {
		int[] newArray = new int[array.length - 1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
		return newArray;
	}

	/**
	 * Creates a new array with the given element added at the end.
	 * 
	 * @param array   An array.
	 * @param element An element to be added to the array.
	 * @return A new array with the given element added at the end.
	 */
	public static int[] add(int[] array, int element) {
		int[] newArray = new int[array.length + 1];
		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = element;
		return newArray;
	}

}
