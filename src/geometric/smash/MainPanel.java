/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Corithian
 */
public class MainPanel extends StackPane {

    private final MainMenu menu;
    private final GameState gameState;
    private final EventHandler<GameEvent> onStart;
    private final EventHandler<GameEvent> onPause;

    public MainPanel() {
        setFocusTraversable(true);
        requestFocus();
        menu = new MainMenu();
        getChildren().add(menu);
        gameState = new GameState();

        onStart = (GameEvent e) -> {
            getChildren().remove(menu);
            getChildren().add(gameState);
            gameState.setPaused(false);
        };

        onPause = (GameEvent e) -> {
            if (!gameState.isPaused()) {
                getChildren().add(new PauseState());
                gameState.setPaused(true);
            } else {
                getChildren().remove(getChildren().size() - 1);
                gameState.setPaused(false);
            }
            e.consume();
        };

        setEventHandler(GameEvent.START, onStart);
        setEventHandler(GameEvent.PAUSE, onPause);
    }

}
