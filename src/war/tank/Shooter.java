package war.tank;

import static war.costanti.CostantiSimulazione.PROBABILITA_CAMBIO_DIREZIONE;
import static war.simulatore.GeneratoreCasuale.siVerificaEventoDiProbabilita;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import war.Campo;
import war.Direzione;

public class Shooter extends Tank {

	public Shooter (Campo campo) {
		super (campo);
	}


	//	public boolean decideDiSparare(int passo) {
	//		return GeneratoreCasuale.siVerificaEventoDiProbabilita(PROBABILITA_SPARO) ;
	//	}

	public boolean decideDiCambiareDirezione(int passo) {
		int traccia = this.getCampo().rilevaTracciaVerso(this.getPosizione(), this.getDirezione());
		if (traccia>0) {
			return false;
		}
		else {
			return (siVerificaEventoDiProbabilita(PROBABILITA_CAMBIO_DIREZIONE) ) ;
		}
	}

	public Direzione cambioDirezione(Set<Direzione> possibili) {
		int intensita = 0;

		//escludo la mia stessa traccia
		possibili.remove(this.getDirezione().opposta());

		return Collections.max(possibili, new Comparator <Direzione>() {

			@Override
			public int compare(Direzione d1, Direzione d2) {
				return getCampo().rilevaTracciaVerso(getPosizione(), d1) - getCampo().rilevaTracciaVerso(getPosizione(), d2);
			}
			
		});
	}

}
