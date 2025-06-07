package prueba;

import entrenador.Entrenador;
import entrenador.HechizoNiebla;
import entrenador.HechizoTormenta;
import entrenador.HechizoViento;
import excepciones.ArenaOcupadaException;
import excepciones.CompraImposibleException;
import excepciones.NombreUtilizadoException;
import excepciones.TipoDesconocidoException;
import interfaces.IHechizo;
import modelo.*;
import pokemones.PokemonFactory;
import armas.Arma;
import armas.ArmaFactory;
import persistencia.GimnasioManager;
import vista.VentanaPokemones;
import controlador.Controlador;

import java.awt.EventQueue;
import java.util.*;

/**
 * Clase principal para ejecutar:
 *  - Versión Consola: Etapa 1 (Registro), Etapa 2 (Inscripción), Etapa 3 (Torneo)
 *  - Versión GUI: Interfaz gráfica con todas las funcionalidades
 */
public class Prueba {
    public static void main(String[] args) {
        // Preguntar al usuario qué versión quiere ejecutar
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Bienvenido al sistema del Gimnasio Pokémon ===");
        System.out.println("¿Qué versión desea ejecutar?");
        System.out.println("1. Versión Consola");
        System.out.println("2. Versión GUI (Interfaz Gráfica)");
        System.out.print("Seleccione opción (1 o 2): ");
        
        int opcion = -1;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Opción inválida. Iniciando versión consola por defecto.");
            opcion = 1;
        }
        
