package modelo;

import java.io.Serializable;
import interfaces.IArena;

public abstract class ArenaDificultadDecorator implements Serializable, IArena {
    private static final long serialVersionUID = 1L;
    protected IArena arenaBase;

    public ArenaDificultadDecorator(IArena arenaBase) { 
        this.arenaBase = arenaBase;
    }

    @Override
    public String getNombre() {
        return arenaBase.getNombre();
    }

    @Override
    public String getDetalle() {
        return arenaBase.getDetalle();
    }

    @Override
    public int getPremio() {
        return arenaBase.getPremio();
    }
}