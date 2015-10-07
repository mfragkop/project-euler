/**************************************************************************** 
 * Project Euler 268														*
 * It can be verified that there are 23 positive integers less than 1000 	*
 * that are divisible by at least four distinct primes less than 100.		*
 * Find how many positive integers less than 1016 are divisible by at 		*
 * least four distinct primes less than 100.								*
 * 																			*
 ****************************************************************************/


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;


public class FourDistinctPrimeFactors {
	
	public static final long  LIMIT = 10000000000000000L;
	
	/* Helper method to check if an integer is a prime number*/
	private static boolean isPrime(int number){
		for (int i = 2; i <= Math.sqrt (number); i++)
        {
            if (number % i == 0)
            {
                return false;
            }
        }
        return true;
    }
	
	/* Finds prime numbers less than n */
	public static ArrayList<Integer> findPrimesLessThanN(int n){
		ArrayList<Integer> listOfPrimes = new ArrayList<Integer>();
		for (int i = 2; i<n; i++){
			if (isPrime(i)){
				listOfPrimes.add(i);
			}
		}
		return listOfPrimes;
	}
	
	/* Computes all combinations of selected from outOf 
	 * and stores its product in an array. 
	 * eg for the combination (2,3,5,7) it stores 210.
	 * This function mainly uses combinationUtil() */
	public static ArrayList<Long> computeCombinations(ArrayList<Integer> primes, int outOf, int selected)
	{
	    long data[] = new long[selected]; 
	    ArrayList<Long> outputArray = new ArrayList<Long>();
	    return combinationUtil(primes, data, outputArray, 0, outOf-1, 0, selected);
	}
	
	/* primes : Input Array List
	 * data[] : Temporary array to store current combination
	 * outputArray : Array List to store the combinations 
	 * start & end : Staring and Ending indexes in primes
	 * index : Current index in data[]
	 * r : Size of a combination to be printed */
	private static ArrayList<Long> combinationUtil(ArrayList<Integer> primes, long data[], ArrayList<Long> outputArray, int start, int end, int index, int r)
	{
	    if (index == r)
	    {
	    	BigInteger multiply = BigInteger.valueOf(1);
	        for (int j=0; j<r; j++)											// Find the product of the combination 
	            multiply = multiply.multiply( BigInteger.valueOf(data[j]));
	        if(multiply.subtract( BigInteger.valueOf(LIMIT)).signum()!=1){ // If the product is greater than limit, it isn't divisible by limit, we don't want to count it
	        	outputArray.add(multiply.longValue());
	        }
	        return outputArray;
	    }
	 
	    /* Replace index with all possible elements. */
	    for (int i=start; i<=end && end-i+1 >= r-index; i++)
	    {
	        data[index] = primes.get(i);
	        combinationUtil(primes, data, outputArray, i+1, end, index+1, r);
	    }
	    return outputArray;
	}
	
	/* Utility method to compute the number all possible combinations of selected from outOf*/
	public static Long computeNrOfCombinations(int selected, int outOf){
		Integer nominator=1;
		for (int i =1 ; i<= outOf; i++){
			nominator *= i;
		}
		Long firstDemon = 1L;
		for (int j=1; j<= selected; j++){
			firstDemon *= j;
		}
		Long secondDemon = 1L;
		for (int k=1; k<=outOf -selected; k++){
			secondDemon *= k;
		}
		return nominator/(firstDemon*secondDemon);
	}
	
	/******************************************************************************************
	 *  Computes the coefficients of the intersections that we add and subtract 
	 * using the inclusion exclusion principle. When we divide each of the product 
	 * of combinations of fours, we count multiple times some of the numbers. For example,
	 * we compute all the limit/(2*3*5*7*11) 5 times, when we divide the limit with (2,3,5,7)
	 * (2,3,5,11), (2,3,7,11), (2,5,7,11) and (3,5,7,11), so we count them 4 extra times.
	 * Then we have to add back the intersections (eg (2,3,5,7,11,13))
	 * that we subtract multiple times and so on (inclusion exclusion principle)
	 * There are 15 different sets of fours in (2,3,5,7,11,13) but we have kept 5 of them,
	 * namely  all the four sets that are derived from (2,3,5,7,11). 
	 * So in general rule the coefficients are computed as coef(i+1) = comb(i+i,4)-comb(i,4).
	 * Doing some maths is equal to (i+1)*(i+2)*(i+3)
	 * ****************************************************************************************/
	public static int[] computeCoeff(){
		int[] coeff = new int[25];
		for (int i = 0; i<25 ; i++){
			coeff[i] = (i+1) * (i+2) * (i+3) / 6;
		}
		return coeff;
	}
	 
	/* Computes the solution calling the recursive method computeSolutionUtil */
	public static Long computeSolution(ArrayList<Integer> arr){
		return computeSolutionUtil(computeCoeff(),  arr, 4, (long)0);
	}
	
	/*************************************************************************************************
	 * First we calculate all the possible combinations of four elements in the prime list, 
	 * then we compute for each combination each product and we divide the limit with each one of them.
	 * At the second step, we calculate all the combinations of five elements and subtract them,
	 * making use of the inclusion exclusion principle, and so on as described before. 
	 * ***********************************************************************************************/
	public static Long computeSolutionUtil(int[] coeff,  ArrayList<Integer> primes, int time,  Long sumOfNumbers){
		ArrayList<Long> combinationArray = computeCombinations(primes, primes.size(), time); 
		Iterator<Long> it = combinationArray.iterator();
		if(combinationArray.size()<=0){ 
			return sumOfNumbers;
		}
		long temp = 0;
		if(time%2 == 0){	
			while(it.hasNext()){
				temp+= (long) (LIMIT/it.next());
				}
			sumOfNumbers += (coeff[time-4])*temp; //time starts at 4
		}
			else{
				while(it.hasNext()){
				temp += (long) (LIMIT/it.next());
			}
				sumOfNumbers -= (coeff[time-4])*temp;
		}
			return computeSolutionUtil(coeff,  primes, ++time, sumOfNumbers);
	}
	
	
	public static void main(String[] args){
		ArrayList<Integer> primes = findPrimesLessThanN(100);
		System.out.println("Sum = "+ computeSolution(primes));
	}
	
}
