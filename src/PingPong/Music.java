// Saivenkat Jilla
// May 30th, 2022
// This class was built to play sounds and music.


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.File;

public class Music {
    private Clip clip;

    // Constructor for Music, called in GamePanel
    // Initializes clip
    public Music() {
        try {
            clip = AudioSystem.getClip(); // Initialize clip
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // plays music/sound effects based on file
    public void playMusic(String fileName) {
        try {
            File file = new File(fileName); // Create a file object from the file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream); // opens the clip
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.start(); // starts the clip
    }
}
