package modelo;

public class CompraImposibleException extends Exception{
	private double creditos, costo;
	
	public CompraImposibleException(double creditos, double costo) {
		super("Monto insuficiente");
		this.creditos = creditos;
		this.costo = costo;
		
	}

	public double getCreditos() {
		return this.creditos;
	}

	
	public double getCosto() {
		return this.costo;
	}
	
}
