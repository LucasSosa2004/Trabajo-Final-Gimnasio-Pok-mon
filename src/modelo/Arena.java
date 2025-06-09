package modelo;

import java.io.Serializable;

public class Arena implements Serializable {
	private static final long serialVersionUID = 1L;
    private int id;


    public Arena(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
} 