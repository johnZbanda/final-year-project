import java.util.*;
//This will be the refactored version of Minesweeper

//Will write the correct terms for certain software skills as well
public class Minesweeper {

    //Singleton for dimensions and mines
    final static int begDimension = 9;
    final static int intDimension = 16;
    final static int advDimension = 24;
    final static int begMines = 10;
    final static int intMines = 40;
    final static int advMines = 99;

    public static void main (String [] args) {
        int dimensions = 0;
        int difficulty = 0;
        int mines = 0;
        do {
            Scanner setDifficulty = new Scanner(System.in);
            System.out.println("-------Select a difficulty----------");
            System.out.println("1 - Beginner, 2 - Intermediate, 3 - Advanced");
            System.out.print("Input here: ");
            //Error Check to see correct input here
            difficulty = setDifficulty.nextInt();
            if (difficulty < 1 || difficulty > 4) {
                System.out.println("INCORRECT INPUT. PLEASE CHOOSE AGAIN \r\n");
            } else { //set dimension and the mines for the board
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
                }
                char board[][] = new char[dimensions][dimensions];
                boolean isMine[][] = new boolean[mines][mines];
                initBoard(board, dimensions);
                initMines(board, isMine, dimensions, mines);
                displayBoard(board, dimensions);
                playGame(board, dimensions, isMine);
                //initialise board, mines, show display, play the game
            }
        } while (difficulty < 1 || difficulty > 4); 
    }

    public static void playGame(char[][]  board, int dimensions, boolean[][] isMine) {
        int row, column = 0; //this will be the input
        char flag;
        boolean gameWon = false;
        boolean gameLost = false;

        System.out.println("Input Coordinates.\r\n F to input a Flag, Anything to input no flag\r\n x y (F)\r\n");
        Scanner x = new Scanner(System.in);
        Scanner y = new Scanner(System.in);
        Scanner f = new Scanner(System.in);

        while (gameLost == false || gameWon == false) {
            //Input needed
            System.out.print("y: ");
            row = x.nextInt();
            System.out.print("x: ");
            column = y.nextInt();
            System.out.print("Flag: ");
            flag = f.next().charAt(0);

            gameLost = checkGameLost(isMine, row, column);
            gameWon = checkGameWon(board, dimensions, isMine);

            if (gameLost == true) {
                displayLostBoard(board, dimensions, isMine);
                System.out.println("You Lose! You found a Mine at " + row + "," + column);
                break;
            } else if (gameWon == true) {
                displayWinBoard(board, dimensions, isMine);
                System.out.println("CONGRATULATIONS!!! YOU WON!!!");
                break;
            } else {
                updateBoard(board, dimensions, row, column, flag, isMine);
                displayBoard(board, dimensions);
            }
        }
        //may need to close inputs
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
                    board[x][y] = 'w'; //indicates that this flag was in the wrong position
                    System.out.print(board[x][y]);
                } else {
                    System.out.print(board[x][y]);
                }
            }
        }
        System.out.println();
    }

    public static void updateBoard(char[][] board, int dimensions, int row, int column, char flag, boolean[][] isMine) {
        if ((flag == 'f' || flag == 'F') && board[row][column] == '-') {
            board[row][column] = 'f';
        } else if ((flag == 'f' || flag == 'F') && board[row][column] == 'f') {
            board[row][column] = '-';
        } else {
            changeIntToChar(board, dimensions, row, column, isMine);
            zeroClause(board, dimensions, row, column, flag, isMine);
        }
    }

    public static void zeroClause(char[][] board, int dimensions, int row, int column, int flag, boolean[][] isMine) {
        int nearbyMines = 0;
        nearbyMines = findNearbyMines(row, column, dimensions, isMine);
        if (nearbyMines == 0) {
            int x = -1;
            while (x <= 1) {
                if (row + x < 0 || row + x > row + 1) {

                } else {
                    int y = -1;
                    while (y <= 1) {
                        if (column + y < 0 || column + y > column + 1) {

                        } else {
                            if (row + x >= dimensions || column + y >= dimensions) {

                            } else {
                                int checkNext = findNearbyMines(row + x, column + y, dimensions, isMine);
                                if (board[row + x][column + y] == 'f') {

                                } else if (checkNext == 0) {
                                    if (board[row + x][column + y] == '-') {
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

    public static void changeIntToChar(char[][] board, int dimensions, int row, int column, boolean[][] isMine) {
        int nearbyMines = 0;
        nearbyMines = findNearbyMines(row, column, dimensions, isMine);
        char intToChar = (char) (nearbyMines + '0');
        board[row][column] = intToChar;
    }

    public static int findNearbyMines(int row, int column, int dimensions, boolean[][] isMine) {
        int nearbyMines = 0;
        if ((row - 1 >= 0) && (column - 1 >= 0) && (isMine[row-1][column-1] == true)) { //NW - TL
            nearbyMines++;
        }
        if ((row >= 0) && (column - 1 >= 0) && (isMine[row][column-1] == true)) { //N - T
            nearbyMines++;
        }
        if ((row + 1 < dimensions) && (column - 1 >= 0) && (isMine[row+1][column-1] == true)) { //NE - TR
            nearbyMines++;
        }
        if ((row + 1 >= 0) && (column < dimensions) && (isMine[row+1][column] == true)) { //E - R
            nearbyMines++;
        }
        if ((row + 1 < dimensions) && (column + 1 < dimensions) && (isMine[row+1][column+1] == true)) { //SE - BR
            nearbyMines++;
        }
        if ((row < dimensions) && (column + 1 < dimensions) && (isMine[row][column+1] == true)) { //S - B
            nearbyMines++;
        }
        if ((row - 1 >= 0) && (column + 1 < dimensions) && (isMine[row-1][column+1] == true)) { //SW + BL
            nearbyMines++;
        }
        if ((row - 1 >= 0) && (column < dimensions) && (isMine[row-1][column] == true)) { //NW - TL
            nearbyMines++;
        }
        return nearbyMines;
    }

    public static void initBoard(char[][] board, int dimensions) { //initialise board
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                board[row][column] = '-';
            }
        }
    }

<<<<<<< HEAD
    public static boolean checkGameWon(char[][] board, int row, int column, boolean[][] isBomb) { //slightly complex
        //if all numbers have been revealed. iterate through every position, check if the value is not .
        //this is still very weird, look over again - 01/10/2019
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (board[x][y] != '.' || (isBomb[x][y] == true && board[x][y] == '.') ) { //might have to check the flag count as well
                    //When won, reveal where flags should be, if they havent been put down, all numbers should be revealed at this point
                    for (int xWin = 0; xWin < row; xWin++) { //this nested for loop is going through the board again to check if flags havent been revealed
                        for (int yWin = 0; yWin < column; yWin++) {
                            if (isBomb[xWin][yWin] == true && board[xWin][yWin] -- '.') {
                                board[xWin][yWin] = 'f';
                            }
                        }
                    }
=======
    public static void displayBoard(char[][] board, int dimensions) { //display board
        System.out.println("");
        for (int x = 0; x < dimensions; x++) { //row and column are the dimensions but it does not make sense
            //sort out numbers on top or below
            for (int y = 0; y < dimensions; y++) {
                System.out.print(board[x][y]);
            }
            System.out.print(" " + x + "\r\n"); //puts numbers on the right
        }
        System.out.println("");
    }

    public static void initMines(char board[][], boolean[][] isMine, int dimensions, int mines) { //initialise mines
        Random randomNumber = new Random();
        for (int placeMines = 0; placeMines < mines; placeMines++) {
            int randomRow = randomNumber.nextInt(dimensions);
            int randomColumn = randomNumber.nextInt(dimensions);
            if (isMine[randomRow][randomColumn] == true) {
                placeMines--;
            } else {
                isMine[randomRow][randomColumn] = true;
                if (isMine[0][0] == true || isMine[0][dimensions - 1] == true || isMine[dimensions - 1][0] == true || isMine[dimensions - 1][dimensions - 1] == true) { //ensures theres no mines in the corners
                    placeMines--;
                    board[randomRow][randomColumn] = '-';
                    isMine[randomRow][randomColumn] = false;
                }
            }
        }
    }    

    public static boolean checkGameWon(char[][] board, int dimensions, boolean[][] isMine) {
        int win = 0;
        boolean gameWon = false;
        for (int x = 0; x < dimensions; x++) {
            for (int y = 0; y < dimensions; y++) {
                if (board[x][y] != '-' || (isMine[x][y] == true && board[x][y] == 'X')) {
                    win++;
>>>>>>> dbdb88eef604a4f423fc70f202ef92154fd47e4d
                }
            }
        }
        if (win == (dimensions * dimensions) + 1) {
            gameWon = true;
        }
        return gameWon;
    }

    public static boolean checkGameLost(boolean[][] isMine, int row, int column) {
        boolean bombFound = false;
        if (isMine[row][column] == true) {
            bombFound = true;
        }
        return bombFound;
<<<<<<< HEAD
    } 
}

//Do select co-ordinate then randomise bombs
=======
    }
}
>>>>>>> dbdb88eef604a4f423fc70f202ef92154fd47e4d
