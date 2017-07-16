package battleship.core;

public class Coord {
    
    private int x;
    private int y;
    
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return int X component of a coordinate
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * @return int Y component of a coordinate
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * 
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }
    
}