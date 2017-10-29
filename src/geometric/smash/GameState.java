/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.time.Instant;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Corithian
 */
public class GameState extends Pane {

    private final Player player;
    ArrayList<GameEntity> gameEntities;

    public GameState() {
        this.player = new Player();
        gameEntities = new ArrayList<>();
        gameEntities.add(player);
        getChildren().add(player);
        player.setTranslateX(this.getWidth() / 2.0);
        player.setTranslateY(this.getHeight() / 2.0);

        setFocusTraversable(true);
        requestFocus();
        addEventHandler(KeyEvent.KEY_PRESSED, InputMap.getHandler());
        addEventHandler(KeyEvent.KEY_RELEASED, InputMap.getHandler());

        AnimationTimer t = new AnimationTimer() {
            boolean running = true;
            double fps = 60.0, dt = 1e9 / fps;
            double updt = dt / 1e9;
            double startTime = System.nanoTime();

            @Override
            public void handle(long curTime) {
                double cT = curTime;
                while (cT - startTime > dt) {
                    startTime += dt;
                    gameEntities.forEach((GameEntity entity) -> {
                        entity.update(updt);

                    });

                }
            }
        };
        t.start();

    }
}
