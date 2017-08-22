package ciccio.mymoney.access;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.TransazioneBase;

public class MensileDefaultDao {
    
    public List<TransazioneBase> getAll(int idConto) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    List<TransazioneBase> result = session.selectList("MensileDefault.getAll", idConto);
	    return result;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public List<TransazioneBase> get(int idConto) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    List<TransazioneBase> result = session.selectList("MensileDefault.get", idConto);
	    return result;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public boolean check(int idConto) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    int result = session.selectOne("MensileDefault.check");
	    return result > 0;
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
    
    public boolean insert(TransazioneBase t, boolean isOld) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    
	    session.insert("MensileDefault." + (isOld ? (t.isActive() ? "update" : "remove") : "insert"), t);
	    
	    session.commit();
	    return true;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
}
