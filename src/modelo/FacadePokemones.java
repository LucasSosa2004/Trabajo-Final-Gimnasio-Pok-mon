package modelo;

import armas.ArmaFactory;
import pokemones.PokemonFactory;

public class FacadePokemones {
    private Gimnasio gimnasio;
    private SistemaPelea sistemaPelea;
    private static FacadePokemones instancia;
    private PokemonFactory pokFac;
    private ArmaFactory armFac;

    private FacadePokemones() {
        this.gimnasio = Gimnasio.getInstancia();
        this.sistemaPelea = SistemaPelea.getInstancia();
        this.armFac = new ArmaFactory();
        this.pokFac = new PokemonFactory();
    }

    public static FacadePokemones getInstancia() {
        if (instancia==null) {
            instancia = new FacadePokemones();
        }
        return instancia;
    }

    public Gimnasio getGimnasio() {
        return gimnasio;
    }
    public SistemaPelea getSistemaPelea() {
        return sistemaPelea;
    }
    public PokemonFactory getPokFact() {
        return this.pokFac;
    }
    public ArmaFactory getArmFact() {
        return this.armFac;
    }


}

