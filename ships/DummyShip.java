package battleship.ships;
import battleship.core.*;
import java.util.List;

/*
 * Dummy Ship
 * @author The Evil Fleet
 */
public class DummyShip extends Ship {
    
    public DummyShip() {
        this.initializeName("Dummy Ship");
        this.initializeOwner("The Evil Fleet");
        this.initializeHull(7);
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
        // Fill in your strategy here
    }
    
}