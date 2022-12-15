package twentyTwo.dayTen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CathodeRayTube {
	private static final String SPACE_STRING = " ";

	private static final String INSTRUCTION_NOOP = "noop";
	private static final String INSTRUCTION_ADDX = "addx";

	private static final int INSTRUCTION_ADDX_CYCLE = 2;
	private static final int CYCLE_20               = 20;
	private static final int CYCLE_40               = 40;

	public static final int    HIGH = 6;
	public static final int    WIDE = 40;
	public static final String DOT  = ".";
	public static final String HASH = "#";

	public static void main(String[] args) throws IOException {
		Stream<String> lines = Files.lines(Path.of("src/main/resources/cathodeRayTube.txt"), StandardCharsets.UTF_8);
		List<Instruction> instructions = new ArrayList<>();
		lines.forEach(line -> {
			String[] commandLine = line.split(SPACE_STRING);
			if (commandLine.length == 1) {
				instructions.add(new Instruction(commandLine[0]));
			} else {
				Instruction instruction = new Instruction(commandLine[0]);
				instruction.setValue(Integer.parseInt(commandLine[1]));
				instructions.add(instruction);
			}
		});

		System.out.printf("Sum of these six signal strengths: %s%n", getSignalStrengths(instructions));
		String[][] screen = new String[HIGH][WIDE];
		draw(instructions, screen);
		print(screen);
	}

	private static void draw(List<Instruction> instructions, String[][] screen) {
		int cycle = 0;
		int registerX = 1;
		// Sprite: ### Position of sprite is the middle.
		int spritePosition = 1;

		for (Instruction instruction : instructions) {
			if (INSTRUCTION_NOOP.equals(instruction.getName())) {
				cycle++;
				screen[getRowIndex(cycle)][getColumnIndex(cycle)] = getPixel(cycle, spritePosition);
			} else if (INSTRUCTION_ADDX.equals(instruction.getName())) {
				for (int i = 0; i < INSTRUCTION_ADDX_CYCLE; i++) {
					cycle++;
					screen[getRowIndex(cycle)][getColumnIndex(cycle)] = getPixel(cycle, spritePosition);
				}
				registerX += instruction.getValue();
				spritePosition = registerX;
			}
		}
	}

	private static int getRowIndex(int cycle) {
		return (cycle - 1) / 40;
	}

	private static int getColumnIndex(int cycle) {
		return (cycle - 1) % 40;
	}

	private static String getPixel(int cycle, int spritePosition) {
		int index = getColumnIndex(cycle);
		return isOverlap(index, spritePosition) ? HASH : DOT;
	}

	private static boolean isOverlap(int index, int spritePosition) {
		return index == spritePosition || index == spritePosition - 1 || index == spritePosition + 1;
	}

	private static void print(String[][] screen) {
		for (String[] row : screen) {
			for (int j = 0; j < row.length; j++) {
				System.out.print(row[j]);
				if (j == row.length - 1) {
					System.out.println();
				}
			}
		}
	}

	private static int getSignalStrengths(List<Instruction> instructions) {
		int cycle = 0;
		int registerX = 1;
		int sumOfSignalStrengths = 0;
		for (Instruction instruction : instructions) {
			if (INSTRUCTION_NOOP.equals(instruction.getName())) {
				cycle++;
				sumOfSignalStrengths += check(cycle, registerX);
			} else if (INSTRUCTION_ADDX.equals(instruction.getName())) {
				for (int i = 0; i < INSTRUCTION_ADDX_CYCLE; i++) {
					cycle++;
					sumOfSignalStrengths += check(cycle, registerX);
				}
				registerX += instruction.getValue();
			}
		}
		return sumOfSignalStrengths;
	}

	private static int check(int cycle, int registerX) {
		if (cycle == CYCLE_20 || (cycle - CYCLE_20) % CYCLE_40 == 0) {
			return cycle * registerX;
		}
		return 0;
	}

}
