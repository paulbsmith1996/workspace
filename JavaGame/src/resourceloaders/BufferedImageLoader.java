package resourceloaders;
//Paul Baird-Smith 2015

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * BufferedImageLoader - Creates an BufferedImage in loadImage method using a
 * String representing the abstract file path of the image
 * 
 * @author Paul
 *
 */
public class BufferedImageLoader {

	private BufferedImage image;

	public BufferedImage loadImage(String imagePath) throws IOException {
		image = ImageIO.read(new File(ResourceReference.SPRITE_LOCATION
				+ imagePath));
		return image;
	}

}