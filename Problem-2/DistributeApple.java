import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
 * Time Complexity: O(2^n) where n is the number of apples.
 * Space Complexity: O(n) where n is the number of apples.
 */

/*
 * Resources Used:
 * https://stackoverflow.com/questions/12519335/resource-leak-in-is-never-closed
 */

public class DistributeApple {

    // List to store the combination of the weight got in current recursion call.
    static List<Integer> currentWeights = new ArrayList<>();

    // List to store the best combination of weights among all the weights.
    static List<Integer> bestTillNow = new ArrayList<>();

    // Used for the case where we cannot cut a apple into pieces have to allocate the whole apple to one of them. But currently, I have not considered this case.
    // static List<Integer> IndexAlreadyTaken = new ArrayList<>();

    public static void main(String[] args) {

        List<Integer> weights = new ArrayList<>();
        
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        //To resolve the error of Resource leak: 'scanner' is never closed
        try{
            System.out.print("Enter apple weight in gram(-1 to stop): ");
            input = scanner.nextInt();

            while(input != -1)
            {
                weights.add(input);
                System.out.print("Enter apple weight in gram(-1 to stop): ");
                input = scanner.nextInt();
            }
        }
        finally{
            scanner.close();
        }

        // System.out.println(weights.toString());//Debugging - Printing the entered weights.

        int size = weights.size();

        int total = getSum(weights);
        // System.out.println("Total " + total); //Debugging

        // double proportionateShare = (amountPaid * (double) totalWeight) / totalAmount;
        double proportionateShareRam = (50 * (double) total) / 100;
        int RamShare = (int)Math.round(proportionateShareRam);
        // System.out.println("RamShare " + RamShare); //Debugging

        // Used for the case where we cannot cut a apple into pieces have to allocate the whole apple to one of them
        //check if the weight present in the given arraylist.
        // boolean ramExists = weights.contains(RamShare);
        // if(!ramExists || IndexAlreadyTaken.contains(weights.indexOf(RamShare)))
        // {
        //     RamShare = getTheNearestWeight(weights, RamShare);
        // }
        
        double proportionateShareSham = (30 * (double) total) / 100;
        int ShamShare = (int)Math.round(proportionateShareSham);
        // System.out.println("ShamShare " + ShamShare); //Debugging


        // Used for the case where we cannot cut a apple into pieces have to allocate the whole apple to one of them
        //check if the weight present in the given arraylist.
        // boolean shamExists = weights.contains(ShamShare); 
        // if(!shamExists || IndexAlreadyTaken.contains(weights.indexOf(ShamShare)))
        // {
        //     ShamShare = getTheNearestWeight(weights, ShamShare);
        //     System.out.println("ShameShare : " + ShamShare); //Debugging
        // }
        
        double proportionateShareRahim = (20 * (double) total) / 100;
        int RahimShare = (int)Math.round(proportionateShareRahim);


        //Used for the case where we cannot cut a apple into pieces have to allocate the whole apple to one of them
        //check if the weight present in the given arraylist.
        // boolean RahimExists = weights.contains(RahimShare);
        // System.out.println("RahimShare " + RahimShare); //Debugging
        // if(!RahimExists || IndexAlreadyTaken.contains(weights.indexOf(RahimShare)))
        // {
        //     RahimShare = getTheNearestWeight(weights, RahimShare);
        // }
        // System.out.println(RamShare + " : " + ShamShare + " : " + RahimShare);// Debugging - Printing the ram, sham, and rahim share.

        System.out.println("Distribution Result : ");

        findCombinations(weights, RamShare, size);
        printResult(bestTillNow,"Ram");
        findAndRemove(weights,bestTillNow);

        //For new recursion, clear all the variables to stored in bestTillNow and currentWeights list.
        // bestTillNow.clear();
        currentWeights.clear();
        
        size = weights.size();
        findCombinations(weights, ShamShare, size);
        printResult(bestTillNow,"Sham");
        findAndRemove(weights,bestTillNow);
        
        // bestTillNow.clear();
        currentWeights.clear();

        size = weights.size();
        findCombinations(weights, RahimShare, size);
        printResult(bestTillNow,"Rahim");
        findAndRemove(weights,bestTillNow);

        // bestTillNow.clear();
        currentWeights.clear();
    }

