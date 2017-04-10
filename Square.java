package personalProjects;

import java.util.Random;
public class Square {
	private int[][] square = new int[3][3];
	int completedCount = 0;
	boolean isCompleted = false;
	
	// ========== Constructors ==========
	public Square() {
		// generates an empty square (already built)
	}
	public Square(int[][] locations) {
		// generates a square with the given locations
		square = locations;
	}
	
	// ========== Accessors ==========
	public int[][] getArray() {
		return square;
	}
	public int getCompletedCount() {
		// updates completedCount
		int count = 0;
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				if (square[i][j] > 0) {
					count++;
				}
			}
		}
		completedCount = count;
		return completedCount;
	}
	public boolean getIsCompleted() {
		// checks to see if completed
		if (getCompletedCount() == (square.length * square[0].length)){
			isCompleted = true;
		}
		return isCompleted;
	}
	public int valuesContained() {
		int numbersContained = 0;
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				if (square[i][j] > 0) {
					numbersContained *= 10;
					numbersContained += square[i][j];
				}
			}
		}
		return numbersContained;
	}
	public int numOfTimesOccurs(int value) {
		int count = 0;
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				if(square[i][j] == value)
					count++;
			}
		}
		return count;
	}
	public int positionOf(int value) {
		int location = 0;
		for(int i = 0; i < square.length; i++) {
			for(int j = 0; j < square[i].length; j++) {
				if(square[i][j] == value) {
					location = (i * 10) + j;
					break;
				}
			}
		}
		return location;
	}
	public boolean contains(int value) {
		boolean containsValue = false;
		String values = String.valueOf(valuesContained());
		for(int i = 0; i < getCompletedCount(); i++) {
			if (values.charAt(i) == value) {
				containsValue = true;
			}
		}
		return containsValue;
	}
	
	// ========== Modifiers ==========
	public void placeValue(int row, int col, int value) {
		square[row][col] = value;
	}
	public void removeValue(int row, int col, int value) {
		String values = String.valueOf(square[row][col]);
		int index = values.indexOf(value);
		values = values.substring(0, index) + values.substring(index + 1);
		square[row][col] = Integer.parseInt(values);
	}
}
