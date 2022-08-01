import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.io.IOException;


public class GameFrame extends JFrame {
    public GameFrame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        this.add(new GamePanel());
        this.setTitle("Snake Game");
        ImageIcon img = new ImageIcon("snake_game.png");
        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);

    }
}
