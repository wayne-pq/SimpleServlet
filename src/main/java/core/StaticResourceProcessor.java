package core;

import connector.HttpRequest;
import connector.HttpRequestWrapper;
import connector.HttpResponse;
import connector.HttpResponseWrapper;

/**
 * http://localhost:8080/index.html
 */
public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
