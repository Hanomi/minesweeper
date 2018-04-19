package com.invictus.minesweeper;

import com.invictus.minesweeper.model.MineField;
import com.invictus.minesweeper.model.Model;
import com.invictus.minesweeper.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends MouseAdapter implements ActionListener {
    private final View view;
    private final Model model;
    public static final String TITLE_OF_PROGRAM = "Mines";
    public static final int BLOCK_SIZE = 20; // size of a cell
    public static final int FIELD_SIZE = 15;
    public static final int NUMBER_OF_MINES = 25;
    public static final int[] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0, 0xFF0000, 0x800000, 0x0};

    private Controller() {
        this.view = new View(this);
        this.model = new Model();

        model.startGame();

        view.initGui();
        view.update();
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
            model.startGame();
            view.update();
        }
        if (model.isWin()) {
            view.gameWin();
            model.startGame();
            view.update();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.startGame();
        view.update();
        view.resetTimer();
    }

    public String getMineCount() {
        return Integer.toString(NUMBER_OF_MINES - model.getFlagCount());
    }
}
