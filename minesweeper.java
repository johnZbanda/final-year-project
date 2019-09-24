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
        int difficulty, xCo, yCo = 0; //Difficulty, X Coordinate, Y Coordinate
        char flag;
        boolean gameWon = false;
        boolean gameLost = false;
        do {
            Scanner difficultyInput = new Scanner(System.in);
            System.out.println("Select a difficulty");
            System.out.println("1 - Beginner, 2 - Intermediate, 3 - Advanced");
            System.out.print("Input here: ");
            //let user choose the difficulty via. an integer 1=Beginner, 2=Intermediate, 3=Advanced
            difficulty = difficultyInput.nextInt(); //possibly refactor so its function
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
                    randomiseBombs(board, difficulty, intDiff, intDiff);      
                    displayBoard(board, intDiff, intDiff);
                } else if (difficulty == 3) {
                    char board[][] = new char[advDiff][advDiff];
                    initialiseBoard(board, difficulty);         
                    randomiseBombs(board, difficulty, advDiff, advDiff);   
                    displayBoard(board, advDiff, advDiff);
                }
            }
        } while (difficulty < 1 || difficulty > 4);
        //below, should be the game
        System.out.println("");
        System.out.println("Input Coordinates after the board, put F AFTER Coordinates to input Flag - x y (F)");
        
        while (gameLost == false && gameWon == false) {
            //play game here
            System.out.println("");
            Scanner xCoInput = new Scanner(System.in);
            Scanner yCoInput = new Scanner(System.in);
            Scanner flagInput = new Scanner(System.in);
            //check game lost or won after every coord input  
            System.out.print("x: ");
            xCo = xCoInput.nextInt();
            System.out.print("y: ");
            yCo = yCoInput.nextInt();
            System.out.print("flag: ");
            flag = flagInput.next().charAt(0);   
            
            gameWon = true; //used for testing
        }

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
    //similar code to gameOfLife finding neighbour for bombs
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
        //refactor your code, it looks ugly right now (to be fair, this will probably be the ugliest part)
        Random randomNumber = new Random();
        boolean[][] isBomb = new boolean[row][column]; //true = bomb, false = no bomb.
        switch (difficulty) {
            case 1: //easy
                //default value is false
                for (int placeBombs = 0; placeBombs < begBombs; placeBombs++) {
                    int randomRow = randomNumber.nextInt(begDiff);
                    int randomColumn = randomNumber.nextInt(begDiff);
                    if (isBomb[randomRow][randomColumn] == true) {
                        placeBombs--;
                    } else {
                        isBomb[randomRow][randomColumn] = true; //get this value, and update somewhere
                        board[randomRow][randomColumn] = 'x';
                        if (isBomb[0][0] == true || isBomb[0][column - 1] == true || isBomb[row - 1][0] == true || isBomb[row - 1][column - 1] == true) {
                            //to ensure no outofarray exceptions, -1 to row and column
                            placeBombs--;
                            board[randomRow][randomColumn] = '.';
                            isBomb[randomRow][randomColumn] = false;
                        }
                    }
                }
                break;
            
            case 2: //intermediate
                //default value is false
                for (int placeBombs = 0; placeBombs < intBombs; placeBombs++) {
                    int randomRow = randomNumber.nextInt(intDiff);
                    int randomColumn = randomNumber.nextInt(intDiff);
                    if (isBomb[randomRow][randomColumn] == true) {
                        placeBombs--;
                    } else {
                        isBomb[randomRow][randomColumn] = true; //get this value, and update somewhere
                        board[randomRow][randomColumn] = 'x';
                        if (isBomb[0][0] == true || isBomb[0][column - 1] == true || isBomb[row - 1][0] == true || isBomb[row - 1][column - 1] == true) {
                            //to ensure no outofarray exceptions, -1 to row and column
                            placeBombs--;
                            board[randomRow][randomColumn] = '.';
                            isBomb[randomRow][randomColumn] = false;
                        }
                    }
                }
                break; 
            
            case 3: //advanced
                //default value is false
                for (int placeBombs = 0; placeBombs < advBombs; placeBombs++) {
                    int randomRow = randomNumber.nextInt(advDiff);
                    int randomColumn = randomNumber.nextInt(advDiff);
                    if (isBomb[randomRow][randomColumn] == true) {
                        placeBombs--;
                    } else {
                        isBomb[randomRow][randomColumn] = true; //get this value, and update somewhere
                        board[randomRow][randomColumn] = 'x';
                        if (isBomb[0][0] == true || isBomb[0][column - 1] == true || isBomb[row - 1][0] == true || isBomb[row - 1][column - 1] == true) {
                            //to ensure no outofarray exceptions, -1 to row and column
                            placeBombs--;
                            board[randomRow][randomColumn] = '.';
                            isBomb[randomRow][randomColumn] = false;
                        }
                    }
                }
                break; 
        }
    }      

    //finds bombs near to the coordinates given by the user
    public static int findNearbyBombs(int xCo, int yCo, int row, int column, boolean[][] isBomb) {
        int nearbyBombs = 0; //increment when there is a neighbour
        if ((xCo - 1 >= 0) && (yCo - 1 >= 0) && (isBomb[xCo-1][yCo-1] == true)) { //NW 
            nearbyBombs++;
        }
        if ((xCo >= 0) && (yCo - 1 >= 0) && (isBomb[xCo][yCo-1] == true)) { //W
            nearbyBombs++;
        }
        if ((xCo + 1 < row) && (yCo - 1 >= 0) && (isBomb[xCo+1][yCo-1] == true)) { //SW
            nearbyBombs++;
        }
        if ((xCo + 1 < row) && (yCo < column) && (isBomb[xCo+1][yCo] == true)) { //S
            nearbyBombs++;
        }
        if ((xCo + 1 < row) && (yCo + 1 < column) && (isBomb[xCo+1][yCo+1] == true)) { //SE
            nearbyBombs++;
        }
        if ((xCo < row) && (yCo + 1 < column) && (isBomb[xCo][yCo+1] == true)) { //E
            nearbyBombs++;
        }
        if ((xCo - 1 >= 0) && (yCo + 1 < column) && (isBomb[xCo-1][yCo+1] == true)) { //NE
            nearbyBombs++;
        }
        if ((xCo - 1 >= 0) && (yCo < column) && (isBomb[xCo-1][yCo] == true)) { //N
            nearbyBombs++;
        }	
        return nearbyBombs;
    }

    public static boolean checkGameWon(char[][] board, int row, int column, boolean[][] isBomb) { //slightly complex
        //if all numbers have been revealed. iterate through every position, check if the value is not .
        
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (board[x][y] != '.' || (isBomb[x][y] == true && board[x][y] == '.') ) { //might have to check the flag count as well
                    
                }
            }
        }
        return true;
    }

    public static boolean checkGameLost(boolean[][] isBomb, int xCo, int yCo) {
        boolean bombFound = false;
        if (isBomb[xCo][yCo] == true) {
            bombFound = true;
        } 
        return bombFound;
    } 
}