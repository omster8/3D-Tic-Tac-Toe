/*
    Om Patel
    3D Tic Tac Toe
    Mr. Rosen
    January 16, 2019
    This program is a two-player game of 3-d tic tac toe.
	The splash screen draws a fancy design and title and prompts the user to continue.
	The main menu screen presents the user with options to continue, exit, view rankings, or see instructions.
	The instructions screen tells the user how to use this program.
	The view rankings screen shows the user the high scores and asks them if they want to clear them.
	The clear rankings screen confirms that the high scores file has been cleared.
	The play game screen runs the game, repeatedly calling player 1's turn and player 2's turn until someone wins.
	The display result screen shows who won the game.
	The ask name screen asks the winner their name to update the rankings file.

    NAME            VARIABLE TYPE               DESCRIPTION
    ------------------------------------------------------------------------------------------------------------------------------
    key             char                        This variable is used in pauseProgram to prompt the user
    xCoord          int                         This variable is used to store the x-coordinate of a position on the board
    yCoord          int                         This variable is used to store the y-coordinate of a position on the board
    layerNumber     int                         This variable is used to store the layer number of a position on the board
    symbol          String                      This variable stores the current player's symbol
    winnerName      String                      This variable is used to store the winning player's name
    winnerNum       int                         This variable stores the winning player's number
    numOfMoves      int                         This variable stores the number of moves (for the score)
    BOARD_LENGTH    final int                   This constant stores the side length of the tic tac toe board
    boardArr        int[][][]                   This 3-d array stores the board positions and whether they are occupied or not
*/

import java.awt.*;
import java.io.*;       // For being able to access and modify files saved on the computer
import hsa.Console;
import hsa.Message;     // For accessing Message dialogue box

public class TicTacToe_3D
{
    // Global variable declaration
    static Console c;                   // The output console
    static char key;                    // Used in pauseProgram
    int xCoord, yCoord, layerNumber;    // Used to determine a position on the board
    String symbol;                      // Used to determine the current player's symbol
    String winnerName;                  // Used to store the winning player's name
    int winnerNum;                      // Used to store the winning player's number
    int numOfMoves = 0;                 // Used to store the number of moves that the game has been won in (for the score)
    final int BOARD_LENGTH = 3;         // Used to store the side length of the tic tac toe board
    // This array is to store the board positions and whether they are filled or not
    int[] [] [] boardArr = new int [BOARD_LENGTH] [BOARD_LENGTH] [BOARD_LENGTH];

    // Font variable declarations
    Font defaultFont = new Font ("Courier New", Font.PLAIN, 14);
    Font boardAxesFont = new Font ("Arial", Font.PLAIN, 11);
    Font titleAndSymbols = new Font ("Comic Sans MS", Font.BOLD, 30);
    Font resultCongratulate = new Font ("Comic Sans MS", Font.PLAIN, 30);
    Font resultSymbol = new Font ("Comic Sans MS", Font.PLAIN, 200);

    /*
	This black box return method checks if there is a winning position on the board.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: winningMove (boolean which checks if there is a winning move or not).
	Global variables used: boardArr (to access the board array).
	---------------------------------------------------------------------------------------------------------------------------------------
	No input/logic/loops are used.
    */
    private boolean isGameOver ()
    {
	boolean winningMove = false;    // Checks whether there is a winning move or not

	// Tests the 49 possible winning positions
	for (int i = 0 ; i < BOARD_LENGTH ; i++)
	{
	    for (int j = 0 ; j < BOARD_LENGTH ; j++)
	    {
		if ((boardArr [i] [j] [0] == boardArr [i] [j] [1] && boardArr [i] [j] [0] == boardArr [i] [j] [2]) || (boardArr [0] [i] [j] == boardArr [1] [i] [j] && boardArr [0] [i] [j] == boardArr [2] [i] [j]) || (boardArr [i] [0] [j] == boardArr [i] [1] [j] && boardArr [i] [0] [j] == boardArr [i] [2] [j]))
		{
		    winningMove = true;     // Sets winningMove to true since a winning move has been found
		    break;
		}
	    }
	    if (winningMove == true)    // Allows the outer loop to exit if the inner loop has exited
	    {
		break;
	    }
	    if ((boardArr [i] [0] [0] == boardArr [i] [1] [1] && boardArr [i] [0] [0] == boardArr [i] [2] [2]) || (boardArr [i] [2] [0] == boardArr [i] [1] [1] && boardArr [i] [2] [0] == boardArr [i] [0] [2]))
	    {
		winningMove = true;     // Sets winningMove to true since a winning move has been found
		break;
	    }
	    else if ((boardArr [0] [i] [0] == boardArr [1] [i] [1] && boardArr [0] [i] [0] == boardArr [2] [i] [2]) || (boardArr [2] [i] [0] == boardArr [1] [i] [1] && boardArr [2] [i] [0] == boardArr [0] [i] [2]))
	    {
		winningMove = true;     // Sets winningMove to true since a winning move has been found
		break;
	    }
	    else if ((boardArr [2] [0] [i] == boardArr [1] [1] [i] && boardArr [2] [0] [i] == boardArr [0] [2] [i]) || (boardArr [0] [0] [i] == boardArr [1] [1] [i] && boardArr [0] [0] [i] == boardArr [2] [2] [i]))
	    {
		winningMove = true;     // Sets winningMove to true since a winning move has been found
		break;
	    }
	}
	if ((boardArr [0] [0] [0] == boardArr [1] [1] [1] && boardArr [0] [0] [0] == boardArr [2] [2] [2]) || (boardArr [2] [2] [0] == boardArr [1] [1] [1] && boardArr [2] [2] [0] == boardArr [0] [0] [2]) || (boardArr [2] [0] [0] == boardArr [1] [1] [1] && boardArr [2] [0] [0] == boardArr [0] [2] [2]) || (boardArr [0] [2] [0] == boardArr [1] [1] [1] && boardArr [0] [2] [0] == boardArr [2] [0] [2]))
	{
	    winningMove = true;     // Sets winningMove to true since a winning move has been found
	}
	return winningMove;     // Returns whether there is a winning move or not
    }


