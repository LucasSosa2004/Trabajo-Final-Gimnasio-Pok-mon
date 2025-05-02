package armas;

import excepciones.TipoDesconocidoException;

public class ArmaFactory {
	
	  public Arma crearArma(String tipo)throws TipoDesconocidoException{
	        switch (tipo.toUpperCase()) {
	            case "ESPADA":
	                return new Espada();
	            case "HACHA":
	                return new Hacha();
	            default:
	            	throw new TipoDesconocidoException(tipo);
	        }
	  }
}
