package ciccio.mymoney.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ciccio.mymoney.access.CategoriaDao;
import ciccio.mymoney.bean.Categoria;

@Path("hello")
public class HelloResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
	Categoria c = new Categoria();
	c.setId(1);
	c.setNome("Alimentari");
	
	return c.toString();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfigurazione(@PathParam("id") int id) {
	CategoriaDao dao = new CategoriaDao();
	List<Categoria> lista = new ArrayList<Categoria>();
	
	try {
	    lista = dao.getListaCategorie();
	} catch (Exception e) {
	    System.err.println(e);
	    return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	return Response.ok(lista).build();
    }
}
