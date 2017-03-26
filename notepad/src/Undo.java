import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by root on 17-1-15.
 */
public class Undo extends JFrame {
     JTextArea text = new JTextArea();
     JPanel pnl = new JPanel(), panel = new JPanel();
     JButton unbtn = new JButton("撤销");
     JButton rebtn = new JButton("恢复");
     UndoManager undoManager = new UndoManager();

    public Undo() {
        text.getDocument().addUndoableEditListener(undoManager);
        unbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()) {
                    undoManager.undo();
                } else {
                    System.out.println("不能撤销");
                }
            }
        });

        rebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()) {
                    undoManager.redo();
                } else {
                    System.out.println("不能撤销");
                }
            }
        });
//        pnl.setLayout(new FlowLayout(5));
        pnl.add(unbtn);
        pnl.add(rebtn);
        this.add(pnl, BorderLayout.NORTH);
        panel.add(text);
        this.setLayout(new BorderLayout(5, 5));
        this.add(panel, BorderLayout.CENTER);


        this.setTitle("d大调");
        this.setLocation(0, 0);
        this.setSize(300, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Undo();
    }
}

