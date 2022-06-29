import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    public void soundpoint() throws LineUnavailableException {

        AudioInputStream Stream;
        try {
            Stream = AudioSystem.getAudioInputStream(new File("src/point.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(Stream);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-30);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void gameover() throws LineUnavailableException {

        AudioInputStream Stream;
        try {
            Stream = AudioSystem.getAudioInputStream(new File("src/gameover.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(Stream);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}

