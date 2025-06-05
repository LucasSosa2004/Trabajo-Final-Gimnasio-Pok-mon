package modelo;

public class ArenaMedio extends ArenaDificultadDecorator {
    public ArenaMedio(ArenaLogica base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return base.getNombre() + " (Medio)";
    }

    @Override
    public String getDetalle() {
        return base.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(base.getPremio() * 1.2);
    }
}
