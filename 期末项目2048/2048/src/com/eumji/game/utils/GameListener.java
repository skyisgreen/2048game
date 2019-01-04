import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.eumji.game.home.GameClient;

public class GameListener extends KeyAdapter implements ActionListener {
	private boolean hasMove = false;
	private GameClient client;	//客户端
	private int[][] gameInfo;	//记录游戏数据的数组
	private Random random = new Random();	//随机数对象 随机生成出现的数字
	private int backUp[][] = new int[4][4];	//回退的数据数组
	private int saveOnce[][] = new int[4][4];	//复活的数组
	public JLabel ScoreJLabel;	//分数面板
	int score;	//分数
	int backScore;	//备份分数
	public JCheckBox voiceCheck;	//静音
	public JButton newGame;	//新游戏按钮
	public JButton about;	//关于按钮
	public JButton back;	//回退一步按钮
	private boolean hasWin=false;	//是否已经胜利
	private boolean hasBack = false;	//是否已经回退
	private boolean hasSound = false;	//是否有声音
	
	
	
	public GameListener(GameClient gameClient, int[][] gameInfo, JButton start, JButton about, JButton back,
			JCheckBox voiceCheck, JLabel score) {
		this.client = gameClient;
		this.gameInfo = gameInfo;
		this.newGame = start;
		this.about = about;
		this.back = back;
		this.ScoreJLabel = score;
		this.voiceCheck = voiceCheck;
	
	}
	//发生点击事件时调用 的方法
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			hasWin = false;
			//初始化游戏信息
			for (int i = 0; i < gameInfo.length; i++) {
				for (int j = 0; j < gameInfo[0].length; j++) {
					gameInfo[i][j] = 0;
				}
			}
				
			score = 0;
			ScoreJLabel.setText("游戏分数： "+score);
			//随机生成初始数据的位置
			int x1 = random.nextInt(4);
			int x2 = random.nextInt(4);
			int y1 = random.nextInt(4);
			int y2 = random.nextInt(4);
			
			while (x1==x2&&y1==y2) {
				x1 = random.nextInt(4);
				x2 = random.nextInt(4);
				y1 = random.nextInt(4);
				y2 = random.nextInt(4);
			}
			//随机初始的值
			int value1 = random.nextInt(2)*2+2;
			int value2 = random.nextInt(2)*2+2;
			