    /*
	This method draws a 3-d tic tac toe board on the screen and labels the axes.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	No input/logic/loops are used.
    */
    public void displayGrid ()
    {
	c.setFont (defaultFont);    // Sets the font to the default font

	// Draws the top layer
	c.drawLine (228, 80, 430, 80);
	c.drawLine (222, 110, 424, 110);
	c.drawLine (216, 140, 418, 140);
	c.drawLine (210, 170, 412, 170);

	c.drawLine (228, 80, 210, 170);
	c.drawLine (430, 80, 412, 170);
	c.drawLine (295, 80, 277, 170);
	c.drawLine (363, 80, 345, 170);

	// Labels the axes
	c.drawString ("1", 260, 70);
	c.drawString ("2", 320, 70);
	c.drawString ("3", 390, 70);
	c.drawString ("(X)", 405, 70);
	c.drawString ("1", 210, 100);
	c.drawString ("2", 202, 130);
	c.drawString ("3", 195, 155);
	c.drawString ("(Y)", 186, 170);
	c.setFont (boardAxesFont);  // Sets font
	c.drawString ("[Layer 1]", 423, 130);
	c.setFont (defaultFont);    // Resets font

	// Draws the middle layer
	c.drawLine (228, 210, 430, 210);
	c.drawLine (222, 240, 424, 240);
	c.drawLine (216, 270, 418, 270);
	c.drawLine (210, 300, 412, 300);

	c.drawLine (228, 210, 210, 300);
	c.drawLine (430, 210, 412, 300);
	c.drawLine (295, 210, 277, 300);
	c.drawLine (363, 210, 345, 300);

	// Labels the axes
	c.drawString ("1", 260, 200);
	c.drawString ("2", 320, 200);
	c.drawString ("3", 390, 200);
	c.drawString ("(X)", 405, 200);
	c.drawString ("1", 210, 230);
	c.drawString ("2", 202, 260);
	c.drawString ("3", 195, 285);
	c.drawString ("(Y)", 186, 300);
	c.setFont (boardAxesFont);  // Sets font
	c.drawString ("[Layer 2]", 423, 260);
	c.setFont (defaultFont);    // Resets font

	// Draws the bottom layer
	c.drawLine (228, 340, 430, 340);
	c.drawLine (222, 370, 424, 370);
	c.drawLine (216, 400, 418, 400);
	c.drawLine (210, 430, 412, 430);

	c.drawLine (228, 340, 210, 430);
	c.drawLine (430, 340, 412, 430);
	c.drawLine (295, 340, 277, 430);
	c.drawLine (363, 340, 345, 430);

	// Labels the axes
	c.drawString ("1", 260, 330);
	c.drawString ("2", 320, 330);
	c.drawString ("3", 390, 330);
	c.drawString ("(X)", 405, 330);
	c.drawString ("1", 210, 360);
	c.drawString ("2", 202, 390);
	c.drawString ("3", 195, 415);
	c.drawString ("(Y)", 186, 430);
	c.setFont (boardAxesFont);  // Sets font
	c.drawString ("[Layer 3]", 423, 390);
	c.setFont (defaultFont);    // Resets font
    }


    /*
	This method reads the high scores from a file and outputs them to the user
	    After this it calls pauseProgram to get the user's choice and either clears the file or returns to the main menu
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables:

	    NAME            TYPE            DESCRIPTION
	    ---------------------------------------------------------------------------------------------------------------------------
	    names           String array    Used to store the names
	    scores          int array       Used to store the scores
	    br              BufferedReader  BufferedReader object that allows you to read the file
	    firstNull       int             Stores the line number of where the rankings end

	Global variables used: key (in pauseProgram to get the user's choice).
	---------------------------------------------------------------------------------------------------------------------------------------
	Input is used through pauseProgram to ask the user for their choice.
	An if-statement is used to figure out where the rankings in the file end.
	    Another one is used in order to return to the main menu or go to clearRankings depending on user input.
	For loops are used to take in the high scores from the file and output them to the screen
    */
    public void viewRankings ()
    {
	String[] names = new String [10];   // For storing the names in the high scores list
	int[] scores = new int [10];        // For storing the scores in the high scores list
	int firstNull;                      // For storing the line number of the first empty line
	BufferedReader br;                  // For reading the scores from the file

	title ();   // Calls the title method

	// Labels the table
	c.setCursor (3, 27);
	c.print ("Here are the top 10 scores:");
	c.setCursor (5, 3);
	c.print ("Rank");
	c.setCursor (5, 25);
	c.print ("Name");
	c.setCursor (5, 52);
	c.print ("Score (Number of Moves)");
	// Draws the table
	c.drawLine (0, 110, 640, 110);
	c.drawLine (60, 80, 60, 450);
	c.drawLine (350, 80, 350, 450);

	// Reads the scores from the file and outputs them
	try
	{
	    firstNull = 10;
	    br = new BufferedReader (new FileReader ("High Scores - 3D Tic Tac Toe.txt"));
	    // Outputs the rank numbers on the table
	    for (int i = 0 ; i < 10 ; i++)
	    {
		c.setCursor (7 + i, 3);
		c.print ((i + 1) + ".");
	    }
	    // Finds how many rankings there are by determining the line number of the first empty line
	    for (int i = 0 ; i < 10 ; i++)
	    {
		names [i] = br.readLine ();
		if (names [i] == null)      // Checks if the line is empty
		{
		    firstNull = i;
		    break;
		}
		// Outputs the names on the table
		c.setCursor (7 + i, 20);
		c.print (names [i]);
		// Outputs the scores on the table
		scores [i] = Integer.parseInt (br.readLine ());
		c.setCursor (7 + i, 60);
		c.print (scores [i]);
	    }
	    // Fills up empty ranks with --- to indicate that they are empty
	    for (int i = firstNull ; i < 10 ; i++)
	    {
		c.setCursor (7 + i, 20);
		c.print ("---");

		c.setCursor (7 + i, 60);
		c.print ("---");
	    }
	}
	catch (IOException e)
	{
	}

	// Asks the user if they want to clear file or go back
	c.setCursor (24, 15);
	pauseProgram ("Press 'c' to clear file, or any other key to go back...");
	if (key == 'c')
	{
	    clearRankings ();   // Calls the clearRankings method
	}
    }


