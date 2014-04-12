import java.io.*;

public class NQueens {

	// Calculate and display solutions for a N-Queens problems
    public static void main(String[] args) {
    	// Allow user to pass in size of board, otherwise set it to 0
        int n = (args.length > 0) ? Integer.parseInt(args[0]) : 0;
        
        // Loop until user inputs a valid n-size (n >= 4)
        while (n < 4) {
            try {
                System.out.println("Veuillez entrer une valeur de n supérieure ou égale à 4 :");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                n = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
            	n = 0;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        // Start computing
        System.out.println("Calcul des solutions pour un problème de " + n + "-Reines ...");
        
    }

}