    // Main recursive function to find all combinations of weights.
    public static void findCombinations(List<Integer> weights, int targetWeight, int n) {
        // Base Case 1:
        if (targetWeight == 0) {
            int currentWeightssize = currentWeights.size();
            int bestTillNowSize = bestTillNow.size();

            // Comparing the size of both the list. Storing the best among them.
            if (bestTillNowSize == 0 || currentWeightssize < bestTillNowSize) {
                storeWeight();
            }

            return;
        }

        //Base Case 2:
        if(n == 0)
        {
            return;
        }


        // Condition based on choice diagram.
        if (weights.get(n - 1) <= targetWeight) {

            // Add the current weight to the current combination.
            currentWeights.add(weights.get(n - 1));

            // Recursively call the function with reduced target weight.
            findCombinations(weights, targetWeight - weights.get(n - 1), n - 1);

            // Backtrack by removing the current weight from the current combination.
            currentWeights.remove(currentWeights.size() - 1);
            
            // Recursively call the function with current weight.
            findCombinations(weights, targetWeight, n - 1);
        }
        else if(weights.get(n - 1) > targetWeight)
        {
            // Recursively call the function with current weight.
            findCombinations(weights, targetWeight, n - 1);
            
        }
    }

    // Function to store the current combination of weights in bestTillNow list.
    public static void storeWeight() {
        // Remove previously stored combination in case any new combination is better with respect to the size.
        bestTillNow.clear();

        // Storing the current combination in bestTillNow list.
        for (int weight : currentWeights) {
            bestTillNow.add(weight);
        }

        // System.out.println("Storing Done"); //Debugging
    }

    //Function to calculate the sum of all weights.
    public static int getSum(List<Integer> weights){
        int sum = 0;
        for(int weight : weights){
            sum += weight;
        }
        // System.out.println("Sum Done"); //Debugging
        return sum;
    }

    //Function to remove the weights that are already included in the bestTillNow list.
    public static void findAndRemove(List<Integer> weights, List<Integer> bestTillNow){

        for(int i = 0; i < weights.size(); i++)
        {
            // System.out.println("bestTillNow: " + bestTillNow.toString()); //Debugging
            if(bestTillNow.contains(weights.get(i))){
                int idx = bestTillNow.indexOf(weights.get(i));
                // System.out.println("idx " + idx); //Debugging
                bestTillNow.remove(idx);

                weights.remove(i);
                i--; // To prevent skipping the next index.

            }
        }


        // System.out.println("Remove Done"); //Debugging
        // System.out.println("Weights: " + weights.toString());//Debugging
    }

    //Function to print the result.
    public static void printResult(List<Integer> bestTillNow,String name){
        System.out.println(name + ": " + bestTillNow.toString());
    }

    // Function to find the nearest weight from the given list. This function is implemeted for the case where we cannot cut a apple into pieces have to allocate the whole apple to one of them
    // public static int getTheNearestWeight(List<Integer> weights, int targetWeight){
    //     int closestWeight = 0;
    //     for (int weight : weights) {
    //         if ( (Math.abs(weight - targetWeight) > Math.abs(closestWeight - targetWeight) && (weight > closestWeight) || Math.abs(weight - targetWeight) < Math.abs(closestWeight - targetWeight) ) && (!IndexAlreadyTaken.contains(weights.indexOf(weight)) ) ) {
    //             closestWeight = weight;
    //         }
    //     }

    //     int idx = weights.indexOf(closestWeight);
    //     IndexAlreadyTaken.add(idx);

    //     // System.out.println("indexArray: " + IndexAlreadyTaken.toString()  ); //Debugging
    //     // System.out.println("closestWeight: " + closestWeight  ); //Debugging
    //     return closestWeight;
    // }
}