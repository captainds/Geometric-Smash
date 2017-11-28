/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author AceForce
 */
public class BulletAttribute {

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public double getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * @return the speed
     */
    public Property<Double> getSpeed() {
        return speed;
    }

    /**
     * @return the acceleration
     */
    public Property<Double> getAcceleration() {
        return acceleration;
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
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
    public static enum Type {
        Circle,
        Square
    }
    private Type type = Type.Circle;
    private double size = 1.0;
    private final Property<Double> speed = new Property<>(0.0);
    private final Property<Double> acceleration = new Property<>(0.0);
    private Point2D direction = new Point2D(1.0, 0.0);
    private double directionOffset = 0.0;
    private Color color = Color.RED;

    /**
     * @return the directionOffset
     */
    protected double getDirectionOffset() {
        return directionOffset;
    }

    /**
     * @param directionOffset the directionOffset to set
     */
    protected void setDirectionOffset(double directionOffset) {
        this.directionOffset = directionOffset;
    }

}
