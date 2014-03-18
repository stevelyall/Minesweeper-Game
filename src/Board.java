import java.util.Random;

public class Board {
	int M;
	int N;
	double P;
	int numBombs = 0;
	boolean[][] bombPositions;
	boolean[][] uncovered;
	boolean[][] cellMarked;
	String [][] board;
	int[][] adjCount;

	Board(int m, int n, double p) {
		M = m;
		N = n;
		P = p;
		bombPositions = new boolean[M+2][N+2]; // contains bomb positions
		uncovered = new boolean[M+2][N+2]; // has player checked the cell
		cellMarked = new boolean[M+2][N+2];
		board = new String [M+2][N+2];
		adjCount = new int[M+2][N+2]; 
		setBombPositions();
		countAdjacent();
		setBoardBorder();
		updateBoard();
	}

	// distributes T and F bomb locations in bomb matrix
	void setBombPositions() {

		Random rand = new Random();
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N;j++) {
				double r = rand.nextDouble();
				if (P<r) {
					bombPositions[i][j] = true;
					numBombs++;
				}
			}
		}
	}

	// counts bombs in adjacent cells and set up matrix to hold them
	void countAdjacent() {
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N; j++) {
				if (bombPositions[i][j] == true) {
					for (int r=i-1;r<=i+1;r++) {
						for (int c=j-1;c<=j+1;c++) {
							if (r==i && c==j) {
								continue; 
							}
							adjCount[r][c] += 1;	
						}
					}
				}
			}
		}
	}

	int getCellsToCheck() {
		return (M*N) - numBombs;

	}

	int getBoardWidth() {
		return M;
	}

	int getBoardHeight() {
		return N;
	}

	// gives game board a border with unused cells
	void setBoardBorder() {
		for (int i =0;i<M+2;i++) {
			for (int j=0;j<N+2; j++) {
				if (i == 0) {
					board[i][j] = "--";
				}
				if (i==board.length-1) {
					board[i][j] = "--";
				}
				if (j==0) {
					board[i][j] = "|";
				}
				if (j==board[i].length-1) {
					board[i][j] = "|";
				}
				if ((i == 0 && j == 0) || (i == board.length-1 && j==board[i].length-1) || (i==0 && j==board[i].length-1) || (i==board.length-1 && j==0)) {
					board[i][j] = " ";
				}
			}
		}
	}	

	// these two methods print hidden matrices for testing
	void showBombLocations() {
		for (int i =0;i<bombPositions.length;i++) {
			for (int j=0;j<bombPositions[0].length; j++) {
				System.out.print(bombPositions[i][j] + " ");
			}
			System.out.println();
		}
	}

	void displayCount() {
		for (int i =0;i<adjCount.length;i++) {
			for (int j=0;j<adjCount[0].length; j++) {
				System.out.print(adjCount[i][j] + " ");
			}
			System.out.println();
		}
	}

	boolean checkforBomb(int i, int j) {
		if (bombPositions[i][j]) { // bomb there
			return true;
		}
		else {
			return false;
		}
	}

	boolean hasBeenChecked(int i, int j) {
		return uncovered[i][j];
	}

	void toggleCellMarked(int i, int j) {
		if (cellMarked[i][j]) {
			cellMarked[i][j] = false;
		}
		else if (!cellMarked[i][j]) {
			cellMarked[i][j] = true;
		}
	}

	void setChecked(int i, int j) {
		uncovered[i][j] = true;
	}

	// updates  the game board matrix as game progresses
	void updateBoard() {
		for (int i =1;i<bombPositions.length-1;i++) {
			for (int j=1;j<bombPositions[0].length-1; j++) {
				if (uncovered[i][j]) {
					if (bombPositions[i][j] == true) {
						board[i][j] = "* ";
					}
					else
						board[i][j] = Integer.toString(adjCount[i][j]) + " ";
				}
				else {
					board[i][j] = "# ";	
					if (cellMarked[i][j]) {
						board[i][j] = "! ";
					}
				}
			}
		}
	}

	// prints the game board
	void displayBoard() {
		for (int i =0;i<board.length;i++) {
			for (int j=0;j<board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	void revealAll() {
		for (int i=1;i<=M;i++) {
			for (int j=1;j<=N;j++) {
				uncovered[i][j] = true;
			}
		}
	}
}

