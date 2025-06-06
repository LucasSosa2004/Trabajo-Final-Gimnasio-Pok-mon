package armas;

import java.util.Random;
import java.io.Serializable;
import pokemones.Pokemon;

public class Hacha extends Arma implements Serializable {
	private static final long serialVersionUID = 1L;

    public Hacha() {
        super();
        this.costo = 80;
        assert this.costo > 0 : "El costo debe ser mayor a 0";
    }

    @Override
    public void atacar(Pokemon adversario) {
        assert adversario != null : "El adversario no puede ser nulo";
        Random random = new Random();
        double dano = random.nextDouble(50, 150);
        adversario.recibeDano(dano);
        assert dano >= 50 && dano <= 150 : "El dano debe estar entre 50 y 150";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public String toString() {
        String resultado = "Hacha []" + super.toString();
        return resultado;
    }
}
