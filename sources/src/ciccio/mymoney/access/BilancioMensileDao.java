package ciccio.mymoney.access;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.BilancioMensile;
import ciccio.mymoney.bean.Transazione;

public class BilancioMensileDao {
    
    public boolean check(BilancioMensile bm) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    int result = session.selectOne("BilancioMensile.check", bm);
	    return result == 1;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public boolean deleteCategoria(int anno, int mese, int idCategoria) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("anno", anno);
	    params.put("mese", mese);
	    params.put("idCategoria", idCategoria);
	    
	    int result = session.insert("VoceSpesa.deleteCategoria", params);
	    session.commit();
	    return result == 1;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public boolean insert(BilancioMensile bm, boolean isOld) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    session.insert("BilancioMensile." + (isOld ? "update" : "insert"), bm);
	    
	    session.commit();
	    return true;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public List<Transazione> getDettaglio(BilancioMensile bm) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    List<Transazione> listaDettaglio = session.selectList("BilancioMensile.getDettaglio", bm);
	    
	    return listaDettaglio;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
}
