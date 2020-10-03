package de.orchound;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigFormatterTest {

	private final Stream<String> input = Stream.of(
		"// generated by QL",
		"unbindall",
		"bind w \"+forward\"",
		"bind s \"+back\"",
		"seta g_weaponRespawn \"15\"",
		"bind a \"+strafeleft\"",
		"bind d \"+straferight\""
	);

	private final String expectedConfig = readExpectedConfig();

	private final ConfigFormatter formatter = new ConfigFormatter(input);

	@Test
	public void testFormat() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		formatter.format(writer);
		writer.flush();
		assertEquals(expectedConfig, outputStream.toString());
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