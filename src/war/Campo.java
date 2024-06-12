package war;

import static war.costanti.CostantiSimulazione.DELTA_TRACCIA;
import static war.costanti.CostantiSimulazione.DIMENSIONE;
import static war.costanti.CostantiSimulazione.DURATA_DELL_ESPLOSIONE;
import static war.costanti.CostantiSimulazione.LUNGHEZZA_OSTACOLI;
import static war.costanti.CostantiSimulazione.PERCENTUALE_OSTACOLI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import war.simulatore.GeneratoreCasuale;
import war.tank.Tank;
import war.tank.Factory;


/**
 * 
 */
public class Campo {

	public static Campo creaCampoSenzaOstacoli(int dim) {
		final Campo soloBordi = new Campo(dim);
		soloBordi.ostacoli.clear();
		soloBordi.fortini.clear();
		return soloBordi;
	}

	final private List<Tank> tank;

	final private Map<Class<? extends Tank>,Fortino> fortini;

	final private Set<Proiettile> proiettili; // proiettili gia' lanciati

	final private Map<Coordinate, Integer> pos2traccia;       // pos to traccia dei cingolati
	
	final private Map<Coordinate, Integer> pos2esplosione;    // pos to esplosione in corso
	
	final private Set<Coordinate> ostacoli;

	final private int dimensione;

	/**
	 * Crea un campo con le dimensioni di default
	 */
	public Campo() {
		this(DIMENSIONE);
	}

	/**
	 * Crea un'campo (quadrato) delle dimensioni date.
	 * @param dim - dimensione
	 */
	public Campo(int dim) {
		this.tank = new ArrayList<>();
		this.proiettili = new HashSet<>();
		this.pos2traccia = new HashMap<>();
		this.pos2esplosione = new HashMap<>();
		this.ostacoli = new HashSet<>();
		this.fortini = new HashMap<>();
		this.inizializzaBordi(dim);
		this.inizializzaOstacoli(dim);
		this.inizializzaFortini();
		this.dimensione = dim;
	}
	
	private void inizializzaBordi(int dim) {
		for(int i=0; i<dim; i++) {
			aggiungiOstacolo(0,i);
			aggiungiOstacolo(i,0);
			aggiungiOstacolo(dim-1,i);
			aggiungiOstacolo(i,dim-1);
		}
	}
	

	public int getDimensione() {
		return this.dimensione;
	}

	public List<Tank> getTank() {
		return this.tank;
	}
	
	public Coordinate posizioneCasuale() {
		Coordinate c = null;
		do {
			c = GeneratoreCasuale.posizioneCasuale();
		} while (suOstacolo(c));
		return c;
	}

	private void inizializzaOstacoli(int dim) {
		final double n = (dim*(dim-4)+4)*PERCENTUALE_OSTACOLI/LUNGHEZZA_OSTACOLI;
		for(int i=0; i<Math.round(n); i++) {			
			Coordinate posizione = posizioneCasuale();
			for(int c=0; c<LUNGHEZZA_OSTACOLI; c++) {
				aggiungiOstacolo(posizione);
			    posizione = posizione.trasla(Direzione.casuale());
			}
		}
	}

	public void aggiungiOstacolo(int x, int y) {
		aggiungiOstacolo(new Coordinate(x, y));
	}

	public boolean aggiungiOstacolo(Coordinate c) {
		for(Fortino fortino : this.getFortini())
			if (c.equals(fortino.getPosizione()))
				return false;
		this.ostacoli.add(c);
		return true;
	}

	public Set<Coordinate> getOstacoli() {
		return this.ostacoli;
	}
	
	private void inizializzaFortini() {
		for(Class<? extends Tank> tipo : Factory.getSingleton().getAllTipiDiTank()) {
			final Coordinate libera = posizioneCasuale();
			final Fortino fortino = new Fortino(libera, tipo);
			this.fortini.put(tipo, fortino);
		}
	}

