import java.util.*;

public class minesweeper {

    //list of things to do
    
    /* Pretty much complete
    - Initialise the board for different difficulties - Done
    - Play the game (multiple sub sections) - started
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
    - Possibly implement into an app - Definitely
    - UI design, different backgrounds (underwater, space, flowery, Halloween, etc) -> FLO'S IDEA
    - Sessions on Web application
    - Hint system
    - Custom games
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
                    boolean isBomb[][] = new boolean[begDiff][begDiff];
                    initialiseBoard(board, difficulty);
                    randomiseBombs(board, difficulty, begDiff, begDiff, isBomb);
                    displayBoard(board, begDiff, begDiff);
                    playGame(board, begDiff, begDiff, isBomb, difficulty);
                    //strangely enough, i think you should put playGame here, or else it'd be long having to do cases for all of them
                } else if (difficulty == 2) {
                    char board[][] = new char[intDiff][intDiff];
                    boolean isBomb[][] = new boolean[intDiff][intDiff];
                    initialiseBoard(board, difficulty); 
                    randomiseBombs(board, difficulty, intDiff, intDiff, isBomb);      
                    displayBoard(board, intDiff, intDiff);
                    playGame(board, intDiff, intDiff, isBomb, difficulty);
                } else if (difficulty == 3) {
                    char board[][] = new char[advDiff][advDiff];
                    boolean isBomb[][] = new boolean[advDiff][advDiff];
                    initialiseBoard(board, difficulty);        
                    randomiseBombs(board, difficulty, advDiff, advDiff, isBomb);   
                    displayBoard(board, advDiff, advDiff);
                    playGame(board, advDiff, advDiff, isBomb, difficulty);
                }
            }
            difficultyInput.close();
        } while (difficulty < 1 || difficulty > 4);
    }

    public static void playGame(char[][] board, int row, int column, boolean[][] isBomb, int difficulty) { 
        //below, should be the 
        int xCo, yCo = 0; //Difficulty, X Coordinate, Y Coordinate
        char flag;
        boolean gameWon = false;
        boolean gameLost = false;

        System.out.println("");
        System.out.println("Input Coordinates after the board, put F AFTER Coordinates to input Flag - x y (F)");
        Scanner xCoInput = new Scanner(System.in);
        Scanner yCoInput = new Scanner(System.in);
        Scanner flagInput = new Scanner(System.in);                
        while (gameLost == false || gameWon == false) {
            //play game here
            System.out.println("");

            //check game lost or won after every coord input  
            System.out.print("y: ");
            xCo = xCoInput.nextInt();
            System.out.print("x: ");
            yCo = yCoInput.nextInt();
            System.out.print("flag: ");
            flag = flagInput.next().charAt(0);   
            gameWon = false; //used for testing
            boolean bombFound = checkGameLost(isBomb, xCo, yCo);
            gameWon = checkGameWon(board, row, column, isBomb, difficulty);
            if (bombFound == true) {
                displayLostBoard(board, row, column, difficulty, isBomb);
                System.out.println("you lose");
                //display board with all x, comment 277 298 and last one to hide bombs
                //dont reveal numbers

                break; //should possibly go back to the menu
            } else if (gameWon == true) {
                displayWinBoard(board, row, column, difficulty, isBomb);
                System.out.println("you win");
                //place flags where bombs are if there aren't
                //all numbers will be revealed by now
                break;
            } else {
                updateBoard(board, xCo, yCo, flag, isBomb, difficulty);
                displayBoard(board, row, column);             
            }
         
        }
        xCoInput.close();
        yCoInput.close();
        flagInput.close();   
    }

    public static void displayWinBoard(char[][] board, int row, int column, int difficulty, boolean[][] isBomb) {
        int setDimensions = 0;
        if (difficulty == 1) {
            setDimensions = begDiff;
        } else if (difficulty == 2) {
            setDimensions = intDiff;
        } else if (difficulty == 3) {
            setDimensions = advDiff;
        }

        for (int x = 0; x < setDimensions; x++) {
            System.out.println("");
            for (int y= 0; y < setDimensions; y++ ) {
                if (board[x][y] != 'f' && isBomb[x][y] == true) {
                    board[x][y] = 'f';
                    System.out.print(board[x][y]);
                } else {
                    System.out.print(board[x][y]);
                }
            }
        }
        System.out.println();
    }

    public static void displayLostBoard(char[][] board, int row, int column, int difficulty, boolean[][] isBomb) {
        int setDimensions = 0;
        if (difficulty == 1) {
            setDimensions = begDiff;
        } else if (difficulty == 2) {
            setDimensions = intDiff;
        } else if (difficulty == 3) {
            setDimensions = advDiff;
        }

        for (int x = 0; x < setDimensions; x++) {
            System.out.println("");
            for (int y= 0; y < setDimensions; y++ ) {
                if (board[x][y] == '.' && isBomb[x][y] == true) {
                    board[x][y] = 'x';
                    System.out.print(board[x][y]);
                } else if (board[x][y] == 'f' && isBomb[x][y] == false) {
                    board[x][y] = 'w'; //there is no bomb under the flag
                    System.out.print(board[x][y]);
                } else {
                    System.out.print(board[x][y]);
                }
            }
        }
        System.out.println("");
    }

    public static void updateBoard(char[][] board, int row, int column, char flag, boolean[][] isBomb, int difficulty) { //dont forget, it is indexed onto 0
        if ((flag == 'f' || flag == 'F') && board[row][column] == '.') { //changes to f, if no flag is there
            board[row][column] = 'f';
        } else if ((flag == 'f' || flag == 'F') && board[row][column] == 'f') { //changes back to '.' if they select f while it is f 
            board[row][column] = '.';
        } else {
            //need findNeighbours, change number into character
            if (difficulty == 1) { //easy
                changeIntToChar(board, row, column, flag, isBomb, difficulty);
                zeroClause(board, row, column, flag, isBomb, difficulty);
            } else if (difficulty == 2) {
                changeIntToChar(board, row, column, flag, isBomb, difficulty);
                zeroClause(board, row, column, flag, isBomb, difficulty);
            } else if (difficulty == 3) {
                changeIntToChar(board, row, column, flag, isBomb, difficulty);
                zeroClause(board, row, column, flag, isBomb, difficulty);
            }

        }
    }

    public static void zeroClause(char [][] board, int row, int column, char flag, boolean[][] isBomb, int difficulty) {
        int nearbyBombs = 0;
        int setDifficulty = 0; //used to determine the number of bombs dependant on difficulty
        if (difficulty == 1) {
            nearbyBombs = findNearbyBombs(row, column, begDiff, begDiff, isBomb, difficulty);
            setDifficulty = begDiff;
        } else if (difficulty == 2) {
            nearbyBombs = findNearbyBombs(row, column, intDiff, intDiff, isBomb, difficulty);
            setDifficulty = intDiff;
        } else if (difficulty == 3) {
            nearbyBombs = findNearbyBombs(row, column, advDiff, advDiff, isBomb, difficulty);
            setDifficulty = advDiff;
        }
        //need a holder for the next position
        //possibly do an array that holds it, skipping the fifth element
        //column is the row for some reason????
        if (nearbyBombs == 0) {
            int xCount = -1;
            while (xCount <= 1) {
                if (row + xCount < 0 || row + xCount > row + 1) { //> end of array
                    //if outside the boundary, don't do anything, skip everything and add one to x
                } else {
                    int yCount = -1;
                    while (yCount <= 1) {
                        if (column + yCount < 0 || column + yCount > column + 1) {

                        } else {

                            if (((row + xCount) >= setDifficulty) || ((column + yCount) >= setDifficulty)) {

                            } else {
                                int checkNext = findNearbyBombs(row + xCount, column + yCount, setDifficulty, setDifficulty, isBomb, difficulty);

                                if (board[row + xCount][column + yCount] == 'f') {

                                } else if (checkNext == 0) {  

                                    if (board[row + xCount][column + yCount] == '.') {

                                        changeIntToChar(board, row + xCount, column + yCount, flag, isBomb, difficulty);
                                        //zeroClause(board, row + xCount, column + yCount, flag, isBomb, difficulty); 
                                        
                                        if (column + yCount > setDifficulty - 1 || column + yCount < 0) {
                                            zeroClause(board, row - 1, column, flag, isBomb, difficulty);
                                        } else if (row + xCount > setDifficulty - 1 || column + yCount < 0) {
                                            zeroClause(board, row, column - 1, flag, isBomb, difficulty);    
                                        } else {
                                            zeroClause(board, row + xCount, column + yCount, flag, isBomb, difficulty); 
                                        }
                                        
                                    }
                                } else {
                                    changeIntToChar(board, row + xCount, column + yCount, flag, isBomb, difficulty);
                                } 
                            }
                        
                        }
                        yCount++;
                    }                                                          
                }
                xCount++;
            }
        } else {
            changeIntToChar(board, row, column, flag, isBomb, difficulty);
        }

    }

    public static void changeIntToChar(char[][] board, int row, int column, char flag, boolean[][] isBomb, int difficulty) {
        int nearbyBombs = 0;
        if (difficulty == 1) {
            nearbyBombs = findNearbyBombs(row, column, begDiff, begDiff, isBomb, difficulty);
        } else if (difficulty == 2) {
            nearbyBombs = findNearbyBombs(row, column, intDiff, intDiff, isBomb, difficulty);
        } else if (difficulty == 3) {
            nearbyBombs = findNearbyBombs(row, column, advDiff, advDiff, isBomb, difficulty);
        }  
        char intToChar = (char) (nearbyBombs + '0');
        board[row][column] = intToChar;
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
    public static void randomiseBombs(char[][] board, int difficulty, int row, int column, boolean[][] isBomb) {
        //refactor your code, it looks ugly right now (to be fair, this will probably be the ugliest part)
        Random randomNumber = new Random();
       // boolean[][] isBomb = new boolean[row][column]; //true = bomb, false = no bomb.
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
                        //board[randomRow][randomColumn] = 'x'; //comment this to hide the bombs
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
                        //board[randomRow][randomColumn] = 'x';
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
                        //board[randomRow][randomColumn] = 'x';
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
    public static int findNearbyBombs(int xCo, int yCo, int row, int column, boolean[][] isBomb, int difficulty) {
        int nearbyBombs = 0; //increment when there is a neighbour
        if ((xCo - 1 >= 0) && (yCo - 1 >= 0) && (isBomb[xCo-1][yCo-1] == true)) { //NW
            nearbyBombs++;
        }
        if ((xCo >= 0) && (yCo - 1 >= 0) && (isBomb[xCo][yCo-1] == true)) { //N
            nearbyBombs++;
        }
        if ((xCo + 1 < row) && (yCo - 1 >= 0) && (isBomb[xCo+1][yCo-1] == true)) { //NE
            nearbyBombs++;
        }
        if ((xCo + 1 < row) && (yCo < column) && (isBomb[xCo+1][yCo] == true)) { //E
            nearbyBombs++;
        }
        if ((xCo + 1 < row) && (yCo + 1 < column) && (isBomb[xCo+1][yCo+1] == true)) { //SE
            nearbyBombs++;
        }
        if ((xCo < row) && (yCo + 1 < column) && (isBomb[xCo][yCo+1] == true)) { //S
            nearbyBombs++;
        }
        if ((xCo - 1 >= 0) && (yCo + 1 < column) && (isBomb[xCo-1][yCo+1] == true)) { //SW
            nearbyBombs++;
        }
        if ((xCo - 1 >= 0) && (yCo < column) && (isBomb[xCo-1][yCo] == true)) { //W
            nearbyBombs++;
        }	

        return nearbyBombs;
    }

    public static boolean checkGameWon(char[][] board, int row, int column, boolean[][] isBomb, int difficulty) { //slightly complex
        //if all numbers have been revealed. iterate through every position, check if the value is not .
        int winCounter = 0;
        int setDifficulty = 0; //used to determine the number of bombs dependant on difficulty
        if (difficulty == 1) {
            setDifficulty = begDiff;
        } else if (difficulty == 2) {
            setDifficulty = intDiff;
        } else if (difficulty == 3) {
            setDifficulty = advDiff;
        }

        boolean gameWon = false;
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (board[x][y] != '.' || (isBomb[x][y] == true && board[x][y] == 'x')) { //might have to check the flag count as well
                    winCounter++; //used to hold a counter for winning the game
                }
            }
        }


        if (winCounter == ((setDifficulty * setDifficulty) + 1)) {
            gameWon = true;
        }
        return gameWon;
    }

    public static boolean checkGameLost(boolean[][] isBomb, int xCo, int yCo) {
        boolean bombFound = false;
        if (isBomb[xCo][yCo] == true) {
            bombFound = true;
        } 
        return bombFound;
    } 
}
