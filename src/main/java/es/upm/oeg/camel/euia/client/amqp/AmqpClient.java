package es.upm.oeg.camel.euia.client.amqp;


import com.rabbitmq.client.*;
import es.upm.oeg.camel.euia.client.IClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AmqpClient implements IClient{

    private static final Logger LOG = LoggerFactory.getLogger(AmqpClient.class);

    public static final String MODE = "batch";

    private static final int THREADS = 20;

    private final String exchange;
    private final String routingKey;

    private Connection connection;
    private Channel channel;
    private ExecutorService executorService;

    public AmqpClient(String exchange, String routingKey) throws IOException {

        AMQP.BasicProperties.Builder bob = new AMQP.BasicProperties.Builder();
        AMQP.BasicProperties minBasic = bob.build();
        AMQP.BasicProperties minPersistentBasic = bob.deliveryMode(2).build();
        AMQP.BasicProperties persistentBasic = bob.priority(0).contentType("application/octet-stream").build();
        AMQP.BasicProperties persistentTextPlain = bob.contentType("text/plain").build();

        this.exchange = exchange;
        this.routingKey = routingKey;

    }

    @Override
    public void initialize(String user, String password, String[] servers, int port) throws IOException{
        // Connecting to a broker
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(user);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(10000);

        // Define a fixed pool of threads
        this.executorService = Executors.newFixedThreadPool(THREADS);

        // Create a set of addresses
        Address[] addresses = new Address[servers.length];
        for(int i=0;i < servers.length ; i++){
            addresses[i]=new Address(servers[i], port);
        }

        // Connect
        LOG.debug("Opening connection to: {} by {}/{}", addresses,user,password);
        this.connection = factory.newConnection(executorService,addresses);

        // Open a channel
        LOG.debug("Creating channel");
        this.channel = connection.createChannel();

        // Create, if not exist, exchange
        boolean durable = true;
        String type = "direct"; // direct, fanout, topic, headers
        channel.exchangeDeclare(exchange, type, durable);

    }

    @Override
    public void close() throws IOException {
        if (this.channel != null) this.channel.close();
        if (this.connection != null) this.connection.close();
        if (this.executorService != null) this.executorService.shutdown();
    }

    @Override
    public void send(String message) throws IOException {
        LOG.debug("Sending to exchange: {} , routingKey: {} the message: {}", this.exchange, this.routingKey, message);
        publish(this.exchange,this.routingKey,message);
    }


    public void listen(String queue, String routingKey, Consumer consumer, String tag) throws IOException {
        boolean durable = true;
        boolean exclusive = false;
        boolean autodelete = false;
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


}
