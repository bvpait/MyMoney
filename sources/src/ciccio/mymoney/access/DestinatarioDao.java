package ciccio.mymoney.access;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.Destinatario;

public class DestinatarioDao {

	public List<Destinatario> getAll() throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			List<Destinatario> elenco = session.selectList("Destinatario.selectAll");
			return elenco;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public boolean insert(Destinatario d) throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			int result = session.insert("Destinatario.insert", d);
			session.commit();
			return result == 1;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
