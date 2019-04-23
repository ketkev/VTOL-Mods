package modInstaller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import jbdiff.JBDiff;
import jbdiff.JBPatch;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Mod {

  private String name, installTo, description, creator, intendedVersion;

  public Mod(String name) {
    this.name = name;
    try {
      List<String> cfg = Files
          .readAllLines(Paths.get(Game.getGamePath() + "\\mods\\" + name + ".cfg"));
      installTo = cfg.get(0);
      description = cfg.get(1);
      creator = cfg.get(2);
      intendedVersion = cfg.get(3);
    } catch (Exception ex) {
      System.out.println(ex.getLocalizedMessage());
    }
  }

  public static void generateMod(File original, File modded, String username, String description,
      String modName) { //TODO add new functionality and cfg generation
    Game game = new Game();
    File cfgFile = new File(Game.getGamePath() + "\\mods\\" + modName + ".cfg");
    try {
      cfgFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    PrintWriter cfg = null;
    try {
      cfg = new PrintWriter(cfgFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    String originalPath = original.getPath().substring(Game.getGamePath().length() + 1);
    System.out.println(originalPath);

    cfg.println(originalPath);
    cfg.println(description);
    cfg.println(username);
    cfg.println(game.getGameVersion());

    cfg.close();
    File diff = new File(Game.getGamePath() + "\\mods\\" + modName + ".mod");
    System.out.println(diff.getPath());
    try {
      diff.createNewFile();
      JBDiff.bsdiff(original, modded, diff);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void uninstallMod(String uninstallFrom) {
    try {
      (new File(Game.getGamePath() + "\\" + uninstallFrom)).delete();
      Files.move(Paths.get(
          Game.getGamePath() + "\\" + uninstallFrom.substring(0, uninstallFrom.length() - 4)
              + "-backup.dll"), Paths.get(
          Game.getGamePath() + "\\" + uninstallFrom)); //TODO rewrite this mess of a line
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void installMod(File original, File diff) {
    String newfilePath =
        original.getPath().substring(0, original.getPath().length() - 4) + "-backup.dll";
    File unModdedCopy = new File(newfilePath);
    if (!(new File(newfilePath)).exists()) {
      try {
        FileUtils.copyFile(original, unModdedCopy);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    File newfile = new File(original.getPath());

    try {
      newfile.createNewFile();
      JBPatch.bspatch(unModdedCopy, newfile, diff);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public String getName() {
    return name;
  }

  public String getInstallTo() {
    return installTo;
  }

  public String getDescription() {
    return description;
  }

  public String getCreator() {
    return creator;
  }

  public String getIntendedVersion() {
    return intendedVersion;
  }

  @Override
  public String toString() {
    return name;
  }
}
