package war.simulatore;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import war.Campo;
import war.Coordinate;
import war.Proiettile;
import war.tank.Tank;

public class GestoreCollisioni {
	
	private Campo campo;

	public GestoreCollisioni(Campo campo) {
		this.campo = campo;
	}

	void gestisciCollisioni() {
		final Iterator<Proiettile> proiettileIt = this.campo.getProiettili().iterator();
		final Set<Proiettile> esplosi = new HashSet<>();
		while (proiettileIt.hasNext()) {
			/* collisioni proiettile/ostacolo */
			final Proiettile proiettile = proiettileIt.next();
			final Coordinate posizione = proiettile.getPosizione();
			if (this.campo.suOstacolo(posizione)) {
				this.campo.boom(posizione);
				// N.B. un proiettile potrebbe esplodere piu' di una volta cosi'
				esplosi.add(proiettile);
			} else { 
				/* collisioni proiettile/tank */
				final Iterator<Tank> tankIt = this.campo.getTank().iterator();
				while(tankIt.hasNext())  {
					final Tank tank = tankIt.next();
					// un proiettile non deve mai collidere con il proprio lanciatore
					if (proiettile.getLanciatore()==tank) continue;
					
					if (posizione.equals(tank.getPosizione())) {
						this.campo.boom(posizione);
						esplosi.add(proiettile);
						tankIt.remove();
						proiettile.setDistrutto(tank);
					}
				}
			}
		}
		this.campo.getProiettili().removeAll(esplosi);
	}

}
