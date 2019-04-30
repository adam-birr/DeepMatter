package adam.birr.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileStorageUtil {

	public static void writeFile(String filename, List<String> lines) throws IOException {
		Files.write(Paths.get("/" + filename + ".txt"), lines);
	}
}
