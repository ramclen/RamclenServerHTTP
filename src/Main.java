import connection.controller.HttpSparkService;
import connection.model.conection.HttpResponse;

import static connection.RamclenServerHTTP.setPath;
import static connection.RamclenServerHTTP.setServiceAndPort;

public class Main {

    public static void main(String[] args) {
        setServiceAndPort(8080, HttpSparkService.class);
        setPath("GET:/main", (request) -> new HttpResponse(200, "Hello"));
        setPath("POST:/main", (request) -> new HttpResponse(200, ""));
        setPath("DELETE:/main", (request) -> new HttpResponse(200, ""));
    }

}
