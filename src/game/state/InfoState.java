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

public class InfoState extends BasicGameState {

	private final int id;
	
	private final GUIRectangle goBackBox;
	
	private final static String[] info = {
		"MENU SETTINGS: Go to MENU then SETTINGS and Adjust size of the grid",
		"3x3 grid requires 3 streaks",
		"4x4 upto 6x6 grid requires 4 streaks",
		"7x7 grid requires 5 streaks",
		"8x8 upto 10x10 grid requires 6 streaks",
		
	};
	
	public InfoState(final int id) {
		this.id = id;
		
		//pause button
		int goBackWidth = 70;
		int goBacHeight = 40;
		goBackBox = new GUIRectangle(50 - goBackWidth / 2, 30 - goBacHeight / 2,
				goBackWidth, goBacHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Graphics g = container.getGraphics();
		
		//goBack
		goBackBox.init(g);
		goBackBox.setText("Back");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(CColor.lightOrange);
		for (int i = 0; i < info.length; i++) {
			g.drawString(info[i], 10, 100 + i * 25);
		}
		
		goBackBox.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if (goBackBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			//play sound
			Game.BUTTON_SOUND.play(1.2f,.3f);
			
			game.enterState(Game.PLAY.getID());
		}
	}

	@Override
	public int getID() {
		
		return id;
	}

}
