package war.tank;

import java.awt.Image;
import java.util.Set;

import war.Campo;
import war.Coordinate;
import war.Direzione;
import war.Fortino;
import war.Proiettile;
import war.simulatore.GeneratoreCasuale;
import war.tank.Factory.Fazione;
import static war.costanti.CostantiSimulazione.*;
import static war.simulatore.GeneratoreCasuale.siVerificaEventoDiProbabilita;

public abstract class Tank {

	private Campo campo;

	private Coordinate posizione; // posizione corrente

	private Direzione direzione;  // direzione corrente

	private Proiettile carico;

	private Fortino fortino;

	private int id;

	public Tank(Campo campo) {
		this.id = Factory.getFazione(this.getClass()).nextProg();
		this.campo = campo;
		this.fortino = campo.getFortinoByTipo(this.getClass());
		this.posizione = campo.posizioneCasuale();
		/* n.b. si parte con una direzione casuale */
		this.direzione = Direzione.casuale();
		this.carico = null;
	}
	
	public boolean nelFortino() {
		return getPosizione().equals(this.getFortino().getPosizione());
	}
	
	public Proiettile caricaProiettile() {
		final Proiettile daScaricare = this.carico;
		this.carico = null;
		return daScaricare;
	}

	public boolean proiettileCaricato() {
		return ( this.getCarico() != null );
	}

	public boolean isCarico() {
		return ( this.getCarico()!=null );
	}

	public Proiettile getCarico() {
		return this.carico;
	}

	public void setCarico(Proiettile proiettile) {
		this.carico = proiettile;
	}

	public Coordinate getPosizione() {
		return this.posizione;
	}

	public void setPosizione(Coordinate nuova) {
		this.posizione = nuova;
	}

	public Fortino getFortino() {
		return this.fortino;
	}

	public void setDirezione(Direzione direzione) {
		this.direzione = direzione;
	}
	
	public Direzione getDirezione() {
		return this.direzione;
	}

	public Campo getCampo() {
		return this.campo;
	}
	
	public boolean decideDiSparare(int passo) {
		return GeneratoreCasuale.siVerificaEventoDiProbabilita(PROBABILITA_SPARO) ;
	}

	public abstract boolean decideDiCambiareDirezione(int passo);

	public abstract Direzione cambioDirezione(Set<Direzione> possibili);
	
	
	public Image getImage() {
		final Fazione fazione = Factory.getFazione(this.getClass());
        return fazione.getTankImage();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+getId();
	}

	public int getId() {
		return this.id;
	}

}
