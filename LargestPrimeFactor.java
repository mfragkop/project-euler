/****************************************************
 * Project Euler 3									*
 * The prime factors of 13195 are 5, 7, 13 and 29.	*
 * What is the largest prime factor of 				*
 * the number 600851475143 ?						*
*****************************************************/
public class LargestPrimeFactor {
	
	long x=600851475143L;
    long biggest=0L;
    
    public static int findLargestPrime(long n){
    	int largest = 0;
	    for(int i=2; i<=n; i++){
	        for(int j=1; j <= Math.sqrt(i); j++){
	            if( j%i == 0 ){
	                break;
	            } 
	            else{
	                while(n%i==0){
	                    n /= i;
	                    largest =i;
	                }
	            }
	        }
	    }
	    return largest;
    }
    
    public static void main(String[] args){
    	System.out.println(findLargestPrime(13195));
    	System.out.println(findLargestPrime(600851475143L));
    }
}
