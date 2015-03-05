package es.upm.oeg.camel.euia.dataformat;


import es.upm.oeg.camel.euia.model.Context;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.util.ExchangeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;

public class EUIADataFormat implements DataFormat{

    private static final Logger LOG = LoggerFactory.getLogger(EUIADataFormat.class);

    @Override
    public void marshal(Exchange exchange, Object body, OutputStream out) throws Exception {
        Context context = ExchangeHelper.convertToMandatoryType(exchange, Context.class, body);
        String json = EUIAConverter.contextToJson(context);
        if (json != null) {
            out.write(json.getBytes());
        } else {
            LOG.debug("Cannot marshal Epnoi UIA Context to json.");
        }
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream in) throws Exception {
        String json = ExchangeHelper.convertToMandatoryType(exchange, String.class, in);
        return EUIAConverter.jsonToContext(json);
    }
}
