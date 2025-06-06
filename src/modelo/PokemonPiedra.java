package modelo;

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
	protected void recibeDano(double danoRecibido) {
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
	protected void recargar() {
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
		PokemonPiedra nPok=null;
		try {
			nPok = (PokemonPiedra)super.clone();
			nPok.arma= (Arma) arma.clone();
		} catch (CloneNotSupportedException e) {
			
		}
		return nPok;
	}
	@Override
	public String toString() {
		return "PokemonPiedra []"+super.toString();
	}
	
	public Arma getArma() {
		return this.arma;
	}

	@Override
	public boolean puedeTenerArma() {
		return true;
	}
	
	@Override
	public void setArma(Arma arma) {
		this.arma = arma;
	}
}
