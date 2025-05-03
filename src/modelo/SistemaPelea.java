package modelo;

import java.util.HashMap;

import excepciones.ArenaOcupadaException;

public class SistemaPelea {
    /** Lista de arenas disponibles en el gimnasio */
    private static SistemaPelea instancia;
    private HashMap<Integer, Duelo> duelos = new HashMap<>();


    public static SistemaPelea getInstancia() {
        if (instancia == null) {
            instancia = new SistemaPelea();
        }
        assert instancia != null : "La instancia no puede ser nula";
        return instancia;
    }


    public HashMap<Integer, Duelo> getDuelos() {
        assert this.duelos != null : "El HashMap de duelos no puede ser nulo";
        return this.duelos;
    }

    public Duelo getDuelo(int numArena) {
        assert numArena >= 0 : "El numero de arena debe ser mayor o igual a 0";
        return this.duelos.get(numArena);
    }


    public void removeDuelo(int numArena) {
        assert numArena >= 0 : "El numero de arena debe ser mayor o igual a 0";
        this.duelos.remove(numArena);
        assert !this.duelos.containsKey(numArena) : "El duelo no fue eliminado correctamente";
    }

    /**
     * Agrega un duelo al sistema de peleas.
     * 
     * <b>Pre:</b><br>
     * El duelo no puede ser nulo.<br><br>
     * <b>Post:</b><br>
     * Agrega un duelo al sistema.<br>
     * 
     * @param duelo El duelo a agregar
     * @throws ArenaOcupadaException Si la arena ya esta ocupada
     */
    public void addDuelo(Duelo duelo) throws ArenaOcupadaException {
        assert duelo != null : "El duelo no puede ser nulo";
        if (this.duelos.containsKey(duelo.getClave())) {
            throw new ArenaOcupadaException(duelo.getClave());
        } else {
            this.duelos.put(duelo.getClave(), duelo);
        }
        assert this.duelos.containsKey(duelo.getClave()) : "El duelo no fue agregado correctamente";
    }

    /**
     * Inicia el combate en una arena especifica.
     * 
     * <b>Pre:</b><br>
     * El numero de arena debe corresponder a un duelo existente.<br><br>
     * <b>Post:</b><br>
     * Inicia el combate en la arena especificada.<br>
     * 
     * @param numArena Numero de la arena
     */
    public void iniciarCombate(int numArena) {
        assert this.duelos.containsKey(numArena) : "El numero de arena no corresponde a un duelo existente";
        Duelo duelo = this.getDuelo(numArena);
        duelo.iniciarDuelo();
        duelo.getEntrenador1().vaciarEquipoActivo();
        duelo.getEntrenador2().vaciarEquipoActivo();
        this.removeDuelo(numArena);
        assert !this.duelos.containsKey(numArena) : "El duelo no fue eliminado correctamente";
    }


    @Override
    public String toString() {
        String resultado = "SistemaPelea [duelos=" + duelos + "]";
        assert resultado != null : "El resultado de toString no puede ser nulo";
        return resultado;
    }
}
