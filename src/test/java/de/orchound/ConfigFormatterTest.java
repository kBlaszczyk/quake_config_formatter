package de.orchound;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigFormatterTest {

	private final ConfigFormatter formatter = initConfigFormatter();
	private final String expectedConfig = readExpectedConfig();

	@Test
	public void testFormat() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		formatter.format(writer);
		writer.flush();
		assertEquals(expectedConfig, outputStream.toString());
	}

	private ConfigFormatter initConfigFormatter() {
		InputStream inputStream = getClass().getResourceAsStream("/unformattedConfig.cfg");

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			return new ConfigFormatter(bufferedReader.lines());
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}

	private String readExpectedConfig() {
		InputStream inputStream = getClass().getResourceAsStream("/expectedConfig.cfg");

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String lineSeparator = System.lineSeparator();
			return bufferedReader.lines().collect(Collectors.joining(lineSeparator)) + lineSeparator;
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