    /*
	This method clears the rankings file and then calls pauseProgram to prompt the user to continue.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: pw (PrintWriter object which allows you to write to the file).
	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	This method takes in input from the file.
	It utilizes a try-catch structure to do this.
    */
    public void clearRankings ()
    {
	PrintWriter pw;     // Used for writing to a file
	try
	{
	    pw = new PrintWriter (new FileWriter ("High Scores - 3D Tic Tac Toe.txt"));
	    pw.println ();      // Clears the file
	}
	catch (IOException e)
	{
	}

	// Notifies user that the file has been cleared
	title ();
	c.setCursor (8, 18);
	c.print ("Rankings file has been successfully cleared!");
	c.setCursor (18, 27);
	pauseProgram ("Press any key to continue...");  // Prompts user to continue
    }


    /*
	This black box return method determines if a winner has made it into the high score rankings.
	It has an int score parameter which is the winner's score.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables:

	    NAME        TYPE            DESCRIPTION
	    ---------------------------------------------------------------------------------------------------------------
	    br          BufferedReader  BufferedReader object which allows you to read the scores from the file
	    String      temp            Used for reading information from the file

	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	This method takes in input from a file.
	It utilizes a try-catch structure to do this.
	There is also a for loop which is used to read multiple lines of input from the file.
	An if-statement checks if a null line has been reached in the top 10 rankings.
	Another if-else statement checks if the current winner's score beats the 10th place on the rankings.
    */
    private boolean isWinnerTop10 (int score)
    {
	BufferedReader br;      // Used to read from a file
	String temp = "";       // Used to take input from the file
	try
	{
	    br = new BufferedReader (new FileReader ("High Scores - 3D Tic Tac Toe.txt"));
	    // Finds the last rank on the list
	    for (int i = 0 ; i < 20 ; i++)
	    {
		temp = br.readLine ();
		if (temp == null)   // Checks if there is an empty spot on the rankings list
		{
		    return true;
		}
	    }
	}
	catch (IOException e)
	{
	}
	// Checks if the current winner's score beats the last score on the rankings list
	if (score < Integer.parseInt (temp))
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }


    /*
	This method asks the winning player's name and updates the rankings file.
	It has an int winningPlayer paramter which is the winner's number (used for congratulated the winner).
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: winnerName (for storing the winner's name).
	---------------------------------------------------------------------------------------------------------------------------------------
	This method repeatedly takes in input in a while loop for the winner's name, until it fits in the required limit.
	There is an if-else structure to check if the name length fits in this limit.
    */
    public void askName (int winningPlayer)
    {
	title ();
	// Congratulates winner that they have made it into the top 10
	c.setCursor (8, 8);
	c.print ("Congratulations player " + winningPlayer + ", you have made it into the Hall of Fame!");
	while (true)    // Repeatedly asks for their name until it is 15 characters or less
	{
	    // Asks for their name
	    c.setCursor (15, 22);
	    c.print ("Please enter your name: ");
	    winnerName = c.readLine ();     // Takes in input
	    if (winnerName.length () < 16)  // Checks the input String's length
	    {
		break;
	    }
	    else
	    {
		c.setCursor (15, 20);
		c.println ();
		new Message ("Your name must be 15 characters or less!", "Error!");     // Informs user about the name length constraint
	    }
	}
	updateRankings ();  // Calls the updateRankings method
    }


