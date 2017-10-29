/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

/**
 *
 * @author Corithian
 */
public abstract class GameEntity extends Group{
    protected final ArrayList<Shape> shapes;
    
    public GameEntity() {
        shapes = new ArrayList<>();
    }
    
    public abstract void update(double dt);
}
