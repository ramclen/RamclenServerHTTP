package Framework.model.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import Framework.model.conection.HttpRequest;
import Framework.model.conection.HttpResponse;
import Framework.model.conection.Request;
import Framework.model.conection.Response;
import Framework.model.proccesor.Processor;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

import static com.ramclen.exceptionRunner.ExceptionHandler.getSafe;
import static com.ramclen.exceptionRunner.ExceptionHandler.runSafe;


public class HttpPetitionHandler implements HttpHandler {
    private final Processor processor;
    private RequestFactory factory;
    private Queue<HttpExchange> requestsQueue;
    private boolean requestsQueueActive = false;

    public HttpPetitionHandler(Processor simulationServer) {
        this.processor = simulationServer;
        factory = new RequestFactory();
        requestsQueue = new LinkedList<>();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(requestsQueueActive)
            requestsQueue.add(httpExchange);
        else {
            executeRequest(httpExchange);
        }
    }

    public void disableRequestQueue() {
        if(requestsQueueActive)
            runRequestsQueue();
        requestsQueueActive = false;
    }

    public void runRequestsQueue() {

        if(!requestsQueue.isEmpty())
            while (!requestsQueue.isEmpty())
                runSafe(() -> executeRequest(requestsQueue.poll()));
    }

    public void enableRequestQueue() {
        requestsQueueActive = true;
    }

    private void executeRequest(HttpExchange httpExchange) throws IOException {
        Request request = transformToRequest(httpExchange);
        System.out.println("request  --> "+request.getBody());
        Response response = processor.process(request);
        System.out.println("response --> "+response.getBody());
        sendResponse(httpExchange, response);
    }

    private Request transformToRequest(HttpExchange httpExchange) {
        try {
            return factory.create(httpExchange);
        } catch (ParseException e) {
            System.err.println("Body is not valid JSON on request received");
            e.printStackTrace();
            runSafe(() -> sendResponse(httpExchange, new HttpResponse(500, "Body is not valid JSON on request received")));
            return getSafe(() -> new HttpRequest("", "", ""));
        }
    }

    private void sendResponse(HttpExchange httpExchange, Response response) throws IOException {
        httpExchange.sendResponseHeaders(response.getStatusCode(), response.getBody().getBytes().length);
        addDataBody(httpExchange.getResponseBody(), response.getBody());
    }

    private void addDataBody(OutputStream outputStreamResponse, String responseData) throws IOException {
        transferData(new ByteArrayInputStream(responseData.getBytes(StandardCharsets.UTF_8)), outputStreamResponse, new byte[0x1024]);
    }

    private static void transferData(InputStream inputStream, OutputStream responseBody, byte[] buffer) throws IOException {
        int length;
        while ((length = inputStream.read(buffer)) >= 0) {
            responseBody.write(buffer, 0, length);
        }
        inputStream.close();
        responseBody.close();
    }
}
