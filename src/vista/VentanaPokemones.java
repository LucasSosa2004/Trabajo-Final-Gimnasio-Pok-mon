package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entrenador.Entrenador;
import modelo.Duelo;

public class VentanaPokemones extends JFrame implements KeyListener, IVista, MouseListener, ListSelectionListener, ItemListener, ActionListener {
	
	private static final long serialVersionUID = 1L;

	private ActionListener actionListener;

	private JPanel contentPane;
	private JPanel panel_central;
	private JPanel panel_entrenadores;
	private JPanel panel_creacion;
	private JPanel panel_botones;
	private JPanel panel_detalles;
	private JList<String> listaDuelo;
	private JPanel panel_opciones;
	private JButton boton_entrenador;
	private JButton boton_pokemon;
	private JButton boton_tienda;
	private JButton boton_arena;
	private JScrollPane scrollEntrenadores;
	private JList<Entrenador> listEntrenadores;
	private JScrollPane scrollConsola;
	private JTextArea textConsola;

	// Paneles específicos para cada opción en panel_detalles
	private JPanel panelAgregarEntrenador;
	private JPanel panelArena;
	private JTextField textNombreEntrenador;
	private JLabel lblCreditosIniciales;
	private JTextField textCred;
	private JPanel panelPokemon;
	private JTextField textNombrePokemonPokemon;
	private JLabel lblTipo;
	private JComboBox<String> comboTipoPokemon;
	private JButton boton_CrearPokemon;
	private JButton boton_AgregarAEquipo;
	private JPanel panel_AgregarAlTorneo;
	private JPanel panelTienda;
	private JLabel lblTiendaEntrenador;
	private JLabel lblTipoPokemonTienda;
	private JComboBox<String> comboTipoPokemonesTienda;
	private JLabel lblPokemonCompraArma;
	private JLabel lblCompraArma;
	private JComboBox<String> comboListaPokemonesTienda;
	private JComboBox<String> comboComprarArmasTienda;
	private JButton boton_ComprarPokemon;
	private JButton boton_CompraArma;
	private JButton boton_CrearNuevoEntrenador;
	private JButton boton_duelo;
	private JPanel panelDuelo;
	private JButton boton_AgregarE1;
	private JButton boton_AgregarE2;
	private JTextField textEntrenador1;
	private JTextField textEntrenador2;
	private JButton boton_AgregarDuelo;
	private JLabel lblNomEntrenadorDuelo;
	private JLabel lblEntrenador;
	private JTextField textNombreEntrenadorTienda;
	private JLabel lblListaPok;
	private JComboBox<String> comboListaPokemonesPokemon;
	private JButton boton_Recargar;
	private JScrollPane scrollDuelo;
	private JTextField textNombrePokemonTienda;
	private JLabel lblNombre;
	private JLabel lblDificultadArena;
	private JComboBox<String> comboDificultadArena;
	private JComboBox<String> comboTipoArena;
	private JButton boton_AgregarArena;
	private JPanel panelBotonesSur;
	private JButton boton_iniciar_torneo;
	private JButton boton_guardar;
	private JButton boton_empezarArch;
	/*  
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPokemones frame = new VentanaPokemones();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public VentanaPokemones() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 777, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel_central = new JPanel();
		contentPane.add(panel_central, BorderLayout.CENTER);
		panel_central.setLayout(new GridLayout(1, 3, 0, 0));

		// Panel de lista de entrenadores
		panel_entrenadores = new JPanel();
		panel_central.add(panel_entrenadores);
		panel_entrenadores.setLayout(new BorderLayout(0, 0));
		scrollEntrenadores = new JScrollPane();
		scrollEntrenadores.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		panel_entrenadores.add(scrollEntrenadores, BorderLayout.CENTER);
		listEntrenadores = new JList<Entrenador>();
		scrollEntrenadores.setViewportView(listEntrenadores);

		panel_AgregarAlTorneo = new JPanel();
		panel_entrenadores.add(panel_AgregarAlTorneo, BorderLayout.SOUTH);
		panel_AgregarAlTorneo.setLayout(new GridLayout(0, 1, 0, 0));

		// Panel de creación y botones
		panel_creacion = new JPanel();
		panel_central.add(panel_creacion);
		panel_creacion.setLayout(new GridLayout(2, 1, 0, 0));

		panel_botones = new JPanel();
		panel_creacion.add(panel_botones);
		panel_botones.setLayout(new BorderLayout(0, 0));

		panel_opciones = new JPanel();
		panel_botones.add(panel_opciones, BorderLayout.CENTER);
		panel_opciones.setLayout(new GridLayout(0, 1, 0, 0));

		// Botones de opciones
		boton_entrenador = new JButton("Entrenador");
		boton_entrenador.setFont(new Font("Arial", Font.BOLD, 14));
		panel_opciones.add(boton_entrenador);

		boton_pokemon = new JButton("Pokemon");
		boton_pokemon.setFont(new Font("Arial", Font.BOLD, 14));
		panel_opciones.add(boton_pokemon);

		boton_tienda = new JButton("Tienda");
		boton_tienda.setFont(new Font("Arial", Font.BOLD, 14));
		panel_opciones.add(boton_tienda);

		boton_arena = new JButton("Arena");
		boton_arena.setFont(new Font("Arial", Font.BOLD, 14));
		panel_opciones.add(boton_arena);

		boton_duelo = new JButton("Duelo");
		boton_duelo.setFont(new Font("Arial", Font.BOLD, 14));
		panel_opciones.add(boton_duelo);

		// Panel de detalles con CardLayout
		panel_detalles = new JPanel();
		panel_creacion.add(panel_detalles);
		panel_detalles.setLayout(new CardLayout());

		// Panel por defecto vacío
		JPanel panel = new JPanel();
		panel_detalles.add(panel, "VACIO");
		
		
		//////////////////////////////////////////////////////////
		// Panel para agregar entrenador
		
				panelAgregarEntrenador = new JPanel();
				GridBagLayout gbl_panelAgregarEntrenador = new GridBagLayout();
				gbl_panelAgregarEntrenador.columnWeights = new double[] { 0.0, 1.0 };
				panelAgregarEntrenador.setLayout(gbl_panelAgregarEntrenador);
																				
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
		
				// Nombre entrenador
				JLabel lblNombreEntrenador = new JLabel("  Nombre:      ");
				lblNombreEntrenador.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente 
				lblNombreEntrenador.setForeground(Color.DARK_GRAY); // Color de texto
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.EAST;
				panelAgregarEntrenador.add(lblNombreEntrenador, gbc);
		
				textNombreEntrenador = new JTextField(15);
				textNombreEntrenador.setFont(new Font("Arial", Font.PLAIN, 14));
				textNombreEntrenador.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), 
																														
						BorderFactory.createEmptyBorder(3, 3, 3, 3)
				));

				GridBagConstraints gbc_textNombreEntrenador = new GridBagConstraints();
				gbc_textNombreEntrenador.insets = new Insets(0, 0, 5, 0);
				gbc_textNombreEntrenador.fill = GridBagConstraints.HORIZONTAL;
				gbc_textNombreEntrenador.gridx = 1;
				gbc_textNombreEntrenador.gridy = 0;
				panelAgregarEntrenador.add(textNombreEntrenador, gbc_textNombreEntrenador);
				textNombreEntrenador.setColumns(10);
		
				// Label "Creditos"
				lblCreditosIniciales = new JLabel("Creditos Iniciales:");
				lblCreditosIniciales.setForeground(Color.DARK_GRAY);
				lblCreditosIniciales.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblCreditosIniciales = new GridBagConstraints();
				gbc_lblCreditosIniciales.anchor = GridBagConstraints.EAST;
				gbc_lblCreditosIniciales.insets = new Insets(0, 0, 5, 5);
				gbc_lblCreditosIniciales.gridx = 0;
				gbc_lblCreditosIniciales.gridy = 3;
				panelAgregarEntrenador.add(lblCreditosIniciales, gbc_lblCreditosIniciales);
		
				textCred = new JTextField(15);
				textCred.setFont(new Font("Arial", Font.PLAIN, 14));
				textCred.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
																														
																														
						BorderFactory.createEmptyBorder(3, 3, 3, 3)
				));

				GridBagConstraints gbc_textCred = new GridBagConstraints();
				gbc_textCred.insets = new Insets(0, 0, 5, 0);
				gbc_textCred.fill = GridBagConstraints.HORIZONTAL;
				gbc_textCred.gridx = 1;
				gbc_textCred.gridy = 3;
				panelAgregarEntrenador.add(textCred, gbc_textCred);
				textCred.setColumns(10);
		
		
				boton_CrearNuevoEntrenador = new JButton("Crear nuevo entrenador");
				boton_CrearNuevoEntrenador.setForeground(Color.DARK_GRAY);
				boton_CrearNuevoEntrenador.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_CrearNuevoEntrenador = new GridBagConstraints();
				gbc_boton_CrearNuevoEntrenador.gridx = 1;
				gbc_boton_CrearNuevoEntrenador.gridy = 4;
				panelAgregarEntrenador.add(boton_CrearNuevoEntrenador, gbc_boton_CrearNuevoEntrenador);
				
				panel_detalles.add(panelAgregarEntrenador, "AGREGAR_ENTRENADOR");


		
		//////////////////////////////////////////////////////////


		//////////////////////////////////////////////////////////
		// Panel para Arena
				panelArena = new JPanel();
				GridBagLayout gbl_panelArena = new GridBagLayout();
				gbl_panelArena.columnWidths = new int[]{86, 142, 30, 0};
				gbl_panelArena.rowHeights = new int[]{0, 0, 0, 0, 30, 0, 0, 0};
				gbl_panelArena.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
				gbl_panelArena.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panelArena.setLayout(gbl_panelArena);
				
				
				panel_detalles.add(panelArena, "ARENA");
				JLabel lblTipoDeArena = new JLabel("Tipo:");
				lblTipoDeArena.setForeground(Color.DARK_GRAY);
				lblTipoDeArena.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblTipoDeArena = new GridBagConstraints();
				gbc_lblTipoDeArena.fill = GridBagConstraints.VERTICAL;
				gbc_lblTipoDeArena.insets = new Insets(0, 0, 5, 5);
				gbc_lblTipoDeArena.gridx = 0;
				gbc_lblTipoDeArena.gridy = 1;
				panelArena.add(lblTipoDeArena, gbc_lblTipoDeArena);
				comboTipoArena = new JComboBox<String>();
				comboTipoArena.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione el Tipo de arena", "Bosque", "Desierto", "Selva"}));
				comboTipoArena.setFont(new Font("Arial", Font.PLAIN, 14));
				GridBagConstraints gbc_comboTipoArena = new GridBagConstraints();
				gbc_comboTipoArena.fill = GridBagConstraints.VERTICAL;
				gbc_comboTipoArena.insets = new Insets(0, 0, 5, 5);
				gbc_comboTipoArena.gridx = 1;
				gbc_comboTipoArena.gridy = 1;
				panelArena.add(comboTipoArena, gbc_comboTipoArena);
						
						lblDificultadArena = new JLabel("Dificultad:");
				lblDificultadArena.setForeground(Color.DARK_GRAY);
				lblDificultadArena.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblDificultadArena = new GridBagConstraints();
				gbc_lblDificultadArena.insets = new Insets(0, 0, 5, 5);
				gbc_lblDificultadArena.gridx = 0;
				gbc_lblDificultadArena.gridy = 3;
				panelArena.add(lblDificultadArena, gbc_lblDificultadArena);
						
						comboDificultadArena = new JComboBox<String> ();
						comboDificultadArena.setFont(new Font("Arial", Font.PLAIN, 14));
				comboDificultadArena.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione la dificultad", "Facil", "Media", "Dificil"}));
				GridBagConstraints gbc_comboDificultadArena = new GridBagConstraints();
				gbc_comboDificultadArena.insets = new Insets(0, 0, 5, 5);
				gbc_comboDificultadArena.gridx = 1;
				gbc_comboDificultadArena.gridy = 3;
				panelArena.add(comboDificultadArena, gbc_comboDificultadArena);
				
						// otros campos:
			     boton_AgregarArena = new JButton("Agregar");
				boton_AgregarArena.setForeground(Color.DARK_GRAY);
				boton_AgregarArena.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_AgregarArena = new GridBagConstraints();
				gbc_boton_AgregarArena.insets = new Insets(0, 0, 5, 5);
				gbc_boton_AgregarArena.fill = GridBagConstraints.VERTICAL;
				gbc_boton_AgregarArena.gridx = 1;
				gbc_boton_AgregarArena.gridy = 4;
				panelArena.add(boton_AgregarArena, gbc_boton_AgregarArena);
				

		//////////////////////////////////////////////////////////

				
		
		//////////////////////////////////////////////////////////
		// Panel pokemon
				panelPokemon = new JPanel();
				GridBagLayout gbl_panelPokemon = new GridBagLayout();
				gbl_panelPokemon.columnWeights = new double[] { 0.0, 0.0, 1.0 };
				panelPokemon.setLayout(gbl_panelPokemon);
				GridBagConstraints gbcpanelPokemon = new GridBagConstraints();
				gbcpanelPokemon.insets = new Insets(10, 10, 10, 10); // Margen
		
				// Nombre
				JLabel lblNombrePokemon = new JLabel("  Nombre:      ");
				lblNombrePokemon.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente
				lblNombrePokemon.setForeground(Color.DARK_GRAY); // Color
				gbcpanelPokemon.gridx = 0;
				gbcpanelPokemon.gridy = 0;
				gbcpanelPokemon.anchor = GridBagConstraints.EAST;
				panelPokemon.add(lblNombrePokemon, gbcpanelPokemon);
		
				textNombrePokemonPokemon = new JTextField(15);
				textNombrePokemonPokemon.setForeground(Color.BLACK);
				textNombrePokemonPokemon.setFont(new Font("Arial", Font.PLAIN, 14));
				textNombrePokemonPokemon.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
																														
																														
						BorderFactory.createEmptyBorder(3, 3, 3, 3)// Padding interno
				));
				GridBagConstraints gbc_textNombrePokemonPokemon = new GridBagConstraints();
				gbc_textNombrePokemonPokemon.insets = new Insets(0, 0, 5, 0);
				gbc_textNombrePokemonPokemon.fill = GridBagConstraints.HORIZONTAL;
				gbc_textNombrePokemonPokemon.gridx = 2;
				gbc_textNombrePokemonPokemon.gridy = 0;
				panelPokemon.add(textNombrePokemonPokemon, gbc_textNombrePokemonPokemon);
				textNombrePokemonPokemon.setColumns(10);
		
				// Tipo
		
		
				lblTipo = new JLabel("  Tipo:             ");
				lblTipo.setForeground(Color.DARK_GRAY);
				lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblTipo = new GridBagConstraints();
				gbc_lblTipo.anchor = GridBagConstraints.EAST;
				gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
				gbc_lblTipo.gridx = 0;
				gbc_lblTipo.gridy = 2;
				panelPokemon.add(lblTipo, gbc_lblTipo);
		
				comboTipoPokemon = new JComboBox<String>();
				comboTipoPokemon.setFont(new Font("Arial", Font.PLAIN, 14));
				comboTipoPokemon.setModel(
						new DefaultComboBoxModel<String>(new String[] { "Seleccione un tipo", "Agua", "Hielo", "Fuego", "Piedra" }));
				GridBagConstraints gbc_comboTipoPokemon = new GridBagConstraints();
				gbc_comboTipoPokemon.insets = new Insets(0, 0, 5, 0);
				gbc_comboTipoPokemon.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboTipoPokemon.gridx = 2;
				gbc_comboTipoPokemon.gridy = 2;
				panelPokemon.add(comboTipoPokemon, gbc_comboTipoPokemon);
				
				boton_CrearPokemon = new JButton("Crear y agregar Pokemon");
				boton_CrearPokemon.setForeground(Color.DARK_GRAY);
				boton_CrearPokemon.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_CrearPokemon = new GridBagConstraints();
				gbc_boton_CrearPokemon.insets = new Insets(0, 0, 5, 0);
				gbc_boton_CrearPokemon.gridx = 2;
				gbc_boton_CrearPokemon.gridy = 3;
				panelPokemon.add(boton_CrearPokemon, gbc_boton_CrearPokemon);
				
				lblListaPok = new JLabel("  Lista pokemones:      ");
				lblListaPok.setForeground(Color.DARK_GRAY);
				lblListaPok.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblListaPok = new GridBagConstraints();
				gbc_lblListaPok.insets = new Insets(0, 0, 5, 5);
				gbc_lblListaPok.gridx = 0;
				gbc_lblListaPok.gridy = 4;
				panelPokemon.add(lblListaPok, gbc_lblListaPok);
				
				panel_detalles.add(panelPokemon, "AGREGAR_POKEMON");	
				
				boton_AgregarAEquipo = new JButton("Agregar a equipo");
				boton_AgregarAEquipo.setFont(new Font("Arial", Font.BOLD, 12));
				boton_AgregarAEquipo.setForeground(Color.DARK_GRAY);
				
				comboListaPokemonesPokemon = new JComboBox<String>();
				comboListaPokemonesPokemon.setFont(new Font("Arial", Font.PLAIN, 14));
				GridBagConstraints gbc_comboListaPokemonesPokemon = new GridBagConstraints();
				gbc_comboListaPokemonesPokemon.insets = new Insets(0, 0, 5, 0);
				gbc_comboListaPokemonesPokemon.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboListaPokemonesPokemon.gridx = 2;
				gbc_comboListaPokemonesPokemon.gridy = 4;
				panelPokemon.add(comboListaPokemonesPokemon, gbc_comboListaPokemonesPokemon);
				
				boton_Recargar = new JButton("Recargar");
				boton_Recargar.setForeground(Color.DARK_GRAY);
				boton_Recargar.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_Recargar = new GridBagConstraints();
				gbc_boton_Recargar.insets = new Insets(0, 0, 5, 0);
				gbc_boton_Recargar.gridx = 2;
				gbc_boton_Recargar.gridy = 5;
				panelPokemon.add(boton_Recargar, gbc_boton_Recargar);
				GridBagConstraints gbc_boton_AgregarAEquipo = new GridBagConstraints();
				gbc_boton_AgregarAEquipo.gridx = 2;
				gbc_boton_AgregarAEquipo.gridy = 6;
				panelPokemon.add(boton_AgregarAEquipo, gbc_boton_AgregarAEquipo);

		//////////////////////////////////////////////////////////


				
		//////////////////////////////////////////////////////////
		// TIENDA
				panelTienda = new JPanel();
				GridBagLayout gbl_panelTienda = new GridBagLayout();
				gbl_panelTienda.columnWeights = new double[] { 0.0, 1.0 };
				panelTienda.setLayout(gbl_panelTienda);
				GridBagConstraints gbc_lblTiendaEntrenador = new GridBagConstraints();
				gbc_lblTiendaEntrenador.anchor = GridBagConstraints.EAST;
				gbc_lblTiendaEntrenador.insets = new Insets(10, 10, 10, 10);
		
				lblTiendaEntrenador = new JLabel("Entrenador:");
				lblTiendaEntrenador.setForeground(Color.DARK_GRAY);
				lblTiendaEntrenador.setFont(new Font("Arial", Font.BOLD, 14));
				gbc_lblTiendaEntrenador.gridx = 0;
				gbc_lblTiendaEntrenador.gridy = 1;
				panelTienda.add(lblTiendaEntrenador, gbc_lblTiendaEntrenador);
				
				textNombreEntrenadorTienda = new JTextField();
				textNombreEntrenadorTienda.setEditable(false);
				textNombreEntrenadorTienda.setText("");
				GridBagConstraints gbc_textNombreEntrenadorTienda = new GridBagConstraints();
				gbc_textNombreEntrenadorTienda.insets = new Insets(0, 0, 5, 0);
				gbc_textNombreEntrenadorTienda.fill = GridBagConstraints.HORIZONTAL;
				gbc_textNombreEntrenadorTienda.gridx = 1;
				gbc_textNombreEntrenadorTienda.gridy = 1;
				panelTienda.add(textNombreEntrenadorTienda, gbc_textNombreEntrenadorTienda);
				textNombreEntrenadorTienda.setColumns(10);
				
				lblNombre = new JLabel("Nombre");
				lblNombre.setForeground(Color.DARK_GRAY);
				lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblNombre = new GridBagConstraints();
				gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
				gbc_lblNombre.gridx = 0;
				gbc_lblNombre.gridy = 2;
				panelTienda.add(lblNombre, gbc_lblNombre);
				
				textNombrePokemonTienda = new JTextField();
				GridBagConstraints gbc_textNombrePokemonTienda = new GridBagConstraints();
				gbc_textNombrePokemonTienda.insets = new Insets(0, 0, 5, 0);
				gbc_textNombrePokemonTienda.fill = GridBagConstraints.HORIZONTAL;
				gbc_textNombrePokemonTienda.gridx = 1;
				gbc_textNombrePokemonTienda.gridy = 2;
				panelTienda.add(textNombrePokemonTienda, gbc_textNombrePokemonTienda);
				textNombrePokemonTienda.setColumns(10);
		
				lblTipoPokemonTienda = new JLabel("Tipo");
				lblTipoPokemonTienda.setForeground(Color.DARK_GRAY);
				lblTipoPokemonTienda.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblTipoPokemonTienda = new GridBagConstraints();
				gbc_lblTipoPokemonTienda.insets = new Insets(0, 0, 5, 5);
				gbc_lblTipoPokemonTienda.gridx = 0;
				gbc_lblTipoPokemonTienda.gridy = 3;
				panelTienda.add(lblTipoPokemonTienda, gbc_lblTipoPokemonTienda);
		
				comboTipoPokemonesTienda = new JComboBox<String>(new String[] { "Seleccione un tipo", "Agua (100)", "Hielo (100)", "Fuego (120)", "Piedra (200)" });
				GridBagConstraints gbc_comboTipoPokemonesTienda = new GridBagConstraints();
				gbc_comboTipoPokemonesTienda.insets = new Insets(0, 0, 5, 0);
				gbc_comboTipoPokemonesTienda.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboTipoPokemonesTienda.gridx = 1;
				gbc_comboTipoPokemonesTienda.gridy = 3;
				panelTienda.add(comboTipoPokemonesTienda, gbc_comboTipoPokemonesTienda);
		
				boton_ComprarPokemon = new JButton("Comprar");
				boton_ComprarPokemon.setForeground(Color.DARK_GRAY);
				boton_ComprarPokemon.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_ComprarPokemon = new GridBagConstraints();
				gbc_boton_ComprarPokemon.insets = new Insets(0, 0, 5, 0);
				gbc_boton_ComprarPokemon.gridx = 1;
				gbc_boton_ComprarPokemon.gridy = 4;
				panelTienda.add(boton_ComprarPokemon, gbc_boton_ComprarPokemon);
		
				lblPokemonCompraArma = new JLabel("Pokemones");
				lblPokemonCompraArma.setForeground(Color.DARK_GRAY);
				lblPokemonCompraArma.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblPokemonCompraArma = new GridBagConstraints();
				gbc_lblPokemonCompraArma.insets = new Insets(0, 0, 5, 5);
				gbc_lblPokemonCompraArma.gridx = 0;
				gbc_lblPokemonCompraArma.gridy = 5;
				panelTienda.add(lblPokemonCompraArma, gbc_lblPokemonCompraArma);
		
				comboListaPokemonesTienda = new JComboBox<String>();
				GridBagConstraints gbc_comboListaPokemonesTienda = new GridBagConstraints();
				gbc_comboListaPokemonesTienda.insets = new Insets(0, 0, 5, 0);
				gbc_comboListaPokemonesTienda.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboListaPokemonesTienda.gridx = 1;
				gbc_comboListaPokemonesTienda.gridy = 5;
				panelTienda.add(comboListaPokemonesTienda, gbc_comboListaPokemonesTienda);
		
				lblCompraArma = new JLabel("Compra Arma");
				lblCompraArma.setForeground(Color.DARK_GRAY);
				lblCompraArma.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblCompraArma = new GridBagConstraints();
				gbc_lblCompraArma.insets = new Insets(0, 0, 5, 5);
				gbc_lblCompraArma.gridx = 0;
				gbc_lblCompraArma.gridy = 6;
				panelTienda.add(lblCompraArma, gbc_lblCompraArma);
				
						comboComprarArmasTienda = new JComboBox<String>();
						comboComprarArmasTienda.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione un arma", "Espada (50)", "Hacha (80)"}));
						GridBagConstraints gbc_comboComprarArmasTienda = new GridBagConstraints();
						gbc_comboComprarArmasTienda.insets = new Insets(0, 0, 5, 0);
						gbc_comboComprarArmasTienda.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboComprarArmasTienda.gridx = 1;
						gbc_comboComprarArmasTienda.gridy = 6;
						panelTienda.add(comboComprarArmasTienda, gbc_comboComprarArmasTienda);
		
				boton_CompraArma = new JButton("Comprar");
				boton_CompraArma.setForeground(Color.DARK_GRAY);
				boton_CompraArma.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_CompraArma = new GridBagConstraints();
				gbc_boton_CompraArma.gridx = 1;
				gbc_boton_CompraArma.gridy = 7;
				panelTienda.add(boton_CompraArma, gbc_boton_CompraArma);

				
				panel_detalles.add(panelTienda, "TIENDA");
		
		//////////////////////////////////////////////////////////
		
				
		//////////////////////////////////////////////////////////
		//DUELO		


				panelDuelo = new JPanel();
				GridBagLayout gbl_panelDuelo = new GridBagLayout();
				gbl_panelDuelo.columnWeights = new double[] { 0.0, 1.0 };
				panelDuelo.setLayout(gbl_panelDuelo);
				GridBagConstraints gbc_lblNomEntrenadorDuelo = new GridBagConstraints();
				gbc_lblNomEntrenadorDuelo.insets = new Insets(0, 0, 5, 5);								
												
				lblNomEntrenadorDuelo = new JLabel("Entrenador 1:");
				lblNomEntrenadorDuelo.setForeground(Color.DARK_GRAY);
				lblNomEntrenadorDuelo.setFont(new Font("Arial", Font.BOLD, 14));

				gbc_lblNomEntrenadorDuelo.gridx = 0;
				gbc_lblNomEntrenadorDuelo.gridy = 0;
				panelDuelo.add(lblNomEntrenadorDuelo, gbc_lblNomEntrenadorDuelo);
		
				textEntrenador1 = new JTextField();
				textEntrenador1.setFont(new Font("Arial", Font.ITALIC, 12));
				GridBagConstraints gbc_textEntrenador1 = new GridBagConstraints();
				gbc_textEntrenador1.fill = GridBagConstraints.HORIZONTAL;
				gbc_textEntrenador1.insets = new Insets(0, 0, 5, 0);
				gbc_textEntrenador1.gridx = 1;
				gbc_textEntrenador1.gridy = 0;
				panelDuelo.add(textEntrenador1, gbc_textEntrenador1);
				textEntrenador1.setColumns(10);
				
				boton_AgregarE1 = new JButton("Seleccionar");
				boton_AgregarE1.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_AgregarE1 = new GridBagConstraints();
				gbc_boton_AgregarE1.insets = new Insets(0, 0, 5, 0);
				gbc_boton_AgregarE1.gridx = 1;
				gbc_boton_AgregarE1.gridy = 1;
				panelDuelo.add(boton_AgregarE1, gbc_boton_AgregarE1);
		
				lblEntrenador = new JLabel("Entrenador 2:");
				lblEntrenador.setForeground(Color.DARK_GRAY);
				lblEntrenador.setFont(new Font("Arial", Font.BOLD, 14));
				GridBagConstraints gbc_lblEntrenador = new GridBagConstraints();
				gbc_lblEntrenador.insets = new Insets(0, 0, 5, 5);
				gbc_lblEntrenador.gridx = 0;
				gbc_lblEntrenador.gridy = 3;
				panelDuelo.add(lblEntrenador, gbc_lblEntrenador);

				textEntrenador2 = new JTextField();
				textEntrenador2.setFont(new Font("Arial", Font.ITALIC, 12));
				GridBagConstraints gbc_textEntrenador2 = new GridBagConstraints();
				gbc_textEntrenador2.fill = GridBagConstraints.HORIZONTAL;
				gbc_textEntrenador2.insets = new Insets(0, 0, 5, 0);
				gbc_textEntrenador2.gridx = 1;
				gbc_textEntrenador2.gridy = 3;
				panelDuelo.add(textEntrenador2, gbc_textEntrenador2);
				textEntrenador2.setColumns(10);
		
				boton_AgregarE2 = new JButton("Seleccionar");
				boton_AgregarE2.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_AgregarE2 = new GridBagConstraints();
				gbc_boton_AgregarE2.insets = new Insets(0, 0, 5, 0);
				gbc_boton_AgregarE2.gridx = 1;
				gbc_boton_AgregarE2.gridy = 4;
				panelDuelo.add(boton_AgregarE2, gbc_boton_AgregarE2);
				
				panel_detalles.add(panelDuelo, "DUELO");
						
				boton_AgregarDuelo = new JButton("Agregar duelo");
				boton_AgregarDuelo.setFont(new Font("Arial", Font.BOLD, 12));
				GridBagConstraints gbc_boton_AgregarDuelo = new GridBagConstraints();
				gbc_boton_AgregarDuelo.insets = new Insets(0, 0, 0, 5);
				gbc_boton_AgregarDuelo.gridx = 0;
				gbc_boton_AgregarDuelo.gridy = 5;
				panelDuelo.add(boton_AgregarDuelo, gbc_boton_AgregarDuelo);

		//////////////////////////////////////////////////////////
				// Panel de lista de torneo
				JPanel panel_torneo = new JPanel();
				panel_central.add(panel_torneo);
				panel_torneo.setLayout(new BorderLayout(0, 0));
				
				scrollDuelo = new JScrollPane();
				panel_torneo.add(scrollDuelo, BorderLayout.CENTER);
				listaDuelo = new JList<String>();
				
				scrollDuelo.setViewportView(listaDuelo);
		
				// Panel sur con consola
				JPanel panel_sur = new JPanel();
				contentPane.add(panel_sur, BorderLayout.SOUTH);
				panel_sur.setPreferredSize(new Dimension(50, 100));
				panel_sur.setLayout(new BorderLayout(0, 0));
				scrollConsola = new JScrollPane();
				panel_sur.add(scrollConsola, BorderLayout.CENTER);
				textConsola = new JTextArea();
				textConsola.setEditable(false);
				textConsola.setFont(new Font("Arial", Font.PLAIN, 14));
				scrollConsola.setViewportView(textConsola);
				
				panelBotonesSur = new JPanel();
				panel_sur.add(panelBotonesSur, BorderLayout.SOUTH);
				panelBotonesSur.setLayout(new GridLayout(0, 3, 0, 0));
				
				boton_iniciar_torneo = new JButton("Iniciar torneo");
				boton_iniciar_torneo.setFont(new Font("Arial", Font.BOLD, 14));
				boton_iniciar_torneo.setEnabled(false);
				boton_iniciar_torneo.setActionCommand("INICIAR_TORNEO");
				panelBotonesSur.add(boton_iniciar_torneo);
				
				boton_guardar = new JButton("Guardar");
				boton_guardar.setForeground(Color.DARK_GRAY);
				boton_guardar.setFont(new Font("Arial", Font.BOLD, 14));
				panelBotonesSur.add(boton_guardar);
				
				boton_empezarArch = new JButton("Empezar de archivo");
				boton_empezarArch.setForeground(Color.DARK_GRAY);
				boton_empezarArch.setFont(new Font("Arial", Font.BOLD, 14));
				panelBotonesSur.add(boton_empezarArch);
				
				//BOTONES
				boton_entrenador.setActionCommand("ENTRENADOR");
				boton_CrearNuevoEntrenador.setActionCommand("CREAR_ENTRENADOR");

				
				boton_pokemon.setActionCommand("POKEMON");
				boton_CrearPokemon.setActionCommand("CREAR_POKEMON");
				boton_Recargar.setActionCommand("RECARGAR");
				boton_AgregarAEquipo.setActionCommand("AGREGAR_A_EQUIPO");
				
				boton_tienda.setActionCommand("TIENDA");
				boton_ComprarPokemon.setActionCommand("COMPRAR_POKEMON");
				boton_CompraArma.setActionCommand("COMPRAR_ARMA");

				boton_arena.setActionCommand("ARENA");
				this.boton_AgregarArena.setActionCommand("AGREGAR_ARENA");
				
				boton_duelo.setActionCommand("DUELO");
				boton_AgregarE1.setActionCommand("AGREGAR_E1");
				boton_AgregarE2.setActionCommand("AGREGAR_E2");
				boton_AgregarDuelo.setActionCommand("AGREGAR_DUELO");
				
				this.boton_empezarArch.setActionCommand("EMPEZAR_DE_ARCHIVO");
				this.boton_guardar.setActionCommand("GUARDAR");
				
				
				
				
				boton_CrearNuevoEntrenador.setEnabled(false);
				
				boton_pokemon.setEnabled(false);
				boton_CrearPokemon.setEnabled(false);
				boton_Recargar.setEnabled(false);
				boton_AgregarAEquipo.setEnabled(false);

				boton_tienda.setEnabled(false);
				boton_ComprarPokemon.setEnabled(false);
				boton_CompraArma.setEnabled(false);
				
				boton_AgregarArena.setEnabled(false);
				
				boton_duelo.setEnabled(false);
				boton_AgregarE1.setEnabled(false);
				boton_AgregarE2.setEnabled(false);
				boton_AgregarDuelo.setEnabled(false);
				
				this.boton_empezarArch.setEnabled(true);
				this.boton_guardar.setEnabled(true);
				
				

				//TEXTOS
				textCred.addKeyListener(this);															
				textNombreEntrenador.addKeyListener(this);	
				
				textNombrePokemonPokemon.addKeyListener(this);
				
				textNombrePokemonTienda.addKeyListener(this);
				
				textEntrenador2.setEnabled(false);
				textEntrenador1.setEditable(false);
				textEntrenador2.setEditable(false);

				//LISTAS
				this.listEntrenadores.addListSelectionListener(this);
				this.listaDuelo.addListSelectionListener(this);

				//JCOMBOBOX
				this.comboTipoPokemon.addItemListener(this);
				this.comboTipoPokemonesTienda.addItemListener(this);
				this.comboComprarArmasTienda.addItemListener(this);
				
				
				this.comboDificultadArena.addItemListener(this);
				this.comboTipoArena.addItemListener(this);
				

				
				this.setVisible(true);

			}
	//////////////////////////////////////////////////////////

	/*public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout) (panel_detalles.getLayout());
		if (e.getSource() == boton_entrenador) {
			cl.show(panel_detalles, "AGREGAR_ENTRENADOR");
		} else if (e.getSource() == boton_arena) {
			cl.show(panel_detalles, "ARENA");
		} else if (e.getSource() == boton_pokemones) {
			cl.show(panel_detalles, "AGREGAR_POKEMON");
		} else if (e.getSource() == boton_tienda) {
			cl.show(panel_detalles, "TIENDA");
		} else if (e.getSource() == boton_duelo) {
			cl.show(panel_detalles, "DUELO");
		} else {
			
			// cl.show(panel_detalles, "VACIO"); // Opcional: oculta si otro botón no tiene
			// panel aún
		}
	}*/

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent event) {

		String nombreEntrenador=this.textNombreEntrenador.getText();
		
		String nombrePokemonCreado=this.textNombrePokemonPokemon.getText();		
		String tipoPokemonCreado=(String)this.comboTipoPokemon.getSelectedItem();
		
		String nombrePokemonTienda=this.textNombrePokemonTienda.getText();
		String tipoPokemonTienda=(String)this.comboTipoPokemonesTienda.getSelectedItem();
		


		
		int creditos=-1;

		try {
		    creditos = Integer.parseInt(this.textCred.getText());

		} catch (NumberFormatException e){}
	    if (this.textCred.getText().equals(""))
	    	creditos = 0;
		boolean condicion = creditos>=0 && nombreEntrenador!=null && !nombreEntrenador.equals("");
		this.boton_CrearNuevoEntrenador.setEnabled(condicion);
		
		boolean condicionCrearPokemon = nombrePokemonCreado!=null && !nombrePokemonCreado.equals("")&&!tipoPokemonCreado.equals("Seleccione un tipo");;
		this.boton_CrearPokemon.setEnabled(condicionCrearPokemon);
		
		boolean condicionComprarPokemon = nombrePokemonTienda!=null && !nombrePokemonTienda.equals("") && !tipoPokemonTienda.equals("Seleccione un tipo");
		this.boton_ComprarPokemon.setEnabled(condicionComprarPokemon);
	 }

	

	@Override
	public void setActionListener(ActionListener actionListener) {
	    this.boton_CrearNuevoEntrenador.addActionListener(actionListener);
	    this.boton_CrearPokemon.addActionListener(actionListener);
	    this.boton_AgregarAEquipo.addActionListener(actionListener);
	    this.boton_entrenador.addActionListener(actionListener);
	    this.boton_pokemon.addActionListener(actionListener);
	    
	    this.boton_tienda.addActionListener(actionListener);
	    this.boton_ComprarPokemon.addActionListener(actionListener);
	    this.boton_CompraArma.addActionListener(actionListener);
	    this.boton_Recargar.addActionListener(actionListener);
	   
	    this.boton_arena.addActionListener(actionListener);
	    this.boton_AgregarArena.addActionListener(actionListener);
	    
	    this.boton_duelo.addActionListener(actionListener);
	    this.boton_AgregarE1.addActionListener(actionListener);
	    this.boton_AgregarE2.addActionListener(actionListener);
	    this.boton_AgregarDuelo.addActionListener(actionListener);
	    
	    this.boton_empezarArch.addActionListener(actionListener);
	    this.boton_guardar.addActionListener(actionListener);
	    
	    this.boton_iniciar_torneo.addActionListener(actionListener);
		this.actionListener = actionListener;

	}

	@Override
	public int getCreditosEntrenador() {
	    if(this.textCred.getText().equals(""))
	    	return 0;
	    else
	    	return  Integer.parseInt(this.textCred.getText());
	    	

	}

	@Override
	public String getNombreEntrenador() {
		// TODO Auto-generated method stub
		return this.textNombreEntrenador.getText();
	}

	@Override
	public void mostrarPanel(String nombrePanel) {
	    CardLayout cl = (CardLayout) panel_detalles.getLayout();
	    cl.show(panel_detalles, nombrePanel);

		
	}

	@Override
	public void actualizarListaEntrenadores(DefaultListModel<Entrenador> modelo) {
	    this.listEntrenadores.setModel(modelo);
	}

	@Override
	public void actualizarListaDuelo(DefaultListModel<String> modelo) {
	    this.listaDuelo.setModel(modelo);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Entrenador entrenador=(Entrenador)this.listEntrenadores.getSelectedValue();
		//Duelo duelo=(Duelo)this.listaDuelo.getSelectedValue();
		ActionEvent event;
		String comando;

		if (e.getSource() == listEntrenadores) {
			comando = "LISTA_ENTRENADORES";
		}else
			comando = "LISTA_DUELOS";

		
		boolean condicionEntrenadorSelect = entrenador!=null;
		boolean condicionEncenderBotonE1 = entrenador!=null && !this.textEntrenador1.getText().isEmpty() && !this.boton_AgregarE2.isEnabled() && !this.boton_AgregarDuelo.isEnabled();
		this.boton_pokemon.setEnabled(condicionEntrenadorSelect);
		this.boton_tienda.setEnabled(condicionEntrenadorSelect);
		
		this.boton_AgregarE1.setEnabled(condicionEncenderBotonE1);

		event = new ActionEvent(e, 0, comando);
		this.actionListener.actionPerformed(event);
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		String tipoPokemonCreado=(String)this.comboTipoPokemon.getSelectedItem();
		String nombrePokemonCreado=this.textNombrePokemonPokemon.getText();
		
		String nombrePokemonCombo=(String)this.comboListaPokemonesPokemon.getSelectedItem();
		
		String tipoPokemonTienda=(String)this.comboTipoPokemonesTienda.getSelectedItem();
		String nombrePokemonTienda=this.textNombrePokemonTienda.getText();
		
		String tipoArmaTienda=(String)this.comboComprarArmasTienda.getSelectedItem();
		
		String tipoArena=(String)this.comboTipoArena.getSelectedItem();
		String dificultadArena=(String)this.comboDificultadArena.getSelectedItem();
		
		boolean condicionCrearPokemon=nombrePokemonCreado!=null && !nombrePokemonCreado.equals("")&&!tipoPokemonCreado.equals("Seleccione un tipo");
		
		boolean condicionPokemonSeleccionado=nombrePokemonCombo!=null;
		
		boolean condicionComprarPokemon=nombrePokemonTienda!=null && !nombrePokemonTienda.equals("")&&!tipoPokemonTienda.equals("Seleccione un tipo");
		
		boolean condicionComprarArma=!tipoArmaTienda.equals("Seleccione un arma");
		
		boolean condicionAgregarArena = !tipoArena.equals("Seleccione el Tipo de arena") && !dificultadArena.equals("Seleccione la dificultad");
		
		this.boton_Recargar.setEnabled(condicionPokemonSeleccionado);	
		this.boton_AgregarAEquipo.setEnabled(condicionPokemonSeleccionado);
		
		this.boton_CrearPokemon.setEnabled(condicionCrearPokemon);
		
		this.boton_ComprarPokemon.setEnabled(condicionComprarPokemon);
		
		this.boton_CompraArma.setEnabled(condicionComprarArma);
		
		this.boton_AgregarArena.setEnabled(condicionAgregarArena);
		
	}

	@Override
	public String getNombrePokemonCreado() {
		return this.textNombrePokemonPokemon.getText();
	}

	@Override
	public String getTipoPokemonCreado() {
		return (String)this.comboTipoPokemon.getSelectedItem();
	}

	@Override
	public Entrenador getEntrenador() {
		return (Entrenador)this.listEntrenadores.getSelectedValue();
	}


	@Override
	public void actualizarComboListaPokemones(DefaultComboBoxModel <String> modelo) {
		this.comboListaPokemonesPokemon.setModel(modelo);
		this.comboListaPokemonesTienda.setModel(modelo);
		
	}

	@Override
	public void resetZonaEntrenador() {
		this.textNombreEntrenador.setText("");
		this.textCred.setText("");
		this.boton_CrearNuevoEntrenador.setEnabled(false);
	}

	@Override
	public void vaciarTextPokemonCreado() {
		this.textNombrePokemonPokemon.setText("");
		this.comboTipoPokemon.setSelectedIndex(0);
		
	}

	@Override
	public void apagarBotonesPokemonCreado() {
		this.boton_Recargar.setEnabled(false);
		this.boton_AgregarAEquipo.setEnabled(false);		
	}
	
	

	@Override
	public String getNombrePokemonComboPokemon() {
		// TODO Auto-generated method stub
		return (String)this.comboListaPokemonesPokemon.getSelectedItem();
	}

	@Override
	public void setTextEntrenador(String nombre) {
		this.textNombreEntrenadorTienda.setText(nombre);
		
	}

	@Override
	public String getNombrePokemonTienda() {
		// TODO Auto-generated method stub
		return this.textNombrePokemonTienda.getText();
	}

	@Override
	public String getTipoPokemonTienda() {
		// TODO Auto-generated method stub
		return (String)this.comboTipoPokemonesTienda.getSelectedItem();
	}

	@Override
	public void apagarBotonesPokemonTienda() {
		this.boton_ComprarPokemon.setEnabled(false);
		
	}

	@Override
	public void vaciarTextPokemonTienda() {
		this.textNombrePokemonTienda.setText("");
		this.comboTipoPokemonesTienda.setSelectedIndex(0);
		
	}

	@Override
	public String getTipoArmaTienda() {
		return (String)this.comboComprarArmasTienda.getSelectedItem();
	}

	@Override
	public String getNombrePokemonComboTienda() {
		return (String)this.comboListaPokemonesTienda.getSelectedItem();
	}
	
	@Override
	public void encenderBotonDuelo() {
		this.boton_duelo.setEnabled(true);
	}
	@Override
	public String getEntrenador1() {
		return this.textEntrenador1.getText();
	}
	@Override
	public void setTextEntrenador1(String nombre) {
		this.textEntrenador1.setText(nombre);
	}
	@Override
	public void setTextEntrenador2(String nombre) {
		this.textEntrenador2.setText(nombre);
		
	}
	@Override
	public void encenderBotonSeleccionarE2() {
		this.boton_AgregarE2.setEnabled(true);
	}
	@Override
	public void encenderTextoE2() {
		this.textEntrenador2.setEnabled(true);
	}
	
	@Override
	public void encenderBotonAgregarDuelo() {
		this.boton_AgregarDuelo.setEnabled(true);
	}
	@Override
	public String getEntrenador2() {
		return this.textEntrenador2.getText();
	}
	@Override
	public boolean estadoBotonE1() {
		return this.boton_AgregarE1.isEnabled();
	}
	@Override
	public void apagarBotonAgregarE1() {
		this.boton_AgregarE1.setEnabled(false);
	}
	@Override
	public boolean estadoBotonE2() {
		return this.boton_AgregarE2.isEnabled();
	}
	@Override
	public void apagarBotonAgregarE2() {
		this.boton_AgregarE2.setEnabled(false);
		
	}
	@Override
	public void resetZonaDuelo() {
		this.textEntrenador1.setText("");
		this.textEntrenador2.setText("");
		this.boton_AgregarE1.setEnabled(false);
		this.boton_AgregarE2.setEnabled(false);
		this.boton_AgregarDuelo.setEnabled(false);
	}

	@Override
	public void setTextConsola(String informacion) {
		this.textConsola.setText(this.textConsola.getText()+informacion+"\n");
		
	}

	@Override
	public String getTipoArena() {
		return (String)this.comboTipoArena.getSelectedItem();
	}

	@Override
	public String getDificultadArena() {
		// TODO Auto-generated method stub
		return (String)this.comboDificultadArena.getSelectedItem();
	}

	@Override
	public void resetZonaArena() {
		this.comboTipoArena.setSelectedIndex(0);
		this.comboDificultadArena.setSelectedIndex(0);
		this.boton_AgregarArena.setEnabled(false);

	}
	@Override
	public void encenderBotonTorneo() {
		this.boton_iniciar_torneo.setEnabled(true);
	}

	@Override
	public void apagarBotonIniciarTorneo() {
		this.boton_iniciar_torneo.setEnabled(false);
	}
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void encenderBotonGuardarEstado() {
		this.boton_guardar.setEnabled(true);
		
	}

	@Override
	public void apagarBotonGuardarEstado() {
		this.boton_guardar.setEnabled(false);

	}

	@Override
	public void encenderBotonCargarEstado() {
		this.boton_empezarArch.setEnabled(true);

		
	}

	@Override
	public void apagarBotonCargarEstado() {
		this.boton_empezarArch.setEnabled(false);
		
	}
	
	
}