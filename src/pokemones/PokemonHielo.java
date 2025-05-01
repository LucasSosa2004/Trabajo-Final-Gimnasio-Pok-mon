package pokemones;

import armas.Arma;

public class PokemonHielo extends Pokemon {

	
	public PokemonHielo(String nombre) {
		this.nombre=nombre;
		this.costo=100;
		this.vitalidad=400;
		this.escudo=120;
		this.fuerza=100;
		this.experiencia=0;
	}
	
	@Override
	public void atacar(Pokemon adversario) {
		adversario.recibeDano(this.fuerza*.15);
		this.fuerza*=.95;
	
	}
	public void recibeDano(double danoRecibido) {
		if(this.escudo-danoRecibido<0) {
			danoRecibido-=this.escudo;
			this.escudo=0;
			this.vitalidad-=danoRecibido;
		}
		else {
			this.escudo-=danoRecibido;
		}
		
	}
	
	@Override
	public void recargar() {
		this.vitalidad+=200;
		this.escudo+=100;
		this.fuerza+=100;
	
	}
	
	
	@Override
	public void recibeTormenta() {
		this.escudo*=.2;
	}
	
	@Override
	public void recibeViento() {
		this.vitalidad*=.8;
		this.fuerza*=.8;
	}
	
	@Override
	public void recibeNiebla() {
		this.vitalidad*=0.4;
	}

	@Override
	public Object clone()  {
		try {
			PokemonHielo nPok=null;
			nPok = (PokemonHielo)super.clone();
			return nPok;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	@Override
	public String toString() {
		return "PokemonHielo []"+super.toString();
	}

	public void setArma(Arma a) throws PokemonNoPuedeUsarArmaE{
		throw new PokemonNoPuedeUsarArmaE(this.nombre); 
	}
	
}
