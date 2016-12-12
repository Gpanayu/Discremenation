package lib;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class Utility {
	public static boolean isImageValid(Image img){
		if(Math.abs(img.getWidth() - 1500) < 0.001 && Math.abs(img.getHeight() - 800) < 0.001){
			return true;
		}
		return false;
	}
	public static boolean isImageFile(File file){
		try {
			return ImageIO.read(file) != null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
