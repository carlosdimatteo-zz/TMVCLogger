package tmvc.example;

import tmvc.logger.TMVCLogger;

import javax.xml.ws.Endpoint;

public class Main {

    public static void main(String[] argv) {
        Object implementor = new TMVCLogger();
        String address = "http://localhost:8080/WebService";
        Endpoint.publish(address, implementor);
    }
}
