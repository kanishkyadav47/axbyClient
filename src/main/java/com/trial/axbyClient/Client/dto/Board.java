package com.trial.axbyClient.Client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Board {

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("nStacks")
    private int nStacks;

    @JsonProperty("inARow")
    private int inARow;

    @JsonProperty("moveMessage")
    private String moveMessage;

    @JsonProperty("state")
    private String[] state;

    public Board(){
    }

    public Board(int depth, int nStacks, int inARow, String moveMessage, String[] state) {
        this.depth = depth;
        this.nStacks = nStacks;
        this.inARow = inARow;
        this.moveMessage = moveMessage;
        this.state = state;
    }

    public void display(){
        int n = state.length;
        System.out.println("\n\n");
        for(int i = 0; i < state.length; i++){
            if(i%9 == 0)
                System.out.println("\n");
            if(state[i].equals("Empty"))
                System.out.print("[ ]  ");
            else if (state[i].equals("BLACK"))
                System.out.print("[o]  ");
            else
                System.out.print("[x]  ");
        }
    }


    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getnStacks() {
        return nStacks;
    }

    public void setnStacks(int nStacks) {
        this.nStacks = nStacks;
    }

    public int getInARow() {
        return inARow;
    }

    public void setInARow(int inARow) {
        this.inARow = inARow;
    }

    public String getMoveMessage() {
        return moveMessage;
    }

    public void setMoveMessage(String moveMessage) {
        this.moveMessage = moveMessage;
    }

    public String[] getState() {
        return state;
    }

    public void setState(String[] state) {
        this.state = state;
    }

}
