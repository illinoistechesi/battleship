package samples;

// BetaShip will be design as a speedy/scouting ship
public class BetaShip extends Ship {
    
    
    public BetaShip(int x, int y, int health, int speed, int visibility, int gunRange) {
        super(x, y, health, speed, visibility, gunRange);
    }
    
    public String action(Theater sea) {
        List<Ship> enemies = sea.getVisibleEnemies(this);
        List<Ship> allies = sea.getAllies();
        if (enemies.size() == 0) {
            // scout move closer
        }
        else if (enemies.size() == 1) { // if one enemy is spotted by a scouting ship
            int readiness = 0;
            for (Ship ally : allies) {
                List<Ship> allyVision = sea.getVisibleEnemies(ally);
                for (int i = 0; i < allyVision.size(); i++) {
                    // if ally also sees or in firing range of enemy ship, increment readiness
                }
            }
            
            if (readiness > allies.size() / 2) { // if more than half the ship can fire using the scout's large field of vision advantage
                // scout ship action = fire at enemy
            }
            else {
                // scout ship move back until fleet catches up
            }
        }
        else if (enemies.size() > 1) { // scout ship should not engage in multiple ships
            // scout move back
        }
    }
    
    
}