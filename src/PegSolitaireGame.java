import java.util.Scanner;

public class PegSolitaireGame
{
	/**
	 * This method is responsible for everything from displaying the opening
	 * welcome message to printing out the final thank you.  It will clearly be
	 * helpful to call several of the following methods from here, and from the
	 * methods called from here.  See the Sample Runs below for a more complete
	 * idea of everything this method is responsible for.
	 *
	 * @param args - any command line arguments may be ignored by this method.
	 */
	public static void main(String[] args)
	{
		System.out.println("WELCOME TO CS300 PEG SOLITAIRE!");
		System.out.println("===============================\n");
		Scanner in = new Scanner(System.in);

		int boardType = readValidInt(in, "Board Style Menu\n   1) Cross\n   2) Circle\n   3) Triangle\n   4) Simple T\nChoose a board style: ", 1, 4);
		char[][] mainBoard = createBoard(boardType);

		while(countPegsRemaining(mainBoard) != 1 && countMovesAvailable(mainBoard) != 0) {
			displayBoard(mainBoard);
			int[] userInputs = readValidMove(in, mainBoard);
			mainBoard = performMove(mainBoard, userInputs[1], userInputs[0], userInputs[2]);
		}

		if (countPegsRemaining(mainBoard) == 1) {
			displayBoard(mainBoard);
			System.out.println("Congrats, you won!");
		} else if (countMovesAvailable(mainBoard) == 0) {
			displayBoard(mainBoard);
			System.out.println("It looks like there are no more legal moves.  Please try again.");
		}

		System.out.println("\n==========================================");
		System.out.println("THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");
	}

	/**
	 * This method is used to read in all inputs from the user.  After printing
	 * the specified prompt, it will check whether the user�s input is in fact
	 * an integer within the specified range.  If the user�s input does not
	 * represent an integer or does not fall within the required range, print
	 * an error message asking for a value within that range before giving the
	 * user another chance to enter valid input.  The user should be given as
	 * many chances as they need to enter a valid integer within the specified
	 * range.  See the Sample Runs to see how these error messages should be
	 * phrased, and to see how the prompts are repeated when multiple invalid
	 * inputs are entered by the user.
	 *
	 * @param in - user input from standard in is ready through this.
	 * @param prompt - message describing what the user is expected to enter.
	 * @param min - the smallest valid integer that the user may enter.
	 * @param max - the largest valid integer that the user may enter.
	 * @return - the valid integer between min and max entered by the user.
	 */
    public static int readValidInt(Scanner in, String prompt, int min, int max) {
        System.out.print(prompt);

        while(true) {
            while(!in.hasNextInt()) {
                in.next();
                System.out.print("Please enter your choice as an integer between " + min + " and " + max + ": ");
            }
            int input = in.nextInt();
            if(input >= min && input <= max) {
                return input;
            }
            System.out.print("Please enter your choice as an integer between " + min + " and " + max + ": ");
        }
    }

	/**
	 * This method creates, initializes, and then returns a rectangular two
	 * dimensional array of characters according to the specified boardType.
	 * Initial configurations for each of the possible board types are depicted
	 * below.  Note that pegs are displayed as @s, empty holes are displayed as
	 * -s, and extra blank positions that are neither pegs nor holes within
	 * each rectangular array are displayed as #s.
	 *
	 * @param boardType - 1-4 indicating one of the following initial patterns:
	 *   1) Cross
	 *     ###@@@###
	 *     ###@@@###
	 *     @@@@@@@@@
	 *     @@@@-@@@@
	 *     @@@@@@@@@
	 *     ###@@@###
	 *     ###@@@###
	 *
	 *   2) Circle
	 *     #-@@-#
	 *     -@@@@-
	 *     @@@@@@
	 *     @@@@@@
	 *     -@@@@-
	 *     #-@@-#
	 *
	 *   3) Triangle
	 *     ###-@-###
	 *     ##-@@@-##
	 *     #-@@-@@-#
	 *     -@@@@@@@-
	 *
	 *   4) Simple T
	 *     -----
	 *     -@@@-
	 *     --@--
	 *     --@--
	 *     -----
	 *
	 * @return - the fully initialized two dimensional array.
	 */
	public static char[][] createBoard(int boardType) {
		//Implementation for Cross
		if (boardType == 1) {
			return new char[][]{
					{'#', '#', '#', '@', '@', '@', '#', '#', '#'},
					{'#', '#', '#', '@', '@', '@', '#', '#', '#'},
					{'@', '@', '@', '@', '@', '@', '@', '@', '@'},
					{'@', '@', '@', '@', '-', '@', '@', '@', '@'},
					{'@', '@', '@', '@', '@', '@', '@', '@', '@'},
					{'#', '#', '#', '@', '@', '@', '#', '#', '#'},
					{'#', '#', '#', '@', '@', '@', '#', '#', '#'},
			};
		}

		//Implementation for Circle
		if (boardType == 2) {
			return new char[][]{
					{'#', '-', '@', '@', '-', '#'},
					{'-', '@', '@', '@', '@', '-'},
					{'@', '@', '@', '@', '@', '@'},
					{'@', '@', '@', '@', '@', '@'},
					{'-', '@', '@', '@', '@', '-'},
					{'#', '-', '@', '@', '-', '#'},
			};
		}

		//Implementation for Triangle
		if (boardType == 3) {

			return new char[][]{
					{'#', '#', '#', '-', '@', '-', '#', '#', '#'},
					{'#', '#', '-', '@', '@', '@', '-', '#', '#'},
					{'#', '-', '@', '@', '-', '@', '@', '-', '#'},
					{'-', '@', '@', '@', '@', '@', '@', '@', '-'},
			};
		}

		//Implementation for Simple T
		if (boardType == 4) {
			return new char[][]{
					{'-', '-', '-', '-', '-'},
					{'-', '@', '@', '@', '-'},
					{'-', '-', '@', '-', '-'},
					{'-', '-', '@', '-', '-'},
					{'-', '-', '-', '-', '-'},
			};
		}
		return null;
	}

