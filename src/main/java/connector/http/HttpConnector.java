package connector.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class HttpConnector implements Runnable {


    private static final Logger log = Logger.getLogger(HttpConnector.class.getName());

    private boolean shutdown = false;
    private String scheme = "http";
    private static final int port = 8080;

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    ServerSocket serverSocket = null;
    /**
     * The background thread.
     */
    private Thread thread = null;
    /**
     * The minimum number of processors to start at initialization time.
     */
    protected int minProcessors = 5;


    /**
     * The maximum number of processors allowed, or <0 for unlimited.
     */
    private int maxProcessors = 15;

    /**
     * The current number of processors that have been created.
     */
    private int curProcessors = 0;

    /**
     * The set of processors that have been created but are not currently
     * being used to process a request.
     */
    private ConcurrentLinkedQueue<HttpProcessor> processors = new ConcurrentLinkedQueue();

    /**
     * The thread synchronization object.
     */
    private Object threadSync = new Object();

    public String getScheme() {
        return scheme;
    }

    public HttpConnector() {
    }

    @Override
    public void run() {

        while (true) {

            log.info("等待指令。。。。" + LocalDateTime.now().toString());
            Socket socket;
            InputStream input;
            OutputStream output;

            try {
                socket = serverSocket.accept();

                HttpProcessor httpProcessor = getProcessor();
                if (httpProcessor == null) {
                    try {
                        log.info("线程池已空，忽略请求");
                        socket.close();
                    } catch (IOException e) {
                        continue;
                    }
                }
                httpProcessor.assign(socket);
            } catch (Exception e) {
                continue;
            }

        }
    }


    /**
     * Recycle the specified Processor so that it can be used again.
     *
     * @param processor The processor to be recycled
     */
    void recycle(HttpProcessor processor) {
        processors.offer(processor);
    }


    /**
     * Create and return a new processor suitable for processing HTTP
     * requests and returning the corresponding responses.
     */
    private HttpProcessor newProcessor() {
        curProcessors++;
        HttpProcessor processor = new HttpProcessor(this);
        new Thread(processor).start();
        return processor;
    }


    /**
     * Create (or allocate) and return an available processor for use in
     * processing a specific HTTP request, if possible.  If the maximum
     * allowed processors have already been created and are in use, return
     * <code>null</code> instead.
     */
    private HttpProcessor getProcessor() {
        if (processors.size() > 0) {
            return processors.poll();
        }
        if ((maxProcessors > 0) && (curProcessors < maxProcessors)) {
            return (newProcessor());
        } else {
            if (maxProcessors < 0) {
                return (newProcessor());
            } else {
                return null;
            }
        }
    }


    /**
     * Initialize this connector (create ServerSocket here!)
     */
    public void initialize() throws IOException {
        serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
    }

    /**
     * Begin processing requests via this Connector.
     *
     * @throws LifecycleException if a fatal startup error occurs
     */
    public void start() {

        // Start our background thread
        threadStart();

        // Create the specified minimum number of processors
        while (curProcessors < minProcessors) {
            if ((maxProcessors > 0) && (curProcessors >= maxProcessors))
                break;
            HttpProcessor processor = newProcessor();
            recycle(processor);
        }

    }


    /**
     * Start the background processing thread.
     */
    private void threadStart() {
        log.info("httpConnector.starting");

        thread = new Thread(this, "SimpleServlet v1.6");
        thread.setDaemon(true);
        thread.start();
    }
}
