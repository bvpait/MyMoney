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

import ciccio.mymoney.access.BeneficiarioDao;
import ciccio.mymoney.bean.Beneficiario;

@Path("beneficiario")
public class BeneficiarioResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
    	BeneficiarioDao dao = new BeneficiarioDao();
    	List<Beneficiario> lista = new ArrayList<Beneficiario>();

    	try {
    		lista = dao.getAll();
    	} catch (Exception e) {
    		System.err.println(e);
    		return Response.status(Status.SERVICE_UNAVAILABLE).build();
    	}
    	return Response.ok(lista).build();
    }
    
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Beneficiario newValue) {
    	BeneficiarioDao dao = new BeneficiarioDao();
    	
    	try {
			dao.insert(newValue);
			return Response.status(Status.OK).build();
		} catch (IOException e) {
			System.err.println(e);
			return Response.status(Status.BAD_REQUEST).build();
		}
    }
    
}
