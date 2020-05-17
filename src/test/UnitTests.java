/*
 * UnitTests.java
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

package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

import main.TestMode;
import parameter.i18n.InternationalizedBigDecimal;
import parameter.i18n.InternationalizedBigInteger;
import parameter.i18n.InternationalizedByte;
import parameter.i18n.InternationalizedDouble;
import parameter.i18n.InternationalizedFloat;
import parameter.i18n.InternationalizedInteger;
import parameter.i18n.InternationalizedLong;
import parameter.i18n.InternationalizedShort;
import parameter.parameter.BigDecimalParameter;
import parameter.parameter.BigIntegerParameter;
import parameter.parameter.ByteParameter;
import parameter.parameter.DoubleParameter;
import parameter.parameter.FloatParameter;
import parameter.parameter.IntegerParameter;
import parameter.parameter.LongParameter;
import parameter.parameter.OptionParameter;
import parameter.parameter.OptionParameter.Option;
import parameter.parameter.ShortParameter;

/**
 * This class contains tests to be run by the test mode.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see main#TestMode
 *
 */
public class UnitTests {

	/**
	 * Run the tests.
	 */
	@SuppressWarnings("unchecked")
	public static void run() {

		// ByteParameter tests

		Constructor<ByteParameter> byteParameterConstructor = TestMode.getConstructor(ByteParameter.class, String.class,
				Byte.class, Byte.class, Byte.class, boolean.class);

		TestMode.Tests.constructorFailureCheckException(byteParameterConstructor, NullPointerException.class, null,
				(byte) 0, (byte) -1, (byte) 1, true);
		TestMode.Tests.constructorFailureCheckException(byteParameterConstructor, IllegalArgumentException.class, "",
				null, (byte) -1, (byte) 1, false);
		TestMode.Tests.constructorFailureCheckException(byteParameterConstructor, IllegalArgumentException.class, "",
				(byte) -2, (byte) -1, (byte) 1, true);
		TestMode.Tests.constructorFailureCheckException(byteParameterConstructor, IllegalArgumentException.class, "",
				(byte) 2, (byte) -1, (byte) 1, true);
		TestMode.Tests.constructorFailureCheckException(byteParameterConstructor, IllegalArgumentException.class, "",
				(byte) 0, (byte) 1, (byte) -1, true);
		TestMode.Tests.constructorSuccess(byteParameterConstructor, "", (byte) 0, (byte) 0, (byte) 0, true);

		ByteParameter byteParameterAllAllowed = null;
		ByteParameter byteParameterNegative = null;
		ByteParameter byteParameterPositive = null;
		ByteParameter byteParameterNoNull = null;

		try {
			byteParameterAllAllowed = TestMode.invokeConstructor(byteParameterConstructor, "", (byte) 0, null, null,
					true);
			byteParameterNegative = TestMode.invokeConstructor(byteParameterConstructor, "", (byte) 0, null, (byte) 0,
					true);
			byteParameterPositive = TestMode.invokeConstructor(byteParameterConstructor, "", (byte) 0, (byte) 0, null,
					true);
			byteParameterNoNull = TestMode.invokeConstructor(byteParameterConstructor, "", (byte) 0, null, null, false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method byteParameterGetCurrentValue = TestMode.getMethod(ByteParameter.class, "getCurrentValue");
		Method byteParameterSetCurrentValue = TestMode.getMethod(ByteParameter.class, "setCurrentValue",
				InternationalizedByte.class);

		TestMode.Tests.methodSuccess(byteParameterSetCurrentValue, byteParameterAllAllowed,
				InternationalizedByte.of((byte) 1));
		TestMode.Tests.methodSuccessCheckResult(byteParameterGetCurrentValue, InternationalizedByte.of((byte) 1),
				byteParameterAllAllowed);
		TestMode.Tests.methodSuccess(byteParameterSetCurrentValue, byteParameterAllAllowed,
				InternationalizedByte.of((byte) -1));
		TestMode.Tests.methodSuccessCheckResult(byteParameterGetCurrentValue, InternationalizedByte.of((byte) -1),
				byteParameterAllAllowed);
		TestMode.Tests.methodSuccess(byteParameterSetCurrentValue, byteParameterAllAllowed, new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(byteParameterGetCurrentValue, InternationalizedByte.of(null),
				byteParameterAllAllowed);
		TestMode.Tests.methodSuccess(byteParameterSetCurrentValue, byteParameterAllAllowed,
				InternationalizedByte.of(null));
		TestMode.Tests.methodSuccessCheckResult(byteParameterGetCurrentValue, InternationalizedByte.of(null),
				byteParameterAllAllowed);

		TestMode.Tests.methodSuccess(byteParameterSetCurrentValue, byteParameterNegative,
				InternationalizedByte.of((byte) -1));
		TestMode.Tests.methodSuccessCheckResult(byteParameterGetCurrentValue, InternationalizedByte.of((byte) -1),
				byteParameterNegative);
		TestMode.Tests.methodFailureCheckException(byteParameterSetCurrentValue, IllegalArgumentException.class,
				byteParameterNegative, InternationalizedByte.of((byte) 1));

		TestMode.Tests.methodFailureCheckException(byteParameterSetCurrentValue, IllegalArgumentException.class,
				byteParameterPositive, InternationalizedByte.of((byte) -1));
		TestMode.Tests.methodSuccess(byteParameterSetCurrentValue, byteParameterPositive,
				InternationalizedByte.of((byte) 1));
		TestMode.Tests.methodSuccessCheckResult(byteParameterGetCurrentValue, InternationalizedByte.of((byte) 1),
				byteParameterPositive);

		TestMode.Tests.methodFailureCheckException(byteParameterSetCurrentValue, IllegalArgumentException.class,
				byteParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(byteParameterSetCurrentValue, IllegalArgumentException.class,
				byteParameterNoNull, InternationalizedByte.of(null));

		// ShortParameter tests

		Constructor<ShortParameter> shortParameterConstructor = TestMode.getConstructor(ShortParameter.class,
				String.class, Short.class, Short.class, Short.class, boolean.class);

		TestMode.Tests.constructorFailureCheckException(shortParameterConstructor, NullPointerException.class, null,
				(short) 0, (short) -1, (short) 1, true);
		TestMode.Tests.constructorFailureCheckException(shortParameterConstructor, IllegalArgumentException.class, "",
				null, (short) -1, (short) 1, false);
		TestMode.Tests.constructorFailureCheckException(shortParameterConstructor, IllegalArgumentException.class, "",
				(short) -2, (short) -1, (short) 1, true);
		TestMode.Tests.constructorFailureCheckException(shortParameterConstructor, IllegalArgumentException.class, "",
				(short) 2, (short) -1, (short) 1, true);
		TestMode.Tests.constructorFailureCheckException(shortParameterConstructor, IllegalArgumentException.class, "",
				(short) 0, (short) 1, (short) -1, true);
		TestMode.Tests.constructorSuccess(shortParameterConstructor, "", (short) 0, (short) 0, (short) 0, true);

		ShortParameter shortParameterAllAllowed = null;
		ShortParameter shortParameterNegative = null;
		ShortParameter shortParameterPositive = null;
		ShortParameter shortParameterNoNull = null;

		try {
			shortParameterAllAllowed = TestMode.invokeConstructor(shortParameterConstructor, "", (short) 0, null, null,
					true);
			shortParameterNegative = TestMode.invokeConstructor(shortParameterConstructor, "", (short) 0, null,
					(short) 0, true);
			shortParameterPositive = TestMode.invokeConstructor(shortParameterConstructor, "", (short) 0, (short) 0,
					null, true);
			shortParameterNoNull = TestMode.invokeConstructor(shortParameterConstructor, "", (short) 0, null, null,
					false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method shortParameterGetCurrentValue = TestMode.getMethod(ShortParameter.class, "getCurrentValue");
		Method shortParameterSetCurrentValue = TestMode.getMethod(ShortParameter.class, "setCurrentValue",
				InternationalizedShort.class);

		TestMode.Tests.methodSuccess(shortParameterSetCurrentValue, shortParameterAllAllowed,
				InternationalizedShort.of((short) 1));
		TestMode.Tests.methodSuccessCheckResult(shortParameterGetCurrentValue, InternationalizedShort.of((short) 1),
				shortParameterAllAllowed);
		TestMode.Tests.methodSuccess(shortParameterSetCurrentValue, shortParameterAllAllowed,
				InternationalizedShort.of((short) -1));
		TestMode.Tests.methodSuccessCheckResult(shortParameterGetCurrentValue, InternationalizedShort.of((short) -1),
				shortParameterAllAllowed);
		TestMode.Tests.methodSuccess(shortParameterSetCurrentValue, shortParameterAllAllowed, new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(shortParameterGetCurrentValue, InternationalizedShort.of(null),
				shortParameterAllAllowed);
		TestMode.Tests.methodSuccess(shortParameterSetCurrentValue, shortParameterAllAllowed,
				InternationalizedShort.of(null));
		TestMode.Tests.methodSuccessCheckResult(shortParameterGetCurrentValue, InternationalizedShort.of(null),
				shortParameterAllAllowed);

		TestMode.Tests.methodSuccess(shortParameterSetCurrentValue, shortParameterNegative,
				InternationalizedShort.of((short) -1));
		TestMode.Tests.methodSuccessCheckResult(shortParameterGetCurrentValue, InternationalizedShort.of((short) -1),
				shortParameterNegative);
		TestMode.Tests.methodFailureCheckException(shortParameterSetCurrentValue, IllegalArgumentException.class,
				shortParameterNegative, InternationalizedShort.of((short) 1));

		TestMode.Tests.methodFailureCheckException(shortParameterSetCurrentValue, IllegalArgumentException.class,
				shortParameterPositive, InternationalizedShort.of((short) -1));
		TestMode.Tests.methodSuccess(shortParameterSetCurrentValue, shortParameterPositive,
				InternationalizedShort.of((short) 1));
		TestMode.Tests.methodSuccessCheckResult(shortParameterGetCurrentValue, InternationalizedShort.of((short) 1),
				shortParameterPositive);

		TestMode.Tests.methodFailureCheckException(shortParameterSetCurrentValue, IllegalArgumentException.class,
				shortParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(shortParameterSetCurrentValue, IllegalArgumentException.class,
				shortParameterNoNull, InternationalizedShort.of(null));

		// IntegerParameter tests

		Constructor<IntegerParameter> integerParameterConstructor = TestMode.getConstructor(IntegerParameter.class,
				String.class, Integer.class, Integer.class, Integer.class, boolean.class);

		TestMode.Tests.constructorFailureCheckException(integerParameterConstructor, NullPointerException.class, null,
				0, -1, 1, true);
		TestMode.Tests.constructorFailureCheckException(integerParameterConstructor, IllegalArgumentException.class, "",
				null, -1, 1, false);
		TestMode.Tests.constructorFailureCheckException(integerParameterConstructor, IllegalArgumentException.class, "",
				-2, -1, 1, true);
		TestMode.Tests.constructorFailureCheckException(integerParameterConstructor, IllegalArgumentException.class, "",
				2, -1, 1, true);
		TestMode.Tests.constructorFailureCheckException(integerParameterConstructor, IllegalArgumentException.class, "",
				0, 1, -1, true);
		TestMode.Tests.constructorSuccess(integerParameterConstructor, "", 0, 0, 0, true);

		IntegerParameter integerParameterAllAllowed = null;
		IntegerParameter integerParameterNegative = null;
		IntegerParameter integerParameterPositive = null;
		IntegerParameter integerParameterNoNull = null;

		try {
			integerParameterAllAllowed = TestMode.invokeConstructor(integerParameterConstructor, "", 0, null, null,
					true);
			integerParameterNegative = TestMode.invokeConstructor(integerParameterConstructor, "", 0, null, 0, true);
			integerParameterPositive = TestMode.invokeConstructor(integerParameterConstructor, "", 0, 0, null, true);
			integerParameterNoNull = TestMode.invokeConstructor(integerParameterConstructor, "", 0, null, null, false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method integerParameterGetCurrentValue = TestMode.getMethod(IntegerParameter.class, "getCurrentValue");
		Method integerParameterSetCurrentValue = TestMode.getMethod(IntegerParameter.class, "setCurrentValue",
				InternationalizedInteger.class);

		TestMode.Tests.methodSuccess(integerParameterSetCurrentValue, integerParameterAllAllowed,
				InternationalizedInteger.of(1));
		TestMode.Tests.methodSuccessCheckResult(integerParameterGetCurrentValue, InternationalizedInteger.of(1),
				integerParameterAllAllowed);
		TestMode.Tests.methodSuccess(integerParameterSetCurrentValue, integerParameterAllAllowed,
				InternationalizedInteger.of(-1));
		TestMode.Tests.methodSuccessCheckResult(integerParameterGetCurrentValue, InternationalizedInteger.of(-1),
				integerParameterAllAllowed);
		TestMode.Tests.methodSuccess(integerParameterSetCurrentValue, integerParameterAllAllowed,
				new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(integerParameterGetCurrentValue, InternationalizedInteger.of(null),
				integerParameterAllAllowed);
		TestMode.Tests.methodSuccess(integerParameterSetCurrentValue, integerParameterAllAllowed,
				InternationalizedInteger.of(null));
		TestMode.Tests.methodSuccessCheckResult(integerParameterGetCurrentValue, InternationalizedInteger.of(null),
				integerParameterAllAllowed);

		TestMode.Tests.methodSuccess(integerParameterSetCurrentValue, integerParameterNegative,
				InternationalizedInteger.of(-1));
		TestMode.Tests.methodSuccessCheckResult(integerParameterGetCurrentValue, InternationalizedInteger.of(-1),
				integerParameterNegative);
		TestMode.Tests.methodFailureCheckException(integerParameterSetCurrentValue, IllegalArgumentException.class,
				integerParameterNegative, InternationalizedInteger.of(1));

		TestMode.Tests.methodFailureCheckException(integerParameterSetCurrentValue, IllegalArgumentException.class,
				integerParameterPositive, InternationalizedInteger.of(-1));
		TestMode.Tests.methodSuccess(integerParameterSetCurrentValue, integerParameterPositive,
				InternationalizedInteger.of(1));
		TestMode.Tests.methodSuccessCheckResult(integerParameterGetCurrentValue, InternationalizedInteger.of(1),
				integerParameterPositive);

		TestMode.Tests.methodFailureCheckException(integerParameterSetCurrentValue, IllegalArgumentException.class,
				integerParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(integerParameterSetCurrentValue, IllegalArgumentException.class,
				integerParameterNoNull, InternationalizedInteger.of(null));

		// LongParameter tests

		Constructor<LongParameter> longParameterConstructor = TestMode.getConstructor(LongParameter.class, String.class,
				Long.class, Long.class, Long.class, boolean.class);

		TestMode.Tests.constructorFailureCheckException(longParameterConstructor, NullPointerException.class, null, 0L,
				-1L, 1L, true);
		TestMode.Tests.constructorFailureCheckException(longParameterConstructor, IllegalArgumentException.class, "",
				null, -1L, 1L, false);
		TestMode.Tests.constructorFailureCheckException(longParameterConstructor, IllegalArgumentException.class, "",
				-2L, -1L, 1L, true);
		TestMode.Tests.constructorFailureCheckException(longParameterConstructor, IllegalArgumentException.class, "",
				2L, -1L, 1L, true);
		TestMode.Tests.constructorFailureCheckException(longParameterConstructor, IllegalArgumentException.class, "",
				0L, 1L, -1L, true);
		TestMode.Tests.constructorSuccess(longParameterConstructor, "", 0L, 0L, 0L, true);

		LongParameter longParameterAllAllowed = null;
		LongParameter longParameterNegative = null;
		LongParameter longParameterPositive = null;
		LongParameter longParameterNoNull = null;

		try {
			longParameterAllAllowed = TestMode.invokeConstructor(longParameterConstructor, "", 0L, null, null, true);
			longParameterNegative = TestMode.invokeConstructor(longParameterConstructor, "", 0L, null, 0L, true);
			longParameterPositive = TestMode.invokeConstructor(longParameterConstructor, "", 0L, 0L, null, true);
			longParameterNoNull = TestMode.invokeConstructor(longParameterConstructor, "", 0L, null, null, false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method longParameterGetCurrentValue = TestMode.getMethod(LongParameter.class, "getCurrentValue");
		Method longParameterSetCurrentValue = TestMode.getMethod(LongParameter.class, "setCurrentValue",
				InternationalizedLong.class);

		TestMode.Tests.methodSuccess(longParameterSetCurrentValue, longParameterAllAllowed,
				InternationalizedLong.of(1L));
		TestMode.Tests.methodSuccessCheckResult(longParameterGetCurrentValue, InternationalizedLong.of(1L),
				longParameterAllAllowed);
		TestMode.Tests.methodSuccess(longParameterSetCurrentValue, longParameterAllAllowed,
				InternationalizedLong.of(-1L));
		TestMode.Tests.methodSuccessCheckResult(longParameterGetCurrentValue, InternationalizedLong.of(-1L),
				longParameterAllAllowed);
		TestMode.Tests.methodSuccess(longParameterSetCurrentValue, longParameterAllAllowed, new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(longParameterGetCurrentValue, InternationalizedLong.of(null),
				longParameterAllAllowed);
		TestMode.Tests.methodSuccess(longParameterSetCurrentValue, longParameterAllAllowed,
				InternationalizedLong.of(null));
		TestMode.Tests.methodSuccessCheckResult(longParameterGetCurrentValue, InternationalizedLong.of(null),
				longParameterAllAllowed);

		TestMode.Tests.methodSuccess(longParameterSetCurrentValue, longParameterNegative,
				InternationalizedLong.of(-1L));
		TestMode.Tests.methodSuccessCheckResult(longParameterGetCurrentValue, InternationalizedLong.of(-1L),
				longParameterNegative);
		TestMode.Tests.methodFailureCheckException(longParameterSetCurrentValue, IllegalArgumentException.class,
				longParameterNegative, InternationalizedLong.of(1L));

		TestMode.Tests.methodFailureCheckException(longParameterSetCurrentValue, IllegalArgumentException.class,
				longParameterPositive, InternationalizedLong.of(-1L));
		TestMode.Tests.methodSuccess(longParameterSetCurrentValue, longParameterPositive, InternationalizedLong.of(1L));
		TestMode.Tests.methodSuccessCheckResult(longParameterGetCurrentValue, InternationalizedLong.of(1L),
				longParameterPositive);

		TestMode.Tests.methodFailureCheckException(longParameterSetCurrentValue, IllegalArgumentException.class,
				longParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(longParameterSetCurrentValue, IllegalArgumentException.class,
				longParameterNoNull, InternationalizedLong.of(null));

		// FloatParameter tests

		Constructor<FloatParameter> floatParameterConstructor = TestMode.getConstructor(FloatParameter.class,
				String.class, Float.class, Float.class, Float.class, boolean.class, boolean.class);

		TestMode.Tests.constructorFailureCheckException(floatParameterConstructor, NullPointerException.class, null, 0f,
				-1f, 1f, true, true);
		TestMode.Tests.constructorFailureCheckException(floatParameterConstructor, IllegalArgumentException.class, "",
				null, -1f, 1f, false, true);
		TestMode.Tests.constructorFailureCheckException(floatParameterConstructor, IllegalArgumentException.class, "",
				Float.NaN, -1f, 1f, true, false);
		TestMode.Tests.constructorFailureCheckException(floatParameterConstructor, IllegalArgumentException.class, "",
				-2f, -1f, 1f, true, true);
		TestMode.Tests.constructorFailureCheckException(floatParameterConstructor, IllegalArgumentException.class, "",
				2f, -1f, 1f, true, true);
		TestMode.Tests.constructorFailureCheckException(floatParameterConstructor, IllegalArgumentException.class, "",
				0f, 1f, -1f, true, true);
		TestMode.Tests.constructorSuccess(floatParameterConstructor, "", 0f, 0f, 0f, true, true);

		FloatParameter floatParameterAllAllowed = null;
		FloatParameter floatParameterNegative = null;
		FloatParameter floatParameterPositive = null;
		FloatParameter floatParameterNoNull = null;
		FloatParameter floatParameterNoNaN = null;

		try {
			floatParameterAllAllowed = TestMode.invokeConstructor(floatParameterConstructor, "", 0f, null, null, true,
					true);
			floatParameterNegative = TestMode.invokeConstructor(floatParameterConstructor, "", 0f, null, 0f, true,
					true);
			floatParameterPositive = TestMode.invokeConstructor(floatParameterConstructor, "", 0f, 0f, null, true,
					true);
			floatParameterNoNull = TestMode.invokeConstructor(floatParameterConstructor, "", 0f, null, null, false,
					true);
			floatParameterNoNaN = TestMode.invokeConstructor(floatParameterConstructor, "", 0f, null, null, true,
					false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method floatParameterGetCurrentValue = TestMode.getMethod(FloatParameter.class, "getCurrentValue");
		Method floatParameterSetCurrentValue = TestMode.getMethod(FloatParameter.class, "setCurrentValue",
				InternationalizedFloat.class);

		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterAllAllowed,
				InternationalizedFloat.of(1f));
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(1f),
				floatParameterAllAllowed);
		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterAllAllowed,
				InternationalizedFloat.of(-1f));
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(-1f),
				floatParameterAllAllowed);
		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterAllAllowed, new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(null),
				floatParameterAllAllowed);
		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterAllAllowed,
				InternationalizedFloat.of(null));
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(null),
				floatParameterAllAllowed);
		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterAllAllowed,
				InternationalizedFloat.of(Float.NaN));
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(Float.NaN),
				floatParameterAllAllowed);

		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterNegative,
				InternationalizedFloat.of(-1f));
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(-1f),
				floatParameterNegative);
		TestMode.Tests.methodFailureCheckException(floatParameterSetCurrentValue, IllegalArgumentException.class,
				floatParameterNegative, InternationalizedFloat.of(1f));

		TestMode.Tests.methodFailureCheckException(floatParameterSetCurrentValue, IllegalArgumentException.class,
				floatParameterPositive, InternationalizedFloat.of(-1f));
		TestMode.Tests.methodSuccess(floatParameterSetCurrentValue, floatParameterPositive,
				InternationalizedFloat.of(1f));
		TestMode.Tests.methodSuccessCheckResult(floatParameterGetCurrentValue, InternationalizedFloat.of(1f),
				floatParameterPositive);

		TestMode.Tests.methodFailureCheckException(floatParameterSetCurrentValue, IllegalArgumentException.class,
				floatParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(floatParameterSetCurrentValue, IllegalArgumentException.class,
				floatParameterNoNull, InternationalizedFloat.of(null));

		TestMode.Tests.methodFailureCheckException(floatParameterSetCurrentValue, IllegalArgumentException.class,
				floatParameterNoNaN, InternationalizedFloat.of(Float.NaN));

		// DoubleParameter tests

		Constructor<DoubleParameter> doubleParameterConstructor = TestMode.getConstructor(DoubleParameter.class,
				String.class, Double.class, Double.class, Double.class, boolean.class, boolean.class);

		TestMode.Tests.constructorFailureCheckException(doubleParameterConstructor, NullPointerException.class, null,
				0d, -1d, 1d, true, true);
		TestMode.Tests.constructorFailureCheckException(doubleParameterConstructor, IllegalArgumentException.class, "",
				null, -1d, 1d, false, true);
		TestMode.Tests.constructorFailureCheckException(doubleParameterConstructor, IllegalArgumentException.class, "",
				Double.NaN, -1d, 1d, true, false);
		TestMode.Tests.constructorFailureCheckException(doubleParameterConstructor, IllegalArgumentException.class, "",
				-2d, -1d, 1d, true, true);
		TestMode.Tests.constructorFailureCheckException(doubleParameterConstructor, IllegalArgumentException.class, "",
				2d, -1d, 1d, true, true);
		TestMode.Tests.constructorFailureCheckException(doubleParameterConstructor, IllegalArgumentException.class, "",
				0d, 1d, -1d, true, true);
		TestMode.Tests.constructorSuccess(doubleParameterConstructor, "", 0d, 0d, 0d, true, true);

		DoubleParameter doubleParameterAllAllowed = null;
		DoubleParameter doubleParameterNegative = null;
		DoubleParameter doubleParameterPositive = null;
		DoubleParameter doubleParameterNoNull = null;
		DoubleParameter doubleParameterNoNaN = null;

		try {
			doubleParameterAllAllowed = TestMode.invokeConstructor(doubleParameterConstructor, "", 0d, null, null, true,
					true);
			doubleParameterNegative = TestMode.invokeConstructor(doubleParameterConstructor, "", 0d, null, 0d, true,
					true);
			doubleParameterPositive = TestMode.invokeConstructor(doubleParameterConstructor, "", 0d, 0d, null, true,
					true);
			doubleParameterNoNull = TestMode.invokeConstructor(doubleParameterConstructor, "", 0d, null, null, false,
					true);
			doubleParameterNoNaN = TestMode.invokeConstructor(doubleParameterConstructor, "", 0d, null, null, true,
					false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method doubleParameterGetCurrentValue = TestMode.getMethod(DoubleParameter.class, "getCurrentValue");
		Method doubleParameterSetCurrentValue = TestMode.getMethod(DoubleParameter.class, "setCurrentValue",
				InternationalizedDouble.class);

		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterAllAllowed,
				InternationalizedDouble.of(1d));
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(1d),
				doubleParameterAllAllowed);
		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterAllAllowed,
				InternationalizedDouble.of(-1d));
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(-1d),
				doubleParameterAllAllowed);
		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterAllAllowed, new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(null),
				doubleParameterAllAllowed);
		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterAllAllowed,
				InternationalizedDouble.of(null));
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(null),
				doubleParameterAllAllowed);
		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterAllAllowed,
				InternationalizedDouble.of(Double.NaN));
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(Double.NaN),
				doubleParameterAllAllowed);

		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterNegative,
				InternationalizedDouble.of(-1d));
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(-1d),
				doubleParameterNegative);
		TestMode.Tests.methodFailureCheckException(doubleParameterSetCurrentValue, IllegalArgumentException.class,
				doubleParameterNegative, InternationalizedDouble.of(1d));

