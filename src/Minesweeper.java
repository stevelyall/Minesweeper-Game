/* Minesweeper.java
 *
 *
 * Steven Lyall 
 *
 * To do:
 * - instructions when app starts?
 * - ease of use?
 * - make board displayed bigger? easier to see?
 * - difficulty?
 * - combine row/column input into one line?
 * - package into executable? browser?
 */

import java.util.Scanner;

public class Minesweeper {
	static Scanner kb = new Scanner(System.in);
	static boolean gameOver = false;
	static boolean gameWon = false;
	static int cellsToCheck = 0;
	static int cellsUncovered = 0;

	// runs each turn player takes
	static void attempt(Board board) {
		board.displayBoard(); //prints the board matrix
		int i =0, j =0;
		// take i and j from user, zero to mark cells
		System.out.println("Enter 0 to mark a cell.");
		System.out.println("Enter row to check:");
		i = kb.nextInt(); 
		if (i==0) { // if a zero is entered, go to mark
			mark(board);
		}
		if (i != 0) { // if zero entered for row and user has marked a space, don't ask for column
			System.out.println("Enter column to check:"); // TODO can i and j values be merged into one input?
			j = kb.nextInt();
			if (j==0) { // if a zero is entered, go to mark
				mark(board);
			}
		}
		if ((i != 0) && (j != 0)) { // if user has marked a cell this turn, skip and start next turn
			if ((i!= board.getBoardWidth()+1) && (j != board.getBoardHeight()+1)) { // ensure input is on the board
				System.out.println("You entered " + i + ", " + j);
				if (board.hasBeenChecked(i, j) == true) { // already attempted
					System.out.println("You have already tried that space.");
				}
				if (!board.hasBeenChecked(i, j)) {
					if (board.checkforBomb(i, j)) { // bomb there
						board.setChecked(i, j);
						gameOver=true;

					}
					else {
						board.setChecked(i, j); // no bomb, reveal cell, count
						cellsUncovered++;
						System.out.println("No bombs yet...");
					}
				}
			}
			else {
				System.out.println("Invalid input. Please enter a column and row that is on the board.");
			}
		}
	}	
	// let user mark uncovered cells that could contain bomb
	static void mark(Board board) {
		System.out.println("Enter row to mark:");
		int i = kb.nextInt();
		System.out.println("Enter column to mark:");
		int j = kb.nextInt();
		if ((i != 0) && (j != 0)) {
			if ((i!= board.getBoardWidth()+1) && (j != board.getBoardHeight()+1)) {
				System.out.println("Marked the space at " + i + ", " + j);
				board.toggleCellMarked(i,j);
			}
			else {
				System.out.println("Invalid input. Please enter a column and row that is on the board.");
			}
		}
		else {
			System.out.println("Invalid input. Please enter a column and row that is on the board.");
		}
	}

	// run when ij contains bomb, breaks loop in main and shows result
	static void gameOver(Board board) {
		gameOver=true;
		board.revealAll();
		board.updateBoard();
		board.displayBoard();
		System.out.println("Game Over");
	}
	// finds out if player has uncovered all cells without bombs

	static boolean GameWon(Board board) {
		cellsToCheck = board.getCellsToCheck();
		if (cellsUncovered==cellsToCheck) {
			return true;
		}
		else {			
			return false;
		}
	}
	
	public static void main (String[] args) {

		// set size of game board
		int M = 5;
		int N = 4;
		// set number of bombs, lower means more
		double p = 0.70;

		Board board = new Board(M,N,p);

		do {
			attempt(board);
			board.updateBoard();
		}
		while (!gameOver && !GameWon(board));

		if (GameWon(board)) {
			board.updateBoard();
			board.displayBoard();
			System.out.println("YOU WIN!");
		}
		if (gameOver) {
			gameOver(board);
		}
	}
}