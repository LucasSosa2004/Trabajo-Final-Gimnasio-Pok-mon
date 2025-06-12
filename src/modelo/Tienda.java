package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import armas.ArmaFactory;
import entrenador.Entrenador;
import excepciones.CompraImposibleException;
import excepciones.PokemonNoExisteException;
import excepciones.PokemonNoPuedeUsarArmaE;
import excepciones.TipoDesconocidoException;
import pokemones.Pokemon;
import pokemones.PokemonFactory;

/**
 * Tienda encargada de vender Pok�mon y armas.
 * Las f�bricas internas se marcan como transient para que no se serialicen.
 */
public class Tienda implements Serializable {
    private static final long serialVersionUID = 1L;

    // Las f�bricas no se serializan
    private transient ArmaFactory armaFactory;
    private transient PokemonFactory pokemonFactory;

    public Tienda() {
        this.armaFactory = new ArmaFactory();
        this.pokemonFactory = new PokemonFactory();
    }

    /**
     * Permite a un entrenador comprar un arma para uno de sus Pok�mons.
     *
     * @param tipo   Tipo de arma a comprar
     * @param e      Entrenador que realiza la compra
     * @param nombre Nombre del Pok�mon que usar� el arma
     * @throws CompraImposibleException   Si el entrenador no tiene suficientes cr�ditos
     * @throws TipoDesconocidoException   Si el tipo de arma no es reconocido
     * @throws PokemonNoPuedeUsarArmaE    Si el Pok�mon no puede usar el arma
     * @throws PokemonNoExisteException   Si el Pok�mon no existe en el entrenador
     */
    public void comprarArma(String tipo, Entrenador e, String nombre)
            throws CompraImposibleException, TipoDesconocidoException, PokemonNoPuedeUsarArmaE, PokemonNoExisteException {
        assert tipo != null && !tipo.isEmpty() : "El tipo de arma no puede ser nulo o vac�o";
        assert e != null : "El entrenador no puede ser nulo";
        assert nombre != null && !nombre.isEmpty() : "El nombre del Pok�mon no puede ser nulo o vac�o";

        // Buscar el Pok�mon en el entrenador
        Pokemon p = e.buscaPokemon(nombre);
        // Obtener el arma de la f�brica (puede lanzar TipoDesconocidoException)
        armas.Arma a = armaFactory.getArma(tipo);
        // Verificar cr�ditos
        double costoArma = a.getCosto();
        if (e.getCreditos() < costoArma) {
            throw new CompraImposibleException(e.getCreditos(), costoArma);
        }
        // Asignar el arma y descontar cr�ditos
        p.setArma(a);
        e.subCreditos(costoArma);

        assert e.getCreditos() >= 0 : "Los cr�ditos del entrenador no pueden ser negativos";
    }

    /**
     * Permite a un entrenador comprar un Pok�mon de un tipo espec�fico.
     *
     * @param e      Entrenador que realiza la compra
     * @param tipo   Tipo de Pok�mon a comprar
     * @param nombre Nombre del Pok�mon a comprar
     * @throws CompraImposibleException If el entrenador no tiene suficientes cr�ditos
     * @throws TipoDesconocidoException If el tipo de Pok�mon no es reconocido
     */
    public void compraPokemon(Entrenador e, String tipo, String nombre)
            throws CompraImposibleException, TipoDesconocidoException {
        assert e != null : "El entrenador no puede ser nulo";
        assert tipo != null && !tipo.isEmpty() : "El tipo de Pok�mon no puede ser nulo o vac�o";
        assert nombre != null && !nombre.isEmpty() : "El nombre del Pok�mon no puede ser nulo o vac�o";
    	

        // Crear el Pok�mon con la f�brica (puede lanzar TipoDesconocidoException)
        Pokemon p = pokemonFactory.getPokemon(tipo, nombre);
        double costoPoke = p.getCosto();
        if (e.getCreditos() < costoPoke) {
            throw new CompraImposibleException(e.getCreditos(), costoPoke);
        }
        // Agregar el Pok�mon al entrenador y descontar cr�ditos
        try {
            e.putPokemon(p);
        } catch (excepciones.NombreUtilizadoException ex) {
            // Si el nombre ya existe, mostramos mensaje y salimos
            System.out.println("El nombre " + ex.getNombre() + " ya est� siendo utilizado");
            return;
        }
        e.subCreditos(costoPoke);

        assert e.getCreditos() >= 0 : "Los cr�ditos del entrenador no pueden ser negativos";
    }

    /**
     * Tras la deserializaci�n, volvemos a instanciar las f�bricas marcadas como transient.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.armaFactory = new ArmaFactory();
        this.pokemonFactory = new PokemonFactory();
    }

    @Override
    public String toString() {
        return "Tienda [armaFactory=" + armaFactory + ", pokemonFactory=" + pokemonFactory + "]";
    }
}
