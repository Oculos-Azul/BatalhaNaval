package view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import controller.GameController;

import java.io.IOException;

public class GameView {
    private Screen screen;
    private GameController controller;
    private int currentRow = 0;
    private int currentCol = 0;
    private boolean isRunning = true;

    public GameView(GameController controller) {
        try {
            screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
            screen.startScreen();
            screen.setCursorPosition(null);  // Desativa o cursor
            this.controller = controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() throws IOException {
        controller.startGame(); // Inicia o jogo, ou outra lógica de inicialização

        while (isRunning) {
            screen.clear(); // Limpa a tela antes de desenhar o novo quadro
            printBanner(); // Chama o banner para estar no topo
            drawGameBoard(); // Desenha o tabuleiro do jogo

            screen.refresh(); // Atualiza a tela para exibir os novos elementos

            KeyStroke keyStroke = screen.readInput(); // Lê a entrada do usuário

            if (keyStroke.getKeyType() == KeyType.ArrowUp && currentRow > 0) {
                currentRow--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && currentRow < 9) {
                currentRow++;
            } else if (keyStroke.getKeyType() == KeyType.ArrowLeft && currentCol > 0) {
                currentCol--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowRight && currentCol < 9) {
                currentCol++;
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
            	
            	screen.newTextGraphics().putString(0, 17, currentCol + " " + currentRow);//printa o valor atual da posição antes de passar para o makeMove
                boolean hit = controller.makeMove(currentRow, currentCol);
                if (hit) {
                    screen.newTextGraphics().setForegroundColor(TextColor.ANSI.GREEN);
                    screen.newTextGraphics().putString(0, 16, "Acertou!");
                } else {
                    screen.newTextGraphics().setForegroundColor(TextColor.ANSI.RED);
                    screen.newTextGraphics().putString(0, 16, "Errou!");
                }
                screen.refresh();
                
                // Espera até o usuário pressionar Enter novamente para continuar
                boolean continueGame = false;
                while (!continueGame) {
                    keyStroke = screen.readInput();
                    if (keyStroke.getKeyType() == KeyType.Enter) {
                        continueGame = true; // Continua o jogo quando Enter for pressionado novamente
                    }
                }
            }
        }
    }

    private void drawGameBoard() {
        controller.getCurrentPlayer().getBoard().drawBoard(screen.newTextGraphics());
        screen.newTextGraphics().setForegroundColor(TextColor.ANSI.YELLOW);
        screen.newTextGraphics().putString(currentCol * 2 + 1, currentRow + 6, "7");
    }
    
    public void printBanner() {
        String[] banner = {
            "  ██████╗  █████╗ ████████╗ █████╗ ██╗     ██╗  ██╗ █████╗     ███╗   ██╗ █████╗ ██╗   ██╗ █████╗ ██╗     ",
            "  ██╔══██╗██╔══██╗╚══██╔══╝██╔══██╗██║     ██║  ██║██╔══██╗    ████╗  ██║██╔══██╗██║   ██║██╔══██╗██║     ",
            "  ██████╔╝███████║   ██║   ███████║██║     ███████║███████║    ██╔██╗ ██║███████║██║   ██║███████║██║     ",
            "  ██╔══██╗██╔══██║   ██║   ██╔══██║██║     ██╔══██║██╔══██║    ██║╚██╗██║██╔══██║╚██╗ ██╔╝██╔══██║██║     ",
            "  ██████╔╝██║  ██║   ██║   ██║  ██║███████╗██║  ██║██║  ██║    ██║ ╚████║██║  ██║ ╚████╔╝ ██║  ██║███████╗",
            "  ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝",
            "                                                                                                          "
        };

        // Imprime o banner no topo da tela
        for (int i = 0; i < banner.length; i++) {
            screen.newTextGraphics().putString(0, i, banner[i]); // Imprime o banner nas primeiras linhas
        }
    }
}
