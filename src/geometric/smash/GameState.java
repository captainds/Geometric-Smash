/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Corithian
 */
public class GameState extends Pane {

    private final Player player;
    private ObservableList<GameEntity> gameEntities;
    private ArrayList<GameEntity> toAdd;
    private ArrayList<GameEntity> toRemove;
    private final AnimationTimer gameLoop;
    private boolean paused;

    public GameState() {
        this.setStyle("-fx-background-color: black");
        setMinSize(800, 600);
        setPrefSize(800, 600);
        setMaxSize(800, 600);
        this.paused = true;
        this.player = new Player();
        gameEntities = FXCollections.observableArrayList();
        gameEntities.addListener((ListChangeListener.Change<? extends GameEntity> e) -> {
            while (e.next()) {
                getChildren().removeAll(e.getRemoved());
                getChildren().addAll(e.getAddedSubList());
            }
        });
        toAdd = new ArrayList<>();
        toRemove = new ArrayList<>();
        DroneMkI d = new DroneMkI();
        d.setTranslateX(400);
        d.setTranslateY(400);
        d.setPlayer(player);
        DroneMkII d2 = new DroneMkII();
        d2.setTranslateX(300);
        d2.setTranslateY(300);
        d2.setPlayer(player);
        addEntity(player);
        addEntity(d);
        addEntity(d2);
        Platform.runLater(() -> {
            player.setTranslateX(this.getWidth() / 2.0);
            player.setTranslateY(this.getHeight() / 2.0);

        });

        setFocusTraversable(true);
        requestFocus();
        addEventHandler(KeyEvent.KEY_PRESSED, InputMap.getHandler());
        addEventHandler(KeyEvent.KEY_RELEASED, InputMap.getHandler());
        addEventHandler(GameEvent.ADD, (GameEvent e) -> {
            addEntity(e.getEntity());
        });
        addEventHandler(GameEvent.REMOVE, (GameEvent e) -> {
            removeEntity(e.getEntity());
        });

        gameLoop = new AnimationTimer() {
            boolean running = true;
            double fps = 60.0;
            double dt = 1 / fps;
            double dtNano = 1e9 * dt; //dt in nano seconds
            double startTime = System.nanoTime();

            @Override
            public void start() {
                startTime = System.nanoTime();
                super.start();
            }

            @Override
            public void handle(long curTime) {
                double cT = curTime;
                InputMap.processInputs();
                while (cT - startTime > dtNano) {
                    startTime += dtNano;
                    gameEntities.forEach((GameEntity entity) -> {
                        entity.update(dt);

                    });
                    gameEntities.removeAll(toRemove);
                    gameEntities.addAll(toAdd);
                    toAdd.clear();
                    toRemove.clear();

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

    public void addEntity(GameEntity e) {
        e.setGameState(this);
        toAdd.add(e);
    }

    public void removeEntity(GameEntity e) {
        toRemove.add(e);
    }
}
