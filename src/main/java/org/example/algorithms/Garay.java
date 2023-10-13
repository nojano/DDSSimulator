package org.example.algorithms;

import org.example.messaging.Consumer;
import org.example.messaging.Coordinator;
import org.example.messaging.Producer;
import org.example.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Garay {


    public static boolean canStartTheAlgorithm = false;
    public int round;
    public int computation;

    public int totalNumberOfProcesses;

    public int idOfTheProcess;

    public int numberOfByzantines;

    public int [] byzantinesInThisRound;

    public Garay(int totalNumberOfProcesses, int idOfTheProcess, int numberOfByzantines){
        this.computation = 0;
        this.round = 0;
        this.totalNumberOfProcesses = totalNumberOfProcesses;
        this.idOfTheProcess = idOfTheProcess;
        this.numberOfByzantines = numberOfByzantines;
        byzantinesInThisRound = new int[numberOfByzantines];
        for(int i=0; i<numberOfByzantines; i++){
            byzantinesInThisRound[i] = -1;
        }
    }

    public void startEven() {
        if(round % 2 == 0){
            System.out.println("EVEN EVEN EVEN EVEN EVEN EVEN EVEN");
            //ROUND 1
            byzantinesInThisRound = GoGoGo(Coordinator.ByzantineInTheRound());
            System.out.println("The vector of byzantines is: [");
            for (int i = 0; i < byzantinesInThisRound.length; i++) {
                System.out.print(byzantinesInThisRound[i] + ", ");
            }
            System.out.println("] ");
            computation++;
            System.out.println("computation is " + computation);
            System.out.println("IL ROUND PRIMA DELL'AGGIORNAMENTO " + round);
            round ++;
            System.out.println("IL ROUND DOPO L'AGGIORNAMENTO " + round);
            Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer, round, idOfTheProcess, "-2");
            return;
        }
        else{
            System.out.println("SOno nel dispari");
            //ROUND 2
            byzantinesInThisRound = GoGoGo(Coordinator.ByzantineInTheRound());
            System.out.println("ODD ODD ODD ODD ODD ODD ODD");
            System.out.println("The vector of byzantines is: [");
            for (int i = 0; i < byzantinesInThisRound.length; i++) {
                System.out.print(byzantinesInThisRound[i] + ", ");
            }
            System.out.println("] ");
            computation++;
            System.out.println("IL ROUND PRIMA DELL'AGGIORNAMENTO " + round);
            round ++;
            System.out.println("IL ROUND DOPO L'AGGIORNAMENTO " + round);
            System.out.println("computation is " + computation);
            Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer1, round, idOfTheProcess, "-2");
        }

    }
    /*public void startOdd() {
        //ROUND 2
        round = 2;
        byzantinesInThisRound = GoGoGo(Coordinator.ByzantineInTheRound());
        System.out.println("ODD ODD ODD ODD ODD ODD ODD");
        System.out.println("The vector of byzantines is: [");
        for (int i = 0; i < byzantinesInThisRound.length; i++) {
            System.out.print(byzantinesInThisRound[i] + ", ");
        }
        System.out.println("] ");
        computation++;
        round ++;
        System.out.println("computation is " + computation);
        Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
        Producer.Initialize(producer1, round, idOfTheProcess, "-2");
    }*/

    public static void GarayInitilize(int idOfTheProcess){
        Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
        Producer.Initialize(producer1, 0, idOfTheProcess, "-2");
    }







        /*int [] byzantinesInThisRound;
        boolean byzantine = false;
        boolean cured = false;
        String [] MVValues = new String[totalNumberOfProcesses];
        String MV;
        Random random = new Random();
        int v = random.nextInt(2);
        int C=0;
        int round = -1;
        int king = 0;
        System.out.println("I'm the process number " + idOfTheProcess);



        //UNIVERSAL EXCHANGE
        round ++;

        System.out.println("ROUND " + round + ": Universal exchange");
        ArrayList<Message> MVi = new ArrayList<>();
        InitializeMV(totalNumberOfProcesses, MVi);

        byzantinesInThisRound = Coordinator.ByzantineInTheRound(round);
        System.out.println("The byzantine vector for the round " + round +" is " + Arrays.toString(byzantinesInThisRound));
        for(int i=0; i<byzantinesInThisRound.length; i++){
            if(idOfTheProcess == byzantinesInThisRound[i]){
                byzantine = true;
                v=-1;
                System.out.println("I'm byzantine in this round, so my value v is -1 and my MV is all -1");
                //InitializeMV(totalNumberOfProcesses, MVi);
            }
        }
        if(byzantine == false){
            Producer producer= new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer, round, idOfTheProcess, Integer.toString(v));// Process sends its first decision;
            System.out.println("I've decided the value " + v + " and I'm sending it");

            try{
                Thread.sleep(1500*totalNumberOfProcesses);//We need to wait in order to complete the vector MV, so as to not use it before the delivering of all the messages
            }catch(Exception ignored){}
            System.out.println("I received all the messages and I've saved them in the MV, now I will do the check on it");
            for(int i=0; i<consumer.MessagesReceived.size(); i++){
                System.out.println("Sto mettendo " + consumer.MessagesReceived.get(i).id + consumer.MessagesReceived.get(i).message);
                MVi.set(consumer.MessagesReceived.get(i).id, consumer.MessagesReceived.get(i));
            }
            System.out.println("Nella posizione 0 c'è  " + MVi.get(0).message);
            System.out.print("The vector MV is [" );
            for(int i=0; i<MVi.size(); i++){
                System.out.print(MVi.get(i).message + ",");
                if(Integer.parseInt(MVi.get(i).message)==1){
                    //System.out.println("È uguale a 1");
                    C = C+1;
                }
                //System.out.println("C è " + v);
            }
            System.out.println("]");
            System.out.println("C is "+ C);
            if(C>=0.5*totalNumberOfProcesses){v=1;}
            else{v=0;}// It checks the MV and it puts the most common choice in the variable v
            System.out.println("The V chosen after the check on MV is " + v);
        }
        for(int i=0; i<MVi.size(); i++){
            MVValues[i] = MVi.get(i).message;
        }
        MV = String.join(", ", MVValues);

        try{
            Thread.sleep(30000);//We need to wait in order to receive all the MV
        }catch(Exception ignored){}
        System.out.println("I'm exiting from the round " + round);


        // Reconstruction and king's broadcast
        round ++;
        System.out.println("ROUND " + round + ": Reconstruction and king's broadcast");
        if(byzantine == true){
            byzantine = false;
            cured = true;
            System.out.println("I'm cured now");
        }
        byzantinesInThisRound = Coordinator.ByzantineInTheRound(round);
        System.out.println("The byzantine vector for the round " + round +" is " + Arrays.toString(byzantinesInThisRound));
        for(int i=0; i<byzantinesInThisRound.length; i++){
            if(idOfTheProcess == byzantinesInThisRound[i]){
                byzantine = true;
                System.out.println("I'm byzantine in this round, so i will do nothing");
                //InitializeMV(totalNumberOfProcesses, MVi);
            }
        }
        if(byzantine == false){
            int [][] ECHO = new int [totalNumberOfProcesses][totalNumberOfProcesses];
            Producer producer= new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer, round, idOfTheProcess, MV);// Process sends MV
            System.out.println("I'm sending the following MV: [" + MV + "]");
            try{
                Thread.sleep(3000*totalNumberOfProcesses);//We need to wait in order to receive all the MV
            }catch(Exception ignored){}
            for(int i=0; i< ECHO.length; i++){
                for(int j=0; j< ECHO.length; j++){
                    ECHO[i][j] = -1;
                }
            }
            for(int i=0; i<consumer.MessagesReceived.size(); i++){
                boolean go = true;
                for(int x=0; x< byzantinesInThisRound.length; x++){
                    if(i == byzantinesInThisRound[x]){
                        go = false;
                    }
                }
                if(go == true){
                    for(int j=0; j<consumer.MessagesReceived.size(); j++){

                        System.out.println("Dimensione di messagesReceived è: " + consumer.MessagesReceived.size());
                        System.out.println("Dimensione totale processi:" + totalNumberOfProcesses);
                        ECHO[i][j]= ExtrapolateInt(i,consumer.MessagesReceived)[j];
                    }
                    System.out.println("Ho fatto con l'mv di " + i);
                }
                //int[] temporaryVector = new int[totalNumberOfProcesses];
                //temporaryVector = ExtrapolateInt(i,consumer.MessagesReceived);
                //System.out.println("Il vettore di numbers è " + Arrays.toString(temporaryVector));
            }
            System.out.println("ECHO is: ");
            for (int riga = 0; riga < ECHO.length; riga++) {
                for (int colonna = 0; colonna < ECHO[riga].length; colonna++) {
                    System.out.print(ECHO[riga][colonna] + " ");
                }
                System.out.println();
            }
            if(cured == true){
                v = Reconstruct(ECHO,totalNumberOfProcesses,byzantinesInThisRound);
                System.out.println("I'm exited from the reconstruct method and my choice is " + v);
            }
            king = (round % totalNumberOfProcesses) + 1;
            if(idOfTheProcess == king){
                Producer producer1= new Producer("tcp://127.0.0.1:61616", "topic");
                Producer.Initialize(producer1, round,  idOfTheProcess, Integer.toString(v));// Process sends its first decision;
                System.out.println("I'm the king and i send the value v: " + v);
            }
        }
*/


    public static void InitializeMV(int totalNumberOfProcesses, ArrayList<Message> MVi) {
        for (int i = 0; i < totalNumberOfProcesses; i++) {
            Message msg0 = new Message(-1, -1, "-1");
            MVi.add(msg0);
        }
    }

    public static int[] ExtrapolateInt(int numberOfProcess, ArrayList<Message> messagesReceived){
        String [] resultInString = messagesReceived.get(numberOfProcess).message.split(", ");
        int[] result = new int[resultInString.length];
        for (int i = 0; i < resultInString.length; i++) {
            result[i] = Integer.parseInt(resultInString[i]);
        }
        return result;
    }

    public static int Reconstruct(int[][]echo, int numberOfProcesses, int[] byzantine){
        System.out.println("I'm in the reconstruct method");
        int delta = numberOfProcesses - byzantine.length;
        int howMany1=0;
        ArrayList<Integer> choices = new ArrayList<>();
        for(int i=0; i<numberOfProcesses; i++){
            choices.add(-1);
        }
        for(int i=0; i<echo.length; i++){
            int x=0;
            for(int j=0; j< echo.length; j++){
                if(echo[i][j] ==1){
                    x++;
                }
            if(x>=delta){
                choices.set(i,1);
                howMany1++;}
            }
        }
        if(howMany1>= numberOfProcesses/2){
            return 1;
        }
        else{return -1;}
    }

    private static int [] GoGoGo(int[] vector){

        while(vector[vector.length-1] == -1){
            System.out.println("Non sono ancora pronto");
        }
        int [] result;
        result = vector;

        for(int i = 0; i<vector.length; i++){
            vector[i] = -1;
        }
        return result;
    }
}
