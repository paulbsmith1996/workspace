package problems;

public class P66 {

	public static void main(String[] args) {
		
		long maxX = 0;
		int bestD = 0;
		
		for(int d = 1; d <= 1000; d++) {
			if(!isSquare(d)) {
				
				long sol = minSol2(d);
				System.out.println(d + ": " + sol);
				
				if(sol > maxX) {
					maxX = sol;
					bestD = d;
				}
			}
		}
		
		System.out.println("Max x: " + maxX);
		System.out.println("Best D: " + bestD);
		
	}
	
	
	public static long minSol2(long d) {
	
		long x = 1;
		long curVal = 0;
		
		while(curVal != 1) {
			
			for(int y = 1; y < x; y++) {
				
				curVal = x*x - d*y*y;
				
				if(curVal == 1) {
					break;
				}
			}
			x++;
		}
		
		return x - 1;
	}
	
	
	public static long minSol(int d) {
		
		int i = 1;
		
		while(!isSquare((d * i * i) + 1)) {
			i++;
		}
		
		System.out.println("y is: " + i);
		long x = (d * i * i) + 1;
		
		return (long)Math.sqrt(x);
	}
	
	public static boolean isSquare(long square) {
		return Math.sqrt(square) % 1 == 0;
	}
}
