/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash.property;

import geometric.smash.DoubleModifier;
import geometric.smash.IntegerModifier;
import geometric.smash.Property;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AceForce
 */
public class PropertyTest {

    public PropertyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Property<Double> instance = new Property<>(5.239);
        instance.addModifier(3, new DoubleModifier.Adder(2.0));
        instance.addModifier(0, new DoubleModifier.Multiplier(2.32));
        instance.addModifier(2, new DoubleModifier.Divider(2.23));
        instance.addModifier(1, new DoubleModifier.Adder(8.0));
        double expResult = (((5.239 * 2.32) + 8.0) / 2.23) + 2.0;
        double result = instance.getValue();
        
        assertEquals(expResult, result, 0.001);
        System.out.format("expected: %f\n\tactual: %f\n", expResult, result);

    }


    @Test
    public void test2GetValue() {
        System.out.println("getValue");
        Property<Integer> instance = new Property<>(5);
        instance.addModifier(2, new IntegerModifier.Divider(3));
        instance.addModifier(0, new IntegerModifier.Multiplier(2));
        instance.addModifier(3, new IntegerModifier.Adder(2));
        instance.addModifier(1, new IntegerModifier.Adder(8));
        
        int expResult = (((5 * 2) + 8) / 3) + 2;
        int result = instance.getValue();
        
        assertEquals(expResult, result);
        System.out.format("expected: %d\n\tactual: %d\n", expResult, result);

    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("geometric.smash.property.PropertyTest");
    }
}
