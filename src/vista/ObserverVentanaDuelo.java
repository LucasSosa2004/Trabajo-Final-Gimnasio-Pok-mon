package vista;

import java.util.Observable;
import java.util.Observer;

import modelo.Duelo;


public class ObserverVentanaDuelo implements Observer {
	private IVista ventana;
	private Duelo observable;
	
	public ObserverVentanaDuelo(Duelo observable, IVista ventana)
	{
		super();
		this.observable = observable;
		this.ventana=ventana;
		this.observable.addObserver(this);
	}
    @Override
    public void update(Observable o, Object informacion) {
        if (o instanceof Duelo && informacion instanceof String) {
            final String resultado = (String) informacion;
            javax.swing.SwingUtilities.invokeLater(() -> mostrarResultado(resultado));
        }
    }

    public void mostrarResultado(String informacion) {
        this.ventana.setTextConsola(informacion);
    }
}