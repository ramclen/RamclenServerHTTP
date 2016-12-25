package Framework.model.handler;

import com.sun.net.httpserver.HttpExchange;
import org.json.simple.parser.ParseException;
import Framework.model.conection.HttpRequest;
import Framework.model.conection.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static com.ramclen.exceptionRunner.ExceptionHandler.getSafe;


public class RequestFactory {

    public Request create(HttpExchange exchange) throws ParseException {
        String body = getSafe(() -> getBodyRequest(exchange), () -> "");
        Request request = new HttpRequest(exchange.getRequestMethod(), exchange.getRequestURI().getPath(), body);
        AddQueryToParameters(exchange, request);
        return request;
    }


    public String getBodyRequest(HttpExchange exchange) throws UnsupportedEncodingException {
        return getContent(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
    }

    private void AddQueryToParameters(HttpExchange exchange, Request request) {
        if(exchange.getRequestURI().getQuery()!=null)
            if (!exchange.getRequestURI().getQuery().equals(""))
                queryToParameters(exchange, request);
    }

    private void queryToParameters(HttpExchange exchange, Request request) {
        String query = exchange.getRequestURI().getQuery();
            for (String pair : query.split("&"))
                setPairParameter(request, pair);
    }

    private void setPairParameter(Request request, String pair) {
        request.setParameter(pair.split("=")[0], pair.split("=")[1]);
    }

    private String getContent(InputStreamReader inputStream) {
        return getSafe(() -> getContent(new BufferedReader(inputStream)), () -> "");
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

