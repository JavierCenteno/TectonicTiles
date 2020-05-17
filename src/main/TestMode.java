/*
 * TestMode.java
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

package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import parameter.ConsoleHelper;
import parameter.configuration.ConfigurationConfigurer;
import test.UnitTests;
import util.StringUtil;

/**
 * This is the driver class for the test mode of the application.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class TestMode {

	////////////////////////////////////////////////////////////////////////////////
	// Program fields

	/**
	 * Console helper used to access console functions.
	 */
	private static ConsoleHelper CONSOLE;
	/**
	 * Number of tests ran.
	 */
	private static int TEST_NUMBER = 0;
	/**
	 * List of the numbers of failed tests.
	 */
	private static List<Integer> FAILED_TEST_NUMBERS = new ArrayList<>();
	/**
	 * List of the info of failed tests.
	 */
	private static List<String> FAILED_TEST_INFO = new ArrayList<>();

	////////////////////////////////////////////////////////////////////////////////
	// Main method

	/**
	 * Run the test mode as an entry point.
	 * 
	 * @param arguments Arguments.
	 */
	public static void main(String[] arguments) {
		run();
	}

	/**
	 * Run this program.
	 */
	public static void run() {
		// initialize console
		CONSOLE = new ConsoleHelper(System.in, System.out);

		// initialize configuration
		ConfigurationConfigurer.getConfiguration().load();

		UnitTests.run();

		CONSOLE.writeLine();
		CONSOLE.writeLine("TEST RESULTS:");
		CONSOLE.writeLine();
		CONSOLE.writeLine("TOTAL TESTS: " + TEST_NUMBER);
		CONSOLE.writeLine("TESTS PASSED: " + (TEST_NUMBER - FAILED_TEST_NUMBERS.size()));
		CONSOLE.writeLine("TESTS FAILED: " + (FAILED_TEST_NUMBERS.size()));
		if (FAILED_TEST_NUMBERS.size() > 0) {
			CONSOLE.writeLine();
			CONSOLE.writeLine("LIST OF FAILED TESTS:");
			for (int index = 0; index < FAILED_TEST_NUMBERS.size(); ++index) {
				CONSOLE.writeLine("[TEST " + FAILED_TEST_NUMBERS.get(index) + "] " + FAILED_TEST_INFO.get(index));
			}
		} else {
			CONSOLE.writeLine();
			CONSOLE.writeLine("NO TESTS FAILED");
		}
		CONSOLE.writeLine("PRESS ENTER TO CLOSE");
		CONSOLE.readLine();
		CONSOLE.writeLine("CLOSING...");
	}

	////////////////////////////////////////////////////////////////////////////////
	// Testing methods

	/**
	 * A class holding the methods to perform tests.
	 *
	 * @author Javier Centeno Vega <jacenve@telefonica.net>
	 * @version 0.3
	 * @since 0.3
	 *
	 */
	public static class Tests {

		/**
		 * Tests the given method with the given arguments, checking it doesn't throw
		 * any exceptions.
		 * 
		 * @param method    A method.
		 * @param instance  Instance this method is called on.
		 * @param arguments The arguments to pass to the method.
		 */
		public static void methodSuccess(Method method, Object instance, Object... arguments) {
			startTest("Testing method: " + StringUtil.asString(method) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			try {
				invokeMethod(method, instance, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable != null) {
				failedTest("No exception expected, " + actualThrowable.getClass().getName() + " thrown.");
			} else {
				passedTest("No exception expected, no exception thrown.");
			}
		}

		/**
		 * Tests the given method with the given arguments, checking it doesn't throw
		 * any exceptions and the result matches the expected result.
		 * 
		 * @param method         A method.
		 * @param expectedResult The expected result.
		 * @param instance       Instance this method is called on.
		 * @param arguments      The arguments to pass to the method.
		 */
		public static void methodSuccessCheckResult(Method method, Object expectedResult, Object instance,
				Object... arguments) {
			startTest("Testing method: " + StringUtil.asString(method) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			Object actualResult = null;
			try {
				actualResult = invokeMethod(method, instance, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable != null) {
				failedTest("No exception expected, " + actualThrowable.getClass().getName() + " thrown.");
			} else if (!Objects.equals(actualResult, expectedResult)) {
				failedTest(expectedResult.toString() + " expected, " + actualResult.toString() + " returned.");
			} else {
				passedTest(expectedResult.toString() + " expected, " + actualResult.toString() + " returned.");
			}
		}

		/**
		 * Tests the given method with the given arguments, checking it throws an
		 * exception.
		 * 
		 * @param method    A method.
		 * @param instance  Instance this method is called on.
		 * @param arguments The arguments to pass to the method.
		 */
		public static void methodFailure(Method method, Object instance, Object... arguments) {
			startTest("Testing method: " + StringUtil.asString(method) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			try {
				invokeMethod(method, instance, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable == null) {
				failedTest("Exception expected, no exception thrown.");
			} else {
				passedTest("Exception expected, exception thrown.");
			}
		}

		/**
		 * Tests the given method with the given arguments, checking it throws a
		 * throwable of the expected type.
		 * 
		 * @param method                 A method.
		 * @param expectedThrowableClass The expected type of throwable to be thrown by
		 *                               the method.
		 * @param instance               Instance this method is called on.
		 * @param arguments              The arguments to pass to the method.
		 */
		public static void methodFailureCheckException(Method method, Class<? extends Throwable> expectedThrowableClass,
				Object instance, Object... arguments) {
			startTest("Testing method: " + StringUtil.asString(method) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			try {
				invokeMethod(method, instance, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable == null) {
				failedTest(expectedThrowableClass.getName() + " expected, no exception thrown.");
			} else if (!actualThrowable.getClass().equals(expectedThrowableClass)) {
				failedTest(expectedThrowableClass.getName() + " expected, " + actualThrowable.getClass().getName()
						+ " thrown.");
			} else {
				passedTest(expectedThrowableClass.getName() + " expected, " + actualThrowable.getClass().getName()
						+ " thrown.");
			}
		}

		/**
		 * Tests the given constructor with the given arguments, checking it doesn't
		 * throw any exceptions.
		 * 
		 * @param <T>         The type of the constructor.
		 * @param constructor A constructor.
		 * @param arguments   The arguments to pass to the constructor.
		 */
		public static <T> void constructorSuccess(Constructor<T> constructor, Object... arguments) {
			startTest("Testing constructor: " + StringUtil.asString(constructor) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			try {
				invokeConstructor(constructor, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable != null) {
				failedTest("No exception expected, " + actualThrowable.getClass().getName() + " thrown.");
			} else {
				passedTest("No exception expected, no exception thrown.");
			}
		}

		/**
		 * Tests the given constructor with the given arguments, checking it doesn't
		 * throw any exceptions and the result matches the expected result.
		 * 
		 * @param <T>            The type of the constructor.
		 * @param constructor    A constructor.
		 * @param expectedResult The expected result.
		 * @param arguments      The arguments to pass to the constructor.
		 */
		public static <T> void constructorSuccessCheckResult(Constructor<T> constructor, T expectedResult,
				Object... arguments) {
			startTest("Testing constructor: " + StringUtil.asString(constructor) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			T actualResult = null;
			try {
				actualResult = invokeConstructor(constructor, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable != null) {
				failedTest("No exception expected, " + actualThrowable.getClass().getName() + " thrown.");
			} else if (!Objects.equals(actualResult, expectedResult)) {
				failedTest(expectedResult.toString() + " expected, " + actualResult.toString() + " returned.");
			} else {
				passedTest(expectedResult.toString() + " expected, " + actualResult.toString() + " returned.");
			}
		}

		/**
		 * Tests the given constructor with the given arguments, checking it throws an
		 * exception.
		 * 
		 * @param <T>         The type of the constructor.
		 * @param constructor A constructor.
		 * @param arguments   The arguments to pass to the constructor.
		 */
		public static <T> void constructorFailure(Constructor<T> constructor, Object... arguments) {
			startTest("Testing constructor: " + StringUtil.asString(constructor) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			try {
				invokeConstructor(constructor, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable == null) {
				failedTest("Exception expected, no exception thrown.");
			} else {
				passedTest("Exception expected, exception thrown.");
			}
		}

		/**
		 * Tests the given constructor with the given arguments, checking it throws a
		 * throwable of the expected type.
		 * 
		 * @param <T>                    The type of the constructor.
		 * @param constructor            A constructor.
		 * @param expectedThrowableClass The expected type of throwable to be thrown by
		 *                               the constructor.
		 * @param arguments              The arguments to pass to the constructor.
		 */
		public static <T> void constructorFailureCheckException(Constructor<T> constructor,
				Class<? extends Throwable> expectedThrowableClass, Object... arguments) {
			startTest("Testing constructor: " + StringUtil.asString(constructor) + " with arguments: ("
					+ StringUtil.asStrings(arguments) + ")");
			Throwable actualThrowable = null;
			try {
				invokeConstructor(constructor, arguments);
			} catch (InvocationTargetException exception) {
				actualThrowable = exception.getCause();
			}
			if (actualThrowable == null) {
				failedTest(expectedThrowableClass.getName() + " expected, no exception thrown.");
			} else if (!actualThrowable.getClass().equals(expectedThrowableClass)) {
				failedTest(expectedThrowableClass.getName() + " expected, " + actualThrowable.getClass().getName()
						+ " thrown.");
			} else {
				passedTest(expectedThrowableClass.getName() + " expected, " + actualThrowable.getClass().getName()
						+ " thrown.");
			}
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// Test control and logging methods

	/**
	 * Starts a test. Increases the total test number by one and logs the start of a
	 * test with the current number.
	 * 
	 * @param info Info to log with the start of the test.
	 */
	public static void startTest(String info) {
		++TEST_NUMBER;
		CONSOLE.writeLine("[TEST " + TEST_NUMBER + " START] " + info);
	}

	/**
	 * Ends a test that passed. Logs the end of a passed test with the current
	 * number.
	 * 
	 * @param info Info to log with the end of the test.
	 */
	public static void passedTest(String info) {
		CONSOLE.writeLine("[TEST " + TEST_NUMBER + " PASSED] " + info);
	}

	/**
	 * Ends a test that failed. Logs the end of a failed test with the current
	 * number.
	 * 
	 * @param info Info to log with the end of the test.
	 */
	public static void failedTest(String info) {
		CONSOLE.writeLine("[TEST " + TEST_NUMBER + " FAILED] " + info);
		FAILED_TEST_NUMBERS.add(TEST_NUMBER);
		FAILED_TEST_INFO.add(info);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Utility methods

	/**
	 * Get the method of the given type with the given parameter types.
	 * 
	 * @param type           A type.
	 * @param parameterTypes The parameter types.
	 * @return The method of the given type with the given parameter types.
	 */
	public static <T> Constructor<T> getConstructor(Class<T> type, Class<?>... parameterTypes) {
		Constructor<T> result = null;
		try {
			result = type.getConstructor(parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get the method of the given type with the given name and the given parameter
	 * types.
	 * 
	 * @param type           A type.
	 * @param name           A name.
	 * @param parameterTypes The parameter types.
	 * @return The method of the given type with the given name and the given
	 *         parameter types.
	 */
	public static Method getMethod(Class<?> type, String name, Class<?>... parameterTypes) {
		Method result = null;
		try {
			result = type.getMethod(name, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Invokes the constructor with the given arguments and returns the result.
	 * 
	 * @param <T>         The class instanced by the constructor.
	 * @param constructor A constructor.
	 * @param arguments   The arguments to pass to the constructor.
	 * @return The instance returned by the constructor.
	 * @throws InvocationTargetException If the constructor throws an exception.
	 * @see Constructor#newInstance(Object...)
	 */
	public static <T> T invokeConstructor(Constructor<T> constructor, Object... arguments)
			throws InvocationTargetException {
		T t = null;
		try {
			t = constructor.newInstance(arguments);
		} catch (IllegalAccessException | InstantiationException exception) {
			exception.printStackTrace();
		}
		return t;
	}

	/**
	 * Invokes the method on the given instance with the given arguments and returns
	 * the result.
	 * 
	 * @param method    A method.
	 * @param instance  An instance of a class.
	 * @param arguments The arguments to pass to the method.
	 * @return The object returned by the method.
	 * @throws InvocationTargetException If the method throws an exception.
	 * @see Method#invoke(Object, Object...)
	 */
	public static Object invokeMethod(Method method, Object instance, Object... arguments)
			throws InvocationTargetException {
		Object result = null;
		try {
			result = method.invoke(instance, arguments);
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
		return result;
	}

}
