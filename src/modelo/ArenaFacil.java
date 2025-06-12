package modelo;

import java.io.Serializable;
import interfaces.IArena;

public class ArenaFacil extends ArenaDificultadDecorator implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArenaFacil(IArena base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return super.getNombre() + " (Fácil)";
    }

    @Override
    public String getDetalle() {
        return super.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(super.getPremio() * 0.9);
    }
}