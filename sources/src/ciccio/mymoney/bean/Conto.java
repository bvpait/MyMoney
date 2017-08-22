package ciccio.mymoney.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Conto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nome;
    private float entrate = 0;
    private float uscite = 0;
    private float trasfEntrate = 0;
    private float trasfUscite = 0;
    
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
    public float getTrasfEntrate() {
        return trasfEntrate;
    }
    public void setTrasfEntrate(float trasfEntrate) {
        this.trasfEntrate = trasfEntrate;
    }
    public float getTrasfUscite() {
        return trasfUscite;
    }
    public void setTrasfUscite(float trasfUscite) {
        this.trasfUscite = trasfUscite;
    }
    
}
