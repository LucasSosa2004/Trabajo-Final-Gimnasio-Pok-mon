package modelo;

public abstract class Arma implements IHostil, Cloneable{
	
	protected int costo;

	@Override
	public void atacar(Pokemon adversario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		 throw new InternalError();
	}
	
	
}