    /*
	This method updates the rankings file.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables:

	    NAME                    TYPE            DESCRIPTION
	    ------------------------------------------------------------------------------------------------------------------------------
	    previousNumRankings     int             Stores the number of rankings before the update
	    endNumRankings          int             Stores the number of rankings after the update
	    br/br2                  BufferedReader  BufferedReader objects used for reading a file
	    pw                      PrintWriter     PrintWriter object used for writing to a file
	    newScoreIndex           int             Stores the ranking of where the new score should go
	    names                   String array    Stores the names of the entries on the rankings
	    scores                  int array       Stores the scores of the entries on the rankings

	Global variables used: winnerName, numOfMoves (the name and score of the current winner, used to update the rankings).

	    NAME            VARIABLE TYPE               DESCRIPTION
	    ------------------------------------------------------------------------------------------------------------------------------
	    winnerName      String                      This variable is used to store the winning player's name
	    numOfMoves      int                         This variable stores the number of moves (for the score)

	---------------------------------------------------------------------------------------------------------------------------------------
	Input is taken from files.
	For loops and if-structures are used to insert the current winner into the top ten rankings based on score.
	For loops are used to read input from files and also to write to them.
    */
    public void updateRankings ()
    {
	int previousNumRankings = 10;   // For storing the beginning number of rankings
	int endNumRankings;             // For storing the end number of rankings
	BufferedReader br;              // For reading from a file
	BufferedReader br2;             // For reading from a file
	PrintWriter pw;                 // For writing to a file
	int newScoreIndex;              // For finding the ranking where the new score should go
	try
	{
	    br = new BufferedReader (new FileReader ("High Scores - 3D Tic Tac Toe.txt"));
	    for (int i = 0 ; i < 20 ; i++)
	    {
		if (br.readLine () == null)     // Checks if the line is empty
		{
		    previousNumRankings = i / 2;    // Calculates the number of rankings before the update
		    break;
		}
	    }
	}
	catch (IOException e)
	{
	}

	if (previousNumRankings != 10)  // Checks if the previous number of rankings was 10
	{
	    endNumRankings = previousNumRankings + 1;   // If no, then the end number of rankings is the previous plus one
	}
	else
	{
	    endNumRankings = previousNumRankings;       // If yes, then the end number of rankings stays 10
	}

	// NOTE TO MR. ROSEN: These two arrays cannot be declared at the beginning of the method as I need to first calculate the ending number of rankings
	String[] names = new String [endNumRankings];   // For storing the names in the high scores list
	int[] scores = new int [endNumRankings];        // For storing the scores in the high scores list

	try
	{
	    br2 = new BufferedReader (new FileReader ("High Scores - 3D Tic Tac Toe.txt"));
	    // Reads in the high scores from the file
	    for (int i = 0 ; i < previousNumRankings ; i++)
	    {
		names [i] = br2.readLine ();
		scores [i] = Integer.parseInt (br2.readLine ());
	    }
	}
	catch (IOException e)
	{
	}

	// Checks if the number of rankings was 1 (to simplify things)
	if (endNumRankings == 1)
	{
	    // The current score is the first entry in the high scores list
	    names [0] = winnerName;
	    scores [0] = numOfMoves;
	    try
	    {
		// Prints the only score on the list to the file
		pw = new PrintWriter (new FileWriter ("High Scores - 3D Tic Tac Toe.txt"));
		pw.println (names [0]);
		pw.println (scores [0]);
		pw.close ();
	    }
	    catch (IOException e)
	    {
	    }
	}
	else
	{
	    newScoreIndex = -1;     // Sets it to -1 to help determine if the current score goes last on the list
	    for (int i = 0 ; i < previousNumRankings ; i++)
	    {
		if (numOfMoves < scores [i])    // Checks if the score fits in the current position
		{
		    newScoreIndex = i;
		    break;
		}
	    }
	    if (newScoreIndex == -1)    // Checks if the score goes last on the rankings
	    {
		newScoreIndex = previousNumRankings;
	    }
	    for (int i = endNumRankings - 1 ; i > newScoreIndex ; i--)  // Shifts everything under the new score index down by one
	    {
		names [i] = names [i - 1];
		scores [i] = scores [i - 1];
	    }
	    // Inserts the new score at the according position
	    names [newScoreIndex] = winnerName;
	    scores [newScoreIndex] = numOfMoves;
	    try
	    {
		pw = new PrintWriter (new FileWriter ("High Scores - 3D Tic Tac Toe.txt"));
		for (int i = 0 ; i < endNumRankings ; i++)  // Writes the new rankings into the file
		{
		    pw.println (names [i]);
		    pw.println (scores [i]);
		}
		pw.close ();
	    }
	    catch (IOException e)
	    {
	    }
	}
    }


    /*
	This method draws a symbol on the game board.
	It has parameters which indicate where to draw the symbol, and which symbol to draw.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	If if-structure is used to set the symbol's color.
    */
    public void drawSymbol (int x, int y, int layerNumber, String symbol)
    {
	if (symbol.equals ("X"))    // Sets color according to the symbol
	{
	    c.setColor (Color.red); // For 'X'
	}
	else
	{
	    c.setColor (Color.blue);    // For 'O'
	}
	c.setFont (titleAndSymbols);  // Sets the font type and size

	// Draws the symbol at the required position on the board
	c.drawString (symbol, (245 + 70 * x) - (5 * y), (105 + 30 * y) + 130 * layerNumber);
	c.setColor (Color.black);
	c.setFont (defaultFont);   // Resets font
    }


    /*
	This method initiates the game board array to its default values.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: value (which sets the default value at each position of the board).
	Global variables used: boardArr (to reset the board's values).
	---------------------------------------------------------------------------------------------------------------------------------------
	Embedded for loops are used to traverse through the 3-d array and set values to it.
    */
    public void initiateGameArr ()
    {
	int value = 3;   // For initiating the tic tac toe board array
	// Traverses through 3d array
	for (int i = 0 ; i < BOARD_LENGTH ; i++)
	{
	    for (int j = 0 ; j < BOARD_LENGTH ; j++)
	    {
		for (int k = 0 ; k < BOARD_LENGTH ; k++)
		{
		    boardArr [i] [j] [k] = value;   // Sets a value to each element of the board array
		    value++;
		}
	    }
	}
    }


