/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Corithian
 */
public abstract class GameEntity extends Group {

    /**
     * @return the gameStateBounds
     */
    public Bounds getGameStateBounds() {
        return gameState.getBoundsInLocal();
    }
    
    protected final ObservableList<Shape> shapes;

    protected final ArrayList<Shape> colliders;

    protected final Property<Double> speed;
    protected final Property<Double> acceleration;
    
    private Point2D direction;
    
    private GameState gameState;

    public GameEntity() {
        this.speed = new Property<>(0.0);
        this.acceleration = new Property<>(0.0);
        this.direction = Point2D.ZERO;
        shapes = FXCollections.observableArrayList();
        shapes.addListener((ListChangeListener.Change<? extends Shape> c) -> {
            while (c.next()) {
                getChildren().removeAll(c.getRemoved());
                getChildren().addAll(c.getAddedSubList());
            }
        });
        colliders = new ArrayList<>();
        gameState = null;
    }

    public abstract void preUpdate(double dt);

    public abstract void postUpdate(double dt);

    public final void update(double dt) {
        preUpdate(dt);
        speed.setBaseValue(speed.getBaseValue() + acceleration.getValue() * dt);
        Point2D velocity = direction.multiply(speed.getValue() * dt);
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());
        postUpdate(dt);
    }

    /**
     * @return the direction
     */
    public Point2D getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Point2D direction) {
        this.direction = direction;
    }

    /**
     * @return the gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the colliders
     */
    public ArrayList<Shape> getColliders() {
        return colliders;
    }
}
