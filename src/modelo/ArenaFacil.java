package modelo;

import java.io.Serializable;

public class ArenaFacil extends ArenaDificultadDecorator implements Serializable {
	private static final long serialVersionUID = 1L;
    public ArenaFacil(ArenaLogica base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return base.getNombre() + " (Fácil)";
    }

    @Override
    public String getDetalle() {
        return base.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(base.getPremio() * 0.9);
    }
}
