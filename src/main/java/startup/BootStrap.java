package startup;

import connector.http.HttpConnector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BootStrap {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();

        Thread thread = new Thread(httpConnector);
        thread.run();
    }
}
