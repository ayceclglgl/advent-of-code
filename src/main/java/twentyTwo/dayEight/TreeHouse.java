package twentyTwo.dayEight;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeHouse {
	
	public static void main(String[] args) throws IOException {
		Stream<String> lines = Files.lines(Path.of("src/main/resources/treeHouse.txt"), StandardCharsets.UTF_8);
		List<char[]> treeHeights = lines.map(String::toCharArray).collect(Collectors.toList());
		System.out.println("Visible trees: " + getVisibleTreeCount(treeHeights));
		System.out.println("Highest scenic score: " + getHighestScenicScore(treeHeights));

	}

	private static int getHighestScenicScore(List<char[]> treeHeights) {
		int maximumScore = 0;
		for (int i = 1; i < treeHeights.size() - 1; i++) {
			char[] trees = treeHeights.get(i);
			for (int j = 1; j < trees.length - 1; j++) {
				int score = getLeftScore(trees, j) * getRightScore(trees, j) * getTopScore(treeHeights, j, i) * getDownScore(treeHeights, j, i);
				if (score > maximumScore) {
					maximumScore = score;
				}
			}
		}
		return maximumScore;
	}

	private static int getDownScore(List<char[]> treeHeights, int rowIndex, int columnIndex) {
		int count = 0;
		for (int i = columnIndex + 1; i < treeHeights.size(); i++) {
			if (treeHeights.get(i)[rowIndex] >= treeHeights.get(columnIndex)[rowIndex]) {
				return ++count;
			}
			count++;
		}
		return count;
	}

	private static int getTopScore(List<char[]> treeHeights, int rowIndex, int columnIndex) {
		int count = 0;
		for (int i = columnIndex - 1; i >= 0; i--) {
			if (treeHeights.get(i)[rowIndex] >= treeHeights.get(columnIndex)[rowIndex]) {
				return ++count;
			}
			count++;
		}
		return count;
	}

	private static int getRightScore(char[] trees, int j) {
		int count = 0;
		for (int i = j + 1; i < trees.length; i++) {
			if (trees[i] >= trees[j]) {
				return ++count;
			}
			count++;
		}
		return count;
	}

	private static int getLeftScore(char[] trees, int j) {
		int count = 0;
		for (int i = j - 1; i >= 0; i--) {
			if (trees[i] >= trees[j]) {
				return ++count;
			}
			count++;
		}
		return count;
	}

	private static int getVisibleTreeCount(List<char[]> treeHeights) {
		int count = 0;
		for (int i = 1; i < treeHeights.size() - 1; i++) {
			char[] trees = treeHeights.get(i);
			for (int j = 1; j < trees.length - 1; j++) {
				if (isVisibleFromLeft(trees, j)) {
					count++;
					continue;
				}
				if (isVisibleFromRight(trees, j)) {
					count++;
					continue;
				}
				if (isVisibleFromTop(treeHeights, i, j)) {
					count++;
					continue;
				}
				if (isVisibleFromBottom(treeHeights, i, j)) {
					count++;
				}
			}
		}
		// add top and bottom trees
		count += treeHeights.get(0).length * 2;
		// add left and right trees
		count += (treeHeights.size() - 2) * 2;
		return count;
	}

	private static boolean isVisibleFromBottom(List<char[]> treeHeights, int rowIndex, int elementIndex) {
		char tree = treeHeights.get(rowIndex)[elementIndex];
		for (int i = treeHeights.size() - 1; i > rowIndex; i--) {
			if (treeHeights.get(i)[elementIndex] >= tree) {
				return false;
			}
		}
		return true;
	}

	private static boolean isVisibleFromTop(List<char[]> treeHeights, int rowIndex, int elementIndex) {
		char tree = treeHeights.get(rowIndex)[elementIndex];
		for (int i = 0; i < rowIndex; i++) {
			if (treeHeights.get(i)[elementIndex] >= tree) {
				return false;
			}
		}
		return true;
	}

	private static boolean isVisibleFromRight(char[] trees, int index) {
		char tree = trees[index];
		for (int i = trees.length - 1; i > index; i--) {
			if (trees[i] >= tree) {
				return false;
			}
		}
		return true;
	}

	private static boolean isVisibleFromLeft(char[] trees, int index) {
		char tree = trees[index];
		for (int i = 0; i < index; i++) {
			if (trees[i] >= tree) {
				return false;
			}
		}
		return true;
	}
}
