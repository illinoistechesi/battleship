package battleship.core;

public class Spawn {
    
    private Class<? extends Ship> shipClass;
    private String team;
    private int x;
    private int y;
    
    public Spawn(Class<? extends Ship> shipClass, String team) {
        this.shipClass = shipClass;
        this.team = team;
    }
    
    public Spawn(Class<? extends Ship> shipClass, String team, int x, int y) {
        this.shipClass = shipClass;
        this.team = team;
        this.x = x;
        this.y = y;
    }
    
    public Class<? extends Ship> getShipClass() {
        return this.shipClass;
    }
    
    public String getTeam() {
        return this.team;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
}