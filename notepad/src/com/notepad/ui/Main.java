package com.notepad.ui;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by root on 17-1-14.
 */
public class Main extends JFrame implements ActionListener {
    private File curFileName;
    private JMenu file, edit, search, help;

    private JMenuItem newCon, open, helpCon, save, saveAs, close;
    private JMenuItem repeal, redo, cut, copy, paste, delete;
    private JMenuItem seek, replace;
    private JMenuItem about;
    private JMenuItem fontItem;

    private JMenuBar menuBar;
    private JPanel panel, outPanel, outP1;
    private JTextArea textArea;
    private KeyStroke keyNewFile, keyOpen, keySave, keySaveAs, keyRepeal, keyCut, keyCopy, keyPaste, keyDelete,
            keySeek, keyReplace;
    //    定义了一个剪贴板，并且获取系统的剪贴板
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //    存放选中的文本内容
    private StringSelection stringSelection = null;
    private UndoManager undoManager = new UndoManager();
    private Transferable contents = null;
    private DataFlavor flavor = null;
    private String selectText;
    private JScrollPane scrollPane = null;
    private JDialog dialog = null;
    private JLabel searchL = null, replaceL = null;
    private JTextField searchTF = null, replaceTF = null;
    private JButton closeButton, okButton;
    private JPopupMenu popupMenu = null;

    public Main() {
        file = new JMenu("文件");
        newCon = new JMenuItem("新建");
        textArea = new JTextArea();
        textArea.setFont(new Font("宋体", Font.BOLD, 16));
        textArea.getDocument().addUndoableEditListener(undoManager);
        textArea.setColumns(74);
        textArea.setRows(33);
        scrollPane = new JScrollPane(textArea);
        open = new JMenuItem("打开");
        save = new JMenuItem("保存");
        saveAs = new JMenuItem("另存为");
        close = new JMenuItem("关闭");

        edit = new JMenu("编辑");
        repeal = new JMenuItem("撤销");
        redo = new JMenuItem("恢复");
        cut = new JMenuItem("剪切");
        copy = new JMenuItem("复制");
        paste = new JMenuItem("粘贴");
        delete = new JMenuItem("删除");

        search = new JMenu("搜索");
        seek = new JMenuItem("查找");
        replace = new JMenuItem("替换");
        fontItem = new JMenuItem("字体");

        help = new JMenu("帮助");
        helpCon = new JMenuItem("帮助");
        about = new JMenuItem("关于");


        panel = (JPanel) this.getContentPane();
        outPanel = new JPanel();
//        添加快捷键， KeyEvent.VK_N 代表 N， InputEvent.CTRL_MASK 代表 Ctrl
//        新建的快捷键 Ctrl+N
        keyNewFile = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK);
        newCon.setAccelerator(keyNewFile);

//        打开的快捷键 Ctrl+O
        keyOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK);
        open.setAccelerator(keyOpen);

//        保存的快捷键 Ctrl + S
        keySave = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        save.setAccelerator(keySave);

//        另存为的快捷键 Ctrl + Shift + S
        keySaveAs = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK);
        saveAs.setAccelerator(keySaveAs);

//        撤销的快捷键 Ctrl + Z
        keyRepeal = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK);
        repeal.setAccelerator(keyRepeal);

//        剪切的快捷键 Ctrl + X
        keyCut = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK);
        cut.setAccelerator(keyCut);

//        复制的快捷键 Ctrl + C
        keyCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK);
        copy.setAccelerator(keyCopy);
//        粘贴的快捷键 Ctrl + V
        keyPaste = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK);
        paste.setAccelerator(keyPaste);

//        删除的快捷键 Ctrl + D
        keySeek = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK);
        seek.setAccelerator(keySeek);

//        查找的快捷键 Ctrl + F
        keyDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK);
        delete.setAccelerator(keyDelete);

//        替换的快捷键 Ctrl + H
        keyReplace = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK);
        replace.setAccelerator(keyReplace);

//        文件一栏
        newCon.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        close.addActionListener(this);

//        编辑一栏
        repeal.addActionListener(this);
        redo.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);

//        搜索一栏
        seek.addActionListener(this);
        replace.addActionListener(this);

