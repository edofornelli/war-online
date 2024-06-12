package war;

import war.simulatore.GeneratoreCasuale;
import static war.costanti.CostantiSimulazione.DIMENSIONE;


/**
 * Rappresenta le coordinate di una posizione modellata come coppia di interi 
 * ascissa (x) ed ordinata (y), all'interno dell'{@link Campo}.
 * <B>(DA COMPLETARE VEDI DOMANDA 1)</B>
 */
public class Coordinate {
	
	public static Coordinate casuali() {
		return GeneratoreCasuale.posizioneCasuale();
	}
	
	static public double distanza(Coordinate c0, Coordinate c1) {
		final int dx = c1.getX()-c0.getX();
		final int dy = c1.getY()-c0.getY();
		return Math.sqrt(dx*dx+dy*dy); // Teorema di Pitagora
	}

	private int x;

	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Crea un oggetto {@link Coordinate} traslato rispetto all'originale.
	 * @param dir  direzione verso cui spostarsi (delta su coordinate)
	 * @return il nuovo oggetto {@link Coordinate} traslato 
	 */
	public Coordinate trasla(Direzione dir) {
		return new Coordinate(getX()+dir.getDx(), getY()+dir.getDy());
	}

	@Override
	public String toString() {
		return "("+getX()+","+getY()+")";
	}
	
	@Override
	public boolean equals(Object o) {
		Coordinate that = (Coordinate) o;
		return this.getX()==that.getX() && this.getY()==that.getY();
		
	}
	
	@Override
	public int hashCode() {
		return this.getX()+this.getY()*DIMENSIONE;
	}

}
