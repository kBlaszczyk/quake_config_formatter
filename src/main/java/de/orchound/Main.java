package de.orchound;

import picocli.CommandLine;

public class Main {

	public static void main(String[] args) {
		new CommandLine(new QuakeConfigFormatterApplication()).execute(args);
	}
}