	/**
	 * This method prints out the contents of the specified board using @s to
	 * represent pegs, -s to represent empty hole, and #s to represent empty
	 * positions that are neither pegs nor holes.  In addition to this, the
	 * columns and rows of this board should be labelled with numbers starting
	 * at one and increasing from left to right (for column labels) and from
	 * top to bottom (for row labels).  See the Sample Runs for examples of how
	 * these labels appear next to boards with different dimensions.
	 *
	 * @param board - the current state of the board being drawn.
	 */
	public static void displayBoard(char[][] board) {
		System.out.println();
		int rowLabel = 1;
		int colLabel = 1;
		for (int r = 0; r < board.length; r++) {
			while (colLabel <= board[0].length) {
				if (colLabel == 1) {
					System.out.print("  ");
				}
				System.out.print(colLabel);
				colLabel++;
			}
			if (colLabel == board[0].length + 1) {
				System.out.println();
				colLabel++;
			}
			for (int c = 0; c < board[0].length; c++) {
				if (c == 0) {
					System.out.print(rowLabel + " ");
					rowLabel++;
				}
				System.out.print(board[r][c]);
			}
			System.out.println();
		}
	}


	/**
	 * This method is used to read in and validate each part of a user�s move
	 * choice: the row and column that they wish to move a peg from, and the
	 * direction that they would like to move/jump that peg in.  When the
	 * player�s row, column, and direction selection does not represent a valid
	 * move, your program should report that their choice does not constitute a
	 * legal move before giving them another chance to enter a different move.
	 * They should be given as many chances as necessary to enter a legal move.
	 * The array of three integers that this method returns will contain: the
	 * user�s choice of column as the first integer, their choice of row as the
	 * second integer, and their choice of direction as the third.
	 *
	 * @param in - user input from standard in is ready through this.
	 * @param board - the state of the board that moves must be legal on.
	 * @return - the user's choice of column, row, and direction representing
	 *   a valid move and store in that order with an array.
	 */
	public static int[] readValidMove(Scanner in, char[][] board)
	{
		while(true) {
			int colInput = readValidInt(in, "Choose the COLUMN of a peg you'd like to move: ", 1, board[0].length);
			int rowInput = readValidInt(in, "Choose the ROW of a peg you'd like to move: ", 1, board.length);
			int directionInput = readValidInt(in, "Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ", 1, 4);
			String dir = "";
			if(directionInput == 1) {
				dir = "UP";
			} else if(directionInput == 2) {
				dir = "DOWN";
			} else if(directionInput == 3) {
				dir = "LEFT";
			} else if(directionInput == 4) {
				dir = "RIGHT";
			}
			if(isValidMove(board, rowInput, colInput, directionInput)) {
				return new int[]{colInput, rowInput, directionInput};
			}
			System.out.println("Moving a peg from row " + rowInput + " and column " + colInput + " " + dir + " is not currently a legal move.");
			System.out.println();
		}
	}

