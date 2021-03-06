import java.util.*;
//import javax.swing.JPanel;
//This will be the refactored version of Minesweeper

//Will write the correct terms for certain software skills as well
public class Minesweeper extends Thread{

    // Singleton for dimensions and mines
    final static int begDimension = 9;
    final static int intDimension = 16;
    final static int advDimension = 24;
    final static int begMines = 10;
    final static int intMines = 40;
    final static int advMines = 99;

    static int dimensions;
    static int difficulty;
    static int mines;
    static char board[][];
    static boolean isMine[][];
    static int row;
    static int column;
    static char flag;
    static int flagTotal;

    public Minesweeper() { // constructor

    }

    public static void main(String[] args) {

        new DifficultyWindow();

    }

    //functionality
    public static void chooseDifficulty() {
    // set dimension and the mines for the board
        switch (difficulty) {
        case 1:
            dimensions = begDimension;
            mines = begMines;
            break;
        case 2:
            dimensions = intDimension;
            mines = intMines;
            break;
        case 3:
            dimensions = advDimension;
            mines = advMines;
            break;
        case 4:
            //Set in DifficultyWindow
            break;
        }
        board = new char[dimensions][dimensions];
        isMine = new boolean[dimensions][dimensions]; //+1 to resolve issue
        initBoard(board, dimensions);
        initMines(board, isMine, dimensions, mines);
        displayBoard(board, dimensions);
        new GameWindow();
    }

    //functionality
    public static void playGame(char[][] board, int dimensions, boolean[][] isMine) {
        boolean gameWon = false;
        boolean gameLost = false;
        flagTotal = checkTotalFlags();    
        if (flag == 'f') {
            System.out.println("Flag is on");
        } else {
            gameLost = checkGameLost(isMine, row, column);
            gameWon = checkGameWon(board, dimensions, isMine);
        }
        if (gameLost == true && board[row][column] != 'f') {
            displayLostBoard(board, dimensions, isMine);
            System.out.println("You Lose! You found a Mine at " + column + "," + row);
        } else if (gameWon == true) {
            updateBoard(board, dimensions, row, column, flag, isMine);
            displayWinBoard(board, dimensions, isMine);
            System.out.println("CONGRATULATIONS!!! YOU WON!!!");
        } else {
            if (Minesweeper.board[row][column] == '-') {              
                if (flagTotal > mines - 1 && flag == 'f') {
                    System.out.println("You have no more flags");
                } else {
                    updateBoard(board, dimensions, row, column, flag, isMine);    
                }
                flagTotal = checkTotalFlags();
                System.out.println("Update Board on - : Flags On Board = " + flagTotal);
            } else if (board[row][column] == 'f') {
                if (flag == 'f' && flagTotal > 0) {
                    updateBoard(board, dimensions, row, column, flag, isMine); 
                } else if (flag != 'f') {
                    System.out.println("THERE IS A FLAG THERE");
                } else {
                    System.out.println("THERE IS A FLAG THERE");
                }
            } else {
                System.out.println("COORDINATE ALREADY HAS A VALUE");
            }
            displayBoard(board, dimensions);
        }
    }

    public static void displayWinBoard(char[][] board, int dimensions, boolean[][] isMine) {
        for (int x = 0; x < dimensions; x++) {
            System.out.println();
            for (int y = 0; y < dimensions; y++) {
                if (board[x][y] != 'f' && isMine[x][y] == true) {
                    board[x][y] = 'f';
                    System.out.print(board[x][y]);
                } else {
                    System.out.print(board[x][y]);
                }
            }
        }
        System.out.println();
    }

    public static void displayLostBoard(char[][] board, int dimensions, boolean[][] isMine) {
        for (int x = 0; x < dimensions; x++) {
            System.out.println();
            for (int y = 0; y < dimensions; y++) {
                if (board[x][y] == '-' && isMine[x][y] == true) {
                    board[x][y] = 'X';
                    System.out.print(board[x][y]);
                } else if (board[x][y] == 'f' && isMine[x][y] == false) {
                    board[x][y] = 'w'; // indicates that this flag was in the wrong position
                    System.out.print(board[x][y]);
                } else {
                    System.out.print(board[x][y]);
                }
            }
        }
        System.out.println();
    }

    //functionality
    public static void updateBoard(char[][] board, int dimensions, int row, int column, char flag, boolean[][] isMine) {
        if ((flag == 'f' || flag == 'F') && board[row][column] == '-') { //from - to flag
            board[row][column] = 'f';
        } else if ((flag == 'f' || flag == 'F') && board[row][column] == 'f') { //from flag to -
            board[row][column] = '-';
        } else { //otherwise change values to respective numbers then to a char
            changeIntToChar(board, dimensions, row, column, isMine); 
            zeroClause(board, dimensions, row, column, flag, isMine);
        }
    }

