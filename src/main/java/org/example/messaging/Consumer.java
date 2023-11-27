package org.example.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.algorithms.Garay;
import org.example.models.Message;

import javax.jms.*;
import java.sql.Struct;
import java.util.ArrayList;

public class Consumer implements AutoCloseable{

    private String baseUrl;

    private Connection connection;

    private Session session;

    private MessageConsumer consumer;

    private boolean inError;

    private String error;

    private Thread worker;

    private boolean isRunning;

    public Message msgReceived;

    public static ArrayList<Message> MessagesReceived = new ArrayList<>();

    public static ArrayList<Message> MessagesReceivedOdd = new ArrayList<>();

    public static Message kingMsg = new Message(-4,-4,"-4");


    private boolean firstMessage = false;

    public static Coordinator coordinator;

    public static boolean even=true;

    public static boolean odd=true;



    public Consumer(String brokerUrl, String topicName, int totalNumberOfProcesses, int personalId, int numberOfByzantine, int rounds,/*Useful only for UpToYou*/int v){
        InitializeMessagesDelivery(totalNumberOfProcesses, MessagesReceived);
        InitializeMessagesDelivery(totalNumberOfProcesses, MessagesReceivedOdd);

        coordinator = new Coordinator(String.valueOf(numberOfByzantine),totalNumberOfProcesses,rounds);     //PROCESS CODE
        var factory = new ActiveMQConnectionFactory(brokerUrl);
        try{
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,session.AUTO_ACKNOWLEDGE);
            var topicDestionation = session.createTopic(topicName);
            consumer = session.createConsumer(topicDestionation);
            worker = new Thread(() ->
                    DoWork(totalNumberOfProcesses, personalId, numberOfByzantine, rounds, v));
        }catch (Exception ex){
            inError = true;
            error = ex.getMessage();
        }
    }
    public boolean isInError() {
        return inError;
    }

    public String getError() {
        return error;
    }

    public void start(){
        isRunning = true;
        worker.start();
    }

    public void stop(){
        isRunning=false;
        try{
            worker.join();
        }catch (Exception ignored){};
    }

    @Override
    public void close() {
        stop();
        try{
            consumer.close();
            session.close();
            connection.close();
        }catch (Exception ignored){
        }
    }
    private void DoWork(int totalNumberOfProcesses, int id, int nByzantine, int rounds, /*Useful only for UpToYou*/int v){
        Garay garay = new Garay(totalNumberOfProcesses,id,nByzantine, rounds);                                      //PROCESS CODE
        while(isRunning) {
            try {
                var msg = consumer.receive();
                msgReceived = new Message(msg.getIntProperty("round"), msg.getIntProperty("id"),msg.getStringProperty("message"));

                //System.out.println("È arrivato il messaggio del round " + msgReceived.round + " dall'id " + msgReceived.id + " che dice " + msgReceived.message);

                //COORDINATOR CODE BEGINS

                /*if(msgReceived.id != -1 && msgReceived.round != -5) {
                    Coordinator.howManyMessages++;
                    if(Integer.parseInt(msgReceived.message) == -2){
                        Coordinator.howManyMessages--;
                    }
                }


                if(id == -1 && Integer.parseInt(msgReceived.message) == -2){
                    if(Coordinator.answers[msgReceived.id] < msgReceived.round) {
                        Coordinator.answers[msgReceived.id]= msgReceived.round;
                        Coordinator.start(totalNumberOfProcesses,1, rounds);
                    }
                }
                if (msgReceived.id != -1 && msgReceived.round == -5){
                    Coordinator.choices[msgReceived.id] = Integer.parseInt(msgReceived.message);
                }*/

              //COORDINATOR CODE ENDS


                // PROCESS CODE BEGINS
                if(msg.getIntProperty("id") == -1) {
                    if (firstMessage == false) {
                        //coordinator.byzantineBehaviour = msg.getStringProperty("message"); //The first message is the behaviour of the byzantines
                        coordinator.byzantineBehaviour = "WorstCaseEven";                     //ATTENZIONE QUI
                        firstMessage = true;
                    } else {
                        //System.out.println("HO Ricevuto il messaggio da " + msg.getIntProperty("id") + " che dice " + msg.getStringProperty("message"));
                        coordinator.byzantineArray = msg.getStringProperty("message");
                        //System.out.println("mi è arrivato il messaggio dei bizantini ed è " + coordinator.byzantineArray);
                        Thread worker1 = new Thread(() -> {
                            garay.startEven(coordinator,v);
                        });
                        worker1.start();
                        firstMessage = false;
                    }
                }
                if(msgReceived.round == -4){
                    if(Integer.parseInt(msgReceived.message) == 3){
                        if(id % 2 == 0) {
                            if(even == true) {
                                Message msgByz = new Message(msgReceived.round, msgReceived.id, "0");
                                kingMsg = msgByz;
                                even = !even;
                            }
                            else{
                                Message msgByz = new Message(msgReceived.round, msgReceived.id, "1");
                                kingMsg = msgByz;
                                even = !even;
                            }
                        }
                        else{
                            if(even == true) {
                                Message msgByz = new Message(msgReceived.round, msgReceived.id, "1");
                                kingMsg = msgByz;
                                even = !even;
                            }
                            else{
                                Message msgByz = new Message(msgReceived.round, msgReceived.id, "0");
                                kingMsg = msgByz;
                                even = !even;
                            }
                        }
                    }
                    else {
                        kingMsg = msgReceived;
                    }
                }
                else {
                    if (msgReceived.round % 2 != 0 && msgReceived.round != -5) {
                        MessagesReceivedOdd.set(msg.getIntProperty("id"), msgReceived);
                    }
                    if (msgReceived.round % 2 == 0 && Integer.parseInt(msg.getStringProperty("message")) != -2 && msgReceived.round != -5) {//ATTENTO QUI, QUESTO IF NON VA BENE PER LA CLASSE COORDINATOR LATO COORDINATORE
                        if(Integer.parseInt(msgReceived.message) == 3){
                            if(id % 2 == 0) {
                                if(odd == true) {
                                    Message msgByz = new Message(msgReceived.round, msgReceived.id, "1");
                                    MessagesReceived.set(msgReceived.id, msgByz);
                                    //even = !even;
                                }
                                else{
                                    Message msgByz = new Message(msgReceived.round, msgReceived.id, "0");
                                    MessagesReceived.set(msgReceived.id, msgByz);
                                    //even = !even;
                                }
                            }
                            else{
                                if(odd == true) {
                                    Message msgByz = new Message(msgReceived.round, msgReceived.id, "0");
                                    MessagesReceived.set(msgReceived.id, msgByz);
                                    //odd = !odd;
                                }
                                else{
                                    Message msgByz = new Message(msgReceived.round, msgReceived.id, "1");
                                    MessagesReceived.set(msgReceived.id, msgByz);
                                    //odd =!odd;
                                }
                            }
                        }
                        else {
                            MessagesReceived.set(msg.getIntProperty("id"), msgReceived);
                        }
                    }
                    if (Integer.parseInt(msg.getStringProperty("message")) == -2) {
                        //System.out.println("The process " + msg.getIntProperty("id") + " is ready to go in the next round");
                    }
                }
                //PROCESS CODE ENDS

            }catch(Exception ex){
                inError = true;
                error = ex.getMessage();
            }
        }
    }

    public void Initialize(Consumer consumer){
        Thread thread = new Thread(() -> {
            //var consumer = new Consumer(brokerUrl, topicName);
            consumer.start();
            int c;
            try {
                c = System.in.read();
            } catch (Exception ignored) {
            }
            //consumer.close();
        });
        thread.start();
    }

    public void InitializeMessagesDelivery(int totalNumberOfProcesses, ArrayList<Message> arrayList){
        for (int i=0; i<totalNumberOfProcesses; i++){
            Message msg0 = new Message( -2, -2, "-1");
            arrayList.add(msg0);
        }
    }

}

