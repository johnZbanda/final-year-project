import java.awt.event.*;
import javax.swing.*;

public class DifficultyWindow extends JFrame implements ActionListener{

    Minesweeper game;
    JLabel title;
    JButton beginner;
    JButton intermediate;
    JButton advanced;

    public DifficultyWindow(int difficulty) {
        setLayout(null);

        super.setTitle("Minesweeper");
        super.setSize(500, 200);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        title = new JLabel("Minesweeper");
        beginner = new JButton("Beginner");
        intermediate = new JButton("Intermediate");
        advanced = new JButton("Advanced");

        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);

        title.setBounds(200, 10, 100, 40);
        beginner.setBounds(40, 60, 100, 40);
        intermediate.setBounds(180, 60, 120, 40);
        advanced.setBounds(340, 60, 100, 40);

        super.add(title);
        super.add(beginner);
        super.add(intermediate);
        super.add(advanced);

    }

    public void actionPerformed(ActionEvent e) { //separate the clicks
        String str = e.getActionCommand();
        if (str.equals("Beginner")) {
            beginner.setText("Clicked");
        } else if (str.equals("Intermediate")) {
            intermediate.setText("Clicked");

        } else if (str.equals("Advanced")) {
            advanced.setText("Clicked");
        }
        
        
        
      
    }
}