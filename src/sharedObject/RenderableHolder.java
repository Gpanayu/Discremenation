package sharedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class RenderableHolder {

	private static final RenderableHolder instance = new RenderableHolder();

	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;
	public static Image blackBlinkerSheet;
	public static Image whiteBlinkerSheet;
	public static Image yellowBlinkerSheet;
	
	static {
		loadResource();
	}
	
	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
	}
	
	public static RenderableHolder getInstance() {
		return instance;
	}
	
	public static void loadResource() {
		blackBlinkerSheet = new Image(ClassLoader.getSystemResource("blackBlinkerSheet.png").toString());
		whiteBlinkerSheet = new Image(ClassLoader.getSystemResource("whiteBlinkerSheet.png").toString());
		yellowBlinkerSheet = new Image(ClassLoader.getSystemResource("yellowBlinkerSheet.png").toString());
	}
	
	public void add(IRenderable entity) {
		entities.add(entity);
		Collections.sort(entities, comparator);
	}
	
	public void update() {
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).isDestroyed())
				entities.remove(i);
		}
	}
	
	public List<IRenderable> getEntities() {
		return entities;
	}
}
