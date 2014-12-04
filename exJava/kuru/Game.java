import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;


public class Game extends JApplet {

  int 
      x = 10, y = 10,
      vx = 3, vy = 3,
      pax, pay, pbx, pby,
      len = 40,
      rot = 0,
      spin = 3, 
      thick = 10;
  static int 
      W = 640,
      H = 480;
  boolean kr, kl, ku, kd;

  public Game() {
    addKeyListener(new KeyList());
    setFocusable(true);
  }

  public void rotate() {
    rot += spin;
  }

  public void move() {
    x += kr ? vx : kl ? -vx : 0;
    y -= ku ? vy : kd ? -vy : 0;

    x = x < len + thick ? len + thick : x > W - thick - len ? W - thick - len : x;
    y = y < len + thick ? len + thick : y > H - thick - len ? H - thick - len : y;

    pax = x + (int)(len * Math.cos(Math.toRadians(rot)));
    pay = y + (int)(len * Math.sin(Math.toRadians(rot)));
    pbx = x - (int)(len * Math.cos(Math.toRadians(rot)));
    pby = y - (int)(len * Math.sin(Math.toRadians(rot)));
  }

  public void clearBar(Graphics2D g) {
    g.clearRect(x - len - thick/2 - 1 , y - len - thick/2 - 1, x + len + thick/2 + 1, y + len + thick/2 + 1);
  }

  public void paint(Graphics graphics) {
    Graphics2D g = (Graphics2D) graphics;
    clearBar(g);
    move();
    rotate();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setPaint(Color.gray);
    g.setStroke(new BasicStroke(thick));
    g.draw(new Line2D.Double(pax, pay, pbx, pby));
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
      Thread.sleep(10);
    }
  }

  class KeyList implements KeyListener {
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {
      String key = KeyEvent.getKeyText(e.getKeyCode());
      if (key.equals("Right")) {
        kr = true;
      } else if (key.equals("Left")) {
        kl = true;
      }
      if (key.equals("Up")) {
        ku = true;
      } else if (key.equals("Down")) {
        kd = true;
      }
    }
    public void keyReleased(KeyEvent e) {
      String key = KeyEvent.getKeyText(e.getKeyCode());
      if (key.equals("Right")) {
        kr = false;
      } else if (key.equals("Left")) {
        kl = false;
      }
      if (key.equals("Up")) {
        ku = false;
      } else if (key.equals("Down")) {
        kd = false;
      }
    }

  }
 
}


