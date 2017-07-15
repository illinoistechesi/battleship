package core;
    
public class Action {
    
    private String type;
    private int turn;
    private String id;
    private int x;
    private int y;
    private int health;
    private String direction;
    private int atX;
    private int atY;
    
    public Action(Ship ship, int turn, Direction dir) {
        this.type = "MOVE";
        this.direction = dir + "";
        this.turn = turn;
        this.id = ship + "";
        this.x = ship.getCoord().getX();
        this.y = ship.getCoord().getY();
        this.health = ship.getHealth();
    }
    
    public Action(Ship ship, int turn, int x, int y) {
        this.type = "FIRE";
        this.atX = x;
        this.atY = y;
        this.turn = turn;
        this.id = ship + "";
        this.x = ship.getCoord().getX();
        this.y = ship.getCoord().getY();
        this.health = ship.getHealth();
    }
    
    public String getType() {
        return this.type;
    }
    
    public int getTurn() {
        return this.turn;
    }
    
    public String getID() {
        return this.id;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public String getDirection() {
        return this.direction;
    }
    
    public int getAtX() {
        return this.atX;
    }
    
    public int getAtY() {
        return this.atY;
    }
    
}