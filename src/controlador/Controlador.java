package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import entrenador.Entrenador;
import excepciones.CompraImposibleException;
import excepciones.EquipoLlenoException;
import excepciones.NombreUtilizadoException;
import excepciones.PokemonNoExisteException;
import excepciones.PokemonNoPuedeUsarArmaE;
import excepciones.TipoDesconocidoException;
import modelo.Duelo;
import modelo.FacadePokemones;
import pokemones.Pokemon;
import vista.IVista;

public class Controlador implements ActionListener {

    private FacadePokemones facade = FacadePokemones.getInstancia();
    private IVista vista;
    private DefaultListModel<Entrenador> modeloListaEntrenadores = new DefaultListModel<>();
    private DefaultListModel<Duelo> modeloListaDuelos = new DefaultListModel<>();
    private DefaultComboBoxModel<String> modeloComboPokemones = new DefaultComboBoxModel<>();

    public IVista getVista() {
        return vista;
    }

    public void setVista(IVista vista) {
        this.vista = vista;
        this.vista.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
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
            // Iniciar torneo
            break;
        case "LISTA_ENTRENADORES":
            LISTA_ENTRENADORES();
            break;
        case "LISTA_DUELOS":
            LISTA_DUELOS();
            break;
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
        // facade.getSistemaPelea().addDuelo(...);
    }

