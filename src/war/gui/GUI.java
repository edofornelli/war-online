package war.gui;

import static java.awt.Color.BLACK;
import static java.awt.Color.YELLOW;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static war.costanti.CostantiGUI.BORDER_GAP;
import static war.costanti.CostantiGUI.BORDER_COLOR;
import static war.costanti.CostantiGUI.DIM_CELLS;
import static war.costanti.CostantiGUI.TANK_DIM;
import static war.costanti.CostantiGUI.FORT_SCALE;
import static war.costanti.CostantiGUI.EXPLOSION_IMG;
import static war.costanti.CostantiGUI.BRICK_IMG;
import static war.costanti.CostantiGUI.BULLET_IMG;
import static war.costanti.CostantiSimulazione.DIMENSIONE;
import static war.costanti.ImmaginiUtils.ruota;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import war.Campo;
import war.Coordinate;
import war.Fortino;
import war.Proiettile;
import war.simulatore.Simulatore;
import war.simulatore.Statistiche;
import war.tank.Tank;
import war.tank.Factory;
import war.tank.Factory.Fazione;


public class GUI extends JPanel {

	final private Simulatore simulatore;

	final private JFrame jframe;

	GUI(final Simulatore simulatore) {
		Factory.reset();
		this.simulatore = simulatore;
		this.jframe = new JFrame("WAR");		
		jframe.add(this);
		jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jframe.setSize(DIMENSIONE*DIM_CELLS, DIMENSIONE*DIM_CELLS + BORDER_GAP);
		jframe.setVisible(true);		
	}

	public void initControlliDaTastiera(final Simulatore simulatore) {

		/* Gestione eventi associati alla tastiera: 
		 * Premi >ESC< per terminare la simulazione e stampare le statistiche */
		this.jframe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==VK_ESCAPE) {
					final Campo campo = simulatore.getCampo();
					new Statistiche().stampaStatisticheFinali(campo);
					simulatore.terminaSimulazione();
				}
			}
		});
	}

	public void riportaSommarioNelTitolo(int passo, Campo campo) {
		final StringBuilder title = new StringBuilder("Step="+passo);
		for(Fortino fortino : campo.getFortini()) {
			final long sparati = fortino.getProiettili().size();
			title.append("; "+fortino.getTipo().getSimpleName()+":");
			title.append("P="+sparati);
			int numeroTankDiQuestoTipo = 0;
			for(Tank t : campo.getTank())
				if (t.getClass()==fortino.getTipo())
					numeroTankDiQuestoTipo++;
			title.append(" T="+numeroTankDiQuestoTipo);
		}

		this.jframe.setTitle(title.toString());
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(BLACK);
		g.fillRect(0, 0, DIMENSIONE * DIM_CELLS, DIMENSIONE * DIM_CELLS);
		final Campo campo = this.simulatore.getCampo();
		this.disegnaOstacoli(g,campo.getOstacoli());
		g.setColor(YELLOW);

		for (Tank tank : this.simulatore.getTank()) {
			disegnaTank(g, tank);
		}	

		for (Proiettile proiettile : campo.getProiettili()) {
			disegnaProiettile(g, proiettile);
		}

		for (Coordinate c : campo.getEsplosioni().keySet()) {
			disegnaEsplosione(g, c);
		}

		for (Coordinate c : campo.getTraccia().keySet()) {
			final int livello = campo.getTraccia().get(c);
			disegnaTraccia(g, c.getX(), c.getY(), livello);
		}
		
		for(Fortino fortino : campo.getFortini())
			disegnaFortino(g,fortino);
	}

	private void disegnaProiettile(Graphics g, Proiettile proiettile) {
		final int rotazione = Math.round(proiettile.getDirezione().getGradi());
		final BufferedImage immagine = ruota(BULLET_IMG, rotazione);
		final float scala = ( rotazione % 90 == 0 ? .66f : 1f );
		disegnaImmagine(g, immagine, proiettile.getPosizione(), scala);
	}

	private void disegnaEsplosione(Graphics g, Coordinate posizione) {
		final Integer esplosione = this.simulatore.getCampo().getEsplosione(posizione);
		if (esplosione==null) return;
		float scala = esplosione.intValue();
		
		disegnaImmagine(g, EXPLOSION_IMG, posizione, scala);
	}

	private void disegnaFortino(Graphics g, Fortino fortino) {
		final Fazione fazione = Factory.getFazione(fortino.getTipo());
		disegnaImmagine(g, fazione.getFortImage(), fortino.getPosizione(), FORT_SCALE);
		g.setColor(BLACK);
	}

	private void disegnaTank(Graphics g, Tank tank) {
        final Coordinate pos = tank.getPosizione();		

        BufferedImage immagine = (BufferedImage) tank.getImage();

		final int rotazione = Math.round(tank.getDirezione().getGradi());
		immagine = ruota(immagine, rotazione);
		disegnaTesto(g, pos, tank.toString());
		// compensa: in diagonale sembrano piu' piccoli
		final float scala = ( rotazione % 90 == 0 ? 1.5f : 2 );
		disegnaImmagine(g, immagine, pos, scala);
	}

	private void disegnaTesto(Graphics g, Coordinate c, String testo) {
		final int x = c.getX();
		final int y = c.getY();
		int d = DIM_CELLS;
		int gx = x*d, gy = y*d;
        g.drawString(testo, gx-d/2, gy - DIM_CELLS / 2);
	}

	private void disegnaImmagine(Graphics g, Image image, Coordinate c) {
		final int x = c.getX();
		final int y = c.getY();
		int d = DIM_CELLS;
		int gx = x*d, gy = y*d;
		g.drawImage(image, gx, gy, d, d, null);		
	}

	private void disegnaImmagine(Graphics g, Image image, Coordinate c, float scala) {
		final int x = c.getX();
		final int y = c.getY();
		int d = DIM_CELLS;
		int gx = Math.round(x*d-d*(scala-1)/2), gy = Math.round(y*d-d*(scala-1)/2);
		int size = Math.round(d*scala);
		g.drawImage(image, gx, gy, size, size, null);		
	}

	private void disegnaOstacoli(Graphics g, Set<Coordinate> ostacoli) {
		for(Coordinate c : ostacoli) {
			disegnaOstacolo(g, c.getX(), c.getY(), BORDER_COLOR); 
		}
	}

	private void disegnaOstacolo(Graphics g, int x, int y, Color colore) {
		g.setColor(colore);
		disegnaImmagine(g, BRICK_IMG, new Coordinate(x, y));
	}

	private void disegnaTraccia(Graphics g, int x, int y, int livello) {
		final Color colore = getColore(livello);
		g.setColor(colore);
		g.fillRect(x*DIM_CELLS+DIM_CELLS/2-TANK_DIM, 
				   y*DIM_CELLS+DIM_CELLS/2-TANK_DIM, 
				   TANK_DIM, 
				   TANK_DIM);
	}

	private Color getColore(float livello) {
		return Color.getHSBColor(0.5f , 0.5f, Math.min( (livello + 50 )/ 100 , 1) );
	}

}
