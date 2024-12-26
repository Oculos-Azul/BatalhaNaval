package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Menu {
    private Screen screen;
    private TextGraphics tg;
    private String[] options = {"Iniciar Jogo", "Regras", "Créditos", "Sair"};
    private int selectedOption = 0;

    public Menu(Screen screen, TextGraphics tg) {
        this.screen = screen;
        this.tg = tg;
    }

    public int display() throws IOException {
        while (true) {
            screen.clear();
            printBanner();
            printOptions();
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedOption = (selectedOption - 1 + options.length) % options.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedOption = (selectedOption + 1) % options.length;
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                return selectedOption;
            }
        }
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

        tg.setForegroundColor(TextColor.ANSI.YELLOW);

        for (int i = 0; i < banner.length; i++) {
            tg.putString(0, i, banner[i]);
        }
    }

    private void printOptions() {
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                tg.setForegroundColor(TextColor.ANSI.GREEN);
                tg.putString(10, 10 + i, ">> " + options[i]);
            } else {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(10, 10 + i, options[i]);
            }
        }
    }
}
