package modInstaller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class Game {
    private ArrayList<Mod> mods = new ArrayList<>();
    private String gameVersion = "";
    
    public static String GAME_PATH;

    public Game() {
    	GAME_PATH = getGamePath(); //System.Exit
        if(!new File(GAME_PATH + "\\mods").exists()) {
            new File(GAME_PATH + "\\mods").mkdirs();
        }

        findMods();
        findGameVersion();
    }

    public static String getGamePath() {
    	if(GAME_PATH!=null) {
    		return GAME_PATH;
    	}
    	File gamePath = selectGameDirectory();
    	if(gamePath==null) {
    		System.exit(1);
    		return null; //won't happen but papers have to be "in order"
    	}else {
    		return gamePath.getAbsolutePath();
    	}
        //String gamePath = (new File("")).getAbsolutePath();
        
    }
    
    private static File selectGameDirectory() {
    	JFileChooser dirOpener = new JFileChooser();
    	dirOpener.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	int reply = dirOpener.showOpenDialog(null); //We don't have a parent frame at the moment
    	if(reply==JFileChooser.APPROVE_OPTION) {
    		return dirOpener.getCurrentDirectory();
    	}else {
    		return null;
    	}
    }

    public ArrayList<Mod> getMods() {
        return mods;
    }

    public void findMods() {
        mods = new ArrayList<>();
        File dir = new File(GAME_PATH + "\\mods");

        File[] matches = dir.listFiles((dir1, name) -> name.endsWith(".mod"));

        for(File mod : matches) {
            mods.add(new Mod(mod.getName().substring(0, mod.getName().length() - 4)));
        }
    }

    public void findGameVersion() {
        List<String> output_log = null;
        try {
            output_log = Files.readAllLines(Paths.get(System.getenv("APPDATA").substring(0, System.getenv("APPDATA").length()-7) + "LocalLow\\Boundless Dynamics, LLC\\VTOL VR\\output_log.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < output_log.size(); i++) {
            try {
                if (output_log.get(i).substring(0, 14).equals("Game version: ")) {
                    setGameVersion(output_log.get(i));
                    break;
                }
            }
            catch(Exception ex) {
            }
        }
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }
}
