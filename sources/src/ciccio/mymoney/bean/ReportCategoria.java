package ciccio.mymoney.bean;

import java.io.Serializable;

public class ReportCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
     
	private int id;
	private int idConto;
	private int idCategoria;
	private int anno;
	private int mese; 
	private float entrate;
	private float uscite;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdConto() {
		return idConto;
	}
	public void setIdConto(int idConto) {
		this.idConto = idConto;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
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
	
}
