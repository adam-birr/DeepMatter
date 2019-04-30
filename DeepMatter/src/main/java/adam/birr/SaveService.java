package adam.birr;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import adam.birr.storage.IStoreService;
import adam.birr.storage.StoreService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;

@Path("/")
public class SaveService {

	@POST
	@Path("save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam(value = "filename") String filename, String contents) {
		int httpReturnCode = 200;
		
		// Ideally this would have been injected using dependency injection, time I am putting in the hard dependency
		IStoreService storeService = new StoreService();
		try {
			storeService.processAndStoreFile(filename, contents);
		} catch (Exception exception) {
			httpReturnCode = 500;
		} 
		return Response.status(httpReturnCode).build();
	}
	
	@GET
	@Path("hello") 
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
	    return "Hello";
	 }
	
}
