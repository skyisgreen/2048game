import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SetSound extends Thread{
	private String filename;
	public SetSound(String filename) {
		// TODO Auto-generated constructor stub
		this.filename = "res/"+filename;
	}
	
	@Override
	public void run() {
		MusicPlay.play(filename);
	}
}
