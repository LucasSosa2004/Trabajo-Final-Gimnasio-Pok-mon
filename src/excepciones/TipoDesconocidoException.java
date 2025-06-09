package excepciones;

import java.io.Serializable;

public class TipoDesconocidoException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
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
