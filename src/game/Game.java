package game;

import game.state.InfoState;
import game.state.MenuState;
import game.state.PlayState;
import game.state.SettingsState;
import game.state.SplashState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import boboy.color.CColor;

public class Game extends StateBasedGame {
	
	public static final GameState SPLASH = new SplashState(0);
	public static final GameState MENU = new MenuState(1);
	public static final GameState PLAY = new PlayState(2);
	public static final GameState CONTROL = new InfoState(3);
	public static final GameState SETTINGS = new SettingsState(4);
	
	public static final String TITLE = "Tic Tac Toe";
	public static final int WIDTH = 640, HEIGHT = 480;
	public static final int FPS = 60;
	
	public static Sound BUTTON_SOUND;
	public static Sound PLACE_SOUND[];
	
	private static int placeSoundCounter;

	public Game(String name) throws SlickException {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(SPLASH);
		addState(MENU);
		addState(SETTINGS);
		addState(PLAY);
		addState(CONTROL);
		container.getGraphics().setBackground(CColor.darkOrange);
		
		BUTTON_SOUND = new Sound("sounds/bubbles.wav");
		PLACE_SOUND = new Sound[3];
		for (int i = 0; i < PLACE_SOUND.length; i++) {
			PLACE_SOUND[i] = new Sound("sounds/place_" + (i + 1) + ".wav");
		}
	}
	
	public static void main (String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game(TITLE));
		app.setDisplayMode(WIDTH,HEIGHT, false);
		app.setTargetFrameRate(FPS);
		app.setShowFPS(false);
		app.setIcons(new String[] {"resources/icon-32.png",
				"resources/icon-24.png", "resources/icon-16.png"});
		app.start();
		
		
	}
	
	public static void playPlaceSound() {
		placeSoundCounter++;
		placeSoundCounter %= PLACE_SOUND.length;
		
		PLACE_SOUND[placeSoundCounter].play();
	}
	
}
