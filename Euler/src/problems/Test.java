package problems;

public class Test {

	public static void main(String[] args) {
		for(int i = 1; i < 101; i++) {
			System.out.println("n = " + i + ": " + Math.pow(2, i) + ", " + Math.pow(5, i));
		}
	}
}
