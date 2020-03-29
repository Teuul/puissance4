import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Plateau extends JFrame{
	
	public Plateau(int n,int m){
		this.n = n;
		this.m = m;
		tour = 1;
		Bot B = new Bot(this);
		background = new JPanel();
		gamePane = new JPanel();
		menuPane = new JPanel();

		background.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 1;
		c.weighty = 1;

		background.add(gamePane,c);
		c.weightx = 0.25;
		c.weighty = 1;
		background.add(menuPane,c);

		// Menu
		menuPane.setPreferredSize(new Dimension(100,50));
		menuPane.setLayout(new GridLayout(2,1));
		menuPane.setBackground(Color.ORANGE);
		// quit button
		JButton quitButton = new JButton("Quit");
		quitButton.setBackground(Color.BLACK);
		quitButton.setForeground(Color.WHITE);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		menuPane.add(quitButton);
		// restart button
		restartButton = new newGameButton(this);
		menuPane.add(restartButton);

		gamePane.setPreferredSize(new Dimension(450,300));
		gamePane.setLayout(new GridLayout(n+1,m));
		playingButtons = new bouttonJoue[m];
		for(int k =0;k<m;k++){
			playingButtons[k] = new bouttonJoue(k,this,B);
			gamePane.add(playingButtons[k]);
		}
		gamePane.setBackground(Color.DARK_GRAY);
		tab = new Case[n][m];
		getContentPane().add(background);
		for(int i=0;i<n;i++) {
			for (int j = 0; j < m; j++) {
				tab[i][j] = new Case();
				gamePane.add(tab[i][j]);
			}
		}
	}

	void imprime(){		// Imprime le plateau dans la console
		for(int i=0;i<n;i++){
			System.out.print("|");
			for(int j=0;j<m;j++){
				System.out.print(tab[i][j].getColor()+"|");
			}
			System.out.println();
			//System.out.print("-");
			/*for(int j=0;j<7;j++){
				System.out.print("--");
			}*/
			//System.out.println();
		}
	}

	int joue(int j) throws ErrGagne{
		int iter = n-1;
		while(iter>=0 && tab[iter][j].getColor()!=0){
			iter-=1;
		}
		if(iter>=0) {
			// descente du jeton
			tab[iter][j].setColorValue(tour);
			Descente d;
			if(tour == 1)
				 d = new Descente(iter,j,tab,n,1);
			else
				d = new Descente(iter,j,tab,n,2);
			d.start();
			this.lastX = j;
			this.lastY = iter;
			int test_winner = tour;
			if (this.tour == 1) {
				for(int k = 0;k<m;k++){
					playingButtons[k].setPlayable(false);
					playingButtons[k].setBotplayable(true);
				}
				this.tour = 2;
			}
			else {
				for(int k = 0;k<m;k++){
					playingButtons[k].setBotplayable(false);
					playingButtons[k].setPlayable(true);
				}
				this.tour = 1;
			}
			System.out.println("Joueur " + gagne(test_winner) + " a gagné !");
			for(int k = 0;k<m;k++){
				playingButtons[k].setBotplayable(false);
				playingButtons[k].setPlayable(false);
			}
			return test_winner;
		}
		return -1;
	}

	int gagne(int joueur) throws ErrGagne{
		int s=0;
		for(int k = 0;k<n;k++){
			for(Case c:tab[k]){
				if(c.getColor()!=0)
					s+=1;
			}
		}
		if(s==n*m)
			throw new ErrGagne(3,this);
		int i;
		//direction 1
		//System.out.println("["+lastX+","+lastY+"]");
		int c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX+i,lastY,c,joueur);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;
		//System.out.println("Count:"+c);
		//direction 2
		c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX,(lastY+i),c,joueur);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;
		//System.out.println("Count:"+c);
		//direction 3
		c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX+i,lastY+i,c,joueur);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;
		//System.out.println("Count:"+c);
		//direction 4
		c= 0;
		for(i=-3;i<=3;i++){
			c = compte(lastX+i,lastY-i,c,joueur);
			if(c==4)
				break;
		}
		if(c==4)
			return joueur;
		//System.out.println("Count:"+c);
		throw new ErrGagne(joueur,this);
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
	Case[][] tab;
	JPanel background;
	JPanel gamePane;
	JPanel menuPane;
	newGameButton restartButton;
	bouttonJoue[] playingButtons;
}
