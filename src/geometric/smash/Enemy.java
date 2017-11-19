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
public class Enemy extends GameEntity {

    private Player player;

    private final Property<Double> speed = new Property<>(0.0);

    private Point2D direction = Point2D.ZERO;
    
    private EnemyBehavior behavior;

    @Override
    public void update(double dt) {
        behavior.apply();
        Point2D velocity = direction.multiply(speed.getValue() * dt);
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());
    }

}
