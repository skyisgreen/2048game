package com.eumji.game.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.eumji.game.utils.GameListener;

public class GameClient extends JFrame {
	int[][] gameInfo = new int[4][4];
	public GameClient() {
		
		this.setTitle("��ϵ��ũ");		//����
		this.setLocation(450, 180);		//��ʼλ��
		this.setSize(400, 520);			 //��С
		this.setLayout(null);			//���þ��Բ���
		//��ʼ��ť����
		ImageIcon startIcon = new ImageIcon("res/begin.png");
		JButton start = new JButton(startIcon);
		start.setFocusable(false);
		start.setFocusPainted(false);
		start.setContentAreaFilled(false);//����������ɫ
		start.setBorderPainted(false);	//ȡ���߿�
		start.setBounds(5, 10, 120, 30);
		this.add(start);	//��ӿ�ʼ��ť
		//���ڰ�ť����
		ImageIcon aboutIcon = new ImageIcon("res/help.png");
		JButton about = new JButton(aboutIcon);
		about.setFocusable(false);
		about.setFocusPainted(false);
		about.setContentAreaFilled(false);//����������ɫ
		about.setBorderPainted(false);	//ȡ���߿�
		about.setBounds(160, 10, 98, 30);
		this.add(about);
		//���˰�ť����
		ImageIcon backIcon = new ImageIcon("res/back.png");
		JButton back = new JButton(backIcon);
		back.setBounds(270, 10, 120, 30);
		back.setFocusable(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);//����������ɫ
		back.setBorderPainted(false);	//ȡ���߿�
		this.add(back);
		
		//��Ϸ������ʾ
		JLabel score = new JLabel("�÷� : 0");
		score.setBounds(40, 45, 120, 50);
		score.setFont(new Font("����", Font.CENTER_BASELINE, 18));//������������
		score.setForeground(new Color(0x000000));
		this.add(score);
		
		//�����Ƿ�����Ϸ��Ч
		JCheckBox voiceCheck = new JCheckBox("�ر�����");
		voiceCheck.setBounds(290, 45, 120, 50);
		voiceCheck.setFont(new Font("����", Font.CENTER_BASELINE, 18));
		voiceCheck.setFocusable(false);
		voiceCheck.setBorderPainted(false);
		voiceCheck.setFocusPainted(false);
		voiceCheck.setContentAreaFilled(false);
		this.add(voiceCheck);
		
	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);	//�޷����
		this.setVisible(true);	//�ɼ�
		
		//���ü���
		GameListener listener = new GameListener(this,gameInfo,start,about,back,voiceCheck,score);
		//��Ӱ�ť�ĵ������
		start.addActionListener(listener);
		about.addActionListener(listener);
		back.addActionListener(listener);
		voiceCheck.addActionListener(listener);
		//��Ӱ�������
		this.addKeyListener(listener);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//���������
		g.setColor(new Color(0xB8AAAA));
		g.fillRoundRect(15, 130, 370, 370, 15, 15);
		//����ÿ��С����
		g.setColor(new Color(0xD4C8BA));
		for (int i = 0; i < gameInfo.length ; i++) {
			for (int j = 0; j < gameInfo[0].length; j++) {
				g.fillRoundRect(25+i*90, 140+j*90, 80, 80, 15, 15);
			}		
		}
	
		//���������еĸ���ֵ��״̬
		for (int i = 0; i < gameInfo[0].length ; i++) {
			for (int j = 0; j < gameInfo.length; j++) {
				if(gameInfo[j][i]!=0){
					switch (gameInfo[j][i]) {
					case 2:
						g.setColor(new Color(0xEEE4D8));
						
						break;
					case 4:
						g.setColor(new Color(0xEDDDC7));
						break;
					
					case 8:
						g.setColor(new Color(0xF2AE7A));
						break;
					
					case 16:
						g.setColor(new Color(0xF58F63));
						break;
						
					case 32:
						g.setColor(new Color(0xF6795F));
						break;
						
					case 64:
						g.setColor(new Color(0xF6523A));
						break;
						
					case 128:
						g.setColor(new Color(0xEDE286));
						break;
						
					case 256:
						g.setColor(new Color(0xEDCF67));
						break;
						
					case 512:
						g.setColor(new Color(0xEDCE47));
						break;
						
					case 1024:
						g.setColor(new Color(0xF0BB39));
						break;
					
					case 2048:
						g.setColor(new Color(0xDEA62C));
						break;
						
					default:
						g.setColor(new Color(0x000000));
						break;
					}
			
					g.fillRoundRect(25+i*90, 140+j*90, 80, 80, 15, 15);
					g.setColor(new Color(0x000000));
					g.setFont(new Font("����", Font.PLAIN, 28));
					g.drawString(gameInfo[j][i]+"", 25+i*90+30,140+j*90+50);
					
				}
			}		
		}
	}
	public static void main(String[] args) {
		
		new GameClient();
	}
}
