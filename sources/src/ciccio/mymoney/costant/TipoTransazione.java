package ciccio.mymoney.costant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoTransazione {
    USCITA (1),
    ENTRATA (2),
    TRASF_USCITA (3),
    TRASF_ENTRATA (4);

    private int value;

    private TipoTransazione(int value) {
	this.value = value;
    }

    public int getValue() {
	return value;
    }
    
    @JsonCreator
    public static TipoTransazione create(int value) {
	for (TipoTransazione tipo: TipoTransazione.values()) {
	    if (tipo.value == value)
		return tipo;
	}
	return USCITA;
    }
}
