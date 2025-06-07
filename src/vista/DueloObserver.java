package vista;

import interfaces.IDueloObserver;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Implementación de IDueloObserver que muestra los eventos del duelo
 * en un componente JTextArea de la interfaz gráfica.
 */
public class DueloObserver implements IDueloObserver {
    private JTextArea textArea;
    
    public DueloObserver(JTextArea textArea) {
        this.textArea = textArea;
    }
    
    private void appendText(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(text + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }
    
    @Override
    public void notificarInicioDuelo(String entrenador1, String entrenador2, String arena) {
        appendText("¡INICIO DEL DUELO!");
        appendText("Entrenador 1: " + entrenador1);
        appendText("Entrenador 2: " + entrenador2);
        appendText("Arena: " + arena);
        appendText("----------------------------------------");
    }
    
    @Override
    public void notificarAtaque(String atacante, String defensor, double daño) {
        appendText(atacante + " ataca a " + defensor + " causando " + String.format("%.2f", daño) + " de daño!");
    }
    
    @Override
    public void notificarPokemonDerrotado(String pokemon, String entrenador) {
        appendText("¡" + pokemon + " de " + entrenador + " ha sido derrotado!");
        appendText("----------------------------------------");
    }
    
    @Override
    public void notificarFinDuelo(String ganador, String perdedor) {
        appendText("\n¡FIN DEL DUELO!");
        appendText("¡" + ganador + " es el ganador!");
        appendText("¡" + perdedor + " ha sido derrotado!");
        appendText("========================================\n");
    }
} 