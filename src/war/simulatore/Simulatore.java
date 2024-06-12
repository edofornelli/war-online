package war.simulatore;

import static war.costanti.CostantiSimulazione.DIMENSIONE;
import static war.costanti.CostantiSimulazione.DURATA_SIMULAZIONE;
import static war.costanti.CostantiSimulazione.NUMERO_PER_TIPOLOGIA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import war.Campo;
import war.Coordinate;
import war.Proiettile;
import war.costanti.CostantiSimulazione;
import war.gui.GUI;
import war.tank.*;

public class Simulatore {

	final private Campo campo;

	private int passo;

	private GUI gui;

	final private GestoreCollisioni gestoreCollisioni;

	final private GestoreLogicaTank gestoreLogicaTank;
	
	public Simulatore() {
		this(DIMENSIONE);
	}

	public Simulatore(int dim) {
		this.campo = new Campo(dim);
		this.gestoreCollisioni = new GestoreCollisioni(this.campo);
		this.gestoreLogicaTank = new GestoreLogicaTank(this.campo);
		this.passo = 0;
		this.creaTank();
	}

	private void creaTank() {
		/* DA AGGIORNARE (VEDI DOMANDA 2, ed anche 7) */
		for(int i=0; i<NUMERO_PER_TIPOLOGIA; i++) {
			this.campo.getTank().add(new Explorer(this.getCampo()));
			this.campo.getTank().add(new Shooter(this.getCampo()));

		}
	}
	
	public List<Tank> getTank() {
		return this.campo.getTank();
	}

	public void setGUI(GUI gui) {
		this.gui = gui;
	}

	public Campo getCampo() {
		return this.campo;
	}

	public int getPasso() {
		return this.passo;
	}

	public void simula() {

		for(this.passo=0; this.passo<DURATA_SIMULAZIONE; this.passo++) {
			simulaTank();
			
			simulaProiettili();			

			this.gestoreCollisioni.gestisciCollisioni();
			
			simulaEsplosioni();
			
			simulaTracceCingolati();
			
			aggiornaStatistiche();

			pausa();
		}
		/**
		 * Termina la simulazione corrente stampando le statistiche finali
		 */
		new Statistiche().stampaStatisticheFinali(this.getCampo());

		terminaSimulazione();
	}

	
	private void simulaEsplosioni() {
		for(Coordinate c : this.getCampo().getCoordinateEsplosioni())
			this.campo.incEsplosione(c);
	}

	private void simulaTank() {
		Collections.shuffle(this.getTank());
		for(Tank tank : this.getTank()) {
			this.gestoreLogicaTank.simula(tank, this.getPasso());
		}
	}

	private void simulaProiettili() {
		final ArrayList<Proiettile> list = new ArrayList<>(this.getCampo().getProiettili());
		Collections.shuffle(list);
		for(Proiettile p : list) {
			p.setPosizione(p.getPosizione().trasla(p.getDirezione()));
		}
	}

	private void simulaTracceCingolati() {
		this.campo.dissipazioneTracce();
	}
	private void aggiornaStatistiche() {
		this.gui.riportaSommarioNelTitolo(this.passo, campo);
	}

	private void pausa() {
		this.updateGui();

		try {
			Thread.sleep(CostantiSimulazione.RITMO);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void updateGui() {
		SwingUtilities.invokeLater( new Runnable() {			
			@Override
			public void run() {
				Simulatore.this.gui.repaint();
			}
		});
	}
		
	
	/**
	 * Termina la partita corrente
	 */
	public void terminaSimulazione() {
		System.exit(-1);
	}

}
