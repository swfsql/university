import java.awt.event.*;

// keyboard events
public class KeyList implements KeyListener {
  public boolean up, down, right, left;

  public void keyTyped(KeyEvent e) {

  }

  public void keyPressed(KeyEvent e) {
    String key = KeyEvent.getKeyText(e.getKeyCode());
    if (key.equals("Right") || key.equals("�E") || key.equals("��")) {
      right = true;
    } else if (key.equals("Left")|| key.equals("��") || key.equals("��")) {
      left = true;
    }
    if (key.equals("Up")|| key.equals("��") || key.equals("��")) {
      up = true;
    } else if (key.equals("Down")|| key.equals("��") || key.equals("��")) {
      down = true;
    }
  }

  public void keyReleased(KeyEvent e) {
    String key = KeyEvent.getKeyText(e.getKeyCode());
    if (key.equals("Right") || key.equals("�E") || key.equals("��")) {
      right = false;
    } else if (key.equals("Left")|| key.equals("��") || key.equals("��")) {
      left = false;
    }
    if (key.equals("Up")|| key.equals("��") || key.equals("��")) {
      up = false;
    } else if (key.equals("Down")|| key.equals("��") || key.equals("��")) {
      down = false;
    }
  }

}