		TestMode.Tests.methodFailureCheckException(doubleParameterSetCurrentValue, IllegalArgumentException.class,
				doubleParameterPositive, InternationalizedDouble.of(-1d));
		TestMode.Tests.methodSuccess(doubleParameterSetCurrentValue, doubleParameterPositive,
				InternationalizedDouble.of(1d));
		TestMode.Tests.methodSuccessCheckResult(doubleParameterGetCurrentValue, InternationalizedDouble.of(1d),
				doubleParameterPositive);

		TestMode.Tests.methodFailureCheckException(doubleParameterSetCurrentValue, IllegalArgumentException.class,
				doubleParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(doubleParameterSetCurrentValue, IllegalArgumentException.class,
				doubleParameterNoNull, InternationalizedDouble.of(null));

		TestMode.Tests.methodFailureCheckException(doubleParameterSetCurrentValue, IllegalArgumentException.class,
				doubleParameterNoNaN, InternationalizedDouble.of(Double.NaN));

		// BigIntegerParameter tests

		Constructor<BigIntegerParameter> bigIntegerParameterConstructor = TestMode.getConstructor(
				BigIntegerParameter.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class,
				boolean.class);

		TestMode.Tests.constructorFailureCheckException(bigIntegerParameterConstructor, NullPointerException.class,
				null, new BigInteger("0"), new BigInteger("-1"), new BigInteger("1"), true);
		TestMode.Tests.constructorFailureCheckException(bigIntegerParameterConstructor, IllegalArgumentException.class,
				"", null, new BigInteger("-1"), new BigInteger("1"), false);
		TestMode.Tests.constructorFailureCheckException(bigIntegerParameterConstructor, IllegalArgumentException.class,
				"", new BigInteger("-2"), new BigInteger("-1"), new BigInteger("1"), true);
		TestMode.Tests.constructorFailureCheckException(bigIntegerParameterConstructor, IllegalArgumentException.class,
				"", new BigInteger("2"), new BigInteger("-1"), new BigInteger("1"), true);
		TestMode.Tests.constructorFailureCheckException(bigIntegerParameterConstructor, IllegalArgumentException.class,
				"", new BigInteger("0"), new BigInteger("1"), new BigInteger("-1"), true);
		TestMode.Tests.constructorSuccess(bigIntegerParameterConstructor, "", new BigInteger("0"), new BigInteger("0"),
				new BigInteger("0"), true);

		BigIntegerParameter bigIntegerParameterAllAllowed = null;
		BigIntegerParameter bigIntegerParameterNegative = null;
		BigIntegerParameter bigIntegerParameterPositive = null;
		BigIntegerParameter bigIntegerParameterNoNull = null;

		try {
			bigIntegerParameterAllAllowed = TestMode.invokeConstructor(bigIntegerParameterConstructor, "",
					new BigInteger("0"), null, null, true);
			bigIntegerParameterNegative = TestMode.invokeConstructor(bigIntegerParameterConstructor, "",
					new BigInteger("0"), null, new BigInteger("0"), true);
			bigIntegerParameterPositive = TestMode.invokeConstructor(bigIntegerParameterConstructor, "",
					new BigInteger("0"), new BigInteger("0"), null, true);
			bigIntegerParameterNoNull = TestMode.invokeConstructor(bigIntegerParameterConstructor, "",
					new BigInteger("0"), null, null, false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method bigIntegerParameterGetCurrentValue = TestMode.getMethod(BigIntegerParameter.class, "getCurrentValue");
		Method bigIntegerParameterSetCurrentValue = TestMode.getMethod(BigIntegerParameter.class, "setCurrentValue",
				InternationalizedBigInteger.class);

		TestMode.Tests.methodSuccess(bigIntegerParameterSetCurrentValue, bigIntegerParameterAllAllowed,
				InternationalizedBigInteger.of(new BigInteger("1")));
		TestMode.Tests.methodSuccessCheckResult(bigIntegerParameterGetCurrentValue,
				InternationalizedBigInteger.of(new BigInteger("1")), bigIntegerParameterAllAllowed);
		TestMode.Tests.methodSuccess(bigIntegerParameterSetCurrentValue, bigIntegerParameterAllAllowed,
				InternationalizedBigInteger.of(new BigInteger("-1")));
		TestMode.Tests.methodSuccessCheckResult(bigIntegerParameterGetCurrentValue,
				InternationalizedBigInteger.of(new BigInteger("-1")), bigIntegerParameterAllAllowed);
		TestMode.Tests.methodSuccess(bigIntegerParameterSetCurrentValue, bigIntegerParameterAllAllowed,
				new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(bigIntegerParameterGetCurrentValue,
				InternationalizedBigInteger.of(null), bigIntegerParameterAllAllowed);
		TestMode.Tests.methodSuccess(bigIntegerParameterSetCurrentValue, bigIntegerParameterAllAllowed,
				InternationalizedBigInteger.of(null));
		TestMode.Tests.methodSuccessCheckResult(bigIntegerParameterGetCurrentValue,
				InternationalizedBigInteger.of(null), bigIntegerParameterAllAllowed);

		TestMode.Tests.methodSuccess(bigIntegerParameterSetCurrentValue, bigIntegerParameterNegative,
				InternationalizedBigInteger.of(new BigInteger("-1")));
		TestMode.Tests.methodSuccessCheckResult(bigIntegerParameterGetCurrentValue,
				InternationalizedBigInteger.of(new BigInteger("-1")), bigIntegerParameterNegative);
		TestMode.Tests.methodFailureCheckException(bigIntegerParameterSetCurrentValue, IllegalArgumentException.class,
				bigIntegerParameterNegative, InternationalizedBigInteger.of(new BigInteger("1")));

		TestMode.Tests.methodFailureCheckException(bigIntegerParameterSetCurrentValue, IllegalArgumentException.class,
				bigIntegerParameterPositive, InternationalizedBigInteger.of(new BigInteger("-1")));
		TestMode.Tests.methodSuccess(bigIntegerParameterSetCurrentValue, bigIntegerParameterPositive,
				InternationalizedBigInteger.of(new BigInteger("1")));
		TestMode.Tests.methodSuccessCheckResult(bigIntegerParameterGetCurrentValue,
				InternationalizedBigInteger.of(new BigInteger("1")), bigIntegerParameterPositive);

		TestMode.Tests.methodFailureCheckException(bigIntegerParameterSetCurrentValue, IllegalArgumentException.class,
				bigIntegerParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(bigIntegerParameterSetCurrentValue, IllegalArgumentException.class,
				bigIntegerParameterNoNull, InternationalizedBigInteger.of(null));

		// BigDecimalParameter tests

		Constructor<BigDecimalParameter> bigDecimalParameterConstructor = TestMode.getConstructor(
				BigDecimalParameter.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class,
				boolean.class);

		TestMode.Tests.constructorFailureCheckException(bigDecimalParameterConstructor, NullPointerException.class,
				null, new BigDecimal("0"), new BigDecimal("-1"), new BigDecimal("1"), true);
		TestMode.Tests.constructorFailureCheckException(bigDecimalParameterConstructor, IllegalArgumentException.class,
				"", null, new BigDecimal("-1"), new BigDecimal("1"), false);
		TestMode.Tests.constructorFailureCheckException(bigDecimalParameterConstructor, IllegalArgumentException.class,
				"", new BigDecimal("-2"), new BigDecimal("-1"), new BigDecimal("1"), true);
		TestMode.Tests.constructorFailureCheckException(bigDecimalParameterConstructor, IllegalArgumentException.class,
				"", new BigDecimal("2"), new BigDecimal("-1"), new BigDecimal("1"), true);
		TestMode.Tests.constructorFailureCheckException(bigDecimalParameterConstructor, IllegalArgumentException.class,
				"", new BigDecimal("0"), new BigDecimal("1"), new BigDecimal("-1"), true);
		TestMode.Tests.constructorSuccess(bigDecimalParameterConstructor, "", new BigDecimal("0"), new BigDecimal("0"),
				new BigDecimal("0"), true);

		BigDecimalParameter bigDecimalParameterAllAllowed = null;
		BigDecimalParameter bigDecimalParameterNegative = null;
		BigDecimalParameter bigDecimalParameterPositive = null;
		BigDecimalParameter bigDecimalParameterNoNull = null;

		try {
			bigDecimalParameterAllAllowed = TestMode.invokeConstructor(bigDecimalParameterConstructor, "",
					new BigDecimal("0"), null, null, true);
			bigDecimalParameterNegative = TestMode.invokeConstructor(bigDecimalParameterConstructor, "",
					new BigDecimal("0"), null, new BigDecimal("0"), true);
			bigDecimalParameterPositive = TestMode.invokeConstructor(bigDecimalParameterConstructor, "",
					new BigDecimal("0"), new BigDecimal("0"), null, true);
			bigDecimalParameterNoNull = TestMode.invokeConstructor(bigDecimalParameterConstructor, "",
					new BigDecimal("0"), null, null, false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method bigDecimalParameterGetCurrentValue = TestMode.getMethod(BigDecimalParameter.class, "getCurrentValue");
		Method bigDecimalParameterSetCurrentValue = TestMode.getMethod(BigDecimalParameter.class, "setCurrentValue",
				InternationalizedBigDecimal.class);

		TestMode.Tests.methodSuccess(bigDecimalParameterSetCurrentValue, bigDecimalParameterAllAllowed,
				InternationalizedBigDecimal.of(new BigDecimal("1")));
		TestMode.Tests.methodSuccessCheckResult(bigDecimalParameterGetCurrentValue,
				InternationalizedBigDecimal.of(new BigDecimal("1")), bigDecimalParameterAllAllowed);
		TestMode.Tests.methodSuccess(bigDecimalParameterSetCurrentValue, bigDecimalParameterAllAllowed,
				InternationalizedBigDecimal.of(new BigDecimal("-1")));
		TestMode.Tests.methodSuccessCheckResult(bigDecimalParameterGetCurrentValue,
				InternationalizedBigDecimal.of(new BigDecimal("-1")), bigDecimalParameterAllAllowed);
		TestMode.Tests.methodSuccess(bigDecimalParameterSetCurrentValue, bigDecimalParameterAllAllowed,
				new Object[] { null });
		TestMode.Tests.methodSuccessCheckResult(bigDecimalParameterGetCurrentValue,
				InternationalizedBigDecimal.of(null), bigDecimalParameterAllAllowed);
		TestMode.Tests.methodSuccess(bigDecimalParameterSetCurrentValue, bigDecimalParameterAllAllowed,
				InternationalizedBigDecimal.of(null));
		TestMode.Tests.methodSuccessCheckResult(bigDecimalParameterGetCurrentValue,
				InternationalizedBigDecimal.of(null), bigDecimalParameterAllAllowed);

		TestMode.Tests.methodSuccess(bigDecimalParameterSetCurrentValue, bigDecimalParameterNegative,
				InternationalizedBigDecimal.of(new BigDecimal("-1")));
		TestMode.Tests.methodSuccessCheckResult(bigDecimalParameterGetCurrentValue,
				InternationalizedBigDecimal.of(new BigDecimal("-1")), bigDecimalParameterNegative);
		TestMode.Tests.methodFailureCheckException(bigDecimalParameterSetCurrentValue, IllegalArgumentException.class,
				bigDecimalParameterNegative, InternationalizedBigDecimal.of(new BigDecimal("1")));

		TestMode.Tests.methodFailureCheckException(bigDecimalParameterSetCurrentValue, IllegalArgumentException.class,
				bigDecimalParameterPositive, InternationalizedBigDecimal.of(new BigDecimal("-1")));
		TestMode.Tests.methodSuccess(bigDecimalParameterSetCurrentValue, bigDecimalParameterPositive,
				InternationalizedBigDecimal.of(new BigDecimal("1")));
		TestMode.Tests.methodSuccessCheckResult(bigDecimalParameterGetCurrentValue,
				InternationalizedBigDecimal.of(new BigDecimal("1")), bigDecimalParameterPositive);

		TestMode.Tests.methodFailureCheckException(bigDecimalParameterSetCurrentValue, IllegalArgumentException.class,
				bigDecimalParameterNoNull, new Object[] { null });
		TestMode.Tests.methodFailureCheckException(bigDecimalParameterSetCurrentValue, IllegalArgumentException.class,
				bigDecimalParameterNoNull, InternationalizedBigDecimal.of(null));

		// OptionParameter tests

		@SuppressWarnings("rawtypes")
		Constructor<OptionParameter> optionParameterConstructor = TestMode.getConstructor(OptionParameter.class,
				String.class);

		OptionParameter<String> optionParameter = null;

		try {
			optionParameter = TestMode.invokeConstructor(optionParameterConstructor, "");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Method optionParameterGetCurrentValue = TestMode.getMethod(OptionParameter.class, "getCurrentValueOption");
		Method optionParameterSetCurrentValue = TestMode.getMethod(OptionParameter.class, "setCurrentValueOption",
				Option.class);
		Method optionParameterAddOption = TestMode.getMethod(OptionParameter.class, "addOption", Option.class);

		TestMode.Tests.methodSuccessCheckResult(optionParameterGetCurrentValue, new OptionParameter.NullOption<>(),
				optionParameter);
		TestMode.Tests.methodFailureCheckException(optionParameterSetCurrentValue, IllegalArgumentException.class,
				optionParameter, new OptionParameter.NullOption<>());

		try {
			TestMode.invokeMethod(optionParameterAddOption, optionParameter, new OptionParameter.NullOption<>());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		TestMode.Tests.methodSuccess(optionParameterSetCurrentValue, optionParameter,
				new OptionParameter.NullOption<>());
		TestMode.Tests.methodSuccessCheckResult(optionParameterGetCurrentValue, new OptionParameter.NullOption<>(),
				optionParameter);

	}

}
