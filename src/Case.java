import javax.swing.*;
import java.awt.*;

public class Case extends JPanel {
    public Case(){
        color = 0;
        setBackground(Color.LIGHT_GRAY);
    }

    public Case(int color){
        this.color = color;
        if(color == 1)
            setBackground(Color.RED);
        else if (color == 2)
            setBackground(Color.YELLOW);
        else
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

    public void setColorDescente(int color){
        if(color == 1)
            paintComponent(getGraphics(),Color.RED);
        else if (color == 2)
            paintComponent(getGraphics(),Color.YELLOW);
        else
            paintComponent(getGraphics(),Color.LIGHT_GRAY);
    }

    static Case[][] copy(int n,int m,Case[][] grid){
        Case[][] res = new Case[n][m];
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                res[i][j] = new Case(grid[i][j].getColor());
            }
        }
        return res;
    }

    static int[][] copy(int n,int m,int[][] grid) {
        int[][] res = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = grid[i][j];
            }
        }
        return res;
    }

    static int[][] colorGrid(int n,int m,Case[][] grid){
        int[][] res = new int[n][m];
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                res[i][j] = grid[i][j].getColor();
            }
        }
        return res;
    }

    int color;  // 0:vide ; 1:rouge ; 2:jaune
}
