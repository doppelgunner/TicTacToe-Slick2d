package game.state;

import game.Game;
import game.Settings;
import game.board.Grid;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import boboy.color.CColor;
import boboy.game.gui.GUIRectangle;

public class SettingsState extends BasicGameState {
	
	private final int id;

	private final GUIRectangle goBackBox;
	
	private final int choiceSize = 100;
	
	private final GUIRectangle[][] choices;
	private final int choicesRows;
	private final int choicesCols;
	
	public SettingsState(final int id) {
		this.id = id;
		
		//pause button
		int goBackWidth = 70;
		int goBacHeight = 40;
		goBackBox = new GUIRectangle(50 - goBackWidth / 2, 30 - goBacHeight / 2,
				goBackWidth, goBacHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		choicesRows = 2;
		choicesCols = 4;
		int choicesSpacesX = 0;
		int choicesSpacesY = 0;
		choices = new GUIRectangle[choicesRows][choicesCols];
		for (int row = 0; row < choices.length; row++) {
			for (int col = 0; col < choices[row].length; col++) {
				choices[row][col] = new GUIRectangle(
						100 + col * choiceSize + choicesSpacesX,
						100 + row * choiceSize + choicesSpacesY,
						choiceSize, choiceSize,
						Color.transparent, CColor.normalWhite,
						Color.transparent, CColor.lightOrange);
				
				choicesSpacesX += 10;
			}
			choicesSpacesY += 10;
			choicesSpacesX = 0;
		}
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Graphics g = container.getGraphics();
		
		//goBack
		goBackBox.init(g);
		goBackBox.setText("Back");
	
		int start = 3;
		for (int row = 0; row < choices.length; row ++) {
			for (int col = 0; col < choices[row].length; col++) {
				int size = start + (row * choices[row].length) + col;
				GUIRectangle choice = choices[row][col];
				choice.init(g);
				choice.setText("" + size + "x" + size);
			}
		}
	
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		
		goBackBox.draw(g);
		
		for (int row = 0; row < choices.length; row ++) {
			for (int col = 0; col < choices[row].length; col++) {
				choices[row][col].draw(g);
			}
		}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if (goBackBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			//play sound
			Game.BUTTON_SOUND.play(1.2f,.3f);
			Game.MENU.init(container, game);
			game.enterState(Game.MENU.getID());
		}
		
		int start = 3;
		for (int row = 0; row < choices.length; row ++) {
			for (int col = 0; col < choices[row].length; col++) {
				int n = start + (row * choices[row].length) + col;
				if (choices[row][col].isMouseInside(input.getMouseX(), input.getMouseY()) 
						&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					Game.BUTTON_SOUND.play(1.2f,.3f);
					Settings.setGridSize(n);
					
					Game.MENU.init(container, game);
					game.enterState(Game.MENU.getID());
				}
			}
		}
	}

	@Override
	public int getID() {
		
		return id;
	}

}
