package com.invictus.minesweeper.view;

import com.invictus.minesweeper.Controller;
import com.invictus.minesweeper.model.MineField;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.invictus.minesweeper.Controller.BLOCK_SIZE;
import static com.invictus.minesweeper.Controller.TITLE_OF_PROGRAM;

public class View extends JFrame {
    private final Controller controller;
    private final Field field;
    private final TimerLabel timer;
    private final JButton smileButton;
    private final JLabel mineCount;

    public View(Controller controller) {
        this.controller = controller;
        this.field = new Field(this);
        this.timer = new TimerLabel();
        this.smileButton = new JButton();
        this.mineCount = new JLabel("0", JLabel.CENTER);
    }

    public void initGui() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
        background.setBorder(BorderFactory.createEmptyBorder(0, 5,5, 5));
        getContentPane().add(background);

        field.addMouseListener(controller);
        field.setPreferredSize(new Dimension(controller.getFieldSize() * BLOCK_SIZE, controller.getFieldSize() * BLOCK_SIZE));

        //just swing...
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
        top.setPreferredSize(new Dimension(40, 40));

        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(60, 30));
        left.setMaximumSize(new Dimension(60, 30));
        left.add(timer);
        left.setBorder(loweredBevelBorder);

        smileButton.setText(":-)");
        smileButton.addActionListener(controller);
        smileButton.setMargin(new Insets(3, 3, 3, 3));
        smileButton.setFocusable(false);
        smileButton.setPreferredSize(new Dimension(30, 30));
        smileButton.setMaximumSize(new Dimension(30, 30));

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(60, 30));
        right.setMaximumSize(new Dimension(60, 30));
        right.add(mineCount);
        right.setBorder(loweredBevelBorder);

        top.add(left);
        top.add(Box.createHorizontalGlue());
        top.add(smileButton);
        top.add(Box.createHorizontalGlue());
        top.add(right);

        background.add(BorderLayout.CENTER, field);
        background.add(BorderLayout.NORTH, top);

        JMenuBar menuBar = initMenuBar();
        setJMenuBar(menuBar);

        setBounds(200, 200, 200, 200);
        pack();
        setVisible(true);
        setResizable(false);
    }

    private JMenuBar initMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(menu);

        menuItem = new JMenuItem("Reset game", KeyEvent.VK_R);
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        menuItem = new JMenuItem("Easy", KeyEvent.VK_E);
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        menuItem = new JMenuItem("Medium", KeyEvent.VK_M);
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        menuItem = new JMenuItem("Hard", KeyEvent.VK_H);
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        menuItem = new JMenuItem("Set level", KeyEvent.VK_L);
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);

        menuItem = new JMenuItem("About", KeyEvent.VK_A);
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        return menuBar;
    }

    public MineField getMineField() {
        return controller.getMineField();
    }

    public void gameOver() {
        timer.stopTimer();
        smileButton.setText(":-(");
        JOptionPane.showMessageDialog(this,
                "YOU DIED", // like dark souls
                "Game over",
                JOptionPane.ERROR_MESSAGE);
        timer.resetTimer();
        smileButton.setText(":-)");
    }

    public void resetTimer() {
        timer.stopTimer();
        timer.resetTimer();
    }

    public void restart() {
        field.setPreferredSize(new Dimension(controller.getFieldSize() * BLOCK_SIZE, controller.getFieldSize() * BLOCK_SIZE));
        pack();
    }

    public void update() {
        field.repaint();
        mineCount.setText(controller.getMineCount());
    }

    public void gameWin() {
        timer.stopTimer();
        JOptionPane.showMessageDialog(this,
                "Congratulations\n" +
                        "time: " + timer.getText() +"\n" +
                        "field size: " + controller.getFieldSize() + "\n" +
                        "mine count: " + controller.getNumberOfMines(),
                "You win.",
                JOptionPane.INFORMATION_MESSAGE);
        timer.resetTimer();
    }
}
