package modelo;

public abstract class ArenaDificultadDecorator extends ArenaLogica {
    protected ArenaLogica base;

    public ArenaDificultadDecorator(ArenaLogica base) {
        this.base = base;
    }
}