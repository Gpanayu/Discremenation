package lib;

public class NotImageException extends Exception{
	public NotImageException(){
		System.out.println("Background has to be an image.");
	}
}
