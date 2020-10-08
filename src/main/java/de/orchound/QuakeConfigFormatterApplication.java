package de.orchound;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Command(
	name = "Quake Config Formatter", mixinStandardHelpOptions = true, version = "1.0",
	description = "Tool for reformatting config files for QL, Q3, etc."
)
public class QuakeConfigFormatterApplication implements Runnable {

	@Option(names = {"-c", "--config"}, description = "The config file")
	private String config;

	@Option(names = {"-t", "--target"}, description = "The target file")
	private String target;

	@Override
	public void run() {
		try(
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStream(config)));
			PrintWriter writer = new PrintWriter(getOutputStream(target), true)
		) {
			ConfigFormatter formatter = new ConfigFormatter(bufferedReader.lines());
			formatter.format(writer);
			writer.flush();
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private InputStream getInputStream(String config) {
		if (config == null) {
			return System.in;
		} else {
			try {
				return new FileInputStream(config);
			} catch (FileNotFoundException ex) {
				throw new RuntimeException("Config not found: " + config);
			}
		}
	}

	private void createFileIfNotExistent(Path target) {
		try {
			Path parentDirectory = target.getParent();
			if (parentDirectory != null)
				Files.createDirectories(target.getParent());
			Files.createFile(target);
		} catch (FileAlreadyExistsException ignored) {
		} catch (IOException ex) {
			throw new RuntimeException("Could not create target file: " + target);
		}
	}

	private OutputStream getOutputStream(String target) {
		if (this.target == null)
			return System.out;

		Path targetPath = Paths.get(target);
		createFileIfNotExistent(targetPath);
		try {
			return Files.newOutputStream(targetPath);
		} catch (IOException ex) {
			throw new RuntimeException("Could not access output file: " + targetPath);
		}
	}
}
