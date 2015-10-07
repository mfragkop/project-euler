/*************************************************************
 * Project Euler 5											 *
 * 2520 is the smallest number that can be divided			 *
 * by each of the numbers from 1 to 10 without any remainder.*
 * What is the smallest positive number that is evenly 		 *
 * divisible by all of the numbers from 1 to 20?			 *
 *************************************************************/

import java.util.ArrayList;
import java.util.Iterator;


public class SmallestMultiple {

	public static int greatestCommonDivisor(int a, int b){
		int d =0;
		while(a%2 == 0 && b%2 ==0){
			a = a/2;
			b = b/2;
			d++;
		}
		while(a!=b){
			if(a % 2 == 0){
				a = a/2;
			}
			else if (b % 2 == 0){
				b = b/2;
			}
			else if (a > b){
				a = (a - b)/2;
			}
			else{
				b = (b - a)/2;
			}
		}
		return (int) Math.pow(2, d)*a;
	}
	
	private static int findElement(ArrayList<Integer> initialArray, int element, boolean changed){
		if(!changed){
			return element;
		}
		ArrayList<Integer> numberArray = new ArrayList<>(initialArray);
		Iterator<Integer> it = numberArray.iterator();
		int largestgcd = 1;
		while(it.hasNext()){
			int el = it.next();
			int gcd = greatestCommonDivisor(el, element);
			if(gcd>largestgcd){
				it.remove();
				largestgcd = gcd;
			}
		}
		if(largestgcd==1){
			changed = false;
		}
		return findElement(numberArray, element/largestgcd, changed);
	}

	
	public static int findSmallestMultiple(int n){
		ArrayList<Integer> numberList = new ArrayList<Integer>();
		for(int i = n; i>0; i--){
			int element = findElement(numberList, i, true);
			if (element!=1){
			numberList.add(element);
			}
		}

		int product = 1;
		Iterator<Integer> it = numberList.iterator();
		while(it.hasNext()){
			int el = it.next();
			System.out.print(el+",");
			product *=el;
		}
		return product;
	}
	
	public static void main(String[] args){
		System.out.println(findSmallestMultiple(20)); }
}
