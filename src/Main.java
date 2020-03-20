import java.util.Scanner;

class Main {
	static public void main(String args[]) throws ErrGagne {
		Plateau P = new Plateau(6, 7);
		P.setSize(600, 400);
		P.setVisible(true);
		/*int winner = -1;
		while (winner != 1 && winner != 2) {
			P.imprime();
			String input;
			System.out.print("Tour de [" + P.tour + "]: jouer dans la colonne ");
			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
			try {
				winner = P.joue(Integer.valueOf(input));
			} catch (ErrGagne e) {
				System.out.println("Le jeu continue.");
			}
		}*/
		P.imprime();
	}
}
