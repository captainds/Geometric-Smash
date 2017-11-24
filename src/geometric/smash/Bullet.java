/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.Property;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author AceForce
 */
public class Bullet extends GameEntity {

    public Bullet(Point2D origin, BulletAttribute ba) {
        Shape shape;
        if (null == ba.getType()) {
            shape = new Text("ERROR");
        } else {
            switch (ba.getType()) {
                case Circle:
                    shape = new Circle(ba.getSize() * 2.0, ba.getSize(), ba.getSize());
                    break;
                case Square:
                    shape = new Rectangle(0.0, 0.0, ba.getSize(), ba.getSize());
                    break;
                default:
                    shape = new Text("ERROR");
                    break;
            }
        }
        setTranslateX(origin.getX() - ba.getSize());
        setTranslateY(origin.getY() - ba.getSize());
        double dir = Math.atan2(ba.getDirection().getY(), ba.getDirection().getX());
        this.setDirection(new Point2D(Math.cos(dir + ba.getDirectionOffset()), Math.sin(dir + ba.getDirectionOffset())));
        this.speed.setBaseValue(ba.getSpeed().getValue());
        this.acceleration.setBaseValue(ba.getAcceleration().getValue());
        shapes.add(shape);
        colliders.add(shape);
        shape.setFill(ba.getColor());
    }

    @Override
    public void preUpdate(double dt) {

    }

    @Override
    public void postUpdate(double dt) {

        Bounds b = getBoundsInParent();
        Bounds gsBounds = this.getGameStateBounds();
        Bounds bounds = new Rectangle(0.0, 0.0, gsBounds.getWidth(), gsBounds.getHeight()).getBoundsInLocal();
        if (b.getMinX() < bounds.getMinX() || b.getMaxX() > bounds.getMaxX()
                || b.getMinY() < bounds.getMinY() || b.getMaxY() > bounds.getMaxY()) {
            fireEvent(new GameEvent(GameEvent.REMOVE, this));

        }
    }

}
