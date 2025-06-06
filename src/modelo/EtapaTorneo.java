package modelo;

import java.io.Serializable;

/**
 * EtapaTorneo controla en qué fase estamos: registro, inscripción, en progreso o finalizado.
 */
public class EtapaTorneo implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Etapa {
        REGISTRO_ENTRENADORES,
        INSCRIPCION_TORNEO,
        EN_PROGRESO,
        FINALIZADO
    }

    private Etapa etapaActual;

    public EtapaTorneo() {
        this.etapaActual = Etapa.REGISTRO_ENTRENADORES;
    }

    /**
     * Avanza de etapa según el siguiente orden:
     * REGISTRO_ENTRENADORES → INSCRIPCION_TORNEO → EN_PROGRESO → FINALIZADO.
     */
    public void avanzarEtapa() {
        switch (etapaActual) {
            case REGISTRO_ENTRENADORES:
                etapaActual = Etapa.INSCRIPCION_TORNEO;
                break;
            case INSCRIPCION_TORNEO:
                etapaActual = Etapa.EN_PROGRESO;
                break;
            case EN_PROGRESO:
                etapaActual = Etapa.FINALIZADO;
                break;
            default:
                break;
        }
    }

    /**
     * Indica si se puede guardar el estado en la etapa actual:
     * Solo en REGISTRO_ENTRENADORES o FINALIZADO.
     */
    public boolean puedeGuardarEstado() {
        return etapaActual == Etapa.REGISTRO_ENTRENADORES || etapaActual == Etapa.FINALIZADO;
    }

    public Etapa getEtapaActual() {
        return etapaActual;
    }

    @Override
    public String toString() {
        return "EtapaTorneo [etapaActual=" + etapaActual + "]";
    }
}
