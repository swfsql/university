import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;


public class Game extends JApplet {

	static int FPS = 25;

	int // BAR 
	x = 180, y = 360, // middle point position
	vx = 22, vy = 22, // velocity
	pax, pay, pbx, pby, // bar extreme points 
	len = 80, // length
	rot = 0, // initial rotation
	spin = 12, // rotation velocity
	thick = 20; // thickness

	int // CAMERA
	camX = 0, camY = 0; // position
	float camSpeed = 1; // 0 < camSpeed <= 1. (1 = full speed)
	static int // WINDOW
	W = 1280, H = 1024; // width, height

	int  // STAGE
	stageW = 3600, stageH = 3600; // width, height
	// this changes every stage

	// keyboard events trigger booleans:
	boolean kr, kl, ku, kd; // right, left, up, down

	private Image offImage; // Image for buffering
	private Graphics gv; // Graphics for buffering

	Image stage; // loaed stage image in stage folder

  public Game() throws Exception {
    addKeyListener(new KeyList()); // keyboard event
    setFocusable(true); // keyboard focus
		stage = loadStage("stages/test.png");
  }

	// bar rotation
	public void rotate() {
		rot += spin;
	}

	// camera movement
	public void camera() {
		// camera tries to follow the bar
		camX += (x - camX) * 1;
		camY += (y - camY) * 1;
		// camera boundaries
		camX = camX < W / 2 ? W / 2 : camX + W / 2 > stageW ? stageW - W / 2 : camX;
		camY = camY < H / 2 ? H / 2 : camY + H / 2 > stageH ? stageH - H / 2 : camY;
	}

	// bar movement
	public void move() {
		// keyboard triggered movement
		x += kr ? vx : kl ? -vx : 0;
		y -= ku ? vy : kd ? -vy : 0;

		// bar boundaries (the should not disappear from the screen)
		x = x < len + thick ? len + thick : x > stageW - thick - len ? stageW - thick - len : x;
		y = y < len + thick ? len + thick : y > stageH - thick - len ? stageH - thick - len : y;

		// bar's boundaries coordinate
		pax = x + (int)(len * Math.cos(Math.toRadians(rot)));
		pay = y + (int)(len * Math.sin(Math.toRadians(rot)));
		pbx = x - (int)(len * Math.cos(Math.toRadians(rot)));
		pby = y - (int)(len * Math.sin(Math.toRadians(rot)));
	}

	// clear drawings
	public void clear() {
		gv.clearRect(0, 0, W, H);
	}

	public void drawBar() {
		((Graphics2D) gv).setPaint(Color.gray);
		((Graphics2D) gv).setStroke(new BasicStroke(thick));
		//g.draw(new Line2D.Double(pax, pay, pbx , pby ));
		((Graphics2D) gv).draw(new Line2D.Double(pax - camX + W / 2, pay - camY + H / 2, pbx - camX + W / 2, pby - camY + H / 2));
	}

	public void update(Graphics g) {
        paint(g);
    }
	
	public void paint(Graphics graphics) {
		offImage = createImage(W, H);
		gv = offImage.getGraphics();
		//Graphics2D g = (Graphics2D) graphics;
		//Graphics gv = offImage.getGraphics();
		((Graphics2D) gv).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rotate();
		move();
		camera();

		// draw
    clear();
		drawStage();
		drawBar();

		// gv draw graphics
		graphics.drawImage(offImage, 0, 0, W, H, this);
  }

  public static void main(String s[]) throws Exception {
    JFrame f = new JFrame("Kuru");
    Game game = new Game();
    f.add(game);
    f.setSize(W, H);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    while (true) {
      game.repaint();
      Thread.sleep(1000 / FPS);
    }
  }

	public Image loadStage(String filename) throws Exception {
		Image img = ImageIO.read(new File(filename));
		BufferedImage bimg = (BufferedImage) img;
 		stageW = bimg.getWidth();
		stageH = bimg.getHeight();
		return img;
	}

	// will delete later, this is NOT how we will draw levels!
	public void drawStage() {
		((Graphics2D) gv).drawImage(stage, W / 2 - camX, H / 2 - camY, this);
	}

	// keyboard events
	class KeyList implements KeyListener {
		public void keyTyped(KeyEvent e) {

		}
		public void keyPressed(KeyEvent e) {
			String key = KeyEvent.getKeyText(e.getKeyCode());
			if (key.equals("Right") || key.equals("右")) {
				kr = true;
			} else if (key.equals("Left")|| key.equals("左")) {
				kl = true;
			}
			if (key.equals("Up")|| key.equals("上")) {
				ku = true;
			} else if (key.equals("Down")|| key.equals("下")) {
				kd = true;
			}
		}
		public void keyReleased(KeyEvent e) {
			String key = KeyEvent.getKeyText(e.getKeyCode());
			if (key.equals("Right") || key.equals("右")) {
				kr = false;
			} else if (key.equals("Left")|| key.equals("左")) {
				kl = false;
			}
			if (key.equals("Up")|| key.equals("上")) {
				ku = false;
			} else if (key.equals("Down")|| key.equals("下")) {
				kd = false;
			}
		}

	}

}



