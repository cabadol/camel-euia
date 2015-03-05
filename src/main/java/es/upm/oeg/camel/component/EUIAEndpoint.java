package es.upm.oeg.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

public class EUIAEndpoint extends DefaultEndpoint {

    private final String url;

    public EUIAEndpoint(String uri, String url, EUIAComponent component) {
        super(uri, component);
        this.url = url;
    }

    public Producer createProducer() throws Exception {
        return new EUIAProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new EUIAConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
}
