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
 * Tienda encargada de vender Pokémon y armas.
 * Las fábricas internas se marcan como transient para que no se serialicen.
 */
public class Tienda implements Serializable {
    private static final long serialVersionUID = 1L;

    // Las fábricas no se serializan
    private transient ArmaFactory armaFactory;
    private transient PokemonFactory pokemonFactory;

    public Tienda() {
        this.armaFactory = new ArmaFactory();
        this.pokemonFactory = new PokemonFactory();
    }

    /**
     * Permite a un entrenador comprar un arma para uno de sus Pokémons.
     *
     * @param tipo   Tipo de arma a comprar
     * @param e      Entrenador que realiza la compra
     * @param nombre Nombre del Pokémon que usará el arma
     * @throws CompraImposibleException   Si el entrenador no tiene suficientes créditos
     * @throws TipoDesconocidoException   Si el tipo de arma no es reconocido
     * @throws PokemonNoPuedeUsarArmaE    Si el Pokémon no puede usar el arma
     * @throws PokemonNoExisteException   Si el Pokémon no existe en el entrenador
     */
    public void comprarArma(String tipo, Entrenador e, String nombre)
            throws CompraImposibleException, TipoDesconocidoException, PokemonNoPuedeUsarArmaE, PokemonNoExisteException {
        assert tipo != null && !tipo.isEmpty() : "El tipo de arma no puede ser nulo o vacío";
        assert e != null : "El entrenador no puede ser nulo";
        assert nombre != null && !nombre.isEmpty() : "El nombre del Pokémon no puede ser nulo o vacío";

        // Buscar el Pokémon en el entrenador
        Pokemon p = e.buscaPokemon(nombre);
        // Obtener el arma de la fábrica (puede lanzar TipoDesconocidoException)
        armas.Arma a = armaFactory.getArma(tipo);
        // Verificar créditos
        double costoArma = a.getCosto();
        if (e.getCreditos() < costoArma) {
            throw new CompraImposibleException(e.getCreditos(), costoArma);
        }
        // Asignar el arma y descontar créditos
        p.setArma(a);
        e.subCreditos(costoArma);

        assert e.getCreditos() >= 0 : "Los créditos del entrenador no pueden ser negativos";
    }

    /**
     * Permite a un entrenador comprar un Pokémon de un tipo específico.
     *
     * @param e      Entrenador que realiza la compra
     * @param tipo   Tipo de Pokémon a comprar
     * @param nombre Nombre del Pokémon a comprar
     * @throws CompraImposibleException If el entrenador no tiene suficientes créditos
     * @throws TipoDesconocidoException If el tipo de Pokémon no es reconocido
     */
    public void compraPokemon(Entrenador e, String tipo, String nombre)
            throws CompraImposibleException, TipoDesconocidoException {
        assert e != null : "El entrenador no puede ser nulo";
        assert tipo != null && !tipo.isEmpty() : "El tipo de Pokémon no puede ser nulo o vacío";
        assert nombre != null && !nombre.isEmpty() : "El nombre del Pokémon no puede ser nulo o vacío";

        // Crear el Pokémon con la fábrica (puede lanzar TipoDesconocidoException)
        Pokemon p = pokemonFactory.getPokemon(tipo, nombre);
        double costoPoke = p.getCosto();
        if (e.getCreditos() < costoPoke) {
            throw new CompraImposibleException(e.getCreditos(), costoPoke);
        }
        // Agregar el Pokémon al entrenador y descontar créditos
        try {
            e.putPokemon(p);
        } catch (excepciones.NombreUtilizadoException ex) {
            // Si el nombre ya existe, mostramos mensaje y salimos
            System.out.println("El nombre " + ex.getNombre() + " ya está siendo utilizado");
            return;
        }
        e.subCreditos(costoPoke);

        assert e.getCreditos() >= 0 : "Los créditos del entrenador no pueden ser negativos";
    }

    /**
     * Tras la deserialización, volvemos a instanciar las fábricas marcadas como transient.
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
