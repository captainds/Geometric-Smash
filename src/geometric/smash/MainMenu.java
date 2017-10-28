/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometric.smash;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Shear;

/**
 *
 * @author Corithian
 */
public class MainMenu extends GridPane {
    private Button startButton;
    private Button exitButton;
    
    public MainMenu (EventHandler<ActionEvent> onStart) {
        VBox buttonBox = new VBox();
        Text geo = new Text("GEOMETRIC" + System.lineSeparator() + "SMASH");
        Font f = new Font(64);
        geo.setFont(f);
        geo.setFill(Color.WHITE);
        this.setStyle("-fx-background-color: black");
        
        startButton = new Button("Start");
        startButton.setMinSize(100, 50);
        exitButton = new Button("Exit");
        exitButton.setMinSize(80, 50);
        
        startButton.setOnAction(onStart);
        exitButton.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        
      
        
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        
        buttonBox.getChildren().addAll(startButton, exitButton);
        RowConstraints [] rowCons = {new RowConstraints(), new RowConstraints(), new RowConstraints(), new RowConstraints()};
        rowCons[0].setPercentHeight(10);
        rowCons[1].setPercentHeight(50);
        rowCons[2].setPercentHeight(30);
        rowCons[3].setPercentHeight(10);
        ColumnConstraints [] colCons = {new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints()};
        colCons[0].setPercentWidth(5);
        colCons[1].setPercentWidth(90);
        colCons[2].setPercentWidth(5);
        this.getRowConstraints().addAll(rowCons);
        this.getColumnConstraints().addAll(colCons);
        this.addColumn(0, new VBox());
        this.addColumn(2, new VBox());
        this.add(new VBox(), 1, 0);
        this.add(geo, 1, 1);
        this.add(buttonBox, 1, 2);
        this.add(new VBox(), 1, 3);
        
    }
}
