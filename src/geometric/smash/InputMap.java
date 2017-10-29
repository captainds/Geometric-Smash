/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Corithian
 */
public class InputMap {

    private static final HashMap<KeyCode, EventType<KeyEvent>> INPUTS = new HashMap<>();
    private static final EventHandler<KeyEvent> HANDLER = (KeyEvent ke) -> {
        ke.consume();
        INPUTS.put(ke.getCode(), ke.getEventType());
    };

    public static EventType<KeyEvent> getState(KeyCode kc) {
        return INPUTS.get(kc);
    }

    public static EventHandler<KeyEvent> getHandler() {
        return HANDLER;
    }

}
