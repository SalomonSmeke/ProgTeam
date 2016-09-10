import java.util.Scanner;

public class hangover {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		float val = sc.nextFloat();
		while (val != 0) {
			float attained = 0;
			float cards = 1;
			while (attained < val){
				attained += 1/(cards+1);
				cards++;
			}
			System.out.println(Math.round(cards-1) + " card(s)");
			val = sc.nextFloat();
		}
	}
}
