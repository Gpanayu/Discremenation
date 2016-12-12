package logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sharedObject.IRenderable;
import lib.ConfigurableOption;

public class Field implements IRenderable{

	private Image bgImage = null;
	private int currentX = 0;
	private int imageWidth;
	
	public Field(Image bg){
		this.bgImage = bg;
		if (bgImage != null){
			imageWidth = (int) bgImage.getWidth();
		}
		else{
			imageWidth = 0;
		}
	}
	
	public void updateField() {
		currentX++;
		if (currentX >= imageWidth)
			currentX = 0;
	}
	
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return Integer.MIN_VALUE;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (bgImage == null){
			return ;
		}
		int currentDrawingX = 0;
		int currentDrawingY = 0;

		while (currentDrawingY < ConfigurableOption.SCREEN_HEIGHT) {
			WritableImage croppedImage = new WritableImage(bgImage.getPixelReader(), currentX, 0, imageWidth - currentX,
					(int) bgImage.getHeight());
			gc.drawImage(croppedImage, currentDrawingX, currentDrawingY);
			currentDrawingY += bgImage.getHeight();
		}
		currentDrawingX += imageWidth - currentX;
		currentDrawingY = 0;

		while (currentDrawingX < ConfigurableOption.SCREEN_WIDTH) {
			while (currentDrawingY < ConfigurableOption.SCREEN_HEIGHT) {
				gc.drawImage(bgImage, currentDrawingX, currentDrawingY);
				currentDrawingY += bgImage.getHeight();
			}
			currentDrawingX += imageWidth;
			currentDrawingY = 0;
		}
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, ConfigurableOption.SCREEN_WIDTH / 2, ConfigurableOption.SCREEN_HEIGHT / 7);
		gc.setFill(Color.BLACK);
		gc.fillRect(ConfigurableOption.SCREEN_WIDTH / 2, 0, ConfigurableOption.SCREEN_WIDTH / 2, ConfigurableOption.SCREEN_HEIGHT / 7);
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

}
