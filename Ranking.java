import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Ranking extends Sequence implements ActionListener { 

	public Ranking() { 
		myInit();
		printWindow();   
	}

	
	ArrayList<Long> rankingData = new ArrayList<Long>();
	private long currentTime;
	private int next;
	public JButton button;
	public int stageID;
	private boolean isClear;
	final int NUMBER_OF_STAGE = 9;
	private Container container;  
	private boolean leave;

	public void myMain() { 
		readRanking();
		if(isClear){
			setRanking(stageID, currentTime);
			writeRanking();
		}
		super.seqInit(null);
		updateLabel();
		leave = false;
		while(!leave) {
			try {
				Thread.sleep(1000 / Main.FPS);
			} catch (InterruptedException e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}
		super.seqEnd(null);
		System.out.println("Ranking End");
	}

	
	
	private void myInit() {
		for (int i = 0; i < NUMBER_OF_STAGE; i++) {
			rankingData.add(i, -1L);
		}
	}

	public void setValues(int stageID,boolean isClear, long currentTime) {
		this.stageID = stageID;
		this.isClear = isClear;
		this.currentTime = currentTime;
	}

	
	public long getTopRecord(int stageID) {
		return rankingData.get(3 * (stageID - 1));
	}

	
	private void setRanking(int stageID, long currentTime) {

		for (int i = 0; i < 3; i++) {
			if(rankingData.get((stageID * 3) + i) >= currentTime || rankingData.get((stageID * 3) + i) == -1){
				switch(i) {
				case 0:
					rankingData.set((stageID * 3) + 2, rankingData.get((stageID * 3) + 1));
					rankingData.set((stageID * 3) + 1, rankingData.get(stageID * 3));
					rankingData.set((stageID * 3) + i, currentTime);
					break;
				case 1:
					rankingData.set((stageID * 3) + 2, rankingData.get((stageID * 3) + 1));
					rankingData.set((stageID * 3) + i, currentTime);
					break;
				case 2:
					rankingData.set((stageID * 3) + i, currentTime);
					break;
				}
				break;
			} else {
			}
		}
	}

	
	private ArrayList<Long> getRanking() {
		return this.rankingData;
	}

	
	private void writeRanking() {
		try {
			
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							new File("./RankingDB.csv"), false), "Shift_JIS")));
			
			for (int i = 0; i < NUMBER_OF_STAGE - 1; i++) {
				pw.print(rankingData.get(i) + ",");
			}
			pw.println(rankingData.get(NUMBER_OF_STAGE - 1) + "");
			pw.close();

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	
	private void readRanking() {
		try {
			
			FileReader fr = new FileReader("./RankingDB.csv");
			BufferedReader br = new BufferedReader(fr);

			
			String line;
			StringTokenizer token;
			while ((line = br.readLine()) != null) {
				
				token = new StringTokenizer(line, ",");

				
				int j = 0;
				while (token.hasMoreTokens()) {
					rankingData.set(j, Long.parseLong(token.nextToken()));
					j += 1;
				}
			}
			br.close();
		} catch (IOException ex) {
			
			ex.printStackTrace();
		}
	}


	
	public void setNext(int next) {
		this.next = next;
	}

	
	public int getNext() {
		return this.next;
	}

	
	

	
	private void printWindow() {
		initFrame();
		makeLabel();
		makeButtonOK();
	}

	
	private void initFrame() {
		container = super.f_frame.getContentPane();
		container.setLayout(null);
	}
	
	private void makeButtonOK() {
		int buttonSizeX = 80;
		int buttonSizeY = 30;
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(this);
		buttonOK.setBounds(Main.WIDTH/2-buttonSizeX/2, 625, buttonSizeX, buttonSizeY);
		super.f_frame.getContentPane().add(buttonOK);
	}

	
	private boolean getIsExist(long rankingData) {
		if (rankingData == -1L) {
			return false;
		} else {
			return true;
		}
	}

	
	private JLabel labelTitle   = new JLabel();
	JLabel labelStage1          = new JLabel();
	JLabel labelStage2          = new JLabel();
	JLabel labelStage3          = new JLabel();
	JLabel []labelTime; 

	
	private void makeLabel(){

		Font fontLabel_32 = new Font("Arial", Font.PLAIN,32);
		Font fontLabel_50= new Font("Arial", Font.PLAIN,50);
		Font fontLabel_100= new Font("Arial", Font.PLAIN,100);


		addLabel(labelTitle, fontLabel_100, 0, 35, Main.WIDTH, 100);
		labelTime = new JLabel[NUMBER_OF_STAGE];

		
		for (int i = 0; i < NUMBER_OF_STAGE; i++) {
			int j;
			if (0 <= i && i <= 2) {
				j = -345;
				addLabel(labelStage1, fontLabel_32, j, 35, Main.WIDTH, 300);
			} else if (3 <= i && i <= 5) {
				j = 0;
				addLabel(labelStage2, fontLabel_32, j, 35, Main.WIDTH, 300);
			} else {
				j = 345;
				addLabel(labelStage3, fontLabel_32, j, 35, Main.WIDTH, 300);
			}
			labelTime[i] = new JLabel();
			addLabel(labelTime[i], fontLabel_50, j, 120 + ((i % 3) * 120), Main.WIDTH, 300);
		}
	}

	private void updateLabel() {
		String noTime = " -- : -- ";
		labelTitle.setText("RANKING");
		labelStage1.setText("EASY");
		labelStage2.setText("NORMAL");
		labelStage3.setText("HARD");
		for (int i = 0; i < NUMBER_OF_STAGE; i++){
			labelTime[i].setText(getIsExist(rankingData.get(i)) ? getCurrentTime(rankingData.get(i)) : noTime);
		}

	}

	
	private void addLabel(JLabel label, Font font, int x, int y, int width, int height) {
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(font);
		label.setBounds(x, y, width, height);
		container.add(label);
	}

	
	private String getCurrentTime(long time) {
		String strTime;
		strTime = String.valueOf( String.format("%02d", time / 60000 )); 
		strTime += " ' ";
		strTime +=  String.valueOf( String.format("%02d",(time % 60000) / 1000 )); 
		strTime += " \" ";
		strTime +=  String.valueOf( String.format("%02d",(time % 60000) % 1000 /10)); 
		return strTime;
	}

	
	public void actionPerformed(ActionEvent e) {
		leave = true;
	}



	


}
