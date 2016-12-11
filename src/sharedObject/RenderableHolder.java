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
	public static Image blackRight;
	public static Image blackLeft;
	public static Image whiteRight;
	public static Image whiteLeft;
	public static Image yellowRight;
	public static Image yellowLeft;
	
	static {
		loadResource();
	}
	
	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ()){
				return 1;
			}
			return -1;
		};
	}
	
	public static RenderableHolder getInstance() {
		return instance;
	}
	
	public static void loadResource() {
		blackRight = new Image(ClassLoader.getSystemResource("BlackRight.png").toString());
		blackLeft = new Image(ClassLoader.getSystemResource("BlackLeft.png").toString());
		whiteRight = new Image(ClassLoader.getSystemResource("WhiteRight.png").toString());
		whiteLeft = new Image(ClassLoader.getSystemResource("WhiteLeft.png").toString());
		yellowRight = new Image(ClassLoader.getSystemResource("YellowRight.png").toString());
		yellowLeft = new Image(ClassLoader.getSystemResource("YellowLeft.png").toString());
	}
	
	public synchronized void add(IRenderable entity) {
		entities.add(entity);
		Collections.sort(entities, comparator);
	}
	
	public synchronized void update() {
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).isDestroyed())
				entities.remove(i);
		}
	}
	
	public synchronized List<IRenderable> getEntities() {
		return entities;
	}
}
