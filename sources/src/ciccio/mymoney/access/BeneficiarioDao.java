package ciccio.mymoney.access;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.Beneficiario;

public class BeneficiarioDao {

	public List<Beneficiario> getAll() throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			List<Beneficiario> elenco = session.selectList("Beneficiario.selectAll");
			return elenco;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public boolean insert(Beneficiario b) throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			int result = session.insert("Beneficiario.insert", b);
			session.commit();
			return result == 1;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
