package modelo;

public class ArenaDificil extends ArenaDificultadDecorator {
    public ArenaDificil(ArenaLogica base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return base.getNombre() + " (Difícil)";
    }

    @Override
    public String getDetalle() {
        return base.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(base.getPremio() * 1.5);
    }
}
