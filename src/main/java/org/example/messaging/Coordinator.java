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

    public static int howManyMessages = 0;

    public static int [] choices;

    public static boolean choiceDone = false;

    public Coordinator(String howManyByzantinePerRound, int numberOfProcesses, int howManyRounds){
        this.byzantinePerRounds = howManyByzantinePerRound;
        this.byzantines = new String[Integer.parseInt(howManyByzantinePerRound)];
        this.choices = new int[numberOfProcesses];
        for(int i = 0; i < numberOfProcesses; i++){
            choices[i]=-2;
        }
        answers = new int[numberOfProcesses];
        for(int i=0; i< answers.length; i++){
            answers[i]=-1;
        }

        //COORDINATOR CODE BEGINS

        /*Consumer consumer = new Consumer("tcp://127.0.0.1:61616", "topic", numberOfProcesses, -1, Integer.parseInt(howManyByzantinePerRound),  howManyRounds, 0);
        consumer.Initialize(consumer);*/
    }

   /*public static void start(int numberOfProcesses, int choiceByzantine, int howManyRounds){
       int zero = 0;
       int one = 0;
        if(rounds<=howManyRounds){
            //System.out.println("Sono dentro");
            boolean allTheSame = true;
                //System.out.println("[");

            for (Integer element : answers) {
                //System.out.print(element + ", ");

                if (element != rounds) {
                    allTheSame = false;
                }
            //System.out.println("] and round is  " + rounds);

            }
            if(allTheSame == true){
                Space(2);
                System.out.println("The messages exchanged until now are " + howManyMessages);

                Space(2);
                for(int i=0; i< numberOfProcesses; i++){
                    if(choices[i]== 0){
                        zero++;

                    }
                    if(choices[i]==1){
                        one ++;

                    }
                }
                if(zero>=numberOfProcesses-2*Integer.parseInt(byzantinePerRounds)){
                    choiceDone = true;
                    Space(5);
                    System.out.println("The consensus has been reached in " + rounds + " rounds and the value is " + 0);
                    System.out.println("The total number of exchanged messages is " + howManyMessages);
                    Space(2);
                }
                if(one>=numberOfProcesses-2*Integer.parseInt(byzantinePerRounds)){
                    choiceDone = true;
                    Space(5);
                    System.out.println("The consensus has been reached in " + rounds + " rounds and the value is " + 1);
                    System.out.println("The total number of exchanged messages is " + howManyMessages);
                    Space(2);
                }

                if(choiceDone == false) {
                    System.out.println("They are all ready, I allow them to start for the round " + rounds);
                    Space(2);
                    Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer, -1, -1, "NoMessage");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ignored) {
                    }
                    for (int j = 0; j < Integer.parseInt(byzantinePerRounds); j++) {
                        byzantines[j] = RandomNumber(numberOfProcesses);
                    }
                    String result = String.join(", ", byzantines);
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, -1, -1, result);
                    System.out.println("I've sent " + result);
                    Space(2);
                    rounds++;
                }
            }
        }

    }

    private static String RandomNumber(int nProcesses){
        Random random = new Random();
        String numeroCasuale = String.valueOf(random.nextInt(nProcesses));
        return numeroCasuale;
    }*/


//COORDINATOR CODE ENDS


    public static int[] ByzantineInTheRound(){
        String [] resultInString = byzantineArray.split(", ");
        int[] result = new int[resultInString.length];
        for (int i = 0; i < resultInString.length; i++) {
            result[i] = Integer.parseInt(resultInString[i]);
        }
        return result;
    }

    private static void Space(int Space){
        for(int i=0; i<Space; i++){
            System.out.println();
        }
    }





}
