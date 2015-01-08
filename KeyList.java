import java.awt.event.*;

// keyboard events
public class KeyList implements KeyListener {
  public boolean up, down, right, left;

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 39) {
      right = true;
    } else if (e.getKeyCode() == 37) {
      left = true;
    }
    if (e.getKeyCode() == 38) {
      up = true;
    } else if (e.getKeyCode() == 40) {
      down = true;
    }
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == 39) {
      right = false;
    } else if (e.getKeyCode() == 37) {
      left = false;
    }
    if (e.getKeyCode() == 38) {
      up = false;
    } else if (e.getKeyCode() == 40) {
      down = false;
    }
  }

}
