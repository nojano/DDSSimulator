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

    public ArrayList<Message> MessagesReceived = new ArrayList<>();

    private boolean firstMessage = false;

    public boolean evenOdd = true;

    public Consumer(String brokerUrl, String topicName, int totalNumberOfProcesses, int personalId, int numberOfByzantine, int rounds){
        var factory = new ActiveMQConnectionFactory(brokerUrl);
        try{
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,session.AUTO_ACKNOWLEDGE);
            var topicDestionation = session.createTopic(topicName);
            consumer = session.createConsumer(topicDestionation);
            worker = new Thread(() ->
                    DoWork(totalNumberOfProcesses, personalId, numberOfByzantine, rounds));
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
    private void DoWork(int totalNumberOfProcesses, int id, int nByzantine, int rounds){
        Garay garay = new Garay(totalNumberOfProcesses,id,nByzantine);
        //InitializeMV(totalNumberOfProcesses);
        while(isRunning) {
            try {
                var msg = consumer.receive();
                msgReceived = new Message(msg.getIntProperty("round"), msg.getIntProperty("id"),msg.getStringProperty("message"));
                System.out.println("My id is " + id);

                /*if(id == -1 && Integer.parseInt(msgReceived.message) == -2){

                    System.out.println("Helloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                    if(Coordinator.answers[msgReceived.id] < msgReceived.round) {
                        System.out.println("I'm filling answer of -1");
                        Coordinator.answers[msgReceived.id]= msgReceived.round;
                        Coordinator.start(totalNumberOfProcesses,1, rounds);
                    }
                }*/

                if(msg.getIntProperty("id") == -1) {
                    if (firstMessage == false) {
                        Coordinator.byzantineBehaviour = msg.getStringProperty("message"); //The first message is the behaviour of the byzantines
                        firstMessage = true;
                    } else {
                        System.out.println("HO Ricevuto il messaggio da " + msg.getIntProperty("id") + " che dice " + msg.getStringProperty("message"));
                        Coordinator.byzantineArray = msg.getStringProperty("message");
                        System.out.println("mi è arrivato il messaggio dei bizantini");

                        garay.startEven();
                        firstMessage = false;
                    }

                }
                MessagesReceived.set(msg.getIntProperty("id"), msgReceived);
                for (int i = 0; i<MessagesReceived.size(); i++){
                    //System.out.println("Nel vettore MessagesReceived alla posizione " + i + " c'è il messaggio " +  MessagesReceived.get(i).message);
                }
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

    /*public void InitializeMV(int totalNumberOfProcesses){
        for (int i=0; i<totalNumberOfProcesses; i++){
            Message msg0 = new Message( -2, -2, "-1");
            MessagesReceived.add(msg0);
        }
    }*/

}

