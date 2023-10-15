package org.example.messaging;

import org.example.messaging.Producer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.SplittableRandom;

public class Coordinator {
    private static int rounds = 0;
    private static String byzantinePerRounds;
    private static String [] byzantines;

    public static boolean canStart = false;

    public static String byzantineBehaviour;
    public static String byzantineArray;

    public static int [] answers;

    public Coordinator(String howManyByzantinePerRound, int numberOfProcesses, int howManyRounds){
        this.byzantinePerRounds = howManyByzantinePerRound;
        //this.byzantines = new String[Integer.parseInt(howManyByzantinePerRound)];
        /*answers = new int[numberOfProcesses];
        for(int i=0; i< answers.length; i++){
            answers[i]=-1;
        }*/
        /*Consumer consumer = new Consumer("tcp://127.0.0.1:61616", "topic", numberOfProcesses, -1, Integer.parseInt(howManyByzantinePerRound),  howManyRounds);
        consumer.Initialize(consumer);*/
    }

   /* public static void start(int numberOfProcesses, int choiceByzantine, int howManyRounds){
        if(rounds<=howManyRounds){
            System.out.println("Sono dentro");
            boolean allTheSame = true;
            System.out.println("[");
            for (Integer element : answers) {
                System.out.print(element + ", ");
                if (element != rounds) {
                    System.out.println("Not equal");
                    allTheSame = false;
                }
                System.out.println("] and round is  " + rounds);
            }
            if(allTheSame == true){
                System.out.println("They were equal");
                switch(choiceByzantine){
                    case 1:
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer,-1,-1,"NoMessage");
                        try{
                            Thread.sleep(2000);
                        }catch(Exception ignored){}
                        for(int j=0; j<Integer.parseInt(byzantinePerRounds); j++){
                            byzantines[j] = RandomNumber(numberOfProcesses);
                        }
                        String result = String.join(", ", byzantines);
                        Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer1,-1,-1,result);
                        System.out.println("I've sent " + result);
                }
                rounds ++;
            }


        }

    }*/

   /* private static String RandomNumber(int nProcesses){
        Random random = new Random();
        String numeroCasuale = String.valueOf(random.nextInt(nProcesses));
        return numeroCasuale;
    }*/



    public static int[] ByzantineInTheRound(){
        String [] resultInString = byzantineArray.split(", ");
        int[] result = new int[resultInString.length];
        for (int i = 0; i < resultInString.length; i++) {
            result[i] = Integer.parseInt(resultInString[i]);
        }
        return result;
    }



}
