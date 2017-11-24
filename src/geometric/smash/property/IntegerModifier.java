/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash.property;

public class IntegerModifier {

    public static class Adder implements Property.Modifier<Integer> {

        public int value;
        
        public Adder(int value) {
            this.value = value;
        }

        @Override
        public Integer apply(Integer value) {
            return this.value + value;
        }

    }

    public static class Multiplier implements Property.Modifier<Integer> {

        public int value;

        public Multiplier(int value) {
            this.value = value;
        }
        
        @Override
        public Integer apply(Integer value) {
            return this.value * value;
        }
        

    }
    
    public static class Divider implements Property.Modifier<Integer> {

        public int value;

        public Divider(int value) {
            this.value = value;
        }
        @Override
        public Integer apply(Integer value) {
            return value / this.value;
        }

    }
    
    public static class Subtractor implements Property.Modifier<Integer> {

        public int value;

        public Subtractor(int value) {
            this.value = value;
        }
        @Override
        public Integer apply(Integer value) {
            return value - this.value;
        }

    }

}
