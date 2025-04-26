package modelo;

import java.util.*;

public class Tienda {
	private HashMap<String,Arma> armas = new HashMap<String,Arma>();
	private ArrayList<Pokemon> pokemones = new ArrayList<>();
	
	public Arma comprarArma(String nombre,Entrenador e, Pokemon p) throws CompraImposibleException{
		Arma arma = armas.get(nombre);
		if(e.getCreditos() > arma.getCosto())
			throw new CompraImposibleException(e.getCreditos(),arma.getCosto());
		return arma;
	}
	//como aceptan el arma los pokemones
	
}
