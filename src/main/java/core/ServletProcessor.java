package core;

import connector.HttpRequest;
import connector.HttpRequestWrapper;
import connector.HttpResponse;
import connector.HttpResponseWrapper;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.logging.Logger;

public class ServletProcessor {

    private static final Logger log = Logger.getLogger(ServletProcessor.class.getName());

    public void process(HttpRequest request, HttpResponse response) {
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);

        URLClassLoader loader = null;

        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(HttpResponse.WEB_ROOT + "servlet" + File.separator);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();

            urls[0] = classPath.toURI().toURL();

            loader = new URLClassLoader(urls);
        } catch (Exception e) {
            log.severe(e.toString());
        }

        Class myClass = null;

        try {
            myClass = loader.loadClass("servlet." + servletName);
        } catch (Exception e) {
            log.severe(e.toString());
        }

        Servlet servlet;

        try {
            servlet = (Servlet) myClass.newInstance();

            HttpRequestWrapper requestWrapper  = new HttpRequestWrapper(request);
            HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response);
            servlet.service(requestWrapper, responseWrapper);
        } catch (Exception e) {
            log.severe(e.toString());
        }
    }
}
