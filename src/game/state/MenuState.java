package game.state;

import game.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import boboy.color.CColor;
import boboy.game.gui.GUIRectangle;

public class MenuState extends BasicGameState {

	private final int id;
	
	private final GUIRectangle playBox;
	private final GUIRectangle exitBox;
	private final GUIRectangle settingsBox;
	
	
	public MenuState(final int id) {
		this.id = id;
		
		//play button
		int playWidth = 150;
		int playHeight = 60;
		playBox = new GUIRectangle(Game.WIDTH / 2 - playWidth / 2 , Game.HEIGHT / 2 - playHeight / 2 -40,
				playWidth, playHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		//settings button
		int settingsWidth = 150;
		int settingsHeight= 60;
		settingsBox = new GUIRectangle(Game.WIDTH / 2 - playWidth / 2 , Game.HEIGHT / 2 - playHeight / 2 +40,
				settingsWidth, settingsHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		//menu button
		int exitWidth = 130;
		int exitHeight = 55;
		exitBox = new GUIRectangle(Game.WIDTH / 2 - exitWidth / 2, Game.HEIGHT / 2 - exitHeight / 2 + 117,
				exitWidth, exitHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Input input = container.getInput();
		input.clearControlPressedRecord();
		input.clearKeyPressedRecord();
		input.clearMousePressedRecord();
		
		Graphics g = container.getGraphics();
		
		playBox.init(g);
		exitBox.init(g);
		playBox.setText("Play GoL");
		exitBox.setText("Exit");
		settingsBox.init(g);
		settingsBox.setText("Settings");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		playBox.draw(g);
		settingsBox.draw(g);
		exitBox.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		
		if (playBox.isMouseInside(input.getMouseX(), input.getMouseY()) && 
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			//play sound
			Game.BUTTON_SOUND.play(1.2f,.3f);
			
			Game.PLAY.init(container, game);
			game.enterState(Game.PLAY.getID());
		}
		
		if (settingsBox.isMouseInside(input.getMouseX(), input.getMouseY()) && 
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			//play sound
			Game.BUTTON_SOUND.play(1.2f,.3f);
			Game.SETTINGS.init(container, game);
			game.enterState(Game.SETTINGS.getID());
		}
		
		if (exitBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			//play sound
			Game.BUTTON_SOUND.play(1.2f,.3f);
			
			//to hear the sound
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//exit the program
			container.exit();
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
}
