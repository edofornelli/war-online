package war.costanti;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImmaginiUtils {

	static public BufferedImage leggi(String filename) {
		try {
			return  ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Non riesco a leggere: "+filename,e);
		}

	}

	static public BufferedImage ricolora(BufferedImage image, Color vecchio, Color nuovo) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originale = new Color(image.getRGB(x, y),true);
                if (originale.equals(vecchio)) {
                    image.setRGB(x, y, nuovo.getRGB());
                }
            }
        }
        return image;
    }
	
	static public BufferedImage ruota(BufferedImage bimg, int angolo) {
	    double sin = Math.abs(Math.sin(Math.toRadians(angolo))),
	           cos = Math.abs(Math.cos(Math.toRadians(angolo)));
	    int width = bimg.getWidth();
	    int height = bimg.getHeight();
	    int new_w = (int) Math.floor(width*cos + height*sin),
	        new_t = (int) Math.floor(height*cos + width*sin);
	    BufferedImage ruotata = new BufferedImage(new_w, new_t, bimg.getType());
	    Graphics2D graphic = ruotata.createGraphics();
	    graphic.translate((new_w-width)/2, (new_t-height)/2);
	    graphic.rotate(Math.toRadians(angolo), width/2, height/2);
	    graphic.drawRenderedImage(bimg, null);
	    graphic.dispose();
		return ruotata;
	}
	    
	
}
