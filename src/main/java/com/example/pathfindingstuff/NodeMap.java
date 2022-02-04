package com.example.pathfindingstuff;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.io.Console;
import java.util.List;

public class NodeMap {
    public double mapWidth;
    private double mapHeight;
    private double rectWidth;
    private double rectHeight;
    public static Node[][] map;
    public boolean startSelection = true;
    public boolean endSelection = false;
    public boolean obstacleSelection = false;

    public int[] startNodePos;
    public int[] endNodePos;

    //bool to detect when the mouse button is pressed down.
    private boolean mousePress;

    //THe user should set the height and width of the nodemap.
    //the function decidingthose variables will then set the height and width of the node rects accordingly

    public double getMapHeight(){
        return mapHeight;
    }

    public void setMapHeight(int heightVal){
        this.mapHeight = heightVal;
    }

    public double getMapWidth(){
        return mapWidth;
    }

    public void setMapWidth(double widthVal) {
        mapWidth = widthVal;
    }

    public void setRectHeight(double rectHeight) {
        this.rectHeight = rectHeight;
    }

    public double getRectHeight() {
        return rectHeight;
    }

    public void setRectWidth(double rectWidth) {
        this.rectWidth = rectWidth;
    }

    public double getRectWidth() {
        return rectWidth;
    }

    public void inputNodeMapVals(int requiredWidthRects, int requiredHeightRects, Scene scene){
        setMapWidth(requiredWidthRects);
        setMapHeight(requiredHeightRects);
        map = new Node[requiredWidthRects][requiredHeightRects];
        setRectWidth((scene.getWidth()/requiredWidthRects));
        setRectHeight((scene.getHeight()/requiredHeightRects));
    }

    public void generateNodeMap(Pane pane){
        //debug color
        //debug counter
        int counter = 0;

        int currentX = 0;
        int currentY = 0;


        for(int i = 0; i < mapHeight; i++){
            for(int j = 0; j < mapWidth; j++){
                //Rectangle(double x, double y, double width, double height)
                //Creates a new instance of Rectangle with the given position and size.
                Node node = new Node(currentX, currentY, rectWidth, rectHeight);
                node.setStroke(Color.BLACK);
                node.setFill(Color.WHITE);
                //adding the click and drag eventlisteners for changing nodes
                node.setMouseListeners();

                System.out.println("x: " + currentX + " Y: " + currentY);
                pane.getChildren().add(node);
                node.nodeIndex = new int[]{j,i};
                map[j][i] = node;
                currentX+=rectWidth;
            }
            currentX = 0;
            currentY+=rectHeight;
        }
    }

    //unused method sets random start node
    public int[] setStartNode(){
        int randX = (int)Math.floor(Math.random()*mapWidth);
        int randY = (int)Math.floor(Math.random()*mapHeight);
        map[randX][randY].setIsStartNode(true);
        map[randX][randY].setFill(Color.GRAY);
        return new int[]{randX, randY};
    }

    //unused method sets random end node
    public int[] setEndNode(){
        int randX = (int)Math.floor(Math.random()*mapWidth);
        int randY = (int)Math.floor(Math.random()*mapHeight);
        map[randX][randY].setIsEndNode(true);
        map[randX][randY].setFill(Color.GRAY);
        return new int[]{randX, randY};
    }

}


