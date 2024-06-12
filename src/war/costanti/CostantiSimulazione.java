package war.costanti;

public class CostantiSimulazione {

	/**
	 * La {@link Campo} è un quadrato di queste dimensioni (incluso i bordi)
	 */
	static final public int DIMENSIONE = 30;

	/**
	 * Numero per ogni tipologia
	 */
	static final public int NUMERO_PER_TIPOLOGIA = 3;

	/**
	 * Durata (in passi) totale della simulazione
	 */
	static final public int DURATA_SIMULAZIONE = 1000;

	/**
	 * Pausa (in millisecondi) tra due passi consecutivi della simulazione
	 */
	static final public int RITMO = 100; // millisecondi
	
	/**
	 * Probabilita' di un cambio di direzione di un {@link Tank}
	 */
	static final public double PROBABILITA_CAMBIO_DIREZIONE = 0.10d;

	/**
	 * Probabilita' di sparare un proiettile da parte di un {@link Tank}
	 */
	static final public double PROBABILITA_SPARO = 0.05d;
	
	/**
	 * Percentuale di ostacoli nell'{@link Campo}
	 */
	static final public double PERCENTUALE_OSTACOLI = 0.04d;

	/**
	 * Lunghezza degli ostacoli nell'{@link Campo}
	 */
	static final public double LUNGHEZZA_OSTACOLI = 4;

	/**
	 * Incremento traccia (per la luminosita' del colore)
	 */
	static final public int DELTA_TRACCIA = 25;
	
	/**
	 * Durata (in passi) dell'animazione di una esplosione
	 */
	static final public int	DURATA_DELL_ESPLOSIONE = 3; // in passi

}
