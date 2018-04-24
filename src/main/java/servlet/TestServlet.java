package servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class TestServlet implements Servlet {

    private static final Logger log = Logger.getLogger(TestServlet.class.getName());

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        log.info("TestServlet init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        log.info("TestServlet service ");
        PrintWriter writer = res.getWriter();
        writer.println("HTTP/1.1 200 \r\n"
                + "Content-Type: text/html\r\n" + "\r\n" + "hello , it is TestServlet");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        log.info("TestServlet destroy");
    }
}
