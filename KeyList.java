import java.awt.event.*;

// keyboard events
public class KeyList implements KeyListener {
  public boolean up, down, right, left;

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 39) { // Right
      right = true;
    } else if (e.getKeyCode() == 37) { // Left
      left = true;
    }
    if (e.getKeyCode() == 38) { // Up
      up = true;
    } else if (e.getKeyCode() == 40) { // Down
      down = true;
    }
  }

  public void keyReleased(KeyEvent e) { 
    if (e.getKeyCode() == 39) { // Right
      right = false;
    } else if (e.getKeyCode() == 37) { // Left
      left = false;
    }
    if (e.getKeyCode() == 38) { // Up
      up = false;
    } else if (e.getKeyCode() == 40) { // Down
      down = false;
    }
  }

}
