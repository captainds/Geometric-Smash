/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.Property;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author AceForce
 */
public class Bullet extends GameEntity {

    public Bullet(Point2D dir, double speed, Shape shape) {

        this.direction = dir;
        this.speed.setBaseValue(speed);
        shapes.add(shape);
        colliders.add(shape);
    }

    @Override
    public void update(double dt) {

        Point2D velocity = direction.multiply(speed.getValue() * dt);
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());

        Bounds b = getBoundsInParent();
        Bounds bounds = new Rectangle(0, 0, 800, 600).getBoundsInLocal();
        if (b.getMinX() < bounds.getMinX() || b.getMaxX() > bounds.getMaxX()
                || b.getMinY() < bounds.getMinY() || b.getMaxY() > bounds.getMaxY()) {
            fireEvent(new GameEvent(GameEvent.REMOVE, this));

        }
    }

}