    private void COMPRAR_ARMA() {
        String tipoArma = vista.getTipoArmaTienda();
        String nombrePokemon = vista.getNombrePokemonComboTienda();
        Entrenador entrenador = vista.getEntrenador();

        try {
            facade.getGimnasio().getTienda().comprarArma(tipoArma, entrenador, nombrePokemon);
            vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
        } catch (PokemonNoPuedeUsarArmaE e) {
            JOptionPane.showMessageDialog(null,
                "El pokémon " + e.getNombre() + " no puede usar ese arma.",
                "Error compra arma", JOptionPane.ERROR_MESSAGE);
        } catch (CompraImposibleException e) {
            JOptionPane.showMessageDialog(null,
                "Créditos insuficientes: tienes " + e.getCreditos() +
                ", necesitas " + e.getCosto() + ".",
                "Error compra arma", JOptionPane.ERROR_MESSAGE);
        } catch (TipoDesconocidoException e) {
            JOptionPane.showMessageDialog(null,
                "Tipo de arma desconocido: " + tipoArma,
                "Error compra arma", JOptionPane.ERROR_MESSAGE);
        } catch (PokemonNoExisteException e) {
            JOptionPane.showMessageDialog(null,
                "El pokémon " + e.getNombre() + " no existe.",
                "Error compra arma", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void COMPRAR_POKEMON() {
        String tipoPokemon = vista.getTipoPokemonTienda();
        String nombrePokemon = vista.getNombrePokemonTienda();
        Entrenador entrenador = vista.getEntrenador();

        try {
            facade.getGimnasio().getTienda().compraPokemon(entrenador, tipoPokemon, nombrePokemon);
            vista.actualizarComboListaPokemones(modeloComboPokemones);
            vista.vaciarTextPokemonTienda();
            vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
            modeloComboPokemones.addElement(nombrePokemon);
            vista.actualizarComboListaPokemones(modeloComboPokemones);
        } catch (TipoDesconocidoException e) {
            JOptionPane.showMessageDialog(null,
                "Tipo de Pokémon desconocido: " + tipoPokemon,
                "Error compra Pokémon", JOptionPane.ERROR_MESSAGE);
        } catch (CompraImposibleException e) {
            JOptionPane.showMessageDialog(null,
                "Créditos insuficientes: tienes " + e.getCreditos() +
                ", necesitas " + e.getCosto() + ".",
                "Error compra Pokémon", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void RECARGAR() {
        String nombrePokemon = vista.getNombrePokemonComboPokemon();
        Entrenador entrenador = vista.getEntrenador();
        try {
            entrenador.buscaPokemon(nombrePokemon).recargar();
        } catch (PokemonNoExisteException e) {
            JOptionPane.showMessageDialog(null,
                "El pokémon " + e.getNombre() + " no existe.",
                "Error recarga", JOptionPane.ERROR_MESSAGE);
        }
    }

    void CREAR_ENTRENADOR() {
        String nombreEntrenador = vista.getNombreEntrenador();
        int creditos = vista.getCreditosEntrenador();
        try {
            Entrenador entrenador = new Entrenador(nombreEntrenador, creditos);
            facade.getGimnasio().putEntrenador(entrenador);
            modeloListaEntrenadores.addElement(entrenador);
            vista.actualizarListaEntrenadores(modeloListaEntrenadores);
            vista.resetZonaEntrenador();
        } catch (NombreUtilizadoException e1) {
            JOptionPane.showMessageDialog(null,
                "El nombre de entrenador ya está en uso: " + e1.getNombre(),
                "Error creación entrenador", JOptionPane.ERROR_MESSAGE);
        }

        if (modeloListaEntrenadores.getSize() > 1) {
            vista.encenderBotonDuelo();
        }
    }

    void CREAR_POKEMON() {
        String nombrePokemon = vista.getNombrePokemonCreado();
        String tipoPokemon = vista.getTipoPokemonCreado();
        Entrenador entrenador = vista.getEntrenador();
        try {
            Pokemon pokemon = facade.getPokFact().getPokemon(tipoPokemon, nombrePokemon);
            entrenador.putPokemon(pokemon);
            modeloComboPokemones.addElement(nombrePokemon);
            vista.actualizarComboListaPokemones(modeloComboPokemones);
            vista.vaciarTextPokemonCreado();
        } catch (TipoDesconocidoException e1) {
            JOptionPane.showMessageDialog(null,
                "Tipo de Pokémon desconocido: " + tipoPokemon,
                "Error creación Pokémon", JOptionPane.ERROR_MESSAGE);
        } catch (NombreUtilizadoException e1) {
            JOptionPane.showMessageDialog(null,
                "Ya existe un Pokémon con ese nombre: " + e1.getNombre(),
                "Error creación Pokémon", JOptionPane.ERROR_MESSAGE);
        }
    }

    void AGREGAR_A_EQUIPO() {
        String nombrePokemon = vista.getNombrePokemonComboPokemon();
        Entrenador entrenador = vista.getEntrenador();
        try {
            entrenador.agregarPokemonEquipo(nombrePokemon);
        } catch (EquipoLlenoException e1) {
            JOptionPane.showMessageDialog(null,
                "El equipo está lleno.",
                "Error agregar al equipo", JOptionPane.ERROR_MESSAGE);
        }
    }

    void LISTA_ENTRENADORES() {
        HashMap<String, Pokemon> listaPokemones;
        vista.vaciarTextPokemonCreado();
        modeloComboPokemones.removeAllElements();

        Entrenador entrenador = vista.getEntrenador();
        if (entrenador != null) {
            if (vista.getEntrenador2().equals("")) {
                vista.setTextEntrenador1(entrenador.getNombre());
            } else if (!vista.getEntrenador1().equals("") &&
                       !vista.getEntrenador1().equals(entrenador.getNombre())) {
                vista.setTextEntrenador2(entrenador.getNombre());
            }
            listaPokemones = entrenador.getPokemones();
            vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
            listaPokemones.forEach((k, v) -> modeloComboPokemones.addElement(k));
        }

        // Reemplaza isEmpty() por getSize() == 0
        if (modeloComboPokemones.getSize() == 0) {
            vista.apagarBotonesPokemonCreado();
        } else {
            vista.actualizarComboListaPokemones(modeloComboPokemones);
        }
    }

    private void LISTA_DUELOS() {
        // implementación pendiente
    }
}
