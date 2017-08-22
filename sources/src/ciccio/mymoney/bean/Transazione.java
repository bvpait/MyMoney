package ciccio.mymoney.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ciccio.mymoney.costant.TipoOperazioneTransazione;
import ciccio.mymoney.costant.TipoTransazione;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transazione implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private TipoOperazioneTransazione tipoOperazione;
    private int idConto;
    private String nomeConto;
    private int anno;
    private int mese;
    private int idContoTrasferimento;
    private int idCategoria;
    private String nomeCategoria;
    private int idSottoCategoria;
    private String nomeSottoCategoria;
    private int idBeneficiario;
    private String nomeBeneficiario;
    private String exp;
    private float importo;
    private TipoTransazione tipo;
    private String nota;
    private int idTag;

    public int getId() {
	return id;
    }
    public void setId(int id) {
	this.id = id;
    }
    public TipoOperazioneTransazione getTipoOperazione() {
        return tipoOperazione;
    }
    public void setTipoOperazione(TipoOperazioneTransazione tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
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
    public int getIdContoTrasferimento() {
        return idContoTrasferimento;
    }
    public void setIdContoTrasferimento(int idContoTrasferimento) {
        this.idContoTrasferimento = idContoTrasferimento;
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
    public String getExp() {
	return exp;
    }
    public void setExp(String exp) {
	this.exp = exp;
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
    public String getNota() {
	return nota;
    }
    public void setNota(String nota) {
	this.nota = nota;
    }
    public int getIdTag() {
	return idTag;
    }
    public void setIdTag(int idTag) {
	this.idTag = idTag;
    }

    public void setChiave(BilancioMensile bm) {
	this.setIdConto(bm.getIdConto());
	this.setAnno(bm.getAnno());
	this.setMese(bm.getMese());
    }
    
    public void creaTransazioneOpposta(Transazione t) {
	this.idConto = t.getIdContoTrasferimento();
	this.anno = t.getAnno();
	this.mese = t.getMese();
	this.idContoTrasferimento = t.getIdConto();
	this.exp = t.getExp();
	this.importo = t.getImporto();
	this.tipo = t.getTipo() == TipoTransazione.TRASF_ENTRATA ? TipoTransazione.TRASF_USCITA : TipoTransazione.TRASF_ENTRATA;
	this.nota = t.getNota();
	this.idTag = t.getIdTag();
    }

}
