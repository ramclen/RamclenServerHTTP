import Framework.controller.HttpSparkService;
import Framework.model.conection.HttpResponse;

import static Framework.GenericServerHTTP.*;

public class Main {

    public static void main(String[] args) {
        setServiceAndPort(8080, HttpSparkService.class);
        get("/main", (request) -> new HttpResponse(200, "Hello"));
        post("/main", (request) -> new HttpResponse(200, ""));
        delete("/main", (request) -> new HttpResponse(200, ""));
    }

}
