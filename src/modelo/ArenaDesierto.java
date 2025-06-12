package modelo;

import java.io.Serializable;
import interfaces.IArena;

public class ArenaDesierto implements Serializable, IArena {
    private static final long serialVersionUID = 1L;

    @Override
    public String getNombre() {
        return "Desierto"; 
    }
    @Override
    public String getDetalle() {
        return "Arena de Desierto"; 
    }
    @Override
    public int getPremio() {
        return 1000; 
    }
}