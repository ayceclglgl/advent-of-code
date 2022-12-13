package twentyTwo.dayNine;

import java.util.ArrayList;
import java.util.List;

public class Rope {
	private final Point       head;
	private final List<Point> knots = new ArrayList<>();

	public Rope(Point head, List<Point> knots) {
		this.head = head;
		this.knots.addAll(knots);
	}

	public Point getHead() {
		return head;
	}

	public List<Point> getKnots() {
		return knots;
	}
}
