package com.invictus.minesweeper;

import com.invictus.minesweeper.model.MineField;
import com.invictus.minesweeper.model.Model;
import com.invictus.minesweeper.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends MouseAdapter implements ActionListener {
    private final View view;
    private final Model model;
    public static final String TITLE_OF_PROGRAM = "Mines";
    public static final int BLOCK_SIZE = 20; // size of a cell
    public static final int[] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0, 0xFF0000, 0x800000, 0x0};

    private int fieldSize = 12;
    private int numberOfMines = 14;

    private Controller() {
        this.view = new View(this);
        this.model = new Model();

        model.startGame(fieldSize, numberOfMines);

        view.initGui();
        view.update();
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public static void main(String[] args) {
        Controller game = new Controller();
    }

    public MineField getMineField() {
        return model.getMineField();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        int x = e.getX()/BLOCK_SIZE;
        int y = e.getY()/BLOCK_SIZE;
        if (e.getButton() == MouseEvent.BUTTON1) {
            model.leftClick(x, y);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            model.rightClick(x, y);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            model.middleClick(x, y);
        }
        view.update();
        if (model.isBang()) {
            view.gameOver();
            newGame();
        }
        if (model.isWin()) {
            view.gameWin();
            newGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Easy":
                newGame(12, 14);
                break;
            case "Medium":
                newGame(16, 40);
                break;
            case "Hard":
                newGame(22, 99);
                break;
            case "Set level":
                try {
                    newGame(Integer.parseInt(JOptionPane.showInputDialog(view, "Enter field size")),
                            Integer.parseInt(JOptionPane.showInputDialog(view, "Enter amount of mines")));
                } catch (NumberFormatException e1) {
                    System.err.println("not int " + e1.getMessage());
                    JOptionPane.showMessageDialog(view, "Level data incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "About":
                // todo About
                break;
            default:
                newGame();
        }
    }

    private void newGame(int...size){
        if (size.length == 2 && size[0] > 4 && size[1] > 1 && (Math.pow(size[0], 2)/3 > size[1])) {
            fieldSize = size[0];
            numberOfMines = size[1];
        }
        model.startGame(fieldSize, numberOfMines);
        view.resetTimer();
        view.restart();
    }

    public String getMineCount() {
        return Integer.toString(numberOfMines - model.getFlagCount());
    }
}
