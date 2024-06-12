package war;

import static org.junit.Assert.assertEquals;
import static war.costanti.CostantiSimulazione.DELTA_TRACCIA;


import org.junit.Before;
import org.junit.Test;

import war.tank.Explorer;

public class CampoTest {
	
	private Campo campo;
	private Explorer explorer;
	
	@Before
	public void setUp() throws Exception {
		this.campo = new Campo(3);
		this.explorer = new Explorer (campo);
		explorer.setPosizione(new Coordinate(1, 1));
		this.campo.lasciaTraccia(explorer);
	}

	@Test
	public void testRilevaTracciaCampoMin() {
		assertEquals(DELTA_TRACCIA, this.campo.rilevaTracciaVerso(new Coordinate (1,0), new Direzione (0,1)));
	}

}
