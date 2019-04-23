package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import modInstaller.Game;
import modInstaller.Mod;
import net.miginfocom.swing.MigLayout;

public class baseGUI extends JFrame implements ActionListener {
    JButton jbInstall, jbCreate, jbUninstall;
    JComboBox jcbModSelector;
    JLabel jlDescription, jlCreator, jlInstalled, jlDifferentVersion;
    private ArrayList<Mod> mods;
    private Game game;
    Mod selectedMod = null;

    public baseGUI(Game game) {
        this.game = game;
        setTitle("VTOL mods");
        setSize(500, 175);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
        setLayout(new MigLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mods = game.getMods();

        jcbModSelector = new JComboBox(new DefaultComboBoxModel(game.getMods().toArray()));
        jcbModSelector.addActionListener(this);

        jlInstalled = new JLabel("Installed successfully!");

        jbInstall = new JButton("Install");
        jbInstall.addActionListener(this);

        jbUninstall = new JButton("Uninstall mods");
        jbUninstall.addActionListener(this);

        jbCreate = new JButton("Create a new mod");
        jbCreate.addActionListener(this);

        jlDescription = new JLabel("Description: ");

        jlCreator = new JLabel("Creator: ");

        jlDifferentVersion = new JLabel("<html>This mod was made for<br> different game version, it<br> might not work</html>");
        jlDifferentVersion.setForeground(Color.red);

        add(jcbModSelector, "cell 0 0");
        add(jbInstall, "cell 1 0");
        add(jbUninstall, "cell 1 1");
        add(jbCreate, "cell 1 2");
        jbCreate.setEnabled(false);
        add(jlDescription, "cell 2 0");
        add(jlCreator, "cell 2 1");
        add(jlInstalled, "cell 0 1");
        jlInstalled.setVisible(false);

        setVisible(true);

        changeSelected();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbCreate) {
            createMod createModWindow = new createMod(this);
        }
        if (e.getSource() == jbInstall) {
            Mod.installMod(new File(Game.GAME_PATH + "\\" +  selectedMod.getInstallTo()), new File(Game.GAME_PATH + "\\mods\\" + selectedMod.getName() + ".mod"));
            jlInstalled.setVisible(true);
            jlInstalled.setText("Installed successfully!");

        }
        if (e.getSource() == jbUninstall) {
            Mod.uninstallMod(selectedMod.getInstallTo());
            jlInstalled.setVisible(true);
            jlInstalled.setText("Uninstalled successfully!");
        }
        if (e.getSource() == jcbModSelector) {
            changeSelected();
        }
    }

    private void changeSelected() {
        String selected = jcbModSelector.getSelectedItem()!=null ? jcbModSelector.getSelectedItem().toString() : null;
        if(selected==null) return;
        for (Mod mod : mods) {
            if (mod.getName().equals(selected)) {
                selectedMod = mod;
                break;
            }
        }

        jlDescription.setText("<html>Description: <br>" + selectedMod.getDescription() + "</html>");
        jlCreator.setText("<html>Creator: <br>" + selectedMod.getCreator() + "</html>");

        if (!selectedMod.getIntendedVersion().equals(game.getGameVersion()) && !jlDifferentVersion.isShowing()) {
            add(jlDifferentVersion, "cell 0 2");
        }
        if (selectedMod.getIntendedVersion().equals(game.getGameVersion()) && jlDifferentVersion.isShowing()) {
            remove(jlDifferentVersion);
            this.repaint();
        }
    }
}
