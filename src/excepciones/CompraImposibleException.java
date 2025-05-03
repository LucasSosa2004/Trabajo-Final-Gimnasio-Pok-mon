package excepciones;

public class CompraImposibleException extends Exception{
	private double creditos, costo;
	
	/**
	 * Constructor de la excepcion CompraImposibleException.
	 * 
	 * @param creditos Creditos del entrenador.
	 * @param costo Costo del objeto a comprar.
	 */
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
