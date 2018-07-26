package battleship.ships;
import battleship.core.*;
import java.util.List;

/*
 * DroneShip
 * @author TA
 */
public class GunDroneShip extends Ship {

    private int waitToMove;
    
    public GunDroneShip() {
        this.initializeName("Gun Drone");
        this.initializeOwner("TA");
        this.initializeHull(1);
        this.initializeFirepower(4);
        this.initializeSpeed(1);
        this.initializeRange(2);
        waitToMove = 2;
    }
    
    /*
     * Determines what actions the ship will take on a given turn
     * @param arena (Arena) the battlefield for the match
     * @return void
     */
    @Override
    protected void doTurn(Arena arena) {
        if (waitToMove > 0) {
            waitToMove = waitToMove - 1;
            // do nothing
        }
        else {
            Coord location = this.getCoord();
            this.move(arena, Direction.WEST);

            // Get a list of nearby ships
            List<Ship> nearby = this.getNearbyShips(arena);
            // Get the number of shots left on this ship
            int numShots = this.getRemainingShots();

            for (int i = 0; i < nearby.size(); i++) {
                if ( this.isSameTeamAs(nearby.get(i)) ) {
                // if same team, don't shoot
                } else {
                    Ship enemy = nearby.get(i);
                    while(enemy.getHealth() > 0 && numShots > 0) {
                        Coord enemyLoc = enemy.getCoord();
                        int x = enemyLoc.getX();
                        int y = enemyLoc.getY();
                        this.fire(arena, x, y);
                        numShots = this.getRemainingShots();
                    }
                    // if we get here, that means a ship has been sunk
                    // call this function again to update the list to not shoot at a sunk ship
                    nearby = this.getNearbyShips(arena);
                }
            }
        }
    }
    
}