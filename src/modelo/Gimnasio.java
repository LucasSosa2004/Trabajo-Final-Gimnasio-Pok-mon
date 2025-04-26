package modelo;

import java.util.ArrayList;

public class Gimnasio {
	private ArrayList<Entrenador> entrenadores = new ArrayList<>();
	private ArrayList<Arena> arenas = new ArrayList<>();
	private Tienda tienda;
	
	public void addEntrenador(Entrenador e) {
		this.entrenadores.add(e);
	}
	public ArrayList<Entrenador> getEntrenadores() {
		return this.entrenadores;
	}
	public void addArena(Arena a) {
		this.arenas.add(a);
	}
	public ArrayList<Arena> getArenas(){
		return this.arenas;
	}
	
	
}
