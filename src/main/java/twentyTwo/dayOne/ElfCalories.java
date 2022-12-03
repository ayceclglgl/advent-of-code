package twentyTwo.dayOne;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElfCalories {
	public static void main(String[] args) {
		try (Stream<String> lines = Files.lines(Path.of("src/main/resources/elfCalories.txt"), StandardCharsets.UTF_8)) {
			String calories = lines.collect(Collectors.joining(System.lineSeparator()));
			List<Integer> elvesCalories = Arrays.stream(calories.split(System.lineSeparator() + System.lineSeparator()))
					.map(elfCalories -> Arrays.stream(elfCalories.split(System.lineSeparator())).mapToInt(Integer::parseInt).sum())
					.sorted(Collections.reverseOrder())
					.collect(Collectors.toList());
			System.out.println("Elf that has most calories: " + elvesCalories.get(0));

			int topThreeElvesCalories = elvesCalories.subList(0, 3).stream().mapToInt(i -> i).sum();
			System.out.println("Calories of top three elves: " + topThreeElvesCalories);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
