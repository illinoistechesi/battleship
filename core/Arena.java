package core;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

public class Arena {
    
    Grid<Ship> grid;
    
    public Arena(int xSize, int ySize) {
        this.grid = new Grid<Ship>(xSize, ySize);
    }
    
    public void fire(Ship source, int x, int y) {
        if (source.getRemainingShots() > 0) {
            Ship target = getGrid().get(x, y);
            if (target != null) {
                target.sustainHit();
                source.useShot();
                if (target.isSunk()) {
                    getGrid().set(x, y, null);
                    target.recordSinking(source);
                }
            }
        }
    }
    
    public void move(Ship source, Direction dir) {
        if (source.getRemainingMoves() > 0) {
            int x = source.getCoord().getX();
            int y = source.getCoord().getY();
            switch (dir) {
                case NORTH:
                    y--;
                    break;
                case SOUTH:
                    y++;
                    break;
                case WEST:
                    x--;
                    break;
                case EAST:
                    x++;
                    break;
            }
            if (getGrid().get(x, y) == null) {
                boolean success = getGrid().set(x, y, source);
                if (success) {
                    int oldX = source.getCoord().getX();
                    int oldY = source.getCoord().getY();
                    getGrid().set(oldX, oldY, null);
                    source.setCoord(x, y);
                    source.useMove();
                }
            }
        }
    }
    
    public List<Ship> getNearbyEnemies(Ship source) {
        List<Ship> res = new ArrayList<Ship>();
        int range = source.getRange() + 1;
        int xPos = source.getCoord().getX();
        int yPos = source.getCoord().getY();
        for (int x = xPos - range; x < xPos + range; x++) {
            for (int y = yPos - range; y < yPos + range; y++) {
                Ship ship = getGrid().get(x, y);
                if (ship != null) {
                    if (!ship.equals(source)) {
                        res.add(ship);
                    }
                }
            }
        }
        return res;
    }
    
    public List<Ship> getNearbyAllies(Ship source) {
        return new ArrayList<Ship>();
    }
    
    public List<Ship> getAllAllies(Ship source) {
        return new ArrayList<Ship>();
    }
    
    public int countEnemies(Ship source) {
        return 0;
    }
    
    public int countAllies(Ship source) {
        return 0;
    }
    
    protected boolean spawnShip(int x, int y, Ship ship) {
        boolean success = false;
        if (getGrid().get(x, y) == null) {
            success = getGrid().set(x, y, ship);
            if (success) {
                ship.setCoord(x, y);
            }
        }
        return success;
    }
    
    private List<Ship> getAllShips() {
        List<Ship> res = new ArrayList<Ship>();
        for (Ship ship : getGrid().getAll()) {
            if (ship != null) {
                if (!ship.isSunk()) {
                    res.add(ship);
                }
            }
        }
        return res;
    }
    
    protected List<Ship> sortShipsByPriority() {
        List<Ship> ships = this.getAllShips();
        Collections.sort(ships, new Comparator<Ship>() {
            public int compare(Ship s1, Ship s2) {
                double p1 = (double) s1.getSpeed();
                double p2 = (double) s2.getSpeed();
                p1 += getRandom().nextDouble();
                p2 += getRandom().nextDouble();
                double diff = p2 - p1;
                return (int) diff;
            }
        });
        return ships;
    }
    
    private Random random = new Random();
    
    public Random getRandom() {
        return this.random;
    }
    
    protected void setSeed(int seed) {
        this.getRandom().setSeed(seed);
    }
    
    protected Grid<Ship> getGrid() {
        return this.grid;
    }
    
    protected void printArena() {
        for (int y = 0; y < grid.getYSize(); y++) {
            for (int x = 0; x < grid.getXSize(); x++) {
                Ship ship = grid.get(x, y);
                if (ship != null) {
                    System.out.print("[S]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.print("\n");
        }
    }
    
    protected String getArenaAsText() {
        String out = "";
        out += "   ";
        for (int h = 0; h < grid.getXSize(); h++) {
            out += String.format(" %d ", h);
        }
        out += "\n";
        for (int y = 0; y < grid.getYSize(); y++) {
            out += String.format(" %d ", y);
            for (int x = 0; x < grid.getXSize(); x++) {
                Ship ship = grid.get(x, y);
                if (ship != null) {
                    out += "[" + ship.getHealth() + "]";
                } else {
                    out += " ~ ";
                }
            }
            out += "\n";
        }
        return out;
    }
    
}