//        帮助一栏
        helpCon.addActionListener(this);
        about.addActionListener(this);
        fontItem.addActionListener(this);

        menuBar = new JMenuBar();
        file.add(newCon);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(close);

        edit.add(repeal);
        edit.add(redo);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);

        search.add(seek);
        search.add(replace);

        help.add(helpCon);
        help.add(about);
        help.add(fontItem);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(search);
        menuBar.add(help);
        menuBar.setFont(new Font("宋体", Font.ITALIC, 16));
        outPanel.add(scrollPane);

        popupMenu = new JPopupMenu();
        popupMenu.add(copy);
        popupMenu.add(paste);
        popupMenu.add(cut);

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(outPanel, e.getX(), e.getY());
                }
            }
        });
        this.setJMenuBar(menuBar);
        this.panel.add(outPanel);
        this.setTitle("记事本");
        this.setLocation(0, 0);
        this.setSize(1193, 658);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Main();
    }


    public void openDialog() {
        File fileName = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("请打开文件");
        int returnVal = fileChooser.showOpenDialog(null);

        fileChooser.setVisible(true);

        if (JFileChooser.APPROVE_OPTION == returnVal) {
            fileName = fileChooser.getSelectedFile().getAbsoluteFile();
            this.setCurFileName(fileName);
        } else if (JFileChooser.ERROR_OPTION == returnVal) {
            JOptionPane.showMessageDialog(null, "打开错误");
        } else if (JFileChooser.CANCEL_OPTION == returnVal) {
            System.out.println("取消打开");
        }
    }

    public void remove() {
        outPanel.removeAll();
        outPanel.repaint();
    }

    public void updateUI() {
        outPanel.updateUI();
    }

    public File getCurFileName() {
        return curFileName;
    }

    public void setCurFileName(File curFileName) {
        this.curFileName = curFileName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = "";
        switch (e.getActionCommand()) {
            case "新建":
                remove();
                textArea = new JTextArea();
                textArea.setFont(new Font("宋体", Font.ITALIC, 16));
                textArea.getDocument().addUndoableEditListener(undoManager);
                textArea.setColumns(79);
                textArea.setRows(33);
                textArea.setText("");
                textArea.setVisible(true);
                scrollPane = new JScrollPane(textArea);
                outPanel.add(scrollPane);
                updateUI();
                break;
            case "打开":
                openDialog();

                readFile(this.getCurFileName(), textArea);
                break;
            case "保存":
                if (this.getCurFileName() == null) {
                    openDialog();
                }
                writeFile(this.getCurFileName(), textArea);
                break;
            case "另存为":
                openDialog();
                writeFile(this.getCurFileName(), textArea);
                break;
            case "关闭":
                remove();
                textArea.setVisible(false);
                System.exit(0);
                updateUI();
                break;
            case "撤销":
//                如果可以撤销，就执行撤销，否则部执行
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;

            case "恢复":
//                如果可以恢复，就执行恢复, 否则不执行
                try {
                    if (undoManager.canUndo()) {
                        undoManager.redo();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "剪切":
                selectText = textArea.getSelectedText();
                stringSelection = new StringSelection(selectText);
                clipboard.setContents(stringSelection, null);
                int start = textArea.getSelectionStart();
                int end = textArea.getSelectionEnd();
                textArea.replaceRange("", start, end);
                break;
            case "复制":
                selectText = textArea.getSelectedText();
                stringSelection = new StringSelection(selectText);
                clipboard.setContents(stringSelection, null);
                break;
            case "粘贴":
                pasteContents();
                break;
            case "删除":
                // TODO: 17-1-15 Exception "java.lang.ClassNotFoundException: com/intellij/codeInsight/editorActions/FoldingData"while constructing DataFlavor for: application/x-java-jvm-local-objectref; class=com.intellij.codeInsight.editorActions.FoldingData
                selectText = textArea.getSelectedText();
                stringSelection = new StringSelection(selectText);
                start = textArea.getSelectionStart();
                end = textArea.getSelectionEnd();
                textArea.replaceRange("", start, end);
                pasteContents();
                if (e.getActionCommand().equals("粘贴")) {
                    JOptionPane.showMessageDialog(null, "不能粘贴");
                }
                break;
            case "查找":
                searchText();
                break;
            case "替换":
                dialog = new JDialog(new Frame(), "自定义的");
                outP1 = new JPanel();
                searchL = new JLabel("查找");
                replaceL = new JLabel("替换为");
                searchTF = new JTextField(15);
                replaceTF = new JTextField(15);
                closeButton = new JButton("关闭");
                okButton = new JButton("替换");

                searchL.setPreferredSize(new Dimension(50, 25));
                searchTF.setPreferredSize(new Dimension(30, 25));
                replaceL.setPreferredSize(new Dimension(50, 25));
                replaceTF.setPreferredSize(new Dimension(30, 25));

                Box b1 = Box.createHorizontalBox();
                b1.add(searchL);
                b1.add(Box.createHorizontalStrut(20));
                b1.add(searchTF);

                Box b2 = Box.createHorizontalBox();
                b2.add(replaceL);
                b2.add(Box.createHorizontalStrut(20));
                b2.add(replaceTF);

                Box b4 = Box.createHorizontalBox();
                b4.add(closeButton);
                b4.add(Box.createHorizontalStrut(20));
                b4.add(okButton);

                Box b3 = Box.createVerticalBox();
                b3.add(Box.createVerticalStrut(60));
                b3.add(b1);
                b3.add(Box.createVerticalStrut(20));
                b3.add(b2);
                b3.add(Box.createVerticalStrut(20));
                b3.add(b4);
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                    }
                });
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int start = 0;
                        String s = searchTF.getText();
                        if (s.trim().length() != 0) {
                            boolean flag = textArea.getText().contains(s);
                            if (flag) {
                                start = textArea.getText().indexOf(s);
                                textArea.setSelectionStart(start);
                                textArea.setSelectionEnd(start + s.length());
                                textArea.setSelectedTextColor(Color.ORANGE);
                                String str = replaceTF.getText();
                                textArea.replaceRange(str, textArea.getSelectionStart(), textArea.getSelectionEnd());
                            }
                        }
                    }
                });
                outP1.add(b3);
                dialog.add(outP1);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setLocation(500, 200);
                dialog.setVisible(true);
                dialog.setSize(400, 300);
                break;
            case "帮助":
                dialog = new JDialog(new Frame(), "自定义的");
                outP1 = new JPanel();
                searchL = new JLabel();
                searchL.setText("欢迎使用赵小刚制作的记事本，有什么问题可以反馈给我！");
                b1 = Box.createVerticalBox();
                b1.add(Box.createVerticalStrut(80));
                b1.add(searchL);
                outP1.add(b1);
                dialog.add(outP1);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setLocation(500, 200);
                dialog.setVisible(true);
                dialog.setSize(400, 300);
                break;
            case "关于":
                dialog = new JDialog(new Frame(), "自定义的");
                outP1 = new JPanel();
                searchL = new JLabel();
                searchL.setText("记事本1.0，开发完成，2.0将继续开发，" +
                        "请各位继续关注，谢谢大家！");
                b1 = Box.createVerticalBox();
                b1.add(Box.createVerticalStrut(80));
                b1.add(searchL);
                outP1.add(b1);
                dialog.add(outP1);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setLocation(500, 200);
                dialog.setVisible(true);
                dialog.setSize(400, 300);
                break;
            case "字体":
                dialog = new JDialog(new Frame(), "自定义的");
                outP1 = new JPanel();
                java.util.List<Font> font = new ArrayList<>();
                font.add(new Font("请选择", Font.BOLD, 12));
                font.add(new Font("宋体", Font.BOLD, 12));
                font.add(new Font("楷体", Font.BOLD, 12));
                font.add(new Font("仿宋", Font.BOLD, 12));
                font.add(new Font("黑体", Font.BOLD, 12));
                font.add(new Font("Ubuntu Mono", Font.BOLD, 12));
                JComboBox<String> fontBox = new JComboBox<>();
                fontBox.addItem(font.get(0).getName());
                fontBox.addItem(font.get(1).getName());
                fontBox.addItem(font.get(2).getName());
                fontBox.addItem(font.get(3).getName());
                fontBox.addItem(font.get(4).getName());
                fontBox.addItem(font.get(5).getName());

                JComboBox<String> color = new JComboBox<>();
                color.addItem("请选择");
                color.addItem("蓝色");
                color.addItem("cyan");
                color.addItem("绿色");

                JComboBox<String> fontSize = new JComboBox<>();
                fontSize.addItem("12");
                fontSize.addItem("14");
                fontSize.addItem("16");
                fontSize.addItem("18");
                fontSize.addItem("20");
                fontSize.addItem("22");
                fontSize.addItem("24");
                fontSize.addItem("26");
                fontSize.addItem("28");

                okButton = new JButton("ok");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(fontBox.getSelectedItem().equals("请选择")) {
                            textArea.setFont(new Font("宋体", Font.BOLD, 12));
                        } else if(fontBox.getSelectedItem().equals("宋体")) {
                            textArea.setFont(font.get(1));
                            System.out.println("宋体");
                        } else if(fontBox.getSelectedItem().equals("楷体")) {
                            textArea.setFont(font.get(2));
                            System.out.println("楷体");
                        } else if(fontBox.getSelectedItem().equals("仿宋")) {
                            textArea.setFont(font.get(3));
                            System.out.println("仿宋");
                        } else if(fontBox.getSelectedItem().equals("黑体")) {
                            textArea.setFont(font.get(4));
                            System.out.println("黑体");
                        } else if(fontBox.getSelectedItem().equals("Ubuntu Mono")) {
                            textArea.setFont(font.get(5));
                            System.out.println("Ubuntu Mono");
                        }
                        // TODO: 17-1-16 字体设置有问题
                        if(color.getSelectedItem().equals("请选择")) {
                            textArea.setBackground(Color.white);
                        } else if(color.getSelectedItem().equals("蓝色")) {
                            textArea.setBackground(Color.blue);
                            System.out.println("蓝色");
                        } else if(color.getSelectedItem().equals("cyan")) {
                            textArea.setBackground(Color.cyan);
                            System.out.println("cyan");
                        } else if(color.getSelectedItem().equals("绿色")) {
                            textArea.setBackground(Color.green);
                            System.out.println("绿色");
                        }
