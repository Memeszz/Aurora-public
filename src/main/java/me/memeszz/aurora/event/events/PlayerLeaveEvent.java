package me.memeszz.aurora.event.events;

public class PlayerLeaveEvent {

    private final String name;

    public PlayerLeaveEvent(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

}
