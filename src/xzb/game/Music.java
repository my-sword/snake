package xzb.game;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * 负责播放音频
 * 调用方法
 * new Music(filepath).start();
 * filepath为音频文件的路径，如果直接使用文件名，文件得放在项目文件夹的最外层目录
 */
public class Music extends Thread{ 	 
    private String filepath;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb   new byte[128x1024]

    public Music(String wavfile) { 
        filepath = wavfile;
    } 
    	    
    public void run() { 
        File soundFile = new File(filepath); 
        AudioInputStream audioInputStream = null;
        try { 
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);//音频多媒体输入流
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);//设置音频格式信息
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { //是否结束
                nBytesRead = audioInputStream.read(abData, 0, abData.length);//读取128k
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);//持续写入128k
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();//清除数据缓存
            auline.close();
        } 
    } 
}
