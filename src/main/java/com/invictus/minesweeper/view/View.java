package com.invictus.minesweeper.view;

import com.invictus.minesweeper.Controller;
import com.invictus.minesweeper.model.MineField;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import static com.invictus.minesweeper.Controller.BLOCK_SIZE;
import static com.invictus.minesweeper.Controller.FIELD_SIZE;
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
        field.setPreferredSize(new Dimension(FIELD_SIZE * BLOCK_SIZE, FIELD_SIZE * BLOCK_SIZE));

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

        setBounds(200, 200, 200, 200);
        pack();
        setVisible(true);
        setResizable(false);
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


    public void update() {
        field.repaint();
        mineCount.setText(controller.getMineCount());
    }

    public void gameWin() {
        timer.stopTimer();
        JOptionPane.showMessageDialog(this,
                "Congratulations\n" +
                        "time: " + timer.getText() +"\n" +
                        "field size: " + Controller.FIELD_SIZE + "\n" +
                        "mine count: " + Controller.NUMBER_OF_MINES,
                "You win.",
                JOptionPane.INFORMATION_MESSAGE);
        timer.resetTimer();
    }
}
