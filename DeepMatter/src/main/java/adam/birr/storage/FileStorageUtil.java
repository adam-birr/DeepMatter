package adam.birr.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileStorageUtil {

	public static void writeFile(String filename, List<String> lines) throws IOException {
		Files.write(Paths.get(System.getProperty("java.io.tmpdir") + "/" + filename + ".txt"), lines);
		System.out.println("created file");
	}
	
	public static List<String> readFile(String filename) throws IOException {
		List<String> fileLines = new ArrayList<String>();

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(System.getProperty("java.io.tmpdir") + "/" + filename + ".txt") ) ) {

			stream.forEach(s -> fileLines.add(s));
			return fileLines;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}		
	}
}
