package twentyTwo.dayEleven;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MonkeyInTheMiddle {
	public static void main(String[] args) throws IOException {
		Stream<String> lines = Files.lines(Path.of("src/main/resources/monkeyInTheMiddle.txt"), StandardCharsets.UTF_8);
		String input = lines.collect(Collectors.joining(System.lineSeparator()));
		List<Monkey> monkeyList = Arrays.stream(input.split(System.lineSeparator() + System.lineSeparator())).map(Monkey::new)
				.collect(Collectors.toList());

		int[] inspection = new int[monkeyList.size()];
		IntStream.range(0, 20).forEach(i -> round(monkeyList, inspection));
		int[] sortedInspection = Arrays.stream(inspection).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
		System.out.printf("Monkey business: %s", sortedInspection[0] * sortedInspection[1]);
	}

	private static void round(List<Monkey> monkeyList, int[] inspection) {
		for (int i = 0; i < monkeyList.size(); i++) {
			Monkey monkey = monkeyList.get(i);
			List<Integer> items = monkey.getItems();
			inspection[i] += items.size();
			for (Integer item : items) {
				Monkey.Operation operation = monkey.getOperation();
				int operationValue = operation.getValue() == 0 ? item : operation.getValue();
				int worryLevel = 0;
				switch (operation.getOperands()) {
				case multiply:
					worryLevel = operationValue * item / 3;
					break;
				case divide:
					worryLevel = operationValue / item / 3;
					break;
				case add:
					worryLevel = (operationValue + item) / 3;
					break;
				case substract:
					worryLevel = (operationValue - item) / 3;
					break;
				}
				Monkey.Test test = monkey.getTest();
				Monkey.Pair pair = test.getPair();
				int whichMonkeyToThrow = worryLevel % test.getOperation().getValue() == 0 ? pair.getFirstItem() : pair.getSecondItem();
				monkeyList.get(whichMonkeyToThrow).getItems().add(worryLevel);
			}
			monkey.setItems(new ArrayList<>());
		}
	}
}
