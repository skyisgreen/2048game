package com.eumji.game.utils;

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
	private GameClient client;	//�ͻ���
	private int[][] gameInfo;	//��¼��Ϸ���ݵ�����
	private Random random = new Random();	//��������� ������ɳ��ֵ�����
	private int backUp[][] = new int[4][4];	//���˵���������
	private int saveOnce[][] = new int[4][4];	//���������
	public JLabel ScoreJLabel;	//�������
	int score;	//����
	int backScore;	//���ݷ���
	public JCheckBox voiceCheck;	//����
	public JButton newGame;	//����Ϸ��ť
	public JButton about;	//���ڰ�ť
	public JButton back;	//����һ����ť
	private boolean hasWin=false;	//�Ƿ��Ѿ�ʤ��
	private boolean hasBack = false;	//�Ƿ��Ѿ�����
	private boolean hasSound = false;	//�Ƿ�������
	
	
	
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
	//��������¼�ʱ���� �ķ���
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			hasWin = false;
			//��ʼ����Ϸ��Ϣ
			for (int i = 0; i < gameInfo.length; i++) {
				for (int j = 0; j < gameInfo[0].length; j++) {
					gameInfo[i][j] = 0;
				}
			}
				
			score = 0;
			ScoreJLabel.setText("��Ϸ������ "+score);
			//������ɳ�ʼ���ݵ�λ��
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
			//�����ʼ��ֵ
			int value1 = random.nextInt(2)*2+2;
			int value2 = random.nextInt(2)*2+2;
			
			gameInfo[y1][x1] = value1;
			gameInfo[y2][x2] = value2;
			//����ͼ��
			client.paint(client.getGraphics());
					
		}
		else if (e.getSource()==about) {
			JOptionPane.showMessageDialog(client, "GAME MESSAGE\n"
					+ "       ��ϵ��ũ��\n"
					+ "    �����ߣ������ ��Ҧ���Ρ��������������ӡ�½Ӿ��\n"
					
					
					+"��������Ϊ�ƶ������\n"
					+ " ����Ϸ������2048��������Ϸȡ��ʤ��\n"
					+ " ף����Ϸ���");
		}
		else if (e.getSource()==back&&hasBack==false) {
			hasBack = true;
			score = backScore;
			ScoreJLabel.setText("������ "+score);
			//����ĸ����޷�ֱ��ʹ�ö�ά���� �޷����
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
		System.out.println("OK �ɹ�");
	
	}
	

	public void keyPressed(KeyEvent e) {
		
		int NumCounter = 0;	//ͳ�����ݸ������Ƿ�����
		int NumNearCounter = 0;	//ͳ�����ڸ�����ͬ�����ָ�����
		
		hasBack = false;
		//���ݷ���
		if (backUp != null || backUp.length != 0) {
			backScore = score;
			
		}
		//������Ϸ����
		for (int i = 0; i < gameInfo.length; i++) {
			backUp[i] = gameInfo[i].clone();
		}
		//��û��Ӯ������� �ж��ĸ�����
		if (!hasWin) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:	//����
				MoveSound(hasSound);
				//�ƶ���һ��
				moveUp();
				//���ƶ��ķ���ϲ���ͬ�Ŀ� �޸ķ���
				for(int i =0 ;i<gameInfo[0].length;i++){ //����
					for (int j = 0; j < gameInfo.length; j++) { //����
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
				
			case KeyEvent.VK_RIGHT://����
				MoveSound(hasSound);
				moveRight();
				for(int j =gameInfo.length-1 ;j>=0;j--){//�������ж��ٸ�����
					for(int i =gameInfo[0].length-1 ;i>=0;i--){ //ÿһ���������������ĳ���
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
			case KeyEvent.VK_LEFT:	//����
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
				
				for (int i = gameInfo[0].length-1; i >=0; i--) {//����
					for (int j = gameInfo.length-1; j >=0; j--) {//����
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
			//�鿴���ڵĿ��ж������
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
			//��¼��ǰ�Ѿ��ж��ٿ��Ѿ�ʹ��
			for (int i = 0; i < gameInfo.length; i++) {
				for (int j = 0; j < gameInfo[0].length; j++) {
					if (gameInfo[i][j]!=0) {
						NumCounter++;	
					}
				}
			}
			//�����ƶ���������޸ķ��� �������µķ���
			if (hasMove) {
				ScoreJLabel.setText("����: "+score);
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
			//�Ƿ��Ѿ�ʤ��
			if (hasWin) {
				client.paint(client.getGraphics());
				JOptionPane.showMessageDialog(client, "��ϲ�����ʤ��! \n"
						+ "�������շ���Ϊ: "+score);
			}
			//�Ƿ��Ѿ�ʧ�� 
			if (NumCounter==16&&NumNearCounter==0) {
				JOptionPane.showMessageDialog(client, "�ܱ�Ǹ���޷��ٽ��������\n"
						+ "������ѡ�����һ���������¿�ʼ\n"
						+ "���ͣ���һ������ʤ���ģ�");
			}
			
			client.paint(client.getGraphics());
			
			}
		}
	
	
	//�����Ƿ�ѡ���� ���������߳�
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
	
	//���ϲ����λ
	public void moveUp(){
		for(int i =0 ;i<gameInfo[0].length;i++){//����
			for (int j = 0; j < gameInfo.length; j++) { //����
					if (gameInfo[j][i]!=0) {
					int tmp = gameInfo[j][i];
					int num = j-1;
					//��ѭ�����Ƶ�һ��
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
	//�����ƶ������λ ���ϲ�
	public void moveDown(){
		for(int i =gameInfo[0].length-1 ;i>=0;i--){
			for(int j =gameInfo.length-1 ;j>=0;j--){
				if (gameInfo[j][i]!=0) {
					int tmp = gameInfo[j][i];
					int num = j+1;
					//��ѭ�������Ƶ�һ��
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
	 * �����ƶ������λ ���ϲ�
	 */
	public void moveLeft(){
		for(int i =0 ;i<gameInfo[0].length;i++){//�߶�
			for (int j = 0; j < gameInfo.length; j++) {//���
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
	//�����ƶ������λ ���ϲ�
	public void moveRight(){
		for(int i =gameInfo.length-1 ;i>=0;i--){
			for(int j =gameInfo[0].length-1 ;j>=0;j--){
				if (gameInfo[i][j]!=0) {
					int tmp = gameInfo[i][j];
					int num = j+1;
					//��ѭ�������Ƶ�һ��
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
