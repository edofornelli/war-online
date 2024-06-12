package war.tank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import war.Campo;

/** 
 * Controllare che questi test abbiano successo sia
 * prima che dopo aver operato le modifiche suggerite<BR/>
 */
public class TankTest {

	private Campo campo;

	@Before
	public void setUp() throws Exception {
		this.campo = new Campo();
		Factory.reset();
	}

	@After
	public void tearDown() throws Exception{
		Factory.reset();
	}

	@Test
	public void testIdProgressiviPerIlPrimoTipoDinamico() {
		assertEquals(0, new Explorer(campo).getId());
		assertEquals(1, new Explorer(campo).getId());

	}

	@Test
	public void testIdProgressiviPerAltroTipoDinamico() {
		assertEquals(0, new Shooter(campo).getId());
		assertEquals(1, new Shooter(campo).getId());	
	}

}
