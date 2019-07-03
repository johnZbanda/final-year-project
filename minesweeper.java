import java.util.*;

public class minesweeper {

    //list of things to do
    
    /*
    - Initialise the board for different difficulties - Done
    - Play the game (multiple sub sections)
    . Click (or type) a space
    . indicate how many bombs are nearby
    . Game Over if bomb is found
    . Reveal the numbers after typing
    . Create constraints
    . 
    */

    //extra stuff

    /*
    - Create different profiles
    - Keep high score on those profiles
    - Let users create their own minesweeper board (hard)
    - Multiplayer Minesweeper
    - Possibly implement into an app
    */

    //these values will never change
    final static int begDiff = 9; //dimensions for beginner 9*9
    final static int intDiff = 16; //dimensions for intermediate 16*16
    final static int advDiff = 24; //dimensions for advanced 24*24
    final static int begBombs = 10; //bombs for beginner 10
    final static int intBombs = 40; //bombs for intermediate  40
    final static int advBombs = 99; //bombs for advanced 99

    public static void main(String[] args) {
        int difficulty = 0;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("Select a difficulty");
            System.out.println("1 - Beginner, 2 - Intermediate, 3 - Advanced");
            System.out.print("Input here: ");
            //let user choose the difficulty via. an integer 1=Beginner, 2=Intermediate, 3=Advanced
            difficulty = input.nextInt(); //possibly refactor so its function
            //user input here
            if (difficulty < 1 || difficulty > 4) {
                //invalid input, error message
                System.out.println("That is not apart of the options. Please select again.");
                System.out.println(); 
            } else {
                if (difficulty == 1) {
                    char board[][] = new char[begDiff][begDiff];
                    boolean[][] isBomb = new boolean[begDiff][begDiff];
                    
                    initialiseBoard(board, difficulty);
                    randomiseBombs(board, difficulty, begDiff, begDiff);
                    displayBoard(board, begDiff, begDiff);

                } else if (difficulty == 2) {
                    char board[][] = new char[intDiff][intDiff];
                    initialiseBoard(board, difficulty);            
                    displayBoard(board, intDiff, intDiff);
                } else if (difficulty == 3) {
                    char board[][] = new char[advDiff][advDiff];
                    initialiseBoard(board, difficulty);            
                    displayBoard(board, advDiff, advDiff);
                }
            }
        } while (difficulty < 1 || difficulty > 4);

        //below, should be the game

    }

    public static void initialiseBoard(char[][] board, int difficulty) {
        int row, column;
        switch (difficulty) {
            case 1:
                for (row = 0; row < begDiff; row++) {
                    for (column = 0; column < begDiff; column++) {
                        board[row][column] = '.';
                    }
                }
                break;
            case 2:
                for (row = 0; row < intDiff; row++) {
                    for (column = 0; column < intDiff; column++) {
                        board[row][column] = '.';
                    }
                }
                break;
            case 3:
                for (row = 0; row < advDiff; row++) {
                    for (column = 0; column < advDiff; column++) {
                        board[row][column] = '.';
                    }
                }
                break;
        }
    } 

    public static void displayBoard(char[][] board, int row, int column) { //outputs the board
        for (int x = 0; x < row; x++) {
            System.out.println();
            for (int y = 0; y < column; y++) {
                System.out.print(board[x][y]);
            }
        }
    }

    //bombs will be classed as X
    public static void randomiseBombs(char[][] board, int difficulty, int row, int column) {
        Random randomNumber = new Random();
        switch (difficulty) {
            case 1: //easy
                boolean[][] isBomb = new boolean[begDiff][begDiff]; //true = bomb, false = no bomb.
                //default value is false
                for (int placeBombs = 0; placeBombs < begBombs; placeBombs++) {
                    int randomRow = randomNumber.nextInt(begDiff);
                    int randomColumn = randomNumber.nextInt(begDiff);
                    if (isBomb[randomRow][randomColumn] == true) {
                        placeBombs--;
                    } else {
                        isBomb[randomRow][randomColumn] = true; //get this value, and update somewhere
                        board[randomRow][randomColumn] = 'x';
                    }
                }
                break; 
        }
    }
}