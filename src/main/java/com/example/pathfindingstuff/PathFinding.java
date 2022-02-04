package com.example.pathfindingstuff;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PathFinding {
//Each 'tapped' node yet to be scanned itself will be added to open nodes.
    ArrayList<Node> openNodes = new ArrayList<Node>();

//Start node x and y indexes can only be set through the start node index method
    private int[] startNodeIndex;
    public int startNodeXIndex;
    public int startNodeYIndex;
    private int[] endNodeIndex;
    public int endNodeXIndex;
    public int endNodeYIndex;
    private static boolean targetReached;

//GETTERS AND SETTERS
//startNodeIndex
    public int[] getStartNodeIndex(){
        return startNodeIndex;
    }
    public void setStartNodeIndex(int[] input){
        startNodeIndex = input;
        startNodeXIndex = input[0];
        startNodeYIndex = input[1];
    }
//endNodeIndex
    public int[] getEndNodeIndex() {
        return endNodeIndex;
    }
    public void setEndNodeIndex(int[] endNodeIndex) {
        this.endNodeIndex = endNodeIndex;
        endNodeXIndex = endNodeIndex[0];
        endNodeYIndex = endNodeIndex[1];
    }

    //Declaring all modifiers for node scanning
    private int[] mod1 = new int[]{-1,-1};
    private int[] mod2 = new int[]{0,-1};
    private int[] mod3 = new int[]{1,-1};
    private int[] mod4 = new int[]{-1,0};
    private int[] mod5 = new int[]{1,0};
    private int[] mod6 = new int[]{-1,1};
    private int[] mod7 = new int[]{0,1};
    private int[] mod8 = new int[]{1,1};
    private int[][]modifiers = new int[][]{mod1,mod2,mod3,mod4,mod5,mod6,mod7,mod8};

//scanning the node is calculating it's values, tapping th enode is turning it red. Will not scan nodes that are not traversable.
//WIll return and set bool if reached the target.
    public void tapNode(Node thisNode){

        for(int i = 0; i < modifiers.length; i++) {
            //check is the index is in bounds before assigning a nodeIndexValue MAKE THIS INTO ITS OWN METHOD
            int[] scannedNodeIndex = new int[]{thisNode.nodeIndex[0]+modifiers[i][0],thisNode.nodeIndex[1]+modifiers[i][1]};
            int scannedXIndex = scannedNodeIndex[0];
            int scannedYIndex = scannedNodeIndex[1];
            if((scannedXIndex>NodeMap.map.length || scannedYIndex>NodeMap.map[0].length)||(scannedXIndex<0||scannedYIndex<0)){
                System.out.println("Index " + scannedXIndex + "," + scannedYIndex + " out of bounds.");
                continue;
            }
            if(NodeMap.map[scannedXIndex][scannedYIndex].getAlreadyScanned()){
                System.out.println("Node already scanned");
                continue;
            }
            Node scannedNode = NodeMap.map[scannedXIndex][scannedYIndex];
            //DEBUG LINE
            System.out.println("Scanning node at " + scannedXIndex + " " + scannedYIndex);
            if (scannedXIndex == endNodeXIndex && scannedYIndex == endNodeYIndex){
                targetReached = true;
                //DEBUG LINE
                System.out.println("End point reached");
                NodeMap.map[endNodeXIndex][endNodeYIndex].setPreviousNode(thisNode.nodeIndex);
                return;
            }
            //Check if scanned node is traversable and HAS NOT been scanned..
            if(NodeMap.map[scannedXIndex][scannedYIndex] != null && scannedNode.getIsTraversable()) {
                //Calculate and set G cost
                scannedNode.setGCost(calculateDistance(scannedNodeIndex, startNodeIndex));
                System.out.println("G cost is " + scannedNode.getGCost());
                //Calculate and set H cost
                scannedNode.setHCost(calculateDistance(scannedNodeIndex, endNodeIndex));
                System.out.println("H cost is " + scannedNode.getHCost());
                //Calculate F cost
                scannedNode.setFCost();
                System.out.println("F cost is " + scannedNode.getFCost());
                //set each scanned node's 'previousNode' to 'thisNode'
                scannedNode.setPreviousNode(thisNode.nodeIndex);
                //mark npde as scanned by changing colour and 'alreadyScanned' boolean
                scannedNode.setFill(Color.RED);
                scannedNode.setAlreadyScanned(true);
                //add node to open nodes
                openNodes.add(scannedNode);

                scannedNode.drawValues();
            }
        }
        //mark this node as tapped by changing the color to red
        thisNode.setFill(Color.RED);
    }

//Checks through the openNode arrayList for the node with the lowest fCost to tap next.. will be called as tapNode(nextNode());
    private int nextNode(){
        System.out.println("Finding next node");
        int lowestFCostIndex = 0;
        int lowestFCost = openNodes.get(0).getFCost();

        for (int i = 0; i<openNodes.size(); i++){
            if(openNodes.get(i).getFCost() < lowestFCost){
                lowestFCost = openNodes.get(i).getFCost();
                lowestFCostIndex = i;
            }
        }
        System.out.println("Found next node with an f cost of " + lowestFCost);
        return lowestFCostIndex;
    }

    private void drawPath(){
        System.out.println("Drawing path");
        ArrayList<Node> finalPath = new ArrayList<Node>();
        boolean reachedStart = false;
        Node currentNode = NodeMap.map[endNodeXIndex][endNodeYIndex];
        while (reachedStart!=true){
            if (currentNode.nodeIndex[0] == startNodeXIndex && currentNode.nodeIndex[1] == startNodeYIndex){
                currentNode.setFill(Color.GREEN);
                reachedStart = true;
            } else {
                currentNode.setFill(Color.BLUE);
                System.out.println(currentNode.nodeIndex[0] + " " + currentNode.nodeIndex[1] + "painted");
                currentNode = NodeMap.map[currentNode.getPreviousNode()[0]][currentNode.getPreviousNode()[1]];
            }
        }
        //print out final path values
        System.out.println("Path drawn");
    }
//While bool reached target == false generatePath will continue scanning nodes.
    public void generatePath(int[] startNodeIndexInput, int[] endNodeIndexInput){
        setStartNodeIndex(startNodeIndexInput);
        setEndNodeIndex(endNodeIndexInput);
        //tap first node
        tapNode(NodeMap.map[startNodeXIndex][startNodeYIndex]);
        //while targetReached = false keep scanning
        while(targetReached != true){
            int nextIndex = nextNode();
            tapNode(openNodes.get(nextIndex));
            openNodes.remove(nextIndex);
        }
        //Function for drawing path
        drawPath();
    }

    public int calculateDistance(int[] originCoord, int[] targetCoord){
        int[] currentCoord = originCoord;
        int distance = 0;
        //While both the x AND y values are different, travel diagonally node by node and add the diagonal distance
        while(currentCoord[0]!=targetCoord[0] && currentCoord[1]!=targetCoord[1]){
            if(currentCoord[0]<targetCoord[0]){
                currentCoord[0]++;
            } else{
                currentCoord[0]--;
            }
            if(currentCoord[1]<targetCoord[1]){
                currentCoord[1]++;
            } else{
                currentCoord[1]--;
            }
            distance+=14;
        }
        //With either x OR y equal to the target now moving horizontally or vertically, adding 1 to the hCost each time.
        while(currentCoord[0]!=targetCoord[0]){
            if (currentCoord[0]<targetCoord[0]){
                currentCoord[0]++;
            } else {
                currentCoord[0]--;
            }
            distance+=10;
        }
        while(currentCoord[1]!=targetCoord[1]){
            if(currentCoord[1]<targetCoord[1]){
                currentCoord[1]++;
            } else{
                currentCoord[1]--;
            }
            distance+=10;
        }
        return distance;
    }

    private void debugPrintOpenNodes(){
        for(int i =0; i< openNodes.size(); i++){
            System.out.println(openNodes.get(i).nodeIndex[0]+ " " + openNodes.get(i).nodeIndex[1]);
            System.out.println(openNodes.get(i).getFCost());
        }
    }
}
