package ciccio.mymoney.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BilancioMensile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int idConto;
	private int anno;
	private int mese;
	private float entrate = 0;
	private float uscite = 0;
	private List<Transazione> listaTransazioni;
	
	public int getIdConto() {
		return idConto;
	}
	public void setIdConto(int idConto) {
		this.idConto = idConto;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	public float getEntrate() {
		return entrate;
	}
	public void setEntrate(float entrate) {
		this.entrate = entrate;
	}
	public float getUscite() {
		return uscite;
	}
	public void setUscite(float uscite) {
		this.uscite = uscite;
	}
	public List<Transazione> getListaTransazioni() {
		return listaTransazioni;
	}
	public void setListaTransazioni(List<Transazione> listaTransazioni) {
		this.listaTransazioni = listaTransazioni;
	}
	
	// custom
	public void aggiungiEntrata(float entrata) {
		this.entrate += entrata;
	}
	public void aggiungiUscita(float uscita) {
		this.uscite += uscita;
	}
	
}
