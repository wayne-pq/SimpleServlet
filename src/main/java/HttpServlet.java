import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class HttpServlet {

    private static final Logger log = Logger.getLogger(HttpServlet.class.getName());

//    请求接口
    private static final int port = 8088;
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;

    private void await() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(HttpServlet.port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            Socket socket;
            InputStream input;
            OutputStream output;

            try {
                System.out.println("等待指令。。。。" + LocalDateTime.now().toString());
//                从 socket拿到InputStream 和 OutputStream 并封装成 Request 和 Response
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                Request request = new Request(input);
                request.parse();
                Response response = new Response(output);

                if (SHUTDOWN_COMMAND.equals(request.getUri())) {
                    break;
                }

                response.setRequest(request);

//              根据url 分发任务给 servlet处理器（ServletProcessor） 或者 静态页面处理器 （StaticResourceProcessor）
                if (request.getUri().startsWith("/servlet/")) {
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }
                socket.close();
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        HttpServlet httpServlet = new HttpServlet();
        System.out.println("servlet容器启动成功");
        httpServlet.await();
    }
}
