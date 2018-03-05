package servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("TestServlet init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        System.out.println("TestServlet service ");
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
        System.out.println("TestServlet destroy");
    }
}
