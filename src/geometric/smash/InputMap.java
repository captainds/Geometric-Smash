/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Corithian
 */
public class InputMap {

    public static enum KeyAction {
        NONE, PRESSED, HELD, RELEASED
    }

    private static final HashMap<KeyCode, KeyAction> INPUT_MAP = new HashMap<>();
    private static final HashMap<KeyCode, EventType<KeyEvent>> INPUTS = new HashMap<>();

    private static final EventHandler<KeyEvent> HANDLER = (KeyEvent ke) -> {
        ke.consume();
        System.out.println(ke);
        INPUTS.put(ke.getCode(), ke.getEventType());

    };

    public static boolean isPressed(KeyCode kc) {
        return INPUT_MAP.get(kc) == KeyAction.PRESSED;
    }

    public static boolean isReleased(KeyCode kc) {
        return INPUT_MAP.get(kc) == KeyAction.RELEASED;
    }

    public static boolean isHeld(KeyCode kc) {
        return INPUT_MAP.get(kc) == KeyAction.HELD;
    }

    public static EventHandler<KeyEvent> getHandler() {
        return HANDLER;
    }

    public static void processInputs() {
        INPUTS.forEach((KeyCode kc, EventType<KeyEvent> et) -> {
            switch (INPUT_MAP.getOrDefault(kc, KeyAction.NONE)) {
                case NONE:
                    if (et == KeyEvent.KEY_PRESSED) {
                        INPUT_MAP.put(kc, KeyAction.PRESSED);
                    }
                    break;
                case PRESSED:
                case HELD:
                    if (et == KeyEvent.KEY_PRESSED) {
                        INPUT_MAP.put(kc, KeyAction.HELD);
                    } else if (et == KeyEvent.KEY_RELEASED) {
                        INPUT_MAP.put(kc, KeyAction.RELEASED);
                    }
                    break;
                case RELEASED:

                    INPUT_MAP.put(kc, KeyAction.NONE);
                    break;
            }
            System.out.println(kc + " = " + et.getName() + ", " + INPUT_MAP.get(kc));
        });
        INPUTS.clear();
    }

}
