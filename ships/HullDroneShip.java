package battleship.ships;
import battleship.core.*;
import java.util.List;

/*
 * HullDroneShip
 * @author TA
 */
public class HullDroneShip extends Ship {
    
    public HullDroneShip() {
        this.initializeName("Health Drone");
        this.initializeOwner("TA");
        this.initializeHull(5);
        this.initializeFirepower(1);
        this.initializeSpeed(1);
        this.initializeRange(1);
    }
    
    /*
     * Determines what actions the ship will take on a given turn
     * @param arena (Arena) the battlefield for the match
     * @return void
     */
    @Override
    protected void doTurn(Arena arena) {
        Coord location = this.getCoord();
        this.move(arena, Direction.WEST);

        List<Ship> nearby = this.getNearbyShips(arena);

        // loop through the list of nearby ships
        for (int i = 0; i < nearby.size(); i++) {
            if ( this.isSameTeamAs(nearby.get(i)) ) {
                // if same team, don't shoot
            }
            else {
                Ship enemy = nearby.get(i);
                Coord enemyLoc = enemy.getCoord();
                int x = enemyLoc.getX();
                int y = enemyLoc.getY();
                this.fire(arena, x, y);
            }
        }
    }
    
}