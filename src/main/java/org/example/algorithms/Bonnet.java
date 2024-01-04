package org.example.algorithms;

import org.example.messaging.Consumer;
import org.example.messaging.Coordinator;
import org.example.messaging.Producer;
import org.example.models.Message;

import java.util.ArrayList;

public class Bonnet {

    public static int round;

    public static int totalRounds;

    public static int totalNumberOfProcesses;

    public static int idOfTheProcess;

    public static int numberOfByzantines;

    public static int [] byzantinesInThisRound;

    static boolean firstByzantine = true;

    static boolean byzantine;
    static boolean cured;
    static String [] PV;

    static String [] SV;

    static String [][] ER;

    static String [] RV;
    static int dec;

    static int v;

    static int s;

    static int c;

    static int w_1;
    static int w_0;

    static int byz;

    static boolean firstRound = true;
    static boolean secondRound = false;
    static boolean thirdRound = false;

    static boolean firstInitialization = true;

    static boolean even;



    public Bonnet (int totalNumberOfProcesses, int idOfTheProcess, int numberOfByzantines, int totalNumberOfRounds){
        this.round = 0;
        this.totalNumberOfProcesses = totalNumberOfProcesses;
        this.idOfTheProcess = idOfTheProcess;
        this.numberOfByzantines = numberOfByzantines;
        this.totalRounds = totalNumberOfRounds;
        byzantinesInThisRound = new int[numberOfByzantines];
        this.byzantine = false;
        this.cured = false;
        this.PV = new String[totalNumberOfProcesses];
        this.SV = new String[totalNumberOfProcesses];
        this.RV = new String[totalNumberOfProcesses];
        this.ER = new String[totalNumberOfProcesses][totalNumberOfProcesses];
        this.v = -1;
        this.s = 0;
        this.c = -1;
        this.dec = -1;
        this.even = true;
        for(int i=0; i<numberOfByzantines; i++){
            byzantinesInThisRound[i] = -1;
        }
    }

