package twentyTwo.dayNine;

public enum Direction {
	R(1, 0), L(-1, 0), U(0, 1), D(0, -1), UR(1, 1), UL(-1, 1), DR(1, -1), DL(-1, -1);

	private final int x;
	private final int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static Direction getDirection(String key) {
		switch (key) {
		case "R":
			return Direction.R;
		case "L":
			return Direction.L;
		case "U":
			return Direction.U;
		case "D":
			return Direction.D;
		default:
			throw new IllegalArgumentException("Invalid input, direction must be right(R), left(L), up(U) or down(D)");
		}
	}
}
