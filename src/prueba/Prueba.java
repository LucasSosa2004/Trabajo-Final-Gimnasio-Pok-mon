package prueba;


import controlador.Controlador;
import vista.VentanaPokemones;

/**
 * Clase principal para ejecutar por consola:
 *  Etapa 1: Registro de entrenadores, alta/mejora de Pokémon, creación de arenas.
 *  Etapa 2: Inscripción de hasta 8 entrenadores al torneo.
 *  Etapa 3: Desarrollo de torneo por eliminación directa (cuartos, semis, final).
 *  Persistencia: se guarda/carga el estado (Gimnasio, SistemaPelea y EtapaTorneo).
 *
 *  NO incluye ninguna interfaz gráfica, todo es por consola.
 */
/**
 * 
 */
public class Prueba {
    public static void main(String[] args) {
        Controlador controlador=new Controlador();
        controlador.setVista(new VentanaPokemones());
    }
}
