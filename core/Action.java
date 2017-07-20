package battleship.core;
    
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
    private String attacker;
    
    public Action(Ship ship, int turn, Direction dir) {
        this.type = "MOVE";
        this.direction = dir + "";
        this.turn = turn;
        this.id = ship + "";
        this.x = ship.getCoord().getX();
        this.y = ship.getCoord().getY();
        this.health = ship.getHealth();
    }
    
    public Action(Ship target, int turn, Ship attacker) {
        this.type = "SINK";
        this.turn = turn;
        this.id = target + "";
        this.x = target.getCoord().getX();
        this.y = target.getCoord().getY();
        this.health = target.getHealth();
        this.attacker = attacker + "";
    }
    
    public Action(Ship target, int turn) {
        this.type = "HIT";
        this.turn = turn;
        this.id = target + "";
        this.x = target.getCoord().getX();
        this.y = target.getCoord().getY();
        this.health = target.getHealth();
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
    
    public String getAttacker() {
        return this.attacker;
    }
    
}