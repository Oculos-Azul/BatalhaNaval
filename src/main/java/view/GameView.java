package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import controller.GameController;

import java.io.IOException;

public class GameView {
    private Screen screen;
    private TextGraphics tg;
    private GameController controller;
    private Menu menu;
    private int currentRow = 1;
    private int currentCol = 0;
    private boolean isRunning = true;

    public GameView() {
        try {
            screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
            screen.startScreen();
            screen.setCursorPosition(null);
            this.tg = screen.newTextGraphics();
            this.menu = new Menu(screen, tg);
            this.controller = new GameController("Jogador 1", "Jogador 2", 11, screen, tg, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() throws IOException {
    	int menuOption;
    	do {
    	menuOption = menu.display();
        } while (handleMenuOption(menuOption));
        while (isRunning) {
            screen.clear();
            menu.printBanner();
            drawGameBoard();

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
                String result = controller.makeMove(currentRow, currentCol + 1);
                tg.putString(0, 19, result);
                screen.refresh();
                screen.readInput();
                screen.clear();
                printNextPlayer();
                screen.refresh();
                screen.readInput();


                if (controller.isGameFinished()) {
                    tg.putString(0, 20, "Pressione qualquer tecla para sair.");
                    screen.refresh();
                    screen.readInput();
                    isRunning = false;
                }
            }
        }

        screen.stopScreen();
    }

    private boolean handleMenuOption(int option) throws IOException {
        switch (option) {
            case 0:
                controller.startGame();
                return false;
            case 1:
                return showRules();
            case 2:
                return showCredits();
            case 3:
                screen.stopScreen();
                System.exit(0);
        }
        return false;
    }

    private boolean showRules() throws IOException {
        screen.clear();
        tg.putString(0, 2, "Regras do Jogo:");
        tg.putString(0, 4, "1. Cada jogador posiciona seus navios.");
        tg.putString(0, 5, "2. Tente afundar todos os navios do oponente.");
        tg.putString(0, 7, "Pressione Enter para voltar ao menu.");
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Enter) return true;
        }
    }

    private boolean showCredits() throws IOException {
        screen.clear();
        tg.putString(0, 2, "Créditos:");
        tg.putString(0, 4, "Desenvolvido por Eric.");
        tg.putString(0, 6, "Pressione Enter para voltar ao menu.");
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Enter) return true;
        }
    }

    private void drawGameBoard() {
        controller.getCurrentPlayer().getBoard().drawBoard(tg);
        tg.putString(currentCol * 2 + 1, currentRow + 6, "▶");
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(0, 17, "Jogador atual: " + controller.getCurrentPlayer().getName());
        tg.putString(0, 18, "Use as setas para se mover, Enter para atirar.");
    }
    
    public void printNextPlayer() {
        String[] msg = {
            "██████  ██████   ██████  ██   ██ ██ ███    ███  ██████           ██  ██████   ██████   █████  ██████   ██████  ██████  ██ ",
            "██   ██ ██   ██ ██    ██  ██ ██  ██ ████  ████ ██    ██          ██ ██    ██ ██       ██   ██ ██   ██ ██    ██ ██   ██ ██ ",
            "██████  ██████  ██    ██   ███   ██ ██ ████ ██ ██    ██          ██ ██    ██ ██   ███ ███████ ██   ██ ██    ██ ██████  ██ ",
            "██      ██   ██ ██    ██  ██ ██  ██ ██  ██  ██ ██    ██     ██   ██ ██    ██ ██    ██ ██   ██ ██   ██ ██    ██ ██   ██    ",
            "██      ██   ██  ██████  ██   ██ ██ ██      ██  ██████       █████   ██████   ██████  ██   ██ ██████   ██████  ██   ██ ██"
        };

        tg.setForegroundColor(TextColor.ANSI.RED);

        for (int i = 0; i < msg.length; i++) {
            tg.putString(0, i, msg[i]);
        }
    }
}
