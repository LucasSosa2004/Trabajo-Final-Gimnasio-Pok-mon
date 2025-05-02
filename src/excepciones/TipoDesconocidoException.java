package excepciones;

public class TipoDesconocidoException extends Exception {
	protected String tipo;

	public TipoDesconocidoException(String tipo) {
		super("Tipo ingresado desconocido");
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
	
	
	

}
