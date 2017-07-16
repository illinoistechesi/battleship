package battleship.core;
import java.util.List;
import java.util.ArrayList;

public class Grid<T> {
    
    private int xSize;
    private int ySize;
    private List<List<T>> grid;
    
    public Grid(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.grid = new ArrayList<List<T>>();
        for (int y = 0; y < ySize; y++) {
            List<T> row = new ArrayList<T>();
            for (int x = 0; x < xSize; x++) {
                row.add(null);
            }
            this.grid.add(row);
        }
    }
    
    public boolean isInBounds(int x, int y) {
        boolean inXBounds = x > -1 && x < this.getXSize();
        boolean inYBounds = y > -1 && y < this.getYSize();
        boolean inBounds = inXBounds && inYBounds;
        return inBounds;
    }
    
    public T get(int x, int y) {
        T res = null;
        if (this.isInBounds(x, y)) {
            res = grid.get(y).get(x);
        }
        return res;
    }
    
    public boolean set(int x, int y, T obj) {
        boolean success = false;
        if (this.isInBounds(x, y)) {
            grid.get(y).set(x, obj);
            success = true;
        }
        return success;
    }
    
    public int getXSize() {
        return this.xSize;
    }
    
    public int getYSize() {
        return this.ySize;
    }
    
    /*
     * Note: Might want to implement Iterable instead
     * https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
     */
    
    public List<T> getAll() {
        List<T> list = new ArrayList<T>();
        for (List<T> row : grid) {
            for(T col : row) {
                list.add(col);
            }
        }
        return list;
    }
    
}