/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.Property;
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

    protected final ObservableList<Shape> shapes;
    
    protected final ArrayList<Shape> colliders;
    
    protected final Property<Double> speed;
    protected Point2D direction;
    
    public GameEntity() {
        this.speed = new Property<>(0.0);
        this.direction = Point2D.ZERO;
        shapes = FXCollections.observableArrayList();
        shapes.addListener((ListChangeListener.Change<? extends Shape> c) -> {
            while (c.next()) {
                getChildren().removeAll(c.getRemoved());
                getChildren().addAll(c.getAddedSubList());
            }
        });
        colliders = new ArrayList<>();
    }

    public abstract void update(double dt);

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
}
