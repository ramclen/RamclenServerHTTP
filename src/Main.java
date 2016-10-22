import connection.controller.HttpNativeService;
import connection.model.conection.HttpResponse;

public class Main {

    public static void main(String[] args) {
        new HttpNativeService(request -> new HttpResponse(200, "Hello World"), 8080).start();
    }
}
