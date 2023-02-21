public class Game extends ConsoleProgram
{
    // =================== VARIABLES AND CONSTANTS =================== 
    
    // Board class object to play game
    Board board = new Board();
    
    // Number of strikes the player has. A game ends when the player reaches 3 strikes.
    int strikes = 0;
    
    // Set to true if the player won the game and false if they lost.
    // This is used for displaying a final winning or losing statement after the game ends.
    boolean wonGame;
    
    // GridElement status constants 
    final boolean UNKNOWN = false;
    final boolean KNOWN = true;
    
    // ======================== METHODS ========================
    
    /**
     * The main method of the program that plays the game.
     * 
     * @return none
    */
    public void run()
    {
        menu();
        
        boolean continueGame = true;
        
        while (continueGame) 
        {
            board.printBoard();
            
            System.out.println("Strikes: " + strikes);
            System.out.println("Please enter your guess in the format \nrow column value");
            
            playTurn();
            
            // Checks if the game is over after each play.
            if (board.getNumUnknown() == 0)
            {
                wonGame = true;
                continueGame = false;
            }
            else if (strikes > 2)
            {
                wonGame = false;
                continueGame = false;
            }
            
            System.out.print("\033[H\033[2J");
        }
        
        board.printBoard();
        
        if (wonGame) 
        {
            System.out.println("You won!");
        }
        else 
        {
            System.out.println("You lost.");    
        }
    }
    
    /**
     * Prints out a menu and asks the user for the difficulty of the game.
     * The difficulty the player enters determines the number of givens they recieve at the start.
     * 
     * @return none
    */
    private void menu()
    {
        System.out.println("Welcome to");
        System.out.println("  _________         .___      __          ");
        System.out.println(" /   _____/__ __  __| _/____ |  | ____ __ ");
        System.out.println(" \\_____  \\|  |  \\/ __ |/  _ \\|  |/ /  |  \\");
        System.out.println(" /        \\  |  / /_/ (  <_> )    <|  |  /");
        System.out.println("/_______  /____/\\____ |\\____/|__|_ \\____/ ");
        System.out.println("        \\/           \\/           \\/     ");
        
        System.out.println("\nDifficulties: ");
        System.out.println("Easy - 40 - 50 givens");
        System.out.println("Medium - 30 - 40 givens");
        System.out.println("Hard - 25 - 30 givens");
        System.out.println("Expert - 20-25 givens");
        System.out.println("If you enter an option that is not one of the above, the number of givens will be a random number from 20-50.");
        
        // Getting the game difficulty from the user
        board.setGivens(readLine("Please enter the game difficulty: ").toLowerCase().trim());
        
        // Clearing the screen
        System.out.print("\033[H\033[2J");
    }
    
    /**
     * This asks the user for what number they are going to guess at what location and checks if it is valid.
     * This method will continue to ask the player until they enter a valid input.
     * 
     * @return none
    */
    private void playTurn()
    {
        boolean validInput = false;
        
        // Input is validated before being sent as a turn
        // A valid input string is in the format "row col val"
        while (!validInput)
        {
            String guess = readLine("input: ").trim();
        
            // The input should only ever be 5 characters long (three single digits separated by two spaces)
            if (guess.length() != 5)
            {
                // If the string is longer or shorter than 5 characters, that means it is not valid.
                System.out.println("Please enter a valid string");
            }
            else
            {
                // Next, there should be three digits , one at index 0, 2, and 4.
                // The numbers are left in their ascii values so that checking if they are a digit or not is not required.
                int rowNum = guess.charAt(0);
                int colNum = guess.charAt(2);
                int val = guess.charAt(4);
                
                // Checks if the numbers are in range
                if ((rowNum > '9') || (rowNum < '1') || (colNum > '9') || (colNum < '1') || (val > '9') || (val < '1'))
                {
                    // If the characters at the required indexes are not digits or are outside of the accepted range.
                    System.out.println("Please enter values within the range");
                }
                else
                {
                    // Converting the above characters to integers
                    // 1 is subtracted from the row and col numbers because their coordinates are 1 greater than their array indexes
                    rowNum = Character.getNumericValue(rowNum) - 1;
                    colNum = Character.getNumericValue(colNum) - 1;
                    val = Character.getNumericValue(val);
                    
                    // check if the location is known already or not
                    if (board.getElemStatus(rowNum, colNum) == UNKNOWN)
                    {
                        makeGuess(rowNum, colNum, val);
                        validInput = true;
                    }
                    else
                    {
                        // If the location the player is trying to play is not UNKNOWN, the player should not be able to play there again.
                        System.out.println("You already guessed there");
                    }
                }
            }
        }
    }
    
    /**
     * Checks if the uer's guess from playTurn() is correct or incorrect.
     * If the player is correct, that location is revealed and if it is incorrect, they get a strike.
     * 
     * @param row The row of the location the player guessed
     * @param col The column of the location the player guessed.
     * @param guess The value the player is guessing is at the location
     * 
     * @return none
    */
    private void makeGuess(int row, int col, int guess)
    {
        // Checks if the number at the board location is the same as the player's guess
        if (guess == board.getElemVal(row, col))
        {
            // If it is, set that location to known and decrease the number of unknown locations by 1.
            board.setElemStatus(row, col, KNOWN);
            board.decrementNumUnknown();
        }
        else
        {
            // If the answer is wrong, the player gets a strike.
            strikes++;
        }
    }
}