    //functionality - proud of this - Algorithm design and recursion
    public static void zeroClause(char[][] board, int dimensions, int row, int column, int flag, boolean[][] isMine) {
        int nearbyMines = 0; //checks to see if 0 was on selected square
        nearbyMines = findNearbyMines(row, column, dimensions, isMine); //is checked row by row, skipping the selected square
        if (nearbyMines == 0) {
            int x = -1; //set to -1 so that it can check the eight coordinates based on the selected square/coordinatte
            while (x <= 1) {
                if (row + x < 0 || row + x > row + 1) {} else {
                    int y = -1;
                    while (y <= 1) {
                        if (column + y < 0 || column + y > column + 1) {} else {
                            if (row + x >= dimensions || column + y >= dimensions) { } else {
                                int checkNext = findNearbyMines(row + x, column + y, dimensions, isMine);
                                if (board[row + x][column + y] == 'f') {} else if (checkNext == 0) {
                                    if (board[row + x][column + y] == '-') {
                                        //use of iterative recursion
                                        changeIntToChar(board, dimensions, row + x, column + y, isMine);
                                        if (column + y > dimensions - 1 || column + y < 0) {
                                            zeroClause(board, dimensions, row - 1, column, flag, isMine);
                                        } else if (row + x > dimensions - 1 || row + x < 0) {
                                            zeroClause(board, dimensions, row, column - 1, flag, isMine);
                                        } else {
                                            zeroClause(board, dimensions, row + x, column + y, flag, isMine);
                                        }
                                    }
                                } else {
                                    changeIntToChar(board, dimensions, row + x, column + y, isMine);
                                }
                            }
                        }
                        y++;
                    }
                }
                x++;
            }
        } else {
            changeIntToChar(board, dimensions, row, column, isMine);
        }
    }

    //functionality
    public static void changeIntToChar(char[][] board, int dimensions, int row, int column, boolean[][] isMine) {
        int nearbyMines = 0; //changes integer to a character
        nearbyMines = findNearbyMines(row, column, dimensions, isMine);
        char intToChar = (char) (nearbyMines + '0');
        board[row][column] = intToChar;
    }

    //functionality
    public static int findNearbyMines(int row, int column, int dimensions, boolean[][] isMine) {
        int nearbyMines = 0; //used to find number of adjacent mines on selected square. Maximum should be 8
        //error is that it is checking outside the array. After changing isMine to correct array
        if ((row - 1 >= 0) && (column - 1 >= 0) && (isMine[row - 1][column - 1] == true)) { // NW - TL
            nearbyMines++;
        }
        if ((row >= 0) && (column - 1 >= 0) && (isMine[row][column - 1] == true)) { // N - T
            nearbyMines++;
        }
        if ((row + 1 < dimensions) && (column - 1 >= 0) && (isMine[row + 1][column - 1] == true)) { // NE - TR
            nearbyMines++;
        }
        if ((row + 1 < dimensions) && (column < dimensions) && (isMine[row + 1][column] == true)) { // E - R
            nearbyMines++;
        }
        if ((row + 1 < dimensions) && (column + 1 < dimensions) && (isMine[row + 1][column + 1] == true)) { // SE - BR
            nearbyMines++;
        }
        if ((row < dimensions) && (column + 1 < dimensions) && (isMine[row][column + 1] == true)) { // S - B
            nearbyMines++;
        }
        if ((row - 1 >= 0) && (column + 1 < dimensions) && (isMine[row - 1][column + 1] == true)) { // SW + BL
            nearbyMines++;
        }
        if ((row - 1 >= 0) && (column < dimensions) && (isMine[row - 1][column] == true)) { // NW - TL
            nearbyMines++;
        }
        return nearbyMines;
    }

    //functionality
    public static void initBoard(char[][] board, int dimensions) { // initialise board
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                board[row][column] = '-';
            }
        }
    }

    public static void displayBoard(char[][] board, int dimensions) { // display board
        for (int x = 0; x < dimensions; x++) { //shown after every selection of a square
            // sort out numbers on top or below
            for (int y = 0; y < dimensions; y++) {
                System.out.print(board[x][y]);
            }
            System.out.print(" " + x + "\r\n"); // puts numbers on the right
        }
        System.out.println("");
    }

    //functionality
    public static void initMines(char board[][], boolean[][] isMine, int dimensions, int mines) { // initialise mines
        Random randomNumber = new Random();
        for (int placeMines = 0; placeMines < mines; placeMines++) {
            int randomRow = randomNumber.nextInt(dimensions);
            int randomColumn = randomNumber.nextInt(dimensions); //row and column are randomised
            if (isMine[randomRow][randomColumn] == true) {
                placeMines--;
            } else {
                isMine[randomRow][randomColumn] = true;
                if (isMine[0][0] == true || isMine[0][dimensions - 1] == true || isMine[dimensions - 1][0] == true
                        || isMine[dimensions - 1][dimensions - 1] == true) { // ensures theres no mines in the corners
                    placeMines--;
                    board[randomRow][randomColumn] = '-';
                    isMine[randomRow][randomColumn] = false;
                }  
            }
        }
    }

    //functionality - This didnt work for the majority of the development. Explain why
    public static boolean checkGameWon(char[][] board, int dimensions, boolean[][] isMine) {
        boolean gameWon = true;
        int win = 0;
        for (int x = 0; x < dimensions; x++) {
            for (int y = 0; y < dimensions; y++) {
                if (board[x][y] == '-' || (board[x][y] == 'f' && isMine[x][y] == true)) { //it reads flags as a plus on the win condition
                    win++;
                }
                if (board[x][y] == 'f' && isMine[x][y] == false) {
                    win++;
                }
            }
        }
        //loops through rows and columns checking if there are no open spaces (i.e. numbers are shown)
        if (win == mines) {
            gameWon = true;
        } else {
            gameWon = false;
        }
        return gameWon;
    }

    //functionality
    public static boolean checkGameLost(boolean[][] isMine, int row, int column) {
        boolean mineFound = false;
        if (isMine[row][column] == true) {
            mineFound = true;
        }
        return mineFound;
    }

    //functionality
    public static int checkTotalFlags() {
        int flagsLeft = 0;
        for (int x = 0; x < dimensions; x++) {
            for (int y = 0; y < dimensions; y++) {
                if (board[x][y] == 'f') {
                    flagsLeft++;
                }
            }
        }
        return flagsLeft;
    }
}
