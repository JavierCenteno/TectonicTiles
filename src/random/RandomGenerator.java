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

package random;

public interface RandomGenerator {

	/**
	 * 
	 */
	public long generateLong();

	/**
	 * Get a random long integer uniformly distributed between 0 (inclusive) and a
	 * given number (exclusive).
	 * 
	 * The arguments are treated as unsigned, so if the maximum is negative the
	 * result will be distributed in the entire range of long except the one between
	 * the given number (inclusive) and 0 (exclusive).
	 *
	 * @param maximum Maximum value of the result (exclusive).
	 * @return A random long integer.
	 */
	public default long generateLong(final long maximum) {
		long result;
		// Distance from the highest multiple of max to the long range
		final long moduloBias = Long.remainderUnsigned(-maximum, maximum);
		if (moduloBias == 0) {
			result = this.generateLong();
		} else {
			// Maximum value without modulo bias, exclusive
			// Note this would result in an infinite loop if moduloBias == 0
			final long unbiasedMaximum = 0 - moduloBias;
			do {
				result = this.generateLong();
			} while (Long.compareUnsigned(result, unbiasedMaximum) >= 0);// result >= unbiasedMaximum);
		}
		return Long.remainderUnsigned(result, maximum);
	}

	/**
	 * Get a random long integer uniformly distributed between a given number
	 * (inclusive) and another given number (exclusive).
	 *
	 * @param minimum Minimum value of the result (inclusive).
	 * @param maximum Maximum value of the result (exclusive).
	 * @return A random long integer.
	 */
	public default long generateLong(final long minimum, final long maximum) {
		long range = maximum - minimum;
		return minimum + generateLong(range);
	}

	/**
	 * Get a random double floating point uniformly distributed between 0
	 * (inclusive) and 1.0 (exclusive).
	 *
	 * @return A random double floating point.
	 */
	public default double generateDouble() {
		return this.generateLong((1L << 53)) / (double) (1L << 53);
	}

}
