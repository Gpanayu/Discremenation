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
	
	public static Image welcomeBackGround;
	public static Image commandList;
	
	public static Image barcode;
	public static Image yinyang;
	public static Image duel;
	public static Image decoy;
	public static Image menu_barcode;
	public static Image yinyang_menu;
	public static Image duel_menu;
	public static Image decoy_menu;
	
	private static final String slash = "sound/slash.mp3";
	private static final String hit = "sound/hit.mp3";
	private static final String blink = "sound/blink.mp3";
	
	private static AudioClip slash_sound;
	private static AudioClip hit_sound;
	private static AudioClip blink_sound;
	
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
		
		welcomeBackGround = new Image(ClassLoader.getSystemResource("start.png").toString());
		commandList = new Image (ClassLoader.getSystemResource("CommandList.png").toString());
		
		slash_sound = new AudioClip(ClassLoader.getSystemResource(slash).toString());
		hit_sound = new AudioClip(ClassLoader.getSystemResource(hit).toString());
		blink_sound = new AudioClip(ClassLoader.getSystemResource(blink).toString());
		
		barcode = new Image(ClassLoader.getSystemResource("bg_barcode.png").toString());
		yinyang = new Image(ClassLoader.getSystemResource("bg_yinyang.png").toString());
		duel = new Image(ClassLoader.getSystemResource("bg_duel.png").toString());
		decoy = new Image(ClassLoader.getSystemResource("bg_decoy.png").toString());
		 
		menu_barcode = new Image(ClassLoader.getSystemResource("barcode_menu.png").toString());
		yinyang_menu = new Image(ClassLoader.getSystemResource("yinyang_menu.png").toString());
		duel_menu = new Image(ClassLoader.getSystemResource("duel_menu.png").toString());
		decoy_menu = new Image(ClassLoader.getSystemResource("decoy_menu.png").toString());

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
