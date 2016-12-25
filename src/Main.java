import connection.controller.HttpSparkService;
import connection.model.conection.HttpResponse;

import static connection.GenericServerHTTP.*;

public class Main {

    public static void main(String[] args) {
        setServiceAndPort(8080, HttpSparkService.class);
        get("/main", (request) -> new HttpResponse(200, "Hello"));
        post("/main", (request) -> new HttpResponse(200, ""));
        delete("/main", (request) -> new HttpResponse(200, ""));
    }

}
