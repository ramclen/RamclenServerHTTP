package connection.controller;

import spark.Route;
import spark.Spark;
import spark.SparkBase;
import connection.model.conection.HttpRequest;
import connection.model.proccesor.RequestProcessor;

public class HttpSparkService implements HttpService{
    private RequestProcessor requestProcessor;
    private int port;

    public HttpSparkService(RequestProcessor requestProcessor, int port) {
        this.requestProcessor = requestProcessor;
        this.port = port;
    }

    @Override
    public void start() {
        SparkBase.port(port);
        SparkBase.staticFileLocation("/public");
        setDefault((req, res) ->
                requestProcessor
                        .process(new HttpRequest(req.requestMethod(), req.pathInfo().replace("/api",""), req.body()))
                        .getBody());

        Spark.get("/about", (req, res) -> "Tafat. SIANI-2015");
    }

    private void setDefault(Route route) {
        Spark.get("/api/*", route);
        Spark.post("/api/*", route);
        Spark.delete("/api/*", route);
    }
}
