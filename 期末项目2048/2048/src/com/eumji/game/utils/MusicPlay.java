package com.eumji.game.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

public class MusicPlay{
	public static void play(String fileName){
		File soundFile = new File(fileName);
		AudioInputStream ais = null;	//��Ƶ������
		try {
			//ͨ��audiosystem��ȡ��Ƶ��������������ת����ͬ��Ƶ��ʽ
			//getaudioinputstream ��file�й�ȥ��Ƶ������
			ais = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//��ȡ��Ƶ�������е���Ƶ��ʽ
		AudioFormat format = ais.getFormat();
		//�������ǿ���д�����ݵ������У��䵱��Ƶ����Դ
		SourceDataLine auLine=null;
		//��ȡ������֧�ֵ���Ƶ��ʽ�Ķ���
		Info info = new DataLine.Info(SourceDataLine.class, format);
		
		try {
			//��ȡ��info�����е�����ƥ�����
			auLine = (SourceDataLine) AudioSystem.getLine(info);
			//�򿪾���ָ����ʽ�ͽ��黺������С���С��������ϵͳ������Դ�����Բ���
			auLine.open();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//��Ҫĳһ������ִ�е�����I/O
		auLine.start();
		int read = 0;
		byte[] buf = new byte[1024];
		while (read != -1){
			try {
				//��ȡ�ļ�
				read = ais.read(buf, 0, buf.length);
				if (read >= 0) {
					//����Ƶд���Ƶ��
					auLine.write(buf, 0, buf.length);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}finally {
				//�رղ���
				auLine.drain();
				auLine.close();
			}
		}
	}
}