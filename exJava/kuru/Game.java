import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;


public class Game extends JApplet {

  int 
      x = 10, y = 10,
      vx = 22, vy = 22,
      pax, pay, pbx, pby,
      len = 100,
      rot = 0,
      spin = 12, 
      thick = 20;

	int camX = 0, 
			camY = 0;
	int stageW = 3600, 
			stageH = 3600;
  static int 
      W = 1280,
      H = 1024;
  boolean kr, kl, ku, kd;

  public Game() {
    addKeyListener(new KeyList());
    setFocusable(true);
  }

  public void rotate() {
    rot += spin;
  }

	public void camera() {
		camX += (x - camX) * 1;
		camY += (y - camY) * 1;
		camX = camX < W / 2 ? W / 2 : camX + W / 2 > stageW ? stageW - W / 2 : camX;
		camY = camY < H / 2 ? H / 2 : camY + H / 2 > stageH ? stageH - H / 2 : camY;
	}

  public void move() {
    x += kr ? vx : kl ? -vx : 0;
    y -= ku ? vy : kd ? -vy : 0;

    x = x < len + thick ? len + thick : x > stageW - thick - len ? stageW - thick - len : x;
    y = y < len + thick ? len + thick : y > stageH - thick - len ? stageH - thick - len : y;

    pax = x + (int)(len * Math.cos(Math.toRadians(rot)));
    pay = y + (int)(len * Math.sin(Math.toRadians(rot)));
    pbx = x - (int)(len * Math.cos(Math.toRadians(rot)));
    pby = y - (int)(len * Math.sin(Math.toRadians(rot)));
  }

  public void clearBar(Graphics2D g) {
    g.clearRect(0,0,W,H);
    //g.clearRect(x - len - thick/2 - 1 , y - len - thick/2 - 1, x + len + thick/2 + 1, y + len + thick/2 + 1);
  }

  public void paint(Graphics graphics) {
    Graphics2D g = (Graphics2D) graphics;
    clearBar(g);
		drawLevel(g);
    move();
		camera();
    rotate();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setPaint(Color.gray);
    g.setStroke(new BasicStroke(thick));
    //g.draw(new Line2D.Double(pax, pay, pbx , pby ));
    g.draw(new Line2D.Double(pax - camX + W / 2, pay - camY + H / 2, pbx - camX + W / 2, pby - camY + H / 2));
  }

  public static void main(String s[]) throws Exception {
    JFrame f = new JFrame("LineDemo");
    Game game = new Game();
    f.add(game);
    f.setSize(W, H);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    while (true) {
      game.repaint();
      Thread.sleep(40);
    }
  }

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


