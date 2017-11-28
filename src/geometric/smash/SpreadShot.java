/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.scene.paint.Color;

public class SpreadShot extends Weapon {

    public SpreadShot(int bulletCount, double minAngle, double maxAngle) {
        if (bulletCount == 0) {
            return;
        }
        this.getCostMultiplier().value = 1.0 + Math.log10(bulletCount / 1.2);
        double midAngle = (minAngle + maxAngle) / 2;
        double diffAngle = (maxAngle - minAngle) / bulletCount;
        double diffAngleOrg = diffAngle;
        boolean even = bulletCount % 2 == 0;
        if (!even) {
            BulletAttribute ba = new BulletAttribute();
            ba.setDirectionOffset(midAngle);
            ba.setType(BulletAttribute.Type.Circle);
            ba.setColor(Color.WHITE);
            ba.getSpeed().setBaseValue(100.0);
            bulletAttributes.add(ba);
            bulletCount--;
        }
        while (bulletCount > 0) {
            BulletAttribute ba = new BulletAttribute();
            ba.setDirectionOffset(midAngle + diffAngle);
            ba.setType(BulletAttribute.Type.Circle);
            ba.setColor(Color.WHITE);
            ba.getSpeed().setBaseValue(100.0);
            bulletAttributes.add(ba);
            --bulletCount;
            ba = new BulletAttribute();
            ba.setDirectionOffset(midAngle - diffAngle);
            ba.setType(BulletAttribute.Type.Circle);
            ba.setColor(Color.WHITE);
            ba.getSpeed().setBaseValue(100.0);
            bulletAttributes.add(ba);
            --bulletCount;
            diffAngle += diffAngleOrg;
        }
    }

}
