import controller.GameController;
import view.GameView;

public class Main {
    public static void main(String[] args) throws Exception {
        GameController gameController = new GameController("Jogador 1", "Jogador 2", 10);
        GameView gameView = new GameView(gameController);

        gameView.startGame();
    }
}
