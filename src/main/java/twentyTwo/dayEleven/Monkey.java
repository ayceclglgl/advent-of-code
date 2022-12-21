package twentyTwo.dayEleven;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Monkey {
	private static final String BY           = "by";
	private static final String MONKEY       = "monkey";
	private static final String COLON        = ":";
	private static final String COMMA        = ",";
	private static final String EQUAL        = "=";
	private static final String SPACE_STRING = " ";
	private static final String DIVIDE       = "/";

	private       List<Integer> items;
	private final Operation     operation;
	private final Test          test;

	public Monkey(String monkey) {
		String[] monkeyInformation = monkey.split(System.lineSeparator());
		String[] itemArray = monkeyInformation[1].split(COLON);
		String itemString = itemArray[itemArray.length - 1];
		this.items = Arrays.stream(itemString.split(COMMA)).map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());

		String[] tempOperationArray = monkeyInformation[2].split(COLON);
		String[] operationArray = tempOperationArray[1].split(EQUAL);
		String[] operation = operationArray[1].trim().split(SPACE_STRING);
		this.operation = new Operation(operation[1], operation[2]);

		String testString = monkeyInformation[3].split(COLON)[1];
		String trueTestCase = monkeyInformation[4].split(COLON)[1];
		int firstItem = Integer.parseInt(trueTestCase.substring(trueTestCase.indexOf(MONKEY) + MONKEY.length()).trim());
		String falseTestCase = monkeyInformation[5].split(COLON)[1];
		int secondItem = Integer.parseInt(falseTestCase.substring(falseTestCase.indexOf(MONKEY) + MONKEY.length()).trim());
		this.test = new Test(new Operation(DIVIDE, testString.substring(testString.indexOf(BY) + BY.length()).trim()),
				new Pair(firstItem, secondItem));
	}

	public List<Integer> getItems() {
		return items;
	}

	public List<Integer> setItems(List<Integer> items) {
		return this.items = items;
	}

	public Operation getOperation() {
		return operation;
	}

	public Test getTest() {
		return test;
	}

	class Operation {
		private final Operand operand;
		private final int     value;

		public Operation(String operand, String value) {
			this.operand = Operand.fromString(operand);

			int number;
			try {
				number = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				number = 0;
			}
			this.value = number;
		}

		public Operand getOperands() {
			return operand;
		}

		public int getValue() {
			return value;
		}
	}

	class Test {
		private final Operation operation;
		private final Pair      pair;

		public Test(Operation operation, Pair pair) {
			this.operation = operation;
			this.pair = pair;
		}

		public Operation getOperation() {
			return operation;
		}

		public Pair getPair() {
			return pair;
		}
	}

	class Pair {
		private final int firstItem;
		private final int secondItem;

		public Pair(int firstItem, int secondItem) {
			this.firstItem = firstItem;
			this.secondItem = secondItem;
		}

		public int getFirstItem() {
			return firstItem;
		}

		public int getSecondItem() {
			return secondItem;
		}
	}
}
