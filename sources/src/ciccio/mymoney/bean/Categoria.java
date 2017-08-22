package ciccio.mymoney.bean;

import java.io.Serializable;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nome;
    private int idPadre;
    private float entrate;
    private float uscite;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdPadre() {
        return idPadre;
    }
    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
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
	
	@Override
    public String toString() {
    	return String.format("Categoria [id=%s, nome=%s, idPadre=%s]", id, nome, idPadre);
    }
    
}
