package startup;

import connector.http.HttpConnector;

import java.io.IOException;

public class BootStrap {
    public static void main(String[] args) throws IOException {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.initialize();
        httpConnector.start();
    }
}
