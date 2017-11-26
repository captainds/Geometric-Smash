/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.DoubleModifier;
import geometric.smash.property.Property;
import java.util.ArrayList;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author AceForce
 */
public class Weapon extends GameEntity {

    /**
     * @return the costMultiplier
     */
    public DoubleModifier.Multiplier getCostMultiplier() {
        return costMultiplier;
    }

    private final DoubleModifier.Multiplier costMultiplier = new DoubleModifier.Multiplier(1.1);

    /**
     * @return the burstCount
     */
    protected int getBurstCount() {
        return burstCount;
    }

    /**
     * @param burstCount the burstCount to set
     */
    protected void setBurstCount(int burstCount) {
        this.burstCount = burstCount;
    }

    /**
     * @return the burstTime
     */
    protected Property<Double> getBurstTime() {
        return burstTime;
    }

    /**
     * @return the burstValue
     */
    protected int getBurstValue() {
        return burstValue.getValue();
    }

    /**
     * @param burstValue the burstValue to set
     */
    protected void setBaseBurstValue(int burstValue) {
        this.burstValue.setBaseValue(burstValue);
    }

    /**
     * @return the cooldownTimer
     */
    protected double getCooldownTimer() {
        return cooldownTimer;
    }

    /**
     * @return the burstTimer
     */
    protected double getBurstTimer() {
        return burstTimer;
    }

    private final Property<Double> cooldown;

    private final Property<Double> burstTime;

    private final Property<Integer> burstValue;

    private int burstCount;

    private double cooldownTimer;

    private double burstTimer;

    private boolean firing;

    protected final ArrayList<BulletAttribute> bulletAttributes;

    private boolean trigger;

    private boolean fired;

    private boolean fullBurst;

    public Weapon() {
        this.firing = false;
        trigger = false;
        cooldown = new Property<>(1.0);
        burstTime = new Property<>(1.0);
        burstValue = new Property<>(1);
        burstCount = 0;
        cooldownTimer = cooldown.getValue();
        burstTimer = burstTime.getValue();
        bulletAttributes = new ArrayList<>();
    }

    public ArrayList<Bullet> fireBullets() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        Node current = this.getParent();
        Point2D wLoc = this.localToParent(getTranslateX(), getTranslateY());
        while (current != getGameState()) {
            wLoc = current.localToParent(wLoc);
            current = current.getParent();
        }
        for (BulletAttribute ba : bulletAttributes) {
            ba.setDirection(this.getDirection());
            bullets.add(new Bullet(wLoc, ba));

        }
        fired = true;
        return bullets;
    }

    @Override
    public void preUpdate(double dt) {
        fired = false;
        fullBurst = false;
        if (!firing) {
            cooldownTimer -= dt;
            if (!trigger) {
                return;
            }
            if (cooldownTimer <= 0.0) {
                cooldownTimer = cooldown.getValue();
                burstTimer = 0.0;
                burstCount = 0;
                firing = true;

            }
        } else {
            burstTimer -= dt;
            if (burstTimer <= 0.0) {
                burstTimer = burstTime.getValue();
                ArrayList<Bullet> bullets = fireBullets();
                for (Bullet b : bullets) {
                    fireEvent(new GameEvent(GameEvent.ADD, b));
                }
                if (++burstCount >= burstValue.getValue()) {
                    firing = false;
                    fullBurst = true;
                }
            }
        }
    }

    @Override
    public void postUpdate(double dt) {

    }

    /**
     * @return the currentTime
     */
    protected double getCurrentTime() {
        return cooldownTimer;
    }

    /**
     * @param currentTime the currentTime to set
     */
    protected void setCurrentTime(double currentTime) {
        this.cooldownTimer = currentTime;
    }

    /**
     * @return the cooldown
     */
    protected Property<Double> getCooldown() {
        return cooldown;
    }

    /**
     * @return the bulletAttributes
     */
    protected ArrayList<BulletAttribute> getBulletAttributes() {
        return bulletAttributes;
    }

    /**
     * @return the trigger
     */
    protected boolean isTrigger() {
        return trigger;
    }

    /**
     * @param trigger the trigger to set
     */
    protected void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    /**
     * @return the fired
     */
    public boolean isFired() {
        return fired;
    }

    /**
     * @param fired the fired to set
     */
    public void setFired(boolean fired) {
        this.fired = fired;
    }

    /**
     * @return the fullBurst
     */
    public boolean isFullBurst() {
        return fullBurst;
    }

    /**
     * @param fullBurst the fullBurst to set
     */
    public void setFullBurst(boolean fullBurst) {
        this.fullBurst = fullBurst;
    }

}
