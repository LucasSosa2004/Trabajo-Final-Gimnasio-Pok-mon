package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;

public class PokemonFuego extends Pokemon {

	public PokemonFuego(String nombre) {
		this.nombre=nombre;
		this.costo=120;
		this.vitalidad=530;
		this.escudo=200;
		this.fuerza=80;
		this.experiencia=0;
	}
	
	@Override
	public void atacar(Pokemon adversario) {
		adversario.recibeDano(this.fuerza*.2);
		if (fuerza*0.75>10) {
			this.fuerza*=.75;
		}
		else
			this.fuerza=10;
	
	}
	
	public void recibeDano(double danoRecibido) {
		if(this.escudo!=0) {
			if(danoRecibido*.75>this.escudo) {
				danoRecibido-=this.escudo;
				this.escudo=0;
			}
			else {
				this.escudo-=danoRecibido*0.75;
				danoRecibido*=.25;		
			}
		}
		this.vitalidad-=danoRecibido;
	}
	
	@Override
	public void recargar() {
		this.vitalidad=500*(.8+0.05*this.experiencia);
		this.escudo=300*(.8+0.05*this.experiencia);
		this.fuerza=150*(.8+0.05*this.experiencia);
		
	}
	
	
	@Override
	public void recibeTormenta() {
		this.escudo*=.8;
		this.fuerza*=.8;
	}
	
	@Override
	public void recibeViento() {
		this.vitalidad*=.5;
	}
	
	@Override
	public void recibeNiebla() {
		this.fuerza*=.5;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public String toString() {
		return "PokemonFuego []"+super.toString();
	}
	
	public void setArma(Arma a) throws PokemonNoPuedeUsarArmaE{
		throw new PokemonNoPuedeUsarArmaE(this.nombre); 
	}
}
