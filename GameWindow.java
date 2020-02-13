import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Spring for 

public class GameWindow extends JFrame implements ActionListener{

    public GameWindow(char[][] board, int dimensions, int mines, boolean[][] isMine) {
        super.setTitle("Minesweeper");
        super.setSize(900, 600);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }
}