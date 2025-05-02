package armas;

import excepciones.TipoDesconocidoException;

public class ArmaFactory {
	
	  public Arma getArma(String tipo)throws TipoDesconocidoException{
		  tipo=tipo.toUpperCase();
	        switch (tipo) {
	            case "ESPADA":
	                return new Espada();
	            case "HACHA":
	                return new Hacha();
	            default:
	            	throw new TipoDesconocidoException(tipo);
	        }
	  }
}
