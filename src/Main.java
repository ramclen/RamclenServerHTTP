import connection.controller.HttpNativeService;
import connection.model.conection.HttpResponse;
import connection.model.conection.Request;
import connection.model.handler.Handler;
import connection.model.proccesor.RequestDictionary;
import connection.model.proccesor.RequestProcessor;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        new HttpNativeService(new RequestProcessor(new myRequestDictionary()), 8080).start();
    }

    private static class myRequestDictionary implements RequestDictionary {
        HashMap<String, Handler> requestDictionary = new HashMap<>();

        public myRequestDictionary() {
            buildRequestMapper();
        }

        @Override
        public Handler find(Request request) {
            return requestDictionary.get(request.getMethod() + request.getPath());
        }

        public void buildRequestMapper() {
            requestDictionary.put("Get:/main", (request) -> new HttpResponse(200, ""));
            requestDictionary.put("Post:/main", (request) -> new HttpResponse(200, ""));
            requestDictionary.put("Delete:/main", (request) -> new HttpResponse(200, ""));
        }

    }
}
