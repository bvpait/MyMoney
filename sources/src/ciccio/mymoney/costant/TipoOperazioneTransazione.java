package ciccio.mymoney.costant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoOperazioneTransazione {
    NONE (0),
    ADDED (1),
    CHANGED (2),
    REMOVED (3);

    private int value;

    private TipoOperazioneTransazione(int value) {
	this.value = value;
    }

    public int getValue() {
	return value;
    }
    
    @JsonCreator
    public static TipoOperazioneTransazione create(int value) {
	for (TipoOperazioneTransazione tipo: TipoOperazioneTransazione.values()) {
	    if (tipo.value == value)
		return tipo;
	}
	return NONE;
    }
    
    public String getDbOperation() {
	String dbOperation = "";
	switch (this) {
	case ADDED:
	    dbOperation = "insert";
	    break;
	case CHANGED:
	    dbOperation = "update";
	    break;
	case REMOVED:
	    dbOperation = "remove";
	    break;
	default:
	    dbOperation = "";;
	}
	
	return dbOperation;
    }
}
