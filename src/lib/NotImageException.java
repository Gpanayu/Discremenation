package lib;

public class NotImageException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotImageException(){
		System.out.println("Background has to be an image.");
	}
}
