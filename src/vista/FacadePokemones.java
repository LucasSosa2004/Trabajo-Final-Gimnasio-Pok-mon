package vista;

import entrenador.Entrenador;
import excepciones.*;
import interfaces.IFacadePokemones;
import modelo.*;

/**
 * Implementacion del patron Facade para simplificar el acceso a las funcionalidades del sistema.
 */
public class FacadePokemones implements IFacadePokemones {
    private static FacadePokemones instancia = null;
    private Gimnasio gimnasio;
    private SistemaPelea sistemaPelea;

    private FacadePokemones() {
        gimnasio = Gimnasio.getInstancia();
        sistemaPelea = SistemaPelea.getInstancia();
        sistemaPelea.inicializarArenas(3);
    }

    public static FacadePokemones getInstancia() {
        if (instancia == null) {
            instancia = new FacadePokemones();
        }
        return instancia;
    }

    @Override
    public void comprarPokemon(String entrenador, String tipo, String nombre) throws Exception {
        gimnasio.getTienda().compraPokemon(
            gimnasio.getEntrenador(entrenador),
            tipo,
            nombre
        );
    }

    @Override
    public void comprarArma(String entrenador, String tipo, String nombrePokemon) throws Exception {
        gimnasio.getTienda().comprarArma(
            tipo,
            gimnasio.getEntrenador(entrenador),
            nombrePokemon
        );
    }

    @Override
    public void agregarAEquipoActivo(String entrenador, String nombrePokemon) throws Exception {
        gimnasio.getEntrenador(entrenador).agregarAEquipoActivo(nombrePokemon);
    }

    @Override
    public void iniciarDuelo(String entrenador1, String entrenador2) throws Exception {
        Entrenador e1 = gimnasio.getEntrenador(entrenador1);
        Entrenador e2 = gimnasio.getEntrenador(entrenador2);
        ArenaFisica arena = sistemaPelea.asignarArenaLibre();
        
        try {
            Duelo duelo = gimnasio.crearDuelo(entrenador1, entrenador2, arena);
            if (duelo != null) {
                sistemaPelea.addDuelo(duelo);
                duelo.iniciarDuelo();
            }
        } finally {
            sistemaPelea.liberarArena(arena);
        }
    }
} 