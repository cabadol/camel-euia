package es.upm.oeg.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EUIAProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(EUIAProducer.class);

    private EUIAEndpoint endpoint;

    public EUIAProducer(EUIAEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());
    }

}