package org.example.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;
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

    public ArrayList<Message> MV = new ArrayList<>();

    private boolean firstMessage = false;

    public Consumer(String brokerUrl, String topicName, int totalNumberOfProcesses){
        var factory = new ActiveMQConnectionFactory(brokerUrl);
        try{
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,session.AUTO_ACKNOWLEDGE);
            var topicDestionation = session.createTopic(topicName);
            consumer = session.createConsumer(topicDestionation);
            worker = new Thread(() ->
                    DoWork(totalNumberOfProcesses));
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
    private void DoWork(int totalNumberOfProcesses){
        InitializeMV(totalNumberOfProcesses);
        while(isRunning) {
            try {
                var msg = consumer.receive();
                //var objMsg = (ObjectMessage)msg;
                msgReceived = new Message(msg.getIntProperty("id"),msg.getStringProperty("message"));
                if(msg.getIntProperty("id") == -1){
                    if(firstMessage == false){
                        Coordinator.byzantineBehaviour = msg.getStringProperty("message"); //The first message is the behaviour of the byzantines
                        firstMessage = true;
                    }
                    else{
                        Coordinator.byzantineArray.add(msg.getStringProperty("message"));
                        //System.out.println(Coordinator.byzantineArray);//The following messages are the processes that will be byzantine
                    }

                }
                //System.out.println("HO Ricevuto il messaggio da " + msg.getIntProperty("id") + " che dice " + msg.getStringProperty("message"));
                MV.set(msg.getIntProperty("id"), msgReceived);
                for (int i = 0; i<MV.size(); i++){
                    //System.out.println("Nel vettore MV alla posizione " + i + " c'è il messaggio " +  MV.get(i).message);
                }
                //System.out.println("HO Ricevuto il messaggio da " + msg.getIntProperty("id") + " che dice " + choice);





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

    public void InitializeMV(int totalNumberOfProcesses){
        for (int i=0; i<totalNumberOfProcesses; i++){
            Message msg0 = new Message(0, "-1");
            MV.add(msg0);
        }
    }

}

