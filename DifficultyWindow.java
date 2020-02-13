import java.awt.event.*;

import javax.swing.*;

public class DifficultyWindow extends JFrame implements ActionListener{

    JLabel minesweeper;
    JButton beginner;
    JButton intermediate;
    JButton advanced;
    public DifficultyWindow(int difficulty) {
        super.setTitle("Minesweeper");
        super.setSize(500, 200);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        minesweeper = new JLabel("Minesweeper");
        beginner = new JButton("Beginner");
        intermediate = new JButton("Intermediate");
        advanced = new JButton("Advanced");

        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);

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

    public void actionPerformed(ActionEvent e) {
        beginner.setText("Clicked");
        intermediate.setText("Clicked");
        advanced.setText("Clicked");
    }
}