/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.Property;
import java.util.ArrayList;

/**
 *
 * @author AceForce
 */
public class Weapon extends GameEntity {
    
    private final Property<Double> cooldown;

    private final ArrayList<BulletAttribute> bulletAttributes;
    
    public Weapon() {
        this.cooldown = new Property<>(0.0);
        bulletAttributes = new ArrayList<>();
    }
    
    @Override
    public void update(double dt) {

    }
    
}
