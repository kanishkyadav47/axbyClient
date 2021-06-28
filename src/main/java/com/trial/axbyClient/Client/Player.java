package com.trial.axbyClient.Client;

public class Player {
    int id;
    String name;
    Marker marker = Marker.WHITE;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        if(id == 1){
            marker = Marker.WHITE;
        }
        else
            marker = Marker.BLACK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    enum Marker{
        WHITE,BLACK,Empty
    }
}
