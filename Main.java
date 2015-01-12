import javax.swing.*;

// obs removed every japanese characters, I (thiago) was getting compile error (even from the comments) :v

public class Main {

  private int f_stageID; //Title
  //private boolean f_isClear; // TODO
  //private long f_currentTime; // TODO

  public final int WIDTH = 800; //
  public final int HEIGHT = 600; //
  public final int FPS = 25; // frames per second

  private Menu menu;
  private Game game;

  private Main() { //
    //f_isClear = false;
    //f_currentTime = -1;
    f_sequence = SeqID.SEQ_MENU;

    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // for fullscreen
    JFrame frame = new JFrame("Kuru");
    //frame.setBounds(0, 0, screenSize.width, screenSize.height); // for fullscreen
    frame.setBounds(0, 0, WIDTH, HEIGHT); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // just create objects
    menu = new Menu(frame, FPS); // search for available stages
    game = new Game(frame, FPS, WIDTH, HEIGHT, menu.getStage()); // use the same stage list reference object

  }

  // calls for OnlyMain.java
  private static Main myMain  = new Main();
  public static Main getMain(){
    return myMain;
  }

  enum SeqID { //SequenceID
    SEQ_MENU,
    SEQ_GAME,
    SEQ_RESULT,
    SEQ_RANKING,
  }

  public SeqID f_sequence; //

  public int getStageID() {
    return this.f_stageID;
  }

  public void setStageID(int tmpStageID) {
    this.f_stageID = tmpStageID;
  }

  /* TODO
  public boolean getIsClear(){
    return this.f_isClear;
  }

  public void setIsClear(boolean tmpIsClear){
    this.f_isClear = tmpIsClear;
  }

  public long getCurrentTime(){
    return this.f_currentTime;
  }

  public void setCurrentTime(long tmpCurrentTime){
    this.f_currentTime = tmpCurrentTime;
  }
*/

  public SeqID getNext(){
    return this.f_sequence;
  }

  public void setNext(SeqID tmpNext) {
    this.f_sequence = tmpNext;
  }

  public void myMain() {

    // calls inside unneeded {} blocks could be moved to the related object, but we would have to send a reference to "this" (Main object) to make the call from that object (such as menu or game). Instead, we have more code into this Main class but we have a simpler call relation between the classes.

    while(true) { // changed myMain call stack to infinite loop
      switch (f_sequence) { 

        case SEQ_MENU:
          menu.myMain(); // starts a new loop that ends when a stage is selected
          {
            this.setStageID(menu.getStageID());
            this.setNext(SeqID.SEQ_GAME);
          }
          break; 

        case SEQ_GAME:
          game.myMain(f_stageID); // starts a new loop that ends when the stage ends (TODO)
          {
            // TODO get timer and/or lives and/or other status (such as game quit or stage clear)
          }
          break;

        case SEQ_RESULT: // TODO
          //Result result = new Result(this);
          //result.myMain();
          {

          }
          break;

        case SEQ_RANKING: // TODO
          //Ranking ranking = new Ranking(this);
          //ranking.myMain();
          {

          }
          break;
      }

    }

  }
}
