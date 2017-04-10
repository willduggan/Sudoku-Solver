package personalProjects;

import java.util.ArrayList;
import java.util.Random;

public class Board {
	private Square[][] board = new Square[3][3];
	private int[][] buildValues = new int[9][9];
	private int[][] tempArray = new int[9][9];
	private PossibilitySquare[][] boardPossibilities = new PossibilitySquare[3][3];
	
	// ========== Constructors ==========
	public Board() {
		// builds new random board
		/*
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Square();
			}
		}
		*/
		Random r = new Random();
		int numOfGivens = r.nextInt(4) + 30;
		for (int i = 0; i < numOfGivens; i++) {
			int row = r.nextInt(9) + 1;
			int col = r.nextInt(9) + 1;
			int value = r.nextInt(9) + 1;
			
			for(int j = 0; j < buildValues.length; j++) {
				if(buildValues[j][col] != value) {
					for(int k = 0; k < buildValues[row].length; k++) {
					
						if(buildValues[row][k] != value && testSquare(row, col, value) == false) {
							buildValues[row][col] = value;
						}
					}
				}
			}
		}
		buildNew(buildValues);
	}
	public Board(int[][] locations) {
		// builds new board with given values (provided by the user)
		buildNew(locations);
	}
	public void buildNew(int[][] locations) {
		int[][] square1 = {
				{locations[0][0], locations[0][1], locations[0][2]},
				{locations[1][0], locations[1][1], locations[1][2]},
				{locations[2][0], locations[2][1], locations[2][2]}};
		int[][] square2 = {
				{locations[0][3], locations[0][4], locations[0][5]},
				{locations[1][3], locations[1][4], locations[1][5]},
				{locations[2][3], locations[2][4], locations[2][5]}};
		int[][] square3 = {
				{locations[0][6], locations[0][7], locations[0][8]},
				{locations[1][6], locations[1][7], locations[1][8]},
				{locations[2][6], locations[2][7], locations[2][8]}};
		int[][] square4 = {
				{locations[3][0], locations[3][1], locations[3][2]},
				{locations[4][0], locations[4][1], locations[4][2]},
				{locations[5][0], locations[5][1], locations[5][2]}};
		int[][] square5 = {
				{locations[3][3], locations[3][4], locations[3][5]},
				{locations[4][3], locations[4][4], locations[4][5]},
				{locations[5][3], locations[5][4], locations[5][5]}};
		int[][] square6 = {
				{locations[3][6], locations[3][7], locations[3][8]},
				{locations[4][6], locations[4][7], locations[4][8]},
				{locations[5][6], locations[5][7], locations[5][8]}};
		int[][] square7 = {
				{locations[6][0], locations[6][1], locations[6][2]},
				{locations[7][0], locations[7][1], locations[7][2]},
				{locations[8][0], locations[8][1], locations[8][2]}};
		int[][] square8 = {
				{locations[6][3], locations[6][4], locations[6][5]},
				{locations[7][3], locations[7][4], locations[7][5]},
				{locations[8][3], locations[8][4], locations[8][5]}};
		int[][] square9 = {
				{locations[6][6], locations[6][7], locations[6][8]},
				{locations[7][6], locations[7][7], locations[7][8]},
				{locations[8][6], locations[8][7], locations[8][8]}};
		/*
		for(int i = 0; i < locations.length; i++) {
			for(int j = 0; j < locations[i].length; j++) {
				if(locations[i][j] > 0) {
					
				}
			}
		}
		*/
		
		// Constructs the squares
		board[0][0] = new Square(square1);
		board[0][1] = new Square(square2);
		board[0][2] = new Square(square3);
		board[1][0] = new Square(square4);
		board[1][1] = new Square(square5);
		board[1][2] = new Square(square6);
		board[2][0] = new Square(square7);
		board[2][1] = new Square(square8);
		board[2][2] = new Square(square9);
	}
	
