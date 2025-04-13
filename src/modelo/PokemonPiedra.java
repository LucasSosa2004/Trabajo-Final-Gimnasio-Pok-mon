package modelo;

public class PokemonPiedra extends Pokemon {

	private Arma arma;
	
	@Override
	public void atacar(Pokemon adversario) {
		if (arma!=null)
			this.arma.atacar(adversario);
		else
			adversario.recibeDano(100);

	}

	@Override
	public double getCosto() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double recibeTormenta() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double recibeViento() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double recibeNiebla() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCategoria() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void recibeDano(int danoRecibido) {
		// TODO Auto-generated method stub
		this.vitalidad-=danoRecibido;
	}

}
