package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;

public class PokemonAgua extends Pokemon {
	
	

	public PokemonAgua(String nombre) {
		super();
		this.nombre=nombre;
		this.costo=100;
		this.vitalidad=500;
		this.escudo=100;
		this.fuerza=120;
		this.experiencia=0;
		
	}
	
	
	public void setArma(Arma a) throws PokemonNoPuedeUsarArmaE{
		throw new PokemonNoPuedeUsarArmaE(this.nombre); 
	}

	@Override
	public void atacar(Pokemon adversario) {
		adversario.recibeDano(this.fuerza*0.1);

	}
	@Override
	public void recibeDano(double danoRecibido) {
		if(this.escudo>0) {
			if(danoRecibido*.5>this.escudo) {
				danoRecibido-=this.escudo;
				this.escudo=0;
			}
			else {
				danoRecibido*=.5;
				this.escudo-=danoRecibido;
			}
		}
		this.vitalidad-=danoRecibido;
	}

	@Override
	public void recargar() {
		this.vitalidad=500;
		this.escudo=100;
		this.fuerza=120;
		
	}


	@Override
	public void recibeTormenta() {
		this.escudo*=.1;
	}

	@Override
	public void recibeViento() {
		this.fuerza*=.9;
		this.vitalidad*=.9;
	}

	@Override
	public void recibeNiebla() {
		this.vitalidad*=.5;
	}
	@Override
	public Object clone()  {
		try {
			PokemonAgua nPok=null;
			nPok = (PokemonAgua)super.clone();
			return nPok;
		} catch (CloneNotSupportedException e) {
			//NUNCA ENTRA
			return null;
		}
	}

	@Override
	public String toString() {
		return "PokemonAgua []"+super.toString();
	}



}
