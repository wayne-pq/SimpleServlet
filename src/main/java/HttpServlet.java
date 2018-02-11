import sun.misc.Request;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpServlet {

    private static final Logger log = Logger.getLogger(HttpServlet.class.getName());

    private static final int port = 8080;
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;

    private void await() {
        ServerSocket serverSocket = null;
        int port = HttpServlet.port;

        try {
            serverSocket = new ServerSocket(HttpServlet.port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

//                Request request = new Request(output);


            } catch (Exception e) {

            }
//            serverSocket.accept();

        }


    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
