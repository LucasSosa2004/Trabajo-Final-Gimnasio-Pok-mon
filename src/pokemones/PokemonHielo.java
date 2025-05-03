package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;

public class PokemonHielo extends Pokemon {

    public PokemonHielo(String nombre) {
        this.nombre = nombre;
        this.costo = 100;
        this.vitalidad = 400;
        this.escudo = 120;
        this.fuerza = 100;
        this.experiencia = 0;
        assert this.vitalidad > 0 : "La vitalidad debe ser mayor a 0";
    }

    public void setArma(Arma a) throws PokemonNoPuedeUsarArmaE {
        throw new PokemonNoPuedeUsarArmaE(this.nombre);
    }

    @Override
    public void atacar(Pokemon adversario) {
        assert adversario != null : "El adversario no puede ser nulo";
        adversario.recibeDano(this.fuerza * 0.15);
        this.fuerza *= 0.95;
        assert this.fuerza >= 0 : "La fuerza no puede ser negativa";
    }

    @Override
    public void recibeDano(double danoRecibido) {
        assert danoRecibido >= 0 : "El dano recibido no puede ser negativo";
        if (this.escudo - danoRecibido < 0) {
            danoRecibido -= this.escudo;
            this.escudo = 0;
            this.vitalidad -= danoRecibido;
        } else {
            this.escudo -= danoRecibido;
        }
        assert this.vitalidad >= 0 : "La vitalidad no puede ser negativa";
    }

    @Override
    public void recargar() {
        this.vitalidad += 200;
        this.escudo += 100;
        this.fuerza += 100;
    }

    @Override
    public void recibeTormenta() {
        this.escudo *= 0.2;
    }

    @Override
    public void recibeViento() {
        this.vitalidad *= 0.8;
        this.fuerza *= 0.8;
    }

    @Override
    public void recibeNiebla() {
        this.vitalidad *= 0.4;
    }

    @Override
    public Object clone() {
        try {
            PokemonHielo nPok = null;
            nPok = (PokemonHielo) super.clone();
            return nPok;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "PokemonHielo []" + super.toString();
    }
}
