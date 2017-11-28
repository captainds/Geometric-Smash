/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

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
    Random rng = new Random();
    
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
        addEntity(player);
        player.setTranslateX(400);
        player.setTranslateY(400);
        
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
        
        ArrayList<Enemy> enemies = generateEnemies(rng.nextInt(30000) + 10000);
        for (Enemy enemy : enemies) {
            enemy.setPlayer(player);
            enemy.setTranslateX(rng.nextBoolean() ? rng.nextInt(300) : rng.nextInt(200) + 450);
            enemy.setTranslateY(rng.nextBoolean() ? rng.nextInt(300) : rng.nextInt(200) + 450);
            addEntity(enemy);
        }
        
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
                        if (entity instanceof Enemy) {
                            Bounds pBounds = player.getBoundsInParent();
                            Bounds eBounds = entity.getBoundsInParent();
                            if (pBounds.intersects(eBounds)) {
                                for (Shape col1 : player.getColliders()) {
                                    for (Shape col2 : entity.getColliders()) {
                                        Shape check = Shape.intersect(col1, col2);
                                        Bounds coll = check.getBoundsInLocal();
                                        if (coll.getWidth() > 0 && coll.getHeight() > 0) {
                                            fireEvent(new GameEvent(GameEvent.END));
                                        }
                                    }
                                }
                            }
                        } else if (entity instanceof Bullet) {
                            Bullet bullet = (Bullet) entity;
                            
                            Bounds bBounds = bullet.getBoundsInParent();
                            if (bullet.isPlayerOwned()) {
                                collisions:
                                for (GameEntity e : gameEntities) {
                                    if (e instanceof Enemy) {
                                        Bounds eBounds = e.getBoundsInParent();
                                        if (bBounds.intersects(eBounds)) {
                                            for (Shape col1 : e.getColliders()) {
                                                for (Shape col2 : bullet.getColliders()) {
                                                    Shape check = Shape.intersect(col1, col2);
                                                    Bounds coll = check.getBoundsInLocal();
                                                    if (coll.getWidth() > 0 && coll.getHeight() > 0) {
                                                        removeEntity(e);
                                                        removeEntity(bullet);
                                                        break collisions;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                Bounds pBounds = player.getBoundsInParent();
                                if (bBounds.intersects(pBounds)) {
                                    for (Shape col1 : player.getColliders()) {
                                        for (Shape col2 : bullet.getColliders()) {
                                            Shape check = Shape.intersect(col1, col2);
                                            Bounds coll = check.getBoundsInLocal();
                                            if (coll.getWidth() > 0 && coll.getHeight() > 0) {
                                                fireEvent(new GameEvent(GameEvent.END));
                                                return;
                                            }
                                        }
                                    }
                                    
                                }
                            }
                        }
                        
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
    
    private void addEntity(GameEntity e) {
        e.setGameState(this);
        toAdd.add(e);
    }
    
    private void removeEntity(GameEntity e) {
        toRemove.add(e);
    }
    
    public ArrayList<Enemy> generateEnemies(int pointSug) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Integer> pointSuggestions = new ArrayList<>();
        boolean qualOverQuant = rng.nextBoolean();
        int enemyMax = rng.nextInt(4) + 4;
        int pointMin = Math.max(pointSug / enemyMax, 100);
        if (!qualOverQuant) {
            
            enemyMax *= (1.75 + rng.nextDouble());
        }
        for (int i = 0; i < enemyMax; i++) {
            
            Enemy e = generateEnemy(Math.max(200, pointSug));
            pointSug -= e.getSpawnCost().getValue();
            enemies.add(e);
            
        }
        return enemies;
    }
    
    private Enemy generateEnemy(int suggestedPoints) {
        Enemy enemy = null;
        if (suggestedPoints > 25000) {
            enemy = new Gunner();
        } else if (suggestedPoints > 14000) {
            enemy = new DroneMkII();
        } else {
            enemy = new DroneMkI();
        }
        for (int i = 0; i < enemy.getPrimaryMax(); i++) {
            Weapon w;
            if (rng.nextDouble() < 0.6) {
                w = new Peashooter();
                w.setBaseBurstValue(rng.nextInt(4) + 3);
                w.getCooldown().setBaseValue(0.5);
            } else {
                double angle1 = rng.nextDouble() * -135.0, angle2 = rng.nextDouble() * 135.0;
                if (angle1 >= angle2) {
                    double tmp = angle1;
                    angle1 = angle2;
                    angle2 = tmp;
                }
                if (angle2 - angle1 < 30) {
                    angle2 = angle1 + 30;
                }
                w = new SpreadShot(3 + rng.nextInt(9), angle1, angle2);
                w.setBaseBurstValue(rng.nextInt(3) + 1);
                w.getCooldown().setBaseValue(1.0);
            }
            enemy.getSpawnCost().addModifier(0, w.getCostMultiplier());
            enemy.addPrimaryWeapon(w);
        }
        suggestedPoints -= enemy.getSpawnCost().getValue();
        if (suggestedPoints > 0) {
            for (int i = 0; i < enemy.getSecondaryMax(); i++) {
                Weapon w;
                if (rng.nextDouble() < 0.7) {
                    w = new Peashooter();
                    w.setBaseBurstValue(rng.nextInt(2) + 1);
                    w.getCooldown().setBaseValue(0.8);
                } else {
                    double angle1 = rng.nextDouble() * 360, angle2 = rng.nextDouble() * 360.0;
                    if (angle1 >= angle2) {
                        double tmp = angle1;
                        angle1 = angle2;
                        angle2 = tmp;
                    }
                    if (angle2 - angle1 < 20) {
                        angle2 = angle1 + 20;
                    }
                    w = new SpreadShot(2 + rng.nextInt(5), angle1, angle2);
                    w.setBaseBurstValue(rng.nextInt(2) + 1);
                    w.getCooldown().setBaseValue(1.0);
                }
                enemy.getSpawnCost().addModifier(0, w.getCostMultiplier());
                
                enemy.addSecondaryWeapon(w);
            }
        }
        enemy.arrangePrimaryWeapons();
        enemy.arrangeSecondaryWeapons();
        System.out.println(enemy.getSpawnCost().getValue());
        return enemy;
    }
}
