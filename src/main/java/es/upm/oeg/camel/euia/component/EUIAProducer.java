package es.upm.oeg.camel.euia.component;

import es.upm.oeg.camel.euia.client.IClient;
import es.upm.oeg.camel.euia.dataformat.EUIAConverter;
import es.upm.oeg.camel.euia.model.Context;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EUIAProducer extends DefaultProducer {

    private static final Logger LOG = LoggerFactory.getLogger(EUIAProducer.class);

    private final IClient client;

    private EUIAEndpoint endpoint;

    public EUIAProducer(EUIAEndpoint endpoint, IClient client) throws IOException {
        super(endpoint);
        this.endpoint = endpoint;
        this.client = client;

    }

    public void process(Exchange exchange) throws Exception {
        // Read json
        String json = exchange.getIn().getBody(String.class);
        LOG.debug("json received: {}",json);

        // Validate json
        Context context = EUIAConverter.jsonToContext(json);

        // send to UIA service
        this.client.send(json);
    }




}