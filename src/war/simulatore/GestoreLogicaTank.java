package war.simulatore;

import java.util.Set;

import war.Campo;
import war.Direzione;
import war.Proiettile;
import war.tank.Tank;

public class GestoreLogicaTank {

	final private Campo campo;

	public GestoreLogicaTank(Campo campo) {
		this.campo = campo;
	}

	public void simula(Tank tank, int passo) {
		if (!tank.proiettileCaricato()) {
			/* sprovvisto di proiettile ? */
			if (tank.nelFortino()) {
				/* rientrato, carica il proiettile e ricomincia da capo */
				tank.getFortino().rifornisciDiProiettile(tank);
			} else {
				/* proiettile mancante: redireziona per rientrare verso il fortino */
				final Direzione versoFortino = new Direzione(tank.getPosizione(),tank.getFortino().getPosizione());
				final Set<Direzione> possibili = getDirezioniAttualmentePossibili(tank);
				if (possibili.contains(versoFortino)) {
					eseguiSpostamento(tank, versoFortino);
				}
				else {
					eseguiSpostamento(tank, Direzione.scegliAcasoTra(possibili));
				}
			}
		} else {
			/* rifornito di proiettile: combatti! */
			if (tank.isCarico() && tank.decideDiSparare(passo)) {
				spara(tank);
			} else { /* niente da fare: scegli una direzione */
				/* calcola l'insieme delle possibili direzioni (senza urtare gli ostacoli) */
				final Set<Direzione> possibili = getDirezioniAttualmentePossibili(tank);
				Direzione direzione = tank.getDirezione();
				if (deveCambiareDirezione(tank, possibili) || tank.decideDiCambiareDirezione(passo)) {
					final Direzione nuovaDirezione = tank.cambioDirezione(possibili);
					/* ricontrolla che la scelta sia sensata: colpisco un ostacolo? */
					if (!possibili.contains(nuovaDirezione))
						throw new IllegalStateException("La direzione scelta per "+tank+" non era tra quelle possibili:"
								+ " cosi' il tank sbatte contro un ostacolo!\n"
								+ "\tPossibili: " + possibili + "\n"
								+ "\t   Decisa: " + nuovaDirezione + "\n");
					direzione = nuovaDirezione;
				}
				eseguiSpostamento(tank, direzione);
			}
		}
		/* lascia traccia dei cingolati durante lo spostamento */
		this.campo.lasciaTraccia(tank);
	}

	private void eseguiSpostamento(Tank tank, Direzione nuova) {
		if (nuova.equals(tank.getDirezione()))
			tank.setPosizione(tank.getPosizione().trasla(tank.getDirezione()));
		else tank.setDirezione(nuova); // cambiare direzione "costa" un passo
	}

	private void spara(Tank tank) {
		final Proiettile proiettile = tank.getCarico();
		proiettile.setDirezione(tank.getDirezione());
		proiettile.setPosizione(tank.getPosizione());
		this.campo.add(proiettile);
		tank.setCarico(null);
	}

	private Set<Direzione> getDirezioniAttualmentePossibili(Tank tank) {
		return this.campo.getPossibiliDirezioni(tank.getPosizione());
	}
	
	private boolean deveCambiareDirezione(final Tank tank, final Set<Direzione> direzioniPossibili) {
		return !direzioniPossibili.contains(tank.getDirezione());
	}

}
