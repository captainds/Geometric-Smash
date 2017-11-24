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

    private Player player;

    private EnemyBehavior behavior;

    @Override
    public void preUpdate(double dt) {
        behavior.apply(player, dt);
    }
    
    @Override
    public void postUpdate(double dt) {
        
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

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

}
