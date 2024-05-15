package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utilz.Constants.UI.URMButtons.URM_SIZE;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.State;
import gamestates.Statemethods;
import main.Game;
import utilz.LoadSave;

import java.awt.Image;

public class GameOver  implements Statemethods{
	private Playing playing;
	private BufferedImage gameOverImage;
	private int imgX,imgY,imgW,imgH;
	private UrmButton Next,Menu ;
	
	
	public GameOver(Playing playing) {
		this.playing = playing ;
		createImg();
		createButtons();
	}
	
	private void createButtons() {
		int yesX = (int) (330* Game.SCALE);
		int noX = (int) ( 100 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		Next = new UrmButton(yesX, y , URM_SIZE, URM_SIZE,2);
		Menu = new UrmButton(noX, y , URM_SIZE, URM_SIZE,0);
	}

	private void createImg() {
		// TODO Auto-generated method stub
		gameOverImage = LoadSave.GetSpriteAtlas(LoadSave.GAME_OVER);
		imgW = (int) (gameOverImage.getWidth() *2);
		imgH = (int) (gameOverImage.getHeight() *Game.SCALE);
		imgX = Game.GAME_WIDTH / 5 - imgW / 3 ;
		imgY = (int) (50 * Game.SCALE);
	}


	
	public void draw(Graphics g) {
		g.setColor(new Color(0,0,0,230));
		g.fillRect(0, 0, Game.GAME_WIDTH+Game.GAME_WIDTH, Game.GAME_HEIGHT+Game.GAME_HEIGHT);
		
		g.drawImage(gameOverImage, imgX, imgY, imgW, imgH, null);
		Next.draw(g);
		Menu.draw(g);
	    }
	
	public void update() {
		Menu.update();
		Next.update();
	}
		
	
	
	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
		
	}
	
	public void mouseMoved(MouseEvent e) {
		Next.setMouseOver(false);
		Menu.setMouseOver(false);
		
		if(isIn(Menu,e))
			Menu.setMouseOver(true);
		else if(isIn(Next , e))
			Next.setMouseOver(true);
		
	}
	
	
	public void mouseReleased(MouseEvent e) {
	    if(isIn(Menu, e)) {
	        if(Menu.isMousePressed()) {
	        	System.out.println("ahah");
	            Gamestate.state = Gamestate.MENU;
	        }
	    } else if(isIn(Next, e)) {
	        if(Next.isMousePressed()) {
	            playing.resetLose();
	        }
	    }   
	    Menu.resetBools();
	    Next.resetBools();     
		
	}

	
	public void mousePressed(MouseEvent e) {
		if (isIn(Menu,e))
			Menu.setMousePressed(true);
		else if (isIn(Next,e))
			Next.setMousePressed(true);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
