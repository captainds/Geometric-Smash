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
        System.out.printf("SpreadShot: %d, %f - %f\n", bulletCount, minAngle, maxAngle);
        double midAngle = (minAngle + maxAngle) / 2;
        double diffAngle = (maxAngle - minAngle) / bulletCount;
        double diffAngleOrg = diffAngle;
        boolean even = bulletCount % 2 == 0;
        System.out.printf("ma, da, dao: %f, %f, %f\n", midAngle, diffAngle, diffAngleOrg);
        if (!even) {
            BulletAttribute ba = new BulletAttribute();
            ba.setDirectionOffset(midAngle);
            ba.setType(BulletAttribute.Type.Circle);
            ba.setColor(Color.WHITE);
            ba.getSpeed().setBaseValue(100.0);
            bulletAttributes.add(ba);
            System.out.printf("ba: %f, %f\n", ba.getDirectionOffset(), midAngle);
            bulletCount--;
        }
        while (bulletCount > 0) {
            BulletAttribute ba = new BulletAttribute();
            ba.setDirectionOffset(midAngle + diffAngle);
            ba.setType(BulletAttribute.Type.Circle);
            ba.setColor(Color.WHITE);
            ba.getSpeed().setBaseValue(100.0);
            bulletAttributes.add(ba);
            System.out.printf("ba: %f\n", ba.getDirectionOffset());
            --bulletCount;
            ba = new BulletAttribute();
            ba.setDirectionOffset(midAngle - diffAngle);
            ba.setType(BulletAttribute.Type.Circle);
            ba.setColor(Color.WHITE);
            ba.getSpeed().setBaseValue(100.0);
            bulletAttributes.add(ba);
            System.out.printf("ba: %f\n", ba.getDirectionOffset());
            --bulletCount;
            diffAngle += diffAngleOrg;
        }
    }

}