    public static void start(Coordinator coordinator, int value){
        if(coordinator.byzantineBehaviour == "NoMessage") {
         /*   if (firstInitialization == true) {
                v = value;
                System.out.println("I'm the process number " + idOfTheProcess + "and my value is " + v);
                firstInitialization = false;
            }
            Space(1);
            if (round < 3 * totalNumberOfProcesses) {
                if (firstRound == true && secondRound == false && thirdRound == false) {
                    InitializeVector(PV);
                    boolean reached = false;
                    System.out.println("ROUND " + round + ": Proposing round");
                    Space(2);
                    System.out.println("My id is " + idOfTheProcess);
                    Space(1);
                    if(byzantine == true){
                        byzantine = false;
                        Space(1);
                    }
                    System.out.print("The vector of byzantines is: [");
                    for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                        byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                        System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
                    }
                    System.out.println("] ");
                    Space(1);
                    for (int i = 0; i < byzantinesInThisRound.length; i++) {
                        if (idOfTheProcess == byzantinesInThisRound[i]) {
                            byzantine = true;
                        }
                    }
                    if(byzantine == true){
                        v = -1;
                        System.out.println("I'm byzantine in this round. I set my MV equal to -1 and I will do nothing");
                        Space(1);
                    }
                    if(byzantine == false){
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                        System.out.println("I've decided the value " + v + " and I'm sending it");
                        Space(1);

                        try {
                            Thread.sleep(1500 * totalNumberOfProcesses);
                        } catch (Exception ignored) {
                        }
                        w_1 = 0;
                        w_0 = 0;
                        *//*ArrayList<Message> Messages = new ArrayList<Message>();
                        Messages = ArrayMessages();*//*
                        for (int i=0; i< ArrayMessages().size(); i++) {
                            boolean go = true;
                            for (int j = 0; j < byzantinesInThisRound.length; j++) {
                                if (i == byzantinesInThisRound[j]) {
                                    PV[i] = "-1";
                                    go = false;
                                }
                            }
                            if(go == true){
                                PV[i] = ArrayMessages().get(i).message;
                                while (Integer.parseInt(PV[i]) == -2){
                                    PV[i] = ArrayMessages().get(i).message;
                                }
                            }
                        }
                        System.out.print("The vector PV is [");
                        for (int i = 0; i < PV.length; i++) {
                            System.out.print(PV[i] + ",");
                            if(Integer.parseInt(PV[i]) == 1){
                                w_1 ++;
                            }
                            else if(Integer.parseInt(PV[i]) == 0){
                                w_0 ++;
                            }
                        }

                        System.out.println("]");
                        if (w_1 >= totalNumberOfProcesses-2*byzantinesInThisRound.length){
                            v=1;
                            reached = true;
                        }
                        else if(w_0 >= totalNumberOfProcesses - 2*byzantinesInThisRound.length){
                            v=0;
                            reached = true;
                        }
                        Space(1);
                        if(reached == true){
                            System.out.println("At the end of this proposing round, the value v is " + v + ", but i put in the dec value the value -1");
                        }
                        else if(reached == false){
                            System.out.println("neither the value 1 or the 0 reached the threshold. I put in the dec the value null");
                        }
                    }
                    firstRound = false;
                    secondRound = true;
                    Producer producer2 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -5, idOfTheProcess, String.valueOf(v));
                    Space(7);
                    round++;
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, round, idOfTheProcess, "-2");


                } else if (firstRound == false && secondRound == true && thirdRound == false) {
                    InitializeVector(SV);
                    System.out.println("ROUND " + round + ": Collecting round");
                    Space(2);
                    System.out.println("My id is " + idOfTheProcess);
                    Space(1);
                    if(byzantine == true){
                        byzantine = false;
                        //cured = true;
                        //System.out.println("I'm cured now");
                        Space(1);
                    }
                    System.out.print("The vector of byzantines is: [");
                    for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                        byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                        System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
                    }
                    System.out.println("] ");
                    Space(1);
                    for (int i = 0; i < byzantinesInThisRound.length; i++) {
                        if (idOfTheProcess == byzantinesInThisRound[i]) {
                            byzantine = true;
                        }
                    }
                    if(byzantine == true){
                        v = -1;
                        for(int i=0; i<totalNumberOfProcesses; i++){
                            SV[i]=String.valueOf(-1);
                        }
                        System.out.println("I'm byzantine in this round. I set my MV equal to -1 and I will do nothing");
                        Space(1);
                    }
                    *//*ArrayList<Message> Messages = new ArrayList<Message>();
                    Messages = ArrayMessages();*//*
                    if(byzantine == false) {
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                        System.out.println("I've decided the value " + v + " and I'm sending it");
                        Space(1);

                        try {
                            Thread.sleep(1500 * totalNumberOfProcesses);
                        } catch (Exception ignored) {
                        }
                        for (int i = 0; i < ArrayMessages().size(); i++) {
                            boolean go = true;
                            for (int j = 0; j < byzantinesInThisRound.length; j++) {
                                if (i == byzantinesInThisRound[j]) {
                                    SV[i] = "-1";
                                    go = false;
                                }
                            }secondRound = false;
                    thirdRound = true;
                    Producer producer2 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -5, idOfTheProcess, String.valueOf(v));
                    Space(7);
                    round++;
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, round, idOfTheProcess, "-2");
                            if (go == true) {
                                    SV[i] = ArrayMessages().get(i).message;
                            }
                        }
                        System.out.print("The vector SV is [");
                        VectorPrinter(SV);
                    }

                }
                else if (firstRound == false && secondRound == false && thirdRound == true) {
                    InitializeVector(RV);
                    System.out.println("ROUND " + round + ": Deciding round");
                    Space(2);
                    System.out.println("My id is " + idOfTheProcess);
                    Space(1);
                    if(byzantine == true){
                        byzantine = false;
                        //cured = true;
                        //System.out.println("I'm cured now");
                        Space(1);
                    }
                    System.out.print("The vector of byzantines is: [");
                    for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                        byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                        System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
                    }
                    System.out.println("] ");
                    Space(1);
                    for (int i = 0; i < byzantinesInThisRound.length; i++) {
                        if (idOfTheProcess == byzantinesInThisRound[i]) {
                            byzantine = true;
                        }
                    }
                    if(byzantine == true){
                        v = -1;
                        System.out.println("I'm byzantine in this round. I set my MV equal to -1 and I will do nothing");
                        Space(1);
                    }
                    ArrayList<Message> Messages = new ArrayList<Message>();
                    Messages = ArrayMessages();
                    if(byzantine == false){
                        String SVstring = String.join(", ",SV);
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, -6, idOfTheProcess, SVstring);
                        System.out.println("I'm sending my SV: [ " + SVstring + " ]");
                        Space(1);
                        try {
                            Thread.sleep(1500 * totalNumberOfProcesses);
                        } catch (Exception ignored) {
                        }
                        for (int i = 0; i < ER.length; i++) {
                            for (int j = 0; j < ER.length; j++) {
                                ER[i][j] = String.valueOf(-1);
                            }
                        }
                        Space(1);
                        for(int i=0; i<ArrayMessages().size(); i++){
                            boolean go = true;
                            for (int x = 0; x < byzantinesInThisRound.length; x++) {
                                if (i == byzantinesInThisRound[x]) {
                                    go = false;
                                }
                            }
                            if(go == true){
                                for (int j = 0; j < totalNumberOfProcesses; j++) {
                                    ER[i][j] = ExtrapolateString(i, ArrayMessages())[j];
                                }
                            }
                        }
                        System.out.println("ER is: ");
                        for (int riga = 0; riga < ER.length; riga++) {
                            for (int colonna = 0; colonna < ER[riga].length; colonna++) {
                                System.out.print(ER[riga][colonna] + " ");
                            }
                            System.out.println();
                        }
                        Space(1);
                        for(int i=0; i<totalNumberOfProcesses; i++){
                            w_0 = 0;
                            w_1 = 0;
                            for(int j=0; j<totalNumberOfProcesses; j++){
                                if(Integer.parseInt(ER[j][i]) == 1 ){
                                    w_1++;
                                }
                                else if(Integer.parseInt(ER[j][i]) == 0){
                                    w_0++;
                                }
                                else{}
                            }
                            if(w_1>2*numberOfByzantines){
                                RV[i]="1";
                            }
                            else if(w_0>2*numberOfByzantines){
                                RV[i]="0";
                            }
                        }
                        System.out.print("The vector RV is [ ");
                        VectorPrinter(RV);
                        Space(1);
                        w_1 = 0;
                        w_0 = 0;
                        for(int i=0; i<RV.length; i++){
                            if(RV[i]=="1"){
                                w_1++;
                            }
                            else if(RV[i]=="0"){
                                w_0++;
                            }
                        }
                        boolean flag = false;
                        if(w_1>3*numberOfByzantines){
                            v = 1;
                            flag = true;
                        }
                        if(w_0>3*numberOfByzantines){
                            v = 0;
                            flag = true;
                        }
                        if(flag == false){
                            c++;
                            System.out.println("No value is been decided through the RV vector, so the process " + c + "is the king of the phase.");
                            w_1 = 0;
                            w_0 = 0;
                            System.out.print("ER[c][] is: [");
                            for(int i=0; i<totalNumberOfProcesses; i++){
                                System.out.print(ER[c][i] + ", ");
                                if(ER[c][i]=="1"){
                                    w_1++;
                                }
                                else if(ER[c][i]=="0"){
                                    w_0++;
                                }
                            }
                            System.out.println("]");
                            Space(1);
                            if(w_1>2*numberOfByzantines){
                                v = 1;
                                System.out.println("The value v is 1");
                                Space(1);
                            }
                            else{
                                System.out.println("The value v is 0");
                                v = 0;
                                Space(1);
                            }
                        }
                    }
                    thirdRound = false;
                    firstRound = true;
                    Producer producer2 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -5, idOfTheProcess, String.valueOf(v));
                    Space(7);
                    round++;
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, round, idOfTheProcess, "-2");
                }
            }
            else{
                System.out.println("Maintaining round");
            }*/
        }
        if(coordinator.byzantineBehaviour == "WorstCase"){
            if (firstInitialization == true) {
                v = value;
                System.out.println("I'm the process number " + idOfTheProcess + "and my value is " + v);
                firstInitialization = false;
            }
            Space(1);
            if(round <3 * totalNumberOfProcesses){
                if (firstRound == true && secondRound == false && thirdRound == false) {
                    InitializeVector(PV);
                    boolean reached = false;
                    System.out.println("ROUND " + round + ": Proposing round");
                    Space(2);
                    System.out.println("My id is " + idOfTheProcess);
                    Space(1);
                    if(byzantine == true){
                        byzantine = false;
                        Space(1);
                    }
                    System.out.print("The vector of byzantines is: [");
                    /*for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                        byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                        System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
                    }*/
                    byz = -1;
                    for(int i=0; i<numberOfByzantines; i++){
                        byz++;
                        byzantinesInThisRound[i] = byz;
                        System.out.print(byzantinesInThisRound[i] + ", ");
                    }
                    System.out.println("] ");
                    Space(1);
                    for (int i = 0; i < byzantinesInThisRound.length; i++) {
                        if (idOfTheProcess == byzantinesInThisRound[i]) {
                            byzantine = true;
                        }
                    }
                    if(byzantine == true){
                        //v = 3;
                        System.out.println("I'm byzantine in this round.");
                        Space(1);
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                        System.out.println("I've decided the value " + v + " and I'm sending it");
                        Space(1);
                    }
                    if(byzantine == false) {
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                        System.out.println("I've decided the value " + v + " and I'm sending it");
                        Space(1);

                        try {
                            Thread.sleep(1500 * totalNumberOfProcesses);
                        } catch (Exception ignored) {
                        }
                        w_1 = 0;
                        w_0 = 0;
                        for (int i=0; i< ArrayMessages().size(); i++) {
                            boolean go = true;
                            /*for (int j = 0; j < byzantinesInThisRound.length; j++) {
                                if (i == byzantinesInThisRound[j]) {
                                    PV[i] = "-1";
                                    go = false;
                                }
                            }*/
                            if(go == true){
                                if(Integer.parseInt(ArrayMessages().get(i).message) == 3){
                                    if(idOfTheProcess%2 == 0){
                                        PV[i] ="1";
                                    }
                                    else PV[i]="0";
                                }
                                else{
                                    PV[i] = ArrayMessages().get(i).message;
                                    while (Integer.parseInt(PV[i]) == -2) {
                                        PV[i] = ArrayMessages().get(i).message;
                                    }
                                }
                            }
                        }
                        System.out.print("The vector PV is [");
                        for (int i = 0; i < PV.length; i++) {
                            System.out.print(PV[i] + ",");
                            if(Integer.parseInt(PV[i]) == 1){
                                w_1 ++;
                            }
                            else if(Integer.parseInt(PV[i]) == 0){
                                w_0 ++;
                            }
                        }

                        System.out.println("]");
                        if (w_1 >= totalNumberOfProcesses-2*byzantinesInThisRound.length){
                            v=1;
                            reached = true;
                        }
                        else if(w_0 >= totalNumberOfProcesses - 2*byzantinesInThisRound.length){
                            v=0;
                            reached = true;
                        }
                        Space(1);
                        if(reached == true){
                            System.out.println("At the end of this proposing round, the value v is " + v + ", but i put in the dec value the value -1");
                        }
                        else if(reached == false){
                            System.out.println("neither the value 1 or the 0 reached the threshold. I put in the dec the value null");
                        }
                    }

                    firstRound = false;
                    secondRound = true;
                    Producer producer2 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -5, idOfTheProcess, String.valueOf(v));
                    Space(7);
                    round++;
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, round, idOfTheProcess, "-2");
                }

                else if (firstRound == false && secondRound == true && thirdRound == false) {
                    InitializeVector(SV);
                    System.out.println("ROUND " + round + ": Collecting round");
                    Space(2);
                    System.out.println("My id is " + idOfTheProcess);
                    Space(1);
                    if (byzantine == true) {
                        byzantine = false;
                        Space(1);
                    }
                    System.out.print("The vector of byzantines is: [");
                    /*for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                        byzantinesInThisRound[i] = GoGoGo(coordinator.ByzantineInTheRound())[i];
                        System.out.print(GoGoGo(coordinator.ByzantineInTheRound())[i] + ", ");
                    }*/
                    for(int i=0; i<numberOfByzantines; i++){
                        byz++;
                        byzantinesInThisRound[i] = byz;
                        System.out.print(byzantinesInThisRound[i] + ", ");

                    }
                    System.out.println("] ");
                    Space(1);
                    for (int i = 0; i < byzantinesInThisRound.length; i++) {
                        if (idOfTheProcess == byzantinesInThisRound[i]) {
                            byzantine = true;
                        }
                    }
                    if(byzantine == true){
                        //v = 3;
                        System.out.println("I'm byzantine in this round.");
                        Space(1);
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                        System.out.println("I've decided the value " + v + " and I'm sending it");
                        Space(1);
                    }
                    if(byzantine == false) {
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, round, idOfTheProcess, String.valueOf(v));// Process sends its first decision;
                        System.out.println("I've decided the value " + v + " and I'm sending it");
                        Space(1);

                        try {
                            Thread.sleep(1500 * totalNumberOfProcesses);
                        } catch (Exception ignored) {
                        }
                        for (int i = 0; i < ArrayMessages().size(); i++) {
                            if(Integer.parseInt(ArrayMessages().get(i).message) == 3){
                                if(idOfTheProcess%2 == 0){
                                    SV[i]="1";
                                }
                                else{
                                    SV[i]="0";
                                }
                            }
                            else {
                                SV[i] = ArrayMessages().get(i).message;
                            }
                        }
                        System.out.print("The vector SV is [");
                        VectorPrinter(SV);
                    }
                    secondRound = false;
                    thirdRound = true;
                    Producer producer2 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -5, idOfTheProcess, String.valueOf(v));
                    Space(7);
                    round++;
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, round, idOfTheProcess, "-2");
                }
                else if (firstRound == false && secondRound == false && thirdRound == true) {
                    c++;
                    InitializeVector(RV);
                    System.out.println("ROUND " + round + ": Deciding round");
                    Space(2);
                    System.out.println("My id is " + idOfTheProcess);
                    Space(1);
                    if (byzantine == true) {
                        byzantine = false;
                        Space(1);
                    }
                    System.out.print("The vector of byzantines is: [");
                    for (int i = 0; i < GoGoGo(coordinator.ByzantineInTheRound()).length; i++) {
                        if(c == totalNumberOfProcesses-1){
                            c--;
                            byzantinesInThisRound[i] = c;
                            c++;
                        }
                        else{
                            byzantinesInThisRound[i] = c;
                        }

                        System.out.print(byzantinesInThisRound[i] + ", ");
                    }
                    System.out.println("] ");
                    Space(1);
                    for (int i = 0; i < byzantinesInThisRound.length; i++) {
                        if (idOfTheProcess == byzantinesInThisRound[i]) {
                            byzantine = true;
                        }
                    }
                    if(byzantine == true){
                        //v = 3;
                        System.out.println("I'm byzantine in this round.");
                        for(int i=0; i< SV.length; i++){
                            SV[i] = String.valueOf(3);
                        }
                        String SVstring = String.join(", ",SV);
                        Space(1);
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, -6, idOfTheProcess, SVstring);
                        System.out.println("I'm sending my SV: [ " + SVstring + " ]");
                        Space(1);
                    }
                    ArrayList<Message> Messages = new ArrayList<Message>();
                    Messages = ArrayMessages();
                    if(byzantine == false) {
                        String SVstring = String.join(", ", SV);
                        Producer producer = new Producer("tcp://127.0.0.1:61616", "topic");
                        Producer.Initialize(producer, -6, idOfTheProcess, SVstring);
                        System.out.println("I'm sending my SV: [ " + SVstring + " ]");
                        Space(1);
                        try {
                            Thread.sleep(1500 * totalNumberOfProcesses);
                        } catch (Exception ignored) {}

                        for (int i = 0; i < ER.length; i++) {
                            for (int j = 0; j < ER.length; j++) {
                                ER[i][j] = String.valueOf(-1);
                            }
                        }
                        Space(1);
                        for(int i=0; i<ArrayMessages().size(); i++){
                            for (int j = 0; j < totalNumberOfProcesses; j++) {
                                if(Integer.parseInt(ExtrapolateString(i, ArrayMessages())[j]) == 3){
                                    if(idOfTheProcess%2 == 0){
                                        ER[i][j] = "1";
                                    }
                                    else{
                                        ER[i][j] = "0";
                                    }
                                }
                                else{
                                    ER[i][j] = ExtrapolateString(i, ArrayMessages())[j];
                                }
                            }
                        }
                        System.out.println("ER is: ");
                        for (int riga = 0; riga < ER.length; riga++) {
                            for (int colonna = 0; colonna < ER[riga].length; colonna++) {
                                System.out.print(ER[riga][colonna] + " ");
                            }
                            System.out.println();
                        }
                        Space(1);
                        for(int i=0; i<totalNumberOfProcesses; i++){
                            w_0 = 0;
                            w_1 = 0;
                            for(int j=0; j<totalNumberOfProcesses; j++){
                                if(Integer.parseInt(ER[j][i]) == 1 ){
                                    w_1++;
                                }
                                else if(Integer.parseInt(ER[j][i]) == 0){
                                    w_0++;
                                }
                                else{}
                            }
                            if(w_1>2*numberOfByzantines){
                                RV[i]="1";
                            }
                            else if(w_0>2*numberOfByzantines){
                                RV[i]="0";
                            }
                        }
                        System.out.print("The vector RV is [ ");
                        VectorPrinter(RV);
                        Space(1);
                        w_1 = 0;
                        w_0 = 0;
                        for(int i=0; i<RV.length; i++){
                            if(RV[i]=="1"){
                                w_1++;
                            }
                            else if(RV[i]=="0"){
                                w_0++;
                            }
                        }
                        boolean flag = false;
                        if(w_1>3*numberOfByzantines){
                            v = 1;
                            flag = true;
                            System.out.println("The value v is 1");
                        }
                        if(w_0>3*numberOfByzantines){
                            v = 0;
                            flag = true;
                            System.out.println("The value v is 0");
                        }
                        if(flag == false){
                            System.out.println("No value is been decided through the RV vector, so the process " + c + "is the king of the phase.");
                            w_1 = 0;
                            w_0 = 0;
                            System.out.print("ER[c][] is: [");
                            for(int i=0; i<totalNumberOfProcesses; i++){
                                System.out.print(ER[c][i] + ", ");
                                if(ER[c][i]=="1"){
                                    w_1++;
                                }
                                else if(ER[c][i]=="0"){
                                    w_0++;
                                }
                            }
                            System.out.println("]");
                            Space(1);
                            if(w_1>2*numberOfByzantines){
                                v = 1;
                                System.out.println("The value v is 1");
                                Space(1);
                            }
                            else{
                                System.out.println("The value v is 0");
                                v = 0;
                                Space(1);
                            }
                        }
                    }
                    thirdRound = false;
                    firstRound = true;
                    Producer producer2 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer2, -5, idOfTheProcess, String.valueOf(v));
                    Space(7);
                    round++;
                    Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
                    Producer.Initialize(producer1, round, idOfTheProcess, "-2");
                }
            }
            else {
                System.out.println("Maintaining round");
            }
        }
    }

    public static void BonnetInitialize(int idOfTheProcess){
        Producer producer1 = new Producer("tcp://127.0.0.1:61616", "topic");
        Producer.Initialize(producer1, 0, idOfTheProcess, "-2");
    }

    private static void Space(int Space){
        for(int i=0; i<Space; i++){
            System.out.println();
        }
    }

    private static int [] GoGoGo(int[] vector){

        while(vector[vector.length-1] == -1){
            System.out.println("Non sono ancora pronto");
        }
        return vector;
    }

    private static ArrayList<Message> ArrayMessages(){
        if(thirdRound == true){
            return Consumer.MessagesReceivedThree;
        }
        else {
            if (round % 2 == 0) {
                return Consumer.MessagesReceived;
            } else {
                return Consumer.MessagesReceivedOdd;
            }
        }
    }

    public static String ConcatenateStringArray(String[] stringArray) {
        StringBuilder sb = new StringBuilder();

        for (String str : stringArray) {
            sb.append(str).append(", ");
        }
        return sb.toString();
    }

    public static String[] ExtrapolateString(int numberOfProcess, ArrayList<Message> messagesReceived){
        String [] resultInString = messagesReceived.get(numberOfProcess).message.split(", ");
        String[] result = new String[resultInString.length];
        for (int i = 0; i < resultInString.length; i++) {
            result[i] = resultInString[i];
        }
        return result;
    }

    public static void VectorPrinter(String [] vector){
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + ",");
        }
        System.out.println("]");
    }

    public static void InitializeVector (String [] vector){
        for(int i=0; i<vector.length; i++){
            vector[i] = "-1";
        }
    }


}
