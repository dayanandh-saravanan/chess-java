/** allows for sound effects to be added to the game
 * @author Dayanidi Saravanan
 * 6/1/2024
 * */ 
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect {
    /** Sound effect file */
    Clip clip;
    /** reads the file name and sets it to the sound effect file
     * 
     * @param fileName              must be a .wav file
     */
    public void setFile(String fileName){
        try{
            File file = new File(fileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch(Exception e){

        }
    }
    /** plays the sound effect */
    public void play(){
        if(clip != null){

        
        clip.setFramePosition(0);
        clip.start();
        }
    }
}
