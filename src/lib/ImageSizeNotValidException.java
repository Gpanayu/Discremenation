package lib;

public class ImageSizeNotValidException extends Exception{


	public ImageSizeNotValidException(){
		System.out.println("The image has to be 1500x800 pixels");
	}
}
