package games;
import core.*;
import ships.*;

public class MissionRunner {
    
    public static Class<? extends Ship> PLAYER_CLASS = ships.CustomShip.class;
    
    public static void main(String[] args) {
        
        try {
            
            int n = Integer.parseInt(args[0]);
            Game mission = null;
            if (args.length >= 2) {
                String className = args[1];
                Class<?> c = Class.forName(className);
                @SuppressWarnings("unchecked")
                Class<? extends Ship> playerClass = (Class<? extends Ship>) c;
                mission = getMission(n, playerClass);
            } else {
                mission = getMission(n, PLAYER_CLASS);
            }
            if (mission != null) {
                mission.setSeed(42);
                mission.run();
                
            } else {
                System.out.println("Could not find Mission #" + n);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static Game getMission(int n, Class<? extends Ship> pc) {
        switch (n) {
            case 1:
                return new Mission01(pc);
            case 2:
                return new Mission02(pc);
            default:
                return null;
        }
    }
    
}