package es.upm.oeg.camel.euia.client;


import es.upm.oeg.camel.euia.client.amqp.AmqpClient;

import java.io.IOException;

public class ClientFactory {


    public static IClient newClient(String mode) throws IOException {
        switch(mode){
            case AmqpClient.MODE:
                String exchangeName = "epnoi.input";
                String routingKey   = "";
                return new AmqpClient(exchangeName,routingKey);
            default: throw new RuntimeException("Invalid mode: " + mode + ". Not supported");
        }

    }

}
