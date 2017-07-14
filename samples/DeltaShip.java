package samples;
import core.*;
import java.util.List;

/*
 * Delta Ship
 * @author Vinesh Kannan
 */
public class DeltaShip extends Ship {
    
    public static void main(String[] args) {
        Ship ship = new DeltaShip();
        System.out.println("Hull: " + ship.getHull());
        System.out.println("Firepower: " + ship.getFirepower());
        System.out.println("Speed: " + ship.getSpeed());
        System.out.println("Range: " + ship.getRange());
    }
    
    private String NAME = "Delta Ship";
    private String OWNER = "Vinesh";
    private int HULL = 2;
    private int FIREPOWER = 2;
    private int SPEED = 3;
    private int RANGE = 3;
    
    public DeltaShip() {
        this.initializeName(NAME);
        this.initializeOwner(OWNER);
        this.initializeHull(HULL);
        this.initializeFirepower(FIREPOWER);
        this.initializeSpeed(SPEED);
        this.initializeRange(RANGE);
    }
    
    /**
     * Determines what actions the ship will take on a given turn
     * @param arena (Arena) the battlefield for the match
     * @return void
     */
    @Override
    public void doTurn(Arena arena) {
        List<Ship> ships = arena.getNearbyEnemies(this);
        double minStrength = Double.POSITIVE_INFINITY;
        Ship target = null;
        for (Ship ship : ships) {
            double strength = ship.getHull();
            if (strength < minStrength) {
                minStrength = strength;
                target = ship;
            }
        }
        if (target != null) {
            Coord coord = target.getCoord();
            for (int f = 0; f < this.getFirepower(); f++) {
                arena.fire(this, coord.getX(), coord.getY());
            }
        }
        for (int m = 0; m < this.getSpeed(); m++) {
            arena.move(this, Direction.NORTH);
        }
    }
    
}