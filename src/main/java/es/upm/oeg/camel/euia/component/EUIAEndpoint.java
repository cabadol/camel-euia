package es.upm.oeg.camel.euia.component;

import es.upm.oeg.camel.euia.client.ClientFactory;
import es.upm.oeg.camel.euia.client.IClient;
import es.upm.oeg.camel.euia.client.amqp.AmqpClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper=true)
public class EUIAEndpoint extends DefaultEndpoint {

    private final String url;

    @UriParam
    private String servers = "127.0.0.1";//CSV
    @UriParam
    private Integer port = 5672;
    @UriParam
    private String username = "guest";
    @UriParam
    private String password = "guest";
    @UriParam
    private String mode = AmqpClient.MODE;

    public EUIAEndpoint(String uri, String url, EUIAComponent component) {
        super(uri, component);
        this.url = url;
    }

    public Producer createProducer() throws Exception {
        return new EUIAProducer(this,newClient());
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new EUIAConsumer(this, processor, newClient());
    }

    public boolean isSingleton() {
        return true;
    }

    private IClient newClient() throws IOException {
        IClient client = ClientFactory.newClient(mode);
        client.initialize(username,password,servers.split(";"),port);
        return client;

    }



}
