package modelo;

import java.io.Serializable;
import interfaces.IArena;

public class ArenaMedio extends ArenaDificultadDecorator implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArenaMedio(IArena base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return super.getNombre() + " (Medio)";
    }

    @Override
    public String getDetalle() {
        return super.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(super.getPremio() * 1.2);
    }
}