/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DroneMkI extends Enemy {

    protected final Circle mainBody;

    /**
     * @return the ramBehavior
     */
    public AbstractRam getRamBehavior() {
        return ramBehavior;
    }

    /**
     * @return the wanderBehavior
     */
    public AbstractWander getWanderBehavior() {
        return wanderBehavior;
    }

    protected AbstractRam ramBehavior;

    protected AbstractWander wanderBehavior;

    public DroneMkI() {
        speed.setBaseValue(60.0);
        this.wanderBehavior = new Wander();
        this.ramBehavior = new Ram();
        getPointValue().setBaseValue(100);
        getSpawnCost().setBaseValue(100.0);
        mainBody = new Circle(15);
        mainBody.setCenterX(0.0);
        mainBody.setCenterY(0.0);
        shapes.add(mainBody);
        colliders.add(mainBody);
        setBehavior(wanderBehavior);
        mainBody.setFill(Color.GREEN);
        setHealth(2);

    }

    @Override
    public void arrangePrimaryWeapons() {

    }

    @Override
    public void arrangeSecondaryWeapons() {

    }

    public static abstract class AbstractRam implements EnemyBehavior {

        protected double time = 0.0;
        protected double timeLimit = 1.5;
        protected Point2D targetDir = Point2D.ZERO;
        protected DoubleModifier.Multiplier ramMult = new DoubleModifier.Multiplier(2.0);

    }

    public static abstract class AbstractWander implements EnemyBehavior {

        protected double time = 0.0;
        protected double swapTime = 3.0;
        protected double tolerance = 280.0;

    }

    public class Ram extends AbstractRam {

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

        @Override
        public void apply(Player player, double dt) {

            if (time == 0.0) {

                setDirection(targetDir);
                speed.addModifier(1, ramMult);

            }
            time += dt;
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

    public class Wander extends AbstractWander {

        @Override
        public void apply(Player player, double dt) {
            Point2D pLoc = player.localToParent(player.getTranslateX(), player.getTranslateY());
            Point2D eLoc = new Point2D(getTranslateX() + mainBody.getCenterX(), getTranslateY() + mainBody.getCenterY());
            Parent current = DroneMkI.this;
            while (current != getGameState()) {
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

    /**
     * @param ramBehavior the ramBehavior to set
     */
    protected void setRamBehavior(Ram ramBehavior) {
        this.ramBehavior = ramBehavior;
    }

    /**
     * @param wanderBehavior the wanderBehavior to set
     */
    protected void setWanderBehavior(Wander wanderBehavior) {
        this.wanderBehavior = wanderBehavior;
    }
}
