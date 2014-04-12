import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NQueens {
	 /* 
	 * Solves a N-Queens problem and displays a random solution
	 * This program is based on a Min-Conflicts algorithm, to which we add a random walk strategy to avoid
	 * being stuck in local optima
	 */
	
	static List<Integer> qPositions = new ArrayList<Integer>(8);
	static int qConflicts = 0;
	static int iter = 0;
	static double probability = 0.04;
	
	/*
	 * Reads size argument from input, then calls method to solve problem
	 */
    public static void main(String[] args) {

    	// Allows user to pass in size of board, otherwise set it to 0
        int n = (args.length > 0) ? Integer.parseInt(args[0]) : 0;
        
        // Loops until user inputs a valid n-size (n >= 4)
        while (n < 4) {
            try {
                System.out.println("Veuillez entrer une valeur de n supérieure ou égale à 4 :");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                n = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
            	n = -1;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        solve(n);
    }
    
    public static void solve(int n) {
    	placeInitialQueens(n);
    	minimizeConflicts();
    	displaySolution(n);
    }

	/*
     * Assigns initial qPositions tuple a random value
     */
    public static void placeInitialQueens(int n) {
    	Random randgen = new Random();
    	int col = 0; int row = 0;
    	// Places queens randomly such that each queen is in a different row
    	while (col < n) {
    		row = randgen.nextInt(n);
    		if(!qPositions.contains(row)) {
    			qPositions.add(col, row);
    			col++;
    		}
    	}
    	
		// Prints initial candidate tuple
		System.out.print("N-uplet initial : ");
		for(int i : qPositions)
			System.out.print((i+1) + " ");
		System.out.println("");
    }
	
    /*
     * Selects a column and move its queen so as to minimize conflicts
     * Repeats until a solution is found
     */
    private static void minimizeConflicts() {
    	Random randgen = new Random();
    	
    	while(getTotalConflicts(qPositions) > 0) {
    		// Selects a column
    		int col = randgen.nextInt(qPositions.size());
    		qConflicts = getTotalConflicts(qPositions);
    		int queenRow = qPositions.get(col);
    		
    		// Applies random move with a probability p
    		if(randgen.nextDouble() <= probability)
    			queenRow = randgen.nextInt(qPositions.size());
    		// Else, applies regular MC algorithm 
    		else {
	    		// Checks all potential moves for the queen across the column
	    		for(int row = 0; row < qPositions.size(); row++) {
	    			if(queenRow == row)
	    				continue;
	    			else {
	    				// Create new tuple to store new queen position
	    				List<Integer> tuple = new ArrayList<Integer>();
	    				for(int i : qPositions)
	    					tuple.add(i);
	    				tuple.set(col, row);
	    				// If new tuple has lower number of conflicts, store row number
	    				if (getTotalConflicts(tuple) <= qConflicts) {
	    					qConflicts = getTotalConflicts(tuple);
	        				queenRow = row;
	    				}
	    			}
	    		}
    		}
	        // Moves queen to new row if new value
    		if(qPositions.get(col) != queenRow)
		    	qPositions.set(col, queenRow);
	    	// Increments number of iterations
	    	iter++;
    	}
	}
    
	/*
	 * Returns number of conflicts (queens attacking) for a given queen
	 */
	public static int getConflicts(List<Integer> tuple, int col) {
		int conflicts = 0;
		int row = tuple.get(col);
		
		// Loops through columns
		for(int i = 0; i < tuple.size(); i++ ) {
			if(col == i)
				continue;
			else if(row == tuple.get(i)) // is in same row
				conflicts++;
			else if(Math.abs(col - i) == Math.abs(row - tuple.get(i)) // is in same 'rising' diagonal
				 || Math.abs(col - i) == Math.abs(tuple.get(i) - row)) // is in same 'falling' diagonal
				conflicts++;
		}
		return conflicts;
	}
	
	/*
	 * Returns total number of conflicts on board given a tuple of queens positions
	 */
	public static int getTotalConflicts(List<Integer> tuple) {
		int conflicts = 0;
		
		for(int i = 0; i < tuple.size(); i++) {
			conflicts += getConflicts(tuple, i);
		}
		
		return conflicts;
	}
	
	/*
	 * Displays a solution with a board output
	 */
	public static void displaySolution(int n) {
		System.out.println("Solution trouvée !");
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < n-1; col++) {
				if(qPositions.get(col) == row)
					System.out.print("|Q");
				else
					System.out.print("|_");
			}
			if(qPositions.get(n-1) == row)
				System.out.print("|Q");
			else
				System.out.print("|_");
			System.out.print("|\n");
		}
		
		System.out.print("N-uplet final : ");
		for(int i : qPositions)
			System.out.print((i+1) + " ");
    	System.out.println("\nItérations nécessaires : " + iter);
	}
}