	/**
	 * This method checks whether a specific move (column + row + direction) is
	 * valid within a specific board configuration.  In order for a move to be
	 * a valid: 1)there must be a peg at position row, column within the board,
	 * 2)there must be another peg neighboring that first one in the specified
	 * direction, and 3)there must be an empty hole on the other side of that
	 * neighboring peg (further in the specified direction).  This method
	 * only returns true when all three of these conditions are met.  If any of
	 * the three positions being checked happen to fall beyond the bounds of
	 * your board array, then this method should return false.  Note that the
	 * row and column parameters here begin with one, and may need to be
	 * adjusted if your programming language utilizes arrays with zero-based
	 * indexing.
	 *
	 * @param board - the state of the board that moves must be legal on.
	 * @param row - the vertical position of the peg proposed to be moved.
	 * @param column - the horizontal position of the peg proposed to be moved.
	 * @param direction - the direction proposed to move/jump that peg in.
	 * @return - true when the proposed move is legal, otherwise false.
	 */
	public static boolean isValidMove(char[][] board, int row, int column, int direction) {
		int rowIndex = row - 1;
		int columnIndex = column - 1;

		if (board[rowIndex][columnIndex] != '@') {
			return false;
		}
		if (direction == 1) {
			if (row == 1 || row == 2) {
				return false;
			}
			if (board[rowIndex - 1][columnIndex] != '@' || board[rowIndex - 2][columnIndex] != '-') {
				return false;
			}
		}
		if (direction == 2) {
			if (row == board.length || row == board.length - 1) {
				return false;
			}
			if (board[rowIndex + 1][columnIndex] != '@' || board[rowIndex + 2][columnIndex] != '-') {
				return false;
			}
		}
		if (direction == 3) {
			if (column == 1 || column == 2) {
				return false;
			}
			if (board[rowIndex][columnIndex - 1] != '@' || board[rowIndex][columnIndex - 2] != '-') {
				return false;
			}
		}
		if (direction == 4) {
			if (column == board[0].length || column == board[0].length - 1) {
				return false;
			}
			if (board[rowIndex][columnIndex + 1] != '@' || board[rowIndex][columnIndex + 2] != '-') {
				return false;
			}
		}
		return true;
	}

	/**
	 * The parameters of this method are the same as those of the isValidMove()
	 * method.  However this method changes the board state according to this
	 * move parameter (column + row + direction), instead of validating whether
	 * the move is valid.  If the move specification that is passed into this
	 * method does not represent a legal move, then do not modify the board.
	 *
	 * @param board - the state of the board will be changed by this move.
	 * @param row - the vertical position that a peg will be moved from.
	 * @param column - the horizontal position that a peg will be moved from.
	 * @param direction - the direction of the neighbor to jump this peg over.
	 * @return - the updated board state after the specified move is taken.
	 */
	public static char[][] performMove(char[][] board, int row, int column, int direction)
	{
		int rowIndex = row - 1;
		int columnIndex = column - 1;

		if (isValidMove(board, row, column, direction)) {
			board[rowIndex][columnIndex] = '-';
			if (direction == 1) {
				board[rowIndex - 2][columnIndex] = '@';
				board[rowIndex - 1][columnIndex] = '-';
			}
			if (direction == 2) {
				board[rowIndex + 2][columnIndex] = '@';
				board[rowIndex + 1][columnIndex] = '-';
			}
			if (direction == 3) {
				board[rowIndex][columnIndex - 2] = '@';
				board[rowIndex][columnIndex - 1] = '-';
			}
			if (direction == 4) {
				board[rowIndex][columnIndex + 2] = '@';
				board[rowIndex][columnIndex + 1] = '-';
			}
		}
		return board;
	}

	/**
	 * This method counts up the number of pegs left within a particular board
	 * configuration, and returns that number.
	 *
	 * @param board - the board that pegs are counted from.
	 * @return - the number of pegs found in that board.
	 */
	public static int countPegsRemaining(char[][] board)
	{
		int pegsRemaining = 0;
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length; c++) {
				if(board[r][c] == '@') {
					pegsRemaining++;
				}
			}
		}
		return pegsRemaining;
	}

	/**
	 * This method counts up the number of legal moves that are available to be
	 * performed in a given board configuration.
	 *
	 * HINT: Would it be possible to call the isValidMove() method for every
	 * direction and from every position within your board?  Counting up the
	 * number of these calls that return true should yield the total number of
	 * moves available within a specific board.
	 *
	 * @param board - the board that possible moves are counted from.
	 * @return - the number of legal moves found in that board.
	 */
	public static int countMovesAvailable(char[][] board)
	{
		int movesAvailable = 0;
		for(int r = 1; r <= board.length; r++) {
			for(int c = 1; c <= board[0].length; c++) {
				for(int i = 1; i <= 4; i++) {
					if(isValidMove(board, r, c, i)) {
						movesAvailable++;
					}
				}
			}
		}
		return movesAvailable;
	}
}
