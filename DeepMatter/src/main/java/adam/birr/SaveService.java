package adam.birr;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;

@Path("/")
public class SaveService {

	@POST
	@Path("save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam(value = "filename") String filename, String contents) {
		String[] words = contents.split("[\\r\\n]+");
		List<String> wordsArrayList = Arrays.asList(words);
		wordsArrayList.sort(String::compareToIgnoreCase);
		return Response.status(200).entity(wordsArrayList).build();
	}
	
	@GET
	@Path("hello") 
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
	    return "Hello";
	 }
	
}