    /*
	This method runs the game by repeatedly calling methods for player 1 and player 2's turns.
	It then updates rankings if necessary and shows rankings to the user before prompting them to continue back to the main menu.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used:

	    NAME            VARIABLE TYPE               DESCRIPTION
	    ------------------------------------------------------------------------------------------------------------------------------
	    symbol          String                      This variable stores the current player's symbol
	    winnerNum       int                         This variable stores the winning player's number
	    numOfMoves      int                         This variable stores the number of moves (for the score)

	---------------------------------------------------------------------------------------------------------------------------------------
	A while loop is used to repeatedly call player 1 and player 2's turns.
	If-structures are used to check if the game is over and if the winner has made it into the high scores.
    */
    public void playGame ()
    {
	title ();                           // Calls title method
	initiateGameArr ();                 // Initiates the board
	displayGrid ();                     // Draws the board on the screen
	numOfMoves = 0;                     // Resets the number of moves variable to 0
	while (true)                        // Repeatedly executes player 1's and player 2's turn until someone wins
	{
	    player1Turn ();
	    numOfMoves++;
	    if (isGameOver () == true)  // Checks if player 1 wins
	    {
		symbol = "X";
		break;
	    }
	    player2Turn ();
	    numOfMoves++;
	    if (isGameOver () == true)  // Checks if player 2 wins
	    {
		symbol = "O";
		break;
	    }
	}
	displayResult (symbol);         // Congratulates the winner
	if (isWinnerTop10 (numOfMoves) == true)     // Checks if the winner has made it into the top 10
	{
	    if (symbol.equals ("X"))    // Determines the player number of the winner
	    {
		winnerNum = 1;
	    }
	    else
	    {
		winnerNum = 2;
	    }
	    askName (winnerNum);    // Asks the winner for their name, and then updates the rankings
	}
	viewRankings ();    // Shows the rankings on the screen
    }


    /*
	This method outputs a fancy animation and title to the screen.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables:

	    NAME            TYPE        DESCRIPTION
	    ----------------------------------------------------------------------------------------------------------------
	    background      Color       For the background color
	    explosion1      Color       For the inner layer of the explosion
	    explosion2      Color       For the middle layer of explosion
	    explosion3      Color       For the outer layer of explosion
	    titleColor      Color       For the fancy title

	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	For loops are used for the animations.
	Try-catch structures are used for the delays.
    */
    public void splashScreen ()
    {
	// Color variable declaration
	Color background = new Color (6, 247, 254);     // For the background color
	// For the explosion
	Color explosion1 = new Color (255, 222, 0);     // Inner layer of explosion
	Color explosion2 = new Color (255, 171, 0);     // Middle layer of explosion
	Color explosion3 = new Color (255, 85, 0);      // Outer layer of explosion
	Color titleColor = new Color (255, 0, 255);     // For drawing the fancy title

	c.setColor (background);        // Sets background color
	c.fillRect (0, 0, 640, 500);    // Draws background

	// Starts the 2d tic tac toe board animations
	TicTacToe_3D_Animation a = new TicTacToe_3D_Animation (c, -100, 150, 445, 1, 10);
	a.start ();
	TicTacToe_3D_Animation a1 = new TicTacToe_3D_Animation (c, -250, 150, 445, 1, 15);
	a1.start ();
	TicTacToe_3D_Animation a2 = new TicTacToe_3D_Animation (c, 640, 275, 370, -1, 20);
	a2.start ();

	try
	{
	    Thread.sleep (11000);    // Waits before merging them into a 3d board
	}
	catch (Exception e)
	{
	}

	// Draws inner circle of explosion
	for (int i = 0 ; i <= 60 ; i++)
	{
	    c.setColor (explosion1);
	    c.drawOval (320 - i, 245 - i, 2 * i, 2 * i);
	    c.drawOval (320 - i, 244 - i, 2 * i, 2 * i);
	    c.drawOval (321 - i, 245 - i, 2 * i, 2 * i);
	    try
	    {
		Thread.sleep (10);  // Delay
	    }
	    catch (Exception e)
	    {
	    }
	}
	// Draws middle circle of explosion
	for (int i = 0 ; i <= 60 ; i++)
	{
	    c.setColor (explosion2);
	    c.drawOval (260 - i, 185 - i, 120 + 2 * i, 120 + 2 * i);
	    c.drawOval (260 - i, 184 - i, 120 + 2 * i, 120 + 2 * i);
	    c.drawOval (261 - i, 185 - i, 120 + 2 * i, 120 + 2 * i);
	    try
	    {
		Thread.sleep (10);  // Delay
	    }
	    catch (Exception e)
	    {
	    }
	}
	// Draws outer circle of explosion
	for (int i = 0 ; i <= 60 ; i++)
	{
	    c.setColor (explosion3);
	    c.drawOval (200 - i, 125 - i, 240 + 2 * i, 240 + 2 * i);
	    c.drawOval (200 - i, 124 - i, 240 + 2 * i, 240 + 2 * i);
	    c.drawOval (201 - i, 125 - i, 240 + 2 * i, 240 + 2 * i);
	    try
	    {
		Thread.sleep (10);  // Delay
	    }
	    catch (Exception e)
	    {
	    }
	}

	// Clears the screen
	c.setColor (background);
	c.fillOval (138, 63, 364, 364);

	// Draws 3d grid
	c.setColor (Color.black);

	// Top layer
	c.drawLine (228, 80, 430, 80);
	c.drawLine (222, 110, 424, 110);
	c.drawLine (216, 140, 418, 140);
	c.drawLine (210, 170, 412, 170);
	c.drawLine (228, 80, 210, 170);
	c.drawLine (430, 80, 412, 170);
	c.drawLine (295, 80, 277, 170);
	c.drawLine (363, 80, 345, 170);

	// Middle layer
	c.drawLine (228, 210, 430, 210);
	c.drawLine (222, 240, 424, 240);
	c.drawLine (216, 270, 418, 270);
	c.drawLine (210, 300, 412, 300);
	c.drawLine (228, 210, 210, 300);
	c.drawLine (430, 210, 412, 300);
	c.drawLine (295, 210, 277, 300);
	c.drawLine (363, 210, 345, 300);

	// Bottom layer
	c.drawLine (228, 340, 430, 340);
	c.drawLine (222, 370, 424, 370);
	c.drawLine (216, 400, 418, 400);
	c.drawLine (210, 430, 412, 430);
	c.drawLine (228, 340, 210, 430);
	c.drawLine (430, 340, 412, 430);
	c.drawLine (295, 340, 277, 430);
	c.drawLine (363, 340, 345, 430);

	// Draws symbols at various positions on the screen
	drawSymbol (0, 1, 0, "X");
	drawSymbol (1, 2, 1, "X");
	drawSymbol (1, 1, 2, "X");
	drawSymbol (2, 0, 1, "O");
	drawSymbol (0, 0, 2, "O");
	drawSymbol (2, 1, 0, "O");

	c.setFont (titleAndSymbols);                // Sets the font type and size
	c.setColor (titleColor);                    // Sets title color
	c.drawString ("3D Tic Tac Toe", 210, 40);   // Draws the title
	c.setColor (Color.black);                   // Resets color
	c.setFont (defaultFont);                    // Resets the font type

	// Calls pauseProgram to prompt user to continue
	c.setCursor (24, 26);
	c.setTextBackgroundColor (background);      // Sets text background color to match with background
	pauseProgram ("Press any key to continue...");
	c.setTextBackgroundColor (Color.white);     // Resets text background color
    }


