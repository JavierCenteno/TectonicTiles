/*
 * Xorshift64StarGenerator.java
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

package random;

/**
 * Implementation of a xorshift64* PRNG.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.1
 * @see random.RandomGenerator
 *
 */
public class Xorshift64StarGenerator implements RandomGenerator {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * State of this generator.
	 */
	private long state;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Constructs a generator with a randomly chosen seed.
	 */
	public Xorshift64StarGenerator() {
		this(System.currentTimeMillis());
		// Generate a long to "warm up" the generator
		this.generateLong();
	}

	/**
	 * Constructs a generator with the given seed as the initial state.
	 * 
	 * @param seed A seed.
	 */
	public Xorshift64StarGenerator(long seed) {
		this.state = seed;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	@Override
	public long generateLong() {
		this.state ^= this.state >>> 12;
		this.state ^= this.state << 25;
		this.state ^= this.state >>> 27;
		return this.state * 0x2545F4914F6CDD1DL;
	}

}
