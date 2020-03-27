import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newGameButton extends JButton {
    public newGameButton(Plateau P){
        this.P = P;
        setBackground(Color.WHITE);
        setText("New game");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
    }

    public void newGame(){
        System.out.println("--new game--");
        for(int i = 0;i<P.n;i++){
            for(int j = 0;j<P.m;j++){
                P.tab[i][j].setColor(0);
            }
        }
        for(int k = 0;k<P.m;k++){
            P.playingButtons[k].setPlayable(true);
        }
        P.tour = 1;
    }

    Plateau P;
}
