import javax.swing.*;

public class DifficultyWindow extends JFrame{

    public DifficultyWindow(int difficulty) {
        super.setTitle("Minesweeper");
        super.setSize(500, 200);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        JLabel minesweeper = new JLabel("Minesweeper");
        JButton beginner = new JButton("Beginner");
        JButton intermediate = new JButton("Intermediate");
        JButton advanced = new JButton("Advanced");

        minesweeper.setBounds(200, 10, 100, 40);
        beginner.setBounds(40, 60, 100, 40);
        intermediate.setBounds(180, 60, 120, 40);
        advanced.setBounds(340, 60, 100, 40);

        super.add(minesweeper);
        super.add(beginner);
        super.add(intermediate);
        super.add(advanced);

        //create the actionbutton listeners
    }

}