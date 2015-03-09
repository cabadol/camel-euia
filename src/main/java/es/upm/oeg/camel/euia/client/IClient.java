package es.upm.oeg.camel.euia.client;


import java.io.IOException;

public interface IClient {

    void initialize(String user, String password, String[] servers, int port) throws IOException;

    void close() throws IOException;

    void send(String message) throws IOException;

}
