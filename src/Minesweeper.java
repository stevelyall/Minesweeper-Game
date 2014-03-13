/* Minesweeper.java
 *git?
 *
 * Steven Lyall 
 *
 * To do:
 * - instructions when app starts?
 * - ease of use?
 * - static huh? clean up method params? possible to move arrays being passed to class scope?
 * - OOP-ness? move to other classes?
 * - make board displayed bigger? easier to see?
 * - combine row/column input into one line?
 * -package into executable? browser?
 * 
 */

import java.util.*;

public class Minesweeper {
	static int M, N;
	static double p;
	static Scanner kb = new Scanner(System.in);
	static boolean gameOver = false;
	static boolean gameWon = false;
	static int cellsToCheck = 0;
	static int cellsUncovered = 0;
	static int numBombs = 0;

	
	static void setBombPositions(boolean[][] matrix) {
		Random rand = new Random();
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N;j++) {
				double r = rand.nextDouble();
				if (p<r) {
					matrix[i][j] = true;
					numBombs++;
				}
	
			}
		}
	}
	static void countAdjacent(boolean[][] a, int[][] b) {
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N; j++) {
				if (a[i][j] == true) {
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
	static void setCountBorder(int[][]a) {
		for (int i =0;i<M+2;i++) {
			for (int j=0;j<N+2; j++) {
				if (i == 0) {
					a[i][j] = 0;
				}
				if (i==a.length-1) {
					a[i][j] = 0;
				}
				if (j==0) {
					a[i][j] = 0;
				}
				if (j==a[i].length-1) {
					a[i][j] = 0;
				}
				if ((i == 0 && j == 0) || (i == a.length-1 && j==a[i].length-1) || (i==0 && j==a[i].length-1) || (i==a.length-1 && j==0)) {
					a[i][j] = 0;
				}
			}
		}
	}
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
	static void setBoardState(boolean[][] a, String[][] b, boolean[][] uncovered, int[][] c) {
		for (int i =1;i<a.length-1;i++) {
			for (int j=1;j<a[0].length-1; j++) {
				if (uncovered[i][j]) {
					if (a[i][j] == true) {
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
	static void displayCount(int[][] matrix) {
		for (int i =0;i<matrix.length;i++) {
			for (int j=0;j<matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	static void displayBoard(String[][] matrix) {
		for (int i =0;i<matrix.length;i++) {
			for (int j=0;j<matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	static void attempt(boolean[][] b, boolean[][] uncovered, String[][] board) {
		displayBoard(board);
		System.out.println("Enter 0 to mark a cell.");
		System.out.println("Enter row to try:");
		int i = kb.nextInt();
		if (i==0) {
			mark(board);
			attempt(b, uncovered, board);
		}
		System.out.println("Enter column to try:");
		int j = kb.nextInt();
		if (j==0) {
			mark(board);
			attempt(b, uncovered, board);
		}
		System.out.println("Trying " + i + ", " + j);
		if (uncovered[i][j] == true) {
			System.out.println("You have already tried that space. \n wtf");
		}
		if (!uncovered[i][j]) {
			if (b[i][j]) {
				uncovered[i][j] = true;
				gameOver=true;
				
			}
			else {
				uncovered[i][j] = true;
				cellsUncovered++;
				System.out.println("Still no bombs...");
			}
		}
		if (i==0) {
			mark(board);
		}
		// recursive possible?
		//if (!gameOver) { 
			//attempt(b, uncovered, board);
	}	
	static void printbMatrix(boolean[][] matrix) {
		for (int i =0;i<matrix.length;i++) {
			for (int j=0;j<matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	static void gameOver(boolean [][] b, String[][] board, boolean[][] uncovered, int[][] bCount) {
		gameOver=true;
		setBoardState(b, board, uncovered, bCount);
		displayBoard(board);
		System.out.println("Game Over");
	}
	static void mark(String[][] board) {
		System.out.println("Enter row to mark:");
		int i = kb.nextInt();
		System.out.println("Enter column to mark:");
		int j = kb.nextInt();
		board[i][j] = "! ";
		
	}
	static boolean checkGameWon(boolean[][] b, boolean [][]uncovered) {
		//for (int i =0;i<b.length-1;i++) {
			//for (int j=0; i<b.length-1;j++) {
			cellsToCheck = (M*N) - numBombs;
			if (cellsUncovered==cellsToCheck) {
					return true;
				}
			//}
		//}
					else			
		return false;
	}
	public static void main (String[] args) {
	//take input from keyboard

		//System.out.println("Value for M:");
		M = 5;
				//kb.nextInt();
		//System.out.println("Value for N:");
		N = 4;
				//kb.nextInt();
		//System.out.println("Value for p:");
		p = 0.66; //lower means more bombs
				//kb.nextDouble();

		
		boolean[][] b = new boolean[M+2][N+2];
		boolean[][] uncovered = new boolean[M+2][N+2];
		setBombPositions(b);

		// new array same size for count
		int bCount[][] = new int[M+2][N+2];
		countAdjacent(b, bCount);
		
		// printbMatrix(b); //prints boolean matrix
		setCountBorder(bCount);
		//displayCount(bCount); // prints count matrix for testing
		
		String [][] board = new String [M+2][N+2];
		setBoardBorder(board);
		setBoardState(b, board, uncovered, bCount);
	
		
		do {
		 //displayBoard(board); not needed, first line of attempt method?
		 // printbMatrix(b); // print answers to cheat while testing
			attempt(b, uncovered, board);
			setBoardState(b, board, uncovered, bCount);
	
		}
		while (!gameOver && !checkGameWon(b, uncovered));
		
		if (checkGameWon(b, uncovered)) {
			setBoardState(b, board,uncovered, bCount);
			displayBoard(board);
			System.out.println("YOU WIN!");
		}
		
		if (gameOver) {
			gameOver(b, board, uncovered, bCount);
		}
	}
}
