package battleship.core;
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Helper {
    
    private static Map<String, Integer> printRecords = new HashMap<String, Integer>();
    public static void printlnLimitTo(String key, String content, int limit){
        if(!printRecords.containsKey(key)){
            printRecords.put(key, 0);
        }
        if(printRecords.get(key) < limit){
            System.out.println(content);
            int count = printRecords.get(key);
                count++;
            printRecords.put(key, count);
        }
    }
    
    private static class FileRecord {
        
        private String filename;
        private FileWriter fw = null;
        private BufferedWriter bw = null;
        private FileReader fr = null;
        private BufferedReader br = null;
        
        public FileRecord(String filename){
            this.filename = filename;
        }
        
        public boolean isOpenForWriting(){
            return this.bw != null;
        }
        
        public boolean isOpenForReading(){
            return this.br != null;
        }
        
        public void openForWriting(){
            try{
                this.fw = new FileWriter(filename);
                this.bw = new BufferedWriter(this.fw);
            }
            catch(IOException e){
                System.out.println("Error in openForWriting()");
                e.printStackTrace();
            }
        }
        
        public void openForReading(){
            try{
                this.fr = new FileReader(filename);
                this.br = new BufferedReader(this.fr);
            }
            catch(IOException e){
                System.out.println("Error in openForReading()");
                e.printStackTrace();
            }
        }
        
        public void writeFileLine(String content){
            try{
                bw.write(content + "\n");
            }
            catch(IOException e){
                System.out.println("Error in writeFileLine()");
                e.printStackTrace();
            }
        }
        
        public String readEntireFile(){
            String out = "";
            StringBuilder sb = new StringBuilder();
            try{
                String line = null;
                boolean reading = true;
                while(reading){
                    line = br.readLine();
                    if(line == null){
                        reading = false;
                    }
                    else{
                        //out += line;
                        sb.append(line);
                    }
                }
                out = sb.toString();
            }
            catch(IOException e){
                System.out.println("Error in readEntireFile()");
                e.printStackTrace();
            }
            return out;
        }
        
        public List<String> readFileLines(){
            List<String> out = new ArrayList<String>();
            try{
                String line = null;
                boolean reading = true;
                while(reading){
                    line = br.readLine();
                    if(line == null){
                        reading = false;
                    }
                    else{
                        out.add(line);
                    }
                }
            }
            catch(IOException e){
                System.out.println("Error in readFileLines()");
                e.printStackTrace();
            }
            return out;
        }
        
        public void closeFile(){
            try{
                if(bw != null){
                    bw.close();
                }
                if(fw != null){
                    fw.close();
                }
                if(br != null){
                    br.close();
                }
                if(fr != null){
                    fr.close();
                }
            }
            catch(IOException e){
                System.out.println("Error in closeFile()");
                e.printStackTrace();
            }
        }
        
    }
    
    private static boolean addedShutdownHook = false;
    private static boolean allowWrites = true;
    private static HashMap<String, FileRecord> fileMap = new HashMap<String, FileRecord>();
    
    public static void writeFileLine(String filename, String content){
        if(allowWrites){
            if(!fileMap.containsKey(filename)){
                fileMap.put(filename, new FileRecord(filename));
            }
            FileRecord rec = fileMap.get(filename);
            if(!rec.isOpenForWriting()){
               rec.openForWriting(); 
            }
            rec.writeFileLine(content);
        }
        if (!Helper.addedShutdownHook) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    //System.out.println("JVM shutting down, closing files.");
                    Helper.closeAllFiles();
                }
            }));
            Helper.addedShutdownHook = true;
        }
    }
    
    public static String readEntireFile(String filename){
        if(!fileMap.containsKey(filename)){
            fileMap.put(filename, new FileRecord(filename));
        }
        FileRecord rec = fileMap.get(filename);
        if(!rec.isOpenForReading()){
            rec.openForReading();
        }
        String content = rec.readEntireFile();
        return content;
    }
    
    public static List<String> readFileLines(String filename){
        if(!fileMap.containsKey(filename)){
            fileMap.put(filename, new FileRecord(filename));
        }
        FileRecord rec = fileMap.get(filename);
        if(!rec.isOpenForReading()){
            rec.openForReading();
        }
        List<String> content = rec.readFileLines();
        return content;
    }
    
    public static void closeAllFiles(){
        for(Map.Entry<String, FileRecord> entry : fileMap.entrySet()){
            entry.getValue().closeFile();
        }
        fileMap.clear();
    }

    public static void disableWrites(){
        allowWrites = false;
    }
    
    public static void enableWrites(){
        allowWrites = true;
    }
    
    public static List<String[]> readCSV(String filename) {
        List<String[]> data = new ArrayList<String[]>();
        List<String> input = Helper.readFileLines(filename);
        for(String str : input){
            String[] line = str.split(Pattern.quote(","));
            data.add(line);
        }
        return data;
    }

}