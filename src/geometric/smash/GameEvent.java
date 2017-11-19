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
    public static final EventType<GameEvent> ADD = new EventType<>(Event.ANY, "ENTITY_ADD");
    public static final EventType<GameEvent> REMOVE = new EventType<>(Event.ANY, "ENTITY_REMOVE");
    
    private GameEntity e;

    public GameEvent(EventType<GameEvent> et) {
        super(et);
    }

    /**
     * @return the e
     */
    public GameEntity getEntity() {
        return e;
    }

    /**
     * @param e the e to set
     */
    public void setEntity(GameEntity e) {
        this.e = e;
    }

}
