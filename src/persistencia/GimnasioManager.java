package persistencia;

import java.io.*;

import modelo.Gimnasio;
import modelo.SistemaPelea;
import modelo.EtapaTorneo;

/**
 * GimnasioManager se encarga de guardar y cargar el estado completo:
 * - La instancia Singleton de Gimnasio
 * - La instancia Singleton de SistemaPelea
 * - La instancia de EtapaTorneo (control de etapas)
 */
public class GimnasioManager {
    private static final String ARCHIVO_ESTADO = "gimnasio.dat";

    public GimnasioManager() {
        // Constructor vacío
    }

    /**
     * Guarda en disco los objetos: Gimnasio.instancia(), SistemaPelea.instancia() y la etapaActual.
     */
    public void guardarEstado(Gimnasio gimnasio, SistemaPelea sistemaPelea, EtapaTorneo etapa) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_ESTADO))) {
            oos.writeObject(gimnasio);
            oos.writeObject(sistemaPelea);
            oos.writeObject(etapa);
        } catch (IOException e) {
            System.err.println("Error al guardar el estado: " + e.getMessage());
        }
    }

    /**
     * Carga desde disco las mismas tres entidades. Si no existe el archivo, retorna null.
     * Además reasigna los singletons de Gimnasio y SistemaPelea.
     *
     * @return la EtapaTorneo cargada (o null si no había archivo)
     */
    @SuppressWarnings("unchecked")
    public EtapaTorneo cargarEstado() {
        File f = new File(ARCHIVO_ESTADO);
        if (!f.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Gimnasio gimCargado = (Gimnasio) ois.readObject();
            SistemaPelea spCargado = (SistemaPelea) ois.readObject();
            EtapaTorneo etapaCargada = (EtapaTorneo) ois.readObject();

            // Reasignar los singletons
            Gimnasio.setInstancia(gimCargado);
            SistemaPelea.setInstancia(spCargado);

            return etapaCargada;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el estado: " + e.getMessage());
            return null;
        }
    }
}