    /*
	This method presents the user with choices on what to do next.
	It calls pauseProgram to take input.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	Input is taken through the pauseProgram method and is stored in the key variable.
    */
    public void mainMenu ()
    {
	title ();   // Calls the title method
	// Presents the user with options
	c.setCursor (6, 32);
	c.println ("i |  Instructions");
	c.setCursor (7, 32);
	c.println ("r | View rankings");
	c.setCursor (8, 32);
	c.println ("e |          Exit");
	c.setCursor (10, 25);
	c.println ("Any other key to continue game.");
	c.println ();   // Skips a line
	c.setCursor (14, 28);   // Sets text cursor
	pauseProgram ("Please enter your choice:");     // Calls pauseProgram
    }


    /*
	This method executes player 1's turn.
	It asks for input for a board position, errorTraps to ensure it is a valid move, and draws the symbol on the board.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: temp (for input).
	Global variables used:

	    NAME            VARIABLE TYPE               DESCRIPTION
	    ------------------------------------------------------------------------------------------------------------------------------
	    xCoord          int                         This variable is used to store the x-coordinate of a position on the board
	    yCoord          int                         This variable is used to store the y-coordinate of a position on the board
	    layerNumber     int                         This variable is used to store the layer number of a position on the board
	    boardArr        int[][][]                   This 3-d array stores the board positions and whether they are occupied or not

	---------------------------------------------------------------------------------------------------------------------------------------
	While loops and try-catch structures are used for errorTrapping input.
	An if-structure is used to ensure that the spot hasn't been taken already.
    */
    public void player1Turn ()
    {
	String temp;    // For taking user input

	// Asks the user to enter the layer number
	c.drawLine (184, 0, 184, 500);  // Draws lines to make the screen look aesthetically pleasing
	c.drawLine (0, 75, 184, 75);
	c.setTextColor (Color.red);
	c.setCursor (2, 8);
	c.print ("Player 1");
	c.setTextColor (Color.black);
	c.setCursor (3, 9);
	c.print ("turn!");
	c.setCursor (8, 2);
	c.print ("Please enter the");
	c.setCursor (9, 2);
	c.print ("layer number: ");
	while (true)    // Repeatedly takes input until it is valid
	{
	    temp = "" + c.getChar ();   // Takes input and assigns it to the temp variable
	    try
	    {
		layerNumber = (Integer.parseInt (temp)) - 1;    // Converts the String into an int
		if (layerNumber == 0 || layerNumber == 1 || layerNumber == 2)   // Ensures input is valid
		{
		    break;
		}
		else
		{
		    new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
		}
	    }
	    catch (Exception e)
	    {
		new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
	    }
	}
	c.print (layerNumber + 1);  // Prints the input right next to where it asks the user

	// Asks the user to enter the x-coordinate
	c.setCursor (15, 2);
	c.print ("Please enter the");
	c.setCursor (16, 2);
	c.print ("X-coordinate: ");
	while (true)   // Repeatedly takes input until it is valid
	{
	    temp = "" + c.getChar ();   // Takes input and assigns it to the temp variable
	    try
	    {
		xCoord = (Integer.parseInt (temp)) - 1;     // Converts the String into an int
		if (xCoord == 0 || xCoord == 1 || xCoord == 2)  // Ensures input is valid
		{
		    break;
		}
		else
		{
		    new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
		}
	    }
	    catch (Exception e)
	    {
		new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
	    }
	}
	c.print (xCoord + 1);   // Prints the input right next to where it asks the user

	// Asks the user to enter the x-coordinate
	c.setCursor (22, 2);
	c.print ("Please enter the");
	c.setCursor (23, 2);
	c.print ("Y-coordinate: ");
	while (true)    // Repeatedly takes input until it is valid
	{
	    temp = "" + c.getChar ();   // Takes input and assigns it to the temp variable
	    try
	    {
		yCoord = (Integer.parseInt (temp)) - 1;     // Converts the String into an int
		if (yCoord == 0 || yCoord == 1 || yCoord == 2)  // Ensures input is valid
		{
		    break;
		}
		else
		{
		    new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
		}
	    }
	    catch (Exception e)
	    {
		new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
	    }
	}
	c.print (yCoord + 1);   // Prints the input right next to where it asks the user

	// Checks if the position on the board is already taken
	if (boardArr [xCoord] [yCoord] [layerNumber] == 1 || boardArr [xCoord] [yCoord] [layerNumber] == 2)
	{
	    new Message ("That spot is already taken!", "Error!");  // Informs the user that this position has already been taken
	    c.setColor (Color.white);       // Clears the left part of the screen
	    c.fillRect (0, 0, 185, 500);
	    c.setColor (Color.black);
	    player1Turn ();                 // Calls the entire method again
	}
	else
	{
	    drawSymbol (xCoord, yCoord, layerNumber, "X");  // Draws the symbol at the correct spot on the grid
	    c.setColor (Color.white);       // Clears the left part of the screen
	    c.fillRect (0, 0, 185, 500);
	    c.setColor (Color.black);
	    boardArr [xCoord] [yCoord] [layerNumber] = 1;   // Sets a value to the appropriate element in the board array
	}
    }


