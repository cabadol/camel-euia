package es.upm.oeg.camel.euia.client.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public abstract class AmqpConsumer extends DefaultConsumer{


    public AmqpConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
    {
        String routingKey = envelope.getRoutingKey();
        String contentType = properties.getContentType();
        long deliveryTag = envelope.getDeliveryTag();
        // (process the message components here ...)
        getChannel().basicAck(deliveryTag, false);
    }


}
