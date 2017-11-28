/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import geometric.smash.property.Property;
import java.util.ArrayList;

/**
 *
 * @author AceForce
 */
public abstract class Enemy extends GameEntity {

    /**
     * @return the pointValue
     */
    public Property<Integer> getPointValue() {
        return pointValue;
    }

    /**
     * @return the levelCost
     */
    public Property<Double> getSpawnCost() {
        return spawnCost;
    }

    private Player player;

    private EnemyBehavior behavior;

    private Property<Integer> pointValue = new Property<Integer>(0);
    private Property<Double> spawnCost = new Property<Double>(0.0);

    private int primaryMax = 0, secondaryMax = 0;

    private final ArrayList<Weapon> primaryWeapons = new ArrayList<>(), secondaryWeapons = new ArrayList<>();

    @Override
    public void preUpdate(double dt) {
        if (behavior != null) {
            behavior.apply(player, dt);
        }
    }

    @Override
    public void postUpdate(double dt) {

    }

    public void addPrimaryWeapon(Weapon w) {
        if (getPrimaryWeapons().size() < getPrimaryMax()) {
            getPrimaryWeapons().add(w);
        }
    }

    public abstract void arrangePrimaryWeapons();

    public void addSecondaryWeapon(Weapon w) {
        if (getSecondaryWeapons().size() < getSecondaryMax()) {
            getSecondaryWeapons().add(w);
        }
    }

    public abstract void arrangeSecondaryWeapons();

    /**
     * @return the behavior
     */
    public final EnemyBehavior getBehavior() {
        return behavior;
    }

    /**
     * @param behavior the behavior to set
     */
    public final void setBehavior(EnemyBehavior behavior) {
        if (this.behavior != null) {
            this.behavior.cleanup(this);
        }
        this.behavior = behavior;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the primaryWeapons
     */
    protected ArrayList<Weapon> getPrimaryWeapons() {
        return primaryWeapons;
    }

    /**
     * @return the secondaryWeapons
     */
    protected ArrayList<Weapon> getSecondaryWeapons() {
        return secondaryWeapons;
    }

    /**
     * @param primaryMax the primaryMax to set
     */
    protected void setPrimaryMax(int primaryMax) {
        this.primaryMax = primaryMax;
    }

    /**
     * @param secondaryMax the secondaryMax to set
     */
    protected void setSecondaryMax(int secondaryMax) {
        this.secondaryMax = secondaryMax;
    }

    /**
     * @return the primaryMax
     */
    public int getPrimaryMax() {
        return primaryMax;
    }

    /**
     * @return the secondaryMax
     */
    public int getSecondaryMax() {
        return secondaryMax;
    }

}
