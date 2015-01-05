import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;


public class Game extends JApplet {

  static int FPS = 25;

  // BAR
  int x = 180, y = 360, // middle point position
      vx = 22, vy = 22, // velocity
      len = 80, // length
      rot = 0, // initial rotation
      spin = 12, // rotation velocity
      thick = 20, // thickness
      // collision
      collision_points = 5, // how many points are going to test wall collision on each side (excluding the middle point)
      collided = 0, // a counter
      // when collided = 0, the is not colliding and is not "recovering" from a collision
      // when collided > 0, the bar is "recovering" from a collision (so we don't need to test for new collisions)
      collided_max = 10, // when the bar collides, the counter goes up to this value
      vx_old, vy_old; // velocity when the collision started
  // CAMERA
  int camX = 0, camY = 0; // position
  float camSpeed = 1; // 0 < camSpeed <= 1. (1 = full speed)
  // Window
  int W, H; // width, height
  static double window_multiplier = 1; // 0 < window_multiplier <= 1 = fullscreen

  // STAGE
  int stageW, stageH; // width, height
  BufferedImage bimg;

  // keyboard events trigger booleans:
  boolean kr, kl, ku, kd; // right, left, up, down

  private Image offImage; // Image for buffering
  private Graphics gv; // Graphics for buffering

  Image stage; // loaed stage image in stage folder

  public Game() throws Exception {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    W = (int) (screenSize.width * window_multiplier);
    H = (int) (screenSize.height * window_multiplier);

    addKeyListener(new KeyList()); // keyboard event
    setFocusable(true); // keyboard focus
    stage = loadStage("stages/test.png");
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

    if (collided == 0) { // normal keyboard movement
      vx_old = kr ? vx : kl ? -vx : 0;
      vy_old = ku ? vy : kd ? -vy : 0;

      // keyboard triggered movement
      x += vx_old;
      y -= vy_old;

      rot += spin; // bar rotation
    } else { // we had a collision

      x -= vx_old * collided / collided_max;
      y += vy_old * collided / collided_max;

      rot -= spin; // bar backwards rotation
    }

    // bar boundaries (the should not disappear from the screen)
    x = x < len + thick ? len + thick : x > stageW - thick - len ? stageW - thick - len : x;
    y = y < len + thick ? len + thick : y > stageH - thick - len ? stageH - thick - len : y;

  }

  public void collision() {
    if (collided > 0) {
      collided--;
      return;
    }

    double rot_rad = Math.toRadians(rot);
    float len2 = len / collision_points;
    boolean new_collision = test_collision(x, y);
    for (int i = 0; i < collision_points && !new_collision; i++) {
      new_collision = test_collision(x + (int)(len2 * (i + 1) * Math.cos(rot_rad)), y + (int)(len2 * (i + 1) * Math.sin(rot_rad)));
    }
    for (int i = 0; i < collision_points && !new_collision; i++) {
      new_collision = test_collision(x - (int)(len2 * (i + 1) * Math.cos(rot_rad)), y - (int)(len2 * (i + 1) * Math.sin(rot_rad)));
    }
    if (new_collision) {
      collided = collided_max;
      //System.out.println("collided");
    }

  }
  public boolean test_collision(int i, int j) {
    return bimg.getRGB(i, j) != -1; 
  }

  // clear drawings
  public void clear() {
    // clears the entire screen
    gv.clearRect(0, 0, W, H);
  }

  public void drawBar() {
    // bar's boundaries coordinate
    double rot_rad = Math.toRadians(rot);
    int pax = x + (int)(len * Math.cos(rot_rad)),
        pay = y + (int)(len * Math.sin(rot_rad)),
        pbx = x - (int)(len * Math.cos(rot_rad)),
        pby = y - (int)(len * Math.sin(rot_rad));
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

    collision();
    move();
    camera();

    // draw
    clear();
    drawStage();
    drawBar();

    // gv draw graphics
    graphics.drawImage(offImage, 0, 0, W, H, this);
  }


  public Image loadStage(String filename) throws Exception {
    Image img = ImageIO.read(new File(filename));
    bimg = (BufferedImage) img;
    stageW = bimg.getWidth();
    stageH = bimg.getHeight();
    return img;
  }

  // stage drawing
  public void drawStage() {
    ((Graphics2D) gv).drawImage(stage, W / 2 - camX, H / 2 - camY, this);
  }

  // keyboard events
  class KeyList implements KeyListener {
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {
      String key = KeyEvent.getKeyText(e.getKeyCode());
      if (key.equals("Right") || key.equals("右") || key.equals("→")) {
        kr = true;
      } else if (key.equals("Left")|| key.equals("左") || key.equals("←")) {
        kl = true;
      }
      if (key.equals("Up")|| key.equals("上") || key.equals("↑")) {
        ku = true;
      } else if (key.equals("Down")|| key.equals("下") || key.equals("↓")) {
        kd = true;
      }
    }
    public void keyReleased(KeyEvent e) {
      String key = KeyEvent.getKeyText(e.getKeyCode());
      if (key.equals("Right") || key.equals("右") || key.equals("→")) {
        kr = false;
      } else if (key.equals("Left")|| key.equals("左") || key.equals("←")) {
        kl = false;
      }
      if (key.equals("Up")|| key.equals("上") || key.equals("↑")) {
        ku = false;
      } else if (key.equals("Down")|| key.equals("下") || key.equals("↓")) {
        kd = false;
      }
    }

  }

  public static void main(String s[]) throws Exception {
    JFrame f = new JFrame("Kuru");
    Game game = new Game();
    f.add(game);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    f.setBounds(0, 0, (int) (screenSize.width * window_multiplier), (int) (screenSize.height * window_multiplier));
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    while (true) {
      game.repaint();
      Thread.sleep(1000 / FPS);
    }
  }

}
