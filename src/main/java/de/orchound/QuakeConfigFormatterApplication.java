package de.orchound;

import picocli.CommandLine.Command;

import java.io.*;

@Command(
	name = "Quake Config Formatter", mixinStandardHelpOptions = true, version = "1.0",
	description = "Tool for reformatting config files for QL, Q3, etc."
)
public class QuakeConfigFormatterApplication implements Runnable {

	@Override
	public void run() {
		Reader inputReader = new InputStreamReader(System.in);
		try (BufferedReader bufferedReader = new BufferedReader(inputReader)) {
			ConfigFormatter formatter = new ConfigFormatter(bufferedReader.lines());
			PrintWriter writer = new PrintWriter(System.out, true);
			formatter.format(writer);
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
