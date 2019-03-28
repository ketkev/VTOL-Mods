package gui;

import mod.Mod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class installMod extends JDialog implements ActionListener {
    JLabel jlOriginal, jlMod;
    JButton jbOriginal, jbMod, jbInstall;
    File fOriginal, fMod;

    public installMod(JFrame frame) {
        super(frame, true);
        setTitle("Install mod");
        setSize(275, 140);
        setLayout(new FlowLayout());

        jlOriginal = new JLabel("Select the original dll: ");
        jbOriginal = new JButton("Select file...");
        jbOriginal.addActionListener(this);
        add(jlOriginal);
        add(jbOriginal);

        jlMod = new JLabel("Select the .mod: ");
        jbMod = new JButton("Select file...");
        jbMod.addActionListener(this);
        add(jlMod);
        add(jbMod);

        jbInstall = new JButton("Install mod");
        jbInstall.addActionListener(this);
        add(jbInstall);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbOriginal) {
            filechooser fcOriginal = new filechooser(this);
            fOriginal = fcOriginal.getFile();
            if(fOriginal != null) {
                jbOriginal.setText(fOriginal.getName());
            }
        }
        if(e.getSource() == jbMod) {
            filechooser fcMod = new filechooser(this);
            fMod = fcMod.getFile();
            if(fMod != null) {
                jbMod.setText(fMod.getName());
            }
        }
        if(e.getSource() == jbInstall) {
            if(fMod != null && fOriginal != null) {
                System.out.println("installing mod");
                Mod.installMod(fOriginal, fMod);
                System.out.println("Installed mod");
                setVisible(false);
            }
        }
    }
}
