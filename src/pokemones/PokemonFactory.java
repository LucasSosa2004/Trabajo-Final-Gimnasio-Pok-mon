package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;
import excepciones.TipoDesconocidoException;

public class PokemonFactory {

    public Pokemon getPokemon(String tipo, String nombre) throws TipoDesconocidoException {
        assert tipo != null && !tipo.isEmpty() : "El tipo no puede ser nulo o vacio";
        assert nombre != null && !nombre.isEmpty() : "El nombre no puede ser nulo o vacio";
        tipo = tipo.toUpperCase();
        nombre = nombre.toUpperCase();
        switch (tipo) {
            case "AGUA":
                return new PokemonAgua(nombre);
            case "FUEGO":
                return new PokemonFuego(nombre);
            case "HIELO":
                return new PokemonHielo(nombre);
            case "PIEDRA":
                return new PokemonPiedra(nombre);
            default:
                throw new TipoDesconocidoException(tipo);
        }
    }

    public Pokemon getPokemon(String tipo, String nombre, Arma arma)
            throws TipoDesconocidoException, PokemonNoPuedeUsarArmaE {
        assert tipo != null && !tipo.isEmpty() : "El tipo no puede ser nulo o vacio";
        assert nombre != null && !nombre.isEmpty() : "El nombre no puede ser nulo o vacio";
        assert arma != null : "El arma no puede ser nula";
        tipo = tipo.toUpperCase();
        nombre = nombre.toUpperCase();
        switch (tipo) {
            case "PIEDRA":
                return new PokemonPiedra(nombre, arma);
            default: {
                if (!tipo.equals("FUEGO") && !tipo.equals("AGUA") && !tipo.equals("HIELO")) {
                    throw new TipoDesconocidoException(tipo);
                } else {
                    throw new PokemonNoPuedeUsarArmaE(nombre);
                }
            }
        }
    }

    @Override
    public String toString() {
        String resultado = "PokemonFactory []";
        assert resultado != null : "El resultado de toString no puede ser nulo";
        return resultado;
    }
}