	// ========== Solving Methods ==========
	public void solve() {
		// runs all methods necessary to solve the board
		tempArray = toTempArray();
		for(int i = 0; i < tempArray.length; i++) {
			for(int j = 0; j < tempArray[i].length; j++) {
				// gets the possibilities remaining after running plan A with the row
				// "i" and the column "j" 
				int possibilities = SolvingMethodA(i, j);
				String possibilitiesString = String.valueOf(possibilities);
				if(possibilitiesString.length() == 1) {
					// We know that the tile has only one possibility. This places
					// the number in "possibilities" into the tile that is being 
					// viewed.
					placeInBoard(i, j, possibilities);
				}
				// places the values within the possibilities integer (returned from Plan A)
				// into the boardPossibilities array so as to have them archived for later
				// plans.
				boardPossibilities[i / 3][j / 3].placeValue(i % 3, j % 3, possibilities);
			}
		} // End of for loop
		if(isComplete() == false) {
			// the Sudoku board is not finished
			SolvingMethodB();
			if(isComplete() == false) {
				// the Sudoku board is not finished
				SolvingMethodC();
				// the Sudoku board MUST be finished after running Plan C and thus
				// there is no need to check further
			}
		}
	}
	private int SolvingMethodA(int row, int col) {
		// Takes in a position on the board and will test all possibilities for that
		// position. It will then eliminate any possibilities that don't work and will
		// return the String "possibilities" which now only contains possibilites
		// that work.
		String possibilities = "123456789";
		for(int i = 0; i < tempArray[row].length;i++) {
			if (tempArray[row][i] > 0) {
				String given = String.valueOf(tempArray[row][i]);
				int location = possibilities.indexOf(given);
				possibilities = possibilities.replace(given, "");
			}
		}
		for(int i = 0; i < tempArray.length; i++) {
			if (tempArray[i][col] > 0) {
				String given = String.valueOf(tempArray[i][col]);
				if (possibilities.contains(given)) {
					int location = possibilities.indexOf(given);
					possibilities = possibilities.replace(given, "");
				}
			}
		}
		// converts the row and column to the corresponding square in order to traverse
		// the square to test possibilites
		Square square = locationToSquare(row, col);
		String squareContains = String.valueOf(square.valuesContained());
		for(int i = 0; i < squareContains.length(); i++) {
			// if "possibilities" contains any of the numbers in "squareContains"
			// they will be removed from "possibilities" in order to signify that
			// they are not possibilities
			if (possibilities.contains(("" + squareContains.charAt(i))));
				possibilities = possibilities.replace("" + squareContains.charAt(i), "");
		}
		return Integer.parseInt(possibilities);
	}
	private void SolvingMethodB() {
		/*
		 * Part A: see if any number only is possible in one tile in a square
		 * Part B: remove any numbers from the possibilities if they are not allowed 
		 * 			by possibilities in other squares.
		 */
		
		// Part A
		for(int i = 0; i < boardPossibilities.length; i++) {
			for(int j = 0; j < boardPossibilities[i].length; j++) {
				for(int k = 1; k <= 9; k++) {
					if (board[i][j].contains(k) == false) {
						// will extract the square at boardPossibilities[i][j] and test
						// to see if the value "k" only occurs once
						if(boardPossibilities[i][j].numOfTimesOccurs(k) == 1) {
							// we know that the number only occurs in the possibilities once
							// for that square
							
							int location = boardPossibilities[i][j].positionOf(k);
							
							// places the value "k" in the location within that space on 
							// the board
							board[i][j].placeValue(location / 10, location % 10, k);
							
							// places the value "k" in the location within that space on
							// the possibilities board, thus making it clear that all other
							// possibilities are no longer possible for that square.
							boardPossibilities[i][j].placeValue(location / 10, location % 10, k);
						}
					}
				}
			}
		}
		
		// Part B
		for(int i = 0; i < boardPossibilities.length; i++) {
			for(int j = 0; j < boardPossibilities[i].length; j++) {
				// traverses the squares on the board
				for(int k = 1; k <= 9; k++) {
					if (boardPossibilities[i][j].allPositionsInSameCol(k) == true) {
						// We know that all possibilities of integer "k" are in the 
						// same row. We have to remove these possibilities from this 
						// from all conflicting squares (those above and below).
						int col = boardPossibilities[i][j].getColNumberForSamePositions(k);
						for(int l = (i - 1); l >= 0; l--) {
							boardPossibilities[l][j].removeValueFromCol(col, k);
						}
						for(int l = (i + 1); i < boardPossibilities.length; l++) {
							boardPossibilities[l][j].removeValueFromCol(col, k);
						}
					}
					if (boardPossibilities[i][j].allPositionsInSameRow(k) == true) {
						// We know that all possibilities of integer "k" are in the 
						// same column. We have to remove these possibilities from this 
						// from all conflicting squares (those left and right).
						int row = boardPossibilities[i][j].getRowNumberForSamePositions(k);
						for(int l = (j - 1); l >= 0; l--) {
							boardPossibilities[i][l].removeValueFromRow(row, k);
						}
						for(int l = (j + 1); i < boardPossibilities.length; l++) {
							boardPossibilities[i][l].removeValueFromRow(row, k);
						}
					}
				}
			}
		}
	}
	private void SolvingMethodC() {
		// Going to make the best guess for a tile. It will start with the tile with 
		// the smallest number of possibilities. If multiple tiles exist with the same
		// number of possibilities, the lowest number of possibilities, it will use 
		// the tile that comes first (left to right, top to bottom).
		
		// have to find the smallest number of possibilities that a tile holds
		int smallestNumberOfPossibilitiesInTile = 9;
		for(int i = 0; i < boardPossibilities.length; i++) {
			for(int j = 0; j < boardPossibilities[i].length; j++) {
				if (boardPossibilities[i][j].smallestNumberOfPossibilities() 
						< smallestNumberOfPossibilitiesInTile) {
					smallestNumberOfPossibilitiesInTile = boardPossibilities[i][j].smallestNumberOfPossibilities();
				}
			}
		}
		boolean placedValue = false;
		int element = 0;
		do {
			int[][] tempArray = toPossibilitiesTempArray();
			String current = "";
			ArrayList<Integer> locations = new ArrayList<>();
			for(int i = 0; i < tempArray.length; i++) {
				for(int j = 0; j < tempArray[i].length; j++) {
					current = String.valueOf(tempArray[i][j]);
					if(current.length() == smallestNumberOfPossibilitiesInTile) {
						locations.add((i * 10) + j);
					}
				}
			}
			int[] testNumbers = new int[current.length()];
			if(current.length() > 0) {
				for(int i = 0; i < testNumbers.length; i++) {
					testNumbers[i] = Integer.parseInt("" + current.charAt(i));
				}
			}
			
			int row = locations.get(element) / 10;
			int col = locations.get(element) % 10;
			
			// We now have the location that we will be using to place the guess.
			/*
			for(int i = 0; i < testNumbers.length; i++) {
			 
				if (boardPossibilities[row/3][col/3].testValue(row % 3, col % 3, testNumbers[i]) == true) {
					boardPossibilities[row/3][col/3].placeValue(row % 3, col % 3, testNumbers[i]);
					placedValue = true;
				}	
			}
			*/
			element++;
			if (element > locations.size()) {
				break;
				// puzzle is not solveable
			}
		} while (placedValue == false);
		
	}

