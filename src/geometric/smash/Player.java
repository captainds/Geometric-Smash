/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Corithian
 */
public class Player extends GameEntity {

    private DoubleModifier.Divider focusSpeed = new DoubleModifier.Divider(1.5);

    private Weapon weapon;

    public Player() {
        this.speed.setBaseValue(150.0);
        this.setDirection(new Point2D(1.0, 0.0));
        Rectangle r = new Rectangle(36, 36);
        r.setX(-r.getWidth() / 2);
        r.setY(-r.getHeight() / 2);
        shapes.add(r);
        r.setFill(Color.BLUE);
        Circle coll = new Circle(0.0, 0.0, 3.0);
        shapes.add(coll);
        colliders.add(coll);
        if (new Random().nextBoolean()) {
            weapon = new SpreadShot(5, -60, 60);
            weapon.setBaseBurstValue(3);
            weapon.getBurstTime().setBaseValue(0.36);
            weapon.getCooldown().setBaseValue(0.8);
        } else {
            weapon = new Peashooter();
            weapon.setBaseBurstValue(5);
            weapon.getBurstTime().setBaseValue(0.12);
            weapon.getCooldown().setBaseValue(0.5);
        }
        
        weapon.setPlayerOwned(true);
        getChildren().add(weapon);
        coll.setFill(((Color) shapes.get(0).getFill()).invert());

    }

    private void updateDirection() {
        boolean left = InputMap.isPressedOrHeld(KeyCode.A);
        boolean right = InputMap.isPressedOrHeld(KeyCode.D);
        boolean up = InputMap.isPressedOrHeld(KeyCode.W);
        boolean down = InputMap.isPressedOrHeld(KeyCode.S);
        setDirection(Point2D.ZERO.add(left != right ? (left ? -1.0 : 1.0) : 0.0,
                up != down ? (up ? -1.0 : 1.0) : 0.0));

    }

    private void updateFocus() {
        boolean focused = InputMap.isPressedOrHeld(KeyCode.SHIFT);
        if (focused) {
            speed.addModifier(0, focusSpeed);
        } else {
            speed.removeModifier(0, focusSpeed);
        }
    }

    private void updateWeapon(double dt) {

        boolean left = InputMap.isPressedOrHeld(KeyCode.LEFT);
        boolean right = InputMap.isPressedOrHeld(KeyCode.RIGHT);
        boolean up = InputMap.isPressedOrHeld(KeyCode.UP);
        boolean down = InputMap.isPressedOrHeld(KeyCode.DOWN);
        weapon.setTrigger(false);
        if ((left != right) != (up != down)) {

            if (left) {
                weapon.setDirection(new Point2D(-1.0, 0.0));
            } else if (up) {
                weapon.setDirection(new Point2D(0.0, -1.0));
            } else if (down) {
                weapon.setDirection(new Point2D(0.0, 1.0));
            } else if (right) {
                weapon.setDirection(new Point2D(1.0, 0));
            }
            weapon.setTrigger(true);
        }
        weapon.update(dt);
    }

    @Override
    public void preUpdate(double dt) {
        updateDirection();
        updateFocus();
        updateWeapon(dt);
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
     * @return the weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
