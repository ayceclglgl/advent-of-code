package twentyTwo.dayTwo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RockPaperScissors {

	// Paper > Rock
	// Rock > Scissors
	// Scissors > Paper
	private static final String ROCK     = "R";
	private static final String SCISSORS = "S";
	private static final String PAPER    = "P";

	private static final String EMPTY_STRING = "";
	private static final String SPACE        = " ";

	private static final Map<String, String> FIRST_PLAYER  = Map.of("A", ROCK, "B", PAPER, "C", SCISSORS);
	private static final Map<String, String> SECOND_PLAYER = Map.of("X", ROCK, "Y", PAPER, "Z", SCISSORS);

	public static void main(String[] args) {
		try (Stream<String> lines = Files.lines(Path.of("src/main/resources/rockPaperScissors.txt"), StandardCharsets.UTF_8)) {
			String players = lines.collect(Collectors.joining(System.lineSeparator()));
			List<String> playerOne = new ArrayList<>();
			List<String> playerTwo = new ArrayList<>();
			Arrays.stream(players.split(System.lineSeparator()))
					.map(s -> s.split(SPACE)).forEach(s -> {
						if (s.length != 2) {
							throw new IllegalArgumentException("Input is not right, there are 2 players");
						}
						playerOne.add(s[0]);
						playerTwo.add(s[1]);
					});

			int totalScore = 0;
			for (int i = 0; i < playerOne.size(); i++) {
				String playerOneChoice = playerOne.get(i);
				String playerTwoChoice = playerTwo.get(i);
				totalScore += getPointOfShape(SECOND_PLAYER.getOrDefault(playerTwoChoice, EMPTY_STRING));
				totalScore += play(FIRST_PLAYER.getOrDefault(playerOneChoice, EMPTY_STRING),
						SECOND_PLAYER.getOrDefault(playerTwoChoice, EMPTY_STRING));
			}
			System.out.println("Total score is: " + totalScore);

			int totalScoreChangeStrategy = 0;
			for (int i = 0; i < playerOne.size(); i++) {
				String playerOneChoice = playerOne.get(i);
				String howRoundEnds = playerTwo.get(i);
				totalScoreChangeStrategy += getRoundPoint(howRoundEnds);
				String shape = chooseShape(FIRST_PLAYER.getOrDefault(playerOneChoice, EMPTY_STRING), howRoundEnds);
				totalScoreChangeStrategy += getPointOfShape(shape);
			}
			System.out.println("Total score in change strategy is: " + totalScoreChangeStrategy);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String chooseShape(String playerOneChoice, String howRoundEnds) {
		if ("Y".equals(howRoundEnds)) {
			return playerOneChoice;
		}
		if ("Z".equals(howRoundEnds)) {
			switch (playerOneChoice) {
			case ROCK:
				return PAPER;
			case SCISSORS:
				return ROCK;
			case PAPER:
				return SCISSORS;
			}
		}

		if ("X".equals(howRoundEnds)) {
			switch (playerOneChoice) {
			case PAPER:
				return ROCK;
			case SCISSORS:
				return PAPER;
			case ROCK:
				return SCISSORS;
			}
		}
		return EMPTY_STRING;
	}

	private static int getRoundPoint(String howRoundEnds) {
		switch (howRoundEnds) {
		case "Y":
			return 3;
		case "Z":
			return 6;
		case "X":
		default:
			return 0;
		}
	}

	private static int getPointOfShape(String choice) {
		// 1 point for Rock, 2 points for Paper, and 3 points for Scissors
		switch (choice) {
		case ROCK:
			return 1;
		case PAPER:
			return 2;
		case SCISSORS:
			return 3;
		default:
			return 0;
		}
	}

	private static int play(String playerOneChoice, String playerTwoChoice) {
		if (playerOneChoice.equals(playerTwoChoice)) {
			return 3;
		}
		if (PAPER.equals(playerTwoChoice) && ROCK.equals(playerOneChoice)) {
			return 6;
		}
		if (ROCK.equals(playerTwoChoice) && SCISSORS.equals(playerOneChoice)) {
			return 6;
		}
		if (SCISSORS.equals(playerTwoChoice) && PAPER.equals(playerOneChoice)) {
			return 6;
		}
		return 0;
	}

}
