package game.state;

import game.Game;
import game.Settings;
import game.board.Grid;
import game.board.drawer.Drawer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import boboy.color.CColor;
import boboy.game.gui.GUIRectangle;

public class PlayState extends BasicGameState {

	private final int id;
	
	private Grid grid;
	private final float X = 0, Y = 30;
	private final int WIDTH = 500, HEIGHT = 400;
	
	private final GUIRectangle pauseBox;
	private final GUIRectangle resumeBox;
	private final GUIRectangle infoStateBox;
	private final GUIRectangle menuBox;
	
	private final GUIRectangle scoreBoard;
	private final GUIRectangle winnerBox;
	private final GUIRectangle infoBox;
	
	private boolean pause;
	
	private Color defaultColor;
	
	public PlayState(final int id) {
		this.id = id;
		
		//pause button
		int pauseWidth = 70;
		int pauseHeight = 40;
		pauseBox = new GUIRectangle(15, 10,
				pauseWidth, pauseHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		//resume button
		int resumeWidth = 150;
		int resumeHeight = 60;
		resumeBox = new GUIRectangle(
				Game.WIDTH / 2 - resumeWidth / 2,Game.HEIGHT / 2 - resumeHeight / 2 - 80,
				resumeWidth,resumeHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		//instruction button
		int controlWidth = 150;
		int controlHeight = 60;
		
		infoStateBox = new GUIRectangle(
				Game.WIDTH / 2 - controlWidth / 2,Game.HEIGHT / 2 - controlHeight / 2,
				controlWidth,controlHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		//menu button
		int menuWidth = 130;
		int menuHeight = 55;
		menuBox = new GUIRectangle(
				Game.WIDTH / 2 - menuWidth / 2, Game.HEIGHT / 2 - menuHeight / 2 + 80,
				menuWidth, menuHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		int scoreBoardWidth = 120;
		int scoreBoardHeight = 55;
		scoreBoard = new GUIRectangle(
				Game.WIDTH / 2 - scoreBoardWidth / 2, 10,
				scoreBoardWidth, scoreBoardHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
		
		int winnerBoxWidth = 140;
		int winnerBoxHeight = 40;
		winnerBox = new GUIRectangle(
				Game.WIDTH / 2 - winnerBoxWidth / 2, Game.HEIGHT - 50,
				winnerBoxWidth, winnerBoxHeight,
				CColor.alphaDarkOrange, CColor.normalWhite,
				CColor.alphaDarkOrange, CColor.lightOrange);
		
		int infoBoxWidth = 100;
		int infoBoxHeight = 200;
		infoBox = new GUIRectangle(
				10, 100,
				infoBoxWidth, infoBoxHeight,
				Color.transparent, CColor.normalWhite,
				Color.transparent, CColor.lightOrange);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		input.clearControlPressedRecord();
		input.clearKeyPressedRecord();
		input.clearMousePressedRecord();
		
		defaultColor = CColor.normalWhite;
		Graphics g = container.getGraphics();
		g.setColor(defaultColor);
		
		int gridWidth = 300;
		int gridHeight = 300;
		grid = new Grid(Game.WIDTH / 2 - gridWidth / 2 + X, Game.HEIGHT / 2 - gridHeight / 2 + Y,
				gridWidth, gridHeight,
				Settings.getGridSize(), Settings.getStreak());
		
		//drawer
		Drawer.clearAll();
		
		//board
		grid.init(container.getGraphics());
		
		//pause
		pauseBox.init(g);
		pauseBox.setText("Pause");
		
		//resume
		resumeBox.init(g);
		resumeBox.setText("Resume");
		
		//instruction
		infoStateBox.init(g);
		infoStateBox.setText("Info");
		
		//menu
		menuBox.init(g);
		menuBox.setText("Menu");
		
		pause = false;
		
		//scoreboard
		scoreBoard.init(g);
		scoreBoard.setText("X:00 | O:00");
		
		//winnerBox
		winnerBox.init(g);
		winnerBox.setVisible(false);

		infoBox.init(g);
		infoBox.setText(""
				+ "Info\n"
				+ "----\n"
				+ "Streak: " + grid.getChecks()+ "\n"
				+ "Size: " + grid.getSize());
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		infoBox.drawTextOnly(g);
		
		grid.render(g);
		scoreBoard.draw(g);
		
		//drawer
		Drawer.draw(g);
		
		winnerBox.draw(g);
		if (!pause) {
			
			//gui
			 pauseBox.draw(g);
		} else {
			g.setColor(CColor.alphaDarkOrange);
			g.fillRect(10,10, Game.WIDTH - 20, Game.HEIGHT - 20);
			g.setColor(CColor.lightOrange);
			g.drawRect(10,10, Game.WIDTH - 20, Game.HEIGHT - 20);
			
			//gui
			resumeBox.draw(g);
			infoStateBox.draw(g);
			menuBox.draw(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		
		if (!container.hasFocus()) {
			pause = true;
		}
		
		if (!pause) {
			
			//when pause is clicked
			if (pauseBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
					input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				
				//play sound
				Game.BUTTON_SOUND.play(1.2f,.3f);
				
				pause = true;
			}
			
			grid.update(container);
			if (grid.someoneWon()) {
				grid.updateScoreBoard(scoreBoard);
				winnerBox.setText(grid.getWinner() + " rematch?");
				winnerBox.setVisible(true);
			}
			
			if (scoreBoard.isMouseInside(input.getMouseX(), input.getMouseY())) {
				//hover effect
			}
			
			if (winnerBox.isMouseInside(input.getMouseX(), input.getMouseY()) 
					&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON) 
					&& winnerBox.isVisible() == true) {
				grid.reset();
				winnerBox.setVisible(false);
				Game.BUTTON_SOUND.play(1.2f,.3f);
			}
		
		} else {
			
			//when resume is clicked
			if (resumeBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
					input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				
				//play sound
				Game.BUTTON_SOUND.play(1.2f,.3f);
				
				//resume game
				pause = false;
			}
			
			//when control is clicked
			if (infoStateBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
					input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				
				//play sound
				Game.BUTTON_SOUND.play(1.2f,.3f);
				
				game.enterState(Game.CONTROL.getID());
			}
			
			//when menu is clicked
			if (menuBox.isMouseInside(input.getMouseX(), input.getMouseY()) &&
					input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				
				//play sound
				Game.BUTTON_SOUND.play(1.2f,.3f);
				
				game.enterState(Game.MENU.getID());
			}
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
