/**
 * http://localhost:8080/index.html
 */
public class StaticResourceProcessor {

    public void process(RequestWrapper request, ResponseWrapper response) {
        try {
            response.sendStaticResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
