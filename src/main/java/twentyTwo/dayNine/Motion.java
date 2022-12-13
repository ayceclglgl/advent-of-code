package twentyTwo.dayNine;

public class Motion {
	private final Direction direction;
	private final int       steps;

	public Motion(Direction direction, int steps) {
		this.direction = direction;
		this.steps = steps;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getSteps() {
		return steps;
	}
}
