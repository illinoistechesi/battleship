package battleship.ships;
import battleship.core.*;
import java.util.List;
import java.util.ArrayList;

public class DroneShip extends Ship {
    
    public DroneShip() {
        this.initializeName("Drone Ship");
        this.initializeOwner("The Evil Fleet");
        this.initializeHull(2);
        this.initializeFirepower(2);
        this.initializeSpeed(3);
        this.initializeRange(3);
    }
    
    @Override
    protected void doTurn(Arena arena) {
        List<Ship> enemies = this.getNearbyEnemies(arena);
        for (int m = 0; m < this.getSpeed(); m++) {
            if (enemies.size() > 0) {
                int rand = arena.getRandom().nextInt(enemies.size());
                Ship target = enemies.get(rand);
                if (!this.isSameTeamAs(target)) {
                    Coord coord = target.getCoord();
                    this.fire(arena, coord.getX(), coord.getY());
                }   
            }
            this.move(arena, this.getRandomDirection(arena));
            enemies = this.getNearbyEnemies(arena);
        }
    }
    
    public static Direction[] directions = {
        Direction.NORTH,
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST
    };
    
    public Direction getRandomDirection(Arena arena) {
        int index = arena.getRandom().nextInt(this.directions.length);
        Direction dir = this.directions[index];
        return dir;
    }
    
    public List<Ship> getNearbyEnemies(Arena arena) {
        List<Ship> nearbyEnemies = new ArrayList<Ship>();
        for (Ship ship : this.getNearbyShips(arena)) {
            if (!ship.isSameTeamAs(this)) {
                nearbyEnemies.add(ship);
            }
        }
        return nearbyEnemies;
    }
    
}