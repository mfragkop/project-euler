/********************************************************
 * Project Euler 4										*
 * A palindromic number reads the same both ways. 		*
 * The largest palindrome made from the product of 		*
 * two 2-digit numbers is 9009 = 91 × 99.				*
 * Find the largest palindrome made from the product 	*
 * of two 3-digit numbers.								*
*********************************************************/

public class LargestPalidromic {
	
	public static boolean isPalindromic(Integer i){
		String number = i.toString();
		if (number.equals(new StringBuilder(number).reverse().toString())){
			return true;
		}
		return false;
	}

	public static int findLargestPalindrome(int n){
		int largest = 0;
		for(int i = n; i > 0; i--){
			for (int j = i; j>0; j--){
				int product = j*i;
				if(isPalindromic(product)){
					if (product > largest){
						largest = product;
					}
				}
			}
		}
		return largest;
	}
	
	public static void main(String args[]){
		System.out.println(findLargestPalindrome(999));
	}
}
