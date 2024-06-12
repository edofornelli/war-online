package war.costanti;


import static java.awt.Color.DARK_GRAY;
import static war.costanti.ImmaginiUtils.leggi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface CostantiGUI {

	// dimensione in px di una cella
	static final public int DIM_CELLS = 30;

	// bordo in px della finestra
	static final public int BORDER_GAP = 38;

	static final public int TANK_DIM = 3;

	static final public int FORT_SCALE = 3;

	static final public Color BORDER_COLOR  = DARK_GRAY;

	static public BufferedImage EXPLOSION_IMG = leggi("boom.png");

	static public BufferedImage BULLET_IMG = leggi("proiettile.png");

	static public BufferedImage BRICK_IMG = leggi("mattone.png");

	// Il colore che sarà rimpiazzato nell'immagine del fortino
	static public Color FORT_MARKER = new Color(255,255,255);

	static public String FORT_IMG_RESOURCE_NAME = "fortino.png";

	// Il colore che sarà rimpiazzato nell'immagine del tank
	static public Color TANK_MARKER = new Color(128,255,0);

	static public String TANK_IMG_RESOURCE_NAME = "tank.png";
	
}

