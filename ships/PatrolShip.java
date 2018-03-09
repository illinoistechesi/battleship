package battleship.ships;
import battleship.core.*;
import java.util.List;

public class PatrolShip extends Ship {
    
    public PatrolShip() {
        this.initializeName("Patrol Ship");
        this.initializeOwner("The Evil Fleet");
        this.initializeHull(6);
        this.initializeFirepower(1);
        this.initializeSpeed(1);
        this.initializeRange(2);
    }
    
    @Override
    protected void doTurn(Arena arena) {
        this.move(arena, Direction.WEST);
        List<Ship> nearby = this.getNearbyShips(arena);
        if (nearby.size() > 0) {
            Ship target = nearby.get(0);
            Coord coord = target.getCoord();
            this.fire(arena, coord.getX(), coord.getY());
        }
    }
    
}