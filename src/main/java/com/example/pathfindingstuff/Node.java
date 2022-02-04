package com.example.pathfindingstuff;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Node extends Rectangle {
    public double xCoord;
    public double yCoord;

//gCost being distance from starting node
    private int gCost;
//hCost being distance from end node
    private int hCost;
    private int fCost;
    public int[] nodeIndex;
//previousNode being a coordinate modifier to indicate the direction of the previous node in the found path.
    private int[] previousNode;
    private boolean isTraversable = true;
    private boolean isStartNode= false;
    private boolean isEndNode= false;
    private boolean alreadyScanned = false;
//this is ugly but I'm going to add a click listener to the first Rectangle that will change a bool based on listening for mouse button up and down.
    private static boolean isMouseButtonDown = false;

//CONSTRUCTOR (from Rectangle constructor)
    public Node(double xPosition, double yPosition, double width, double height){
        super(xPosition,yPosition,width, height);
        xCoord = xPosition;
        yCoord = yPosition;
    }

//GETTERS AND SETTERS

//distanceFromOrigin
    public double getGCost(){
        return gCost;
    }
    public void setGCost(int gCost){
        this.gCost = gCost;
    }
//distanceFromDestination
    public double getHCost() {
        return hCost;
    }
    public void setHCost(int hCost) {
        this.hCost = hCost;
    }
//fCost being gCost+hCost
    public int getFCost(){
        return fCost;
    }
    public void setFCost(){
        fCost = gCost + hCost;
    }

//previousNode
    public int[] getPreviousNode() {
        return previousNode;
    }
    public void setPreviousNode(int[] previousNode) {
        this.previousNode = previousNode;
    }
//isTraversable
    public boolean getIsTraversable(){
        return isTraversable;
    }
    public void setIsTraversable(boolean input){
        isTraversable = input;
    }
    //setTraversable will be called from the outside for now to ALSO CHANGE THE COLOUR of nodes that aren't traversable.
    public void setTraversable(boolean inputBool){
        setIsTraversable(inputBool);
        if (!isTraversable){
            this.setFill(Color.BLACK);
        }
    }
//isStartNode
    public boolean getIsStartNode(){
        return isStartNode;
    }
    public void setIsStartNode(boolean input){
        isStartNode = input;
    }
//isEndNode
    public boolean getIsEndNode(){
        return isEndNode;
    }
    public void setIsEndNode(boolean input){
        isEndNode = input;
    }
//alreadyScanned
    public boolean getAlreadyScanned(){
        return alreadyScanned;
    }
    public void  setAlreadyScanned(boolean input){
        alreadyScanned = input;
    }

//To be called when Node values have been calculated. WIll draw the values.
    public void drawValues(){

        String input = "G Cost : " + getGCost() + "\nH Cost : " + getHCost() + "\nF Cost : " + getFCost();
        Text text = new Text(this.xCoord+10, this.yCoord+10, input);
        HelloApplication.pane.getChildren().add(text);
    }

//if the mouse enters the node AND the mouse button is pressed down node = !traversable
    public void setMouseListeners(){
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setTraversable(false);
            }
        });
    }
}
