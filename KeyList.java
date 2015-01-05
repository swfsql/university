import java.awt.event.*;

// keyboard events
public class KeyList implements KeyListener {
  public boolean up, down, right, left;

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    String key = KeyEvent.getKeyText(e.getKeyCode());
    if (key.equals("Right") || key.equals("右") || key.equals("→")) {
      right = true;
    } else if (key.equals("Left")|| key.equals("左") || key.equals("←")) {
      left = true;
    }
    if (key.equals("Up")|| key.equals("上") || key.equals("↑")) {
      up = true;
    } else if (key.equals("Down")|| key.equals("下") || key.equals("↓")) {
      down = true;
    }
  }

  public void keyReleased(KeyEvent e) {
    String key = KeyEvent.getKeyText(e.getKeyCode());
    if (key.equals("Right") || key.equals("右") || key.equals("→")) {
      right = false;
    } else if (key.equals("Left")|| key.equals("左") || key.equals("←")) {
      left = false;
    }
    if (key.equals("Up")|| key.equals("上") || key.equals("↑")) {
      up = false;
    } else if (key.equals("Down")|| key.equals("下") || key.equals("↓")) {
      down = false;
    }
  }

}