	// ========== Accessors ==========
	private boolean testSquare(int row, int col, int value) {
		boolean contains = false;
		for(int i = (row-(row%3)); i <= (row-(row%3))+3; i++) {
			for(int j = (col-(col%3)); j <= (col-(col%3))+3; j++) {
				if(buildValues[i][j] == value) {
					contains = true;
					break;
				}
			}
		}
		return contains;
	}
	private int[][] toTempArray() {
		int[][] convert = new int[9][9];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				int[][] current = board[i][j].getArray();
				for(int k = 0; k < current.length; k++) {
					for(int l = 0; l < current[i].length; l++) {
						convert[(i * 3) + k][(j * 3) + l] = current[k][l];
					}
				}
			}
		}
		return convert;
	}
	private int[][] toPossibilitiesTempArray() {
		int[][] convert = new int[9][9];
		for(int i = 0; i < boardPossibilities.length; i++) {
			for(int j = 0; j < boardPossibilities[i].length; j++) {
				int[][] current = board[i][j].getArray();
				for(int k = 0; k < current.length; k++) {
					for(int l = 0; l < current[i].length; l++) {
						convert[(i * 3) + k][(j * 3) + l] = current[k][l];
					}
				}
			}
		}
		return convert;
	}
	public Square locationToSquare(int row, int col) {
		int squareRowIndex = row/3;
		int squareColIndex = col/3;
		return board[squareRowIndex][squareColIndex];		
	}
	public boolean isComplete() {
		boolean completed = true;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j].isCompleted == false) {
					completed = false;
					break;
				}
			}
		}
		return completed;
	}
	public int getCompletedCount() {
		int completedCount = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				completedCount += board[i][j].getCompletedCount();
			}
		}
		return completedCount;
	}
	public String toString() {
		tempArray = toTempArray();
		String output = "__________________";
		for(int i = 0; i < tempArray.length; i++) {
			output += "|";
			for(int j = 0; j < tempArray[i].length; j++) {
				output += "" + tempArray[i][j] + "|";
			}
		}
		output += "__________________";
		return output;
	}
	
	// ========== Modifiers ==========
	private void placeInBoard(int row, int col, int value) {
		int squareRowIndex = row/3;
		int squareColIndex = col/3;
		board[squareRowIndex][squareColIndex].placeValue(row % 3, col % 3, value);
	}
}
