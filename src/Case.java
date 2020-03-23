import javax.swing.*;
import java.awt.*;

public class Case extends JPanel {
    public Case(){
        color = 0;
        setBackground(Color.LIGHT_GRAY);
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(25,25,25,25);
    }

    public void paintComponent(Graphics g,Color c){
        g.setColor(c);
        g.fillOval(25,25,25,25);
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        if(color == 1)
            paintComponent(getGraphics(),Color.RED);
        else if (color == 2)
            paintComponent(getGraphics(),Color.YELLOW);
        else
            paintComponent(getGraphics(),Color.LIGHT_GRAY);
    }

    int color;  // 0:vide ; 1:rouge ; 2:jaune
}
