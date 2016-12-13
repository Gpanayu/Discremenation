package lib;

public class ImageSizeNotValidException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageSizeNotValidException(){
		System.out.println("The image has to be 1500x800 pixels");
	}
}
