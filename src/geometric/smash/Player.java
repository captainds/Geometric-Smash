/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.DoubleModifier;
import geometric.smash.property.Property;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Corithian
 */
public class Player extends GameEntity {

    private final Property<Double> speed = new Property<>(150.0);
    private DoubleModifier.Divider focusSpeed = new DoubleModifier.Divider(1.5);
    private Point2D direction;
    
    private Weapon weapon;

    public Player() {
        this.direction = Point2D.ZERO;
        shapes.add(new Rectangle(20, 20));
        shapes.get(0).setFill(Color.BLUE);
        Circle coll = new Circle(1);
        shapes.add(coll);
        colliders.add(coll);
        
        coll.setFill(((Color)shapes.get(0).getFill()).invert());
        coll.setCenterX(10.5);
        coll.setCenterY(10.5);
    }

    private void updateDirection() {
        boolean left = InputMap.isPressedOrHeld(KeyCode.A);
        boolean right = InputMap.isPressedOrHeld(KeyCode.D);
        boolean up = InputMap.isPressedOrHeld(KeyCode.W);
        boolean down = InputMap.isPressedOrHeld(KeyCode.S);
        direction = Point2D.ZERO.add(left != right ? (left ? -1.0 : 1.0) : 0.0,
                up != down ? (up ? -1.0 : 1.0) : 0.0);

    }

    @Override
    public void update(double dt) {
        updateDirection();
        boolean focused = InputMap.isPressedOrHeld(KeyCode.SHIFT);
        if (focused) speed.addModifier(0, focusSpeed);
        else speed.removeModifier(0, focusSpeed);
        Point2D velocity = direction.multiply(speed.getValue() * dt);
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());
        Bounds pBounds = getBoundsInParent();
        Bounds bounds = new Rectangle(0, 0, 800, 600).getBoundsInLocal();
        System.out.println(pBounds);
        System.out.println(bounds);
        if (pBounds.getMinX() < bounds.getMinX()) {
            this.setTranslateX( this.getTranslateX() + bounds.getMinX() - pBounds.getMinX());
        } else if (pBounds.getMaxX() > bounds.getMaxX()) {
            this.setTranslateX(this.getTranslateX() + bounds.getMaxX() - pBounds.getMaxX());
        }
        if (pBounds.getMinY() < bounds.getMinY()) {
            this.setTranslateY(this.getTranslateY() + bounds.getMinY() - pBounds.getMinY());
        } else if (pBounds.getMaxY() > bounds.getMaxY()) {
            this.setTranslateY(this.getTranslateY() + bounds.getMaxY() - pBounds.getMaxY());
        }

    }

}
