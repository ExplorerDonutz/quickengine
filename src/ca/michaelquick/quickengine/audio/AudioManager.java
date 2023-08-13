package ca.michaelquick.quickengine.audio;

import javax.sound.sampled.Clip;
import java.util.ArrayList;

/**
 * Handles audio for the game
 */
public class AudioManager {

    private final ArrayList<Clip> audioList;

    /**
     * Creates a new audio manager
     */
    public AudioManager() {
        audioList = new ArrayList<>();
    }

    public void stop() {
        for (Clip clip : audioList) {
            clip.close();
        }

        audioList.clear();
    }

    public void playSound(Clip sound) {
        audioList.add(sound);

        sound.setMicrosecondPosition(0);
        sound.start();
    }

    public void playSoundLooping(Clip sound) {
        audioList.add(sound);

        sound.setMicrosecondPosition(0);
        sound.loop(Clip.LOOP_CONTINUOUSLY);
        sound.start();
    }
}
