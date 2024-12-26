package models;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Board {
    private String[][] grid;
    private int size;
    private List<Ship> ships;

    public Board(int size) {
        this.size = size;
        this.grid = new String[size][size];
        this.ships = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = "⚓";
            }
        }
    }

    public boolean placeShip(int row, int col, int length, boolean horizontal) {
        if (horizontal) {
            if (col + length > size) return false;
            for (int i = 0; i < length; i++) {
                if (grid[row][col + i].equals("X")) return false;
            }
            for (int i = 0; i < length; i++) {
                grid[row][col + i] = "X";
            }
        } else {
            if (row + length > size) return false;
            for (int i = 0; i < length; i++) {
                if (grid[row + i][col].equals("X")) return false;
            }
            for (int i = 0; i < length; i++) {
                grid[row + i][col] = "X";
            }
        }

        ships.add(new Ship("Barco", length));
        return true;
    }

    public boolean shoot(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }

        if (grid[row][col].equals("X")) {
            grid[row][col] = "O";

            for (Ship ship : ships) {
                if (!ship.isDestroyed()) {
                    ship.hit();
                    break;
                }
            }

            return true;
        }

        return false;
    }

    public boolean allShipsDestroyed() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public void drawBoard(TextGraphics tg) {
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                tg.putString(j * 2 + 1, i + 6, grid[i][j]);
            }
        }
    }
}
