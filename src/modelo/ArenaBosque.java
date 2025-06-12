package modelo;

import java.io.Serializable;
import interfaces.IArena;

public class ArenaBosque implements Serializable, IArena {
    private static final long serialVersionUID = 1L;

    @Override
    public String getNombre() {
        return "Bosque"; 
    }
    @Override
    public String getDetalle() {
        return "Arena de Bosque"; 
    }
    @Override
    public int getPremio() {
        return 700; 
    }
}