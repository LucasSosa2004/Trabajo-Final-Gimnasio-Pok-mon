package armas;

import excepciones.TipoDesconocidoException;

public class ArmaFactory {
	
	/**
	 * Metodo que devuelve un objeto de tipo Arma segun el tipo especificado.
	 * @param tipo Tipo de arma a crear.
	 * @return Un objeto de tipo Arma.
	 * @throws TipoDesconocidoException Si el tipo de arma no es reconocido.
	 * **/
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
