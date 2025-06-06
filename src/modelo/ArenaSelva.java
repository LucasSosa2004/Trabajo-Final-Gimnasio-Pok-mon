package modelo;

import java.io.Serializable;

public class ArenaSelva extends ArenaLogica implements Serializable {
	private static final long serialVersionUID = 1L;
    public String getNombre() {
    	return "Selva"; 
    }
    public String getDetalle() {
    	return "Arena de Selva"; 
    }
    public int getPremio() {
    	return 800; 
    }
} 
