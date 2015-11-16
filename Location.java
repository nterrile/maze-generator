/**
 * Location Class
 * 
 * @author terrilen 
 * @version March 4, 2015
 */
public class Location
{
    private int x;
    private int y;
    
    public Location(int y, int x)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public boolean equals(Object otherObject)
    {
        Location other = (Location) otherObject;
        return this.x == other.x && this.y == other.y;
    }
}
