/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;


public class Peashooter extends Weapon {
    public Peashooter() {
        this.getCooldown().setBaseValue(1.0/3.0);
        BulletAttribute ba = new BulletAttribute();
        ba.setSize(1.5);
        ba.setType(BulletAttribute.Type.Circle);
        ba.setColor(Color.WHITE);
        ba.getSpeed().setBaseValue(100.0);
        ba.setDirectionOffset(0.0);
        bulletAttributes.add(ba);
    }
}
