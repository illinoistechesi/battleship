package battleship.core;

public enum Direction {
    
    NORTH("North"),
    SOUTH("South"),
    WEST("West"),
    EAST("East");
    
    private String name;
    
    Direction(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}