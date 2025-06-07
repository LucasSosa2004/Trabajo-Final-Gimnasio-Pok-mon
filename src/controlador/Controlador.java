package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import armas.Arma;
import armas.ArmaFactory;
import entrenador.Entrenador;
import excepciones.*;
import modelo.ArenaFisica;
import modelo.Duelo;
import modelo.FacadePokemones;
import modelo.Gimnasio;
import modelo.IFacadePokemones;
import modelo.SistemaPelea;
import pokemones.Pokemon;
import pokemones.PokemonFactory;
import vista.DueloObserver;
import vista.IVista;
import vista.VentanaPokemones;
import interfaces.IFacadePokemones;
import vista.FacadePokemones;

public class Controlador implements ActionListener {

    private IFacadePokemones facade = FacadePokemones.getInstancia();
    private IVista vista;
    private Gimnasio gimnasio;
    private SistemaPelea sistemaPelea;
    private DefaultListModel<Entrenador> modeloListaEntrenadores = new DefaultListModel<>();
    private DefaultListModel<Duelo> modeloListaDuelos = new DefaultListModel<>();
    private DefaultComboBoxModel<String> modeloComboPokemones = new DefaultComboBoxModel<>();
    private List<Duelo> duelos;
    private DueloObserver dueloObserver;

    public IVista getVista() {
        return vista;
    }

    public void setVista(IVista vista) {
        this.vista = vista;
        this.vista.setActionListener(this);
        this.gimnasio = Gimnasio.getInstancia();
        this.sistemaPelea = SistemaPelea.getInstancia();
        this.sistemaPelea.inicializarArenas(3); // Por defecto 3 arenas
        this.duelos = new ArrayList<>();
        this.dueloObserver = new DueloObserver(vista.getTextAreaDuelo());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        try {
            if (comando.equals("COMPRAR_POKEMON")) {
                facade.comprarPokemon(
                    vista.getEntrenadorSeleccionado(),
                    vista.getTipoPokemonSeleccionado(),
                    vista.getNombrePokemon()
                );
                vista.mostrarMensaje("Pokemon comprado exitosamente");
            }
            else if (comando.equals("COMPRAR_ARMA")) {
                facade.comprarArma(
                    vista.getEntrenadorSeleccionado(),
                    vista.getTipoArmaSeleccionado(),
                    vista.getNombrePokemonParaArma()
                );
                vista.mostrarMensaje("Arma comprada exitosamente");
            }
            else if (comando.equals("AGREGAR_A_EQUIPO")) {
                facade.agregarAEquipoActivo(
                    vista.getEntrenadorSeleccionado(),
                    vista.getNombrePokemonParaEquipo()
                );
                vista.mostrarMensaje("Pokemon agregado al equipo activo exitosamente");
            }
            else if (comando.equals("INICIAR_DUELO")) {
                facade.iniciarDuelo(
                    vista.getEntrenador1Seleccionado(),
                    vista.getEntrenador2Seleccionado()
                );
            }
            else if (comando.equals("ENTRENADOR")) {
                vista.mostrarPanel("AGREGAR_ENTRENADOR");
            }
            else if (comando.equals("POKEMON")) {
                vista.mostrarPanel("AGREGAR_POKEMON");
            }
            else if (comando.equals("TIENDA")) {
                vista.mostrarPanel("TIENDA");
            }
            else if (comando.equals("ARENA")) {
                vista.mostrarPanel("ARENA");
            }
            else if (comando.equals("DUELO")) {
                vista.mostrarPanel("DUELO");
            }
            else if (comando.equals("CREAR_ENTRENADOR")) {
                CREAR_ENTRENADOR();
            }
            else if (comando.equals("CREAR_POKEMON")) {
                CREAR_POKEMON();
            }
            else if (comando.equals("RECARGAR")) {
                RECARGAR();
            }
            else if (comando.equals("AGREGAR_E1")) {
                AGREGAR_E1();
            }
            else if (comando.equals("AGREGAR_E2")) {
                AGREGAR_E2();
            }
            else if (comando.equals("AGREGAR_DUELO")) {
                AGREGAR_DUELO();
            }
            else if (comando.equals("INICIAR_TORNEO")) {
                INICIAR_TORNEO();
            }
            else if (comando.equals("LISTA_ENTRENADORES")) {
                LISTA_ENTRENADORES();
            }
            else if (comando.equals("LISTA_DUELOS")) {
                LISTA_DUELOS();
            }
        } catch (Exception ex) {
            vista.mostrarError("Error: " + ex.getMessage());
        }
    }

    private void AGREGAR_E2() {
        vista.encenderBotonAgregarDuelo();
    }

    private void AGREGAR_E1() {
        vista.encenderTextoE2();
        vista.encenderBotonSeleccionarE2();
    }

    private void AGREGAR_DUELO() {
        try {
            String nombreE1 = vista.getEntrenador1();
            String nombreE2 = vista.getEntrenador2();
            
            ArenaFisica arena = sistemaPelea.asignarArenaLibre();
            Duelo duelo = gimnasio.crearDuelo(nombreE1, nombreE2, arena);
            
            if (duelo != null) {
                duelo.addObserver(dueloObserver);
                sistemaPelea.addDuelo(duelo);
                this.duelos.add(duelo);
                modeloListaDuelos.addElement(duelo);
                vista.actualizarListaDuelo(modeloListaDuelos);
                duelo.iniciarDuelo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al crear duelo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void INICIAR_TORNEO() {
        for (Duelo d : duelos) {
            d.iniciarDuelo();
        }
    }

    private void RECARGAR() {
        String nombrePokemon = vista.getNombrePokemonComboPokemon();
        Entrenador entrenador = vista.getEntrenador();
        try {
            Pokemon p = entrenador.buscaPokemon(nombrePokemon);
            p.recargar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error recarga", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CREAR_ENTRENADOR() {
        String nombreEntrenador = vista.getNombreEntrenador();
        int creditos = vista.getCreditosEntrenador();
        try {
            facade.crearEntrenador(nombreEntrenador, creditos);
            modeloListaEntrenadores.addElement(gimnasio.getEntrenador(nombreEntrenador));
            vista.actualizarListaEntrenadores(modeloListaEntrenadores);
            vista.resetZonaEntrenador();

            if (modeloListaEntrenadores.getSize() > 1) {
                vista.encenderBotonDuelo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error creación entrenador", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CREAR_POKEMON() {
        String nombrePokemon = vista.getNombrePokemonCreado();
        String tipoPokemon = vista.getTipoPokemonCreado();
        Entrenador entrenador = vista.getEntrenador();
        try {
            PokemonFactory factory = new PokemonFactory();
            Pokemon p = factory.getPokemon(tipoPokemon, nombrePokemon);
            entrenador.agregarPokemon(p);
            modeloComboPokemones.addElement(nombrePokemon);
            vista.actualizarComboListaPokemones(modeloComboPokemones);
            vista.vaciarTextPokemonCreado();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error creación Pokémon", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void LISTA_ENTRENADORES() {
        Entrenador entrenador = vista.getEntrenador();
        if (entrenador != null) {
            modeloComboPokemones.removeAllElements();
            for (Pokemon p : entrenador.getPokemones().values()) {
                modeloComboPokemones.addElement(p.getNombre());
            }
            vista.actualizarComboListaPokemones(modeloComboPokemones);
        }
    }

    private void LISTA_DUELOS() {
        // Implementar si es necesario
    }
}
