package modelo;

import java.io.Serializable;
import interfaces.IArena;

public class ArenaSelva implements Serializable, IArena {
    private static final long serialVersionUID = 1L;

    @Override
    public String getNombre() {
        return "Selva"; 
    }
    @Override
    public String getDetalle() {
        return "Arena de Selva"; 
    }
    @Override
    public int getPremio() {
        return 800; 
    }
}