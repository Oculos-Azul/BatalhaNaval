package controller;

import java.io.IOException;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import models.Player;
import view.Menu;

public class GameController {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponent;
    private boolean gameFinished;
    private Screen screen;
    private TextGraphics tg;
    private Menu menu;
    private int currentRow = 1;
    private int currentCol = 0;

    public GameController(String player1Name, String player2Name, int boardSize, Screen screen, TextGraphics tg, Menu menu) {
        this.player1 = new Player(player1Name, boardSize);
        this.player2 = new Player(player2Name, boardSize);
        this.currentPlayer = player1;
        this.opponent = player2;
        this.gameFinished = false;
        this.screen = screen;
        this.tg = tg;
        this.menu = menu;
    }

    public void startGame() throws IOException {
        placeShipsForPlayer(player1);
        placeShipsForPlayer(player2);
    }

    private void placeShipsForPlayer(Player player) throws IOException {
        System.out.println(player.getName() + " deve posicionar seus barcos.");
        int shipLength = 3;
        boolean shipPlaced = false;

        while (!shipPlaced) {
            screen.clear();
            menu.printBanner();
            player.getBoard().drawBoard(tg);

            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(currentCol * 2 + 1, currentRow + 6, "►");
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);

            tg.putString(0, 18, "Posicione seu navio. Pressione Enter para confirmar.");
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.ArrowUp && currentRow > 1) {
                currentRow--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && currentRow < 10) {
                currentRow++;
            } else if (keyStroke.getKeyType() == KeyType.ArrowLeft && currentCol > 0) {
                currentCol--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowRight && currentCol < 9) {
                currentCol++;
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                tg.putString(0, 19, "Escolha a direção (H para Horizontal, V para Vertical): ");
                screen.refresh();
                keyStroke = screen.readInput();
                boolean horizontal = false;
                boolean validInput = false;

                while (!validInput) {
                    if (keyStroke.getKeyType() == KeyType.Character) {
                        char input = Character.toUpperCase(keyStroke.getCharacter());
                        if (input == 'H') {
                            horizontal = true;
                            validInput = true;
                        } else if (input == 'V') {
                            horizontal = false;
                            validInput = true;
                        } else {
                            tg.putString(0, 20, "Entrada inválida. Por favor, digite 'H' para Horizontal ou 'V' para Vertical.");
                            screen.refresh();
                            keyStroke = screen.readInput();
                        }
                    }
                }

                shipPlaced = player.placeShip(currentRow, currentCol + 1, shipLength, horizontal);
                if (!shipPlaced) {
                    tg.putString(0, 20, "Posição inválida, tente novamente.");
                } else {
                    tg.putString(0, 20, "Navio posicionado com sucesso!");
                }
            }
            screen.refresh();
        }
    }

    public String makeMove(int row, int col) {
        if (gameFinished) {
            return "O jogo já terminou!";
        }

        boolean hit = currentPlayer.shootAt(opponent, row, col);
        String result = hit ? "Acertou!" : "Errou!";

        if (checkWinner()) {
            gameFinished = true;
            return "O jogador " + currentPlayer.getName() + " venceu!";
        }

        swapPlayers();
        return result;
    }

    private void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean checkWinner() {
        return opponent.getBoard().allShipsDestroyed();
    }
}