			gameInfo[y1][x1] = value1;
			gameInfo[y2][x2] = value2;
			//绘制图形
			client.paint(client.getGraphics());
					
		}
		else if (e.getSource()==about) {
			JOptionPane.showMessageDialog(client, "GAME MESSAGE\n"
					+ "       佛系码农组\n"
					+ "    开发者：张荣翊 、姚锦涛、廖铠琦、董晋佑、陆泳桥\n"
					
					
					+"上下左右为移动方向键\n"
					+ " 若游戏出现了2048数字则游戏取得胜利\n"
					+ " 祝您游戏愉快");
		}
		else if (e.getSource()==back&&hasBack==false) {
			hasBack = true;
			score = backScore;
			ScoreJLabel.setText("分数： "+score);
			//数组的复制无法直接使用二维数组 无法辨别
			for (int i = 0; i < backUp.length; i++) {
				gameInfo[i] = backUp[i].clone(); 
			}
			client.paint(client.getGraphics());
		
		}
		else if(e.getSource() == voiceCheck){
			if (voiceCheck.isSelected()) {
				hasSound = false;
			}else {
				hasSound = true;
			}
			
		}
		System.out.println("OK 成功");
	
	}
	

	public void keyPressed(KeyEvent e) {
		
		int NumCounter = 0;	//统计数据个数，是否满了
		int NumNearCounter = 0;	//统计相邻格子相同的数字个数。
		
		hasBack = false;
		//备份分数
		if (backUp != null || backUp.length != 0) {
			backScore = score;
			
		}
		//备份游戏数组
		for (int i = 0; i < gameInfo.length; i++) {
			backUp[i] = gameInfo[i].clone();
		}
		//在没有赢得情况下 判断哪个操作
		if (!hasWin) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:	//向上
				MoveSound(hasSound);
				//移动在一起
				moveUp();
				//在移动的方向合并相同的快 修改分数
				for(int i =0 ;i<gameInfo[0].length;i++){ //横向
					for (int j = 0; j < gameInfo.length; j++) { //纵向
							if (j+1<gameInfo.length&&(gameInfo[j][i]!=0||gameInfo[j+1][i]!=0)&&gameInfo[j][i]==gameInfo[j+1][i]) {
								
									MergeSound(hasSound);
									
									gameInfo[j][i]+=gameInfo[j+1][i];
									gameInfo[j+1][i]=0;
									score += gameInfo[j][i];
									hasMove = true;
									if (gameInfo[j][i]==2048) {
										hasWin = true;
									}
								
							}
						}
					}
			
				moveUp();
				break;
				
			case KeyEvent.VK_RIGHT://向右
				MoveSound(hasSound);
				moveRight();
				for(int j =gameInfo.length-1 ;j>=0;j--){//数组里有多少个数组
					for(int i =gameInfo[0].length-1 ;i>=0;i--){ //每一个数组里面的数组的长度
						if (i+1<gameInfo.length&&gameInfo[j][i]!=0&&gameInfo[j][i]==gameInfo[j][i+1]) {
							
								MergeSound(hasSound);
								gameInfo[j][i+1] += gameInfo[j][i] ;
								gameInfo[j][i] = 0;
								score += gameInfo[j][i+1];
								hasMove = true;
								if (gameInfo[j][i]==2048) {
									hasWin = true;
								}
					
						}
					}
				}
				moveRight();
				break;
			case KeyEvent.VK_LEFT:	//向左
				MoveSound(hasSound);
				moveLeft();
				
				for (int i = 0; i < gameInfo.length; i++) {
					for (int j = 0; j < gameInfo[0].length; j++) {
						if (j+1<gameInfo.length&&(gameInfo[i][j]==gameInfo[i][j+1])&&gameInfo[i][j]!=0) {
							MergeSound(hasSound);
							gameInfo[i][j] += gameInfo[i][j+1];
							gameInfo[i][j+1] = 0;
							hasMove = true;
							score += gameInfo[i][j];
							if (gameInfo[i][j]==2048) {
								hasWin = true;
							}
						}
					}
					
				}
					
				moveLeft();
					
				break;
						
			case KeyEvent.VK_DOWN:
				MoveSound(hasSound);
				moveDown();
				
				for (int i = gameInfo[0].length-1; i >=0; i--) {//横向
					for (int j = gameInfo.length-1; j >=0; j--) {//纵向
						if (j+1<gameInfo[0].length&&(gameInfo[j][i]==gameInfo[j+1][i])&&gameInfo[j][i]!=0) {
							MergeSound(hasSound);
							
							gameInfo[j+1][i] += gameInfo[j][i];
							gameInfo[j][i] = 0;
							hasMove = true;
							score += gameInfo[j+1][i];
							if (gameInfo[j+1][i]==2048) {
								hasWin = true;
							}
						}
						
					}
				}
					
				moveDown();
				break;
				}
			//查看相邻的快有多少相等
			for (int i = 0; i < gameInfo.length-1; i++) {
				for (int j = 0; j < gameInfo[0].length-1; j++) {
					if (gameInfo[i][j]==gameInfo[i][j+1]&&gameInfo[i][j]!=0) {
						NumNearCounter++;
					}
					if (gameInfo[i][j]==gameInfo[i+1][j]&&gameInfo[j][j]!=0) {
						NumNearCounter++;
					}
					if (gameInfo[gameInfo.length-1][j] == gameInfo[gameInfo.length-1][j+1]&&gameInfo[gameInfo.length-1][j]!=0) {
						NumNearCounter++;
						
					}
					if (gameInfo[i][gameInfo[0].length-1]==gameInfo[i+1][gameInfo[0].length-1]&&gameInfo[i][gameInfo[0].length-1]!=0) {
						NumNearCounter++;
					}
				}
			}
			//记录当前已经有多少块已经使用
			for (int i = 0; i < gameInfo.length; i++) {
				for (int j = 0; j < gameInfo[0].length; j++) {
					if (gameInfo[i][j]!=0) {
						NumCounter++;	
					}
				}
			}
			//在有移动的情况下修改分数 并产生新的方块
			if (hasMove) {
				ScoreJLabel.setText("分数: "+score);
				int x1 = random.nextInt(4);
				int y1 = random.nextInt(4);
				while(gameInfo[y1][x1]!=0){
					x1 = random.nextInt(4);
					y1 = random.nextInt(4);
				}
				
				int value1 = random.nextInt(2)*2+2;
				gameInfo[y1][x1] = value1;
				hasMove = false;
			}
			//是否已经胜利
			if (hasWin) {
				client.paint(client.getGraphics());
				JOptionPane.showMessageDialog(client, "恭喜您获得胜利! \n"
						+ "您的最终分数为: "+score);
			}
			//是否已经失败 
			if (NumCounter==16&&NumNearCounter==0) {
				JOptionPane.showMessageDialog(client, "很抱歉您无法再进行组合了\n"
						+ "您可以选择后退一步或者重新开始\n"
						+ "加油！您一定可以胜利的！");
			}
			
			client.paint(client.getGraphics());
			
			}
		}
	
	
	//根据是否勾选静音 开启声音线程
	public void MoveSound(boolean hasSound){
		if (hasSound == true) {
			new SetSound("move.wav").start();
		}
	}
	
	public void MergeSound(boolean hasSound){
		if (hasSound == true) {
			new SetSound("merge.wav").start();
		}
	}
	
	//向上补齐空位
	public void moveUp(){
		for(int i =0 ;i<gameInfo[0].length;i++){//横向
			for (int j = 0; j < gameInfo.length; j++) { //纵向
					if (gameInfo[j][i]!=0) {
					int tmp = gameInfo[j][i];
					int num = j-1;
					//先循环上移到一起
					while (num >= 0 && gameInfo[num][i]==0){
						gameInfo[num][i] = tmp;
						gameInfo[num+1][i] = 0;
						num--;
						hasMove = true;
					}
				}
			}
		}
	}
	//向下移动补齐空位 不合并
	public void moveDown(){
		for(int i =gameInfo[0].length-1 ;i>=0;i--){
			for(int j =gameInfo.length-1 ;j>=0;j--){
				if (gameInfo[j][i]!=0) {
					int tmp = gameInfo[j][i];
					int num = j+1;
					//先循环向下移到一起
					while (num < gameInfo.length && gameInfo[num][i]==0){
						gameInfo[num][i] = tmp;
						gameInfo[num-1][i] = 0;
						num++;
						hasMove = true;
					}
				}
			}
		}
	}
	/**
	 * 向左移动补齐空位 不合并
	 */
	public void moveLeft(){
		for(int i =0 ;i<gameInfo[0].length;i++){//高度
			for (int j = 0; j < gameInfo.length; j++) {//宽度
					if (gameInfo[i][j]!=0) {
					int tmp = gameInfo[i][j];
					int num = j-1;
					//
					while (num >= 0 && gameInfo[i][num]==0){
						gameInfo[i][num] = tmp;
						gameInfo[i][num+1] = 0;
						num--;
						hasMove = true;
					}
				}
			}
		}
	}
	//向右移动补齐空位 不合并
	public void moveRight(){
		for(int i =gameInfo.length-1 ;i>=0;i--){
			for(int j =gameInfo[0].length-1 ;j>=0;j--){
				if (gameInfo[i][j]!=0) {
					int tmp = gameInfo[i][j];
					int num = j+1;
					//先循环向下移到一起
					while (num < gameInfo.length && gameInfo[i][num]==0){
						gameInfo[i][num] = tmp;
						gameInfo[i][num-1] = 0;
						num++;
						hasMove = true;
					}
				}
			}
		}
	}

}
