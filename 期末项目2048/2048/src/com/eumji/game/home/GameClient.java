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
		
		this.setTitle("佛系码农");		//标题
		this.setLocation(450, 180);		//开始位置
		this.setSize(400, 520);			 //大小
		this.setLayout(null);			//设置绝对布局
		//开始按钮设置
		ImageIcon startIcon = new ImageIcon("res/begin.png");
		JButton start = new JButton(startIcon);
		start.setFocusable(false);
		start.setFocusPainted(false);
		start.setContentAreaFilled(false);//消除背景颜色
		start.setBorderPainted(false);	//取消边框
		start.setBounds(5, 10, 120, 30);
		this.add(start);	//添加开始按钮
		//关于按钮设置
		ImageIcon aboutIcon = new ImageIcon("res/help.png");
		JButton about = new JButton(aboutIcon);
		about.setFocusable(false);
		about.setFocusPainted(false);
		about.setContentAreaFilled(false);//消除背景颜色
		about.setBorderPainted(false);	//取消边框
		about.setBounds(160, 10, 98, 30);
		this.add(about);
		//回退按钮设置
		ImageIcon backIcon = new ImageIcon("res/back.png");
		JButton back = new JButton(backIcon);
		back.setBounds(270, 10, 120, 30);
		back.setFocusable(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);//消除背景颜色
		back.setBorderPainted(false);	//取消边框
		this.add(back);
		
		//游戏分数显示
		JLabel score = new JLabel("得分 : 0");
		score.setBounds(40, 45, 120, 50);
		score.setFont(new Font("楷体", Font.CENTER_BASELINE, 18));//设置字体属性
		score.setForeground(new Color(0x000000));
		this.add(score);
		
		//设置是否开启游戏音效
		JCheckBox voiceCheck = new JCheckBox("关闭音乐");
		voiceCheck.setBounds(290, 45, 120, 50);
		voiceCheck.setFont(new Font("楷体", Font.CENTER_BASELINE, 18));
		voiceCheck.setFocusable(false);
		voiceCheck.setBorderPainted(false);
		voiceCheck.setFocusPainted(false);
		voiceCheck.setContentAreaFilled(false);
		this.add(voiceCheck);
		
	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);	//无法最大化
		this.setVisible(true);	//可见
		
		//设置监听
		GameListener listener = new GameListener(this,gameInfo,start,about,back,voiceCheck,score);
		//添加按钮的点击监听
		start.addActionListener(listener);
		about.addActionListener(listener);
		back.addActionListener(listener);
		voiceCheck.addActionListener(listener);
		//添加按键监听
		this.addKeyListener(listener);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//最外层大格子
		g.setColor(new Color(0xB8AAAA));
		g.fillRoundRect(15, 130, 370, 370, 15, 15);
		//里层的每个小格子
		g.setColor(new Color(0xD4C8BA));
		for (int i = 0; i < gameInfo.length ; i++) {
			for (int j = 0; j < gameInfo[0].length; j++) {
				g.fillRoundRect(25+i*90, 140+j*90, 80, 80, 15, 15);
			}		
		}
	
		//设置数组中的各个值得状态
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
					g.setFont(new Font("楷体", Font.PLAIN, 28));
					g.drawString(gameInfo[j][i]+"", 25+i*90+30,140+j*90+50);
					
				}
			}		
		}
	}
	public static void main(String[] args) {
		
		new GameClient();
	}
}
