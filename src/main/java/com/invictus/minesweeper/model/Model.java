package com.invictus.minesweeper.model;

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
        int count = 0;
        Cell[][] field = mineField.getCells();
        int size = field.length * field[0].length;
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                if (field[y][x].isFlag()) count++;
                if (field[y][x].isOpen()) count++;
            }
        }
        isWin = (count == size);
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
        if (field[y][x].isFlag()) return;
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
        Cell[][] field = mineField.getCells();
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                field[y][x].open();
            }
        }
        isBang = true;
    }

    public int getFlagCount() {
        int count = 0;
        Cell[][] field = mineField.getCells();
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                if (field[y][x].isFlag()) {
                    count++;
                }
            }
        }
        return count;
    }
}