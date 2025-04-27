package modelo;

public class ArmaFactory {
	
	  public static Arma crearArma(String tipo) {
	        switch (tipo.toUpperCase()) {
	            case "ESPADA":
	                return new Espada();
	            case "HACHA":
	                return new Hacha();
	            default:
	                throw new IllegalArgumentException("Tipo de arma desconocido: " + tipo);
	        }
	  }
}
