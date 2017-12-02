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
    public static final EventType<GameEvent> END = new EventType<>(Event.ANY, "GAME_END");
    public static final EventType<GameEvent> NEXT = new EventType<>(Event.ANY, "LEVEL_NEXT");
    public static final EventType<GameEvent> ADD = new EventType<>(Event.ANY, "ENTITY_ADD");
    public static final EventType<GameEvent> REMOVE = new EventType<>(Event.ANY, "ENTITY_REMOVE");
    
    private GameEntity entity;

    public GameEvent(EventType<GameEvent> et) {
        this(et, null);
    }
    public GameEvent(EventType<GameEvent> et, GameEntity e) {
        super(et);
        this.entity = e;
    }

    /**
     * @return the entity
     */
    
    public GameEntity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(GameEntity entity) {
        this.entity = entity;
    }

}
