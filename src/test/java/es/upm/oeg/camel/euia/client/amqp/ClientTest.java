package es.upm.oeg.camel.euia.client.amqp;


import es.upm.oeg.camel.euia.client.ClientFactory;
import es.upm.oeg.camel.euia.client.IClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class ClientTest {


    private IClient client;

    @Before
    public void setup() throws IOException {
        this.client = ClientFactory.newClient(AmqpClient.MODE);
        String[] servers = new String[]{"127.0.0.1"};
        this.client.initialize("guest", "guest", servers, 5672);
    }

    @After
    public void close() throws IOException {
        this.client.close();
    }

    @Test
    @Ignore
    public void sendMessage() throws IOException, InterruptedException {
        try {
            this.client.send("Hello World!");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
