package org.example.algorithms;

import org.example.messaging.Consumer;
import org.example.messaging.Coordinator;
import org.example.messaging.Producer;
import org.example.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Garay {


    public static Consumer consumerAlg;
    public static int round;

    public static int totalNumberOfProcesses;

    public static int idOfTheProcess;

    public static int numberOfByzantines;

    public static int [] byzantinesInThisRound;

    static boolean byzantine;
    static boolean cured;
    static String [] MVValues;
    static String MV;
    static Random random = new Random();
    static int v;
    static int C;

    static int king;

    static boolean kingByzantine;

    public Garay(int totalNumberOfProcesses, int idOfTheProcess, int numberOfByzantines){
        this.round = 0;
        this.totalNumberOfProcesses = totalNumberOfProcesses;
        this.idOfTheProcess = idOfTheProcess;
        this.numberOfByzantines = numberOfByzantines;
        byzantinesInThisRound = new int[numberOfByzantines];
        this.byzantine = false;
        this.cured = false;
        this.MVValues = new String[totalNumberOfProcesses];
        this.v = random.nextInt(2);
        this.C=0;
        for(int i=0; i<numberOfByzantines; i++){
            byzantinesInThisRound[i] = -1;
        }
    }

    public static void startEven(Coordinator coordinator) {
        Space(1);
        if(round % 2 == 0){
            System.out.println("ROUND "+ round + ": Universal exchange");
            Space(2);
            System.out.println("My id is " + idOfTheProcess);
            Space(1);
            if(byzantine == true){
                byzantine = false;
                cured = true;
                System.out.println("I'm cured now");
                Space(1);
                v=-1;
            }
            System.out.print("The vector of byzantines is: [");
            for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
            }
            System.out.println("] ");
            Space(1);
            ArrayList<Message> MVi = new ArrayList<>();
            InitializeMV(totalNumberOfProcesses, MVi);
            for(int i=0; i<byzantinesInThisRound.length; i++){
                if(idOfTheProcess == byzantinesInThisRound[i]){
                    byzantine = true;
                    v=-1;
                    System.out.println("I'm byzantine in this round, so my value v is -1 and my MV is all -1");
                    Space(1);
                }
            }
            if(byzantine == false) {
                Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                System.out.println("I've decided the value " + v + " and I'm sending it");
                Space(1);

                try{
                    Thread.sleep(1500*totalNumberOfProcesses);
                }catch (Exception ignored){}

                for(int i=0; i<Consumer.MessagesReceived.size(); i++){
                    boolean go = true;
                    for(int j=0; j<byzantinesInThisRound.length; j++){
                        if(i==byzantinesInThisRound[j]){
                            Message msg = new Message(round,i,"-1");
                            MVi.set(i,msg);
                            go = false;
                        }
                    }
                    if(go == true){

                        //System.out.println("Sto mettendo " + consumerAlg.MessagesReceived.get(i).id + consumerAlg.MessagesReceived.get(i).message);
                        /*while(Consumer.MessagesReceived.get(i).)*/
                        MVi.set(Consumer.MessagesReceived.get(i).id, Consumer.MessagesReceived.get(i));
                        while(Integer.parseInt(MVi.get(i).message) == -2){
                        MVi.set(Consumer.MessagesReceived.get(i).id, Consumer.MessagesReceived.get(i));
                        //System.out.println(Integer.parseInt(MVi.get(i).message));
                            }}
                }
                System.out.print("The vector MV is [" );
                C=0;
                for(int i=0; i<MVi.size(); i++){
                    System.out.print(MVi.get(i).message + ",");
                    if(Integer.parseInt(MVi.get(i).message)==1){
                        //System.out.println("È uguale a 1");
                        C = C+1;
                    }
                    //System.out.println("C è " + v);
                }
                System.out.println("]");
                Space(1);
                System.out.println("C is "+ C);
                Space(1);
                if(C>=0.5*totalNumberOfProcesses){v=1;}
                else{v=0;}// It checks the MV and it puts the most common choice in the variable v
                System.out.println("The V chosen after the check on MV is " + v);
                Space(1);
            }
            for(int i=0; i<MVi.size(); i++){
                MVValues[i] = MVi.get(i).message;
            }
            MV = String.join(", ", MVValues);

            System.out.println("The final decision of this round is: " + v);
            Space(7);
            round ++;
            Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer, round, idOfTheProcess, "-2");
        }


        else{
            System.out.println("ROUND "+ round +": Reconstruction and king's broadcast");
            Space(2);
            System.out.println("My id is " + idOfTheProcess);
            Space(1);


            if(byzantine == true){
                byzantine = false;
                cured = true;
                System.out.println("I'm cured now");
                Space(1);
            }
            System.out.print("The vector of byzantines is: [");
            for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
            }
            System.out.println("] ");
            Space(1);
            for(int i=0; i<byzantinesInThisRound.length; i++){
                //System.out.println("My id is " + idOfTheProcess + " and the byzantine is " + byzantinesInThisRound[i]);
                if(idOfTheProcess == byzantinesInThisRound[i]){
                    byzantine = true;
                    System.out.println("I'm byzantine in this round, so I will do nothing");
                    Space(1);
                }
            }
            if(byzantine == false){
                int [][] ECHO = new int [totalNumberOfProcesses][totalNumberOfProcesses];
                Producer producer1= new Producer("tcp://127.0.0.1:61616", "topic");
                Producer.Initialize(producer1, round, idOfTheProcess, MV);// Process sends MV
                System.out.println("I'm sending the following MV: [" + MV + "]");
                Space(1);
                try{
                    Thread.sleep(3000*totalNumberOfProcesses);//We need to wait in order to receive all the MV
                }catch(Exception ignored){}
                for(int i=0; i< ECHO.length; i++){
                    for(int j=0; j< ECHO.length; j++){
                        ECHO[i][j] = -1;
                    }
                }
                for(int i=0; i<Consumer.MessagesReceivedOdd.size(); i++) {
                    boolean go = true;
                    for (int x = 0; x < byzantinesInThisRound.length; x++) {
                        if (i == byzantinesInThisRound[x]) {
                            go = false;
                        }
                    }
                    if (go == true) {
                        for (int j = 0; j < Consumer.MessagesReceivedOdd.size(); j++) {

                            /*System.out.println("Dimensione di messagesReceived è: " + Consumer.MessagesReceived.size());
                            System.out.println("Dimensione totale processi:" + totalNumberOfProcesses);*/
                            ECHO[i][j] = ExtrapolateInt(i, Consumer.MessagesReceivedOdd)[j];
                        }
                        //System.out.println("Ho fatto con l'mv di " + i);
                    }
                }
                System.out.println("ECHO is: ");
                for (int riga = 0; riga < ECHO.length; riga++) {
                    for (int colonna = 0; colonna < ECHO[riga].length; colonna++) {
                        System.out.print(ECHO[riga][colonna] + " ");
                    }
                    System.out.println();
                }
                Space(1);

                if(cured == true){
                    v = Reconstruct(ECHO,totalNumberOfProcesses,byzantinesInThisRound);
                    System.out.println("I'm exited from the reconstruct method and my choice is " + v);
                    Space(1);
                    cured = false;
                }
                king = (round % totalNumberOfProcesses) + 1;
                String kingChoice = "null";
                System.out.println("The king of the round is the process number " + king);
                Space(1);
                kingByzantine = false;
                for(int i=0; i<byzantinesInThisRound.length; i++) {
                    if (king == byzantinesInThisRound[i]) {
                        //System.out.println("The king of this round is byzantine, so I put -1 as is choice");
                        kingChoice = "-1";
                        kingByzantine = true;

                    }
                }
                if(kingByzantine == false && idOfTheProcess == king){
                    Producer producer2= new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -4,  idOfTheProcess, Integer.toString(v));// Process sends its first decision;
                    System.out.println("I'm the king and i send the value v: " + v);
                    Space(1);
                }
                try{
                    Thread.sleep(2000*totalNumberOfProcesses);
                }catch (Exception ignored){}

                if(kingByzantine == false){
                    kingChoice = Consumer.kingMsg.message;
                }
                if(Integer.parseInt(kingChoice)==0||Integer.parseInt(kingChoice)==1){
                    if(C < (totalNumberOfProcesses - 2*numberOfByzantines) ){
                        v = Integer.parseInt(kingChoice);
                    }
                }

            }
            System.out.println("The final decision of this round is: " + v);
            Space(5);
            round ++;
            Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
            Producer.Initialize(producer1, round, idOfTheProcess, "-2");
        }

    }

    //  QUESTION TO MAKE: THE CURED IN THE EVEN ROUND WHAT THEY DO?


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
            Message msg0 = new Message(-1, -2, "-1");
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
        Space(1);
        int delta = numberOfProcesses - byzantine.length;
        int howMany1=0;
        ArrayList<Integer> choices = new ArrayList<>();
        for(int i=0; i<numberOfProcesses; i++){
            choices.add(-1);
        }
        for(int i=0; i<echo.length; i++){
            C=0;
            for(int j=0; j< echo.length; j++){
                if(echo[i][j] ==1){
                    C++;
                }
            if(C>=delta){
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
        return vector;
    }

    private static void InitializeAgainVector(int[] vector){

        for(int i = 0; i<vector.length; i++){
            vector[i] = -1;
        }
    }

    private static void Space(int Space){
        for(int i=0; i<Space; i++){
            System.out.println();
        }
    }
}
