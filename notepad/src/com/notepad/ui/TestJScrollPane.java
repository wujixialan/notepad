package com.notepad.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by root on 17-1-16.
 */
public class TestJScrollPane extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JPanel panel, outPanel;
    public TestJScrollPane() {
        textArea = new JTextArea();
        textArea.setFont(new Font("宋体", Font.ITALIC, 16));
        textArea.setPreferredSize(new Dimension(1300, 800));
        scrollPane = new JScrollPane(textArea);
        panel = (JPanel) this.getContentPane();
        outPanel = new JPanel();
        outPanel.add(scrollPane);
        textArea.setVisible(true);
        outPanel.add(scrollPane);
        this.panel.add(outPanel);
        this.setLocation(0, 0);
        this.setSize(300, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TestJScrollPane();
    }
}
