package games;
import core.*;
import ships.*;

public class MissionRunner {
    
    public static void main(String[] args) {
        
        /*if (args.length >= 1) {
            if (args[0].equals("-d")) {
                DEBUG_MODE = true;   
            }
        }*/
        
        try {
            int n = Integer.parseInt(args[0]);
            String className = args[1];
            Class<?> c = Class.forName(className);
            @SuppressWarnings("unchecked")
            Class<? extends Ship> playerClass = (Class<? extends Ship>) c;
            Game mission = null;
            switch (n) {
                case 1:
                    mission = new Mission01(playerClass);
                    break;
                case 2:
                    mission = new Mission02(playerClass);
                    break;
                default:
                    System.out.println("Could not find Mission  #" + n + ".");
            }
            if (mission != null) {
                mission.run();
                Helper.closeAllFiles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}