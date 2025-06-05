package modelo;

public class ArenaFacil extends ArenaDificultadDecorator {
    public ArenaFacil(ArenaLogica base) {
        super(base);
    }

    @Override
    public String getNombre() {
        return base.getNombre() + " (Fácil)";
    }

    @Override
    public String getDetalle() {
        return base.getDetalle();
    }

    @Override
    public int getPremio() {
        return (int)(base.getPremio() * 0.9);
    }
}
