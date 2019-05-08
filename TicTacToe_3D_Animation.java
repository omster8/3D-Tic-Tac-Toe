/*
    Om Patel
    3D Tic Tac Toe - Animation Class
    Mr. Rosen
    January 16, 2019
    This class is for the animations in the splash screen for the 3D Tic Tac Toe project.
    
    NAME            VARIABLE TYPE               DESCRIPTION
    ------------------------------------------------------------------------------------------------------------------------------
    xCoordinate     int                         This variable is used to store the left-most x-coordinate of the 2d board
    yCoordinate     int                         This variable is used to store the top-most y-coordinate of the 2d board
    direction       int                         This variable is for indicating the direction (1 for right or -1 for left)
    distance        int                         This variable is for the distance for the board to travel
    delay           int                         This variable is for the speed of the board
*/
import java.awt.*;
import java.lang.*;     // Allows access to Thread class
import hsa.Console;

public class TicTacToe_3D_Animation extends Thread
{
    private static Console c;           // The output console

    // Global variable declaration
    private int xCoordinate;    // x-coordinate of the board
    private int yCoordinate;    // y-coordinate of the board
    private int direction;      // direction (1 for right, -1 for left)
    private int distance;       // the distance for the board to travel
    private int delay;          // the speed of the board

    /*
	This method draws the animation of a 2d tic tac toe board flying into the screen.
	---------------------------------------------------------------------------------------------------------------------------------------
	Local variables: None.
	Global variables used:
	
	    NAME            VARIABLE TYPE               DESCRIPTION
	    ------------------------------------------------------------------------------------------------------------------------------
	    xCoordinate     int                         This variable is used to store the left-most x-coordinate of the 2d board
	    yCoordinate     int                         This variable is used to store the top-most y-coordinate of the 2d board
	    direction       int                         This variable is for indicating the direction (1 for right or -1 for left)
	    distance        int                         This variable is for the distance for the board to travel
	    delay           int                         This variable is for the speed of the board
	    
	---------------------------------------------------------------------------------------------------------------------------------------
	This method uses a for loop to draw the animation.
    */
    public void draw2DGrid ()
    {
	Color XSymbol = new Color (255, 0, 0);          // x symbol color
	Color OSymbol = new Color (0, 0, 255);          // o symbol color
	Color background = new Color (6, 247, 254);     // background color (for erase)

	Font symbolFont = new Font ("Comic Sans MS", Font.BOLD, 30);    // creates a new Font variable for the symbols

	for (int i = 0 ; i <= distance ; i++)
	{
	    // Erase
	    c.setColor (background);    // Sets color to the background color
	    c.fillRect (xCoordinate - 1 + direction * i, yCoordinate - 1, 103, 103);

	    // Grid lines
	    c.setColor (Color.black);   // Sets color
	    c.drawLine (xCoordinate + direction * i, yCoordinate, xCoordinate + 100 + direction * i, yCoordinate);
	    c.drawLine (xCoordinate + direction * i, yCoordinate + 33, xCoordinate + 100 + direction * i, yCoordinate + 33);
	    c.drawLine (xCoordinate + direction * i, yCoordinate + 67, xCoordinate + 100 + direction * i, yCoordinate + 67);
	    c.drawLine (xCoordinate + direction * i, yCoordinate + 100, xCoordinate + 100 + direction * i, yCoordinate + 100);

	    c.drawLine (xCoordinate + direction * i, yCoordinate, xCoordinate + direction * i, yCoordinate + 100);
	    c.drawLine (xCoordinate + 33 + direction * i, yCoordinate, xCoordinate + 33 + direction * i, yCoordinate + 100);
	    c.drawLine (xCoordinate + 67 + direction * i, yCoordinate, xCoordinate + 67 + direction * i, yCoordinate + 100);
	    c.drawLine (xCoordinate + 100 + direction * i, yCoordinate, xCoordinate + 100 + direction * i, yCoordinate + 100);

	    // X symbols
	    c.setFont (symbolFont);  // Sets font
	    c.setColor (XSymbol);    // Sets color
	    c.drawString ("X", xCoordinate + 5 + direction * i, yCoordinate + 30);
	    c.drawString ("X", xCoordinate + 73 + direction * i, yCoordinate + 60);

	    // O symbols
	    c.setColor (OSymbol);   // Sets color
	    c.drawString ("O", xCoordinate + 5 + direction * i, yCoordinate + 95);
	    c.drawString ("O", xCoordinate + 38 + direction * i, yCoordinate + 30);

	    try
	    {
		Thread.sleep (delay);   // Delay
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public TicTacToe_3D_Animation (Console con, int x, int y, int dist, int dir, int t)    // Creates an object of the class
    {
	// Sets the variables of this class equal to the parameters of this object
	c = con;
	xCoordinate = x;
	yCoordinate = y;
	distance = dist;
	direction = dir;
	delay = t;
    }


    public void run ()  // run method
    {
	draw2DGrid ();  // calls the draw2DGrid method to start the animation
    }
} // TicTacToe_3D_Animation class