    /*
	This method executes player 2's turn.
	It asks for input for a board position, errorTraps to ensure it is a valid move, and draws the symbol on the board.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: temp (for input).
	Global variables used:

	    NAME            VARIABLE TYPE               DESCRIPTION
	    ------------------------------------------------------------------------------------------------------------------------------
	    xCoord          int                         This variable is used to store the x-coordinate of a position on the board
	    yCoord          int                         This variable is used to store the y-coordinate of a position on the board
	    layerNumber     int                         This variable is used to store the layer number of a position on the board
	    boardArr        int[][][]                   This 3-d array stores the board positions and whether they are occupied or not

	---------------------------------------------------------------------------------------------------------------------------------------
	While loops and try-catch structures are used for errorTrapping input.
	An if-structure is used to ensure that the spot hasn't been taken already.
    */
    public void player2Turn ()
    {
	String temp;    // For taking in user input

	// Asks user to enter the layer number
	c.drawLine (468, 0, 468, 500);  // Draws lines to make the screen look aesthetically pleasing
	c.drawLine (468, 75, 640, 75);
	c.setTextColor (Color.blue);
	c.setCursor (2, 67);
	c.print ("Player 2");
	c.setTextColor (Color.black);
	c.setCursor (3, 68);
	c.print ("turn!");
	c.setCursor (8, 61);
	c.print ("Please enter the");
	c.setCursor (9, 61);
	c.print ("layer number: ");
	while (true)    // Repeatedly takes input until it is valid
	{
	    temp = "" + c.getChar ();   // Takes input and assigns it to the temp variable
	    try
	    {
		layerNumber = (Integer.parseInt (temp)) - 1;    // Converts the String into an int
		if (layerNumber == 0 || layerNumber == 1 || layerNumber == 2)   // Ensures input is valid
		{
		    break;
		}
		else
		{
		    new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
		}
	    }
	    catch (Exception e)
	    {
		new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
	    }
	}
	c.print (layerNumber + 1);  // Prints the input right next to where it asks the user

	// Asks user to enter the x-coordinate
	c.setCursor (15, 61);
	c.print ("Please enter the");
	c.setCursor (16, 61);
	c.print ("X-coordinate: ");
	while (true)   // Repeatedly takes input until it is valid
	{
	    temp = "" + c.getChar ();   // Takes input and assigns it to the temp variable
	    try
	    {
		xCoord = (Integer.parseInt (temp)) - 1;     // Converts the String into an int
		if (xCoord == 0 || xCoord == 1 || xCoord == 2)  // Ensures input is valid
		{
		    break;
		}
		else
		{
		    new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
		}
	    }
	    catch (Exception e)
	    {
		new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
	    }
	}
	c.print (xCoord + 1);   // Prints the input right next to where it asks the user

	// Asks user to enter y-coordinate
	c.setCursor (22, 61);
	c.print ("Please enter the");
	c.setCursor (23, 61);
	c.print ("Y-coordinate: ");
	while (true)    // Repeatedly takes input until it is valid
	{
	    temp = "" + c.getChar ();   // Takes input and assigns it to the temp variable
	    try
	    {
		yCoord = (Integer.parseInt (temp)) - 1;     // Converts the String into an int
		if (yCoord == 0 || yCoord == 1 || yCoord == 2)  // Ensures input is valid
		{
		    break;
		}
		else
		{
		    new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
		}
	    }
	    catch (Exception e)
	    {
		new Message ("Please enter either 1, 2, or 3!", "Error!");  // Informs reader that their input is invalid
	    }
	}
	c.print (yCoord + 1);   // Prints the input right next to where it asks the user

	// Checks if the position on the board has already been taken
	if (boardArr [xCoord] [yCoord] [layerNumber] == 1 || boardArr [xCoord] [yCoord] [layerNumber] == 2)
	{
	    new Message ("That spot is already taken!", "Error!");  // Informs the user that their selection has already been taken
	    c.setColor (Color.white);   // Clears the right part of the screen
	    c.fillRect (467, 0, 640, 500);
	    c.setColor (Color.black);
	    player2Turn ();             // Calls the entire method again
	}
	else
	{
	    drawSymbol (xCoord, yCoord, layerNumber, "O");  // Draws the symbol at the correct spot on the grid
	    c.setColor (Color.white);   // Clears the right part of the screen
	    c.fillRect (467, 0, 640, 500);
	    c.setColor (Color.black);
	    boardArr [xCoord] [yCoord] [layerNumber] = 2;   // Sets a value to the appropriate element of the board array
	}
    }


