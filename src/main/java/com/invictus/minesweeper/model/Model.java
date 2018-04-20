package com.invictus.minesweeper.model;

import java.util.Arrays;

public class Model {
    private MineField mineField;
    private boolean isBang;
    private boolean isWin;

    public void startGame(int fieldSize, int numberOfMines) {
        mineField = new MineField(fieldSize, numberOfMines);
        isBang = false;
        isWin = false;
    }

    public boolean isBang() {
        return isBang;
    }

    public boolean isWin() {
        return isWin;
    }

    private void checkWin() {
        Cell[][] field = mineField.getCells();
        int size = field.length * field[0].length;
        isWin = (size == Arrays.stream(field)
                .flatMap(Arrays::stream)
                .filter(f -> f.isOpen() || f.isFlag())
                .count());
    }

    public MineField getMineField() {
        return mineField;
    }

    public void rightClick(int x, int y) {
        mineField.getCells()[y][x].changeFlag();
        checkWin();
    }

    public void leftClick(int x, int y) {
        if (!mineField.getCells()[y][x].isOpen()) {
            openCell(x, y);
        }
        checkWin();
    }

    public void middleClick(int x, int y) {
        for (int dx = -1; dx < 2; dx++)
            for (int dy = -1; dy < 2; dy++) openCell(x + dx, y + dy);
        checkWin();
    }

    private void openCell(int x, int y) {
        Cell[][] field = mineField.getCells();

        if (x < 0 || x > field[0].length - 1 || y < 0 || y > field.length - 1) return; // wrong coordinates
        if (field[y][x].isOpen()) return; // cell is already open
        if (field[y][x].isFlag()) return; // don't open flags
        field[y][x].open();
        if (field[y][x].isMine()) {
            field[y][x].bang();
            openAll();
            return;
        }
        if (field[y][x].getCountBombNear() > 0) return; // the cell is not empty
        for (int dx = -1; dx < 2; dx++)
            for (int dy = -1; dy < 2; dy++) openCell(x + dx, y + dy);
    }

    private void openAll() {
        Arrays.stream(mineField.getCells())
                .flatMap(Arrays::stream)
                .filter(Cell::isMine)
                .forEach(Cell::open);
        isBang = true;
    }

    public int getFlagCount() {
        return (int) Arrays.stream(mineField.getCells())
                .flatMap(Arrays::stream)
                .filter(Cell::isFlag)
                .count();
    }
}