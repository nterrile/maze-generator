/**
 * Maze.java
 * 
 * @author terrilen
 * @version March 4, 2015
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.*;
public class Maze
{
    private int rows;
    private int cols;
    private boolean[][] maze;
    private boolean[][] solution;
    private boolean[][] robotPath;
    private Random generator;
    private Location start;
    private Location finish;
    private final String WALL = "[]";
    private final String SPACE = "  ";
    private final String ROBOT = "\u2588\u258C";
    private final String PATH = "• ";
    public Maze(int height, int width)
    {
        if (height % 2 == 0) 
        {
            rows = height - 1;
        }
        else
        {
            rows = height;
        }
        if (width % 2 == 0)
        {
            cols = width - 1;
        }
        else
        {
            cols = width;
        }
        maze = new boolean[rows][cols];
        solution = new boolean[rows][cols];
        robotPath = new boolean[rows][cols];
        generator = new Random();
        start = new Location(0, 1);
        finish = new Location(rows - 1, cols - 2);
        newMaze();
    }
    
    public boolean[][] getMazeArray()
    {
        return maze;
    }

    public void newMaze()
    {
        carvePath();
        carveDeadEnds();
    }

    public void carvePath()
    {
        reset();
        maze[0][1] = true;
        maze[1][1] = true;
        int x = 1;
        int y = 1;
        int count = 0;
        while (x != cols - 2 || y != rows - 2)
        {
            int direction = generator.nextInt(4);
            if (direction == 0 && y - 2 > 0 && x < cols - 3 && x > 2)
            {
                if (maze[y - 2][x] == false && maze[y - 3][x] == false 
                && maze[y - 2][x + 1] == false && maze[y - 2][x - 1] == false)
                {
                    y -= 1;
                    maze[y][x] = true;
                    y -= 1;
                    maze[y][x] = true;
                }
            }
            else if (direction == 1 && x + 2 < cols - 1)
            {
                if (maze[y][x + 2] == false && maze[y][x + 3] == false 
                && maze[y + 1][x + 2] == false && maze[y - 1][x + 2] == false)
                {
                    x += 1;
                    maze[y][x] = true;
                    x += 1;
                    maze[y][x] = true;
                }
            }
            else if (direction == 2 && y + 2 < rows - 1)
            {
                if (maze[y + 2][x] == false && maze[y + 3][x] == false 
                && maze[y + 2][x + 1] == false && maze[y + 2][x - 1] == false)
                {
                    y += 1;
                    maze[y][x] = true;
                    y += 1;
                    maze[y][x] = true;
                }
            }
            else if (direction == 3 && x - 2 > 0 && y < rows - 3 && y > 2)
            {
                if (maze[y][x - 2] == false && maze[y][x - 3] == false 
                && maze[y + 1][x - 2] == false && maze[y - 1][x - 2] == false)
                {
                    x -= 1;
                    maze[y][x] = true;
                    x -= 1;
                    maze[y][x] = true;
                }
            }
            count++;
            if (count > rows * cols)
            {
                break;
            }
        }
        maze[rows - 1][cols - 2] = true;
        if (count > rows * cols)
        {
            carvePath();
        }
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                solution[row][col] = maze[row][col];
            }
        }
    }

    public void carveDeadEnds()
    {
        int x = 0;
        int y = 0;
        boolean foundSpace = false;
        for (int row = rows - 2; row > 0; row -= 2)
        {
            for (int col = 1; col < cols - 1; col += 2)
            {
                x = col;
                y = row;
                boolean validSpace = true;
                for (int i = row - 1; i <= row + 1; i++)
                {
                    for (int j = col - 1; j <= col + 1; j++)
                    {
                        if (maze[i][j])
                        {
                            validSpace = false;
                            break;
                        }
                    }
                    if (!validSpace)
                    {
                        break;
                    }
                }
                if (validSpace)
                {
                    foundSpace = true;
                    break;
                }
            }
            if (foundSpace)
            {
                break;
            }
        }
        
        if (foundSpace)
        {
            maze[y][x] = true;
            int lastDirection = -1;
            ArrayList<String> positions = new ArrayList<String>();
            positions.add(Integer.toString(y) + "," + Integer.toString(x));
            boolean[] tries = new boolean[] {false, false, false, false};
            int indexOfPositions = 0;
            while (true)
            {
                if (!containsFalse(tries))
                {
                    indexOfPositions--;
                    String location = positions.get(indexOfPositions);
                    y = Integer.parseInt(location.substring(0, location.indexOf(",")));
                    x = Integer.parseInt(location.substring(location.indexOf(",") + 1));
                    for (int i = 0; i < 4; i++)
                    {
                        tries[i] = false;
                    }
                }
                int direction = generator.nextInt(4);
                tries[direction] = true;
                if (direction == 0 && y - 2 > 0 && lastDirection != 2 && 
                !positions.contains(Integer.toString(y - 2) + "," + Integer.toString(x)))
                {
                    y -= 1;
                    maze[y][x] = true;
                    y -= 1;
                    if (maze[y][x])
                    {
                        break;
                    }
                    else
                    {
                        maze[y][x] = true;
                    }
                    positions.add(Integer.toString(y) + "," + Integer.toString(x));
                    indexOfPositions++;
                    lastDirection = direction;
                    for (int i = 0; i < 4; i++)
                    {
                        tries[i] = false;
                    }
                }
                else if (direction == 1 && x + 2 < cols - 1 && lastDirection != 3 &&
                !positions.contains(Integer.toString(y) +  "," + Integer.toString(x + 2)))
                {
                    x += 1;
                    maze[y][x] = true;
                    x += 1;
                    if (maze[y][x])
                    {
                        break;
                    }
                    else
                    {
                        maze[y][x] = true;
                    }
                    positions.add(Integer.toString(y) + "," + Integer.toString(x));
                    indexOfPositions++;
                    lastDirection = direction;
                    for (int i = 0; i < 4; i++)
                    {
                        tries[i] = false;
                    }
                }
                else if (direction == 2 && y + 2 < rows - 1 && lastDirection != 0 &&
                !positions.contains(Integer.toString(y + 2) +  "," + Integer.toString(x)))
                {
                    y += 1;
                    maze[y][x] = true;
                    y += 1;
                    if (maze[y][x])
                    {
                        break;
                    }
                    else
                    {
                        maze[y][x] = true;
                    }
                    positions.add(Integer.toString(y) + "," + Integer.toString(x));
                    indexOfPositions++;
                    lastDirection = direction;
                    for (int i = 0; i < 4; i++)
                    {
                        tries[i] = false;
                    }
                }
                else if (direction == 3 && x - 2 > 0 && lastDirection != 1 &&
                !positions.contains(Integer.toString(y) +  "," + Integer.toString(x - 2)))
                {
                    x -= 1;
                    maze[y][x] = true;
                    x -= 1;
                    if (maze[y][x])
                    {
                        break;
                    }
                    else
                    {
                        maze[y][x] = true;
                    }
                    positions.add(Integer.toString(y) + "," + Integer.toString(x));
                    indexOfPositions++;
                    lastDirection = direction;
                    for (int i = 0; i < 4; i++)
                    {
                        tries[i] = false;
                    }
                }
            }
            carveDeadEnds();
        }
    }

    public void reset()
    {
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                maze[row][col] = false;
            }
        }
    }

    private boolean containsFalse(boolean[] array)
    {
        for (boolean value : array) {
            if (!value) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isLegal(Location loc)
    {
        if (loc.getX() >= 0 &&
            loc.getX() < maze.length &&
            loc.getY() >= 0 &&
            loc.getY() < maze[0].length)
        {
            return maze[loc.getY()][loc.getX()];
        }
        else
        {
            return false;
        }
    }
    
    public Location getStart()
    {
        return start;
    }
    
    public Location getFinish()
    {
        return finish;
    }

    public String toString()
    {
        String mazeString = "";
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (maze[row][col] == false)
                {
                    mazeString += WALL;
                }
                else
                {
                    mazeString += SPACE;
                }
            }
            if (row < rows - 1)
            {
                mazeString += "\n";
            }
        }
        return mazeString;
    }
    
    public String toString(Location robotLocation)
    {
        String mazeString = "";
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (robotLocation.getY() == row && robotLocation.getX() == col)
                {
                    mazeString += ROBOT;
                }
                else if (maze[row][col] == false)
                {
                    mazeString += WALL;
                }
                else
                {
                    mazeString += SPACE;
                }
            }
            if (row < rows - 1)
            {
                mazeString += "\n";
            }
        }
        return mazeString;
    }
    
    public String toStringWithPath(Location robotLocation)
    {
        robotPath[robotLocation.getY()][robotLocation.getX()] = true;
        String mazeString = "";
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (robotLocation.getY() == row && robotLocation.getX() == col)
                {
                    mazeString += ROBOT;
                }
                else if (robotPath[row][col])
                {
                    mazeString += PATH;
                }
                else if (maze[row][col] == false)
                {
                    mazeString += WALL;
                }
                else
                {
                    mazeString += SPACE;
                }
            }
            if (row < rows - 1)
            {
                mazeString += "\n";
            }
        }
        return mazeString;
    }
    
    public String toStringWithSolution()
    {
        String mazeString = "";
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (solution[row][col] == true)
                {
                    mazeString += PATH;
                }
                else if (maze[row][col] == false)
                {
                    mazeString += WALL;
                }
                else
                {
                    mazeString += SPACE;
                }
            }
            if (row < rows - 1)
            {
                mazeString += "\n";
            }
        }
        return mazeString;
    }
}
