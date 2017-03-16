package pack;

import java.math.BigInteger;

public class PSHW5 {

	// Finds the smallest number composed only of 0's and 7's that is
	// divisible by 2017
	public static void main(String[] args) {
		
		// Note your start time
		long start = System.currentTimeMillis();
		
		// Define two BigInteger objects
		// Bi will be used to divide curNumBig
		// curNumBig will hold the current number (made of 0s and 7s)
		BigInteger year = new BigInteger("2017");
		BigInteger curNumBig = BigInteger.ZERO;
		
		// Loop through all numbers that are composed of 0s and 7s
		for(int perm = 1; perm < Integer.MAX_VALUE; perm++) {
			
			// We will use permStr essentially as a way to pick out subsets
			// of 7s from an imaginary set. Because this is a binary string,
			// we will actually be checking numbers in increasing order 
			String permStr = Integer.toBinaryString(perm);
			
			// Keep a string representation of the current number
			String curNum = ""; 
			
			// Make each digit of our number a 0 or a 1
			for(int index = 0; index < permStr.length(); index++) {
				if(permStr.charAt(index) == '1') {
					// If the digit of permStr is a 1, throw in a 7 at that digit
					curNum += 7;
				} else {
					// If the digit of permStr is a 0, throw in a 0 at that digit
					curNum += 0;
				}
			}

			// Wrap String into a BigInteger. This allows us to use useful methods
			curNumBig = new BigInteger(curNum);
			
			// If the remainder of this division is 0, we know that our number is
			// divisible by bi
			if(curNumBig.divideAndRemainder(year)[1] == BigInteger.ZERO) {
				// We have found our solution so we exit with curNumBig being assigned
				// the correct smallest number with our desired property
				break;
			}
			
			// Print out the current number every once in a while so we don't eat up
			// cycles, but we still get a good idea of our progress. Essentially a sanity
			// check
			/*
			if(perm % 1000 == 0) {
				System.out.println(curNumBig.toString());
			}
			*/
		}
		
		// Print out the resulting smallest number composed of 0s and 7s that is divisible
		// by 2017 (or any arbitrary number for that matter)
		System.out.println("Done: " + curNumBig.toString());
		
		// Note your end time
		long end = System.currentTimeMillis();
		
		System.out.println("Total time elapsed: " + (end - start) + "ms");
	}
}
