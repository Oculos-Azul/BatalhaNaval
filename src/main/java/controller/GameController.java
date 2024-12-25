package controller;

import model.Player;
import java.util.Scanner;

public class GameController {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponent;

    public GameController(String player1Name, String player2Name, int boardSize) {
        this.player1 = new Player(player1Name, boardSize);
        this.player2 = new Player(player2Name, boardSize);
        this.currentPlayer = player1;
        this.opponent = player2;
    }

    // Método que agora pergunta onde o jogador quer colocar os barcos
    public void startGame() {
        System.out.println("Jogo iniciado!");
        placeShipsForPlayer(player1);
        placeShipsForPlayer(player2);
    }

    private void placeShipsForPlayer(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(player.getName() + ", é sua vez de colocar seus barcos!");

        // Definindo o tamanho dos barcos
        int[] shipLengths = {2};  // Exemplo de barcos com comprimentos diferentes

        for (int length : shipLengths) {
            boolean placed = false;
            while (!placed) {
                // Limpa a tela e exibe o texto sem apagar as entradas anteriores
                System.out.println("Posicione um barco de comprimento " + length + "!");
                System.out.print("Digite a linha de onde você quer colocar o barco: ");
                int row = readInt(scanner);

                System.out.print("\nDigite a coluna de onde você quer colocar o barco: ");
                int col = readInt(scanner);

                System.out.print("\nDigite a orientação (1 para horizontal, 0 para vertical): ");
                boolean horizontal = readInt(scanner) == 1;

                // Tenta posicionar o barco
                placed = player.getBoard().placeShip(row, col, length, horizontal);
                if (!placed) {
                    System.out.println("Posição inválida! Tente novamente.");
                }
            }
        }
    }

    private int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next(); // Consome o valor inválido
        }
        return scanner.nextInt();
    }

    public boolean makeMove(int row, int col) {
        boolean hit = currentPlayer.shootAt(opponent, row, col);
        swapPlayers();
        return hit;
    }

    private void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }

    public boolean checkWinner() {
        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
