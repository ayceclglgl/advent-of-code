package twentyTwo.dayNine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RopeBridge {
	public static final String SPACE_STRING = " ";

	public static void main(String[] args) throws IOException {
		Stream<String> lines = Files.lines(Path.of("src/main/resources/ropeBridge.txt"), StandardCharsets.UTF_8);
		List<Motion> motions = new ArrayList<>();
		lines.forEach(line -> {
			String[] commandLine = line.split(SPACE_STRING);
			motions.add(new Motion(Direction.getDirection(commandLine[0]), Integer.parseInt(commandLine[1])));
		});

		Point head = new Point(0, 0);
		Point tail = new Point(0, 0);
		Rope ropeWithTail = new Rope(head, List.of(tail));
		ropeWithHeadAndTail(ropeWithTail, motions);

		Point headOfKnots = new Point(0, 0);
		List<Point> knots = new ArrayList<>();
		IntStream.range(0, 9).forEach(knot -> knots.add(new Point(0, 0)));
		Rope ropeWithKnots = new Rope(headOfKnots, knots);
		ropeWithKnots(ropeWithKnots, motions);
	}

	private static void ropeWithKnots(Rope rope, List<Motion> motions) {
		Point head = rope.getHead();
		List<Point> knots = rope.getKnots();

		Set<Point> tailCells = new HashSet<>();
		// The last knot is tail
		int tailIndex = knots.size() - 1;
		tailCells.add(new Point(rope.getKnots().get(tailIndex).getX(), knots.get(tailIndex).getY()));

		motions.forEach(motion -> {
			IntStream.range(0, motion.getSteps()).forEach(step -> {
				move(head, motion.getDirection());
				for (int i = 0; i < knots.size(); i++) {
					Point first = i == 0 ? head : knots.get(i - 1);
					Point second = knots.get(i);
					if (isKnotMove(first, second)) {
						Direction direction = findKnotDirection(first, second);
						move(second, direction);
						//Tail
						if (i == tailIndex) {
							tailCells.add(new Point(second.getX(), second.getY()));
						}
					}
				}
			});
		});
		System.out.printf("Head: %s %s%n", head.getX(), head.getY());
		System.out.printf("Tail: %s %s%n", knots.get(tailIndex).getX(), knots.get(tailIndex).getY());
		System.out.printf("Tail cells: %s%n", tailCells.size());
	}

	private static void ropeWithHeadAndTail(Rope rope, List<Motion> motions) {
		Point head = rope.getHead();
		Point tail = rope.getKnots().get(0);

		Set<Point> tailCells = new HashSet<>();
		tailCells.add(new Point(tail.getX(), tail.getY()));
		motions.forEach(motion -> IntStream.range(0, motion.getSteps()).forEach(step -> {
			move(head, motion.getDirection());
			if (isKnotMove(head, tail)) {
				Direction direction = findKnotDirection(head, tail);
				move(tail, direction);
				tailCells.add(new Point(tail.getX(), tail.getY()));
			}
		}));
		System.out.printf("Head: %s %s%n", head.getX(), head.getY());
		System.out.printf("Tail: %s %s%n", tail.getX(), tail.getY());
		System.out.printf("Tail cells: %s%n", tailCells.size());
	}

	private static void move(Point point, Direction direction) {
		point.setX(point.getX() + direction.getX());
		point.setY(point.getY() + direction.getY());
	}

	private static Direction findKnotDirection(Point head, Point tail) {
		int x = head.getX() - tail.getX();
		int y = head.getY() - tail.getY();

		if (x == 0 && y < 0) {
			return Direction.D;
		}
		if (x == 0 && y > 0) {
			return Direction.U;
		}
		if (y == 0 && x < 0) {
			return Direction.L;
		}
		if (y == 0 && x > 0) {
			return Direction.R;
		}
		if (x > 0 && y > 0) {
			return Direction.UR;
		}
		if (x < 0 && y > 0) {
			return Direction.UL;
		}
		if (x > 0) {
			return Direction.DR;
		}
		return Direction.DL;
	}

	private static boolean isKnotMove(Point head, Point tail) {
		return Math.abs(head.getX() - tail.getX()) > 1 || Math.abs(head.getY() - tail.getY()) > 1;
	}

}
