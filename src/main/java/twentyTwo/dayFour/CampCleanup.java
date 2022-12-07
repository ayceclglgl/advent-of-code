package twentyTwo.dayFour;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class CampCleanup {

	public static void main(String[] args) {
		try (Stream<String> lines = Files.lines(Path.of("src/main/resources/campCleanup.txt"), StandardCharsets.UTF_8)) {
			AtomicInteger containedSectionsCount = new AtomicInteger();
			AtomicInteger overlapSectionsCount = new AtomicInteger();
			lines.forEach(line -> {
				String[] elfPairs = line.split(",");
				int[] firstElfSections = Arrays.stream(elfPairs[0].split("-")).mapToInt(Integer::parseInt).toArray();
				int[] secondElfSections = Arrays.stream(elfPairs[1].split("-")).mapToInt(Integer::parseInt).toArray();
				if (isContain(firstElfSections, secondElfSections)) {
					containedSectionsCount.addAndGet(1);
				}
				if (isOverlap(firstElfSections, secondElfSections)) {
					overlapSectionsCount.addAndGet(1);
				}
			});
			System.out.println("Sections that fully contain the other: " + containedSectionsCount);
			System.out.println("Overlap section : " + overlapSectionsCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isContain(int[] firstElfSections, int[] secondElfSections) {
		return secondElfSections[0] >= firstElfSections[0] && secondElfSections[1] <= firstElfSections[1] ||
				firstElfSections[0] >= secondElfSections[0] && firstElfSections[1] <= secondElfSections[1];
	}

	private static boolean isOverlap(int[] firstElfSections, int[] secondElfSections) {
		return firstElfSections[0] >= secondElfSections[0] && firstElfSections[0] <= secondElfSections[1] ||
				secondElfSections[0] >= firstElfSections[0] && secondElfSections[0] <= firstElfSections[1];
	}
}
