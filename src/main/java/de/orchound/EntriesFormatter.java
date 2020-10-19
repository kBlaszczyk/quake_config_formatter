package de.orchound;

import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntriesFormatter {

	private final Pattern entryPattern = Pattern.compile("^[a-z]+ (\\w+) .+$");
	private final Map<String, TreeSet<String>> entryClusters = new TreeMap<>();

	public void addEntry(String entry) {
		getEntryPrefix(entry).ifPresent(entryPrefix -> {
			if (entryClusters.containsKey(entryPrefix)) {
				entryClusters.get(entryPrefix).add(entry);
			} else {
				TreeSet<String> entries = new TreeSet<>();
				entries.add(entry);
				entryClusters.put(entryPrefix, entries);
			}
		});
	}

	private Optional<String> getEntryPrefix(String entry) {
		Matcher matcher = entryPattern.matcher(entry);
		if (matcher.find()) {
			String name = matcher.group(1);
			int prefixIndex = name.indexOf('_');
			int targetIndex = prefixIndex > 0 ? prefixIndex : Math.min(name.length(), 3);
			return Optional.of(name.substring(0, targetIndex));
		}

		return Optional.empty();
	}

	public void print(PrintWriter writer) {
		boolean firstLine = true;
		boolean lineBreakRequired = false;

		for (Set<String> entryCluster : entryClusters.values()) {
			if (lineBreakRequired || entryCluster.size() > 2 && !firstLine)
				writer.println();

			entryCluster.forEach(writer::println);
			lineBreakRequired = entryCluster.size() > 2;
			firstLine = false;
		}
	}
}
