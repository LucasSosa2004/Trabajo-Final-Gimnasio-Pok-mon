package excepciones;

import java.io.Serializable;

public class CompraImposibleException extends Exception implements Serializable{
	private static final long serialVersionUID = 1L;
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
