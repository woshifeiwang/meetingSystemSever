package com.hnair.iot.dataserver.util;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

/**
 * Common file utility
 */
public final class Files {

	/**
	 * This method find file by name in context and convert input of file to string.
	 *
	 * @param filePath of the file path
	 * @param clazz the clazz
	 * @return String
	 */
	public static String getFileContent(String filePath) {
		try {
			byte[] arrayData = IOUtils.toByteArray(read(filePath));
			if (arrayData != null) {
				return new String(arrayData, StandardCharsets.UTF_8);
			}
		}
		catch (IOException e) {
			throw new IllegalStateException("Can't read data from file:" + filePath, e);
		}
		return null;
	}

	/**
	 * Read file as BufferedReader.
	 * 
	 * @param filePath the file path.
	 * @return a BufferedReader
	 */
	@SuppressWarnings("resource")
	public static BufferedReader read(String filePath) {
		try {
			InputStream in = null;
			File file = new File(filePath);
			if (file.exists() && file.canRead()) {
				in = new FileInputStream(file);
			}
			else {
				file = new File(System.getProperty("user.dir"), filePath);
				if (file.exists() && file.canRead()) {
					in = new FileInputStream(file);
				}
				in = Files.class.getClassLoader().getResourceAsStream(filePath);
			}
			if (in == null) {
				throw new IllegalArgumentException("File Not Found:" + filePath);
			}

			return new BufferedReader(new InputStreamReader(in, "utf-8"));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("utf-8 Unsupported", e);
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File Not Found:" + filePath, e);
		}
	}

	private Files() {
	}

}
