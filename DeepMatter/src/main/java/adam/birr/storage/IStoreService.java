package adam.birr.storage;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IStoreService {

	public void processAndStoreFile(String filename, String content) throws IOException, GeneralSecurityException;

}
