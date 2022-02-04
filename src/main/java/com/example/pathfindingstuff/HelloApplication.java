package com.example.pathfindingstuff;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class HelloApplication extends Application {
    public static NodeMap nodeMap;
    public PathFinding pathfinder = new PathFinding();
    public static Pane pane;

    @Override
    public void start(Stage stage) throws IOException {
        setupApp(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    //Create GUI interface for user to enter the amount of width and height rects
    public void setupApp(Stage stage) {
        stage.setTitle("Pathfinding");
        TextField textField1 =new TextField("How may boxes wide?");
        TextField textField2 = new TextField("How many boxes high?");
        Button startButton = new Button("Submit values");
        GridPane grid = new GridPane();
        grid.addRow(0,textField1);
        grid.addRow(1, textField2);
        grid.addRow(2,startButton);
        Scene setupScene = new Scene(grid, 200,300, true);
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startApp(Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()), stage);
            }
        });
        stage.setScene(setupScene);
        stage.show();
    }

    //Method to submit the input values and generate the app
    public void startApp(int width, int height, Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        nodeMap = new NodeMap();
        Button startButton = new Button("Start Pathfinding.");
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!nodeMap.startSelection && !nodeMap.endSelection) {
                    pathfinder.generatePath(nodeMap.startNodePos, nodeMap.endNodePos);
                }
            }
        });


        pane = new Pane();
        Scene scene = new Scene(pane, 1024, 800, true);
        //changes the nodemap input values
        nodeMap.inputNodeMapVals(width,height,scene);
        //Draws the nodemap from the values held in the nodemap class
        nodeMap.generateNodeMap(pane);
        pane.getChildren().add(startButton);


        stage.setTitle("Pathfinding");

        stage.setScene(scene);
        stage.show();
    }

}