	public Fortino getFortinoByTipo(Class<? extends Tank> tipo) {
		return Objects.requireNonNull(this.fortini.get(tipo), "Tipo "+tipo+" ignoto!");
	}

	public Collection<Fortino> getFortini() {
		return this.fortini.values();
	}

	public boolean suOstacolo(Coordinate pos) {
		return this.getOstacoli().contains(pos);
	}

	public void add(Proiettile proiettile) {
		this.proiettili.add(proiettile);
	}

	public Collection<Proiettile> getProiettili() {
		return this.proiettili;
	}

	public boolean remove(Proiettile p) {
		return this.proiettili.remove(p);
	}

	public Map<Coordinate, Integer> getTraccia() {
		return this.pos2traccia;
	}

	public Integer getTraccia(Coordinate p) {
		return this.pos2traccia.get(p);
	}
	
	public int rilevaTracciaVerso(Coordinate riferimento, Direzione dir) {
		final Coordinate adiacente = riferimento.trasla(dir);
		final Integer traccia = getTraccia(adiacente);
		if (traccia!=null) {
			return traccia;
		} 
		return 0;
	}	

	public void lasciaTraccia(Tank tank) {
		Coordinate p = tank.getPosizione();
		if (suOstacolo(p)) 
			throw new IllegalArgumentException("Un cingolato non puo' attraversare un muro!");
		Integer livello = this.pos2traccia.get(p);
		if (livello==null) livello = Integer.valueOf(0);
		livello = livello + DELTA_TRACCIA;
		this.pos2traccia.put(p, livello);
	}

	public void dissipazioneTracce() {
		final Set<Coordinate> daRimuovere = new HashSet<>();
		for(Coordinate p : this.pos2traccia.keySet()) {
			int vecchioLivello = this.pos2traccia.get(p);
			final int nuovoLivello = vecchioLivello-1;
			if (nuovoLivello<=0)
				daRimuovere.add(p);
			else
				this.pos2traccia.put(p, nuovoLivello);
		}
		this.pos2traccia.keySet().removeAll(daRimuovere);
	}

	/**
	 * Restituisce l'insieme degli oggetti {@link Direzione} che possono
	 * essere seguite a partire dalla posizione passata come riferimento,
	 * oppure l'insieme vuoto se nessuna direzione e' possibile.<BR/>
	 * @param riferimento - la posizione di partenza
	 * @return l'insieme delle direzioni lecite (senza colpire ostacoli)
	 */
	public Set<Direzione> getPossibiliDirezioni(Coordinate riferimento) {
		/* seleziona solo direzioni verso posizioni adiacenti 
		 * al riferimento che non siano sul bordo */		
		final Set<Direzione> possibili = new HashSet<>();
		possibili.addAll(Direzione.TUTTE);
		final Iterator<Direzione> it = possibili.iterator();
		while (it.hasNext()) {
			final Coordinate destinazione = riferimento.trasla(it.next());
			if (this.suOstacolo(destinazione))
				it.remove();
		}
		return possibili;
	}

	
	public void boom(Coordinate posizione) {
		this.pos2esplosione.put(posizione, Integer.valueOf(1));
	}
	
	public Collection<Coordinate> getCoordinateEsplosioni() {
		return new HashSet<>(getEsplosioni().keySet());
	}

	public Map<Coordinate, Integer> getEsplosioni() {
		return this.pos2esplosione;
	}

	public Integer getEsplosione(Coordinate posizione) {
		return this.pos2esplosione.get(posizione);
	}

	public Integer incEsplosione(Coordinate posizione) {
		final Integer vecchioValore = this.getEsplosione(posizione);
		final Integer nuovoValore = this.pos2esplosione.put(posizione, vecchioValore+1);
		if (nuovoValore>DURATA_DELL_ESPLOSIONE)
			this.pos2esplosione.remove(posizione);
		return nuovoValore;
	}
}
