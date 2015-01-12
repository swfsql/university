import java.awt.event.*;

// keyboard events
public class KeyList implements KeyListener {
  public boolean up, down, right, left;

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    String key = KeyEvent.getKeyText(e.getKeyCode());
    if (key.equals("Right") || key.equals("âE") || key.equals("Å®")) {
      right = true;
    } else if (key.equals("Left")|| key.equals("ç∂") || key.equals("Å©")) {
      left = true;
    }
    if (key.equals("Up")|| key.equals("è„") || key.equals("Å™")) {
      up = true;
    } else if (key.equals("Down")|| key.equals("â∫") || key.equals("Å´")) {
      down = true;
    }
  }

  public void keyReleased(KeyEvent e) {
    String key = KeyEvent.getKeyText(e.getKeyCode());
    if (key.equals("Right") || key.equals("âE") || key.equals("Å®")) {
      right = false;
    } else if (key.equals("Left")|| key.equals("ç∂") || key.equals("Å©")) {
      left = false;
    }
    if (key.equals("Up")|| key.equals("è„") || key.equals("Å™")) {
      up = false;
    } else if (key.equals("Down")|| key.equals("â∫") || key.equals("Å´")) {
      down = false;
    }
  }

}
