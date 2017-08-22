package ciccio.mymoney.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ciccio.mymoney.access.ContoDao;
import ciccio.mymoney.bean.Conto;

@Path("conto")
public class ContoResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
	ContoDao dao = new ContoDao();
	List<Conto> lista = new ArrayList<Conto>();

	try {
	    lista = dao.getAll();
	} catch (Exception e) {
	    System.err.println(e);
	    return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	return Response.ok(lista).build();
    }
    
    @GET
    @Path("check/{nome}")
    public Response check(@PathParam("nome") String nome) {
	ContoDao dao = new ContoDao();

	try {
	    boolean checkValue = dao.check(nome);
	    return Response.status(checkValue ? Status.OK : Status.NO_CONTENT).build();
	} catch (Exception e) {
	    System.err.println(e);
	    return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Conto newValue) {
	ContoDao dao = new ContoDao();

	try {
	    dao.insert(newValue);
	    return Response.status(Status.OK).build();
	} catch (IOException e) {
	    System.err.println(e);
	    return Response.status(Status.BAD_REQUEST).build();
	}
    }
    
}
