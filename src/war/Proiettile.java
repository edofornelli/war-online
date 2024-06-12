package war;

import war.tank.Tank;

public class Proiettile {

	private Coordinate posizione;
	
	private Tank lanciatore;
	
	private Tank distrutto;
	
	private Direzione direzione;
	
	public Proiettile(Tank t) {
		this.posizione = t.getPosizione();
		this.direzione = t.getDirezione();
		this.lanciatore = t;
		this.distrutto = null;
	}

	public void setPosizione(Coordinate nuove) {
		this.posizione = nuove;
	}

	public Coordinate getPosizione() {
		return this.posizione;
	}
	
	public Direzione getDirezione() {
		return this.direzione;
	}
	
	public void setDirezione(Direzione dir) {
		this.direzione = dir;
	}

	public Tank getLanciatore() {
		return this.lanciatore;
	}

	public Tank getDistrutto() {
		return distrutto;
	}

	public void setDistrutto(Tank distrutto) {
		this.distrutto = distrutto;
	}

}
