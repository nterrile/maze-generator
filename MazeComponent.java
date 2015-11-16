/**
 * MazeComponent.java
 * 
 * @author terrilen
 * @version 2015-2-2
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JComponent;

public class MazeComponent extends JComponent
{
    private int height;
    private int width;
    private int size;
    public MazeComponent(int height, int width, int size)
    {
        this.height = height;
        this.width = width;
        this.size = size;
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Maze m = new Maze(height, width);
        m.newMaze();
        boolean[][] maze = m.getMazeArray();
        width = maze[0].length;
        height = maze.length;
        //int size = 700 / height;

        Rectangle wall = new Rectangle(0, 0, size, size);
        g2.setColor(Color.BLACK);
        g2.fill(wall);
        g2.draw(wall);
        int x = 0;
        int y = 0;

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (!maze[i][j])
                {
                    wall = new Rectangle(x, y, size, size);
                    g2.fill(wall);
                    g2.draw(wall);
                }
                x += size;
            }
            x = 0;
            y += size;
        }
    }
}
