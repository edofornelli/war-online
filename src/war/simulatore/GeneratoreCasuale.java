package war.simulatore;

import static war.costanti.CostantiSimulazione.DIMENSIONE;

import java.util.*;

import war.Coordinate;

public class GeneratoreCasuale {

	static final public Random random = new Random();
	
	/**
	 * @return le coordinate di una posizione scelta a caso tra quelle
	 *         all'interno dell'campo di esecuzione
	 */
	static public Coordinate posizioneCasuale() {
		final int x = 1 + random.nextInt(DIMENSIONE-2);
		final int y = 1 + random.nextInt(DIMENSIONE-2);
		return new Coordinate(x,y);
	}

	/**
	 * Metodo di utilita' per decidere se si verifica o meno un evento di probabilita' data
	 * secondo una distribuzione uniforme
	 * @param probabilita - la probabilita' dell'evento che si deve verificare o meno
	 * @return true se e solo se l'evento di data probabilita' si e' verificato
	 */
	static public boolean siVerificaEventoDiProbabilita(double probabilita) {
		if (probabilita<0 || probabilita>1)
			throw new IllegalArgumentException("Atteso un valore p tale che 0<=p<=1");
		return ( random.nextDouble() < probabilita );
	}

	static public <T> T scegliAcasoTra(Collection<T> collection) {
		if (collection.isEmpty())
			throw new NoSuchElementException("Non posso scegliere a caso un elemento da una collezione vuota!");
		final ArrayList<T> arrayList = new ArrayList<T>(collection);
		return arrayList.get(random.nextInt(collection.size()));
	}
	
}
