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
	
	private static final String slash = "sound/slash.wav";
	private static final String hit = "sound/hit.wav";
	private static final String blink = "sound/blink.wav";
	
	private static AudioClip slash_sound;
	private static AudioClip hit_sound;
	private static AudioClip blink_sound;
	private static AudioClip instinct_song;
	private static AudioClip yoshida_song;
	
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
		
		instinct_song = new AudioClip(ClassLoader.getSystemResource("sound/bensound-instinct.mp3").toString());
		yoshida_song = new AudioClip(ClassLoader.getSystemResource("sound/Yoshida Brothers - Ibuki.mp3").toString());
		
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
	
	public AudioClip getSound(String sound){
		if (sound.equals("slash_sound")){
			return RenderableHolder.slash_sound;
		}
		else if(sound.equals("hit_sound")){
			return RenderableHolder.hit_sound;
		}
		else if (sound.equals("blink_sound")){
			return RenderableHolder.blink_sound;
		}
		else if (sound.equals("instinct_song")){
			return RenderableHolder.instinct_song;
		}
		else if (sound.equals("yoshida_song")){
			return RenderableHolder.yoshida_song;
		}
		else{
			return null;
		}
	}
	
	public List<IRenderable> getEntities() {
		return entities;
	}
}