    /*
	This method draws the game over screen and congratulates the winner.
	It has a String symbol parameter to indicate which symbol is the winner.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: playerNum (to store the winning player's number).
	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	An if-structure is used to set the color and the winning player's number.
    */
    public void displayResult (String symbol)
    {
	int playerNum;  // For storing the player number of the winner

	title ();   // Calls the title method
	c.setFont (resultCongratulate);  // Sets the font type and size
	c.drawString ("Congratulations!", 200, 110);    // Congratulates the winner
	if (symbol.equals ("X"))    // Sets color and the winner's player number depending on the winning symbol
	{
	    c.setColor (Color.red);
	    playerNum = 1;
	}
	else
	{
	    c.setColor (Color.blue);
	    playerNum = 2;
	}
	// Further flatters the winner
	c.drawString ("Player " + playerNum + "!!!", 250, 160);
	c.setCursor (10, 31);
	c.print ("You are the winner!");
	c.setCursor (11, 31);
	c.print ("You won in " + numOfMoves + " moves!");   // Tells the winner their score

	c.setFont (resultSymbol);   // Sets font
	c.drawString (symbol, 235, 400);    // More fancy decorations
	c.setCursor (23, 26);
	c.setFont (defaultFont);    // Resets the font
	c.setColor (Color.black);
	pauseProgram ("Press any key to continue...");  // Prompts user to continue
    }


    /*
	This method outputs the instructions for the game and prompts the user to continue.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: key (used through pauseProgram for prompting the user).
	---------------------------------------------------------------------------------------------------------------------------------------
	Input is taken through pauseProgram to prompt user to continue.
    */
    public void instructions ()
    {
	title ();   // Calls the title method
	// Outputs the instructions of the game
	c.setCursor (6, 1);
	c.println ("This game is 3D Tic Tac Toe. Both players go turn by turn filling up the board  with their respective symbols ('O' and 'X'). The   first player is the 'X'. The first player to get 3 symbols in a row wins. The number of moves that have      occured in the game is the winner's score. The lower this number, the better    your score.");
	c.setCursor (12, 35);
	// Wishes the users luck
	c.println ("Good luck!");
	c.setCursor (20, 27);
	pauseProgram ("Press any key to go back...");   // Prompts user to continue
    }


    /*
	This method clears the screen and outputs the title.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: None.
	---------------------------------------------------------------------------------------------------------------------------------------
	No input/logic/loops are used.
    */
    public void title ()
    {
	c.clear ();                     // Clears screen
	c.print (' ', 33);              // Adds extra spaces to center the title
	c.println ("3D Tic Tac Toe");   // Prints title
	c.println ();                   // Skips a line
    }


    /*
	This method takes in char input and prompts the user to continue.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: key (for taking char input).
	---------------------------------------------------------------------------------------------------------------------------------------
	Input is used to prompt the user.
    */
    public void pauseProgram (String message)
    {
	c.print (message);      // Prints the message to the screen
	key = c.getChar ();     // Takes a char input from the user
    }


    /*
	This method thanks the user for using the game and closes the window after prompting the user.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: key (used through pauseProgram for prompting user).
	---------------------------------------------------------------------------------------------------------------------------------------
	Input is taken through pauseProgram for prompting the user.
    */
    public void goodbye ()
    {
	title ();   // Calls the title method
	// Thanks the user for using the program and shows the creator of the program
	c.setCursor (12, 25);
	c.println ("Thanks for using this program!");
	c.setCursor (13, 30);
	c.println ("Created by: Om Patel");
	c.setCursor (20, 28);
	pauseProgram ("Press any key to quit...");  // Prompts the user to continue
	c.close ();     // Closes the window
    }


    public TicTacToe_3D ()     // Class constructor
    {
	c = new Console ("3D Tic Tac Toe");     // Initializes Console object
    }


    /*
	This is the main method and it executes the other methods with a TicTacToe_3D object.
	It also uses a loop to keep repeating the program until the user wants to quit.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used: key (for checking if the user wants to quit).
	---------------------------------------------------------------------------------------------------------------------------------------
	This method uses a while loop to keep repeating the program until the user wants to exit.
	It also uses an if-else structure to check when the user wants to exit.
    */
    public static void main (String[] args)
    {
	TicTacToe_3D t = new TicTacToe_3D ();     // Creates an object of the class
	t.splashScreen ();  // Executes the splashScreen method
	while (true)    // Repeatedly executes the program until the user wants to exit
	{
	    t.mainMenu ();      // Executes the mainMenu method
	    if (key == 'i')
	    {
		t.instructions ();  // Outputs instructions
	    }
	    else if (key == 'r')
	    {
		t.viewRankings ();  // Shows rankings
	    }
	    else if (key == 'e')
	    {
		break;  // Exits the loop and goes to the goodbye method
	    }
	    else
	    {
		t.playGame ();  // Continues on with the game
	    }
	}
	t.goodbye ();   // Executes the goodbye method
    } // main method
} // TicTacToe_3D class
