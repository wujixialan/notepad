package com.notepad.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by root on 17-1-16.
 */
public class TestJPoppuMenu extends JFrame  {
    private JPopupMenu popupMenu;
    private JMenuItem copy;
    private JFrame jf;
    public TestJPoppuMenu(){

    }
    public TestJPoppuMenu(JMenuItem copy, JMenuItem paste, JMenuItem cut) {
        final JFrame frame = new JFrame("窗体");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add("黄色");
        popupMenu.add("宏色");
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(frame, e.getX(), e.getY());
                }
            }
        });
        frame.setVisible(true);
    }
//    public static void main(String[] args) {
//
//        new com.notepad.ui.TestJPoppuMenu();
//    }
}
