package org.example.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.models.Message;

import javax.jms.*;

public class Producer implements AutoCloseable {
    //private String baseUrl;

    //private ConnectionFactory connectionFactory;

    private Connection connection;

    private Session session;

    //private Topic topic;    //I choose topic over queue

    private MessageProducer producer;

    private boolean inError;

    private String error;

    //private static Producer instance;

    public Producer(String borkerUrl, String topicName){
        var factory = new ActiveMQConnectionFactory(borkerUrl);
        try{
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            var destinationTopic = session.createTopic(topicName);
            producer = session.createProducer(destinationTopic);
        }catch(Exception ex){
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

    public void publish(Message message){
        try{
            var msg = session.createObjectMessage();
            msg.setIntProperty("round", message.round);
            msg.setIntProperty("id", message.id);
            msg.setStringProperty("message", message.message);
            producer.send(msg);
        }
        catch (Exception ex){
            inError = true;
            error = ex.getMessage();
        }
    }



    @Override
    public void close(){
        try{
            producer.close();
            session.close();
            connection.close();
        }catch (Exception ignored){}
    }

    public static void Initialize(Producer producer, int round, int id, String messageText){
        Thread thread = new Thread(() -> {
            Message message = new Message(round,id,messageText);
            if(producer.isInError()){
                System.out.println(producer.getError());
            }else{
                producer.publish(message);
                //System.out.println("Message " + message.id + " added to the topic");
                if(producer.isInError()){
                    System.out.println(producer.getError());
                }
            }
            producer.close();} );
        thread.start();
    }
}
