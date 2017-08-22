package ciccio.mymoney.access;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.Categoria;

public class CategoriaDao {

	public List<Categoria> getListaCategorie1() throws IOException {
		List<Categoria> elenco = new ArrayList<Categoria>();
		for (int i = 0; i < 8; i++) {
			Categoria c = new Categoria();
			c.setId(i+1);
			c.setNome("Cat " + (i+1));
			if (i > 2) {
				c.setIdPadre((i % 2) == 0 ? 1 : 2);
			} else {
				c.setIdPadre(0);
			}
			
			elenco.add(c);
		}
		
		return elenco;
	}
	
	public List<Categoria> getListaCategorie() throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			List<Categoria> elenco = session.selectList("Categoria.selectAll");
			return elenco;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public boolean insert(Categoria c) throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			// inserimento categoria / sottoCategoria
			int result = session.insert("Categoria.insert", c);
			
			// insieme a una categoria principale aggiungiamo una sottoCategoria generica
			if (c.getIdPadre() == 0) {
				Categoria sottoCategoriaGenerica = new Categoria();
				sottoCategoriaGenerica.setNome("*Non Classificato");
				sottoCategoriaGenerica.setIdPadre(c.getId());
				session.insert("Categoria.insert", sottoCategoriaGenerica);
			}
			session.commit();
			return result == 1;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public int getID(Categoria c) throws IOException {
		SqlSession session = null;
		try {
			session = SessionFactory.getSessionFactory().openSession();
			int result = session.selectOne("Categoria.getID", c);
			return result;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public static void main(String[] args) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			CategoriaDao dao = new CategoriaDao();
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\temp\\Categories.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] row = sCurrentLine.split(":");
				
				Categoria c = new Categoria();
				c.setNome(row[0]);
				c.setIdPadre(0);
				int id = dao.getID(c);
				
				if (row.length > 1) {
					c.setNome(row[1]);
					c.setIdPadre(id);
					if (dao.getID(c) == 0) {
						dao.insert(c);
						//System.out.println("Inserisci il figlio: " + c.getNome());
					} else {
						System.out.println("Figlio inserito: " + c.getNome());
					}
				} else {
					if (id == 0) {
						System.out.println("Inserisci il padre: " + c.getNome());
						//dao.insert(c);
					} else {
						System.out.println("Padre inserito: " + c.getNome());
					}

				}
				
				//System.out.println(String.format("%s - %s", row[0], (row.length > 1 ? row[1] : "PADRE")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
