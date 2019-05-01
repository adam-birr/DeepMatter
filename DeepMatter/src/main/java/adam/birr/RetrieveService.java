package adam.birr;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import adam.birr.storage.IStoreService;
import adam.birr.storage.StoreService;

@Path("/")
public class RetrieveService {

	@GET
	@Path("retrieve")
	public Response retrieve(@QueryParam(value = "filename") String filename,
			@QueryParam(value = "begin") String beginString, @QueryParam(value = "end") String endString) {

		// Ideally this would have been injected using dependency injection, time I am
		// putting in the hard dependency
		IStoreService storeService = new StoreService();
		
		int httpReturnCode = HttpStatus.SC_OK;
		int end;
		int begin;
		List<String> result = null;

		if (filename == null) {
			httpReturnCode = HttpStatus.SC_BAD_REQUEST;
		} else {
			try {
				begin = Integer.parseInt(beginString);
				try {
					end = Integer.parseInt(endString);
					try {
						result = storeService.retrieveFileAndProcess(filename, begin, end);
					} catch (Exception processException1) {
						httpReturnCode = HttpStatus.SC_BAD_REQUEST;
					}
				} catch (Exception endIndexParseException) {
					try {
						result = storeService.retrieveFileAndProcess(filename, begin);
					} catch (Exception processException2) {
						httpReturnCode = HttpStatus.SC_BAD_REQUEST;
					}
				}
			} catch (Exception beginIndexParseException) {
				try {
					result = storeService.retrieveFileAndProcess(filename);
				} catch (Exception processException3) {
					httpReturnCode = HttpStatus.SC_BAD_REQUEST;
				}
			}
		}
		System.out.println("result:\n"+result.toString());
		return Response.status(httpReturnCode).entity(result).build();
	}
}
