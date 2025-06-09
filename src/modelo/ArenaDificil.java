package modelo;

import java.io.Serializable;

public class ArenaDificil extends ArenaDificultadDecorator implements Serializable {
	private static final long serialVersionUID = 1L;
    public ArenaDificil(ArenaLogica base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return base.getNombre() + " (Difícil)";
    }

    @Override
    public String getDetalle() {
        return base.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(base.getPremio() * 1.5);
    }
}
