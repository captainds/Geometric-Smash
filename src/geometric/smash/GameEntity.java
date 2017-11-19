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
import javafx.scene.Group;
import javafx.scene.shape.Shape;

/**
 *
 * @author Corithian
 */
public abstract class GameEntity extends Group {

    protected final ObservableList<Shape> shapes;
    
    protected final ArrayList<Shape> colliders;

    public GameEntity() {
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
}
