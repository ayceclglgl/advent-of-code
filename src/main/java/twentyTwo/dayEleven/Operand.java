package twentyTwo.dayEleven;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Operand {

	multiply("*"), divide("/"), add("+"), substract("-"), unknown("");

	private static final Map<String, Operand> operandTypeMap = Arrays.stream(Operand.values())
			.collect(Collectors.toMap(Operand::getOperand, a -> a));

	private final String operand;

	Operand(String operand) {
		this.operand = operand;
	}

	public String getOperand() {
		return operand;
	}

	public static Operand fromString(String operand) {
		return operandTypeMap.getOrDefault(operand, Operand.unknown);
	}

}
