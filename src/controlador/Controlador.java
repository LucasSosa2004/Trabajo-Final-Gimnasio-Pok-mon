package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import entrenador.Entrenador;
import excepciones.ArenaOcupadaException;
import excepciones.CompraImposibleException;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;
import excepciones.EquipoLlenoException;
import excepciones.NombreUtilizadoException;
import excepciones.PokemonNoExisteException;
import excepciones.PokemonNoPuedeUsarArmaE;
import excepciones.TipoDesconocidoException;
import interfaces.IArena;
import modelo.Duelo;
import modelo.FacadePokemones;
import pokemones.Pokemon;
import vista.IVista;
import vista.ObserverVentanaDuelo;

public class Controlador implements ActionListener {

	private FacadePokemones facade = FacadePokemones.getInstancia();
	private IVista vista;
	private DefaultListModel<Entrenador> modeloListaEntrenadores = new DefaultListModel<>();
	private DefaultListModel<String> modeloListaDuelos = new DefaultListModel<>();
	private DefaultComboBoxModel<String> modeloComboPokemones = new DefaultComboBoxModel<>();
	private List<String> entrenadoresEnDuelos = new ArrayList<>();
	private boolean hayArena;

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
			this.ARENA();
			break;
		case "DUELO":
			vista.mostrarPanel("DUELO");
			break;
		case "CREAR_ENTRENADOR":
			this.CREAR_ENTRENADOR();
			break;
		case "CREAR_POKEMON":
			this.CREAR_POKEMON();
			break;
		case "AGREGAR_A_EQUIPO":
			this.AGREGAR_A_EQUIPO();
			break;
		case "RECARGAR":
			this.RECARGAR();
			break;
		case "COMPRAR_POKEMON":
			this.COMPRAR_POKEMON();
			break;
		case "COMPRAR_ARMA":
			this.COMPRAR_ARMA();
			break;
		case "AGREGAR_ARENA":
			this.AGREGAR_ARENA();
			break;
		case "AGREGAR_E1":
			this.AGREGAR_E1();
			break;
		case "AGREGAR_E2":
			this.AGREGAR_E2();
			break;
		case "AGREGAR_DUELO":
			this.AGREGAR_DUELO();
			break;
		case "INICIAR_TORNEO":
			this.INICIAR_TORNEO();
			break;
		case "LISTA_ENTRENADORES":
			this.LISTA_ENTRENADORES();
			break;
		case "LISTA_DUELOS":
			this.LISTA_DUELOS();
			break;
		case "EMPEZAR_DE_ARCHIVO":
			this.EMPEZAR_DE_ARCHIVO();
			break;
		case "GUARDAR":
			this.GUARDAR();
			break;
		}
	}

	private void GUARDAR() {
		if (facade.puedeGuardarEstado()) {
			facade.guardarArch();
			vista.setTextConsola("Estado guardado exitosamente");
		} else {
			vista.setTextConsola("No se puede guardar el estado mientras un torneo esta en curso");
		}
	}

	private void EMPEZAR_DE_ARCHIVO() {
		try {
			// Intentar cargar el estado
			var etapa = facade.cargarEstado();

			if (etapa != null) {
				// Actualizar la vista con los datos cargados
				modeloListaDuelos.clear();
				modeloListaEntrenadores.clear();
				facade.actualizarReferencias();
				for (var entrenador : facade.getGimnasio().getEntrenadores().values()) {
					modeloListaEntrenadores.addElement(entrenador);
				}
				this.facade.setNumArenas();
				this.hayArena = facade.hayArenas();
				if (this.modeloListaEntrenadores.getSize() > 1 && hayArena) {
					vista.encenderBotonDuelo();
				}
				if (this.modeloListaDuelos.getSize() == 4)
					vista.encenderBotonTorneo();
					
				vista.mostrarPanel("VACIO");
				vista.actualizarListaDuelo(modeloListaDuelos);
				vista.actualizarListaEntrenadores(modeloListaEntrenadores);
				vista.setTextConsola("Estado cargado exitosamente");
			} else {
				vista.setTextConsola("No hay estado guardado para cargar");
			}
		} catch (Exception ex) {
			vista.setTextConsola("Error al cargar el estado: " + ex.getMessage());
		}
	}

	private void INICIAR_TORNEO() {
	    new Thread(() -> {
	        Entrenador ganador1;
	        Entrenador ganador2;
	        Duelo duelo;
	        try {

                // CUARTOS
                SwingUtilities.invokeLater(() -> vista.setTextConsola("INICIO CUARTOS"));
                facade.ejecutarRonda();
                SwingUtilities.invokeLater(() -> vista.setTextConsola("FIN DE CUARTOS"));

                // Limpio lista
                SwingUtilities.invokeLater(() -> {
                    modeloListaDuelos.clear();
                    vista.actualizarListaDuelo(modeloListaDuelos);
                });

                // GENERO SEMIS
                for (int i = 0; i < 4; i += 2) {
                    ganador1 = facade.getListaDuelosAux().get(i).getGanador();
                    ganador2 = facade.getListaDuelosAux().get(i + 1).getGanador();
                    duelo = facade.crearDuelo(ganador1.getNombre(), ganador2.getNombre());

                    ObserverVentanaDuelo v1 = new ObserverVentanaDuelo(duelo, vista);
                    facade.agregarDuelo(duelo);

                    String texto = ganador1.getNombre() + " VS " + ganador2.getNombre();
                    SwingUtilities.invokeLater(() -> {
                        modeloListaDuelos.addElement(texto);
                        vista.actualizarListaDuelo(modeloListaDuelos);
                    });
                }
	            

                // SEMIS
                SwingUtilities.invokeLater(() -> vista.setTextConsola("INICIO SEMIS"));
                facade.ejecutarRonda();
                SwingUtilities.invokeLater(() -> vista.setTextConsola("FIN DE SEMIS"));

                SwingUtilities.invokeLater(() -> {
                    modeloListaDuelos.clear();
                    vista.actualizarListaDuelo(modeloListaDuelos);
                });

                ganador1 = facade.getListaDuelosAux().get(0).getGanador();
                ganador2 = facade.getListaDuelosAux().get(1).getGanador();
                duelo = facade.crearDuelo(ganador1.getNombre(), ganador2.getNombre());

                ObserverVentanaDuelo v1 = new ObserverVentanaDuelo(duelo, vista);
                facade.agregarDuelo(duelo);

                String textoFinal = ganador1.getNombre() + " VS " + ganador2.getNombre();
                SwingUtilities.invokeLater(() -> {
                    modeloListaDuelos.addElement(textoFinal);
                    vista.actualizarListaDuelo(modeloListaDuelos);
                });
	            

	            // FINAL
	            SwingUtilities.invokeLater(() -> vista.setTextConsola("INICIO FINAL"));
	            facade.ejecutarRonda();
	            SwingUtilities.invokeLater(() -> vista.setTextConsola("FIN DEL TORNEO"));

	            // Mostrar campeón
	            Duelo finalDuelo = facade.getListaDuelosAux().get(0);
	            Entrenador campeon = finalDuelo.getGanador();
	            SwingUtilities.invokeLater(() -> {
	                vista.setTextConsola("CAMPEÓN DEL TORNEO: " + campeon.getNombre());
	                vista.apagarBotonIniciarTorneo();
	                modeloListaDuelos.clear();
	                vista.actualizarListaDuelo(modeloListaDuelos);
	            });

	            entrenadoresEnDuelos.clear();

	        } catch (EntrenadorNoExisteException | EntrenadorSinPokemonesException e) {
	            SwingUtilities.invokeLater(() -> vista.setTextConsola(e.getMessage()));
	        } catch (ArenaOcupadaException e) {
	            e.printStackTrace(); // Podés también mostrar esto por consola si querés
	        }
	    }).start();
	}

	private void AGREGAR_ARENA() {
		String tipoArena;
		String dificultadArena;
		IArena base;
		IArena decorada;
		
		tipoArena=vista.getTipoArena();
		dificultadArena=vista.getDificultadArena();
		base = facade.crearArenaBase(tipoArena);
		decorada = facade.crearArenaDecorada(dificultadArena,base);
		facade.agregarArena(decorada);
		
		vista.setTextConsola("Arena de "+tipoArena+", dificultad "+dificultadArena+" agregada al gimnasio.");
		this.hayArena  = true;
		if (this.modeloListaEntrenadores.getSize() > 1) 
			vista.encenderBotonDuelo();
		vista.resetZonaArena();		
	}

	private void ARENA() {

	}

	private void AGREGAR_E2() {
		String entrenador;
		entrenador = vista.getEntrenador2();
		if (this.entrenadoresEnDuelos.contains(entrenador)) {
			vista.setTextConsola("El entrenador " + entrenador + " ya participa de un duelo, seleccione otro.");
		} else {

			vista.encenderBotonAgregarDuelo();
			vista.apagarBotonAgregarE2();
			vista.setTextConsola(vista.getEntrenador2() + " aregado al duelo.");
		}
	}

	private void AGREGAR_E1() {
		String entrenador;
		entrenador = vista.getEntrenador1();
		if (this.entrenadoresEnDuelos.contains(entrenador)) {
			vista.setTextConsola("El entrenador " + entrenador + " ya participa de un duelo, seleccione otro.");
		} else {
			vista.apagarBotonAgregarE1();
			vista.encenderTextoE2();
			vista.encenderBotonSeleccionarE2();
			vista.setTextConsola(vista.getEntrenador1() + " aregado al duelo.");
		}

	}

	private void AGREGAR_DUELO() {
		String entrenador1;
		String entrenador2;

		entrenador1 = vista.getEntrenador1();
		entrenador2 = vista.getEntrenador2();

		vista.resetZonaDuelo();
		try {
			Duelo duelo = facade.crearDuelo(entrenador1, entrenador2);

			ObserverVentanaDuelo v1 = new ObserverVentanaDuelo(duelo, this.vista);

			facade.agregarDuelo(duelo);

			modeloListaDuelos.addElement(entrenador1 + " VS " + entrenador2);
			vista.actualizarListaDuelo(modeloListaDuelos);
			vista.setTextConsola("Duelo creado.");
			this.entrenadoresEnDuelos.add(entrenador1);
			this.entrenadoresEnDuelos.add(entrenador2);
			if (this.modeloListaDuelos.getSize() == 4) {
				vista.encenderBotonTorneo();
			}

		} catch (EntrenadorNoExisteException e) {
			vista.setTextConsola("Nombre de entrenador " + e.getNombre() + " no existe.");
		} catch (IllegalArgumentException | IllegalStateException e) {
			vista.setTextConsola(e.getMessage());
		} catch (EntrenadorSinPokemonesException e) {
			this.vista.setTextConsola(e.getMessage());
			;
		} catch (ArenaOcupadaException e) {
		}

	}

	private void COMPRAR_ARMA() {
		String tipoArma;
		String nombrePokemon;
		Entrenador entrenador;

		tipoArma = vista.getTipoArmaTienda();
		nombrePokemon = vista.getNombrePokemonComboTienda();
		entrenador = vista.getEntrenador();

		try {
			facade.comprarArma(tipoArma, entrenador, nombrePokemon);
			vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
			vista.setTextConsola(
					tipoArma + " adquirida, añadido al pokemon " + nombrePokemon + " de " + entrenador.getNombre());
		} catch (PokemonNoPuedeUsarArmaE e) {
			vista.setTextConsola("El pokemon: " + e.getNombre() + " no es de tipo piedra, no puede usar arma.");

		} catch (CompraImposibleException e) {
			vista.setTextConsola(
					"Dinero insuficiente. Dinero actual: " + e.getCreditos() + ". Dinero necesario: " + e.getCosto());
		} catch (TipoDesconocidoException e) {
		} catch (PokemonNoExisteException e) {
		}

	}

	private void COMPRAR_POKEMON() {
		String tipoPokemon;
		String nombrePokemon;
		Entrenador entrenador;
		nombrePokemon = vista.getNombrePokemonTienda();
		tipoPokemon = vista.getTipoPokemonTienda();
		entrenador = vista.getEntrenador();
		try {

			facade.comprarPokemon(entrenador, tipoPokemon, nombrePokemon);
			vista.actualizarComboListaPokemones(modeloComboPokemones);
			vista.vaciarTextPokemonTienda();
			vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
			modeloComboPokemones.addElement(nombrePokemon);
			vista.actualizarComboListaPokemones(modeloComboPokemones);
			vista.setTextConsola(
					"Pokemon " + nombrePokemon + " adquirido, añadido al equipo de " + entrenador.getNombre());
		} catch (CompraImposibleException e) {
			vista.setTextConsola(
					"Dinero insuficiente. Dinero actual: " + e.getCreditos() + ". Dinero necesario: " + e.getCosto());
		} catch (TipoDesconocidoException e) {
		}

	}

	private void RECARGAR() {
		String nombrePokemon;
		Entrenador entrenador;
		nombrePokemon = vista.getNombrePokemonComboPokemon();
		entrenador = vista.getEntrenador();

		try {
			entrenador.buscaPokemon(nombrePokemon).recargar();
			vista.setTextConsola("Pokemon " + nombrePokemon + " del " + entrenador.getNombre() + " recargado.");
			vista.actualizarListaEntrenadores(modeloListaEntrenadores);

		} catch (PokemonNoExisteException e) {

		}
	}

	void CREAR_ENTRENADOR() {
		String nombreEntrenador;
		Entrenador entrenador;
		int creditos;
		nombreEntrenador = vista.getNombreEntrenador();
		creditos = vista.getCreditosEntrenador();
		try {
			entrenador = facade.crearEntrenador(nombreEntrenador, creditos);
			facade.agregarAlGimnasio(entrenador);
			modeloListaEntrenadores.addElement(entrenador);
			vista.actualizarListaEntrenadores(modeloListaEntrenadores);
			vista.resetZonaEntrenador();
			vista.setTextConsola("Entrenador " + nombreEntrenador + " añadido al gimnasio.");
			if (this.modeloListaEntrenadores.getSize() > 1 && hayArena) {
				vista.encenderBotonDuelo();
			}
		} catch (NombreUtilizadoException e1) {

			vista.setTextConsola("Nombre de entrenador " + e1.getNombre() + " ya utilizado, escriba otro.");

		}

	}

	void CREAR_POKEMON() {
		String tipoPokemon;
		String nombrePokemon;
		Entrenador entrenador;
		Pokemon pokemon;
		nombrePokemon = vista.getNombrePokemonCreado();
		tipoPokemon = vista.getTipoPokemonCreado();
		entrenador = vista.getEntrenador();
		try {
			pokemon = facade.crearPokemon(tipoPokemon, nombrePokemon);
			entrenador.putPokemon(pokemon);
			modeloComboPokemones.addElement(nombrePokemon);
			vista.setTextConsola("Pokemon " + nombrePokemon + " se añadio a la lista de pokemones de "
					+ entrenador.getNombre() + ".");
			vista.actualizarComboListaPokemones(modeloComboPokemones);
			vista.vaciarTextPokemonCreado();
		} catch (TipoDesconocidoException e1) {
		}

		catch (NombreUtilizadoException e1) {
			vista.setTextConsola("Nombre de pokemon " + e1.getNombre() + " ya utilizado, escriba otro.");
		}

	}

	void AGREGAR_A_EQUIPO() {
		String nombrePokemon;
		Entrenador entrenador;
		nombrePokemon = vista.getNombrePokemonComboPokemon();
		entrenador = vista.getEntrenador();
		try {
			entrenador.agregarPokemonEquipo(nombrePokemon);
			vista.setTextConsola(
					"Pokemon " + nombrePokemon + " añadido al equipo de combate de " + entrenador.getNombre() + ".");
			vista.actualizarListaEntrenadores(modeloListaEntrenadores);
		} catch (EquipoLlenoException e1) {
			vista.setTextConsola("El equipo esta lleno, no se pueden agregar mas pokemones.");
		}
	}

	void LISTA_ENTRENADORES() {

		Entrenador entrenador;
		HashMap<String, Pokemon> listaPokemones = new HashMap<>();
		vista.vaciarTextPokemonCreado();
		this.modeloComboPokemones.removeAllElements();
		entrenador = vista.getEntrenador();
		if (entrenador != null) {

			if (vista.getEntrenador1().equals("") || vista.estadoBotonE1()) {
				vista.setTextEntrenador1(entrenador.getNombre());
			} else if (vista.estadoBotonE2() && !vista.getEntrenador1().equals(entrenador.getNombre())) {
				vista.setTextEntrenador2(entrenador.getNombre());
			}
			listaPokemones = entrenador.getPokemones();
			vista.setTextEntrenador(entrenador.getNombre() + " Cred: " + entrenador.getCreditos());
		}
		if (listaPokemones.isEmpty()) {
			vista.apagarBotonesPokemonCreado();
		} else
			listaPokemones.forEach((k, v) -> modeloComboPokemones.addElement(k));
		vista.actualizarComboListaPokemones(modeloComboPokemones);
	}

	private void LISTA_DUELOS() {

	}

}
