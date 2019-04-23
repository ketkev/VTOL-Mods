package gui;

import modInstaller.Mod;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import net.miginfocom.swing.MigLayout;

public class createMod extends JDialog implements ActionListener {

  String username, description, modName;
  JLabel jlOriginal, jlModded, jlUsername, jlDescription, jlModName;
  JTextField jtfUsername, jtfDescription, jtfModName;
  JButton jbOriginal, jbModded, jbCreate;
  File fOriginal, fModded;

  public createMod(JFrame frame) {
    super(frame, true);
    setTitle("Create mod");
    setSize(500, 140);
    setLayout(new MigLayout());

    jlOriginal = new JLabel("Select the original dll: ");
    jbOriginal = new JButton("Select file...");
    ToolTipManager.sharedInstance().setInitialDelay(0);
    jbOriginal.setToolTipText("Make sure the original dll still has it's original name");
    jbOriginal.addActionListener(this);
    add(jlOriginal, "cell 0 0");
    add(jbOriginal, "cell 1 0");

    jlModded = new JLabel("Select the modded dll: ");
    jbModded = new JButton("Select file...");
    jbModded.addActionListener(this);
    add(jlModded, "cell 0 1");
    add(jbModded, "cell 1 1");

    jbCreate = new JButton("Create mod");
    jbCreate.addActionListener(this);
    add(jbCreate, "cell 1 2");

    jlModName = new JLabel("Enter the mod's name: ");
    jtfModName = new JTextField(10);
    add(jlModName, "cell 2 0");
    add(jtfModName, "cell 3 0");

    jlUsername = new JLabel("Your username: ");
    jtfUsername = new JTextField(10);
    add(jlUsername, "cell 2 1");
    add(jtfUsername, "cell 3 1");

    jlDescription = new JLabel("Enter a description:");
    jtfDescription = new JTextField(10);
    add(jlDescription, "cell 2 2");
    add(jtfDescription, "cell 3 2");

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == jbOriginal) {
      filechooser fcOriginal = new filechooser(this);
      fOriginal = fcOriginal.getFile();
      if (fOriginal != null) {
        jbOriginal.setText(fOriginal.getName());
      }
    }
    if (e.getSource() == jbModded) {
      filechooser fcModded = new filechooser(this);
      fModded = fcModded.getFile();
      if (fModded != null) {
        jbModded.setText(fModded.getName());
      }
    }
    if (e.getSource() == jbCreate) {
      if (fModded != null && fOriginal != null && !jtfModName.getText().equals("") && !jtfDescription
          .getText().equals("") && !jtfUsername.getText()
          .equals("")) { //TODO this can be done better
        username = jtfUsername.getText();
        modName = jtfModName.getText();
        description = jtfDescription.getText();
        System.out.println("creating mod");
        Mod.generateMod(fOriginal, fModded, username, description, modName);
        System.out.println("Created mod");
        setVisible(false);
      }
    }
  }
}
