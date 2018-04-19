package com.invictus.minesweeper.model;

import java.util.Random;

public class MineField {
    private final Cell[][] cells;
    private final int fieldSize;
    private final int numberOfMines;
    private final Random random;

    MineField(int fieldSize, int numberOfMines) {
        this.fieldSize = fieldSize;
        this.cells = new Cell[fieldSize][fieldSize];
        this.numberOfMines = numberOfMines;
        this.random = new Random();

        initField();
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void initField() {
        int x, y, countMines = 0;
        // add random mines
        while (countMines < numberOfMines) {
            do { //search empty spot
                x = random.nextInt(fieldSize);
                y = random.nextInt(fieldSize);
            } while (cells[y][x] != null);
            cells[y][x] = new Cell(true);
            countMines++;
        }
        for (x = 0; x < fieldSize; x++) {
            for (y = 0; y < fieldSize; y++) {
                if (cells[y][x] == null) {
                    cells[y][x] = new Cell(false);
                    int count = 0;
                    for (int dx = -1; dx < 2; dx++)
                        for (int dy = -1; dy < 2; dy++) {
                            int nX = x + dx;
                            int nY = y + dy;
                            if (nX < 0 || nY < 0 || nX > fieldSize - 1 || nY > fieldSize - 1) {
                                nX = x;
                                nY = y;
                            }
                            count += (cells[nY][nX] != null && cells[nY][nX].isMine()) ? 1 : 0;
                        }
                    cells[y][x].setCountBombNear(count);
                }
            }
        }
    }
}
