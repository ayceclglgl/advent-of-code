package twentyTwo.dayTen;

public class Instruction {
	private final String name;
	private       int    value;

	public Instruction(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
