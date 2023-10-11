package org.example.messaging;

import org.example.messaging.Producer;

import java.util.ArrayList;
import java.util.Random;
import java.util.SplittableRandom;

public class Coordinator {
    private static String rounds;
    private String byzantinePerRounds;
    private String [] byzantines;

    public static boolean canStart = false;

    public static String byzantineBehaviour;
    public static ArrayList<String> byzantineArray = new ArrayList<>();

    public Coordinator(String howManyByzantinePerRound, String howManyRounds){
        this.rounds = howManyRounds;
        this.byzantinePerRounds = howManyByzantinePerRound;
        this.byzantines = new String[Integer.parseInt(howManyByzantinePerRound)];
    }

    public void start(int numberOfProcesses, int choiceByzantine){
        switch(choiceByzantine){
            case 1:
                Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                Producer.Initialize(producer,-1,"NoMessage");
                try{
                    Thread.sleep(2000);
                }catch(Exception ignored){}
                for(int i=0; i<Integer.parseInt(rounds); i++){
                    for(int j=0; j<Integer.parseInt(byzantinePerRounds); j++){
                        byzantines[j] = RandomNumber(numberOfProcesses);
                    }
                    String result = String.join(", ", byzantines);
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1,-1,result);
                    System.out.println("I've sent " + result);
                }
        }
    }
    private static String RandomNumber(int nProcesses){
        Random random = new Random();
        String numeroCasuale = String.valueOf(random.nextInt(nProcesses));
        return numeroCasuale;
    }

    public static void CanStart(){
        while(byzantineArray.size() != Integer.parseInt(rounds)){
        }
        canStart = true;
    }

    public static int[] ByzantineInTheRound(int round){
        String [] resultInString = byzantineArray.get(round).split(", ");
        int[] result = new int[resultInString.length];
        for (int i = 0; i < resultInString.length; i++) {
            result[i] = Integer.parseInt(resultInString[i]);
        }
        return result;
    }



}
