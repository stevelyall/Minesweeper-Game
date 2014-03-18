/* Minesweeper.java
 *
 *
 * Steven Lyall 
 *
 * To do:
 * - fix cells not marking?
 * - make sure user can't mark border?
 * - instructions when app starts?
 * - ease of use?
 * - clean up method params? possible to move arrays being passed to class scope?
 * - OOP? move to other classes?
 * - make board displayed bigger? easier to see?
 * - lower difficulty?
 * - combine row/column input into one line?
 * -package into executable? browser?
 * 
 */

import java.util.*;

class MatrixPrinter {

	// prints bomb locations for testing, hidden from user
	static void printbMatrix(boolean[][] matrix) {
		for (int i =0;i<matrix.length;i++) {
			for (int j=0;j<matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	// print number of adjacent bombs for all cells
		static void displayCount(int[][] matrix) {
			for (int i =0;i<matrix.length;i++) {
				for (int j=0;j<matrix[0].length; j++) {
					System.out.print(matrix[i][j] + " ");
				}
				System.out.println();
			}
		}
}
public class Minesweeper {
	static int M, N;
	static double p;
	static Scanner kb = new Scanner(System.in);
	static boolean gameOver = false;
	static boolean gameWon = false;
	static int cellsToCheck = 0;
	static int cellsUncovered = 0;
	static int numBombs = 0;

// distributes T and F bomb locations in bomb matrix
	static void setBombPositions(boolean[][] bombPositions) {

		Random rand = new Random();
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N;j++) {
				double r = rand.nextDouble();
				if (p<r) {
					bombPositions[i][j] = true;
					numBombs++;
				}
			}
		}
	}
// counts bombs in adjacent cells and set up matrix to hold them
	static void countAdjacent(boolean[][] bombPositions, int[][] b) {
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N; j++) {
				if (bombPositions[i][j] == true) {
					for (int r=i-1;r<=i+1;r++) {
						for (int c=j-1;c<=j+1;c++) {
							if (r==i && c==j) {
								continue; 
								}
								b[r][c] += 1;	
						}
					}
				}
			}
		}
	}
// make unused edge cells into border
	static void setBoardBorder(String[][]a) {
		for (int i =0;i<M+2;i++) {
			for (int j=0;j<N+2; j++) {
				if (i == 0) {
					a[i][j] = "--";
				}
				if (i==a.length-1) {
					a[i][j] = "--";
				}
				if (j==0) {
					a[i][j] = "|";
				}
				if (j==a[i].length-1) {
					a[i][j] = "|";
				}
				if ((i == 0 && j == 0) || (i == a.length-1 && j==a[i].length-1) || (i==0 && j==a[i].length-1) || (i==a.length-1 && j==0)) {
					a[i][j] = " ";
				}
			}
		}
	}
// update the game board matrix as game progresses
	static void setBoardState(boolean[][] bombPositions, String[][] b, boolean[][] uncovered, int[][] c) {
		for (int i =1;i<bombPositions.length-1;i++) {
			for (int j=1;j<bombPositions[0].length-1; j++) {
				if (uncovered[i][j]) {
					if (bombPositions[i][j] == true) {
						b[i][j] = "* ";
					}
					else
					b[i][j] = Integer.toString(c[i][j]) + " ";
				}
				else {
					b[i][j] = "# ";	
				}
			}
		}
	}

// prints matrix that user sees
	static void displayBoard(String[][] matrix) {
		for (int i =0;i<matrix.length;i++) {
			for (int j=0;j<matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	// runs each turn player takes
	static void attempt(boolean[][] bombPositions, boolean[][] uncovered, String[][] board) {
		displayBoard(board); //prints the board matrix
		int i =0, j =0;
		// take i and j from user, zero to mark cells
		System.out.println("Enter 0 to mark a cell.");
		System.out.println("Enter row to try:");
		i = kb.nextInt(); 
			if (i==0) { // if a zero is entered, go to mark
			mark(board);
		}
		if (i != 0) { // if zero entered for row and user has marked a space, don't ask for column
			System.out.println("Enter column to try:"); //// TODO can i and j values be merged into one input?
			j = kb.nextInt();
				if (j==0) { // if a zero is entered, go to mark
				mark(board);
			}
		}
		if ((i != 0) && (j != 0)) { // if user has marked a cell this turn, skip this
			if ((i!= M+1) && (j != N+1)) {
				System.out.println("You entered " + i + ", " + j); // check if bomb in boolean matrix at ij
				if (uncovered[i][j] == true) { // already attempted
					System.out.println("You have already tried that space");
				}
				if (!uncovered[i][j]) {
					if (bombPositions[i][j]) { // bomb there, game over
						uncovered[i][j] = true;
						gameOver=true;
	
					}
					else {
						uncovered[i][j] = true; // no bomb, reveal cell, count
						cellsUncovered++;
						System.out.println("Still no bombs...");
					}
				}
			}
			else {
			System.out.println("Invalid input. Please enter a column and row that is on the board.");
			}
		}
	}	
	// let user mark uncovered cells that could contain bomb
	static void mark(String[][] board) {
		System.out.println("Enter row to mark:");
		int i = kb.nextInt();
		System.out.println("Enter column to mark:");
		int j = kb.nextInt();
		System.out.println(i + ", " + j + " marked as a possible bomb.");
		board[i][j] = "! ";
	}
	// run when ij contains bomb, breaks loop in main and shows result
	// TODO show bomb locations that haven't been located?
	static void gameOver(boolean [][] b, String[][] board, boolean[][] uncovered, int[][] bCount) {
		gameOver=true;
		setBoardState(b, board, uncovered, bCount);
		displayBoard(board);
		System.out.println("Game Over");
	}
	// finds out if player has uncovered all cells without bombs
	static boolean checkGameWon(boolean[][] b, boolean [][]uncovered) {
			cellsToCheck = (M*N) - numBombs;
			if (cellsUncovered==cellsToCheck) {
					return true;
				}
			else {			
				return false;
			}
	}
	public static void main (String[] args) {
	//take input from keyboard
	// not letting user change these yet

		//System.out.println("Value for M:");
		M = 5;
				//kb.nextInt();
		//System.out.println("Value for N:");
		N = 4;
				//kb.nextInt();
		//System.out.println("Value for p:");
		p = 0.70; //lower means more bombs
				//kb.nextDouble();


		boolean[][] bombPositions = new boolean[M+2][N+2]; // contains bomb positions
		boolean[][] uncovered = new boolean[M+2][N+2]; // has player checked the cell
		setBombPositions(bombPositions); 

		int bCount[][] = new int[M+2][N+2]; 	// array for counting adjacent bombs
		countAdjacent(bombPositions, bCount);

		// prints for testing
		//MatrixPrinter.printbMatrix(bombPositions);
		//MatrixPrinter.displayCount(bCount);

		String [][] board = new String [M+2][N+2];
		setBoardBorder(board);
		setBoardState(bombPositions, board, uncovered, bCount);


		do {
			attempt(bombPositions, uncovered, board);
			setBoardState(bombPositions, board, uncovered, bCount);

		}
		while (!gameOver && !checkGameWon(bombPositions, uncovered));

		if (checkGameWon(bombPositions, uncovered)) {
			setBoardState(bombPositions, board,uncovered, bCount);
			displayBoard(board);
			System.out.println("YOU WIN!");
		}

		if (gameOver) {
			gameOver(bombPositions, board, uncovered, bCount);
		}
	}
}