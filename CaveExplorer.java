import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaveExplorer {
	// Step 1: Zero parameter constructor to create the specified cave
	private char [][] cave;

	private String path = ""; // This will hold the path taken to reach the end position

	// The cave layout is represented as a 2D array of characters, where:
	// 'R' represents a rock (an impassable cell)
	// 'S' represents the start position
	// 'E' represents the end position
	public CaveExplorer() {
		cave = new char [][] {
			"RRRRRR".toCharArray(),
			"R..SRR".toCharArray(),
			"R.RRRR".toCharArray(),
			"R.MRRR".toCharArray(),
			"RRRRRR".toCharArray()
		};
	}
	// Step 2: toString method
	// This method should return a string representation of the cave layout.
	@Override 
	public String toString() {
		StringBuilder layout = new StringBuilder();
		// Loop through each row of the cave and append it to the layout string
		for (char[] row : cave) {
			layout.append(row);
			layout.append("\n");
		}
		// Remove the last newline character for cleaner output
		return layout.toString();
	}
	// Step 3: solve method
	public boolean canMoveto(int row, int col) {
		if (row < 0 || row >= cave.length) {
			return false; // Out of bounds
		}
		if (col < 0 || col >= cave[row].length) {
			return false; // Out of bounds
		}

		char square = cave[row][col];
		return square == '.' || square == 'M';
	}
	// This method should return true if there is a path from the start to the end,
	// and false otherwise.
	private boolean walkFrom(int row, int col) {

		StringBuilder moves = new StringBuilder();

		while (cave[row][col] != 'M') {
			int nextRow = row;
			int nextCol = col;

			if(canMoveto(row - 1, col)) { // North
				nextRow--; 
				moves.append('n'); // Append 'n' for north move
			} else if(canMoveto(row + 1, col)) { // South
				nextRow++;
				moves.append('s');// Append 's' for south move
			} else if(canMoveto(row, col -1)){ // West
				nextCol--;
				moves.append('w');// Append 'w' for west move
			} else if(canMoveto(row, col + 1)){ // East
				nextCol++;
				moves.append('e');// Append 'e' for east move
			} else {
				return false; // No valid moves available
			}
			
			cave[row][col] = '*'; // Mark the path
			row = nextRow;
			col = nextCol;
		}
		path = moves.toString(); // Store the path taken
		return true; // Reached the end
	}

	// This method should return true if there is a path from the start to the end, 
	// and false otherwise.
	// it will just print out the possible moves from the start position.
	public boolean solve() {
		for (int row = 0; row < cave.length; row++) {
			for (int col = 0; col < cave[row].length; col++) {
				if (cave[row][col] == 'S') {
					//System.out.println("Found S at row " + row + ", col" + col);
					//System.out.println("North: " + canMoveto(row - 1, col));
					//System.out.println("South: " + canMoveto(row + 1, col));
					//System.out.println("West: " + canMoveto(row, col - 1));
					//System.out.println("East: " + canMoveto(row, col + 1));
					return walkFrom(row, col); // Start walking from the start position
				}
			}
		}
		return false;
	}
	
	// Step 4: getPath method
	public String getPath(){
		return path;
	}
	
	// Step 5: One parameter constructor to read from a file
	
	public CaveExplorer (String fileName) throws FileNotFoundException {
	    
		try (Scanner input = new Scanner(new File(fileName))){
			int rows = input.nextInt();
			int cols = input.nextInt();

			cave = new char[rows][cols];
			
			for (int row = 0; row < rows; row++) {
				String line = input.next();

				if (line.length() != cols) {
					throw new IllegalArgumentException(
						"Cave row " + row + "must contain " + cols + "characters.");
				}
				
				line.getChars(0, cols, cave [rows], 0);
			}  	
    	} // Construct cave and populate with rest of data in the file
	}	

	

	
	public static void main(String[] args) {
		System.out.println("Starting CaveExplorer");
		
		// Create a CaveExplorer object and print the starting layout
		CaveExplorer explorer = new CaveExplorer();
		String startingLayout = explorer.toString();
		System.out.println(startingLayout);

		String expected = "RRRRRR\nR..SRR\nR.RRRR\nR.MRRR\nRRRRRR\n";
		System.out.println("Layout matches expected: " + expected.equals(startingLayout));

		// Call solve
		//explorer.solve();
		boolean foundPool = explorer.solve();

		// Print the final layout, whether there is a path, and if so, what it is.
		System.out.println("Final layout: ");
		System.out.println(explorer);
		System.out.println("Reached mirror pool: " + foundPool);

		System.out.println("Path taken; " + explorer.getPath());
		(System.out).println("Path matches expected: " + "wwsse".equals(explorer.getPath()));


		
		// Step 5/6: Repeat for a different CaveExplorer object read from a file
		// Uncomment code below to start testing your 1-parameter constructor
		String fileName = "testdat.txt"; 
		try { 
			CaveExplorer filExplorer = new CaveExplorer(fileName); 
			System.out.println("Starting layout from " + fileName + ":");
			System.out.println(filExplorer);

		} 
		catch (FileNotFoundException e ) {
			System.out.println("Can't find file " + fileName); 
		}
		catch (Exception e) {
			System.out.println("Other error: " + e.getMessage());
		}
		
		
		System.out.println("Finished CaveExplorer");
	}
}
