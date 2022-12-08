package twentyTwo.dayFive;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SupplyStacks {
	private final static String ELEMENT_SPACE                     = " {3}";
	private static final String ELEMENT_BEGIN_CHARACTER           = "[";
	private static final int    ELEMENT_BEGIN_INDEX_IN_STACK_LINE = 4;
	public static final  String COMMAND_SEPARATOR                 = " ";

	public static void main(String[] args) throws IOException {
		Stream<String> lines = Files.lines(Path.of("src/main/resources/supplyStacks.txt"), StandardCharsets.UTF_8);
		String input = lines.collect(Collectors.joining(System.lineSeparator()));
		String[] stackAndCommands = input.split(System.lineSeparator() + System.lineSeparator());
		if (stackAndCommands.length != 2) {
			throw new IllegalArgumentException("Invalid input. Input should contain stack and commands information.");
		}

		String stackString = stackAndCommands[0];
		String commandString = stackAndCommands[1];
		List<Triple<Integer, Integer, Integer>> commandList = getCommands(commandString);

		System.out.println(moveRegularFashion(getStacks(stackString), commandList));
		System.out.println(moveMultipleElementsInOrder(getStacks(stackString), commandList));
	}

	private static String moveRegularFashion(List<Stack<String>> stacks, List<Triple<Integer, Integer, Integer>> commands) {
		commands.forEach(command -> {
			int itemNumber = command.getItemNumber();
			IntStream.range(0, itemNumber).forEach(i -> {
				String element = stacks.get(command.getFrom()).pop();
				stacks.get(command.getTo()).push(element);
			});
		});
		return getPeeks(stacks);
	}

	private static String moveMultipleElementsInOrder(List<Stack<String>> stacks, List<Triple<Integer, Integer, Integer>> commands) {
		commands.forEach(command -> {
			int itemNumber = command.getItemNumber();
			String[] tempArray = new String[itemNumber];
			IntStream.range(0, itemNumber).forEach(i -> {
				String element = stacks.get(command.getFrom()).pop();
				tempArray[i] = element;
			});
			IntStream.iterate(itemNumber - 1, i -> i - 1).limit(itemNumber)
					.forEach(i -> stacks.get(command.getTo()).push(tempArray[i]));
		});
		return getPeeks(stacks);
	}

	private static String getPeeks(List<Stack<String>> stacks) {
		return stacks.stream().map(Stack::peek).collect(Collectors.joining());
	}

	private static List<Triple<Integer, Integer, Integer>> getCommands(String commandString) {
		return Arrays.stream(commandString.split(System.lineSeparator()))
				.map(command -> command.split(COMMAND_SEPARATOR))
				.map(command -> new Triple<>(Integer.parseInt(command[1]), Integer.parseInt(command[3]) - 1,
						Integer.parseInt(command[5]) - 1))
				.collect(Collectors.toList());
	}

	private static List<Stack<String>> getStacks(String stackString) {
		String[] stackElements = stackString.split(System.lineSeparator());
		String stackCountLine = stackElements[stackElements.length - 1];
		int stackCount = stackCountLine.split(ELEMENT_SPACE).length;
		List<Stack<String>> stackList = IntStream.range(0, stackCount).mapToObj(i -> new Stack<String>()).collect(Collectors.toList());
		for (int i = stackElements.length - 2; i >= 0; i--) {
			int fromIndex = 0;
			String element = stackElements[i];
			for (int j = 0; j < stackCount && fromIndex < element.length(); j++) {
				int beginIndex = element.indexOf(ELEMENT_BEGIN_CHARACTER, fromIndex);
				stackList.get(beginIndex / ELEMENT_BEGIN_INDEX_IN_STACK_LINE).push(getElement(element, beginIndex));
				fromIndex = beginIndex + ELEMENT_BEGIN_INDEX_IN_STACK_LINE;
			}
		}
		return stackList;
	}

	private static String getElement(String input, int beginIndex) {
		// element string: [D]
		// element: D
		return input.substring(beginIndex + 1, beginIndex + 2);
	}

	private static class Triple<I, F, T> {
		private final I itemNumber;
		private final F from;
		private final T to;

		public Triple(I itemNumber, F from, T to) {
			this.itemNumber = itemNumber;
			this.from = from;
			this.to = to;
		}

		public I getItemNumber() {
			return itemNumber;
		}

		public F getFrom() {
			return from;
		}

		public T getTo() {
			return to;
		}
	}
}
