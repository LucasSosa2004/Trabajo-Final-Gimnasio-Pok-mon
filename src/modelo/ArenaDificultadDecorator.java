package modelo;

import java.io.Serializable;

public abstract class ArenaDificultadDecorator extends ArenaLogica implements Serializable {
	private static final long serialVersionUID = 1L;
    protected ArenaLogica base;

    public ArenaDificultadDecorator(ArenaLogica base) {
        this.base = base;
    }
}