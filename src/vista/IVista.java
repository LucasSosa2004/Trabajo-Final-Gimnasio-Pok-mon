package vista;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import entrenador.Entrenador;
import modelo.Duelo;

public interface IVista {

    void setActionListener(ActionListener actionListener);

    int getCreditosEntrenador();

    String getNombreEntrenador();

    String getNombrePokemonCreado();

    String getNombrePokemonTienda();

    String getNombrePokemonComboPokemon();

    String getTipoPokemonCreado();

    String getTipoPokemonTienda();

    String getTipoArmaTienda();

    String getNombrePokemonComboTienda();

    Entrenador getEntrenador();

    void apagarBotonesPokemonCreado();

    void apagarBotonesPokemonTienda();

    void resetZonaEntrenador();

    void vaciarTextPokemonCreado();

    void vaciarTextPokemonTienda();

    void mostrarPanel(String string);

    void actualizarListaEntrenadores(DefaultListModel<Entrenador> modelo);

    void actualizarComboListaPokemones(DefaultComboBoxModel <String>modelo);

    void setTextEntrenador(String nombre);

    void encenderBotonDuelo();

    void actualizarListaDuelo(DefaultListModel<Duelo> modelo);

    Duelo getDuelo();

    String getEntrenador1();

    void setTextEntrenador1(String nombre);

    void setTextEntrenador2(String nombre);

    void encenderBotonSeleccionarE2();

    void encenderTextoE2();

    void encenderBotonAgregarDuelo();

    String getEntrenador2();

}