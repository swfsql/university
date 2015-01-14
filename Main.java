
// obs removed every japanese characters, I (thiago) was getting compile error (even from the comments) :v

public class Main {

  private boolean f_isClear; // TODO
  private long f_currentTime; // TODO

  final static int WIDTH = 1200; //
  final static int HEIGHT = 800; //
  final static int FPS = 25; // frames per second

  private static Sequence sequence;
  private static Menu menu;
  private static Game game;
  private static Result result;
  private static Ranking ranking;

  private Main() { //
    f_isClear = false;
    f_currentTime = -1;
    f_sequence = SeqID.SEQ_MENU;

    // just create objects
    Stage stage = new Stage(); // search for available stages
    menu = new Menu(stage.getStagesLength()); 
    game = new Game(stage);
    game.setStageID(menu.getStageID());
    result = new Result(menu.getStageID(),f_isClear,f_currentTime);
    ranking = new Ranking();

    sequence = menu;
  }

  // calls for OnlyMain.java
  private static Main myMain  = new Main();
  public static Main getMain(){
    return myMain;
  }

  enum SeqID { //SequenceID
    SEQ_MENU{
      void after() {
        game.setStageID(menu.getStageID());
        sequence = game;
        f_sequence = SeqID.SEQ_GAME;
      }
    },
    SEQ_GAME{
      void after() {
        f_sequence = SeqID.SEQ_RESULT;
        sequence = result;
      }
    },
    SEQ_RESULT{
      void after() {
        f_sequence = SeqID.SEQ_RANKING;
        sequence = ranking;
      }
    },
    SEQ_RANKING{
      void after() {
        f_sequence = SeqID.SEQ_MENU;
        sequence = menu;
      }
    };

    abstract void after();
  }

  private static SeqID f_sequence; //


  public void setIsClear(boolean tmpIsClear){
    this.f_isClear = tmpIsClear;
  }

  public void setCurrentTime(long tmpCurrentTime){
    this.f_currentTime = tmpCurrentTime;
  }

  // this myMain is executed only once
  void myMain() {
    while(true) {
      sequence.myMain();
      f_sequence.after();
    }
  }

}

