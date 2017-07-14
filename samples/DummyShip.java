package samples;
import core.*;
import java.util.List;

/*
 * Dummy Ship
 * @author Vinesh Kannan
 */
public class DummyShip extends Ship {
    
    private String NAME = "Dummy Ship";
    private String OWNER = "Vinesh";
    private int HULL = 7;
    private int FIREPOWER = 1;
    private int SPEED = 1;
    private int RANGE = 1;
    
    public DummyShip() {
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
        // Does nothing
    }
    
}