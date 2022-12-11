package twentyTwo.daySeven;

import java.util.ArrayList;
import java.util.List;

public class Directory {
	private final Directory       parent;
	private final List<Directory> directories;

	private final String     name;
	private final List<File> files;

	public Directory(String name, Directory parent) {
		this.name = name;
		this.parent = parent;
		this.directories = new ArrayList<>();
		this.files = new ArrayList<>();
	}

	public Directory getParent() {
		return parent;
	}

	public List<Directory> getDirectories() {
		return directories;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return files.stream().mapToInt(File::getSize).sum() + directories.stream().mapToInt(Directory::getSize).sum();
	}

	public List<File> getFiles() {
		return files;
	}
}
