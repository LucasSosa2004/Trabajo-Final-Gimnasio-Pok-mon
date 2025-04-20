package prueba;

import modelo.*;



public class Prueba {

	public static void main(String[] args) {
		Gimnasio gimnasio = new Gimnasio();
		PokemonFactory factoria= new PokemonFactory();
		Entrenador e1,e2;
		Arena arena;
		
		


		e1=gimnasio.getEntrenador("ASH");
		e2=gimnasio.getEntrenador("JESUS");
				
		
		e1.setEquipo("pikachu","magikarp","charizard");
		e2.setEquipo("vaporeon","magikarp2","charizard3");

		
		
		arena=gimnasio.IniciaArena(e1,e2);
		
		arena.iniciarDuelo();
		

		

		
	}

}
