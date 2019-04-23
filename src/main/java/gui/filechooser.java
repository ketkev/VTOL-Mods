package gui;

import modInstaller.Game;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class filechooser extends JDialog implements ActionListener {

  JFileChooser fc;
  File file;

  public filechooser(JFrame frame) {
    super(frame, true);
    setTitle("Choose file");
    setSize(800, 600);
    setLayout(new FlowLayout());

    fc = new JFileChooser();
    fc.addActionListener(this);
    add(fc);
    fc.setPreferredSize(new Dimension(780, 550));

    setVisible(true);
  }

  public filechooser(JDialog frame) {
    super(frame, true);
    setTitle("Choose file");
    setSize(800, 600);
    setLayout(new FlowLayout());
    try {
      fc = new JFileChooser(new File(Game.getGamePath()));
    } catch (Exception ex) {
      ex.getLocalizedMessage();
      fc = new JFileChooser();
    }
    fc.addActionListener(this);
    fc.setFileFilter(new FileFilter() {
      @Override
      public boolean accept(File f) {
        if (f.isDirectory()) {
          return true;
        }

        if (getExtension(f) != null) {
          if (getExtension(f).equals("dll") || getExtension(f).equals("mod")) {
            return true;
          } else {
            return false;
          }
        }

        return false;
      }

      @Override
      public String getDescription() {
        return null;
      }
    });
    add(fc);
    fc.setPreferredSize(new Dimension(780, 550));

    setVisible(true);
  }

  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
    }
    return ext;
  }

  public File getFile() {
    return file;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("ApproveSelection")) {
      file = fc.getSelectedFile();
      System.out.println(file);
      setVisible(false);
    }
  }
}
