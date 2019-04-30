package adam.birr.storage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class StoreService implements IStoreService {

	public void processAndStoreFile(String filename, String content) throws IOException, GeneralSecurityException {
		String[] words = content.split("[\\r\\n]+");
		List<String> wordsArrayList = Arrays.asList(words);
		wordsArrayList.sort(String::compareToIgnoreCase);
		
		FileStorageUtil.writeFile(filename, wordsArrayList);
		GoogleDriveStorageUtil.uploadFile(filename);
	}
}
