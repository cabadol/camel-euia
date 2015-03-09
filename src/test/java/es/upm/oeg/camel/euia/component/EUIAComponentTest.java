package es.upm.oeg.camel.euia.component;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class EUIAComponentTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testRoute() throws Exception {

        String json = "{\n" +
                "    \"publications\": [\n" +
                "        {\n" +
                "            \"title\": \"Identificación y caracterización funcional del complejo nuclear de proteínas LSM de \\\"Arabidopsis thaliana\\\" en la respuesta de aclimatación a las temperaturas bajas\",\n" +
                "            \"description\": \"\",\n" +
                "            \"published\": \"2015-03-03T13:38:54Z\",\n" +
                "            \"uri\": \"http://localhost:8080/oaipmh/resource-1425392911570-reduced.pdf\",\n" +
                "            \"url\": \"file://oaipmh/ucm/2015-3-3/resource-1425389934000.pdf\",\n" +
                "            \"language\": \"es\",\n" +
                "            \"rights\": \"info:eu-repo/semantics/openAccess\",\n" +
                "            \"creators\": [\n" +
                "                \"Hernández Verdeja, Tamara\",\n" +
                "                \"Fernandez Libre, Antonio\"\n" +
                "            ],\n" +
                "            \"format\": \"pdf\",\n" +
                "            \"reference\": {\n" +
                "                \"format\": \"xml\",\n" +
                "                \"url\": \"file://oaipmh/ucm/2015-3-3/resource-1425389934000.xml\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"source\": {\n" +
                "        \"name\": \"ucm\",\n" +
                "        \"uri\": \"http://www.epnoi.org/oai-providers/ucm\",\n" +
                "        \"url\": \"http://eprints.ucm.es/cgi/oai2\",\n" +
                "        \"protocol\": \"oaipmh\"\n" +
                "    }\n" +
                "}\n";

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.expectedBodiesReceived(json);

        template.sendBody(json);
        resultEndpoint.assertIsSatisfied();

        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").
                    to("euia://epnoi?servers=localhost&port=5672").
                    to("mock:result");
            }
        };
    }
}