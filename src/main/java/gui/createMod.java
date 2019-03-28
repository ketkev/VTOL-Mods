package gui;

import mod.Mod;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class createMod extends JDialog implements ActionListener {
    JLabel jlOriginal, jlModded;
    JButton jbOriginal, jbModded, jbCreate;
    File fOriginal, fModded;

    public createMod(JFrame frame) {
        super(frame, true);
        setTitle("Create mod");
        setSize(320, 140);
        setLayout(new FlowLayout());

        jlOriginal = new JLabel("Select the original dll: ");
        jbOriginal = new JButton("Select file...");
        jbOriginal.addActionListener(this);
        add(jlOriginal);
        add(jbOriginal);

        jlModded = new JLabel("Select the modded dll: ");
        jbModded = new JButton("Select file...");
        jbModded.addActionListener(this);
        add(jlModded);
        add(jbModded);

        jbCreate = new JButton("Create mod");
        jbCreate.addActionListener(this);
        add(jbCreate);

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
        if(e.getSource() == jbModded) {
            filechooser fcModded = new filechooser(this);
            fModded = fcModded.getFile();
            if(fModded != null) {
                jbModded.setText(fModded.getName());
            }
        }
        if(e.getSource() == jbCreate) {
            if(fModded != null && fOriginal != null) {
                System.out.println("creating mod");
                Mod.generateMod(fOriginal, fModded);
                System.out.println("Created mod");
                setVisible(false);
            }
        }
    }
}
