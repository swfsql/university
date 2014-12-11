import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;


public class Game extends JApplet {

	// game fps
	static int FPS = 25;

  int // BAR 
      x = 10, y = 10, // middle point position
      vx = 22, vy = 22, // velocity
      pax, pay, pbx, pby, // bar extreme points 
      len = 100, // length
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

  public Game() {
    addKeyListener(new KeyList()); // keyboard event
    setFocusable(true); // keyboard focus
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
  public void clear(Graphics2D g) {
    g.clearRect(0,0,W,H);
  }

	public void drawBar(Graphics2D g) {
    g.setPaint(Color.gray);
    g.setStroke(new BasicStroke(thick));
    //g.draw(new Line2D.Double(pax, pay, pbx , pby ));
    g.draw(new Line2D.Double(pax - camX + W / 2, pay - camY + H / 2, pbx - camX + W / 2, pby - camY + H / 2));
	}

  public void paint(Graphics graphics) {
    Graphics2D g = (Graphics2D) graphics;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    rotate();
    move();
		camera();

		// draw
    clear(g);
		drawLevel(g);
		drawBar(g);
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

	// will delete later, this is NOT how we will draw levels!
	public void drawLevel(Graphics2D g) {
		int []lvl1x = {20, 80, 120, 460};
		int []lvl1y = {20, 0, 100, 1150};
		int lvl1len = 4;

		int []lvlcpx = {0, 0, 0, 0};
		int []lvlcpy = {0, 0, 0, 0};

		for (int i = 0; i < lvl1len; i++) {
			lvlcpx[i] = lvl1x[i] - camX + W / 2;
			lvlcpy[i] = lvl1y[i] - camY + H / 2;
			//lvlcpx[i] = lvl1x[i];
			//lvlcpy[i] = lvl1y[i];
		}
		g.fillPolygon(lvlcpx, lvlcpy, 4);
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


