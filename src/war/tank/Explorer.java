package war.tank;

import static war.costanti.CostantiSimulazione.PROBABILITA_CAMBIO_DIREZIONE;
import static war.simulatore.GeneratoreCasuale.siVerificaEventoDiProbabilita;

import java.util.Set;

import war.Campo;
import war.Direzione;

public class Explorer extends Tank {
	

	public Explorer(Campo campo) {
		super (campo);
	}
	
	
//	public boolean decideDiSparare(int passo) {
//		return GeneratoreCasuale.siVerificaEventoDiProbabilita(PROBABILITA_SPARO) ;
//	}

	public boolean decideDiCambiareDirezione(int passo) {
		return (siVerificaEventoDiProbabilita(PROBABILITA_CAMBIO_DIREZIONE) ) ;
	}

	public Direzione cambioDirezione(Set<Direzione> possibili) {
		return Direzione.scegliAcasoTra(possibili);
	}

}