        if (opcion == 2) {
            iniciarVersionGUI();
        } else {
            iniciarVersionConsola(scanner);
        }
    }

    private static void iniciarVersionGUI() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Inicializar el sistema
                    Gimnasio gimnasio = Gimnasio.getInstancia();
                    SistemaPelea sistemaPelea = SistemaPelea.getInstancia();
                    sistemaPelea.inicializarArenas(3); // Inicializar con 3 arenas por defecto
                    
                    // Crear y configurar la vista y el controlador
                    VentanaPokemones ventana = new VentanaPokemones();
                    Controlador controlador = new Controlador();
                    controlador.setVista(ventana);
                    
                    // Mostrar la ventana
                    ventana.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error al iniciar la interfaz gráfica: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private static void iniciarVersionConsola(Scanner scanner) {
        // 1) Instanciar GimnasioManager y tratar de cargar estado existente
        GimnasioManager manager = new GimnasioManager();
        EtapaTorneo etapaTorneo = manager.cargarEstado();
        if (etapaTorneo == null) {
            etapaTorneo = new EtapaTorneo();
        }

        // 2) Obtener singletons (o instanciarlos si aún no existían)
        Gimnasio gimnasio = Gimnasio.getInstancia();
        SistemaPelea sistemaPelea = SistemaPelea.getInstancia();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Etapa actual: " + etapaTorneo.getEtapaActual() + " ---");
            System.out.println("Menú principal:");
            System.out.println("1. Alta de Entrenador");
            System.out.println("2. Alta/Mejora de Pokémon para un Entrenador");
            System.out.println("3. Configurar/Añadir Arenas (Etapa 1)");
            System.out.println("4. Inscribir Entrenador al Torneo (Etapa 2)");
            System.out.println("5. Iniciar Torneo (Etapa 3)");
            System.out.println("6. Guardar Estado");
            System.out.println("7. Cargar Estado");
            System.out.println("8. Mostrar Estado Actual (Entrenadores, Arenas, Inscriptos)");
            System.out.println("9. Salir");

            System.out.print("Seleccione opción: ");
            int opcion = -1;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Opción inválida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    if (etapaTorneo.getEtapaActual() != EtapaTorneo.Etapa.REGISTRO_ENTRENADORES) {
                        System.out.println("Solo puede registrar entrenadores en la etapa de REGISTRO.");
                        break;
                    }
                    altaEntrenador(gimnasio, manager, etapaTorneo, scanner);
                    break;

                case 2:
                    if (etapaTorneo.getEtapaActual() != EtapaTorneo.Etapa.REGISTRO_ENTRENADORES) {
                        System.out.println("Solo puede dar de alta o mejorar Pokémon en la etapa de REGISTRO.");
                        break;
                    }
                    altaOmejoraPokemon(gimnasio, scanner);
                    break;

                case 3:
                    if (etapaTorneo.getEtapaActual() != EtapaTorneo.Etapa.REGISTRO_ENTRENADORES) {
                        System.out.println("Solo puede configurar arenas en la etapa de REGISTRO.");
                        break;
                    }
                    configurarArenas(sistemaPelea, manager, etapaTorneo, scanner);
                    break;

                case 4:
                    if (etapaTorneo.getEtapaActual() != EtapaTorneo.Etapa.REGISTRO_ENTRENADORES
                            && etapaTorneo.getEtapaActual() != EtapaTorneo.Etapa.INSCRIPCION_TORNEO) {
                        System.out.println("No puede inscribir entrenadores en esta etapa.");
                        break;
                    }
                    if (etapaTorneo.getEtapaActual() == EtapaTorneo.Etapa.REGISTRO_ENTRENADORES) {
                        etapaTorneo.avanzarEtapa(); // pasamos a INSCRIPCION_TORNEO
                    }
                    inscribirEntrenador(gimnasio, etapaTorneo, scanner);
                    break;

                case 5:
                    if (etapaTorneo.getEtapaActual() != EtapaTorneo.Etapa.INSCRIPCION_TORNEO) {
                        System.out.println("Solo puede iniciar el torneo en la etapa de INSCRIPCION_TORNEO.");
                        break;
                    }
                    if (gimnasio.getInscriptosTorneo().size() < 8) {
                        System.out.println("Debe haber 8 entrenadores inscritos para iniciar el torneo. Actualmente hay: "
                                + gimnasio.getInscriptosTorneo().size());
                        break;
                    }
                    etapaTorneo.avanzarEtapa(); // pasamos a EN_PROGRESO
                    iniciarTorneo(gimnasio, sistemaPelea, etapaTorneo);
                    break;

                case 6:
                    if (!etapaTorneo.puedeGuardarEstado()) {
                        System.out.println("No se puede guardar el estado en la etapa actual: " + etapaTorneo.getEtapaActual());
                        break;
                    }
                    manager.guardarEstado(gimnasio, sistemaPelea, etapaTorneo);
                    System.out.println("Estado guardado correctamente.");
                    break;

                case 7:
                    EtapaTorneo nuevaEtapa = manager.cargarEstado();
                    if (nuevaEtapa != null) {
                        etapaTorneo = nuevaEtapa;
                        gimnasio = Gimnasio.getInstancia();
                        sistemaPelea = SistemaPelea.getInstancia();
                        System.out.println("Estado cargado correctamente. Etapa: " + etapaTorneo.getEtapaActual());
                    } else {
                        System.out.println("No existe un estado previo para cargar.");
                    }
                    break;

                case 8:
                    mostrarEstadoActual(gimnasio, sistemaPelea);
                    break;

                case 9:
                    // Antes de salir, intentamos guardar si estamos en etapa que lo permite
                    if (etapaTorneo.puedeGuardarEstado()) {
                        manager.guardarEstado(gimnasio, sistemaPelea, etapaTorneo);
                        System.out.println("Estado guardado antes de salir.");
                    } else {
                        System.out.println("No se guardó el estado porque el torneo está en progreso.");
                    }
                    System.out.println("¡Hasta luego!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

    private static void altaEntrenador(Gimnasio gimnasio, GimnasioManager manager, EtapaTorneo etapa, Scanner scanner) {
        System.out.println("\n-- Alta de Entrenador --");
        System.out.print("Nombre del entrenador: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Créditos iniciales (entero): ");
        int creditos;
        try {
            creditos = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Créditos inválidos.");
            return;
        }

        System.out.println("¿Quiere asignar un hechizo inicial? (S/N): ");
        String resp = scanner.nextLine().trim();
        IHechizo hechizo = null;
        if (resp.equalsIgnoreCase("S")) {
            System.out.println("Seleccione hechizo: 1. Tormenta  2. Viento  3. Niebla");
            String h = scanner.nextLine().trim();
            switch (h) {
                case "1":
                    hechizo = new HechizoTormenta();
                    break;
                case "2":
                    hechizo = new HechizoViento();
                    break;
                case "3":
                    hechizo = new HechizoNiebla();
                    break;
                default:
                    System.out.println("Hechizo inválido. Se crea sin hechizo.");
                    break;
            }
        }

        try {
            Entrenador e = (hechizo == null)
                    ? new Entrenador(nombre, creditos)
                    : new Entrenador(nombre, creditos, hechizo);
            gimnasio.putEntrenador(e);
            // Guardamos inmediatamente (en esta etapa sí está permitido)
            manager.guardarEstado(gimnasio, SistemaPelea.getInstancia(), etapa);
            System.out.println("Entrenador \"" + nombre + "\" registrado con éxito.");
        } catch (NombreUtilizadoException ex) {
            System.out.println("Error: el nombre \"" + ex.getNombre() + "\" ya está en uso.");
        }
    }

    private static void altaOmejoraPokemon(Gimnasio gimnasio, Scanner scanner) {
        System.out.println("\n-- Alta/Mejora de Pokémon --");
        System.out.print("Ingrese nombre del entrenador: ");
        String nombre = scanner.nextLine().trim();
        try {
            Entrenador e = gimnasio.getEntrenador(nombre);
            System.out.println("1. Añadir Pokémon desde fábrica");
            System.out.println("2. Comprar o mejorar Pokémon desde la tienda");
            System.out.print("Seleccione opción: ");
            String opt = scanner.nextLine().trim();
            PokemonFactory pokFac = new PokemonFactory();
            ArmaFactory armFac = new ArmaFactory();

            switch (opt) {
                case "1":
                    System.out.println("¿Qué tipo? (Agua, Fuego, Piedra, Hielo)");
                    String tipo = scanner.nextLine().trim();
                    System.out.println("¿Qué nombre de Pokémon? (por ejemplo: Magikarp, Onix, Charizard...)");
                    String nombrePoke = scanner.nextLine().trim();
                    System.out.println("¿Quiere asignar un arma inicial? (S/N)");
                    String resp = scanner.nextLine().trim();
                    if (resp.equalsIgnoreCase("S")) {
                        System.out.println("Arma: Espada o Hacha");
                        String armaStr = scanner.nextLine().trim();
                        Arma a = armFac.getArma(armaStr.toLowerCase());
                        e.putPokemon(pokFac.getPokemon(tipo, nombrePoke, a));
                    } else {
                        e.putPokemon(pokFac.getPokemon(tipo, nombrePoke));
                    }
                    System.out.println("Pokémon agregado al entrenador " + nombre);
                    break;

                case "2":
                    System.out.println("Comprando/mejorando Pokémon desde la Tienda:");
                    System.out.println("Ingrese tipo de Pokémon (Agua, Fuego, Piedra, Hielo): ");
                    String t2 = scanner.nextLine().trim();
                    System.out.println("Ingrese nombre de Pokémon: ");
                    String n2 = scanner.nextLine().trim();
                    try {
                        gimnasio.getTienda().compraPokemon(e, t2, n2);
                        System.out.println("Pokémon comprado: " + n2 + " (Tipo " + t2 + ")");
                    } catch (CompraImposibleException ex) {
                        System.out.println("Error al comprar/mejorar Pokémon: " + ex.getMessage());
                    } catch (TipoDesconocidoException ex) {
                        System.out.println("Error al comprar/mejorar Pokémon: Tipo " + ex.getTipo() + " desconocido");
                    }
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void configurarArenas(SistemaPelea sistemaPelea, GimnasioManager manager, EtapaTorneo etapa, Scanner scanner) {
        System.out.println("\n-- Configurar Arenas --");
        System.out.print("¿Cuántas arenas desea crear? (entero): ");
        int n;
        try {
            n = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
            return;
        }
        sistemaPelea.inicializarArenas(n);
        // Guardamos inmediatamente la configuración de arenas (estamos en REGISTRO, así que sí se permite)
        manager.guardarEstado(Gimnasio.getInstancia(), sistemaPelea, etapa);
        System.out.println("Se crearon " + n + " arenas (todas Bosque + Fácil).");
    }

    private static void inscribirEntrenador(Gimnasio gimnasio, EtapaTorneo etapa, Scanner scanner) {
        System.out.println("\n-- Inscribir Entrenador al Torneo --");
        List<Entrenador> lista = gimnasio.getInscriptosTorneo();
        System.out.println("Actualmente inscritos (" + lista.size() + "/8):");
        for (Entrenador e : lista) {
            System.out.println(" - " + e.getNombre());
        }
        if (lista.size() >= 8) {
            System.out.println("Ya se alcanzó el cupo máximo de 8 inscriptos.");
            return;
        }
        System.out.print("Nombre del entrenador a inscribir: ");
        String nombre = scanner.nextLine().trim();
        try {
            gimnasio.inscribirAlTorneo(nombre);
            System.out.println("Entrenador \"" + nombre + "\" inscrito correctamente.");
        } catch (Exception ex) {
            System.out.println("No se pudo inscribir: " + ex.getMessage());
        }
        if (gimnasio.getInscriptosTorneo().size() == 8) {
            System.out.println("¡Ya hay 8 entrenadores inscritos! Listos para iniciar el torneo.");
        }
    }

    private static void iniciarTorneo(Gimnasio gimnasio, SistemaPelea sistemaPelea, EtapaTorneo etapa) {
        System.out.println("\n--- INICIO DEL TORNEO (Eliminación directa) ---");
        List<Entrenador> participantes = new ArrayList<>(gimnasio.getInscriptosTorneo());
        List<Entrenador> ganadoresCuartos = new ArrayList<>();

        try {
            // ---------- CUARTOS DE FINAL (4 duelos simultáneos) ----------
            System.out.println("\n[CUARTOS DE FINAL]");
            List<Duelo> duelosCuartos = new ArrayList<>();
            for (int i = 0; i < 8; i += 2) {
                ArenaFisica arena = sistemaPelea.asignarArenaLibre();
                Duelo d = gimnasio.crearDuelo(
                        participantes.get(i).getNombre(),
                        participantes.get(i + 1).getNombre(),
                        arena
                );
                if (d != null) {
                    duelosCuartos.add(d);
                    sistemaPelea.addDuelo(d);
                } else {
                    System.out.println("No se pudo crear duelo entre " +
                                       participantes.get(i).getNombre() + " y " +
                                       participantes.get(i + 1).getNombre());
                    arena.liberar();
                }
            }

            // Si no se crearon duelos, abortamos
            if (duelosCuartos.isEmpty()) {
                System.out.println("No hay duelos válidos en cuartos. Torneo finalizado.");
                return;
            }

            // Ejecutar los duelos de cuartos en paralelo y esperar
            List<Thread> hilosCuartos = new ArrayList<>();
            for (final Duelo d : duelosCuartos) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            d.run();
                        } catch (Exception ex) {
                            System.out.println("Error en duelo concurrente: " + ex.getMessage());
                        } finally {
                            sistemaPelea.liberarArena(d.getArena());
                        }
                    }
                });
                hilosCuartos.add(t);
                t.start();
            }
            for (Thread t : hilosCuartos) {
                t.join();
            }

            // Recoger ganadores de cuartos
            for (Duelo d : duelosCuartos) {
                if (d.getGanador() != null) {
                    ganadoresCuartos.add(d.getGanador());
                    System.out.println("Ganador Cuarto: " + d.getGanador().getNombre());
                }
            }

            // Si menos de 2 ganadores, abortamos
            if (ganadoresCuartos.size() < 2) {
                System.out.println("No hay suficientes ganadores de cuartos para semifinales. Torneo finalizado.");
                return;
            }

            // ---------- SEMIFINALES (2 duelos simultáneos) ----------
            System.out.println("\n[SEMIFINALES]");
            List<Entrenador> semifinalistas = new ArrayList<>(ganadoresCuartos);
            List<Duelo> duelosSemi = new ArrayList<>();
            List<Entrenador> ganadoresSemi = new ArrayList<>();

            for (int i = 0; i < semifinalistas.size(); i += 2) {
                if (i + 1 >= semifinalistas.size()) break;
                ArenaFisica arena = sistemaPelea.asignarArenaLibre();
                Duelo d = gimnasio.crearDuelo(
                        semifinalistas.get(i).getNombre(),
                        semifinalistas.get(i + 1).getNombre(),
                        arena
                );
                if (d != null) {
                    duelosSemi.add(d);
                    sistemaPelea.addDuelo(d);
                } else {
                    System.out.println("No se pudo crear duelo semifinal entre " +
                                       semifinalistas.get(i).getNombre() + " y " +
                                       semifinalistas.get(i + 1).getNombre());
                    arena.liberar();
                }
            }

            // Si no se crearon semifinales, abortamos
            if (duelosSemi.isEmpty()) {
                System.out.println("No hay duelos válidos en semifinales. Torneo finalizado.");
                return;
            }

            // Ejecutar las semifinales en paralelo y esperar
            List<Thread> hilosSemi = new ArrayList<>();
            for (final Duelo d : duelosSemi) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            d.run();
                        } catch (Exception ex) {
                            System.out.println("Error en duelo semifinal: " + ex.getMessage());
                        } finally {
                            sistemaPelea.liberarArena(d.getArena());
                        }
                    }
                });
                hilosSemi.add(t);
                t.start();
            }
            for (Thread t : hilosSemi) {
                t.join();
            }

            // Recoger ganadores de semifinales
            for (Duelo d : duelosSemi) {
                if (d.getGanador() != null) {
                    ganadoresSemi.add(d.getGanador());
                    System.out.println("Ganador Semifinal: " + d.getGanador().getNombre());
                }
            }

            // Si menos de 2 ganadores, abortamos
            if (ganadoresSemi.size() < 2) {
                System.out.println("No hay dos ganadores de semifinales para la final. Torneo finalizado.");
                return;
            }

            // ---------- FINAL (1 duelo) ----------
            System.out.println("\n[FINAL]");
            ArenaFisica arenaFinal = sistemaPelea.asignarArenaLibre();
            Duelo dFinal = gimnasio.crearDuelo(
                    ganadoresSemi.get(0).getNombre(),
                    ganadoresSemi.get(1).getNombre(),
                    arenaFinal
            );
            if (dFinal != null) {
                sistemaPelea.addDuelo(dFinal);
                dFinal.iniciarDuelo();
                System.out.println("Ganador Final: " + dFinal.getGanador().getNombre());
                sistemaPelea.liberarArena(arenaFinal);
            } else {
                System.out.println("No se pudo crear el duelo final. Torneo finalizado.");
                arenaFinal.liberar();
            }

            // El torneo ya terminó
            etapa.avanzarEtapa(); // pasa a FINALIZADO
            System.out.println("\n--- TORNEO FINALIZADO: ETAPA FINALIZADO ---");

            // Limpiamos los inscriptos para un posible próximo torneo
            gimnasio.clearInscriptos();

        } catch (InterruptedException ie) {
            System.out.println("Error al asignar arena: " + ie.getMessage());
        } catch (ArenaOcupadaException ae) {
            System.out.println("Error: la arena está ocupada.");
        }
    }

    private static void mostrarEstadoActual(Gimnasio gim, SistemaPelea sp) {
        System.out.println("\n-- Estado Actual del Gimnasio --");
        System.out.println("Entrenadores registrados:");
        for (String key : gim.getEntrenadores().keySet()) {
            System.out.println(" - " + key);
        }
        System.out.println("\nArenas disponibles (#" + sp.getArenas().size() + "): ");
        for (ArenaFisica a : sp.getArenas()) {
            System.out.println("Arena #" + a.getId() + "  |  Ocupada: " + a.estaOcupada());
        }
        System.out.println("\nEnt. inscritos al próximo torneo (" + gim.getInscriptosTorneo().size() + "/8):");
        for (Entrenador e : gim.getInscriptosTorneo()) {
            System.out.println(" - " + e.getNombre());
        }
    }
}
