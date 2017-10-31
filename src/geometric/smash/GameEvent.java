/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Corithian
 */
public class GameEvent extends Event {

    public static final EventType<GameEvent> START = new EventType<>(Event.ANY, "GAME_START");
    public static final EventType<GameEvent> PAUSE = new EventType<>(Event.ANY, "GAME_PAUSE");

    public GameEvent(EventType<GameEvent> et) {
        super(et);
    }

}
