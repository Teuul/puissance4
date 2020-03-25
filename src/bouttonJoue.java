import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bouttonJoue extends JButton {

    public bouttonJoue(int m,Plateau P,Bot B){
        this.p = P;
        this.col = m;
        this.B = B;
        this.playable =  true;
        setBackground(Color.LIGHT_GRAY);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(getPlayable()==true)
                        P.joue(m);
                } catch (ErrGagne err) {
                    //System.out.println("Le jeu continue.");
                }
                if(getPlayable()==true)
                    B.play();
            }
        });
    }

    public void setPlayable(boolean playable){
        this.playable = playable;
    }

    public boolean getPlayable(){
        return playable;
    }

    int col;
    Plateau p;
    Bot B;
    private boolean playable;
}
