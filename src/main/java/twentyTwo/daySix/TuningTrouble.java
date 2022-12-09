package twentyTwo.daySix;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TuningTrouble {
	private static final int START_OF_PACKET_MARKER  = 4;
	private static final int START_OF_MESSAGE_MARKER = 14;

	public static void main(String[] args) {
		try (Stream<String> lines = Files.lines(Path.of("src/main/resources/tuningTrouble.txt"), StandardCharsets.UTF_8)) {
			String input = lines.collect(Collectors.joining());
			int packetMarkerCharCount = getCharacterCount(START_OF_PACKET_MARKER, input);
			int messageMarkerCharCount = getCharacterCount(START_OF_MESSAGE_MARKER, input);
			System.out.println("Characters need to be processed before the first start of packet: " + packetMarkerCharCount);
			System.out.println("Characters need to be processed before the first start of message: " + messageMarkerCharCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getCharacterCount(int characterCount, String input) {
		int windowStart = 0;
		for (int windowEnd = characterCount; windowEnd < input.length(); windowEnd++) {
			String tempString = input.substring(windowStart, windowEnd);
			if (isUniqueCharacters(tempString)) {
				return windowEnd;
			}
			windowStart++;
		}
		throw new IllegalArgumentException("Could not found a substring that is consist of distinct characters.");
	}

	private static boolean isUniqueCharacters(String input) {
		return input.chars().distinct().count() == input.length();
	}
}
