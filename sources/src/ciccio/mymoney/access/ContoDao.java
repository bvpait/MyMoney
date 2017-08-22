package ciccio.mymoney.access;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.Conto;

public class ContoDao {
    
    public List<Conto> getAll() throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    List<Conto> elenco = session.selectList("Conto.selectAll");
	    return elenco;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public boolean check(String nome) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    int result = session.selectOne("Conto.check", nome.toUpperCase());
	    return result == 1;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    
    public boolean insert(Conto c) throws IOException {
	SqlSession session = null;
	try {
	    session = SessionFactory.getSessionFactory().openSession();
	    int result = session.insert("Conto.insert", c);
	    
	    session.commit();
	    return result == 1;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
}
