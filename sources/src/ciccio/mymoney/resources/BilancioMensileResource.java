package ciccio.mymoney.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.access.BilancioMensileDao;
import ciccio.mymoney.access.CategoriaDao;
import ciccio.mymoney.access.SessionFactory;
import ciccio.mymoney.access.TransazioneDao;
import ciccio.mymoney.bean.BilancioMensile;
import ciccio.mymoney.bean.Categoria;
import ciccio.mymoney.bean.Transazione;
import ciccio.mymoney.costant.TipoOperazioneTransazione;

@Path("bilancio_mensile")
public class BilancioMensileResource {
    
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDettaglio(BilancioMensile bm) {
	BilancioMensileDao dao = new BilancioMensileDao();
	List<Transazione> lista = new ArrayList<Transazione>();
	
	try {
	    boolean isBilancioPresente = dao.check(bm);
	    if (isBilancioPresente) {
		lista = dao.getDettaglio(bm);
		return Response.ok(lista).build();
	    } else {
		return Response.status(Status.NO_CONTENT).build();
	    }
	} catch (IOException e) {
	    System.err.println(e);
	    return Response.status(Status.BAD_REQUEST).build();
	}
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(BilancioMensile bm) {
	TransazioneDao dao = new TransazioneDao();
	SqlSession session = null;
	
	try {
	    // apri sessione
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    // elimina i trasferimenti
	    if ((new BilancioMensileDao()).check(bm))
		dao.removeTransfers(bm, session);
	    
	    // gestione delle transazioni
	    List<Transazione> lista = bm.getListaTransazioni();
	    for (Transazione t: lista) {
		if (t.getId() == 0 || t.getTipoOperazione() != TipoOperazioneTransazione.NONE) {
		    t.setChiave(bm);
		    
		    dao.insert(t, session);
		    
		    // trasferimento - aggiunge controparte (entrata <-> uscita) sull'altro conto
		    if (t.getIdContoTrasferimento() > 0) {
    		    	Transazione trasfOpposta = new Transazione();
    		    	trasfOpposta.creaTransazioneOpposta(t);
    		    	dao.insert(trasfOpposta, session);
		    }
		} else {
		    System.out.println("Skip transazione ID: " + t.getId());
		}
	    }
	    
	    // salva i dati
	    session.commit();
	    
	    return Response.status(Status.OK).build();
	} catch (IOException e) {
	    System.err.println(e);
	    return Response.status(Status.BAD_REQUEST).build();
	} finally {
	    session.close();
	}
    }
    
}
