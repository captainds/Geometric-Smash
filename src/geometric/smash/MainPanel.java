/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Corithian
 */
public class MainPanel extends StackPane {

    private final MainMenu menu;
    private GameState gameState;
    private final EventHandler<GameEvent> onStart;
    private final EventHandler<GameEvent> onPause;
    private final EventHandler<GameEvent> onEnd;

    public MainPanel() {

        this.setStyle("-fx-background-color: white");
        setFocusTraversable(true);
        setMinSize(790, 590);
        setPrefSize(790, 590);
        setMaxSize(790, 590);
        Platform.runLater(() -> {
            System.out.println(this.getWidth() + " " + this.getHeight());
        });
        requestFocus();
        menu = new MainMenu();
        getChildren().add(menu);

        onStart = (GameEvent e) -> {
            startGame();
            getChildren().remove(menu);
            getChildren().add(gameState);
            gameState.setPaused(false);
            e.consume();
        };

        onPause = (GameEvent e) -> {
            if (!gameState.isPaused()) {
                gameState.setPaused(true);
                getChildren().add(new PauseState());
            } else {
                gameState.setPaused(false);
                getChildren().remove(getChildren().size() - 1);
            }
            e.consume();
        };

        onEnd = (GameEvent e) -> {
            getChildren().clear();
            getChildren().add(menu);
            e.consume();
            InputMap.processInputs();
        };

        setEventHandler(GameEvent.START, onStart);
        setEventHandler(GameEvent.PAUSE, onPause);
        setEventHandler(GameEvent.END, onEnd);
    }

    private void startGame() {
        gameState = new GameState();

    }

}
