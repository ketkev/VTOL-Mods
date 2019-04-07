package modInstaller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private ArrayList<Mod> mods = new ArrayList<>();
    private String gameVersion = "";

    public Game() {
        if(!new File(getGamePath() + "\\mods").exists()) {
            new File(getGamePath() + "\\mods").mkdirs();
        }

        findMods();
        findGameVersion();
    }

    public static String getGamePath() {
        String gamePath = (new File("")).getAbsolutePath();
        return gamePath;
    }

    public ArrayList<Mod> getMods() {
        return mods;
    }

    public void findMods() {
        mods = new ArrayList<>();
        File dir = new File(getGamePath() + "\\mods");

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
