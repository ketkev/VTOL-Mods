package mod;

import jbdiff.JBDiff;
import jbdiff.JBPatch;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Mod {
    public static void generateMod(File original, File modded) {
        File diff = new File(new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\mod.mod");
        System.out.println(diff.getPath());
        try {
            diff.createNewFile();
            JBDiff.bsdiff(original, modded, diff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void installMod(File original, File diff) {
        String newfilePath = original.getPath().substring(0, original.getPath().length() - 4) + "-unmodded.dll";
        File unModdedCopy = new File(newfilePath);
        try {
            FileUtils.copyFile(original, unModdedCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(newfilePath);

        File newfile = new File(original.getPath());

        try {
            newfile.createNewFile();
            JBPatch.bspatch(original, newfile, diff);
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
    }
}
