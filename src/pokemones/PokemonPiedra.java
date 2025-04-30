package pokemones;

import armas.Arma;

public class PokemonPiedra extends Pokemon {

	private Arma arma;
	
	
	public PokemonPiedra(String nombre) {
		this.nombre=nombre;
		this.costo=200;
		this.vitalidad=600;
		this.escudo=300;
		this.fuerza=150;
		this.experiencia=0;
	}

	@Override
	public void atacar(Pokemon adversario) {
		if (arma!=null)
			this.arma.atacar(adversario);
		else {
			adversario.recibeDano(this.fuerza*.15);
			this.fuerza*=.95;
		}

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
		this.vitalidad=500*(.8+0.1*this.experiencia);
		this.escudo=300*(.8+0.1*this.experiencia);
		this.fuerza=150*(.8+0.1*this.experiencia);
		
		if(this.vitalidad>1000) {
			this.vitalidad=1000;
		}
		if(this.escudo>600) {
			this.escudo=600;
		}
		if(this.fuerza>300) {
			this.fuerza=300;
		}
	}


	@Override
	public void recibeTormenta() {
		this.escudo=0;
		this.fuerza*=0.7;
	}

	@Override
	public void recibeViento() {
		this.vitalidad*=.25;
	}

	@Override
	public void recibeNiebla() {
		this.fuerza*=0.4;
	}
	
	@Override
	public Object clone()  {
		try {
			PokemonPiedra nPok=null;
			nPok = (PokemonPiedra)super.clone();
			if(nPok.arma != null)
				nPok.arma= (Arma) arma.clone();
			return nPok;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	@Override
	public String toString() {
		return "PokemonPiedra []"+super.toString();
	}
	
	public Arma getArma() {
		return this.arma;
	}

	public void setArma(Arma a) {
		this.arma = a;
	}
	
}
