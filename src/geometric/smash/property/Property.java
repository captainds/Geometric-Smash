/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 *
 * @author AceForce
 * @param <Type>
 */
public class Property<Type> {

    private Type baseValue;

    private boolean dirty;

    private final TreeMap<Integer, ArrayList<Modifier<Type>>> modifiers;

    private final HashSet<Modifier<Type>> modifierSet;

    private final Applier<Type> applier;

    public Property(Type value) {
        this.baseValue = value;
        dirty = false;
        modifiers = new TreeMap<>();
        this.applier = new Applier<>(baseValue);
        modifierSet = new HashSet<>();
    }

    public void addModifier(Integer priority, Modifier<Type> modifier) {
        if (modifierSet.contains(modifier)) {
            return;
        }
        ArrayList<Modifier<Type>> modifierList = modifiers.get(priority);
        if (modifierList == null) {
            modifierList = new ArrayList<>();
            modifiers.put(priority, modifierList);
        }
        modifierList.add(modifier);
        modifierSet.add(modifier);

        dirty = true;
    }

    public void removeModifier(Modifier<Type> modifier) {
        if (!modifierSet.contains(modifier)) {
            return;
        }

        modifiers.forEach((Integer i, ArrayList<Modifier<Type>> modifierList) -> {
            modifierList.remove(modifier);
        });
        modifierSet.remove(modifier);
        dirty = true;

    }

    public void removeModifier(Integer priority, Modifier<Type> modifier) {
        if (!modifierSet.contains(modifier)) {
            return;
        }

        ArrayList<Modifier<Type>> modifierList = modifiers.get(priority);
        if (modifierList != null) {
            modifierList.remove(modifier);
        }
        modifierSet.remove(modifier);
        dirty = true;
    }

    public final Type getBaseValue() {
        return baseValue;
    }

    /**
     * @param baseValue the baseValue to set
     */
    public final void setBaseValue(Type baseValue) {
        this.baseValue = baseValue;
        dirty = true;
    }

    public final Type getValue() {
        if (dirty) {
            applier.value = baseValue;
            modifiers.forEach(applier);
            dirty = false;
        }
        return applier.value;

    }

    private class Applier<Type> implements BiConsumer<Integer, ArrayList<Modifier<Type>>> {

        public Type value;

        public Applier(Type value) {
            this.value = value;
        }

        @Override
        public void accept(Integer t, ArrayList<Modifier<Type>> modifierList) {
            for (Modifier<Type> modifier : modifierList) {
                value = modifier.apply(value);
            }
        }

    }

    public interface Modifier<Type> {

        Type apply(Type value);
    }
}
