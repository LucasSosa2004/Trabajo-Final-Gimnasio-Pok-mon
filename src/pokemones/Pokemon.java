package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;
import interfaces.IClasificable;
import interfaces.IHechizable;
import interfaces.IHostil;
import interfaces.IValuable;

public abstract class Pokemon implements Cloneable, IHostil, IValuable, IHechizable, IClasificable {
    public String nombre;
    public double vitalidad;
    public double escudo;
    public double fuerza;
    public double costo;
    public int experiencia;

    /**
     * Invariante de clase:
     * - La vitalidad, escudo, fuerza y costo deben ser mayores o iguales a 0.
     */
    private void verificarInvariante() {
        assert vitalidad >= 0 : "La vitalidad no puede ser negativa";
        assert escudo >= 0 : "El escudo no puede ser negativo";
        assert fuerza >= 0 : "La fuerza no puede ser negativa";
        assert costo >= 0 : "El costo no puede ser negativo";
    }

    public abstract void recibeDano(double danoRecibido);
    public abstract void recargar();  
    public abstract void setArma(Arma a) throws PokemonNoPuedeUsarArmaE;

    public int getCategoria() {
        return this.experiencia;
    }

    /**
     * Incrementa la experiencia del Pokemon en 1.
     * 
     * Postcondicion:
     * - La experiencia del Pokemon aumenta en 1.
     */
    public void recibeExp() {
        this.experiencia++;
        assert this.experiencia > 0 : "La experiencia debe ser mayor a 0";
    }

    public double getCosto() {
        return this.costo;
    }

    public double getVitalidad() {
        return this.vitalidad;
    }

    public String getNombre() {
        return this.nombre;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object copia = super.clone();
        assert copia != null : "La copia del Pokemon no puede ser nula";
        return copia;
    }

    @Override
    public String toString() {
        return " [nombre=" + nombre + ", vitalidad=" + vitalidad + ", escudo=" + escudo + ", fuerza=" + fuerza
                + ", costo=" + costo + ", experiencia=" + experiencia + "]";
    }
}
