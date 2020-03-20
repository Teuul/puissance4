import javax.swing.*;
import java.awt.*;

class Plateau extends JFrame{
	
	public Plateau(int n,int m){
		this.n = n;
		this.m = m;
		tour = 1;
		background = new JPanel();
		background.setPreferredSize(new Dimension(600,400));
		background.setLayout(new GridLayout(n,m));
		background.setBackground(Color.DARK_GRAY);
		tab = new Case[n][m];
		getContentPane().add(background);
		for(int i=0;i<n;i++) {
			for (int j = 0; j < m; j++) {
				tab[i][j] = new Case();
				background.add(tab[i][j]);
			}
		}
	}

	void imprime(){
		for(int i=0;i<n;i++){
			System.out.print("|");
			for(int j=0;j<m;j++){
				System.out.print(tab[i][j].getColor()+"|");
			}
			System.out.println();
			System.out.print("-");
			for(int j=0;j<7;j++){
				System.out.print("--");
			}
			System.out.println();
		}
	}

	int joue(int j) throws ErrGagne{
		int iter = 5;
		while(iter>=0 && tab[iter][j].getColor()!=0){
			iter-=1;
		}
		if(iter>=0) {
			tab[iter][j].setColor(tour);
			this.lastX = j;
			this.lastY = iter;
			int test_winner = tour;
			if (this.tour == 1)
				this.tour = 2;
			else
				this.tour = 1;
			System.out.println("Joueur " + gagne(test_winner) + " a gagné !");
			return test_winner;
		}
		return -1;
	}

	int gagne(int joueur) throws ErrGagne{
		int i;
		//direction 1
		int c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX+i,lastY,c,joueur);
			//System.out.println("DIRECTION 1["+(lastX+i)+","+lastY+"]="+getCase(lastX+i,lastY)+"|c="+c);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;

		//direction 2
		c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX,(lastY+i),c,joueur);
			//System.out.println("DIRECTION 2["+(lastX)+","+(lastY+i)+"]="+getCase(lastX,lastY+i)+"|c="+c);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;

		//direction 3
		c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX+i,lastY+i,c,joueur);
			//System.out.println("DIRECTION 3["+(lastX+i)+","+(lastY+i)+"]="+getCase(lastX+i,lastY+i)+"|c="+c);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;

		//direction 4
		c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX+i,lastY-i,c,joueur);
			//System.out.println("DIRECTION 4["+(lastX+i)+","+(lastY-i)+"]="+getCase(lastX,lastY-i)+"|c="+c);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;
		throw new ErrGagne(joueur);
	}

	int compte(int x,int y,int c,int joueur){
		if(getCase(x,y)!=null) {
			if (getCase(x, y).getColor() == joueur)
				return c + 1;
		}
		return 0;
	}

	Case getCase(int x,int y){
		if(0<=x && x<m){
			if(0<=y && y<n){
				return tab[y][x];
			}
		}
		return null;
	}

	int lastX,lastY;// Y:ligne et X:colonne
	int tour; 	// 1 ou 2
	int n,m;
	private Case[][] tab;
	JPanel background;
}
