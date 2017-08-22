package ciccio.mymoney.access;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import ciccio.mymoney.bean.BilancioMensile;
import ciccio.mymoney.bean.Transazione;

public class TransazioneDao {
	
    public boolean insert(Transazione t, SqlSession session) throws IOException {
	System.out.println("Operazione: Transazione." + t.getTipoOperazione().getDbOperation());
	int result = session.insert("Transazione." + t.getTipoOperazione().getDbOperation(), t);
	return result == 1;
    }
    
    public void removeTransfers(BilancioMensile bm, SqlSession session) throws IOException {
	session.insert("Transazione.removeTransfers", bm);
    }
	
    public boolean remove(BilancioMensile bm, SqlSession session) throws IOException {
	int result = session.insert("Transazione.remove", bm);
	return result > 0;
    }
	
}
