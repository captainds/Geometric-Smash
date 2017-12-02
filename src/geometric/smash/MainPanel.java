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
    private final EventHandler<GameEvent> onNext;
    private final int minPoints;
    private final int maxPoints;

    private int points;

    public MainPanel() {
        this.minPoints = 23000;
        this.maxPoints = 100000;
        this.points = minPoints;

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
            InputMap.processInputs();
            gameState = new GameState(points);
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
            points = minPoints;
            e.consume();
        };

        onNext = (GameEvent e) -> {
            getChildren().clear();
            gameState = new GameState(points);
            getChildren().add(gameState);
            points *= 1.15;
            if (points > maxPoints) {
                points = maxPoints;
            }
            onStart.handle(e);
        };

        setEventHandler(GameEvent.START, onStart);
        setEventHandler(GameEvent.PAUSE, onPause);
        setEventHandler(GameEvent.END, onEnd);
        setEventHandler(GameEvent.NEXT, onNext);
    }

}
