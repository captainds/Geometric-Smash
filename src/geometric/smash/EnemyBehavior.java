/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

/**
 *
 * @author AceForce
 */
public interface EnemyBehavior {
    public void apply(Player player, double dt);
    public void cleanup(Enemy enemy);
}
