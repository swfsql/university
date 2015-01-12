import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;

public class Menu extends JApplet implements KeyListener {

  private int stageID;
  private winID win;

  enum winID {
    MENU,
    HOW_TO_PLAY,
    STAGE_SELECTION;

    public int toInt() {
      switch (this) {
        case MENU:
          return MENU.ordinal();
          //break;
        case HOW_TO_PLAY:
          return HOW_TO_PLAY.ordinal();
          //break;
        case STAGE_SELECTION:
          return STAGE_SELECTION.ordinal();
          //break;
        default:
          return MENU.ordinal();
      }
    }
  }

  Stage stage;
  Image [] bgs;

  private boolean leave;

  private int FPS;
  private JFrame frame;
  public Menu(JFrame frame, int FPS) {
    this.frame = frame; // only used in this.myMain
    this.FPS = FPS; // only used in this.myMain
    this.stageID = 0;
    win = winID.MENU;

    // read menu background images
    bgs = new Image[winID.values().length];
    try {
      bgs[winID.MENU.ordinal()] = ImageIO.read(new File("resource/images/menu/menu.png"));
      bgs[winID.HOW_TO_PLAY.ordinal()] = ImageIO.read(new File("resource/images/menu/how_to_play.png"));
      bgs[winID.STAGE_SELECTION.ordinal()] = ImageIO.read(new File("resource/images/menu/stage_selection.png"));
    } catch (Exception e) {
      System.err.println("Exception: " + e.getMessage());
    }

    // red available stage files
    stage = new Stage();
    stage.searchStages(); // search for available stages in the stages directory
  }

  public void myMain() {
    frame.add(this);
    frame.setVisible(true);
    this.addKeyListener(this); 
    this.setFocusable(true); // keyboard focus
    leave = false;
    while(!leave) {
      try {      
        Thread.sleep(1000 / FPS); // the player is running keyboard events while the code is blocked here.
      } catch (Exception e) {
        System.err.println("Exception: " + e.getMessage());
      }
    }
    this.removeKeyListener(this); 
    this.setFocusable(false); // keyboard focus
    frame.setVisible(false);
    frame.remove(this);
  }

  public int getStageID() {
    return stageID;
  }
  public Stage getStage() {
    return stage;
  }

  public void paint(Graphics graphics) {
    Graphics2D g = (Graphics2D) graphics;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.drawImage(bgs[win.toInt()], 0, 0, this);
  }


  public void keyTyped(KeyEvent e) {
  }

  public void keyPressed(KeyEvent e) {
    switch (win) {
      case MENU:
        switch (e.getKeyCode()) {
          case 39: // right
            win = winID.STAGE_SELECTION;
            this.repaint();
            break;
          case 37: // left
            System.exit(0); // QUIT
            break;
          case 40: // down
            win = winID.HOW_TO_PLAY; // HOW TO PLAY
            this.repaint();
            break;
            //case 38: // up
            //break;
          default:
            return;
        }
        break;

      case HOW_TO_PLAY:
        switch (e.getKeyCode()) {
          case 39: // right
            win = winID.STAGE_SELECTION;
            this.repaint();
            break;
          case 37: // left
            System.exit(0); // QUIT
            break;
            //case 40: // down
            //break;
            //case 38: // up
            //break;
          default:
            return;
        }
        break;

      case STAGE_SELECTION:
        switch (e.getKeyCode()) {
          case 39: // right
            leave = true; // leave menu (stage selected)
            // we could bring some calls to myMain here
            return;
            //break;
          case 37: // left
            win = winID.MENU;
            this.repaint();
            break;
          case 40: // down
            stageID = stageID == stage.getStagesLength() - 1 ? 0 : stageID + 1;
            // TODO draw stage number or miniature
            break;
          case 38: // up
            stageID = stageID == 0 ? stage.getStagesLength() - 1 : stageID - 1;
            // TODO draw stage number or miniature
            break;
          default:
            return;
        }
        break;

      default:
        return;
    }
  }

  public void keyReleased(KeyEvent e) {
  }

}
