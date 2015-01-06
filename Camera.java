import java.awt.*;
import javax.swing.*;

public class Camera extends JApplet {


  public Graphics gv; // Graphics for buffering
  private Image offImage; // Image for buffering


  int x = 0, y = 0, // position
      w = 0, h = 0; // screen width, height

  // when moving
  float speed = 1; // 0 < speed <= 1 = no delay

  public void resize(int w, int h) {
    this.w = w;
    this.h = h;
  }

  // camera movement
  public void move(Bar bar, Stage stage) {
    // camera tries to follow the target
    x += (int) ((bar.x - x) * speed);
    y += (int) ((bar.y - y) * speed);

    // camera boundaries
    x = x < w / 2 ? w / 2 : x + w / 2 > stage.w ? stage.w - w / 2 : x;
    y = y < h / 2 ? h / 2 : y + h / 2 > stage.h ? stage.h - h / 2 : y;
  }

  // clear drawings
  public void clear() {
    // clears the entire screen
    gv.clearRect(0, 0, w, h);
  }

  public void update(Graphics g) {
    paint(g);
  }

  // buffer for flickering
  public void updateBuffer() {
    offImage = createImage(w, h);
    gv = offImage.getGraphics();
    ((Graphics2D) gv).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  public void paint(Graphics graphics) {
    graphics.drawImage(offImage, 0, 0, w, h, this);
  }
}




