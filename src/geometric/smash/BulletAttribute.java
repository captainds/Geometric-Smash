/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.Property;
import javafx.geometry.Point2D;

/**
 *
 * @author AceForce
 */
public class BulletAttribute {
    public enum Type {
        Circle,
        Square
    }
    private Type type = Type.Circle;
    private double size = 1.0;
    private final Property<Double> speed = new Property<>(0.0);
    private Point2D direction = Point2D.ZERO;
}
