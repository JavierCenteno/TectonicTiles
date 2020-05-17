/*
 * StringUtil.java
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

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utilities to convert objects to string.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 *
 */
public class StringUtil {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	/**
	 * String designating the separation of elements.
	 */
	public static final String ELEMENT_SEPARATOR = ", ";
	/**
	 * String designating the start of a list.
	 */
	public static final String LIST_START = "[";
	/**
	 * String designating the end of a list.
	 */
	public static final String LIST_END = "]";
	/**
	 * String designating the start of a map.
	 */
	public static final String MAP_START = "{";
	/**
	 * String designating the end of a map.
	 */
	public static final String MAP_END = "}";
	/**
	 * String designating the separation of the key and the value in a key, value
	 * pair.
	 */
	public static final String MAP_KEY_VALUE_SEPARATOR = ": ";
	/**
	 * String designating the start of the arguments of a function.
	 */
	public static final String CALL_START = "(";
	/**
	 * String designating the end of the arguments of a function.
	 */
	public static final String CALL_END = ")";

	////////////////////////////////////////////////////////////////////////////////
	// Class methods

	/**
	 * Returns a string representation of the given object.
	 * 
	 * @param object An object.
	 * @return A string representation of the given object.
	 */
	public static String asString(Object object) {
		if (object == null) {
			return "null";
		} else if (object.getClass().isArray()) {
			String result = LIST_START;
			switch (object.getClass().getComponentType().getName()) {
			case "boolean":
				boolean[] booleanArray = (boolean[]) object;
				if (booleanArray.length != 0) {
					result += String.valueOf(booleanArray[0]);
					for (int index = 1; index < booleanArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(booleanArray[index]);
					}
				}
				break;
			case "byte":
				byte[] byteArray = (byte[]) object;
				if (byteArray.length != 0) {
					result += String.valueOf(byteArray[0]);
					for (int index = 1; index < byteArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(byteArray[index]);
					}
				}
				break;
			case "short":
				short[] shortArray = (short[]) object;
				if (shortArray.length != 0) {
					result += String.valueOf(shortArray[0]);
					for (int index = 1; index < shortArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(shortArray[index]);
					}
				}
				break;
			case "int":
				int[] intArray = (int[]) object;
				if (intArray.length != 0) {
					result += String.valueOf(intArray[0]);
					for (int index = 1; index < intArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(intArray[index]);
					}
				}
				break;
			case "long":
				long[] longArray = (long[]) object;
				if (longArray.length != 0) {
					result += String.valueOf(longArray[0]);
					for (int index = 1; index < longArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(longArray[index]);
					}
				}
				break;
			case "float":
				float[] floatArray = (float[]) object;
				if (floatArray.length != 0) {
					result += String.valueOf(floatArray[0]);
					for (int index = 1; index < floatArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(floatArray[index]);
					}
				}
				break;
			case "double":
				double[] doubleArray = (double[]) object;
				if (doubleArray.length != 0) {
					result += String.valueOf(doubleArray[0]);
					for (int index = 1; index < doubleArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(doubleArray[index]);
					}
				}
				break;
			case "char":
				char[] charArray = (char[]) object;
				if (charArray.length != 0) {
					result += String.valueOf(charArray[0]);
					for (int index = 1; index < charArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += String.valueOf(charArray[index]);
					}
				}
				break;
			default:
				Object[] objectArray = (Object[]) object;
				if (objectArray.length != 0) {
					result += asString(objectArray[0]);
					for (int index = 1; index < objectArray.length; ++index) {
						result += ELEMENT_SEPARATOR;
						result += asString(objectArray[index]);
					}
				}
				break;
			}
			result += LIST_END;
			return result;
		} else if (Iterable.class.isAssignableFrom(object.getClass())) {
			String result = LIST_START;
			boolean isFirstElement = true;
			for (Object element : ((Iterable<?>) object)) {
				if (!isFirstElement) {
					result += ELEMENT_SEPARATOR;
				} else {
					isFirstElement = false;
				}
				result += asString(element);
			}
			result += LIST_END;
			return result;
		} else if (Map.class.isAssignableFrom(object.getClass())) {
			String result = MAP_START;
			boolean isFirstEntry = true;
			for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
				if (!isFirstEntry) {
					result += ELEMENT_SEPARATOR;
				} else {
					isFirstEntry = false;
				}
				result += asString(entry.getKey());
				result += MAP_KEY_VALUE_SEPARATOR;
				result += asString(entry.getValue());
			}
			result += MAP_END;
			return result;
		} else {
			String result;
			switch (object.getClass().getName()) {
			case "java.lang.String":
				result = (String) object;
				result = result.replace("\\", "\\\\");
				result = result.replace("\'", "\\\'");
				result = result.replace("\"", "\\\"");
				result = result.replace("\b", "\\b");
				result = result.replace("\f", "\\f");
				result = result.replace("\n", "\\n");
				result = result.replace("\r", "\\r");
				result = result.replace("\t", "\\t");
				result = "\"" + result + "\"";
				break;
			case "java.io.File":
				File file = (File) object;
				result = file.getPath();
				break;
			case "java.lang.Class":
				Class<?> type = (Class<?>) object;
				result = type.getName();
				break;
			case "java.lang.reflect.Field":
				Field field = (Field) object;
				result = field.getDeclaringClass().getName() + "." + field.getName();
				break;
			case "java.lang.reflect.Constructor":
				Constructor<?> constructor = (Constructor<?>) object;
				result = constructor.getName();
				result += CALL_START;
				result += asStrings((Object[]) constructor.getParameterTypes());
				result += CALL_END;
				break;
			case "java.lang.reflect.Method":
				Method method = (Method) object;
				result = method.getName();
				result += CALL_START;
				result += asStrings((Object[]) method.getParameterTypes());
				result += CALL_END;
				break;
			default:
				result = object.toString();
				break;
			}
			return result;
		}
	}

	/**
	 * Returns a string representation of the given objects separated by the element
	 * separator strings.
	 * 
	 * @param objects Multiple objects.
	 * @return A string representation of the given object.
	 * @see StringUtil#asString(Object)
	 * @see StringUtil#ELEMENT_SEPARATOR
	 */
	public static String asStrings(Object... objects) {
		StringBuilder result = new StringBuilder();
		if (objects.length > 0) {
			result.append(asString(objects[0]));
			for (int index = 1; index < objects.length; ++index) {
				result.append(ELEMENT_SEPARATOR);
				result.append(asString(objects[index]));
			}
		}
		return result.toString();
	}

}
