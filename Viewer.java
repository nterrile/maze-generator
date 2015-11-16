/**
 * Viewer.java
 * 
 * @author terrilen
 * @version 2015-2-2
 */

import javax.swing.JFrame;
import java.util.Scanner;

public class Viewer
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(1200, 800);
        frame.setTitle("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Scanner in = new Scanner(System.in);
        System.out.println("Maze Height:");
        int height = in.nextInt();
        System.out.println("Maze Width:");
        int width = in.nextInt();
        System.out.println("Print Size:");
        int size = in.nextInt();
        
        MazeComponent component = new MazeComponent(height, width, size);
        frame.add(component);
        
        frame.setVisible(true);
    }
}
