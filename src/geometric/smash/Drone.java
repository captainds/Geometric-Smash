/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.DoubleModifier;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Drone extends Enemy {

    /**
     * @return the ramBehavior
     */
    public Ram getRamBehavior() {
        return ramBehavior;
    }

    /**
     * @return the wanderBehavior
     */
    public Wander getWanderBehavior() {
        return wanderBehavior;
    }

    public final Ram ramBehavior;

    public final Wander wanderBehavior;

    private double tolerance = 200.0;

    public Drone() {
        speed.setBaseValue(50.0);
        this.wanderBehavior = new Wander();
        this.ramBehavior = new Ram();
        Circle body = new Circle(5);
        shapes.add(body);
        colliders.add(body);
        setBehavior(wanderBehavior);
        body.setFill(Color.GREEN);

    }

    public class Ram implements EnemyBehavior {

        /**
         * @return the targetDir
         */
        public Point2D getTargetDir() {
            return targetDir;
        }

        /**
         * @param targetDir the targetDir to set
         */
        public void setTargetDir(Point2D targetDir) {
            this.targetDir = targetDir;
        }

        private double time = 0.0;
        private double timeLimit = 1.5;
        private Point2D targetDir = Point2D.ZERO;
        private DoubleModifier.Multiplier ramMult = new DoubleModifier.Multiplier(2.0);

        @Override
        public void apply(Player player, double dt) {

            if (time == 0.0) {

                direction = targetDir;
                speed.addModifier(1, ramMult);

            }
            time += dt;
            System.out.println(time);
            if (time >= timeLimit) {
                time = 0.0;
                setBehavior(getWanderBehavior());
            }
        }

        @Override
        public void cleanup(Enemy enemy) {
            speed.removeModifier(1, ramMult);
            time = 0.0;
        }

        /**
         * @return the timeLimit
         */
        public double getTimeLimit() {
            return timeLimit;
        }

        /**
         * @param timeLimit the timeLimit to set
         */
        public void setTimeLimit(double timeLimit) {
            this.timeLimit = timeLimit;
        }

        /**
         * @return the ramMult
         */
        public double getRamMult() {
            return ramMult.value;
        }

        /**
         * @param ramMult the ramMult to set
         */
        public void setRamMult(double ramMult) {
            this.ramMult.value = ramMult;
        }

    }

    public class Wander implements EnemyBehavior {

        double time = 0.0;
        double swapTime = 3.0;

        @Override
        public void apply(Player player, double dt) {
            Point2D pLoc = player.localToParent(player.getTranslateX(), player.getTranslateY());
            Point2D eLoc = new Point2D(getTranslateX(), getTranslateY());
            Parent current = Drone.this;
            while (!(current instanceof GameState)) {
                eLoc = current.localToParent(eLoc);
                current = current.getParent();
            }
            Point2D diff = pLoc.subtract(eLoc);
            System.out.println(diff);
            System.out.println(diff.dotProduct(diff));
            if (time == 0.0) {

                double direction = Math.random() * 360.0;

                setDirection(new Point2D(Math.cos(direction), -Math.sin(direction)));
            }
            time += dt;
            if (diff.dotProduct(diff) < tolerance * tolerance) {
                ramBehavior.targetDir = pLoc.subtract(eLoc).normalize();
                setBehavior(getRamBehavior());
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

    public void update(double dt) {
        super.update(dt);
        Bounds pBounds = getBoundsInParent();
        Bounds bounds = new Rectangle(0, 0, 800, 600).getBoundsInLocal();

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
