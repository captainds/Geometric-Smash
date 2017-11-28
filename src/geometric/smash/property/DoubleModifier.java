/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash.property;

public class DoubleModifier {

    public static class Adder implements Property.Modifier<Double> {

        public double value;
        
        public Adder(double value) {
            this.value = value;
        }

        @Override
        public Double apply(Double value) {
            return this.value + value;
        }

    }

    public static class Multiplier implements Property.Modifier<Double> {

        public double value;

        public Multiplier(double value) {
            this.value = value;
        }
        
        @Override
        public Double apply(Double value) {
            return this.value * value;
        }
        

    }
    
    public static class Divider implements Property.Modifier<Double> {

        public double value;

        public Divider(double value) {
            this.value = value;
        }
        @Override
        public Double apply(Double value) {
            return value / this.value;
        }

    }
    
    public static class Subtractor implements Property.Modifier<Double> {

        public double value;

        public Subtractor(double value) {
            this.value = value;
        }
        @Override
        public Double apply(Double value) {
            return value - this.value;
        }

    }

}
