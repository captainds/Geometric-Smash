/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.geometry.Point2D;

/**
 *
 * @author AceForce
 */
public class Enemy extends GameEntity {

    public Player player;

    private EnemyBehavior behavior;

    @Override
    public void update(double dt) {
        behavior.apply(player, dt);
        Point2D velocity = direction.multiply(speed.getValue() * dt);
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());
    }

    /**
     * @return the behavior
     */
    public final EnemyBehavior getBehavior() {
        return behavior;
    }

    /**
     * @param behavior the behavior to set
     */
    public final void setBehavior(EnemyBehavior behavior) {
        if (this.behavior != null) {
            this.behavior.cleanup(this);
        }
        this.behavior = behavior;
    }

}
