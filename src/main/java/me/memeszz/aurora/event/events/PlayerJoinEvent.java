package me.memeszz.aurora.event.events;

public class PlayerJoinEvent {
    private final String name;

    public PlayerJoinEvent(String n){
        name = n;
    }

    public String getName(){
        return name;
    }
}
