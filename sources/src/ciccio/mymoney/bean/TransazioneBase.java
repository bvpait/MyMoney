package ciccio.mymoney.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ciccio.mymoney.costant.TipoTransazione;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransazioneBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int idConto;
	private String nomeConto;
	private int idCategoria;
	private String nomeCategoria;
	private int idSottoCategoria;
	private String nomeSottoCategoria;
	private int idBeneficiario;
	private String nomeBeneficiario;
	private float importo;
	private TipoTransazione tipo;
	private String note;
	private int idTag;
	private boolean active;

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
	public String getNomeConto() {
		return nomeConto;
	}
	public void setNomeConto(String nomeConto) {
		this.nomeConto = nomeConto;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public int getIdSottoCategoria() {
		return idSottoCategoria;
	}
	public void setIdSottoCategoria(int idSottoCategoria) {
		this.idSottoCategoria = idSottoCategoria;
	}
	public String getNomeSottoCategoria() {
		return nomeSottoCategoria;
	}
	public void setNomeSottoCategoria(String nomeSottoCategoria) {
		this.nomeSottoCategoria = nomeSottoCategoria;
	}
	public int getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdBeneficiario(int idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}
	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	public TipoTransazione getTipo() {
		return tipo;
	}
	public void setTipo(TipoTransazione tipo) {
		this.tipo = tipo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getIdTag() {
		return idTag;
	}
	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}
