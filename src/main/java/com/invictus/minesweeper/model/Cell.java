package com.invictus.minesweeper.model;

import java.awt.*;

import static com.invictus.minesweeper.Controller.BLOCK_SIZE;
import static com.invictus.minesweeper.Controller.COLOR_OF_NUMBERS;

public class Cell {
    private int countBombNear;
    private Color color = Color.black;
    private final boolean isMine;

    private boolean isOpen;
    private boolean isFlag;

    Cell(boolean isMine) {
        this.isMine = isMine;

        this.isFlag = false;
        this.isOpen = false;
    }

    int getCountBombNear() {
        return countBombNear;
    }

    void setCountBombNear(int countBombNear) {
        this.countBombNear = countBombNear;
    }

    boolean isOpen() {
        return isOpen;
    }

    boolean isMine() {
        return isMine;
    }

    boolean isFlag() {
        return isFlag;
    }

    void open() {
        isOpen = true;
        isFlag = false;
    }

    void changeFlag() {
        if (isOpen) return;
        isFlag = !isFlag;
    }

    void bang() {
        color = Color.RED;
    }


    public void paint(Graphics graphics, int x, int y) {
        graphics.setColor(Color.lightGray);
        graphics.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        if (!isOpen) {
            graphics.setColor(Color.lightGray);
            graphics.fill3DRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
            if (isFlag) paintString(graphics, "F", x, y, Color.red);
        } else {
            if (isMine) {
                paintBomb(graphics, x, y);
            } else if (countBombNear > 0) {
                paintString(graphics, Integer.toString(countBombNear), x, y, new Color(COLOR_OF_NUMBERS[countBombNear - 1]));
            }
        }
    }

    private void paintBomb(Graphics graphics, int x, int y) {
        graphics.setColor(color);
        graphics.fillOval(x * BLOCK_SIZE + 3, y * BLOCK_SIZE + 3, BLOCK_SIZE - 6, BLOCK_SIZE - 6);
        graphics.setColor(Color.white);
        graphics.fillOval(x * BLOCK_SIZE + 6, y * BLOCK_SIZE + 6, 3, 3);
        if (isFlag) {
            graphics.setColor(Color.RED);
            graphics.drawLine(x*BLOCK_SIZE+1, y*BLOCK_SIZE+1, x*BLOCK_SIZE+BLOCK_SIZE-1, y*BLOCK_SIZE+BLOCK_SIZE-1);
            graphics.drawLine(x*BLOCK_SIZE+1, y*BLOCK_SIZE+BLOCK_SIZE-1, x*BLOCK_SIZE+BLOCK_SIZE-1, y*BLOCK_SIZE+1);
        }
    }

    private void paintString(Graphics graphics, String str, int x, int y, Color color) {
        graphics.setColor(color);
        graphics.setFont(new Font("", Font.BOLD, BLOCK_SIZE - 4));
        graphics.drawString(str, x * BLOCK_SIZE + 5, y * BLOCK_SIZE + BLOCK_SIZE - 3);
    }
}
