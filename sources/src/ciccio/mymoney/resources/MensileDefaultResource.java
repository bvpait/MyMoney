package ciccio.mymoney.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ciccio.mymoney.access.BilancioMensileDao;
import ciccio.mymoney.access.MensileDefaultDao;
import ciccio.mymoney.bean.BilancioMensile;
import ciccio.mymoney.bean.TransazioneBase;

@Path("mensile_default")
public class MensileDefaultResource {
    
	@GET
	@Path("{idConto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@PathParam("idConto") int idConto) {
		MensileDefaultDao dao = new MensileDefaultDao();
		List<TransazioneBase> lista = new ArrayList<TransazioneBase>();

		try {
			lista = dao.get(idConto);
		} catch (Exception e) {
			System.err.println(e);
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.ok(lista).build();
	}
	
	@GET
	@Path("all/{idConto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("idConto") int idConto) {
		MensileDefaultDao dao = new MensileDefaultDao();
		List<TransazioneBase> lista = new ArrayList<TransazioneBase>();

		try {
			lista = dao.getAll(idConto);
		} catch (Exception e) {
			System.err.println(e);
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.ok(lista).build();
	}
    
    @POST
    @Path("check")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response check(BilancioMensile bm) {
	BilancioMensileDao dao = new BilancioMensileDao();

	try {
	    boolean checkValue = dao.check(bm);
	    return Response.status(checkValue ? Status.OK : Status.NO_CONTENT).build();
	} catch (IOException e) {
	    System.err.println(e);
	    return Response.status(Status.BAD_REQUEST).build();
	}
    }

    @POST
    @Path("dettaglio")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDettaglio(BilancioMensile bm) {
	BilancioMensileDao dao = new BilancioMensileDao();

	try {
	    boolean isBilancioPresente = dao.check(bm);
	    return Response.status(isBilancioPresente ? Status.OK : Status.NO_CONTENT).build();
	} catch (IOException e) {
	    System.err.println(e);
	    return Response.status(Status.BAD_REQUEST).build();
	}
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(List<TransazioneBase> lista) {
    	MensileDefaultDao dao = new MensileDefaultDao();

    	try {
    		// inserisce/aggiorna le transazioni di default
    		for (TransazioneBase t: lista) {
    			dao.insert(t, t.getId() > 0);
    		}

    		return Response.status(Status.OK).build();
    	} catch (IOException e) {
    		System.err.println(e);
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    }
    
}
