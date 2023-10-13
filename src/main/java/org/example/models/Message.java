package org.example.models;

public class Message {

    //public static int nextId = 0;
    public int round;
    public int id;

    public String message;

    public Message(int round, int id, String message) {
        this.id = id;
        this.message = message;
        this.round = round;
    }
}
