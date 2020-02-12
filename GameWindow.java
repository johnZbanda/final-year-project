import javax.swing.JFrame;

public class GameWindow extends JFrame{

    public GameWindow() {
        super.setTitle("Minesweeper");
        super.setSize(900, 600);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }


}