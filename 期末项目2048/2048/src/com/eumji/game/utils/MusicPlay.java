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
		AudioInputStream ais = null;	//音频输入流
		try {
			//通过audiosystem获取音频输入流，还可以转换不同音频格式
			//getaudioinputstream 从file中过去音频输入流
			ais = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//获取音频输入流中的音频格式
		AudioFormat format = ais.getFormat();
		//数据行是可以写入数据的数据行，充当混频器的源
		SourceDataLine auLine=null;
		//获取数据行支持的音频格式的对象
		Info info = new DataLine.Info(SourceDataLine.class, format);
		
		try {
			//获取和info对象中的描述匹配的行
			auLine = (SourceDataLine) AudioSystem.getLine(info);
			//打开具有指定格式和建议缓冲区大小的行。获得所有系统所需资源并可以操作
			auLine.open();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//需要某一数据行执行的数据I/O
		auLine.start();
		int read = 0;
		byte[] buf = new byte[1024];
		while (read != -1){
			try {
				//读取文件
				read = ais.read(buf, 0, buf.length);
				if (read >= 0) {
					//将音频写入混频器
					auLine.write(buf, 0, buf.length);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}finally {
				//关闭操作
				auLine.drain();
				auLine.close();
			}
		}
	}
}