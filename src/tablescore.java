import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

public class tablescore {

    public static void tableScore() {

        int score = 0;

        JFrame frame = new JFrame("Score table");
        frame.setSize(300,300);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score: " + score);
        scoreLabel.setBounds(100, 100, 100, 100);
        scoreLabel.setBackground(Color.WHITE);

        frame.add(scoreLabel);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }

    
    
}
