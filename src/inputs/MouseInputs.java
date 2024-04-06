package inputs;

import main.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import gamestates.Playing;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private Playing playing;
	private Game game;
	private GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel, Game game) {
		this.gamePanel = gamePanel; this.game = game;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case LEVEL1:
			break;
		case LEVEL2:
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseMoved(e);
			break;
		case LEVEL1:
			break;
		case LEVEL2:
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// switch (Gamestate.state) {
		// case LEVEL1:
		// 	game.setVisible();
		// 	playing = new Playing(10,8,8);
		// 	break;
		// case LEVEL2:
		// 	game.setVisible();
		// 	playing = new Playing(25,10,10);
		// 	break;
		// default:
		// 	break;

		// }

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case LEVEL1:
			// game.setVisible();
			// playing = new Playing(10,8,8);
			break;
		case LEVEL2:
			// game.setVisible();
			// playing = new Playing(25,10,10);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case LEVEL1:
			game.setVisible();
			playing = new Playing(10,8,8);
			break;
		case LEVEL2:
			game.setVisible();
			playing = new Playing(25,10,10);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}