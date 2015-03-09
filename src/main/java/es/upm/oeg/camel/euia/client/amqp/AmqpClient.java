package es.upm.oeg.camel.euia.client.amqp;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AmqpClient {


    private final Connection connection;
    private final Channel channel;
    private final ExecutorService executorService;

    public AmqpClient(List<Address> addresses, String username, String password, int threads) throws IOException {

        AMQP.BasicProperties.Builder bob = new AMQP.BasicProperties.Builder();
        AMQP.BasicProperties minBasic = bob.build();
        AMQP.BasicProperties minPersistentBasic = bob.deliveryMode(2).build();
        AMQP.BasicProperties persistentBasic = bob.priority(0).contentType("application/octet-stream").build();
        AMQP.BasicProperties persistentTextPlain = bob.contentType("text/plain").build();

        // Connecting to a broker
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(10000);

        // Define a fixed pool of threads
        this.executorService = Executors.newFixedThreadPool(threads);
        this.connection = factory.newConnection(executorService,addresses.toArray(new Address[0]));

        // Open a channel
        this.channel = connection.createChannel();

    }


    public void listen(String exchange, String queue, String type, String routingKey, Consumer consumer, String tag) throws IOException {
        boolean durable = true;
        boolean exclusive = false;
        boolean autodelete = false;
        channel.exchangeDeclare(exchange, type, durable);
        channel.queueDeclare(queue, durable, exclusive, autodelete, null);
        channel.queueBind(queue, exchange, routingKey);


        boolean autoAck = false;
        channel.basicConsume(queue, autoAck, tag,consumer);

    }

    public void publish(String exchange, String key, String message) throws IOException {
        byte[] messageBodyBytes = message.getBytes();
        boolean mandatory = true;
        channel.basicPublish(exchange, key, mandatory, MessageProperties.PERSISTENT_TEXT_PLAIN, messageBodyBytes);
    }

    public void close() throws IOException {
        if (this.channel != null) this.channel.close();
        if (this.connection != null) this.connection.close();
        if (this.executorService != null) this.executorService.shutdown();
    }

}
