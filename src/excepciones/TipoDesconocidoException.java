package excepciones;

public class TipoDesconocidoException extends Exception {
	protected String tipo;

	/**
	 * Constructor de la excepcion TipoDesconocidoException.
	 * 
	 * @param tipo El tipo de pokemon que lanza la excepcion.
	 */
	public TipoDesconocidoException(String tipo) {
		super("Tipo ingresado desconocido");
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
	
	
	

}
