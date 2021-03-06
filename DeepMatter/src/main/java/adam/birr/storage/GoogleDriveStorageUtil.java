package adam.birr.storage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

// Mainly taken from the Google quickstart guide and other documentation and adapted accordingly
public class GoogleDriveStorageUtil {
	private static final String APPLICATION_NAME = "Deep Matter";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	// Changed from ReadOnly Scope, but still get a scoping error
	private static List<String> scopes = Collections.singletonList(DriveScopes.DRIVE);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

		// Load client secrets.
		InputStream in = GoogleDriveStorageUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		System.out.println("Google drive scopes:" + scopes);
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, scopes)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	public static File uploadFile(String fileName) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		File fileMetadata = new File();
		fileMetadata.setName(fileName + ".txt");
		java.io.File filePath = new java.io.File(System.getProperty("java.io.tmpdir") + "/");
		FileContent content = new FileContent(null, filePath);
		System.out.println("starting upload of " +  System.getProperty("java.io.tmpdir") + "/" + fileName + ".txt");
		try {
			// The following line fails with a 403 permissions scoping error
			File file = service.files().create(fileMetadata, content).setFields("id").execute();
			return file;
		} catch (Exception e) {
			System.out.println(e + " " + e.getMessage());
			throw e;
		}
	}

	private static String getFileId(String filename, Drive service) throws IOException, GeneralSecurityException {

		// Set page size to 100 - not sure on best practise here
		FileList result = service.files().list().setPageSize(100).setFields("nextPageToken, files(id, name)").execute();
		List<File> files = result.getFiles();
		if (files == null || files.isEmpty()) {
			throw new FileNotFoundException("file list empty or null");
		} else {
			for (File file : files) {
				if (file.getName().equals(filename))
					return file.getId();
			}
		}
		throw new FileNotFoundException("No Found");
	}

	public static String downloadFileContents(String filename) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		// Need to get the file ID before we can download the file.
		String fileId = getFileId(filename, service);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		service.files().get(fileId).executeMediaAndDownloadTo(outputStream);
		return outputStream.toString();
	}
}
