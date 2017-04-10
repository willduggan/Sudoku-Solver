package personalProjects;

public class PossibilitySquare extends Square {
	
	// ========== Constructors ==========
	public PossibilitySquare() {
		super();
	}
	public PossibilitySquare(int[][] values) {
		super(values);
	}
	
	// ========== Accessors ==========
	public int numOfTimeOccurs(int value) {
		int count = 0;
		int[][] square = super.getArray();
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				String possibilities = String.valueOf(square[i][j]);
				for(int k = 0; k < possibilities.length(); k++) {
					if (Integer.parseInt("" + possibilities.charAt(k)) == value)
						count++;
				}
			}
		}
		return count;
	}
	public int positionOf(int value) {
		// assumes that the value is only possible in one tile in the square
		// returns the location in the format ("row""col")
		//		example: row = 1, col = 2, location will equal "12"
		int location = 0;
		if (numOfTimeOccurs(value) != 1) {
			location = -1;
		}
		else {
			int[][] square = super.getArray();
			// traverses the square's elements
			for(int i = 0; i < square.length; i++) {
				for(int j = 0; j < square[i].length; j++) {
					// gets the element's possibility values
					String possibilities = String.valueOf(square[i][j]);
					for(int k = 0; k < possibilities.length(); k++) {
						// traverses the string containing the possibilities
						if (Integer.parseInt("" + possibilities.charAt(k)) == value) {
							location = (i * 10) + j;
						}
					}
				}
			}
		}
		return location;
	}
	private String[] positionsOf(int value) {
		int[][] square = super.getArray();
		int count = 0;
		// traverses the square's elements
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				// gets the element's possibility values
				String possibilities = String.valueOf(square[i][j]);
				for(int k = 0; k < possibilities.length(); k++) {
					// traverses the string containing the possibilities
					if (Integer.parseInt("" + possibilities.charAt(k)) == value) {
						count++;
					}
				}
			}
		}
		// knows how big to build the array of locations of the value
		String[] locations = new String[count];
		int location = 0;
		// traverses the square's elements
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				// gets the element's possibility values
				String possibilities = String.valueOf(square[i][j]);
				for(int k = 0; k < possibilities.length(); k++) {
					// traverses the string containing the possibilities
					if (Integer.parseInt("" + possibilities.charAt(k)) == value) {
						// places the location in the next open element in the array
						// of locations
						locations[location] = "" + i + "," + j;
						location++;
					}
				}
			}
		}
		return locations;
	}
	public boolean allPositionsInSameRow(int value) {
		String[] positionsOfValue = positionsOf(value);
		boolean areSame = true;
		int row = Integer.parseInt("" + positionsOfValue[0].charAt(0));
		for(int i = 0; i < positionsOfValue.length; i++) {
			if (Integer.parseInt("" + positionsOfValue[i].charAt(0)) != row) {
				areSame = false;
				break;
			}
		}
		return areSame;
	}
	public boolean allPositionsInSameCol(int value) {
		String[] positionsOfValue = positionsOf(value);
		boolean areSame = true;
		int col = Integer.parseInt("" + positionsOfValue[0].charAt(2));
		for(int i = 0; i < positionsOfValue.length; i++) {
			if (Integer.parseInt("" + positionsOfValue[i].charAt(2)) != col) {
				areSame = false;
				break;
			}
		}
		return areSame;
	}
	public int getColNumberForSamePositions(int value) {
		String[] positionsOfValue = positionsOf(value);
		return Integer.parseInt("" + positionsOfValue[0].charAt(2));
	}
	public int getRowNumberForSamePositions(int value) {
		String[] positionsOfValue = positionsOf(value);
		return Integer.parseInt("" + positionsOfValue[0].charAt(0));
	}
	public int smallestNumberOfPossibilities() {
		int smallest = 9;
		for(int i =  0; i < super.getArray().length; i++) {
			for(int j = 0; j < super.getArray()[i].length; j++) {
				String current = String.valueOf(super.getArray()[i][j]);
				if (current.length() < smallest) {
					smallest = current.length();
				}
			}
		}
		return smallest;
	}
	
	// ========== Modifiers ==========
	public void removeValueFromCol(int col, int value) {
		for(int i = 0; i < super.getArray().length; i++) {
			// traverses the rows
			super.removeValue(i, col, value);
		}
	}
	public void removeValueFromRow(int row, int value) {
		for(int i = 0; i < super.getArray()[0].length; i++) {
			// traverses the number of columns
			super.removeValue(row, i, value);
		}
	}
}
