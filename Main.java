public class Main {

	private int f_stageID; //Title�őI�΂ꂽ�X�e�[�WID
	private boolean f_isClear; //Game���N���A�ŏI��点�����ǂ���
	private long f_currentTime; //Game�ɂ�����������

	public final int WIDTH = 800; //��ʂ̉���
	public final int HEIGHT = 600; //��ʂ̏c��

	private Main() { //�V���O���g���g��
		f_isClear = false;
		f_currentTime = -1;
		f_sequence = SeqID.SEQ_TITLE;
	}
	private static Main myMain  = new Main();
	public static Main getMain(){
		return myMain;
	}

	enum SeqID { //SequenceID�A��������Ɏ��̃V�[�P���X�����߂�
		SEQ_MENU,
		SEQ_GAME,
		SEQ_RESULT,
		SEQ_RANKING,
	}

	public SeqID f_sequence; //�ړ����class�̖��O

	public int getStageID() {
		return this.f_stageID;
	}

	public void setStageID(int tmpStageID) {
		this.f_stageID = tmpStageID;
	}

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

	public SeqID getNext(){
		return this.f_sequence;
	}

	public void setNext(SeqID tmpNext) {
		this.f_sequence = tmpNext;
	}

	public void myMain() throws Exception {

		switch (f_sequence) { //f_next�Ŏ��̃V�[�P���X��
		case SEQ_MENU:
			Menu menu = new Menu(this); //���̃N���X��n��
			menu.myMain(); //We must make this method
			break; //to comment 'case SEQ_MENU...' is a last resort

		case SEQ_GAME:
			Game game = new Game(this);
			game.myMain();
			break;

		case SEQ_RESULT:
			//Result result = new Result(this);
			//result.myMain();
			break;

		case SEQ_RANKING:
			//Ranking ranking = new Ranking(this);
			//ranking.myMain();
			break;
		}

	}

}
