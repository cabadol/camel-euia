package es.upm.oeg.camel.euia.component;

import es.upm.oeg.camel.euia.client.IClient;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

import java.util.Date;

public class EUIAConsumer extends ScheduledPollConsumer {
    private final EUIAEndpoint endpoint;
    private final IClient client;

    public EUIAConsumer(EUIAEndpoint endpoint, Processor processor, IClient client) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        this.client = client;
    }

    @Override
    protected int poll() throws Exception {
        Exchange exchange = endpoint.createExchange();

        // TODO create a message body
        exchange.getIn().setBody("not supported");

        try {
            // send message to next processor in the route
            getProcessor().process(exchange);
            return 1; // number of messages polled
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
    }
}