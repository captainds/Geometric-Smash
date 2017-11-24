/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.DoubleModifier;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DroneMkII extends DroneMkI {

    public Shoot shootBehavior;
    private Weapon weapon;
    public DoubleModifier.Multiplier halt = new DoubleModifier.Multiplier(0.0);

    public DroneMkII() {
        this.shootBehavior = new Shoot();
        this.wanderBehavior = new Wander();
        ramBehavior.ramMult.value *= 1.5;
        setBehavior(wanderBehavior);
        mainBody.setFill((Color.DARKGREEN));
        speed.setBaseValue(52.0);
        weapon = new Peashooter();
        weapon.setBaseBurstValue(3);
        Circle c = new Circle(weapon.getTranslateX(), weapon.getTranslateY(), 6);
        c.setFill(Color.DARKCYAN);
        weapon.getChildren().add(c);
        getChildren().add(weapon);
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
            weapon.setDirection(diff);
            weapon.setTrigger(true);
            if (weapon.isFired()) {
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
            weapon.setTrigger(false);
            speed.removeModifier(0, halt);

        }
    }

    public class Wander extends AbstractWander {

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
            if (time == 0.0) {

                double direction = Math.random() * 360.0;

                setDirection(new Point2D(Math.cos(direction), -Math.sin(direction)));
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
        weapon.update(dt);
    }

    /**
     * @return the weapon
     */
    protected Weapon getWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set
     */
    protected void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

}
