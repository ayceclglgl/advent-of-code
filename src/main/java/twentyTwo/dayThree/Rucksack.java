package twentyTwo.dayThree;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rucksack {

	public static void main(String[] args) {
		try (Stream<String> lines = Files.lines(Path.of("src/main/resources/rucksacks.txt"), StandardCharsets.UTF_8)) {
			List<String> items = lines.collect(Collectors.toList());

			List<Character> commonItemList = new ArrayList<>();
			items.forEach(item -> {
				String firstPart = item.substring(0, item.length() / 2);
				String secondPart = item.substring(item.length() / 2);
				findCommonCharacter(firstPart, secondPart).ifPresent(commonItemList::add);
			});
			System.out.println("Sum of the priorities of item types: " + getSum(commonItemList));

			List<Character> commonItemInGroupedThree = new ArrayList<>();
			int index = 0;
			while (index < items.size()) {
				String firstItem = items.get(index++);
				String secondItem = items.get(index++);
				String thirdItem = items.get(index++);
				String commonCharacters = findCommonCharacters(firstItem, secondItem);
				findCommonCharacter(commonCharacters, thirdItem).ifPresent(commonItemInGroupedThree::add);
			}
			System.out.println("Sum of the priorities of item types: " + getSum(commonItemInGroupedThree));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getSum(List<Character> list) {
		return list.stream().mapToInt(character -> {
			if (isLowerCase(character)) {
				return character - 'a' + 1;
			}
			if (isUpperCase(character)) {
				return character - 'A' + 27;
			}
			return 0;
		}).sum();
	}

	private static Optional<Character> findCommonCharacter(String firstString, String secondString) {
		List<Character> commonCharacterList = new ArrayList<>();
		for (char c : firstString.toCharArray()) {
			if (secondString.indexOf(c) != -1) {
				commonCharacterList.add(c);
			}
		}
		if (commonCharacterList.stream().allMatch(commonCharacterList.get(0)::equals)) {
			return Optional.of(commonCharacterList.get(0));
		}
		return Optional.empty();
	}

	private static String findCommonCharacters(String firstString, String secondString) {
		List<Character> commonCharacterList = new ArrayList<>();
		for (char c : firstString.toCharArray()) {
			if (secondString.indexOf(c) != -1) {
				commonCharacterList.add(c);
			}
		}
		return commonCharacterList.stream().map(String::valueOf).collect(Collectors.joining());
	}
}
