package modelo;

import java.io.Serializable;

public class ArenaDesierto extends ArenaLogica implements Serializable {
	private static final long serialVersionUID = 1L;
    public String getNombre() {
    	return "Desierto"; 
    }
    public String getDetalle() {
    	return "Arena de Desierto"; 
    }
    public int getPremio() {
    	return 1000; 
    }
} 