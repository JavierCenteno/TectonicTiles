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

package core;

import random.RandomGenerator;
import random.Xorshift64StarGenerator;

/**
 * A generator which generates a terrain of a specific type using provided
 * parameters.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.2
 * @since 0.2
 * @param <T> The type of terrain generated by this generator.
 * @see core.Terrain
 *
 */
public abstract class Generator<T extends Terrain> {

	////////////////////////////////////////////////////////////////////////////////
	// Generator parameters

	private final long seed;
	protected RandomGenerator randomGenerator;
	protected T terrain;
	protected Crease crease;

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	public long getSeed() {
		return seed;
	}

	public void initializeGenerator() {
		this.randomGenerator = new Xorshift64StarGenerator(this.seed);
	}

	public T getTerrain() {
		return terrain;
	}

	public Crease getCrease() {
		return crease;
	}

	public void setCrease(Crease crease) {
		this.crease = crease;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Instantiates a generator with default parameters.
	 */
	public Generator(long seed) {
		this.seed = seed;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Generation methods

	public abstract void generate();

}
