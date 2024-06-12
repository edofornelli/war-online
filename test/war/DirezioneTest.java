package war;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/** 
 * MODIFICARE la classe {@link Direzione} affinche'
 * questi test abbiano successo
 */
public class DirezioneTest {

	private Direzione sudOvest;

	private Direzione nordEst;

	private Direzione est;

	private Direzione ovest;

	@Before
	public void setUp() throws Exception {
		this.sudOvest = new Direzione(+1, +1);
		this.nordEst = new Direzione(-1, -1);
		this.est   = new Direzione(+1,0);
		this.ovest = new Direzione(-1,0);
	}

	@Test
	public void testDirezioneEquals() {
		assertEquals("CORREGGERE", new Direzione(1,1), new Direzione(1,1));
		assertNotEquals("CORREGGERE", new Direzione(-1,1), new Direzione(1,-1));
	}

	@Test
	public void testDirezioneOpposta() {
		assertEquals("CORREGGERE", this.sudOvest,this.nordEst.opposta());
		assertEquals("CORREGGERE", this.nordEst, this.sudOvest.opposta());
		assertEquals("CORREGGERE", this.est,     this.ovest.opposta());
		assertEquals("CORREGGERE", this.ovest,   this.est.opposta());
	}

}
