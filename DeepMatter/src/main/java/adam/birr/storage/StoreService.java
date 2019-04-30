package adam.birr.storage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class StoreService implements IStoreService {

	private static final String NEW_LINE_REGEX = "[\\r\\n]+";
	private static final int NOT_PROVIDED = -1;
	
	@Override
	public void processAndStoreFile(String filename, String content) throws IOException, GeneralSecurityException {
		String[] words = content.split(NEW_LINE_REGEX);
		List<String> wordsArrayList = Arrays.asList(words);
		wordsArrayList.sort(String::compareToIgnoreCase);
		
		System.out.println("storing " + wordsArrayList.toString() + " to file:" + filename);
		FileStorageUtil.writeFile(filename, wordsArrayList);
		GoogleDriveStorageUtil.uploadFile(filename);
	}

	@Override
	public List<String> retrieveFileAndProcess(String filename) throws Exception {
		return retrieveFileAndProcess(filename, NOT_PROVIDED, NOT_PROVIDED);
	}

	@Override
	public List<String> retrieveFileAndProcess(String filename, int begin) throws Exception {
		return retrieveFileAndProcess(filename, begin, NOT_PROVIDED);
	}

	@Override
	public List<String> retrieveFileAndProcess(String filename, int begin, int end) throws Exception {
		if (begin == NOT_PROVIDED) {
			begin = 0;
		}
		try {
			String contentString = GoogleDriveStorageUtil.downloadFileContents(filename);
			List<String> lines = Arrays.asList( contentString.split(NEW_LINE_REGEX) );
			if (end == NOT_PROVIDED) {
				end = lines.size();
			}			
			return lines.subList(begin, end);
		} catch (GeneralSecurityException | IOException e) {
			throw e;
		}
	}
}
