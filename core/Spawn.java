package battleship.core;

public class Spawn {
    
    private Class<? extends Ship> shipClass;
    private String team;
    
    public Spawn(Class<? extends Ship> shipClass, String team) {
        this.shipClass = shipClass;
        this.team = team;
    }
    
    public Class<? extends Ship> getShipClass() {
        return this.shipClass;
    }
    
    public String getTeam() {
        return this.team;
    }
    
}