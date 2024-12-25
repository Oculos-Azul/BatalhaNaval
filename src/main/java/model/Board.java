package model;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;

public class Board {
    private String[][] grid;
    private int size;

    public Board(int size) {
        this.size = size;
        this.grid = new String[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = "⚓";  // Água
            }
        }
    }

    public boolean placeShip(int row, int col, int length, boolean horizontal) {
        if (horizontal) {
            if (col + length > size) return false;
            for (int i = 0; i < length; i++) {
                if (grid[row][col + i] == "X") return false; // Local ocupado
            }
            for (int i = 0; i < length; i++) {
                grid[row][col + i] = "X"; // Coloca o barco
            }
        } else {
            if (row + length > size) return false;
            for (int i = 0; i < length; i++) {
                if (grid[row + i][col] == "X") return false; // Local ocupado
            }
            for (int i = 0; i < length; i++) {
                grid[row + i][col] = "X"; // Coloca o barco
            }
        }
        return true;
    }

    public boolean shoot(int row, int col) {
        if (grid[row][col] == "X") {
            grid[row][col] = "O"; // Marca o local como atingido
            return true; // Acertou
        }
        return false; // Errou
    }

    // Método para desenhar o tabuleiro no terminal usando a Lanterna
    public void drawBoard(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tg.putString(j * 2 + 1, i+6, grid[i][j]);
            }
        }
    }
}
