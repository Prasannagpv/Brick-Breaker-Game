import java.awt.BasicStroke;
import java.awt.Color;
//import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapGenerator {
   
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row, int col)
    {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) 
        {
            for (int j = 0; j < map[0].length; j++) 
            {
                map[i][j] = 1;/*this particular brick have not been 
                                intersected with the ball because this has to be shown on 
                                the panel  */  
            
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;

    }

    public void draw(Graphics2D g)
    {
        for (int i = 0; i < map.length; i++) 
        {
            for (int j = 0; j < map[0].length; j++)
            {
                if(map[i][j]>0)
                {   
                    g.setColor(Color.RED);
                    g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
                }
            } 
        }
    }


    public void setBrickValue(int value, int row, int col)
    {
        map[row][col] = value;
    }
}
