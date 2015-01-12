import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Menu implements KeyListener {

	Main main;

	int winID, stageID, stageMax;
	boolean playRequested;
	/* 
	 * 0 = KURU / ABOUT (Å© QUIT)(Å® SELECT STAGE / SCORE)(Å´ HOW TO PLAY)(Å™ -)
	 * 1 = HOW TO PLAY (Å© QUIT)(Å® SELECT STAGE / SCORE)(Å´ -)(Å™ -)
	 * 2 = STAGE SELECT / SCORE (Å© KURU / ABOUT)(Å® PLAY)(Å´ change stage)(Å™ change stage)
	 */

	Image [] bgs;

	public Menu(Main main) throws Exception {
		this.main = main;
		winID = 0;
		stageID = 0;
		playRequested = false;
		//this.stageMax = stageMax;
		bgs = new Image[3];
		bgs[0] = ImageIO.read(new File("resource/images/bg0.png"));
		bgs[1] = ImageIO.read(new File("resource/images/bg1.png"));
		bgs[2] = ImageIO.read(new File("resource/images/bg2.png"));
	}

	public void setStageMax(int stageMax) {
		this.stageMax = stageMax;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (playRequested) return;

		switch (winID) {
		case 0:
			switch (e.getKeyCode()) {
			case 39: // right
				winID = 2; // STAGE SELECT
				break;
			case 37: // left
				System.exit(0); // QUIT
				break;
			case 40: // down
				winID = 1; // HOW TO PLAY
				break;
				//case 38: // up
				//break;
			default:
				return;
			}
			break;
		case 1:
			switch (e.getKeyCode()) {
			case 39: // right
				winID = 2; // STAGE SELECT
				break;
			case 37: // left
				System.exit(0); // QUIT
				break;
				//case 40: // down
				//break;
				//case 38: // up
				//break;
			default:
				return;
			}
			break;
		case 2:
			switch (e.getKeyCode()) {
			case 39: // right
				playRequested = true; // PLAY
				return;
				//break;
			case 37: // left
				winID = 0; // MENU
				//draw();
				break;
			case 40: // down
				stageID = stageID == stageMax - 1 ? 0 : stageID + 1;
				// TODO draw stage number or miniature
				break;
			case 38: // up
				stageID = stageID == 0 ? stageMax - 1 : stageID - 1;
				// TODO draw stage number or miniature
				break;
			default:
				return;
			}
			break;
		default:
			return;
		}
	}

	public void draw(Camera cam) {
		((Graphics2D) cam.gv).drawImage(bgs[winID], 0, 0, cam);
	}

	public void keyReleased(KeyEvent e) {
	}

}
