package org.example.algorithms;

import org.example.messaging.Consumer;
import org.example.messaging.Coordinator;
import org.example.messaging.Producer;
import org.example.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Garay {



    public static void start(int totalNumberOfProcesses, int idOfTheProcess, Consumer consumer){

        System.out.println("The Garay's algorithm starts");
        int [] byzantinesInThisRound;
        boolean byzantine = false;
        Random random = new Random();
        int v = random.nextInt(2);
        int C=0;
        int round = -1;
        System.out.println("I'm the process number " + idOfTheProcess);



        //UNIVERSAL EXCHANGE
        round ++;
        ArrayList<Message> MVi = new ArrayList<>();
        InitializeMV(totalNumberOfProcesses, MVi);

        int roundPrint = round + 1;
        System.out.println("ROUND " + roundPrint + ": Universal exchange");
        byzantinesInThisRound = Coordinator.ByzantineInTheRound(round);
        System.out.println("The byzantine vector for the round " + roundPrint +" is " + Arrays.toString(byzantinesInThisRound));
        for(int i=0; i<byzantinesInThisRound.length; i++){
            if(idOfTheProcess == byzantinesInThisRound[i]){
                byzantine = true;
                v=-1;
                System.out.println("I'm byzantine in this round, so my value v is -1 and my MV is all -1");
                InitializeMV(totalNumberOfProcesses, MVi);
            }
        }
        if(byzantine == false){
            Producer producer= new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer, idOfTheProcess, Integer.toString(v));// Process sends its first decision;
            System.out.println("I've decided the value " + v + " and I'm sending it");

            try{
                Thread.sleep(1000*totalNumberOfProcesses);//We need to wait in order to complete the vector MV, so as to not use it before the delivering of all the messages
            }catch(Exception ignored){}
            System.out.println("I received all the messages and I've saved them in the MV, now I will do the check on it");
            MVi = consumer.MV;
            System.out.print("The vector MV is [" );
            for(int i=0; i<MVi.size(); i++){
                System.out.print(MVi.get(i).message + ",");
                if(Integer.parseInt(MVi.get(i).message)==1){
                    //System.out.println("È uguale a 1");
                    C = C+1;
                }
                //System.out.println("C è " + v);
            }
            System.out.println();
            System.out.println("C is "+ C);
            if(C>=0.5*totalNumberOfProcesses){v=1;}
            else{v=0;}// It checks the MV and it puts the most common choice in the variable v
            System.out.println("The V chosen after the check on MV is " + v);
        }


        // Reconstruction and kings




    }

    public static void InitializeMV(int totalNumberOfProcesses, ArrayList<Message> MVi) {
        for (int i = 0; i < totalNumberOfProcesses; i++) {
            Message msg0 = new Message(0, "-1");
            MVi.add(msg0);
        }
    }
}
