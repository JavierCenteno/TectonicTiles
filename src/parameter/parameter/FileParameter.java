/*
 * FileParameter.java
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

package parameter.parameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import parameter.i18n.InternationalizedString;

/**
 * A parameter where the value is a file.
 *
 * @author Javier Centeno Vega <jacenve@telefonica.net>
 * @version 0.3
 * @since 0.3
 * @see parameter.parameter.Parameter
 *
 */
public class FileParameter extends Parameter<File> {

	////////////////////////////////////////////////////////////////////////////////
	// Class fields

	private static final InternationalizedString CANCEL = new InternationalizedString("parameter.cancel");
	private static final InternationalizedString LIST_SEPARATOR = new InternationalizedString("meta.listSeparator");
	private static final InternationalizedString NULL = new InternationalizedString("parameter.file.null");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT = new InternationalizedString(
			"parameter.file.constraint.noDefault");
	private static final InternationalizedString CONSTRAINT_NO_DEFAULT_FORMAT = new InternationalizedString(
			"parameter.file.constraint.noDefaultFormat");
	private static final InternationalizedString CONSTRAINT_DEFAULT = new InternationalizedString(
			"parameter.file.constraint.default");
	private static final InternationalizedString CONSTRAINT_DEFAULT_FORMAT = new InternationalizedString(
			"parameter.file.constraint.defaultFormat");
	private static final InternationalizedString EXCEPTION_ACCESS_DENIED = new InternationalizedString(
			"parameter.file.exception.accessDenied");
	private static final InternationalizedString EXCEPTION_UNREADABLE = new InternationalizedString(
			"parameter.file.exception.unreadable");
	private static final InternationalizedString EXCEPTION_UNWRITABLE = new InternationalizedString(
			"parameter.file.exception.unwriteable");
	private static final InternationalizedString EXCEPTION_NOT_A_FILE = new InternationalizedString(
			"parameter.file.exception.notAFile");
	private static final InternationalizedString EXCEPTION_NOT_A_DIRECTORY = new InternationalizedString(
			"parameter.file.exception.notADirectory");
	private static final InternationalizedString EXCEPTION_NOT_EXIST = new InternationalizedString(
			"parameter.file.exception.notExist");
	private static final InternationalizedString EXCEPTION_FORMAT = new InternationalizedString(
			"parameter.file.exception.format");
	private static final InternationalizedString EXCEPTION_FORMAT_NOT_ALLOWED = new InternationalizedString(
			"parameter.file.exception.formatNotAllowed");

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	private boolean isReadable;
	private boolean isWritable;
	private boolean isFile;
	private boolean isDirectory;
	private boolean exists;
	private String[] allowedFormats;

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	/**
	 * Constructs a new file parameter with the given name and default value.
	 * 
	 * @param nameKey        The internationalization key for the name of the
	 *                       parameter.
	 * @param defaultValue   The default and current value of the parameter.
	 * @param isReadable     Whether the file must be readable. If the file doesn't
	 *                       exist, this check fails.
	 * @param isWritable     Whether the file must be writable. If the file doesn't
	 *                       exist, this checks still holds true if it's possible to
	 *                       create a new file.
	 * @param isFile         Whether the file must be a file. If the file doesn't
	 *                       exist, this check fails.
	 * @param isDirectory    Whether the file must be a directory. If the file
	 *                       doesn't exist, this check fails.
	 * @param exists         Whether the file must exist. If the file doesn't exist,
	 *                       this check fails.
	 * @param allowedFormats The file formats this file can have. May be empty or
	 *                       null if the file can have any format.
	 */
	public FileParameter(String nameKey, File defaultValue, boolean isReadable, boolean isWritable, boolean isFile,
			boolean isDirectory, boolean exists, String... allowedFormats) {
		super(nameKey);
		// set with the method to trigger the checks in the setters
		this.setDefaultValue(defaultValue);
		this.setCurrentValue(defaultValue);
		this.isReadable = isReadable;
		this.isWritable = isWritable;
		this.isFile = isFile;
		this.isDirectory = isDirectory;
		this.exists = exists;
		if (allowedFormats == null) {
			this.allowedFormats = new String[0];
		} else {
			this.allowedFormats = allowedFormats;
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Checks

	/**
	 * Checks if the given value meets the restrictions of this parameter and throws
	 * an exception if that's not the case.
	 * 
	 * @param value A value.
	 */
	private void checkRestrictions(File value) {
		String canonicalPath = "";
		try {
			canonicalPath = value.getCanonicalPath();
		} catch (Exception exception) {
			throw new IllegalArgumentException(EXCEPTION_ACCESS_DENIED.getValue("{value}", value.getPath()), exception);
		}
		if (this.allowedFormats.length > 0) {
			boolean formatAllowed = false;
			for (int index = 0; index < this.allowedFormats.length; ++index) {
				if (canonicalPath.endsWith("." + this.allowedFormats[index])) {
					formatAllowed = true;
				}
			}
			if (!formatAllowed) {
				throw new IllegalArgumentException(EXCEPTION_FORMAT_NOT_ALLOWED.getValue("{value}", canonicalPath));
			}
		}
		if (this.isReadable) {
			if (!value.canRead()) {
				throw new IllegalArgumentException(EXCEPTION_UNREADABLE.getValue("{value}", canonicalPath));
			}
			// additional readibility check
			try {
				new FileInputStream(value).close();
			} catch (Exception exception) {
				throw new IllegalArgumentException(EXCEPTION_UNREADABLE.getValue("{value}", canonicalPath), exception);
			}
		}
		if (this.isWritable) {
			// writability check
			// by creating a file output stream, we check if it's possible to write to this
			// file regardless of whether the file exists or not
			try {
				// The argument "append" must be true, otherwise this would delete the file's
				// contents
				new FileOutputStream(value, true).close();
			} catch (Exception exception) {
				throw new IllegalArgumentException(EXCEPTION_UNWRITABLE.getValue("{value}", canonicalPath), exception);
			}
		}
		if (this.isFile) {
			if (!value.isFile()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_A_FILE.getValue("{value}", canonicalPath));
			}
		}
		if (this.isDirectory) {
			if (!value.isDirectory()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_A_DIRECTORY.getValue("{value}", canonicalPath));
			}
		}
		if (this.exists) {
			if (!value.exists()) {
				throw new IllegalArgumentException(EXCEPTION_NOT_EXIST.getValue("{value}", canonicalPath));
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Accessors

	/**
	 * Set the current value of this parameter to the given value. Throws an
	 * exception if the value doesn't meet the restrictions of this parameter.
	 * 
	 * @param currentValue A value.
	 */
	public void setCurrentValue(File currentValue) {
		if (currentValue != null) {
			checkRestrictions(currentValue);
		}
		super.setCurrentValue(currentValue);
	}

	/**
	 * Set the default value of this parameter to the given value. Throws an
	 * exception if the value doesn't meet the restrictions of this parameter.
	 * 
	 * @param defaultValue A value.
	 */
	public void setDefaultValue(File defaultValue) {
		if (defaultValue != null) {
			checkRestrictions(defaultValue);
		}
		super.setDefaultValue(defaultValue);
	}

	/**
	 * Get whether this parameter's value must be readable.
	 * 
	 * @return Whether this parameter's value must be readable.
	 */
	public boolean getIsReadable() {
		return this.isReadable;
	}

	/**
	 * Set whether this parameter's value must be readable.
	 * 
	 * @param isReadable Whether this parameter's value must be readable.
	 */
	public void setIsReadable(boolean isReadable) {
		this.isReadable = isReadable;
	}

	/**
	 * Get whether this parameter's value must be writable.
	 * 
	 * @return Whether this parameter's value must be writable.
	 */
	public boolean getIsWritable() {
		return this.isWritable;
	}

	/**
	 * Set whether this parameter's value must be writable.
	 * 
	 * @param isWritable Whether this parameter's value must be writable.
	 */
	public void setIsWritable(boolean isWritable) {
		this.isWritable = isWritable;
	}

	/**
	 * Get whether this parameter's value must be a file.
	 * 
	 * @return Whether this parameter's value must be a file.
	 */
	public boolean getIsFile() {
		return this.isFile;
	}

	/**
	 * Set whether this parameter's value must be a file.
	 * 
	 * @param isFile Whether this parameter's value must be a file.
	 */
	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}

	/**
	 * Get whether this parameter's value must be a directory.
	 * 
	 * @return Whether this parameter's value must be a directory.
	 */
	public boolean getIsDirectory() {
		return this.isDirectory;
	}

	/**
	 * Set whether this parameter's value must be a directory.
	 * 
	 * @param isDirectory Whether this parameter's value must be a directory.
	 */
	public void setIsDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	/**
	 * Get whether this parameter's value must exist.
	 * 
	 * @return Whether this parameter's value must exist.
	 */
	public boolean getExists() {
		return this.exists;
	}

	/**
	 * Set whether this parameter's value must exist.
	 * 
	 * @param exists Whether this parameter's value must exist.
	 */
	public void setExists(boolean exists) {
		this.exists = exists;
	}

	/**
	 * Get the file formats this file can have.
	 * 
	 * @return The file formats this file can have.
	 */
	public String[] getAllowedFormats() {
		return this.allowedFormats;
	}

	/**
	 * Set the file formats this file can have. May be empty or null if the file can
	 * have any format.
	 * 
	 * @param allowedFormats The file formats this file can have. May be empty or
	 *                       null if the file can have any format.
	 */
	public void setAllowedFormats(String[] allowedFormats) {
		if (allowedFormats == null) {
			this.allowedFormats = new String[0];
		} else {
			this.allowedFormats = allowedFormats;
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// I/O methods

	@Override
	public String userGetConstraints() {
		if (this.getDefaultValue() == null) {
			if (this.getAllowedFormats().length == 0) {
				return CONSTRAINT_NO_DEFAULT.getValue();
			} else {
				return CONSTRAINT_NO_DEFAULT_FORMAT.getValue("{formats}",
						String.join(LIST_SEPARATOR.getValue(), this.getAllowedFormats()));
			}
		} else {
			try {
				if (this.getAllowedFormats().length == 0) {
					return CONSTRAINT_DEFAULT.getValue("{value}", this.getDefaultValue().getCanonicalPath());
				} else {
					return CONSTRAINT_DEFAULT_FORMAT.getValue("{value}", this.getDefaultValue().getCanonicalPath(),
							"{formats}", String.join(LIST_SEPARATOR.getValue(), this.getAllowedFormats()));
				}
			} catch (IOException e) {
				// Shouldn't happen, we have no way of handling this if it does
				throw new Error(e);
			}
		}
	}

	@Override
	public String userGetCurrentValue() {
		try {
			if (this.getCurrentValue() == null) {
				return NULL.getValue();
			} else {
				return this.getCurrentValue().getCanonicalPath();
			}
		} catch (IOException e) {
			// Shouldn't happen, we have no way of handling this if it does
			throw new Error(e);
		}
	}

	/**
	 * Set the current value of this parameter to the value represented by the given
	 * string. If the string is empty, the current value is set to the default
	 * value, otherwise the current value is set to the result of parsing the string
	 * as an integer.
	 * 
	 * @param value A string.
	 * @throws IllegalArgumentException If an empty string is given but the default
	 *                                  value is null or the expected format for
	 *                                  integer is not met.
	 * @see Integer#parseInt(String)
	 */
	@Override
	public void userSetCurrentValue(String value) {
		if (value.equals(CANCEL.getValue())) {
			throw new CancelledException();
		}
		if (value.isEmpty()) {
			if (this.getDefaultValue() != null) {
				this.reset();
			} else {
				throw new IllegalArgumentException(EXCEPTION_FORMAT.getValue("{value}", value));
			}
		} else {
			File result = new File(value);
			this.setCurrentValue(result);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	/**
	 * Get the input stream corresponding to the current value of this file
	 * parameter.
	 * 
	 * @return The input stream corresponding to the current value of this file
	 *         parameter.
	 */
	public InputStream getInputStream() {
		if (this.isReadable) {
			try {
				return new FileInputStream(this.getCurrentValue());
			} catch (FileNotFoundException exception) {
				/*
				 * We already tested if it's possible to create a stream during our check.
				 * However, it's possible for a file to change between the check and the
				 * execution of this method so that an exception does happen.
				 */
				throw new RuntimeException(
						EXCEPTION_UNREADABLE.getValue("{value}", this.getCurrentValue().getAbsolutePath()), exception);
			}
		} else {
			throw new UnsupportedOperationException("Can't guarantee that the file is readable.");
		}
	}

	/**
	 * Get the output stream corresponding to the current value of this file
	 * parameter.
	 * 
	 * @return The output stream corresponding to the current value of this file
	 *         parameter.
	 */
	public OutputStream getOutputStream() {
		if (this.isWritable) {
			try {
				return new FileOutputStream(this.getCurrentValue());
			} catch (FileNotFoundException exception) {
				/*
				 * We already tested if it's possible to create a stream during our check.
				 * However, it's possible for a file to change between the check and the
				 * execution of this method so that an exception does happen.
				 */
				throw new RuntimeException(
						EXCEPTION_UNWRITABLE.getValue("{value}", this.getCurrentValue().getAbsolutePath()), exception);
			}
		} else {
			throw new UnsupportedOperationException("Can't guarantee that the file is writable.");
		}
	}

	/**
	 * Get the current format of this file as the string after the last dot in the
	 * file's path. If this file has no format, returns an empty string.
	 * 
	 * @return This file's format.
	 */
	public String getFormat() {
		try {
			String path = this.getCurrentValue().getCanonicalPath();
			if (path.contains(".")) {
				return path.substring(path.lastIndexOf('.') + 1);
			} else {
				return "";
			}
		} catch (IOException e) {
			// Shouldn't happen, we have no way of handling this if it does
			throw new Error(e);
		}
	}

}
