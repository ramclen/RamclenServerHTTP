package connection.controller;

import connection.model.conection.HttpResponse;
import connection.model.conection.Request;
import connection.model.conection.Response;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.ramclen.exceptionRunner.ExceptionHandler.getSafe;


public class ResponseFactory {

    public Response NotRespondErrorMessage(Request request, IOException e) {
        System.err.println(e.getMessage());
        return new HttpResponse(500, request.getPath() + " Not responding body petition sent "+ request.getBody());
    }

    public Response create(CloseableHttpResponse closeableHttpResponse) {
        return getSafe(() -> new HttpResponse(closeableHttpResponse.getStatusLine().getStatusCode(), extractBody(closeableHttpResponse)),
                () -> new HttpResponse(500, "Error on server trying to read the response content"));
    }

    private String extractBody(CloseableHttpResponse response) throws IOException {
        return getContent(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
    }

    private String getContent(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(512);
        int character = reader.read();
        while (character != -1) {
            stringBuilder.append((char) character);
            character = reader.read();
        }
        return stringBuilder.toString();
    }
}
