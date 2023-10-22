package org.example;

import org.example.algorithms.Garay;
import org.example.messaging.Consumer;
import org.example.messaging.Coordinator;
import org.example.messaging.Producer;
import org.example.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        //PROCESS CODE

        Consumer consumer = new Consumer("tcp://127.0.0.1:61616", "topic",Integer.parseInt(args[0]),Integer.parseInt(args[4]),Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[5]));
        consumer.Initialize(consumer);
        //Coordinator coordinator1 = new Coordinator(args[1], Integer.parseInt(args[0]),Integer.parseInt(args[2]));
        //Coordinator.CanStart();
        try{
            Thread.sleep(1500*7);
        }catch(Exception ignored){}
        Garay.GarayInitilize(Integer.parseInt(args[4]));


        //COORDINATOR CODE

       /*Coordinator coordinator1 = new Coordinator(args[1], Integer.parseInt(args[0]), Integer.parseInt(args[2]));
       coordinator1.start(Integer.parseInt(args[0]),Integer.parseInt(args[3]),Integer.parseInt(args[2]));*/



    }

}