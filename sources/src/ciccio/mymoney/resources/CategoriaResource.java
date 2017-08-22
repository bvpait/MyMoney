package ciccio.mymoney.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ciccio.mymoney.access.CategoriaDao;
import ciccio.mymoney.bean.Categoria;

@Path("categoria")
public class CategoriaResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
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

	@POST
	@Path("insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Categoria newValue) {
		CategoriaDao dao = new CategoriaDao();

		try {
			dao.insert(newValue);
			return Response.status(Status.OK).build();
		} catch (IOException e) {
			System.err.println(e);
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

}
