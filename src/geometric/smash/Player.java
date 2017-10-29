/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Corithian
 */
public class Player extends GameEntity {

    private double speed = 150.0;
    private Point2D direction;

    public Player() {
        this.direction = new Point2D(0.0, 0.0);
        shapes.add(new Rectangle(20, 20));
        shapes.get(0).setFill(Color.BLUE);
        getChildren().addAll(shapes);
    }

    private void updateDirection() {
        direction = direction.subtract(direction);
        boolean left = InputMap.getState(KeyCode.A) == KeyEvent.KEY_PRESSED;
        boolean right = InputMap.getState(KeyCode.D) == KeyEvent.KEY_PRESSED;
        boolean up = InputMap.getState(KeyCode.W) == KeyEvent.KEY_PRESSED;
        boolean down = InputMap.getState(KeyCode.S) == KeyEvent.KEY_PRESSED;
        direction = direction.add(left != right ? (left ? -1.0 : 1.0) : 0.0,
                up != down ? (up ? -1.0 : 1.0) : 0.0);

    }

    @Override
    public void update(double dt) {
        updateDirection();
        Point2D velocity = direction.multiply(speed * dt);
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());

    }

}
