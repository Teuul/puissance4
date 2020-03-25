public class ErrGagne extends Exception {
    public ErrGagne(int tour){
        code = tour;
        //System.out.println("Joueur " + tour + " n'a pas gagné.");
    }

    int code;
}
