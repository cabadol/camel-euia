package es.upm.oeg.camel.euia.component;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class EUIAComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new EUIAEndpoint(uri, remaining, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}