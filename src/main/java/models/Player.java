package models;

public class Player {
    private String name;
    private Board board;

    public Player(String name, int boardSize) {
        this.name = name;
        this.board = new Board(boardSize);
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public boolean placeShip(int row, int col, int length, boolean horizontal) {
        return board.placeShip(row, col, length, horizontal);
    }

    public boolean shootAt(Player opponent, int row, int col) {
        return opponent.getBoard().shoot(row, col);
    }
}
