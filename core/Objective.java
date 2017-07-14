package core;

public class Objective {
    
    public Objective() {
        
    }
    
    public String getObjective() {
        return "Game Mode: Free play.";
    }
    
    public boolean isMet(Arena arena) {
        return false;
    }
    
    public String getResults(Arena arena) {
        return "No results to display.";
    }
    
}