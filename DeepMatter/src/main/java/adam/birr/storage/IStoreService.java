package adam.birr.storage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface IStoreService {

	public void processAndStoreFile(String filename, String content) throws IOException, GeneralSecurityException;

	public List<String> retrieveFileAndProcess(String filename) throws Exception;
	
	public List<String> retrieveFileAndProcess(String filename, int begin) throws Exception;

	public List<String> retrieveFileAndProcess(String filename, int begin, int end) throws Exception;
	
}
