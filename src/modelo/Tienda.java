package modelo;

import java.util.*;

public class Tienda {

	public void comprarArma(String nombre,Entrenador e, Pokemon p) throws CompraImposibleException{
		Arma arma = ArmaFactory.crearArma(nombre); // Revisar
		if(p.puedeTenerArma()) {
			if(e.getCreditos() < arma.getCosto())
				throw new CompraImposibleException(e.getCreditos(),arma.getCosto());
			else
				p.setArma(arma);
		}
	}
	
}
