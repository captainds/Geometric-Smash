/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Corithian
 */
public class PauseState extends BorderPane {

    public PauseState() {
        System.out.println("PAUSE");
        Text pText = new Text("PAUSED");
        pText.setFont(new Font(64));
        pText.setFill(Color.WHITE);
        pText.setStroke(Color.BLACK);
        setCenter(pText);
        new AnimationTimer() {
            @Override
            public void handle(long l) {

                if (InputMap.isReleased(KeyCode.P)) {
                    fireEvent(new GameEvent(GameEvent.PAUSE));
                    stop();
                }
            }

        }.start();
    }
}
