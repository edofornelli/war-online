package war.tank;

import static java.awt.Color.*;
import static war.costanti.CostantiGUI.FORT_IMG_RESOURCE_NAME;
import static war.costanti.CostantiGUI.FORT_MARKER;
import static war.costanti.CostantiGUI.TANK_IMG_RESOURCE_NAME;
import static war.costanti.CostantiGUI.TANK_MARKER;
import static war.costanti.ImmaginiUtils.leggi;
import static war.costanti.ImmaginiUtils.ricolora;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import war.Fortino;

public class Factory {
	
	private static Factory singleton = new Factory();

	public static Factory getSingleton() { return singleton; }

	private Map<Class<? extends Tank>, Fazione>  tipo2fazione = new HashMap<>();

	private Factory() {
		this.tipo2fazione.put(Explorer.class, new Fazione(Explorer.class, WHITE));
		this.tipo2fazione.put(Shooter.class, new Fazione(Shooter.class, GREEN));

	}

	public static Fazione getFazione(Class<?> tipo) {
		return singleton.tipo2fazione.get(tipo);
	}

	public static void reset() {
		singleton = new Factory();
	}

	public Set<Class<? extends Tank>> getAllTipiDiTank() {
		return this.tipo2fazione.keySet();
	}
	
	/**
	 * Modella una <EM>fazione</EM>.
	 * Di questa conserva il prossimo id progressivo da assegnare,
	 * ed il {@link Color} associato ai {@link Tank} ed ai
	 * {@link Fortino} 
	 */
	public static class Fazione {
		
		private Class<? extends Tank> tipo;
		
		private int progId = 0;
		
		private Color colore;

		private BufferedImage tankImg;

		private BufferedImage fortImg;
		
		public Fazione(Class<? extends Tank> tipo, Color colore) {
			this.tipo = tipo;
			this.colore = colore;
			this.progId = 0;
			this.tankImg = ricolora(leggi(TANK_IMG_RESOURCE_NAME), TANK_MARKER, colore);
			this.fortImg = ricolora(leggi(FORT_IMG_RESOURCE_NAME), FORT_MARKER, colore);
		}
		
	    public int nextProg() {
	    	return this.progId++;
	    }
		
		public Color getColore() {
			return this.colore;
		}
		
		public Class<? extends Tank> getTipo() {
			return this.tipo;
		}
		
		public Image getTankImage() {
			return this.tankImg;	
		}

		public Image getFortImage() {
			return this.fortImg;	
		}
		
		@Override
		public String toString() {
			return getTipo().getSimpleName();
		}

	}
	
}
