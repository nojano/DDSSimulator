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

        //CODICE DEI PROCESSI

        Consumer consumer = new Consumer("tcp://127.0.0.1:61616", "topic", Integer.parseInt(args[0]));
        consumer.Initialize(consumer);
        try{
            Thread.sleep(1000);
        }catch(Exception ignored){}
        System.out.println("Give me 1 minute please");
        try{
            Thread.sleep(60000);
        }catch(Exception ignored){}
        System.out.println("I'm checking that everything is ok");
        Coordinator coordinator1 = new Coordinator(args[1], args[2]);
        Coordinator.CanStart();
        Garay.start(Integer.parseInt(args[0]),Integer.parseInt(args[4]), consumer);



        //CODICE DEL COORDINATORE
     /*   try{
        Thread.sleep(5000);
        }catch(Exception ignored){}
       Coordinator coordinator1 = new Coordinator(args[1], args[2]);
       coordinator1.start(Integer.parseInt(args[0]),Integer.parseInt(args[3]));
*/

    }

}