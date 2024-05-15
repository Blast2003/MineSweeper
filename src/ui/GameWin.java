package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class GameWin implements MouseListener{

	private Playing playing;
	private UrmButton No, Yes;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;

	public GameWin(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int noX = (int) (330 * Game.SCALE);
		int yesX = (int) (100 * Game.SCALE);
		int y = (int) (250 * Game.SCALE);
		Yes = new UrmButton(noX, y, URM_SIZE, URM_SIZE, 0);
		No = new UrmButton(yesX, y, URM_SIZE, URM_SIZE, 2);
	}

	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAME_WIN);
		bgW = (int) (img.getWidth() * 2);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 5 - bgW / 3;
		bgY = (int) (50 * Game.SCALE);
	}

	public void draw(Graphics g) {
		// Added after youtube upload
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH+Game.GAME_WIDTH, Game.GAME_HEIGHT+Game.GAME_HEIGHT);

		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		Yes.draw(g);
		No.draw(g);
	}

	public void update() {
		Yes.update();
		No.update();
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		Yes.setMouseOver(false);
		No.setMouseOver(false);

		if (isIn(No, e))
			No.setMouseOver(true);
		else if (isIn(Yes, e))
			Yes.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
	    if(isIn(No, e)) {
	        if(No.isMousePressed()) {
	        	System.out.println("ahah");
	            Gamestate.state = Gamestate.MENU;
	        }
	    } else if(isIn(Yes, e)) {
	        if(Yes.isMousePressed()) {
	            playing.resetWin();
	        }
	    }   
	    No.resetBools();
	    Yes.resetBools();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(No, e))
			No.setMousePressed(true);
		else if (isIn(Yes, e))
			Yes.setMousePressed(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.print("haahah");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}