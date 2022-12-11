package twentyTwo.daySeven;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoSpaceLeftOnDevice {
	private static final String LS              = "$ ls";
	private static final String CD_ONE_LEVEL_UP = "$ cd ..";
	private static final String CD              = "$ cd ";
	private static final String DIR             = "dir ";
	private static final String EMPTY_STRING    = "";
	private static final String SPACE_STRING    = " ";

	private static final int SIZE_LIMIT   = 100000;
	private static final int UPDATE_LIMIT = 30000000;
	private static final int TOTAL_SIZE   = 70000000;

	public static void main(String[] args) throws IOException {
		Stream<String> lines = Files.lines(Path.of("src/main/resources/noSpaceLeftOnDevice.txt"), StandardCharsets.UTF_8);
		List<String> input = lines.collect(Collectors.toList());
		Directory directory = null;
		for (String line : input) {
			directory = getDirectoryHierarchy(line, directory);
		}
		Directory root = getRootDirectory(directory).orElseThrow(() -> new IllegalStateException("Root folder could not be found."));
		List<Directory> directoryList = getAllDirectories(root);
		long sum = directoryList.stream().mapToInt(Directory::getSize).filter(s -> s < SIZE_LIMIT).sum();
		System.out.println("Sum of the total sizes of directories with a total size of at most 100000: " + sum);

		int unusedSpace = TOTAL_SIZE - root.getSize();
		int spaceNeeded = UPDATE_LIMIT - unusedSpace;
		int smallestDirectorySize = directoryList.stream().mapToInt(Directory::getSize).filter(s -> s > spaceNeeded).min().orElse(0);
		System.out.println("Smallest directory that, if deleted, would free up enough space on the filesystem: " + smallestDirectorySize);
	}

	private static List<Directory> getAllDirectories(Directory root) {
		List<Directory> list = new ArrayList<>();
		for (Directory directory : root.getDirectories()) {
			list.add(directory);
			list.addAll(getAllDirectories(directory));
		}
		return list;
	}

	private static Optional<Directory> getRootDirectory(Directory directory) {
		if (directory == null) {
			return Optional.empty();
		}

		while (directory.getParent() != null) {
			directory = directory.getParent();
		}
		return Optional.of(directory);
	}

	private static Directory getDirectoryHierarchy(String line, Directory parent) {
		if (line.startsWith(LS)) {
			return parent;
		}
		if (line.startsWith(CD_ONE_LEVEL_UP)) {
			return parent.getParent();
		}
		if (line.startsWith(CD)) {
			String directoryName = line.replace(CD, EMPTY_STRING);
			if (Character.isLetter(directoryName.charAt(0))) {
				return parent.getDirectories().stream().filter(d -> d.getName().equals(directoryName)).findFirst()
						.orElseThrow(() -> new IllegalArgumentException("Invalid input. Directory must be created oin order to navigate."));
			}
			return new Directory(directoryName, parent);
		}
		if (line.startsWith(DIR)) {
			Directory child = new Directory(line.replace(DIR, EMPTY_STRING), parent);
			parent.getDirectories().add(child);
			return parent;
		}
		String[] fileLine = line.split(SPACE_STRING);
		int size = Integer.parseInt(fileLine[0]);
		File file = new File(fileLine[1], size);
		parent.getFiles().add(file);
		return parent;
	}
}
