package modelo;

import java.io.Serializable;

public class ArenaMedio extends ArenaDificultadDecorator implements Serializable {
	private static final long serialVersionUID = 1L;
    public ArenaMedio(ArenaLogica base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return base.getNombre() + " (Medio)";
    }

    @Override
    public String getDetalle() {
        return base.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(base.getPremio() * 1.2);
    }
}
