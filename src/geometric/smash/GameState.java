/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.time.Instant;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Corithian
 */
public class GameState extends Pane {

    private final Player player;
    private ArrayList<GameEntity> gameEntities;
    private final AnimationTimer gameLoop;
    private boolean paused;

    public GameState() {
        this.paused = true;
        this.player = new Player();
        gameEntities = new ArrayList<>();
        gameEntities.add(player);
        getChildren().add(player);
        Platform.runLater(() -> {
            player.setTranslateX(this.getWidth() / 2.0);
            player.setTranslateY(this.getHeight() / 2.0);
        });

        setFocusTraversable(true);
        requestFocus();
        addEventHandler(KeyEvent.KEY_PRESSED, InputMap.getHandler());
        addEventHandler(KeyEvent.KEY_RELEASED, InputMap.getHandler());

        gameLoop = new AnimationTimer() {
            boolean running = true;
            double fps = 60.0;
            double dt = 1 / fps;
            double dtNano = 1e9 * dt; //dt in nano seconds
            double startTime = System.nanoTime();

            @Override
            public void handle(long curTime) {
                double cT = curTime;
                InputMap.processInputs();
                while (cT - startTime > dtNano) {
                    startTime += dtNano;
                    gameEntities.forEach((GameEntity entity) -> {
                        entity.update(dt);

                    });

                    if (InputMap.isReleased(KeyCode.P)) {
                        fireEvent(new GameEvent(GameEvent.PAUSE));
                        return;
                    }
                }
            }
        };
    }

    public void setPaused(boolean paused) {
        if (this.paused == paused) {
            return;
        }
        if (paused) {
            gameLoop.stop();
        } else {
            gameLoop.start();
        }
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
