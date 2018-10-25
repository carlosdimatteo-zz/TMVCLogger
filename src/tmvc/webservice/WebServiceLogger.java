package tmvc;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService()
public class WebServiceLogger {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) {
    Object implementor = new WebServiceLogger ();
    String address = "http://localhost:8080/WebService";
    Endpoint.publish(address, implementor);
  }
}
