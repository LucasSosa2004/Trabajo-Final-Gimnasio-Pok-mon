package modelo;

import java.io.Serializable;

public class ArenaBosque extends ArenaLogica implements Serializable {
	private static final long serialVersionUID = 1L;
    public String getNombre() {
    	return "Bosque"; 
    }
    public String getDetalle() {
    	return "Arena de Bosque"; 
    }
    public int getPremio() {
    	return 700; 
    }
} 