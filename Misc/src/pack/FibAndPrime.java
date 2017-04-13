package pack;

public class FibAndPrime {

	public static void main(String[] args) {
		boolean isGood = true;
		int num = 100000;
		
		int failCount = 0;
		
		
		//while(isGood) {
		while(num < 2000000) {
			
			int n = 0;
			int fib = fib(n);
			
			isGood = false;
			
			while(true) {
				
				if(isPrime(num - fib) == 1) {
					System.out.println(num + " is good: Fib = " + fib + ", Prime = " + (num-fib));
					isGood = true;
					break;
				}
				
				if(isPrime(num + fib) == 1) {
					System.out.println(num + " is good: Fib = -" + fib + ", Prime = " + (num+fib));
					isGood = true;
					break;
				}
				
				n++;
				fib = fib(n);
			}
			if(!isGood) {
				failCount++;
			}
			
			num++;
		}
		
		//System.out.println("Bad num: " + (num - 1));
		System.out.println(failCount);
	}
	
	public static int fib(int n) {
		if(n == 0) {
			return 0;
		} else if(n == 1) {
			return 1;
		}
		
		return fib(n-1) + fib(n-2);
	}
	
	public static int isPrime(int p) {
		
		if(p == 1) {
			return 0;
		}
		
		double s  = Math.sqrt(p);
		for(int i = 2; i <= s; i++) {
			if(p % i == 0) {
				return 0;
			}
		}
		
		return 1;
	}
	
	public static int gcd(int a, int b) {
		int r;
		while (b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return a;

	}
}
