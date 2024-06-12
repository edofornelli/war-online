package war;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import war.tank.Tank;

public class Fortino {

	private Coordinate posizione;

	private Set<Proiettile> proiettili; // proiettili riforniti sinora

	private Class<? extends Tank> tipo;

	public Fortino(Coordinate posizione, Class<? extends Tank> tipo) {
		this.posizione  = posizione;
		this.proiettili = new HashSet<>();
		this.tipo = tipo;
	}

	public Class<? extends Tank> getTipo() {
		return this.tipo;
	}
	
	public Coordinate getPosizione() {
		return this.posizione;
	}
	
	public void rifornisciDiProiettile(Tank tank) {
		final Proiettile proiettile = new Proiettile(tank);
		this.proiettili.add(proiettile);
		tank.setCarico(proiettile);
	}

	public Set<Proiettile> getProiettili() {
		return Collections.unmodifiableSet(this.proiettili);
	}
	
}
