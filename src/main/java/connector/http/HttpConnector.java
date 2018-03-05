package connector.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class HttpConnector implements Runnable {


    private static final Logger log = Logger.getLogger(HttpConnector.class.getName());

    private boolean shutdown = false;
    private String scheme = "http";
    private static final int port = 8080;

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    public String getScheme() {
        return scheme;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {


            log.info("等待指令。。。。" + LocalDateTime.now().toString());
            Socket socket;
            InputStream input;
            OutputStream output;

            try {
                socket = serverSocket.accept();

                HttpProcessor httpProcessor = new HttpProcessor();
                httpProcessor.process(socket);

                socket.close();

            } catch (Exception e) {
                continue;
            }

        }
    }
}
