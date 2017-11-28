/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DroneMkII extends DroneMkI {

    public Shoot shootBehavior;
    public DoubleModifier.Multiplier halt = new DoubleModifier.Multiplier(0.0);

    public DroneMkII() {
        this.shootBehavior = new Shoot();
        this.wanderBehavior = new Wander();
        ramBehavior.ramMult.value *= 1.5;
        getPointValue().setBaseValue(200);
        getSpawnCost().setBaseValue(300.0);
        setBehavior(wanderBehavior);
        wanderBehavior.tolerance = 340;
        mainBody.setFill((Color.DARKGREEN));
        Rectangle rectangle = new Rectangle(-5, -5, 10, 10);
        rectangle.setFill(Color.RED);
        shapes.add(rectangle);
        speed.setBaseValue(40.0);
        setPrimaryMax(1);
        setSecondaryMax((int) (Math.random() + 0.5));
    }

    @Override
    public void arrangePrimaryWeapons() {
        getChildren().addAll(getPrimaryWeapons());
    }

    @Override
    public void arrangeSecondaryWeapons() {

        getChildren().addAll(getSecondaryWeapons());
    }

    public static abstract class AbstractShoot implements EnemyBehavior {

        protected int shotCount = 3;

    }

    public class Shoot extends AbstractShoot {

        @Override
        public void apply(Player player, double dt) {

            Point2D pLoc = player.localToParent(player.getTranslateX(), player.getTranslateY());
            Point2D eLoc = new Point2D(getTranslateX() + mainBody.getCenterX(), getTranslateY() + mainBody.getCenterY());
            Parent current = DroneMkII.this;
            while (!(current instanceof GameState)) {
                eLoc = current.localToParent(eLoc);
                current = current.getParent();
            }
            Point2D diff = pLoc.subtract(eLoc);
            if (getPrimaryWeapons().size() > 0) {
                Weapon weapon = getPrimaryWeapons().get(0);
                weapon.setDirection(diff);
                weapon.setTrigger(true);
                if (weapon.isFullBurst()) {
                    --shotCount;
                }
            } else {
                --shotCount;
            }

            if (shotCount <= 0) {
                ramBehavior.targetDir = pLoc.subtract(eLoc).normalize();
                setBehavior(ramBehavior);

            }

        }

        @Override
        public void cleanup(Enemy enemy) {
            shotCount = 3;
            if (getPrimaryWeapons().size() > 0) {
                Weapon weapon = getPrimaryWeapons().get(0);
                weapon.setTrigger(false);
            }
            speed.removeModifier(0, halt);

        }
    }

    public class Wander extends AbstractWander {

        @Override
        public void apply(Player player, double dt) {
            Point2D pLoc = player.localToParent(player.getTranslateX(), player.getTranslateY());
            Point2D eLoc = new Point2D(getTranslateX() + mainBody.getCenterX(), getTranslateY() + mainBody.getCenterY());
            Parent current = DroneMkII.this;
            while (current != getGameState()) {
                eLoc = current.localToParent(eLoc);
                current = current.getParent();
            }
            Point2D diff = pLoc.subtract(eLoc);
            for (Weapon weapon : getSecondaryWeapons()) {
                weapon.setTrigger(false);
            }
            if (time == 0.0) {

                double angle = Math.random() * 360.0;

                Point2D dir = new Point2D(Math.cos(angle), -Math.sin(angle));
                setDirection(dir);
                for (Weapon weapon : getSecondaryWeapons()) {
                    weapon.setDirection(dir.multiply(-1));
                    weapon.setTrigger(true);
                }
            }
            time += dt;
            if (diff.dotProduct(diff) < tolerance * tolerance) {
                setBehavior(shootBehavior);
                speed.addModifier(0, halt);
            } else if (time >= swapTime) {
                double direction = Math.random() * 360.0;

                setDirection(new Point2D(Math.cos(direction), -Math.sin(direction)));
                time = 0.0;
            }
        }

        @Override
        public void cleanup(Enemy enemy) {
            time = 0.0;
        }

    }

    @Override
    public void preUpdate(double dt) {
        super.preUpdate(dt);
        if (getPrimaryWeapons().size() > 0) {
            Weapon weapon = getPrimaryWeapons().get(0);
            weapon.update(dt);
        }
    }

}
