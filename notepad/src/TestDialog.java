import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.*;

/**
 * Created by root on 17-1-15.
 */
public class TestDialog extends JDialog {
    private JLabel label = new JLabel("姓名"), label1 = new JLabel("密码");
    private JButton button = new JButton("ok");
    private JPanel panel, outPanel = null;
    public TestDialog() {
        panel = (JPanel) this.getContentPane();
        outPanel = new JPanel();
        label.setPreferredSize(new Dimension(100, 20));
        label1.setPreferredSize(new Dimension(100, 20));
        button.setPreferredSize(new Dimension(100, 20));
        Box b1 = Box.createVerticalBox();
        b1.add(label);
        b1.add(label1);
        b1.add(button);
        outPanel.add(b1);

        this.panel.add(outPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(300, 200));
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new TestDialog();
    }
}