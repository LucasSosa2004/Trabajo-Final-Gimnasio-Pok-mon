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
        this.duelos = new ArrayList<>();
        this.dueloObserver = new DueloObserver(vista.getTextAreaDuelo());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        try {
            switch (comando) {
                case "ENTRENADOR":
                    vista.mostrarPanel("AGREGAR_ENTRENADOR");
                    break;
                case "POKEMON":
                    vista.mostrarPanel("AGREGAR_POKEMON");
                    break;
                case "TIENDA":
                    vista.mostrarPanel("TIENDA");
                    break;
                case "ARENA":
                    vista.mostrarPanel("ARENA");
                    break;
                case "DUELO":
                    vista.mostrarPanel("DUELO");
                    break;
                case "CREAR_ENTRENADOR":
                    CREAR_ENTRENADOR();
                    break;
                case "CREAR_POKEMON":
                    CREAR_POKEMON();
                    break;
                case "AGREGAR_A_EQUIPO":
                    AGREGAR_A_EQUIPO();
                    break;
                case "RECARGAR":
                    RECARGAR();
                    break;
                case "COMPRAR_POKEMON":
                    COMPRAR_POKEMON();
                    break;
                case "COMPRAR_ARMA":
                    COMPRAR_ARMA();
                    break;
                case "AGREGAR_E1":
                    AGREGAR_E1();
                    break;
                case "AGREGAR_E2":
                    AGREGAR_E2();
                    break;
                case "AGREGAR_DUELO":
                    AGREGAR_DUELO();
                    break;
                case "INICIAR_TORNEO":
                    INICIAR_TORNEO();
                    break;
                case "LISTA_ENTRENADORES":
                    LISTA_ENTRENADORES();
                    break;
                case "LISTA_DUELOS":
                    LISTA_DUELOS();
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private void COMPRAR_POKEMON() {
        String tipoPokemon = vista.getTipoPokemonTienda();
        String nombrePokemon = vista.getNombrePokemonTienda();
        Entrenador entrenador = vista.getEntrenador();

        try {
            PokemonFactory factory = new PokemonFactory();
            Pokemon p = factory.getPokemon(tipoPokemon, nombrePokemon);
            gimnasio.comprarPokemon(entrenador, p);
            modeloComboPokemones.addElement(nombrePokemon);
            vista.actualizarComboListaPokemones(modeloComboPokemones);
            vista.vaciarTextPokemonTienda();
            vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error compra Pokémon", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void COMPRAR_ARMA() {
        String tipoArma = vista.getTipoArmaTienda();
        String nombrePokemon = vista.getNombrePokemonComboTienda();
        Entrenador entrenador = vista.getEntrenador();

        try {
            ArmaFactory factory = new ArmaFactory();
            Arma a = factory.getArma(tipoArma);
            Pokemon p = entrenador.buscaPokemon(nombrePokemon);
            gimnasio.comprarArma(entrenador, p, a);
            vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error compra arma", JOptionPane.ERROR_MESSAGE);
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

    private void AGREGAR_A_EQUIPO() {
        String nombrePokemon = vista.getNombrePokemonComboPokemon();
        Entrenador entrenador = vista.getEntrenador();
        try {
            Pokemon p = entrenador.buscaPokemon(nombrePokemon);
            entrenador.agregarAEquipoActivo(p);
            vista.apagarBotonesPokemonCreado();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al agregar al equipo", JOptionPane.ERROR_MESSAGE);
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
