/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Gunner extends Enemy {

    private Rectangle mainBody;
    private Circle[] sideParts;
    public DoubleModifier.Multiplier halt = new DoubleModifier.Multiplier(0.0);

    private Wander wander = new Wander();
    private Shoot shoot = new Shoot();

    public Gunner() {
        this.sideParts = new Circle[4];
        sideParts[0] = new Circle(-25, 0, 25);
        sideParts[1] = new Circle(0, -25, 25);
        sideParts[2] = new Circle(25, 0, 25);
        sideParts[3] = new Circle(0, 25, 25);
        this.mainBody = new Rectangle(-30, -30, 60, 60);
        speed.setBaseValue(50.0);
        getPointValue().setBaseValue(800);
        getSpawnCost().setBaseValue(900.0);
        for (Shape s : sideParts) {
            s.setStroke(Color.GREEN);
            s.setFill(Color.DARKRED);
            colliders.add(s);
        }
        shapes.addAll(sideParts);
        shapes.add(mainBody);
        colliders.add(mainBody);
        mainBody.setFill(Color.RED);
        setPrimaryMax(new Random().nextInt(3) + 1);
        setSecondaryMax(4);
        setBehavior(wander);
        setHealth(30);

    }

    @Override
    public void arrangePrimaryWeapons() {
        getChildren().addAll(getPrimaryWeapons());
    }

    @Override
    public void arrangeSecondaryWeapons() {

        for (int i = 0; i < getSecondaryWeapons().size(); ++i) {
            Weapon weapon = getSecondaryWeapons().get(i);
            weapon.setTranslateX(sideParts[i % sideParts.length].getCenterX() / 2);
            weapon.setTranslateY(sideParts[i % sideParts.length].getCenterY() / 2);
            getChildren().add(weapon);
        }
    }

    public class Shoot implements EnemyBehavior {

        protected int currentSec = 0;
        protected int currentCycle = 0;
        protected int maxCycles = 2;
        protected double waitTimer = 1.5;
        protected boolean initial = true;
        protected boolean waiting = false;
        Point2D fireDir = new Point2D(1.0, 0.0);

        @Override
        public void apply(Player player, double dt) {

            ArrayList<Weapon> primaryWeapons = getPrimaryWeapons();
            Weapon weapon = primaryWeapons.get(currentSec);
            Point2D pLoc = player.localToParent(player.getTranslateX(), player.getTranslateY());
            Point2D eLoc = new Point2D(getTranslateX(), getTranslateY());
            Parent current = Gunner.this;
            while (!(current instanceof GameState)) {
                eLoc = current.localToParent(eLoc);
                current = current.getParent();
            }
            fireDir = pLoc.subtract(eLoc).normalize();
            weapon.setTrigger(true);
            weapon.setDirection(fireDir);

            if (weapon.isFullBurst()) {
                weapon.setTrigger(false);
                waitTimer = 0.08;
                waiting = true;
            }
            if (waiting) {
                waitTimer -= dt;

                if (waitTimer <= 0.0) {
                    ++currentSec;
                    currentSec %= primaryWeapons.size();
                    if (currentSec == 0) {
                        ++currentCycle;
                        if (currentCycle == maxCycles) {
                            setBehavior(wander);
                            return;
                        }
                    }
                    weapon = primaryWeapons.get(currentSec);
                    double fireAngle = Math.random() * 360.0;
                    Point2D fireDir = new Point2D(Math.cos(fireAngle), -Math.sin(fireAngle));
                    weapon.setDirection(fireDir);
                    weapon.setTrigger(true);
                    waiting = false;
                }
            }
            weapon.update(dt);
        }

        @Override
        public void cleanup(Enemy enemy) {
            currentCycle = 0;
            currentSec = 0;
            waiting = false;
            speed.removeModifier(0, halt);
        }

    }

    public class Wander implements EnemyBehavior {

        protected double time = 0.0;
        protected int currentSec = 0;
        protected int currentCycle = 0;
        protected int maxCycles = 3;
        protected double swapTime = 1.0;
        protected double waitTimer = 0.15;
        protected boolean initial = true;
        protected boolean waiting = false;
        Point2D fireDir = new Point2D(1.0, 0.0);

        @Override
        public void apply(Player player, double dt) {

            ArrayList<Weapon> secondaryWeapons = getSecondaryWeapons();
            Weapon weapon = secondaryWeapons.get(currentSec);
            if (initial) {
                double moveAngle = Math.random() * 360.0;
                double fireAngle = Math.random() * 360.0;
                Point2D moveDir = new Point2D(Math.cos(moveAngle), -Math.sin(moveAngle));
                fireDir = new Point2D(Math.cos(fireAngle), -Math.sin(fireAngle));
                setDirection(moveDir);
                weapon.setDirection(fireDir);
                weapon.setTrigger(true);
                initial = false;
            }
            time += dt;
            if (weapon.isFullBurst()) {
                weapon.setTrigger(false);
                waitTimer = 0.15;
                waiting = true;
            }
            if (waiting) {
                waitTimer -= dt;

                if (waitTimer <= 0.0) {
                    ++currentSec;
                    currentSec %= secondaryWeapons.size();
                    if (currentSec == 0) {
                        ++currentCycle;
                        if (currentCycle == maxCycles) {
                            setBehavior(shoot);
                            speed.addModifier(0, halt);
                            return;
                        }
                    }
                    weapon = secondaryWeapons.get(currentSec);
                    double fireAngle = Math.random() * 360.0;
                    Point2D fireDir = new Point2D(Math.cos(fireAngle), -Math.sin(fireAngle));
                    weapon.setDirection(fireDir);
                    weapon.setTrigger(true);
                    waiting = false;
                }
            }
            if (time >= swapTime) {
                double direction = Math.random() * 360.0;

                setDirection(new Point2D(Math.cos(direction), -Math.sin(direction)));
                time = 0.0;
            }
            weapon.update(dt);
        }

        @Override
        public void cleanup(Enemy enemy) {
            time = 0.0;
            currentCycle = 0;
            currentSec = 0;
            waiting = false;

        }
    }

    @Override
    public void postUpdate(double dt) {

        Bounds pBounds = getBoundsInParent();
        GameState gameState = getGameState();
        Bounds bounds = new Rectangle(0.0, 0.0, gameState.getWidth(), gameState.getHeight()).getBoundsInLocal();

        if (pBounds.getMinX() < bounds.getMinX()) {
            this.setTranslateX(this.getTranslateX() + bounds.getMinX() - pBounds.getMinX());
        } else if (pBounds.getMaxX() > bounds.getMaxX()) {
            this.setTranslateX(this.getTranslateX() + bounds.getMaxX() - pBounds.getMaxX());
        }
        if (pBounds.getMinY() < bounds.getMinY()) {
            this.setTranslateY(this.getTranslateY() + bounds.getMinY() - pBounds.getMinY());
        } else if (pBounds.getMaxY() > bounds.getMaxY()) {
            this.setTranslateY(this.getTranslateY() + bounds.getMaxY() - pBounds.getMaxY());
        }

    }

}