//
//                        if(fontSize.getSelectedItem().equals("12")) {
//                            textArea.setFont(Font);
//                        }
                    }
                });

                Box b = Box.createVerticalBox();
                b.add(fontBox);
                b.add(Box.createVerticalStrut(15));
                b.add(color);
                b.add(Box.createVerticalStrut(15));
                b.add(okButton);
                outP1.add(b);
                dialog.add(outP1);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setLocation(500, 200);
                dialog.setVisible(true);
                dialog.setSize(400, 300);
                break;
        }
    }

    /**
     * 首先判断文件名是否为空， 文件名是否存在，文本框中的内容是否为空
     *
     * @param fileName 要读取的文件名
     * @param ta       把读取的文件内容追加到 JTextArea 中
     */
    public void readFile(File fileName, JTextArea ta) {
        if ((fileName == null) || (!fileName.exists()) || ta == null) {
            return;
        }
        ta.setColumns(74);
        ta.setRows(40);
        InputStreamReader read = null;
        BufferedReader br = null;
        try {
            read = new InputStreamReader(new FileInputStream(fileName));
            br = new BufferedReader(read);
            String s = null;
            while ((s = br.readLine()) != null) {
                ta.append(s + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把文本框中的内容写入文件中
     *
     * @param fileName
     * @param ta
     */
    public void writeFile(File fileName, JTextArea ta) {
        PrintWriter write = null;
        if ((fileName == null) || ta == null) {
            return;
        }
        try {
            write = new PrintWriter(fileName);
            write.write(ta.getText());
            write.flush();
            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void pasteContents() {
        contents = clipboard.getContents(this);
        flavor = DataFlavor.stringFlavor;
        if (contents.isDataFlavorSupported(flavor)) {
            try {
                selectText = (String) contents.getTransferData(flavor);
                textArea.append(selectText);
            } catch (UnsupportedFlavorException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void searchText() {
        int start = 0;
        String s = "";
        s = JOptionPane.showInternalInputDialog(panel, "请输入要查找的内容");
        if (s.trim().length() != 0) {
            boolean flag = textArea.getText().contains(s);
            if (flag) {
                start = textArea.getText().indexOf(s);
                textArea.setSelectionStart(start);
                textArea.setSelectionEnd(start + s.length());
                textArea.setSelectedTextColor(Color.ORANGE);
            }
        }
    }
}

