/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Corithian
 */
public class MainPanel extends BorderPane{
    private final EventHandler<ActionEvent> onStart;
    private final MainMenu menu;
    public MainPanel() {
        this.onStart = (ActionEvent e) -> {
            
        };
        this.menu = new MainMenu(onStart);
        setCenter(menu);
    }
   
}
