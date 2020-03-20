import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bouttonJoue extends JButton {

    public bouttonJoue(int m,Plateau P){
        this.col = m;
        setBackground(Color.LIGHT_GRAY);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    P.joue(m);
                } catch (ErrGagne err) {
                    System.out.println("Le jeu continue.");
                }
            }
        });
    }

    int col;
    Plateau p;
}
