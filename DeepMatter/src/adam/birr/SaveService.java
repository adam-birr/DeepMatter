package adam.birr;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/save")
public class SaveService {

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/")
	public Response save(@FormParam(value = "filename") String filename, String contents) {
		String[] words = contents.split("[\\r\\n]+");
		return Response.status(200).entity(words).contentsbuild();
	}
}
