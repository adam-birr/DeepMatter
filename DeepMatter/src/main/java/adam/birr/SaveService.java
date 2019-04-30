package adam.birr;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import adam.birr.storage.IStoreService;
import adam.birr.storage.StoreService;

@Path("/")
public class SaveService {

	@POST
	@Path("save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@QueryParam(value = "filename") String filename, String contents) {
		int httpReturnCode = HttpStatus.SC_OK;
		
		// Ideally this would have been injected using dependency injection, time I am putting in the hard dependency
		IStoreService storeService = new StoreService();
		try {
			System.out.println("request recieved");
			storeService.processAndStoreFile(filename, contents);
		} catch (Exception exception) {
			httpReturnCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
		} 
		return Response.status(httpReturnCode).build();
	}
	
}
