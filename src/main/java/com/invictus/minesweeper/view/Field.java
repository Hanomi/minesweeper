package com.invictus.minesweeper.view;

import com.invictus.minesweeper.model.Cell;

import javax.swing.*;
import java.awt.*;

class Field extends JPanel {
    private final View view;

    Field(View view ) {
        this.view = view;
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0, getWidth(), getHeight());
        Cell[][] cells = view.getMineField().getCells();
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                cells[y][x].paint(graphics, x, y);
            }
        }
    }
}
