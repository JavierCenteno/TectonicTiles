package random;

/**
 * Implementation of a xorshift64* PRNG.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.1
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
