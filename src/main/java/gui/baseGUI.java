package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class baseGUI extends JFrame implements ActionListener {
    JButton jbInstall, jbCreate;

    public baseGUI() {
        setTitle("VTOL mods");
        setSize(300, 75);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jbInstall = new JButton("Install a mod");
        jbInstall.addActionListener(this);
        add(jbInstall);

        jbCreate = new JButton("Create a new mod");
        jbCreate.addActionListener(this);
        add(jbCreate);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbCreate) {
            createMod createModWindow = new createMod(this);
        }
        if(e.getSource() == jbInstall) {
            installMod installModWindow = new installMod(this);
        }
    }
}
