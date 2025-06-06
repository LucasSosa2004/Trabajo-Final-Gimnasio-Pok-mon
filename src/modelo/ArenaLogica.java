package modelo;

import java.io.Serializable;

public abstract class ArenaLogica implements Serializable {
	private static final long serialVersionUID = 1L;
    public abstract String getNombre();
    public abstract String getDetalle();
    public abstract int getPremio();
}